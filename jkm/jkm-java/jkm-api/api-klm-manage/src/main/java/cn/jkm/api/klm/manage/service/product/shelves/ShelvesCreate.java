package cn.jkm.api.klm.manage.service.product.shelves;

import cn.jkm.api.core.helper.ApiResponse;
import cn.jkm.api.klm.manage.service.AbstractManageService;
import cn.jkm.core.domain.mongo.product.Shelves;
import cn.jkm.framework.core.validator.NotNull;
import cn.jkm.service.eb.product.ShelfService;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Author: Guo Fei
 * @Description: 新增货架
 * @Date: 8:57 2017/7/25
 */
@Component("shelvesCreate1.0")
@NotNull(name = {"json"}, message = "缺少参数")
public class ShelvesCreate extends AbstractManageService {

    @Reference(version = "1.0")
    private ShelfService shelfService;

    @Override
    public Map service(Map request) {
        String json = formatString(request.get("json"));
        Shelves shelves = JSON.parseObject(json, Shelves.class);
        return ApiResponse.success().body(shelfService.addShelves(shelves));
    }
}
