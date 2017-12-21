package cn.jkm.api.klm.manage.service.content.contentpost;

import cn.jkm.api.core.helper.ApiResponse;
import cn.jkm.api.klm.manage.service.AbstractManageService;
import cn.jkm.core.domain.mongo.content.ContentPost;
import cn.jkm.core.domain.mysql.user.User;
import cn.jkm.core.domain.type.HandleType;
import cn.jkm.framework.core.validator.NotNull;
import cn.jkm.service.content.ContentPostService;
import cn.jkm.service.sys.SysUserService;
import cn.jkm.service.sys.UserService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhengzb on 2017/7/20.
 * 帖子详情
 */
@Component("contentPostDetail1.0")
@NotNull(name = {"id"}, message = "缺少参数")
public class ContentPostDetail extends AbstractManageService {

    @Reference(version = "1.0")
    ContentPostService contentPostService;
    @Reference(version = "1.0")
    UserService userService;
    @Reference(version = "1.0")
    SysUserService sysUserService;
    @Override
    public Map service(Map request) {
        ContentPost contentPost = contentPostService.find(formatString(request.get("id")));
        return ApiResponse.success().body(new HashMap(){{
            User user = userService.find(contentPost.getPublishUserId());
            if(contentPost.getHandleType() != null && contentPost.getHandleType().equals(HandleType.APPUSER)){
                put("handleUserName",userService.find(contentPost.getHandleUserId()).getName()) ;
            }
            if(contentPost.getHandleType() != null && contentPost.getHandleType().equals(HandleType.SYSUSER)){
                put("handleUserName",sysUserService.find(contentPost.getHandleUserId()).getAccountName());
            }
            put("id",contentPost.getId());
            put("title",contentPost.getTitle());
            //todo 查找频道名
            put("channelName",contentPost.getChannelName());
            put("detail",contentPost.getDetail());
            put("commentNum",contentPost.getCommentNum());
            put("browseNum",contentPost.getBrowseNum());
            put("pointNum",contentPost.getPointNum());
            put("collectionNum",contentPost.getCollectionNum());
            put("publicUserName",user.getName());
            put("headUrl",user.getHeadUrl());
            put("userStatus",user.getUserStatus());
            put("handleTime",contentPost.getHandleTime());
            put("createAt",contentPost.getCreateAt());
            put("contentStatus",contentPost.getContentStatus());
        }});
    }
}
