package cn.jkm.core.domain.mongo.product;

import cn.jkm.core.domain.mongo.MongoBasic;
import cn.jkm.framework.core.annotation.Document;

import javax.persistence.Column;
import java.util.Set;

/**
 * @Author: Guo Fei
 * @Description: 产品分类
 * @Date: 10:01 2017/7/18
 */
@Document(name = "category")
public class Category extends MongoBasic<Category> {
    /**
     * 分类图标
     */
    private String icon;
    /**
     * 分类名称
     */
    private String name;
    /**
     * 子分类
     */
    private Set<Category> subCategory;
    /**
     * 排序号
     */
    private Long sortOrder;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Category> getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(Set<Category> subCategory) {
        this.subCategory = subCategory;
    }

    public Long getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Long sortOrder) {
        this.sortOrder = sortOrder;
    }
}
