package cn.jkm.service.eb.product;

import cn.jkm.core.domain.mysql.product.Goods;

import java.util.List;

/**
 * @Author: Guo Fei
 * @Description: 商品信息
 * @Date: 13:51 2017/7/27
 */
public interface GoodsService {

    void shelvesGoods(List<Goods> goodsList);

    void updateGoods();
}
