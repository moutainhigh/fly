package cn.jkm.core.domain.type;

/**
 * @author zhong
 * @version V1.0
 * @project jkm-root
 * @package cn.jkm.core.domain.type
 * @description //活动定制表选项类
 * @update 2017/7/19 15:30
 */
public enum ActivityItemType {

    RADIO("单选"),CHECKBOX("多选"),DROPDWN("下拉菜单"),TEXT("单行文本"),AREA("多行文本");

    private String name;

    ActivityItemType(String name) {
        this.name = name;
    }
}
