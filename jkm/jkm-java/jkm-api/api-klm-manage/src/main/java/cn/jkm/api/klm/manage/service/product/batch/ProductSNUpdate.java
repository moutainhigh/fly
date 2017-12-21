package cn.jkm.api.klm.manage.service.product.batch;

import cn.jkm.api.core.helper.ApiResponse;
import cn.jkm.api.klm.manage.service.AbstractManageService;
import cn.jkm.core.domain.type.BatchType;
import cn.jkm.framework.core.validator.NotNull;
import cn.jkm.service.eb.product.BatchService;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Author: Guo Fei
 * @Description: 新增批次信息
 * @Date: 10:50 2017/7/25
 */
@Component("productSNUpdate1.0")
@NotNull(name = {"productSNStr", "totalType", "operateType", "remark"}, message = "缺少参数")
public class ProductSNUpdate extends AbstractManageService {

    @Reference(version = "1.0")
    private BatchService batchService;

    @Override
    public Map service(Map request) {
        String productSNStr = formatString(request.get("productSNStr"));
        String totalType = formatString(request.get("totalType"));
        String operateType = formatString(request.get("operateType"));
        String remark = formatString(request.get("remark"));

        batchService.updateProductSN(productSNStr, totalType, operateType, remark);

        return ApiResponse.success();
    }
}
