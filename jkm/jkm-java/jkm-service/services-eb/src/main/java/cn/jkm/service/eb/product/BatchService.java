package cn.jkm.service.eb.product;

import cn.jkm.core.domain.mongo.product.Batch;
import cn.jkm.core.domain.mongo.product.ProductSN;
import cn.jkm.core.domain.type.BatchType;
import cn.jkm.service.eb.module.BatchModule;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Author: Guo Fei
 * @Description: 库存批次
 * @Date: 18:40 2017/7/17
 */
public interface BatchService {

    /**
     * 新增或修改
     * @param moduleList
     */
    void addBatch(List<BatchModule> moduleList);

    /**
     * 根据id查询
     * @param batchId
     * @return
     */
    Batch findBatch(String batchId);

    /**
     * 修改单品信息
     * @param productSNStr 单品id字符串，使用分隔符“,”
     * @param operateType
     * @param remark
     */
    void updateProductSN(String productSNStr, String totalType, String operateType, String remark);

    /**
     * 单品信息分页查询
     * @param conditions
     * @param limit
     * @param page
     * @return
     */
    List<ProductSN> productSNPage(Map conditions, int limit, int page);

    /**
     * 统计货架产品信息
     * @param queryType real：实际库存，spec：特殊库存
     * @param productId
     * @param limit
     * @param page
     * @return
     */
    List<Map<String, String>> pageProductSNStatis(String queryType, String productId, int limit, int page);
}