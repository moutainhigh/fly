package cn.jkm.core.domain.type;

/**
 * @author zhong
 * @version V1.0
 * @project jkm-root
 * @package cn.jkm.core.domain.type
 * @description //配方状态表
 * @update 2017/7/25 10:09
 */
public enum FormulaStatus {

    SHOW("显示"),HIDDEN("隐藏"),DEL("删除");
    private String name;

    FormulaStatus( String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
