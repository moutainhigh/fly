package cn.jkm.core.domain.type;

/**
 * @author zhong
 * @version V1.0
 * @project jkm-root
 * @package cn.jkm.core.domain.type
 * @description //积分奖励的类型
 * @update 2017/7/26 9:47
 */
public enum  RewardType {
    SUBJECT_ACCEPT("主题贴被审核通过"),ACCEPT_SUBORDINATW("审核并通过下级主题贴"),COMMENT("发表评论")
    ;
    private String name;

    RewardType( String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
