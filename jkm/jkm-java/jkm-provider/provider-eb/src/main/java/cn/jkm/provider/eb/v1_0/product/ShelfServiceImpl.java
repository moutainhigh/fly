package cn.jkm.provider.eb.v1_0.product;

import cn.jkm.core.domain.mongo.product.Batch;
import cn.jkm.core.domain.mongo.product.Shelves;
import cn.jkm.core.domain.type.Status;
import cn.jkm.framework.mongo.mongo.Mongo;
import cn.jkm.service.core.exception.ProviderException;
import cn.jkm.service.eb.product.ShelfService;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @Author: Guo Fei
 * @Description: 货架
 * @Date: 11:05 2017/7/21
 */
public class ShelfServiceImpl implements ShelfService {

    @Override
    public Shelves addShelves(Shelves shelves) {
        if(this.nameExistCount(shelves.getName()) > 0) {
            throw new ProviderException("货架名称已经存在");
        }

        Mongo.buildMongo().insert(shelves);
        return shelves;
    }

    @Override
    public Shelves editShelves(Shelves shelves) {
        if(StringUtils.isEmpty(shelves.getId()) || StringUtils.isEmpty(shelves.getName())) {
            throw new ProviderException("缺少请求参数");
        }

        if(this.nameExistCount(shelves.getName()) > 0) {
            throw new ProviderException("货架名称已经存在");
        }

        Mongo.buildMongo().eq("_id", shelves.getId()).upsert(update -> {
            update.set("name", shelves.getName());
            update.set("sortOrder", shelves.getSortOrder());
        }, "shelves");

        return shelves;
    }

    @Override
    public void mergeShelves(String fromId, String toId) {
        Shelves fromObj = Mongo.buildMongo().eq("_id", fromId).findOne(Shelves.class);
        Shelves toObj = Mongo.buildMongo().eq("_id", toId).findOne(Shelves.class);

        Mongo.buildMongo().eq("shelvesId", fromId).updateMulti(update -> {
            update.set("shelvesId", toId);
        }, "shelves");

        Mongo.buildMongo().eq("shelvesId", fromId).updateMulti(update -> {
            update.set("shelvesId", toId);
            update.set("shelvesName", toObj.getName());
        }, "product_sn");

        Mongo.buildMongo().eq("_id", fromId).updateFirst(update -> {
            update.set("status", Status.DELETE);
        }, Batch.class);

        Mongo.buildMongo().eq("_id", toId).updateFirst(update -> {
            update.inc("stockNum", fromObj.getStockNum());
            update.inc("realStockNum", fromObj.getRealStockNum());
            update.inc("specStockNum", fromObj.getSpecStockNum());
        }, "shelves");
    }

    @Override
    public List<Shelves> pageShelves(String keyword, String sortStr, int limit, int page) {
        Mongo mongo = Mongo.buildMongo().eq("status", Status.ACTIVE);
        if(!StringUtils.isEmpty(keyword)) {
            mongo.or(new String[] {"name"},
                    new String[] {keyword},
                    Mongo.OP.F);
        }

        String[] sortArray = sortStr.split("|");
        if("asc".equals(sortArray[0].toLowerCase())) {
            mongo.asc("stockNum");
        } else {
            mongo.desc("stockNum");
        }

        if("asc".equals(sortArray[1].toLowerCase())) {
            mongo.asc("realStockNum");
        } else {
            mongo.desc("realStockNum");
        }

        if("asc".equals(sortArray[2].toLowerCase())) {
            mongo.asc("specStockNum");
        } else {
            mongo.desc("specStockNum");
        }

        if(limit < 0) {//不分页
            return mongo.find(Shelves.class);
        }

        return mongo.limit(limit, page).find(Shelves.class);
    }

    private long nameExistCount(String name) {
        return Mongo.buildMongo().eq("name", name).count("shelves");
    }
}
