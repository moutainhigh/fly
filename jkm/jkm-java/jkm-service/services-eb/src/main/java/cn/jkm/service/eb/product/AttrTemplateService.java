package cn.jkm.service.eb.product;

import cn.jkm.core.domain.mongo.product.AttrTemplate;
import cn.jkm.service.eb.module.AttrTemplateModule;

import java.util.List;

/**
 * @Author: Guo Fei
 * @Description: 属性模板
 * @Date: 10:52 2017/7/25
 */
public interface AttrTemplateService {

    /**
     * 新增属性模板
     * @param module
     * @return
     */
    AttrTemplate addAttrTemplate(AttrTemplateModule module);

    /**
     * 修改或删除基础属性
     * @param attrTemplate
     */
    void updateAttrTemplate(AttrTemplateModule attrTemplate);

    /**
     * 删除
     * @param attrTemplateId
     */
    void delAttrTemplate(String attrTemplateId);

    /**
     * 根据id查询模板
     * @param templateId
     * @return
     */
    AttrTemplateModule findAttrTemplate(String templateId);

    /**
     * 分页查询
     * @param keyword
     * @param limit
     * @param page
     * @return
     */
    List<AttrTemplate> pageAttrTemplate(String keyword, int limit, int page);
}
