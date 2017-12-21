package cn.jkm.api.klm.manage.service.product.product;

import cn.jkm.api.core.helper.ApiResponse;
import cn.jkm.api.klm.manage.service.AbstractManageService;
import cn.jkm.core.domain.mysql.product.Product;
import cn.jkm.core.domain.mysql.product.ProductAttr;
import cn.jkm.core.domain.utils.CalculateUtils;
import cn.jkm.framework.core.validator.NotNull;
import cn.jkm.service.eb.product.ProductService;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @Author: Guo Fei
 * @Description: 新增产品
 * @Date: 8:57 2017/7/25
 */
@Component("productSelect1.0")
@NotNull(name = {"productId"}, message = "缺少参数")
public class ProductSelect extends AbstractManageService {

    @Reference(version = "1.0")
    private ProductService productService;

    @Override
    public Map service(Map request) {
        String productId = formatString(request.get("productId"));

        return ApiResponse.success().body(productService.findProduct(productId));
    }
}
