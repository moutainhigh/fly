package cn.jkm.api.klm.manage.service.content.channel;

import cn.jkm.api.core.helper.ApiResponse;
import cn.jkm.api.klm.manage.service.AbstractManageService;
import cn.jkm.core.domain.mysql.content.SettingBanner;
import cn.jkm.core.domain.mysql.content.SettingChannel;
import cn.jkm.framework.core.validator.NotNull;
import cn.jkm.service.content.BannerService;
import cn.jkm.service.content.ChannelService;
import cn.jkm.service.core.domain.ListResult;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author zhong
 * @version V1.0
 * @project jkm-root
 * @package cn.jkm.api.klm.manage.service.content.channel
 * @description //获取所有频道
 * @update 2017/7/25 15:40
 */
@Component("channelList1.0")
public class ChannelList extends AbstractManageService{

    @Reference(version = "1.0")
    ChannelService channelService;

    @Override
    public Map service(Map request) {

        List<SettingChannel> result = channelService.findChannelList();
        return ApiResponse.success().body(result);
    }
}
