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
 * @Description: 修改产品信息
 * @Date: 17:08 2017/7/27
 */
@Component("productUpdate1.0")
@NotNull(name = {"product", "productAttrs"}, message = "缺少参数")
public class ProductUpdate extends AbstractManageService {

    @Reference(version = "1.0")
    private ProductService productService;

    @Override
    public Map service(Map request) {
        String productStr = formatString(request.get("product"));
        String productAttrStr = formatString(request.get("productAttrs"));
        Map productMap = JSON.parseObject(productStr, Map.class);

        List<ProductAttr> productAttrList = JSON.parseObject(productAttrStr, List.class);
        long costPrice = CalculateUtils.yuanToFen(formatString(productMap.get("costPrice")));
        long price = CalculateUtils.yuanToFen(formatString(productMap.get("price")));
        productMap.put("costPrice", costPrice);
        productMap.put("price", price);
        Product product = JSON.parseObject(JSON.toJSON(productMap).toString(), Product.class);

        productService.updateProduct(product, productAttrList);

        return ApiResponse.success();
    }
}
