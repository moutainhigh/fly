package cn.jkm.provider.content.v1_0.content;

import cn.jkm.core.domain.mongo.content.ContentFormula;
import cn.jkm.core.domain.type.ContentType;
import cn.jkm.core.domain.type.FormulaStatus;
import cn.jkm.core.domain.type.Status;
import cn.jkm.framework.core.spring.SpringApplicationContext;
import cn.jkm.framework.mongo.mongo.Mongo;
import cn.jkm.provider.content.action.PointAction;
import cn.jkm.provider.content.action.StoreAction;
import cn.jkm.service.content.FormulaService;
import cn.jkm.service.core.exception.ProviderException;
import com.alibaba.druid.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zhong
 * @version V1.0
 * @project jkm-root
 * @package cn.jkm.provider.content.v1_0.content
 * @description //配方实现表
 * @update 2017/7/21 16:23
 */
@com.alibaba.dubbo.config.annotation.Service(version = "1.0")
public class FormulaServiceImpl implements FormulaService {

    @Override
    public void formulaAdd(String title, String info, String symptom, String expertId, String productInfos) {

        //1、添加配方信息
        ContentFormula contentFormula = new ContentFormula();
        contentFormula.setStatus(Status.ACTIVE);
        contentFormula.setFormulaBuyNum(0);
        contentFormula.setFormulaExpertId(expertId);
        contentFormula.setFormulaInfo(info);
        contentFormula.setFormulaProductionInfo(productInfos);
        contentFormula.setFormulaSymptom(symptom);
        contentFormula.setBrowseNum(0);
        contentFormula.setChannelId("");
        contentFormula.setChannelName("");
        contentFormula.setCollectionNum(0);
        contentFormula.setCommentNum(0);
        contentFormula.setComplaintCount(0);
        contentFormula.setComplaintHandleCount(0);
        contentFormula.setPointNum(0);
        contentFormula.setTitle(title);
        contentFormula.setFormulaStatus(FormulaStatus.SHOW);
        Mongo.buildMongo().insert(contentFormula);

        //2、添加产品配方对应

    }

    @Override
    public List<ContentFormula> formulaListAll(int limit, int page) {
        List<ContentFormula> formulas = Mongo.buildMongo()
                .or(new String[]{"formulaStatus","formulaStatus"},FormulaStatus.SHOW.toString(),FormulaStatus.HIDDEN.toString())
                .limit(limit, page)
                .find(ContentFormula.class);
        return formulas;
    }

    @Override
    public long formulaCountAll() {
        return Mongo.buildMongo()
                .or(new String[]{"formulaStatus","formulaStatus"},FormulaStatus.SHOW.toString(),FormulaStatus.HIDDEN.toString())
                .count("content_formula");
    }

    @Override
    public long formulaCountShow() {
        return Mongo.buildMongo().eq("formulaStatus", FormulaStatus.SHOW.toString()).count("content_formula");
    }

    @Override
    public List<ContentFormula> formulaListShow(int limit, int page) {
        List<ContentFormula> formulas = Mongo.buildMongo().eq("formulaStatus", FormulaStatus.SHOW.toString()).limit(limit, page).find(ContentFormula.class);
        return formulas;
    }

    @Override
    public List<ContentFormula> formulaListByExpertId(String expertId, int limit, int page) {
        Mongo mongo = Mongo.buildMongo();
        if (!StringUtils.isEmpty(expertId)) {
            mongo = mongo.eq("formulaExpertId", expertId);
            mongo = mongo.eq("formulaStatus", FormulaStatus.SHOW.toString());
        }
        List<ContentFormula> formulas = mongo.limit(limit, page).find(ContentFormula.class);
        return formulas;
    }

    @Override
    public long formulaCountByExpertId(String expertId) {
        Mongo mongo = Mongo.buildMongo();
        if (!StringUtils.isEmpty(expertId)) {
            mongo = mongo.eq("formulaExpertId", expertId);
            mongo = mongo.eq("formulaStatus", FormulaStatus.SHOW.toString());
        }
        long formulaCount = mongo.count("content_formula");
        return formulaCount;
    }

