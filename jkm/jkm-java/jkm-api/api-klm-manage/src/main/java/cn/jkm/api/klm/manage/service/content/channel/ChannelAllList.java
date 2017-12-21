package cn.jkm.api.klm.manage.service.content.channel;

import cn.jkm.api.core.helper.ApiResponse;
import cn.jkm.api.klm.manage.service.AbstractManageService;
import cn.jkm.core.domain.mysql.content.SettingChannel;
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
 * @description //查询所有的频道栏目
 * @update 2017/7/25 15:59
 */
@Component("channelAllList1.0")
public class ChannelAllList extends AbstractManageService {

    @Reference(version = "1.0")
    ChannelService channelService;

    @Override
    public Map service(Map request) {

        List<SettingChannel> result = channelService.findAllActiveChannelAndColumn();
        return ApiResponse.success().body(result);
    }
}