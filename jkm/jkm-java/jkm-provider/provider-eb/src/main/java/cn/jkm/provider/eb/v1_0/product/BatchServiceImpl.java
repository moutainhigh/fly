package cn.jkm.provider.eb.v1_0.product;

import cn.jkm.core.domain.mongo.product.Batch;
import cn.jkm.core.domain.mongo.product.ProductSN;
import cn.jkm.core.domain.type.BatchType;
import cn.jkm.core.domain.type.ProductStatus;
import cn.jkm.core.domain.type.Status;
import cn.jkm.framework.mongo.mongo.Mongo;
import cn.jkm.service.eb.module.BatchModule;
import cn.jkm.service.eb.product.BatchService;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * @Author: Guo Fei
 * @Description: 批次
 * @Date: 10:32 2017/7/21
 */
public class BatchServiceImpl implements BatchService {

    @Override
    public void addBatch(List<BatchModule> moduleList) {
        moduleList.forEach(module -> {
            Batch batch = new Batch();
            String batchNo = UUID.randomUUID().toString();
            batch.setBatchType(BatchType.BATCH_STORAGE);
            batch.setBatchNo(batchNo);
            batch.setCostPrice(module.getCostPrice());
            batch.setProductBatchNo(module.getProductBatchNo());
            batch.setProductId(module.getProductId());
            batch.setProductNo(module.getProductNo());
            batch.setProductNum(module.getProductNum());
            batch.setOperateUserName(module.getOperateUserName());

            Set<ProductSN> productSNSet = module.getProductSNSet();
            productSNSet.forEach(productSN -> {
                productSN.setProductId(module.getProductId());
                productSN.setShelvesId(module.getShelvesId());
                productSN.setBatchNo(batchNo);
                productSN.setCostPrice(module.getCostPrice());
                productSN.setOperateType(BatchType.BATCH_ADD_STORAGE);
                productSN.setProductNo(module.getProductNo());
                productSN.setOperateUserName(module.getOperateUserName());

                Mongo.buildMongo().insert(module);
            });

            Mongo.buildMongo().insert(batch);

            //TODO 更新商品库存信息
        });
    }

    @Override
    public Batch findBatch(String batchId) {
        return Mongo.buildMongo().eq("_id", batchId).findOne(Batch.class);
    }

    @Override
    public void updateProductSN(String productSNStr, String totalType, String operateType, String remark) {

        if(productSNStr.contains(",")) {
            String[] snIds = productSNStr.split(",");
                Mongo.buildMongo().in("_id", snIds).updateMulti(update -> {
                    update.set("totalType", totalType);
                    update.set("operateType", operateType);
                    update.set("remark", remark);
                }, "product_sn");
        } else {
            Mongo.buildMongo().eq("_id", productSNStr).updateFirst(update -> {
                update.set("totalType", totalType);
                update.set("operateType", operateType);
                update.set("remark", remark);
            }, ProductSN.class);
        }
    }

    @Override
    public List<ProductSN> productSNPage(Map conditions, int limit, int page) {
        Mongo mongo = Mongo.buildMongo().eq("status", Status.ACTIVE);
        if(!StringUtils.isEmpty(conditions.get("startTime"))) {
            mongo.lte("createAt", conditions.get("startTime"));
        }

        if(!StringUtils.isEmpty(conditions.get("endTime"))) {
            mongo.gte("createAt", conditions.get("endTime"));
        }

        if(!StringUtils.isEmpty(conditions.get("shelvesId"))) {
            mongo.eq("shelvesId", conditions.get("shelvesId"));
        }

        if(!StringUtils.isEmpty(conditions.get("batchType"))) {
            mongo.eq("batchType", conditions.get("batchType"));
        }

        if(!StringUtils.isEmpty(conditions.get("operateType"))) {
            mongo.eq("operateType", conditions.get("operateType"));
        }

        if(!StringUtils.isEmpty(conditions.get("keyword"))) {
            String keyword = conditions.get("keyword").toString();
            mongo.or(new String[] {"batchNo", "createAt", "productNo", "operateUserName"},
                    new String[] {keyword, keyword, keyword, keyword},
                    Mongo.OP.F);
        }

        return mongo.limit(limit, page).find(ProductSN.class);
    }

    @Override
    public List<Map<String, String>> pageProductSNStatis(String queryType, String productId, int limit, int page) {


        return null;
    }
}
