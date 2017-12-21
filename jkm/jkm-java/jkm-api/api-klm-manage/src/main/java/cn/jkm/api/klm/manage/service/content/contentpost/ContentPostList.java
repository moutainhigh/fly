package cn.jkm.api.klm.manage.service.content.contentpost;

import cn.jkm.api.core.helper.ApiResponse;
import cn.jkm.api.klm.manage.service.AbstractManageService;
import cn.jkm.core.domain.mongo.content.ContentPost;
import cn.jkm.core.domain.type.ContentStatus;
import cn.jkm.service.content.ContentPostService;
import cn.jkm.service.core.domain.ListResult;
import cn.jkm.service.sys.UserService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhengzb on 2017/7/20.
 * 搜索帖子、新闻
 */
@Component("contentPostList1.0")
public class ContentPostList extends AbstractManageService {
    @Reference(version = "1.0")
    ContentPostService contentPostService;
    @Reference(version = "1.0")
    UserService userService;

    @Override
    public Map service(Map request) {
        ListResult<ContentPost> contentPosts = contentPostService.listByCondition(
                formatString(request.get("postType")), formatString(request.get("channelId")),
                formatString(request.get("contentStatus")), formatString(request.get("keys")),
                formatLong(request.get("startTime")), formatLong(request.get("endTime")),
                formatString(request.get("orderBy")), formatInteger(request.get("page")), formatInteger(request.get("limit"))
        );

        return ApiResponse.success().body(new HashMap() {
            {
                put("count", contentPosts.getCount());
                put("list", new ArrayList() {{
                    contentPosts.getList().forEach(v -> {
                        add(new HashMap() {{
                            put("id", v.getId());
                            put("title", v.getTitle());
                            //todo 查找频道名
                            put("channelName", v.getChannelName());
                            put("commentNum", v.getCommentNum());
                            put("browseNum", v.getBrowseNum());
                            put("pointNum", v.getPointNum());
                            put("collectionNum", v.getCollectionNum());
//                     put("publicUserName",userService.find(v.getPublishUserId()).getName());
                            put("createAt", v.getCreateAt());
                            put("contentStatus", v.getContentStatus());
                        }});
                    });
                }});

            }
        });
    }
}
