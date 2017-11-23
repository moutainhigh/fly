package cn.jkm.core.domain.mongo.content;

import cn.jkm.core.domain.type.FormulaStatus;
import cn.jkm.framework.core.annotation.Document;

/**
 * Created by zhong on 2017/7/18.
 * 配方表
 */
@Document(name = "content_formula")
public class ContentFormula extends ContentBasic<ContentFormula>{

    private String formulaInfo;				//配方详情
    private String formulaSymptom;			//配方适应证
    private Integer formulaBuyNum;			//配方购买量
    private String formulaProductionInfo;	//配方包含的商品信息
    private String formulaExpertId;			//配方专家id
    private FormulaStatus formulaStatus;          //配方状态
    private String delUserId;                 //删除用户id

    public String getFormulaInfo() {
        return formulaInfo;
    }

    public void setFormulaInfo(String formulaInfo) {
        this.formulaInfo = formulaInfo;
    }

    public String getFormulaSymptom() {
        return formulaSymptom;
    }

    public void setFormulaSymptom(String formulaSymptom) {
        this.formulaSymptom = formulaSymptom;
    }

    public Integer getFormulaBuyNum() {
        return formulaBuyNum;
    }

    public void setFormulaBuyNum(Integer formulaBuyNum) {
        this.formulaBuyNum = formulaBuyNum;
    }

    public String getFormulaProductionInfo() {
        return formulaProductionInfo;
    }

    public void setFormulaProductionInfo(String formulaProductionInfo) {
        this.formulaProductionInfo = formulaProductionInfo;
    }

    public FormulaStatus getFormulaStatus() {
        return formulaStatus;
    }

    public void setFormulaStatus(FormulaStatus formulaStatus) {
        this.formulaStatus = formulaStatus;
    }

    public String getFormulaExpertId() {
        return formulaExpertId;
    }

    public void setFormulaExpertId(String formulaExpertId) {
        this.formulaExpertId = formulaExpertId;
    }


    public String getDelUserId() {
        return delUserId;
    }

    public void setDelUserId(String delUserId) {
        this.delUserId = delUserId;
    }

    @Override
    public String toString() {
        return "ContentFormula{" +
                "formulaInfo='" + formulaInfo + '\'' +
                ", formulaSymptom='" + formulaSymptom + '\'' +
                ", formulaBuyNum=" + formulaBuyNum +
                ", formulaProductionInfo='" + formulaProductionInfo + '\'' +
                ", formulaExpertId='" + formulaExpertId + '\'' +
                ", formulaStatus=" + formulaStatus +
                ", delUserId='" + delUserId + '\'' +
                '}';
    }
}
