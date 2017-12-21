package cn.jkm.api.klm.manage.service.product.attr;

import cn.jkm.api.core.helper.ApiResponse;
import cn.jkm.api.klm.manage.service.AbstractManageService;
import cn.jkm.framework.core.validator.NotNull;
import cn.jkm.service.eb.module.AttrTemplateModule;
import cn.jkm.service.eb.product.AttrTemplateService;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Author: Guo Fei
 * @Description: 更新属性模板
 * @Date: 15:41 2017/7/25
 */
@Component("attrTemplateUpdate1.0")
@NotNull(name = {"json"}, message = "缺少参数")
public class AttrTemplateUpdate extends AbstractManageService {

    @Reference(version = "1.0")
    private AttrTemplateService attrTemplateService;

    @Override
    public Map service(Map request) {
        String json = formatString(request.get("json"));
        AttrTemplateModule module = JSON.parseObject(json, AttrTemplateModule.class);
        attrTemplateService.updateAttrTemplate(module);
        return ApiResponse.success();
    }
}
