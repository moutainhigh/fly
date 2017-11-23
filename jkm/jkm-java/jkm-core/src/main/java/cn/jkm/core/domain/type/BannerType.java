package cn.jkm.core.domain.type;

/**
 * Created by zhong on 2017/7/17.
 * banner类别吗，分为闪屏和banner
 */
public enum BannerType {
    SPLASH("闪屏"),BANNER("banner");
    private String name;

    BannerType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
