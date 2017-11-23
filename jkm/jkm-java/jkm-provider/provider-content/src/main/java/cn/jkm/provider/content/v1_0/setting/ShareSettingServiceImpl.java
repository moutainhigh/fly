package cn.jkm.provider.content.v1_0.setting;

import cn.jkm.core.domain.mysql.content.SettingSystem;
import cn.jkm.framework.mysql.Jdbc;
import cn.jkm.provider.content.action.SettingSystemAction;
import cn.jkm.service.content.ShareSettingService;
import com.alibaba.druid.support.json.JSONParser;
import com.alibaba.dubbo.config.annotation.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhong
 * @version V1.0
 * @project jkm-root
 * @package cn.jkm.provider.content.v1_0.setting
 * @description //设置分享的图片
 * @update 2017/7/26 15:05
 */
@Service(version = "1.0")
public class ShareSettingServiceImpl implements ShareSettingService{
    @Override
    public boolean upsert(String json) {

        Map<String,Object> hashMap = new JSONParser(json).parseMap();
        Object topPic = hashMap.get("TOP_PIC");
        Object bottomPic = hashMap.get("BOTTOM_PIC");

        SettingSystemAction.upsert("TOP_PIC","分享页上标",topPic.toString());
        SettingSystemAction.upsert("BOTTOM_PIC","分享页下标",bottomPic.toString());

        return true;
    }

    @Override
    public Map<String, Object> getShareInfo() {
        SettingSystem topPic = Jdbc.build().query(SettingSystem.class).where("setting_key = ?","TOP_PIC").first();
        SettingSystem bottomPic = Jdbc.build().query(SettingSystem.class).where("setting_key = ?","BOTTOM_PIC").first();

        return new HashMap(){{
            put("TOP_PIC",topPic.getKeyValue());
            put("BOTTOM_PIC",bottomPic.getKeyValue());
        }};
    }
}
