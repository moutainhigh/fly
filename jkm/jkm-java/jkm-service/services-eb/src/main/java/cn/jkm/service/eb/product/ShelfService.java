package cn.jkm.service.eb.product;

import cn.jkm.core.domain.mongo.product.Shelves;

import java.util.List;

/**
 * @Author: Guo Fei
 * @Description: 产品货架
 * @Date: 11:00 2017/7/21
 */
public interface ShelfService {

    /**
     * 新增
     * @param shelves
     * @return
     */
    Shelves addShelves(Shelves shelves);

    /**
     * 修改
     * @param shelves
     * @return
     */
    Shelves editShelves(Shelves shelves);

    /**
     * 货架合并
     * @param fromId
     * @param toId
     */
    void mergeShelves(String fromId, String toId);

    /**
     * 分页查询
     * @param keyword
     * @param sortStr 排序字符串使用分隔符“|”，
     *                如ASC|ASC|ASC的排序分别对应一下字段
     *                stockNum|realStockNum|specStockNum
     * @param limit
     * @param page
     * @return
     */
    List<Shelves> pageShelves(String keyword, String sortStr, int limit, int page);
}
