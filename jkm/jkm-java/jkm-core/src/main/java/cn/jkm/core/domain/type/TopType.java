package cn.jkm.core.domain.type;

/**
 * Created by zhong on 2017/7/18.
 */
public enum TopType {

    UN_TOP("未点赞"),TOP("点赞");
    private String name;

    TopType(String name) {
        this.name = name;
    }
}
