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
 * @Description: 属性模板查询
 * @Date: 15:47 2017/7/25
 */
@Component("attrTemplateSelect1.0")
@NotNull(name = {"templateId"}, message = "缺少参数")
public class AttrTemplateSelect extends AbstractManageService {

    @Reference(version = "1.0")
    private AttrTemplateService attrTemplateService;

    @Override
    public Map service(Map request) {
        String templateId = formatString(request.get("templateId"));
        return ApiResponse.success().body(attrTemplateService.findAttrTemplate(templateId));
    }
}
