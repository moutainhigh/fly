package cn.jkm.core.domain.type;

/**
 * Created by zhong on 2017/7/18.
 */
public enum ContentType {
    POST("帖子"),NEWS("资讯"),ACTIVITY("活动"),EXPERT_ARTICLE("专家文章"),FORMULA("配方");
    private String name;
    ContentType(String name) {
        this.name = name;
    }
}
