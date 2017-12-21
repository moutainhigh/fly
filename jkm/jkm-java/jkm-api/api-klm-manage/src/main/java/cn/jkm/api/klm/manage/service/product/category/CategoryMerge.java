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
 * @Description: 分类合并
 * @Date: 17:25 2017/7/25
 */
@Component("categoryMerge1.0")
@NotNull(name = {"mergeType", "fromId", "toId"}, message = "缺少参数")
public class CategoryMerge extends AbstractManageService {

    @Reference(version = "1.0")
    private CategoryService categoryService;

    @Override
    public Map service(Map request) {
        String mergeType = formatString(request.get("mergeType"));
        String fromId = formatString(request.get("fromId"));
        String toId = formatString(request.get("toId"));
        categoryService.mergeCategory(mergeType, fromId, toId);
        return ApiResponse.success();
    }
}
