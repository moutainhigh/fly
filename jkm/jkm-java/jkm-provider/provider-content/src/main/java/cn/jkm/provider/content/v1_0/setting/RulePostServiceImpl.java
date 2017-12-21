package cn.jkm.provider.content.v1_0.setting;

import cn.jkm.core.domain.mysql.content.RuleReward;
import cn.jkm.core.domain.mysql.content.SettingSystem;
import cn.jkm.core.domain.type.PunishType;
import cn.jkm.core.domain.type.RewardType;
import cn.jkm.framework.mysql.Jdbc;
import cn.jkm.provider.content.action.RulePostAction;
import cn.jkm.provider.content.action.SettingSystemAction;
import cn.jkm.service.content.RulePostService;
import com.alibaba.druid.support.json.JSONParser;
import com.alibaba.dubbo.common.json.JSON;
import com.alibaba.dubbo.common.json.ParseException;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhong
 * @version V1.0
 * @project jkm-root
 * @package cn.jkm.provider.content.v1_0.setting
 * @description //发帖规则设置
 * @update 2017/7/26 11:27
 */
@Service(version = "1.0")
public class RulePostServiceImpl implements RulePostService {

    @Transactional
    @Override
    public boolean upsert(String json) {
        Map<String,Object> hashMap = new JSONParser(json).parseMap();

            SettingSystemAction.upsert(RewardType.SUBJECT_ACCEPT.toString(),RewardType.SUBJECT_ACCEPT.getName(),hashMap.get("SUBJECT_ACCEPT").toString());
            SettingSystemAction.upsert(RewardType.ACCEPT_SUBORDINATW.toString(),RewardType.ACCEPT_SUBORDINATW.getName(),hashMap.get("ACCEPT_SUBORDINATW").toString());
            SettingSystemAction.upsert(RewardType.COMMENT.toString(),RewardType.COMMENT.getName(),hashMap.get("COMMENT").toString());

            SettingSystemAction.upsert(PunishType.HIGHER_REJECT.toString(),PunishType.HIGHER_REJECT.getName(),hashMap.get("HIGHER_REJECT").toString());
            SettingSystemAction.upsert(PunishType.DEL.toString(),PunishType.DEL.getName(),hashMap.get("DEL").toString());
            SettingSystemAction.upsert(PunishType.COMPLAINT_REJECT.toString(),PunishType.COMPLAINT_REJECT.getName(),hashMap.get("COMPLAINT_REJECT").toString());
            SettingSystemAction.upsert(PunishType.COMMENT_DEL.toString(),PunishType.COMMENT_DEL.getName(),hashMap.get("COMMENT_DEL").toString());
            SettingSystemAction.upsert(PunishType.HIDDEN.toString(),PunishType.HIDDEN.getName(),hashMap.get("HIDDEN").toString());
            SettingSystemAction.upsert(PunishType.TIME_OUT.toString(),PunishType.TIME_OUT.getName(),hashMap.get("TIME_OUT").toString());
            SettingSystemAction.upsert("DEFAULT_AVATAR","默认头像地址",hashMap.get("DEFAULT_AVATAR").toString());

        return false;
    }

    @Override
    public Map<String,Object> getInfo(){

        SettingSystem topPic = Jdbc.build().query(SettingSystem.class).where("setting_key = ?","DEFAULT_AVATAR").first();
        return new HashMap(){{
            put(RewardType.SUBJECT_ACCEPT.toString(),RulePostAction.rulePostAcceptSubject());
            put(RewardType.SUBJECT_ACCEPT.toString(),RulePostAction.rulePostAcceptSubordinatw());
            put(RewardType.COMMENT,RulePostAction.rulePostComment());
            put(PunishType.COMMENT_DEL,RulePostAction.rulePostCommentDel());
            put(PunishType.COMPLAINT_REJECT,RulePostAction.rulePostComplaintReject());
            put(PunishType.DEL,RulePostAction.rulePostDel());
            put(PunishType.HIDDEN,RulePostAction.rulePostHidden());
            put(PunishType.HIGHER_REJECT,RulePostAction.rulePostHigherReject());
            put(PunishType.TIME_OUT,RulePostAction.rulePostTimeout());
            put("DEFAULT_AVATAR",topPic.getKeyValue());
        }};
    }
}
