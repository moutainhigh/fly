package cn.jkm.api.klm.manage.service.product.category;

import cn.jkm.api.core.helper.ApiResponse;
import cn.jkm.api.klm.manage.service.AbstractManageService;
import cn.jkm.framework.core.validator.NotNull;
import cn.jkm.service.eb.product.CategoryService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Author: Guo Fei
 * @Description: 分类分页查询
 * @Date: 17:22 2017/7/25
 */
@Component("categoryPage1.0")
@NotNull(name = {"limit"}, message = "缺少参数")
public class CategoryPage extends AbstractManageService {

    @Reference(version = "1.0")
    private CategoryService categoryService;

    @Override
    public Map service(Map request) {
        String keyword = formatString(request.get("keyword"));
        int limit = formatInteger(request.get("limit"));
        int page = formatInteger(request.get("page"));
        return ApiResponse.success().body(categoryService.pageCategory(keyword, limit, page));
    }
}
