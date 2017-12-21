package cn.jkm.core.domain.mongo.product;

import cn.jkm.core.domain.mongo.MongoBasic;
import cn.jkm.framework.core.annotation.Document;

/**
 * @Author: Guo Fei
 * @Description: 货架
 * @Date: 10:02 2017/7/18
 *
 */
@Document(name = "shelves")
public class Shelves extends MongoBasic<Shelves> {
    /**
     * 货架名称
     */
    private String name;
    /**
     * 库存
     */
    private long stockNum;
    /**
     * 实际库存
     */
    private long realStockNum;
    /**
     * 特殊库存
     */
    private long specStockNum;
    /**
     * 排序号
     */
    private long sortOrder;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getStockNum() {
        return stockNum;
    }

    public void setStockNum(long stockNum) {
        this.stockNum = stockNum;
    }

    public long getRealStockNum() {
        return realStockNum;
    }

    public void setRealStockNum(long realStockNum) {
        this.realStockNum = realStockNum;
    }

    public long getSpecStockNum() {
        return specStockNum;
    }

    public void setSpecStockNum(long specStockNum) {
        this.specStockNum = specStockNum;
    }

    public long getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(long sortOrder) {
        this.sortOrder = sortOrder;
    }
}
