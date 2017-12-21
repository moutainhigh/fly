package cn.jkm.api.klm.manage.service.content.channel;

import cn.jkm.api.core.helper.ApiResponse;
import cn.jkm.api.klm.manage.service.AbstractManageService;
import cn.jkm.core.domain.mysql.content.SettingChannel;
import cn.jkm.framework.core.validator.NotNull;
import cn.jkm.service.content.ChannelService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author zhong
 * @version V1.0
 * @project jkm-root
 * @package cn.jkm.api.klm.manage.service.content.channel
 * @description //根据频道获取栏目
 * @update 2017/7/25 15:40
 */
@Component("columnList1.0")
@NotNull(name = {"channelId"}, message = "缺少参数")
public class ColumnList extends AbstractManageService {

    @Reference(version = "1.0")
    ChannelService channelService;

    @Override
    public Map service(Map request) {

        List<SettingChannel> result = channelService.findColumnList(formatString(request.get("channelId")));
        return ApiResponse.success().body(result);
    }
}
