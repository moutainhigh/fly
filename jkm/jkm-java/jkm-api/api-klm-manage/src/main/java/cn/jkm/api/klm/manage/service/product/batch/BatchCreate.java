package cn.jkm.api.klm.manage.service.product.batch;

import cn.jkm.api.core.helper.ApiResponse;
import cn.jkm.api.klm.manage.service.AbstractManageService;
import cn.jkm.framework.core.validator.NotNull;
import cn.jkm.service.eb.module.AttrTemplateModule;
import cn.jkm.service.eb.module.BatchModule;
import cn.jkm.service.eb.product.AttrTemplateService;
import cn.jkm.service.eb.product.BatchService;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @Author: Guo Fei
 * @Description: 新增批次信息
 * @Date: 10:50 2017/7/25
 */
@Component("batchCreate1.0")
@NotNull(name = {"json"}, message = "缺少参数")
public class BatchCreate extends AbstractManageService {

    @Reference(version = "1.0")
    private BatchService batchService;

    @Override
    public Map service(Map request) {
        String json = formatString(request.get("json"));
        List<BatchModule> moduleList = JSON.parseObject(json, List.class);
        batchService.addBatch(moduleList);
        return ApiResponse.success();
    }
}
