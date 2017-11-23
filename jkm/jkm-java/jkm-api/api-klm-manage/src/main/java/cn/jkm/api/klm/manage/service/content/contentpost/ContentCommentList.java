package cn.jkm.api.klm.manage.service.content.contentpost;

import cn.jkm.api.core.helper.ApiResponse;
import cn.jkm.api.klm.manage.service.AbstractManageService;
import cn.jkm.core.domain.mongo.content.ContentComment;
import cn.jkm.core.domain.mysql.user.User;
import cn.jkm.framework.core.validator.NotNull;
import cn.jkm.service.content.ContentCommentService;
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
 * 分页查询帖子或新闻的评论
 */
@Component("contentCommentList1.0")
@NotNull(name = {"id", "type","limit","page"}, message = "缺少参数")
public class ContentCommentList extends AbstractManageService {

    @Reference(version = "1.0")
    ContentCommentService contentCommentService;

    @Reference(version = "1.0")
    UserService userService;

    @Override
    public Map service(Map request) {
        ListResult<ContentComment> comments = contentCommentService.listCommentsPage(formatString(request.get("id")),
                                                                                formatString(request.get("type")),
                                                                                null,1,
                                                                                    formatInteger(request.get("page")),
                                                                                formatInteger(request.get("limit")));
        return ApiResponse.success().body(new HashMap() {
            {
                put("count", comments.getCount());
                put("list", new ArrayList() {{
                    comments.getList().forEach(v -> {
                        add(new HashMap() {{
//                    User user = userService.find(v.getUserId());
                            put("id", v.getId());
                            put("content", v.getContent());
                            put("createAt", v.getCreateAt());
//                    put("userName",user.getName());
//                    put("headUrl",user.getHeadUrl());
//                    put("userStatus",user.getUserStatus());
                        }});
                    });
                }});

            }
        });
    }
}
