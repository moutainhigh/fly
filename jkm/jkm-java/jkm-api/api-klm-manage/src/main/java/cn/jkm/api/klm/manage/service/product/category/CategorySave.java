package cn.jkm.api.klm.manage.service.product.category;

import cn.jkm.api.core.helper.ApiResponse;
import cn.jkm.api.klm.manage.service.AbstractManageService;
import cn.jkm.core.domain.mongo.product.Category;
import cn.jkm.framework.core.validator.NotNull;
import cn.jkm.service.eb.product.CategoryService;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @Author: Guo Fei
 * @Description: 新增或修改分类
 * @Date: 17:17 2017/7/25
 */
@Component("categorySave1.0")
@NotNull(name = {"json"}, message = "缺少参数")
public class CategorySave extends AbstractManageService {

    @Reference(version = "1.0")
    private CategoryService categoryService;

    @Override
    public Map service(Map request) {
        String json = formatString(request.get("json"));
        List<Category> catList = JSON.parseObject(json, List.class);
        categoryService.saveCategory(catList);
        return ApiResponse.success();
    }
}
