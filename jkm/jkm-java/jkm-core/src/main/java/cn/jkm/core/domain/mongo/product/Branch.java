package cn.jkm.core.domain.mongo.product;

import cn.jkm.core.domain.mongo.MongoBasic;
import cn.jkm.framework.core.annotation.Document;

import javax.persistence.Column;

/**
 * @Author: Guo Fei
 * @Description: 品牌
 * @Date: 10:01 2017/7/18
 */
@Document(name = "branch")
public class Branch extends MongoBasic<Branch> {
    /**
     * 品牌Logo
     */
    private String logo;
    /**
     * 品牌名称
     */
    private String name;
    /**
     * 排序号
     */
    private Long sortOrder;

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Long sortOrder) {
        this.sortOrder = sortOrder;
    }
}
