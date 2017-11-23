package cn.jkm.core.domain.mongo.content;

import cn.jkm.core.domain.mongo.MongoBasic;
import cn.jkm.framework.core.annotation.Document;

/**
 * Created by zhong on 2017/7/18.
 * 配方产品对应表
 */
@Document(name = "formula_product")
public class FormulaProduct extends MongoBasic<FormulaProduct> {

    private String formulaId;				 //配方id
    private String productId;			     //产品id
    private int productNum;               //产品id

    public String getFormulaId() {
        return formulaId;
    }

    public void setFormulaId(String formulaId) {
        this.formulaId = formulaId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getProductNum() {
        return productNum;
    }

    public void setProductNum(int productNum) {
        this.productNum = productNum;
    }

    @Override
    public String toString() {
        return "FormulaProduct{" +
                "formulaId='" + formulaId + '\'' +
                ", productId='" + productId + '\'' +
                ", productNum=" + productNum +
                '}';
    }
}
