package cn.jkm.api.klm.manage.service.content.formula;

import cn.jkm.api.core.helper.ApiResponse;
import cn.jkm.api.klm.manage.service.AbstractManageService;
import cn.jkm.core.domain.mongo.content.ContentFormula;
import cn.jkm.framework.core.validator.NotNull;
import cn.jkm.service.content.FormulaService;
import cn.jkm.service.core.domain.ListResult;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author zhong
 * @version V1.0
 * @project jkm-root
 * @package cn.jkm.api.klm.manage.service.content.formula
 * @description //显示所有的配方信息
 * @update 2017/7/25 16:13
 */
@Component("formulaListDel1.0")
@NotNull(name = {"limit","page"}, message = "缺少参数")
public class FormulaListDel extends AbstractManageService {

    @Reference(version = "1.0")
    FormulaService formulaService;

    @Override
    public Map service(Map request) {

        List<ContentFormula> formulaList = formulaService.formulaDelList(formatInteger(request.get("limit")),formatInteger(request.get("page")));
        long count = formulaService.formulaDelCount();
        ListResult result = new ListResult(count,formulaList);
        return ApiResponse.success().body(result);
    }
}