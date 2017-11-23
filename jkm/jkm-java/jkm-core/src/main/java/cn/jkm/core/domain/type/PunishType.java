package cn.jkm.core.domain.type;

/**
 * @author zhong
 * @version V1.0
 * @project jkm-root
 * @package cn.jkm.core.domain.type
 * @description //扣除的积分规则
 * @update 2017/7/26 10:00
 */
public enum PunishType {

    HIGHER_REJECT("主题贴被上级打回"),COMPLAINT_REJECT("主题贴被投诉打回"),TIME_OUT("主题贴超时未审核"),
    DEL("主题贴被删除"),HIDDEN("主题贴被隐藏"), COMMENT_DEL("评论被删除")
    ;
    private String name;

    PunishType( String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
