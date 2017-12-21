package cn.jkm.core.domain.mysql.product;

import cn.jkm.core.domain.mysql.MySqlBasic;
import cn.jkm.core.domain.type.GoodsType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @Author: Guo Fei
 * @Description: 商品信息
 * @Date: 17:36 2017/7/18
 */
@Entity
@Table(name = "tb_goods")
public class Goods extends MySqlBasic<Goods> {

    /**
     * 产品id
     */
    @Column(name = "product_id", columnDefinition = "varchar(64) COMMENT '产品id'")
    private String productId;
    /**
     * 已上架库存
     */
    @Column(name = "goods_num", columnDefinition = "bigint COMMENT '已上架库存'")
    private int goodsNum;
    /**
     * 已上架实际库存
     */
    @Column(name = "shelves_real_num", columnDefinition = "bigint COMMENT '实际库存'")
    private int shelvesRealNum;
    /**
     * 已上架特殊库存
     */
    @Column(name = "shelves_spec_num", columnDefinition = "bigint COMMENT '特殊库存数'")
    private int shelvesSpecNum;

    /**
     * 实际库存数少于?报警
     */
    @Column(name = "warn_goods_num", columnDefinition = "bigint COMMENT '实际库存数少于?报警'")
    private int warnGoodsNum;
    /**
     * 特殊库存少于?报警
     */
    @Column(name = "warn_spec_num", columnDefinition = "bigint COMMENT '特殊库存少于?报警'")
    private int warnSpecNum;
    /**
     * 商品类型
     */
    @Column(name = "goods_type", columnDefinition = "bigint COMMENT '商品类型'")
    private GoodsType goodsType;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(int goodsNum) {
        this.goodsNum = goodsNum;
    }

    public int getShelvesRealNum() {
        return shelvesRealNum;
    }

    public void setShelvesRealNum(int shelvesRealNum) {
        this.shelvesRealNum = shelvesRealNum;
    }

    public int getShelvesSpecNum() {
        return shelvesSpecNum;
    }

    public void setShelvesSpecNum(int shelvesSpecNum) {
        this.shelvesSpecNum = shelvesSpecNum;
    }

    public int getWarnGoodsNum() {
        return warnGoodsNum;
    }

    public void setWarnGoodsNum(int warnGoodsNum) {
        this.warnGoodsNum = warnGoodsNum;
    }

    public int getWarnSpecNum() {
        return warnSpecNum;
    }

    public void setWarnSpecNum(int warnSpecNum) {
        this.warnSpecNum = warnSpecNum;
    }

    public GoodsType getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(GoodsType goodsType) {
        this.goodsType = goodsType;
    }
}
