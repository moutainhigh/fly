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
 * @Description: 新增品牌
 * @Date: 11:16 2017/7/24
 */
@Component("branchCreate1.0")
@NotNull(name = {"json"}, message = "缺少参数")
public class BranchCreate extends AbstractManageService {

    @Reference(version = "1.0")
    private BranchService branchService;

    @Override
    public Map service(Map request) {
        String json = formatString(request.get("json"));
        Branch branch = JSON.parseObject(json, Branch.class);
        return ApiResponse.success().body(branchService.addBranch(branch));
    }
}
