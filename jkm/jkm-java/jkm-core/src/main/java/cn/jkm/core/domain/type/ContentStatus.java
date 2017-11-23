package cn.jkm.core.domain.type;

/**
 * Created by zhong on 2017/7/17.
 */
public enum ContentStatus {

    PRE_COMMIT("待提交"),PRE_EXAMINE("待审核"),SHOW("显示"),TOP("置顶"),HIDDEN("隐藏"),DEL("删除");
    private String name;

    ContentStatus( String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
