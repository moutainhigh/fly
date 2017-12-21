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
 * @Description: 属性模板分页查询
 * @Date: 15:43 2017/7/25
 */
@Component("attrTemplatePage1.0")
@NotNull(name = {"limit"}, message = "缺少参数")
public class AttrTemplatePage extends AbstractManageService {

    @Reference(version = "1.0")
    private AttrTemplateService attrTemplateService;

    @Override
    public Map service(Map request) {
        String keyword = formatString(request.get("keyword"));
        int limit = formatInteger(request.get("limit"));
        int page = formatInteger(request.get("page"));
        return ApiResponse.success().body(attrTemplateService.pageAttrTemplate(keyword, limit, page));
    }
}
