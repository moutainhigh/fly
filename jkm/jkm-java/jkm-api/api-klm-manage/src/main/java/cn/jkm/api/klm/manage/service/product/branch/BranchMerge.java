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
 * @Description: 品牌合并
 * @Date: 17:17 2017/7/24
 */
@Component("branchMerge1.0")
@NotNull(name = {"fromId", "toId"}, message = "缺少参数")
public class BranchMerge extends AbstractManageService {

    @Reference(version = "1.0")
    private BranchService branchService;

    @Override
    public Map service(Map request) {
        String fromId = formatString(request.get("fromId"));
        String toId = formatString(request.get("toId"));
        branchService.mergeBranch(fromId, toId);
        return ApiResponse.success();
    }
}
