package cn.jkm.api.klm.manage.service.product.shelves;

import cn.jkm.api.core.helper.ApiResponse;
import cn.jkm.api.klm.manage.service.AbstractManageService;
import cn.jkm.framework.core.validator.NotNull;
import cn.jkm.service.eb.product.ShelfService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Author: Guo Fei
 * @Description: 货架信息分页查询
 * @Date: 9:33 2017/7/25
 */
@Component("shelvesPage1.0")
@NotNull(name = {"limit", "page"}, message = "缺少参数")
public class ShelvesPage  extends AbstractManageService {

    @Reference(version = "1.0")
    private ShelfService shelfService;

    @Override
    public Map service(Map request) {
        String keyword = formatString(request.get("keyword"));
        String sortStr = formatString(request.get("sortStr"));
        int limit = formatInteger(request.get("limit"));
        int page = formatInteger(request.get("page"));
        return ApiResponse.success().body(shelfService.pageShelves(keyword, sortStr, limit, page));
    }
}
