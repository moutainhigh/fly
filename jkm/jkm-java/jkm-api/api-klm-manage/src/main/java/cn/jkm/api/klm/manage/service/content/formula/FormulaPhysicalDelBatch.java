package cn.jkm.api.klm.manage.service.content.formula;

import cn.jkm.api.core.helper.ApiResponse;
import cn.jkm.api.klm.manage.service.AbstractManageService;
import cn.jkm.framework.core.validator.NotNull;
import cn.jkm.service.content.FormulaService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Map;

/**
 * @author zhong
 * @version V1.0
 * @project jkm-root
 * @package cn.jkm.api.klm.manage.service.content.formula
 * @description //物理批量清除配方,不能恢复操作
 * @update 2017/7/25 16:13
 */
@Component("formulaPhysicalDelBatch1.0")
@NotNull(name = {"formulaIds"}, message = "缺少参数")
public class FormulaPhysicalDelBatch extends AbstractManageService {

    @Reference(version = "1.0")
    FormulaService formulaService;

    @Override
    public Map service(Map request) {
        String formulaIds = formatString(request.get("formulaIds"));
        formulaIds = formulaIds.replace("[","");
        formulaIds = formulaIds.replace("]","");
        String[] formulaIdArray = formulaIds.split(",");
        formulaService.formulaPhysicalDel(formulaIdArray);
        return ApiResponse.success().body("");
    }
}