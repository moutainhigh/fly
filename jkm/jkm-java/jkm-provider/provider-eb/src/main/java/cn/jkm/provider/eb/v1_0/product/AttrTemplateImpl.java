package cn.jkm.provider.eb.v1_0.product;

import cn.jkm.core.domain.mongo.product.Attr;
import cn.jkm.core.domain.mongo.product.AttrTemplate;
import cn.jkm.core.domain.mysql.product.ProductAttr;
import cn.jkm.core.domain.type.Status;
import cn.jkm.framework.mongo.mongo.Mongo;
import cn.jkm.service.core.exception.ProviderException;
import cn.jkm.service.eb.module.AttrTemplateModule;
import cn.jkm.service.eb.product.AttrTemplateService;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Author: Guo Fei
 * @Description: 属性模板
 * @Date: 11:08 2017/7/25
 */
public class AttrTemplateImpl implements AttrTemplateService {

    @Override
    public AttrTemplate addAttrTemplate(AttrTemplateModule module) {

        if(this.nameExistCount("", module.getName()) > 0) {
            throw new ProviderException("模板名称已经存在");
        }

        AttrTemplate template = new AttrTemplate();
        template.setName(module.getName());

        Set<Attr> attrs = module.getAttrs();

        Mongo.buildMongo().insert(template);
        Mongo.buildMongo().insert(attrs);
        return template;
    }

    @Override
    public void updateAttrTemplate(AttrTemplateModule module) {

        if(this.nameExistCount(module.getId(), module.getName()) > 0) {
            throw new ProviderException("模板名称已经存在");
        }

        Set<Attr> attrs = module.getAttrs();

        Set<String> delIds = new HashSet<>();
        attrs.forEach(attr -> {
            if(!StringUtils.isEmpty(attr.getId())) {
                delIds.add(attr.getId());
            }

            //新增或修改属性
            Mongo.buildMongo().upsert(update -> {
                update.set("_id", attr.getId());
                update.set("templateId", module.getId());
                update.set("attrKey", attr.getAttrKey());
                update.set("attrRequired", attr.getAttrRequired());
            }, "attr");

            //修改产品属性key
            Mongo.buildMongo().eq("templateId", module.getId()).eq("attrId", attr.getId()).updateMulti(update -> {
                update.set("attrKey", attr.getAttrValue());
                update.set("attrRequired", attr.getAttrRequired());
            }, ProductAttr.class);
        });

        //修改属性模板
        Mongo.buildMongo().eq("_id", module.getId()).updateFirst(update -> {
            update.set("name", module.getName());
        }, AttrTemplate.class);

        //删除产品属性
        Mongo.buildMongo().eq("templateId", module.getId()).nin("_id", delIds).remove("product_attr");
    }

    @Override
    public void delAttrTemplate(String attrTemplateId) {
        Mongo.buildMongo().eq("_id", attrTemplateId).updateMulti(update -> {
            update.set("status", Status.DELETE);
        }, AttrTemplate.class);

        Mongo.buildMongo().eq("templateId", attrTemplateId).updateMulti(update -> {
            update.set("status", Status.DELETE);
        }, Attr.class);

        Mongo.buildMongo().eq("templateId", attrTemplateId).updateMulti(update -> {
            update.set("status", Status.DELETE);
        }, ProductAttr.class);
    }

    @Override
    public AttrTemplateModule findAttrTemplate(String templateId) {

        Set<Attr> attrs = (Set<Attr>) Mongo.buildMongo().eq("status", Status.ACTIVE).eq("templateId", templateId).find(Attr.class);
        AttrTemplate template = Mongo.buildMongo().eq("status", Status.ACTIVE).eq("_id", templateId).findOne(AttrTemplate.class);

        AttrTemplateModule module = new AttrTemplateModule();
        module.setId(templateId);
        module.setName(template.getName());
        module.setStatus(template.getStatus());
        module.setAttrs(attrs);

        return module;
    }

    @Override
    public List<AttrTemplate> pageAttrTemplate(String keyword, int limit, int page) {
        Mongo mongo = Mongo.buildMongo().eq("status", Status.ACTIVE);
        if(!StringUtils.isEmpty(keyword)) {
            mongo.fuzzy("name", keyword);
        }

        if(limit > 0) {
            return mongo.limit(limit, page).find(AttrTemplate.class);
        }

        return mongo.find(AttrTemplate.class);
    }

    private long nameExistCount(String id, String name) {
        Mongo mongo = Mongo.buildMongo().eq("name", name);
        if(!StringUtils.isEmpty(id)) {
            mongo.ne("_id", id);
        }
        return mongo.count("attr_template");
    }
}
