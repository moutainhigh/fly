package cn.jkm.service.eb.product;

import cn.jkm.core.domain.mongo.product.Card;
import cn.jkm.core.domain.type.ShelfStatus;

import java.util.List;

/**
 * @Author: Guo Fei
 * @Description: 卡券
 * @Date: 10:01 2017/7/20
 */
public interface CardService {

    /**
     * 新增卡券
     * @param card
     * @return
     */
    Card addCard(Card card);

    /**
     * 删除卡券
     * @param cardId
     */
    void delCard(String cardId);

    /**
     * 修改卡券
     * @param card
     */
    void updateCard(Card card);

    /**
     * 卡券查询
     * @param cardId
     * @return
     */
    Card findCard(String cardId);

    /**
     * 产品上下架
     * @param shelfStatus
     * @param cardId
     */
    void shelfCard(ShelfStatus shelfStatus, String cardId);

    /**
     * 卡券分页查询
     * @param status 是否上下架
     * @param keyword 卡券编号/名称
     * @param limit
     * @param page
     * @return
     */
    List<Card> pageCard(String status, String keyword, int limit, int page);
}
