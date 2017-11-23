package cn.jkm.provider.content.action;

import cn.jkm.core.domain.mysql.content.SettingSystem;
import cn.jkm.core.domain.type.Status;
import cn.jkm.framework.mysql.Jdbc;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhong
 * @version V1.0
 * @project jkm-root
 * @package cn.jkm.provider.content.action
 * @description //系统键值对的设置
 * @update 2017/7/26 14:13
 */

public class SettingSystemAction {
    public static void upsert(String key, String name, String value) {

        SettingSystem settingSystem = Jdbc.build().query(SettingSystem.class)
                .where("setting_key = ? ", key).first();
        if (settingSystem == null) {
            settingSystem = new SettingSystem();
            settingSystem.setStatus(Status.ACTIVE);
            settingSystem.setKeyName(name);
            settingSystem.setSettingKey(key);
            settingSystem.setKeyValue(value);
            settingSystem.setId(settingSystem.uuid());
            Jdbc.build().insert(settingSystem);
        } else {
            Map<String,Object> map = new HashMap() {{
                put("key_value",value);
                put("create_at",System.currentTimeMillis()/1000);
            }};
            Jdbc.build().update(SettingSystem.class)
                    .where("id = ? ", settingSystem.getId())
                    .set(map);
        }
    }
}
