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
 * @Description: 产品分页
 * @Date: 8:57 2017/7/25
 */
@Component("productPage1.0")
@NotNull(name = {"json", "limit"}, message = "缺少参数")
public class ProductPage extends AbstractManageService {

    @Reference(version = "1.0")
    private ProductService productService;

    @Override
    public Map service(Map request) {
        String json = formatString(request.get("json"));
        int limit = formatInteger(request.get("limit"));
        int page = formatInteger(request.get("page"));
        Map map = JSON.parseObject(json, Map.class);

        productService.pageProduct(map, limit, page);
        return ApiResponse.success();
    }
}
