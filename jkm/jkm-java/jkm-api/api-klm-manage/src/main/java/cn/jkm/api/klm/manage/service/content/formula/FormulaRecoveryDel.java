package cn.jkm.api.klm.manage.service.content.formula;

import cn.jkm.api.core.helper.ApiResponse;
import cn.jkm.api.klm.manage.service.AbstractManageService;
import cn.jkm.framework.core.validator.NotNull;
import cn.jkm.service.content.FormulaService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author zhong
 * @version V1.0
 * @project jkm-root
 * @package cn.jkm.api.klm.manage.service.content.formula
 * @description //恢复删除的配方
 * @update 2017/7/25 16:13
 */
@Component("formulaRecoveryDel1.0")
@NotNull(name = {"formulaId"}, message = "缺少参数")
public class FormulaRecoveryDel extends AbstractManageService {

    @Reference(version = "1.0")
    FormulaService formulaService;

    @Override
    public Map service(Map request) {

        formulaService.formulaRecoveryDel(formatString(request.get("formulaId")));
        return ApiResponse.success().body("");
    }
}