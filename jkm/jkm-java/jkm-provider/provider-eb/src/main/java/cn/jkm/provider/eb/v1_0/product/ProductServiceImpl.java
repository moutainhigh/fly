package cn.jkm.provider.eb.v1_0.product;

import cn.jkm.core.domain.mysql.product.Product;
import cn.jkm.core.domain.mysql.product.ProductAttr;
import cn.jkm.core.domain.type.Status;
import cn.jkm.framework.mysql.Jdbc;
import cn.jkm.framework.mysql.operation.Query;
import cn.jkm.framework.mysql.operation.Update;
import cn.jkm.service.core.exception.ProviderException;
import cn.jkm.service.eb.product.ProductService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @Author: Guo Fei
 * @Description: 产品
 * @Date: 14:24 2017/7/27
 */
public class ProductServiceImpl implements ProductService {

    @Transactional
    @Override
    public void addProduct(Product product,  List<ProductAttr> productAttrs) {

        if(StringUtils.isEmpty(product.getGoodsNo())) {
            throw new ProviderException("商品编号不能为空");
        }

        if(StringUtils.isEmpty(product.getGoodsName())) {
            throw new ProviderException("商品名称不能为空");
        }

        if(StringUtils.isEmpty(product.getPrice())) {
            throw new ProviderException("出售价格不能为空");
        }

        product.setId(UUID.randomUUID().toString());
        Jdbc.build().insert(product);

        productAttrs.forEach(productAttr -> {
            productAttr.setId(UUID.randomUUID().toString());
            productAttr.setProductId(product.getId());
        });

        Jdbc.build().insertBatch(productAttrs);
    }

    @Transactional
    @Override
    public void delProduct(String productId) {
        Jdbc.build().update(Product.class).where("id", productId).set("status", Status.DELETE);
    }

    @Override
    public void updateProduct(Product product, List<ProductAttr> productAttrs) {
        if(StringUtils.isEmpty(product.getGoodsNo())) {
            throw new ProviderException("商品编号不能为空");
        }

        if(StringUtils.isEmpty(product.getGoodsName())) {
            throw new ProviderException("商品名称不能为空");
        }

        if(StringUtils.isEmpty(product.getPrice())) {
            throw new ProviderException("出售价格不能为空");
        }

        Update update = Jdbc.build().update(Product.class).where("id", product.getId());
        update.set("goods_no", product.getGoodsNo());
        update.set("goods_name", product.getGoodsName());
        update.set("category_id", product.getCategoryId());
        update.set("branch_id", product.getBranchId());
        update.set("marketing_rule_id", product.getMarketingRuleId());
        update.set("product_thumb", product.getProductThumb());
        update.set("product_images", product.getProductImages());
        update.set("cost_price", product.getCostPrice());
        update.set("price", product.getPrice());
        update.set("allow_return", product.getAllowReturn());
        update.set("dosage", product.getDosage());
        update.set("component", product.getComponent());
        update.set("effect", product.getEffect());
        update.set("product_brief", product.getProductBrief());
        update.set("detail", product.getDetail());
        update.set("shelf_life", product.getShelfLife());

        //删除属性
        Jdbc.build().delete(ProductAttr.class).where("product_id", product.getId()).delete();

        //新增属性
        productAttrs.forEach(productAttr -> {
            productAttr.setId(UUID.randomUUID().toString());
            productAttr.setProductId(product.getId());
        });

        Jdbc.build().insertBatch(productAttrs);
    }

    @Override
    public List<Product> pageProduct(Map<String, String> conditions, int limit, int page) {
        String categoryId = conditions.get("categoryId");
        String branchId = conditions.get("branchId");
        String templateId = conditions.get("templateId");
        String keyword = conditions.get("keyword");

        Query query = Jdbc.build().query(Product.class);
        if(!StringUtils.isEmpty(categoryId)) {
            query.where("instr(category_id, ?) > 0", categoryId);
        }

        if(!StringUtils.isEmpty(branchId)) {
            query.where("branch_id", branchId);
        }

        if(!StringUtils.isEmpty(templateId)) {
            query.where("template_id", templateId);
        }

        if(!StringUtils.isEmpty(keyword)) {
            query.where("(goods_no like ? or goods_name like ?)", keyword, keyword);
        }

        if(limit > 0) {
            return query.page(limit, page);
        }

        return query.all();
    }

    @Override
    public Map findProduct(String productId) {

        Product product = Jdbc.build().query(Product.class).find(productId);
        List<ProductAttr> attrList = Jdbc.build().query(Product.class).where("product_id", productId).all();
        return new HashMap(){{
            put("product", product);
            put("productAttrs", attrList);
        }};
    }
}
