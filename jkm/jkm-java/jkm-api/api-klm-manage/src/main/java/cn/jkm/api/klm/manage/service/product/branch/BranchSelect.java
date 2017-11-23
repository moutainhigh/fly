package cn.jkm.api.klm.manage.service.product.branch;

import cn.jkm.api.core.helper.ApiResponse;
import cn.jkm.api.klm.manage.service.AbstractManageService;
import cn.jkm.framework.core.validator.NotNull;
import cn.jkm.service.eb.product.BranchService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Author: Guo Fei
 * @Description: 根据id查询品牌
 * @Date: 16:51 2017/7/24
 */
@Component("branchSelect1.0")
@NotNull(name = {"branchId"}, message = "缺少参数")
public class BranchSelect extends AbstractManageService {

    @Reference(version = "1.0")
    private BranchService branchService;

    @Override
    public Map service(Map request) {
        String branchId = formatString(request.get("branchId"));
        return ApiResponse.success().body(branchService.findBranch(branchId));
    }
}
