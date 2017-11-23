package cn.jkm.service.eb.module;

import cn.jkm.core.domain.mongo.MongoBasic;
import cn.jkm.core.domain.mongo.product.Attr;

import java.util.Set;

/**
 * @Author: Guo Fei
 * @Description: 属性模板
 * @Date: 14:17 2017/7/25
 */
public class AttrTemplateModule extends MongoBasic<AttrTemplateModule> {
    /**
     * 模板名称
     */
    private String name;
    /**
     * 属性集合
     */
    private Set<Attr> attrs;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Attr> getAttrs() {
        return attrs;
    }

    public void setAttrs(Set<Attr> attrs) {
        this.attrs = attrs;
    }
}