    @Override
    public void formulaHiddenById(String formulaId) {
        if (!StringUtils.isEmpty(formulaId)) {
            Mongo.buildMongo().eq("_id", formulaId).updateFirst(formula -> formula.set("formulaStatus", FormulaStatus.HIDDEN.toString()), ContentFormula.class);
        } else {
            throw new ProviderException("缺少参数formulaId");
        }
    }

    @Override
    public void formulaDelById(String formulaId,String userId) {
        if (!StringUtils.isEmpty(formulaId)) {
            Mongo.buildMongo()
                    .eq("_id", formulaId)
                    .updateFirst(formula ->formula
                                    .set("formulaStatus", FormulaStatus.DEL.toString())
                                    .set("delUserId",userId)
                                    .set("update_at",System.currentTimeMillis()/1000)
                            , ContentFormula.class);
        } else {
            throw new ProviderException("缺少参数formulaId");
        }
    }

    @Override
    public ContentFormula formulaGetById(String formulaId) {

        if (!StringUtils.isEmpty(formulaId)) {
            return Mongo.buildMongo().eq("_id", formulaId).findOne(ContentFormula.class);
        }

        return null;
    }

    @Override
    public void formulaBrowse(String formulaId) {
        Mongo.buildMongo().eq("_id", formulaId).upsert(update -> {
            update.inc("browseNum", 1);
        }, "content_formula");
    }

    @Override
    public void formulaBuy(String formulaId) {
        Mongo.buildMongo().eq("_id", formulaId).upsert(update -> {
            update.inc("formulaBuyNum", 1);
        }, "content_formula");
    }

    @Override
    public void formulaPoint(String formulaId, String userId) {

        if(PointAction.point(formulaId, userId, ContentType.FORMULA)){
            Mongo.buildMongo().eq("_id", formulaId).upsert(update -> {
                update.inc("pointNum", 1);
            }, "content_formula");
        }
    }

    @Override
    public void formulaStore(String formulaId, String userId) {

        if(StoreAction.store(formulaId, userId, ContentType.FORMULA)){
            Mongo.buildMongo().eq("_id", formulaId).upsert(update -> {
                update.inc("collectionNum", 1);
            }, "content_formula");
        }
    }

    @Override
    public void formulaUnStore(String formulaId, String userId) {
        if(StoreAction.unStore(formulaId, userId, ContentType.FORMULA)){
            Mongo.buildMongo().eq("_id", formulaId).upsert(update -> {
                update.inc("collectionNum", -1);
            }, "content_formula");
        }
    }

    @Override
    public void formulaCommentAddCount(String formulaId) {

    }

    @Override
    public List<ContentFormula> formulaDelList(int limit, int page) {

        List<ContentFormula> formulas = Mongo.buildMongo()
                .eq("formulaStatus", FormulaStatus.DEL.toString())
                .limit(limit, page)
                .find(ContentFormula.class);
        return formulas;
    }

    @Override
    public long formulaDelCount() {
        return Mongo.buildMongo().eq("formulaStatus", FormulaStatus.DEL.toString()).count("content_formula");
    }

    @Override
    public void formulaPhysicalDel(String formulaId) {
        if (!StringUtils.isEmpty(formulaId)) {
            Mongo.buildMongo()
                    .eq("_id", formulaId)
                    .remove("content_formula");
        } else {
            throw new ProviderException("缺少参数formulaId");
        }
    }

    @Override
    public void formulaPhysicalDel(String[] formulaIds) {
        if (null!=formulaIds&&formulaIds.length>0) {
            Mongo.buildMongo()
                    .in("_id", formulaIds)
                    .remove("content_formula");
        } else {
            throw new ProviderException("缺少参数formulaId");
        }
    }

    @Override
    public void formulaRecoveryDel(String formulaId) {
        if (!StringUtils.isEmpty(formulaId)) {
            Mongo.buildMongo()
                    .eq("_id", formulaId)
                    .updateFirst(formula -> formula.set("formulaStatus", FormulaStatus.SHOW.toString()), ContentFormula.class);
        } else {
            throw new ProviderException("缺少参数formulaId");
        }
    }
}
