package com.helome.messagecenter.core;

import com.helome.messagecenter.core.TimeoutManager.Slot;
import com.helome.messagecenter.message.Message;
import com.helome.messagecenter.message.business.LeaveChatMessage;
import com.helome.messagecenter.utils.MemCachedUtil;
import com.helome.messagecenter.utils.Utils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.LinkedBlockingQueue;

public class Context {
	private final static Logger logger = LoggerFactory.getLogger(Context.class);
	private static ConcurrentHashMap<Long, List<Endpoint>> ID_TO_ENDPOINTS = new ConcurrentHashMap<Long, List<Endpoint>>();
	private static ConcurrentHashMap<Endpoint, Long> ENDPOINT_TO_ID = new ConcurrentHashMap<Endpoint, Long>();
	private static ConcurrentHashMap<Long, Long> IN_VIDEO_OR_AUDIO = new ConcurrentHashMap<Long, Long>();
	public static ConcurrentHashMap<Endpoint, Long> ENDPOINTS_TO_PEER = new ConcurrentHashMap<Endpoint, Long>();
	public static BlockingQueue<Long> fileMessageQueue = new LinkedBlockingQueue<Long>();
	public static ConcurrentMap<Long, ConcurrentMap<Integer, Message>> fileMessageData = new ConcurrentHashMap<Long, ConcurrentMap<Integer, Message>>();
	public static TimeoutManager<Timeout> TIMEOUT_MANAGER = new TimeoutManager<Timeout>(
			60000, 1000);
	public static ConcurrentHashMap<Long, String> ID_TO_TOKEN = new ConcurrentHashMap<Long, String>();
	public static ConcurrentHashMap<Long, Slot> ID_TO_SLOT = new ConcurrentHashMap<Long, Slot>();
    private static ConcurrentHashMap<Long, Long> PEER_AUDIO_OR_VIDEO = new ConcurrentHashMap<Long, Long>();     /** 接入音/视频通讯的用户(ID)对 **/
    public static TimeoutManager<Timeout> TIMEOUT_MANAGER_PAV = new TimeoutManager<Timeout>(60*1000, 1000);   /** 等待接入音/视频通讯的计时器 **/
    public static ConcurrentHashMap<Long, Slot> ID_TO_SLOT_PAV = new ConcurrentHashMap<Long, Slot>();    /** 辅助等待接入音/视频通讯的计时器 **/

	public static boolean registerVA(long userId, long inviteeId) {
		synchronized (IN_VIDEO_OR_AUDIO) {
			if ((!IN_VIDEO_OR_AUDIO.containsKey(userId))
					&& (!IN_VIDEO_OR_AUDIO.containsKey(inviteeId))) {
				logger.info("id(" + userId + ");id(" + inviteeId + ")在VA中注册");
                IN_VIDEO_OR_AUDIO.put(userId, inviteeId);
				return true;
			}
			return false;
		}
	}

	public static void deregisterVA(long userId, long inviteeId) {
		synchronized (IN_VIDEO_OR_AUDIO) {
			logger.info("id(" + userId + "),id(" + inviteeId + ")从VA注销");
			if (!IN_VIDEO_OR_AUDIO.remove(userId, inviteeId)) {
                IN_VIDEO_OR_AUDIO.remove(inviteeId, userId);
			}
		}
	}

	public static boolean existInVA(long userId) {
		synchronized (IN_VIDEO_OR_AUDIO) {
			logger.info("用户：" + userId + "是否已经在VA中");
			return IN_VIDEO_OR_AUDIO.containsKey(userId)
					|| IN_VIDEO_OR_AUDIO.containsValue(userId);
		}
	}

    public static boolean existInVA(long userId, long inviteeId) {
        synchronized (IN_VIDEO_OR_AUDIO) {
            logger.info("用户双方:id(" + userId + "),id(" + inviteeId + ")是否已经在VA中");
            return IN_VIDEO_OR_AUDIO.containsKey(inviteeId)
                    && IN_VIDEO_OR_AUDIO.containsValue(userId);
        }
    }

