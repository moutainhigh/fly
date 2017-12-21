package cn.jkm.api.klm.manage.service.content.contentpost;

import cn.jkm.api.core.helper.ApiResponse;
import cn.jkm.api.klm.manage.service.AbstractManageService;
import cn.jkm.core.domain.mongo.content.ContentComplaint;
import cn.jkm.framework.core.validator.NotNull;
import cn.jkm.service.content.ContentComplaintService;
import cn.jkm.service.core.domain.ListResult;
import cn.jkm.service.sys.UserService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhengzb on 2017/7/27.
 * 分页获取待处理的评论的投诉列表
 */
@Component("contentCommentComplaintNo1.0")
@NotNull(name = {"id", "type","limit","page"}, message = "缺少参数")
public class ContentCommentComplaintNo extends AbstractManageService {
    @Reference(version = "1.0")
    ContentComplaintService contentComplaintService;
    @Reference(version = "1.0")
    UserService userService;

    @Override
    public Map service(Map request) {
        ListResult<ContentComplaint> complaints = contentComplaintService.listComplaintComment(formatString(request.get("id")),
                formatString(request.get("commentId")),
                formatString(request.get("type")),
                page(request),
                limit(request));
        return ApiResponse.success().body(
                new HashMap(){{
                    put("count",complaints.getCount());
                    put("list",new ArrayList(){{
                        complaints.getList().forEach(v->{{
                            add(
                                    new HashMap(){{
                                        //                               User user = userService.find(v.getUserId());
                                        put("id",v.getId());
                                        put("content",v.getContent());
                                        put("complaintStatus",v.getComplaintStatus());
                                        put("createAt",v.getCreateAt());
                                        //                               put("userName",user.getName());
                                        //                               put("headUrl",user.getHeadUrl());
                                    }});
                        }});
                    }});
                }});
    }
}
