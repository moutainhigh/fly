package cn.jkm.service.eb.product;

import cn.jkm.core.domain.mysql.product.Product;
import cn.jkm.core.domain.mysql.product.ProductAttr;

import java.util.List;
import java.util.Map;

/**
 * @Author: Guo Fei
 * @Description: 产品信息
 * @Date: 14:20 2017/7/27
 */
public interface ProductService {

    /**
     * 新增产品
     * @param product
     * @param productAttrs
     */
    void addProduct(Product product, List<ProductAttr> productAttrs);

    /**
     * 根据id删除产品
     * @param productId
     */
    void delProduct(String productId);

    /**
     * 修改产品
     * @param product
     */
    void updateProduct(Product product, List<ProductAttr> productAttrs);

    /**
     * 产品分页查询
     * @param conditions
     * @param limit
     * @param page
     * @return
     */
    List<Product> pageProduct(Map<String, String> conditions, int limit, int page);

    /**
     * 根据id查询产品
     * @param productId
     * @return
     */
    Map findProduct(String productId);
}