	public static Endpoint register(Long id, Endpoint endpoint) {
		ENDPOINT_TO_ID.put(endpoint, id);
		MemCachedUtil.setOnLine(id, "1");
		logger.info("id {},endpoint 注册",id, endpoint);
		if (ID_TO_ENDPOINTS.containsKey(id)) {
			if (endpoint instanceof WebSocketEndpoint) {
				ID_TO_ENDPOINTS.get(id).add(endpoint);
				return null;
			} else {
				Endpoint e = ID_TO_ENDPOINTS.get(id).get(0);
				List<Endpoint> edpoints = new LinkedList<Endpoint>();
				edpoints.add(endpoint);
				ID_TO_ENDPOINTS.put(id, edpoints);
				return e;
			}
		} else {
			List<Endpoint> edpoints = new LinkedList<Endpoint>();
			edpoints.add(endpoint);
			ID_TO_ENDPOINTS.put(id, edpoints);
			// memcacheClient.set("MC.user.id"+String.valueOf(id), 0, "true");
			return null;
		}
	}

	public static void deregister(Long id, Message message) {
		List<Endpoint> endpoints = null;
		endpoints = ID_TO_ENDPOINTS.get(id);
		if (endpoints != null) {
			synchronized (endpoints) {
				for (Endpoint endpoint : endpoints) {
					if (endpoint instanceof SocketEndpoint) {
						endpoint.getContext().channel()
								.writeAndFlush(message.toBinary());
					} else {
						endpoint.getContext()
								.channel()
								.writeAndFlush(
										new TextWebSocketFrame(message.toJson()));
					}
					// deregisterEndpoint(endpoint);
					logger.info("关闭用户{}的{}" , id , endpoint.getContext().channel());
					Utils.close(endpoint.context);
				}
			}
			for (int i=0;i<endpoints.size();i++) {// list 遍历同时删除元素
				deregisterEndpoint(endpoints.get(i));
			}
		}
	}

	public static List<Endpoint> getEndpoints(Long id) {
		return ID_TO_ENDPOINTS.get(id);

	}

	public static void deregisterChannelHandlerContext(ChannelHandlerContext ctx) {
		deregisterEndpoint(EndpointFactory.removeEndpoint(ctx));
	}

	public static void deregisterEndpoint(Endpoint endpoint) {
		logger.info("deregisterEndpoint {}",endpoint);
		if (endpoint != null) {
			if (endpoint instanceof WebRTCEndpoint) {
				Long userId = ENDPOINT_TO_ID.get(endpoint);
				Long peerId = ENDPOINTS_TO_PEER.remove(endpoint);
				logger.info("deregister userId {} peerId {}.", userId,peerId);
				MemCachedUtil.setInChat(userId, peerId, 0L);// 设置成不在聊天室状态
				if (userId != null && peerId != null) {
					removeWebRTCEndpointPeer(endpoint, userId, peerId);
				}
			}

			Long id = ENDPOINT_TO_ID.remove(endpoint);
			if (id != null) {
				synchronized (ID_TO_ENDPOINTS.get(id)) {
					if (!ID_TO_ENDPOINTS.isEmpty()) {
						if (ID_TO_ENDPOINTS.get(id) != null) {
							ID_TO_ENDPOINTS.get(id).remove(endpoint);
							if (ID_TO_ENDPOINTS.get(id) != null
									&& ID_TO_ENDPOINTS.get(id).size() == 0) {
								ID_TO_ENDPOINTS.remove(id);
								removeIdToToken(id);
								logger.info("用户 {} 离开.", id);
								MemCachedUtil.setOnLine(id, "0");// 设置成不在线状态
							}
						}
					}
				}
			}
		}
	}

