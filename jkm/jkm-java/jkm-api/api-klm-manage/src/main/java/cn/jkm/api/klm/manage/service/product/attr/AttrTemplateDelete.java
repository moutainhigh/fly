package cn.jkm.api.klm.manage.service.product.attr;

import cn.jkm.api.core.helper.ApiResponse;
import cn.jkm.api.klm.manage.service.AbstractManageService;
import cn.jkm.framework.core.validator.NotNull;
import cn.jkm.service.eb.product.AttrTemplateService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Author: Guo Fei
 * @Description: 删除属性模板
 * @Date: 15:39 2017/7/25
 */
@Component("attrTemplateDelete1.0")
@NotNull(name = {"templateId"}, message = "缺少参数")
public class AttrTemplateDelete extends AbstractManageService {

    @Reference(version = "1.0")
    private AttrTemplateService attrTemplateService;

    @Override
    public Map service(Map request) {
        String templateId = formatString(request.get("templateId"));
        attrTemplateService.delAttrTemplate(templateId);
        return ApiResponse.success();
    }
}
