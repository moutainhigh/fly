package cn.jkm.core.domain.mongo.product;

import cn.jkm.core.domain.mongo.MongoBasic;
import cn.jkm.framework.core.annotation.Document;

import java.util.Set;

/**
 * @Author: Guo Fei
 * @Description: 产品属性模板
 * @Date: 10:01 2017/7/18
 */
@Document(name = "attr_template")
public class AttrTemplate extends MongoBasic<AttrTemplate> {
    /**
     * 模板名称
     */
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
