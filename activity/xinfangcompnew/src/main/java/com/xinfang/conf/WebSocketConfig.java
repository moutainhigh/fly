/*package com.xinfang.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer{

	//建立服务端点接收客户端连接,STOMP协议
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("sockets").setAllowedOrigins("*").withSockJS();
		//添加“/socket”端点，开启SockJS支持。。允许跨域
	}

	//这个方法的作用是定义消息代理，通俗一点讲就是设置消息连接请求的各种规范信息。
	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		registry.enableSimpleBroker("/topic");//表示客户端订阅地址的前缀信息，也就是客户端接收服务端消息的地址的前缀信息
		registry.setApplicationDestinationPrefixes("/app");//指服务端接收地址的前缀，意思就是说客户端给服务端发消息的地址的前缀
		//上面两个方法定义的信息其实是相反的，一个定义了客户端接收的地址前缀，一个定义了客户端发送地址的前缀
		registry.setUserDestinationPrefix("/user/");//这个是点对点消息的接收默认/user/
		super.configureMessageBroker(registry);
	}

}
*/