package cn.jkm.service.content;

import java.util.Map;

/**
 * @author zhong
 * @version V1.0
 * @project jkm-root
 * @package cn.jkm.service.content
 * @description //分享图片的设置
 * @update 2017/7/26 9:26
 */
public interface ShareSettingService {

    /**
     * 新增或修改分享的图标
     */
    boolean upsert(String json);

    public Map<String,Object> getShareInfo();
}
