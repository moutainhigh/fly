package cn.jkm.api.klm.manage.service.content.contentpost;

import cn.jkm.api.core.helper.ApiResponse;
import cn.jkm.api.klm.manage.service.AbstractManageService;
import cn.jkm.core.domain.mongo.content.ContentPost;
import cn.jkm.core.domain.mysql.content.SettingChannel;
import cn.jkm.framework.core.validator.NotNull;
import cn.jkm.service.content.ChannelService;
import cn.jkm.service.content.ContentPostService;
import cn.jkm.service.core.domain.ListResult;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhengzb on 2017/7/27.
 * 查询用户投诉列表
 */
@Component("contentComplaintList1.0")
public class ContentComplaintList extends AbstractManageService {
    @Reference(version = "1.0")
    ContentPostService contentPostService;
    @Reference(version = "1.0")
    ChannelService channelService;
    @Override
    public Map service(Map request) {
        ListResult<ContentPost> contentPosts = contentPostService.listComplaint(request);
        return ApiResponse.success().body(new HashMap(){{
            put("count",contentPosts.getCount());
            put("list",new ArrayList(){{
                contentPosts.getList().forEach(v->{{
//                    List<SettingChannel> channels = channelService.findAllColumnList(v.getChannelId());
                    add(new HashMap(){{
                        put("id",v.getId());
                        put("title",v.getTitle());
//                        put("channelName",v.getId());
//                        put("columnName",v.getChannelId());
                        put("complaintNoHandleCount",v.getComplaintNoHandleCount());
                        put("complaintCount",v.getComplaintCount());
                        put("complaintTime",v.getComplaintTime());
//                        put("handleName",v.getId());
                        put("handleTime",v.getHandleTime());
                    }});
                }});
            }});
        }});
    }
}