	public static void registerWebRTCEndpointPeer(Endpoint endpoint,
			Long receiverId) {
		logger.debug("用户：" + receiverId + "注册WebRTCEndpointPee");
		ENDPOINTS_TO_PEER.put(endpoint, receiverId);

	}

	public static void removeWebRTCEndpointPeer(Endpoint endpoint, Long userId,
			Long receiverId) {
		logger.debug("removeWebRTCEndpointPeer userId {},receiverId {}",userId, receiverId);
		Context.deregisterVA(userId, receiverId);
        Context.deregisterPAV(userId,receiverId);
        Context.deregisterPAV(receiverId,userId);
		List<Endpoint> toEndpoints = Context.getEndpoints(receiverId);
		LeaveChatMessage message = MessageFactory.createLeaveChatMessage(
				userId, receiverId);

		List<Endpoint> fromEndpoints = Context.getEndpoints(userId);
		if (fromEndpoints != null) {
			synchronized (fromEndpoints) {
				for (Endpoint toEndpoint : fromEndpoints) {
					if (toEndpoint != endpoint) {
						if (toEndpoint instanceof SocketEndpoint) {
							toEndpoint.getContext().channel()
									.writeAndFlush(message.toBinary());
						} else {
							toEndpoint
									.getContext()
									.channel()
									.writeAndFlush(
											new TextWebSocketFrame(message
													.toJson()));
						}
					}
				}
			}
			if (toEndpoints != null) {

				synchronized (toEndpoints) {
					for (Endpoint toEndpoint : toEndpoints) {
						if (toEndpoint instanceof SocketEndpoint) {
							toEndpoint.getContext().channel()
									.writeAndFlush(message.toBinary());
						} else {
							toEndpoint
									.getContext()
									.channel()
									.writeAndFlush(
											new TextWebSocketFrame(message
													.toJson()));
						}
					}
				}
			}
		}

	}

	public static void registerIdToToken(Long id, String token) {
		ID_TO_TOKEN.put(id, token);
	}

	public static void removeIdToToken(Long id) {
		ID_TO_TOKEN.remove(id);
	}

    public static boolean registerPAV(long userId, long peerId) {
        synchronized (PEER_AUDIO_OR_VIDEO) {
            if ((!PEER_AUDIO_OR_VIDEO.containsKey(userId))
                    && (!PEER_AUDIO_OR_VIDEO.containsValue(peerId))) {
                logger.info("userId(" + userId + ");peerId(" + peerId + ")在PAV中注册");
                PEER_AUDIO_OR_VIDEO.put(userId, peerId);
                return true;
            }
            return false;
        }
    }

    public static void deregisterPAV(long userId, long peerId) {
        synchronized (PEER_AUDIO_OR_VIDEO) {
            logger.info("userId(" + userId + "),peerId(" + peerId + ")从PAV中注销");
            if (PEER_AUDIO_OR_VIDEO.containsKey(userId)
                    && PEER_AUDIO_OR_VIDEO.containsValue(peerId)) {
                PEER_AUDIO_OR_VIDEO.remove(userId, peerId);
            }
        }
    }

    public static boolean existInPAV(long userId, long peerId) {
        synchronized (PEER_AUDIO_OR_VIDEO) {
            logger.info("userId(" + peerId + "),peerId(" + userId + ")是否已经在PAV中注册");
            return PEER_AUDIO_OR_VIDEO.containsKey(peerId)
                    && PEER_AUDIO_OR_VIDEO.containsValue(userId);
        }
    }

	public static List<Endpoint> getGroupEndpoints(String receiverIds) {
		// ID_TO_ENDPOINTS.get(id);
		List<Endpoint> result = new ArrayList<Endpoint>();
		String[] ids = receiverIds.split(",");
		for(String id:ids){
			List<Endpoint> port = ID_TO_ENDPOINTS.get(Long.parseLong(id));
			result.addAll(port);
		}
		
		return result;
	}

}
