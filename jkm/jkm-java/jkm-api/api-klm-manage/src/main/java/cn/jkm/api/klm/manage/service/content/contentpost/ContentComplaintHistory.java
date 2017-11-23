package cn.jkm.api.klm.manage.service.content.contentpost;

import cn.jkm.api.core.helper.ApiResponse;
import cn.jkm.api.klm.manage.service.AbstractManageService;
import cn.jkm.core.domain.mongo.content.ContentComplaint;
import cn.jkm.core.domain.mysql.user.User;
import cn.jkm.framework.core.validator.NotNull;
import cn.jkm.service.content.ContentCommentService;
import cn.jkm.service.content.ContentComplaintService;
import cn.jkm.service.core.domain.ListResult;
import cn.jkm.service.sys.UserService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhengzb on 2017/7/21.
 * 查询主题帖的投诉历史
 */
@Component("contentComplaintHistory1.0")
@NotNull(name = {"id", "type","limit","page"}, message = "缺少参数")
public class ContentComplaintHistory extends AbstractManageService {

    @Reference(version = "1.0")
    ContentComplaintService contentComplaintService;
    @Reference(version = "1.0")
    ContentCommentService contentCommentService;
    @Reference(version = "1.0")
    UserService userService;

    @Override
    public Map service(Map request) {
        ListResult<ContentComplaint> complaints = contentComplaintService.listComplaintLog(formatString(request.get("id")),
                formatString(request.get("type")),
                null,
                formatInteger(request.get("page")),
                formatInteger(request.get("limit")));
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
                                        put("create",v.getCreateAt());
                                        //加载投诉引用的评论
                                        if(v.getCommentId() != null && !("null").equals(v.getCommentId())){
                                            System.out.println(contentCommentService.find(v.getCommentId()));
                                            put("commentContent",contentCommentService.find(v.getCommentId()).getContent());
                                            put("complaintType","主题");
                                        }else{
                                            put("commentContent",null);
                                            put("complaintType","评论");
                                        }
                                        //                               put("userName",user.getName());
                                        //                               put("headUrl",user.getHeadUrl());
                                    }});
                        }});
                    }});
                }});
    }
}
