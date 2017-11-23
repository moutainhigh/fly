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
 * @Description: 货架合并
 * @Date: 9:35 2017/7/25
 */
@Component("shelvesMerge1.0")
@NotNull(name = {"json"}, message = "缺少参数")
public class ShelvesMerge extends AbstractManageService {

    @Reference(version = "1.0")
    private ShelfService shelfService;

    @Override
    public Map service(Map request) {
        String fromId = formatString(request.get("fromId"));
        String toId = formatString(request.get("toId"));
        shelfService.mergeShelves(fromId, toId);
        return ApiResponse.success();
    }
}
