package cn.jkm.service.content;

import java.util.Map;

/**
 * @author zhong
 * @version V1.0
 * @project jkm-root
 * @package cn.jkm.service.content
 * @description //发帖规则接口
 * @update 2017/7/26 10:58
 */
public interface RulePostService {

    /**
     *
     * @param json
     * @return
     */
    public boolean upsert(String json);

    /**
     * 获取积分规则的所有信息
     * @return
     */
    public Map<String,Object> getInfo();
}
