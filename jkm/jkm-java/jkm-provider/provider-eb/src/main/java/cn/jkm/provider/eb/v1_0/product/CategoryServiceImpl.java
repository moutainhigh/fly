package cn.jkm.provider.eb.v1_0.product;

import cn.jkm.core.domain.mongo.product.Category;
import cn.jkm.core.domain.type.Status;
import cn.jkm.framework.mongo.mongo.Mongo;
import cn.jkm.service.eb.product.CategoryService;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.util.StringUtils;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;

/**
 * @Author: Guo Fei
 * @Description: 产品分类
 * @Date: 11:47 2017/7/20
 */
@Service(version = "1.0")
public class CategoryServiceImpl implements CategoryService {
    @Override
    public void saveCategory(List<Category> categoryList) {
        categoryList.forEach(category -> {
            //新增分类
            if(StringUtils.isEmpty(category.getId())) {
                category.setStatus(Status.ACTIVE);
                category.getSubCategory().forEach(subCategory -> {
                    subCategory.setId(UUID.randomUUID().toString());
                    subCategory.setStatus(Status.ACTIVE);
                });
                Mongo.buildMongo().insert(category);
                return;
            } else {//修改分类
                Mongo.buildMongo().eq("_id", category.getId()).updateFirst(update -> {

                    update.set("name", category.getName());
                    update.set("icon", category.getIcon());
                    update.set("sortOrder", category.getSortOrder());

                    category.getSubCategory().forEach(subCategory -> {
                        //新增
                        if (StringUtils.isEmpty(subCategory.getId())) {
                            subCategory.setId(UUID.randomUUID().toString());
                            subCategory.setStatus(Status.ACTIVE);
                        }
                        update.set("subCategory", subCategory);
                    });
                }, Category.class);
            }
        });
    }

    @Override
    public List<Category> pageCategory(String keyword, int limit, int page) {
        Mongo mongo = Mongo.buildMongo().eq("status", Status.ACTIVE);
        if(StringUtils.isEmpty(keyword)) {
            mongo.fuzzy("name", keyword);
        }

        return mongo.limit(limit, page).find(Category.class);
    }

    @Override
    public void mergeCategory(String mergeType, String fromId, String toId) {
        //父类合并
        if("parent".equals(mergeType)) {
            Category fromObj = Mongo.buildMongo().eq("_id", fromId).findOne(Category.class);
            Category toObj = Mongo.buildMongo().eq("_id", toId).findOne(Category.class);
            Mongo.buildMongo().eq("_id", toId).updateFirst(update -> {
                fromObj.getSubCategory().forEach(subCategory -> {
                    toObj.getSubCategory().add(subCategory);
                });
                update.set("subCategory", toObj.getSubCategory());
            }, Category.class);

            Mongo.buildMongo().eq("_id", fromId).updateFirst(update -> {
                update.set("status", Status.DELETE);
            }, Category.class);

            //TODO 修改产品所属分类

        } else {//子类合并
            Category fromObj = Mongo.buildMongo().eq("subCategory.id", fromId).findOne(Category.class);
            Category toObj = Mongo.buildMongo().eq("subCategory.id", toId).findOne(Category.class);

            Iterator iterator = fromObj.getSubCategory().iterator();
            while (iterator.hasNext()) {
                Category item = (Category)iterator.next();
                if(fromId.equals(item.getId())) {
                    toObj.getSubCategory().add(item);
                    iterator.remove();
                }
            }

            Mongo.buildMongo().eq("_id", toId).updateFirst(update -> {
                update.set("subCategory", toObj.getSubCategory());
            }, Category.class);

            Mongo.buildMongo().eq("_id", fromId).updateFirst(update -> {
                update.set("subCategory", fromObj.getSubCategory());
            }, Category.class);

            //TODO 修改产品所属分类
        }
    }
}
