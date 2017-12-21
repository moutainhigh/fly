package cn.jkm.api.klm.manage.service.product.branch;

import cn.jkm.api.core.helper.ApiResponse;
import cn.jkm.api.klm.manage.service.AbstractManageService;
import cn.jkm.core.domain.mongo.product.Branch;
import cn.jkm.framework.core.validator.NotNull;
import cn.jkm.service.eb.product.BranchService;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Author: Guo Fei
 * @Description: 品牌分页查询
 * @Date: 16:55 2017/7/24
 */
@Component("branchPage1.0")
@NotNull(name = {"limit","page"}, message = "缺少参数")
public class BranchPage extends AbstractManageService {

    @Reference(version = "1.0")
    private BranchService branchService;

    @Override
    public Map service(Map request) {
        String keyword = formatString(request.get("keyword"));
        int limit = formatInteger(request.get("limit"));
        int page = formatInteger(request.get("page"));
        return ApiResponse.success().body(branchService.pageBranch(keyword, limit, page));
    }
}
