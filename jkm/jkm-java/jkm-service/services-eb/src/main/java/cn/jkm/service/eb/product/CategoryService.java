package cn.jkm.service.eb.product;

import cn.jkm.core.domain.mongo.product.Category;

import java.util.List;

/**
 * @Author: Guo Fei
 * @Description: 产品分类
 * @Date: 10:03 2017/7/20
 */
public interface CategoryService {

    /**
     * 新增或修改
     * @param categoryList
     */
    void saveCategory(List<Category> categoryList);

    /**
     * 条件分页查询
     * @param keyword
     * @param limit
     * @param page
     * @return
     */
    List<Category> pageCategory(String keyword, int limit, int page);

    /**
     * 分类合并
     * @param mergeType supMerge:父类合并，subMerge：子类合并
     * @param fromId
     * @param toId
     */
    void mergeCategory(String mergeType, String fromId, String toId);
}
