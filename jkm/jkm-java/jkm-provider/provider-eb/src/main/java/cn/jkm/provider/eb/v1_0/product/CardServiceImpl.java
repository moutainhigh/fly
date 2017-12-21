package cn.jkm.provider.eb.v1_0.product;

import cn.jkm.core.domain.mongo.product.Card;
import cn.jkm.core.domain.type.ShelfStatus;
import cn.jkm.core.domain.type.Status;
import cn.jkm.framework.mongo.mongo.Mongo;
import cn.jkm.service.eb.product.CardService;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @Author: Guo Fei
 * @Description: 卡券
 * @Date: 17:01 2017/7/20
 */
@Service(version = "1.0")
public class CardServiceImpl implements CardService {

    @Override
    public Card addCard(Card card) {
        card.setStatus(Status.ACTIVE);
        Mongo.buildMongo().insert(card);
        return card;
    }

    @Override
    public void delCard(String cardId) {
        if(cardId.contains(",")) {
            String[] cardIds = cardId.split(",");
            for(String id : cardIds) {
                Mongo.buildMongo().eq("_id", id).updateFirst(update -> {
                    update.set("status", Status.DELETE);
                }, Card.class);
            }
        } else {
            Mongo.buildMongo().eq("_id", cardId).updateFirst(update -> {
                update.set("status", Status.DELETE);
            }, Card.class);
        }
    }

    @Override
    public void updateCard(Card card) {
        Mongo.buildMongo().eq("_id", card.getId()).updateFirst(update -> {
            update.set("cardStatus", ShelfStatus.UP);
            update.set("status", Status.ACTIVE);
        }, Card.class);
    }

    @Override
    public Card findCard(String cardId) {
        return Mongo.buildMongo().eq("_id", cardId).findOne(Card.class);
    }

    @Override
    public void shelfCard(ShelfStatus shelfStatus, String cardId) {

        if(cardId.contains(",")) {
            String[] cardIds = cardId.split(",");
            for(String id : cardIds) {
                Mongo.buildMongo().eq("_id", id).updateFirst(update -> {
                    update.set("cardStatus", shelfStatus);
                }, Card.class);
            }
        } else {
            Mongo.buildMongo().eq("_id", cardId).updateFirst(update -> {
                update.set("cardStatus", shelfStatus);
            }, Card.class);
        }
    }

    @Override
    public List<Card> pageCard(String shelfStatus, String keyword, int limit, int page) {
        Mongo mongo = Mongo.buildMongo().eq("status", Status.ACTIVE);
        if("UP".equals(shelfStatus)) {
            mongo.eq("cardStatus", shelfStatus);
        } else if(!StringUtils.isEmpty(keyword)) {
            mongo.fuzzy("name", keyword);
            mongo.or(new String[]{"name", "cardNo"},
                    new String[]{keyword, keyword},
                    new Mongo.OP[]{Mongo.OP.F, Mongo.OP.F});
        }

        return mongo.limit(limit, page).find(Card.class);
    }
}
