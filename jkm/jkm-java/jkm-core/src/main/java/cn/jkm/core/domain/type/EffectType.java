package cn.jkm.core.domain.type;

/**
 * Created by zhong on 2017/7/17.
 */
public enum EffectType {
    //0：无；1：左滑；2：右滑；3：渐变
    NONE("无"),LEFT_SLIP("左滑"),RIGHT_SLIP("右滑"),GRADIENT("渐变");
    private String name;

    EffectType( String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
