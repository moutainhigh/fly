package cn.jkm.provider.content.action;

import cn.jkm.core.domain.mysql.content.RulePunish;
import cn.jkm.core.domain.mysql.content.RuleReward;
import cn.jkm.core.domain.mysql.content.SettingSystem;
import cn.jkm.core.domain.type.PunishType;
import cn.jkm.core.domain.type.RewardType;
import cn.jkm.framework.mysql.Jdbc;
import com.alibaba.dubbo.common.json.JSON;
import com.alibaba.dubbo.common.json.ParseException;

/**
 * @author zhong
 * @version V1.0
 * @project jkm-root
 * @package cn.jkm.provider.content.action
 * @description //积分的发帖规则
 * @update 2017/7/26 9:32
 */
public class RulePostAction {

    //1、主题帖审核通过
    public static RuleReward rulePostAcceptSubject(){

        SettingSystem settingSystem = Jdbc.build()
                .query(SettingSystem.class)
                .where("setting_key = ?", RewardType.SUBJECT_ACCEPT.toString()).first();
        if(settingSystem!=null){
            String value = settingSystem.getKeyValue();
            //String value = "{'score':2,'type':CONSUMER_POINTS,'dayLimit':200,'totalLimit':2000}";
            return parseRuleReward(value, RewardType.SUBJECT_ACCEPT);
        }
        return null;
    }
    //2、审核通过下级主题帖
    public static RuleReward rulePostAcceptSubordinatw(){

        SettingSystem settingSystem = Jdbc.build()
                .query(SettingSystem.class)
                .where("setting_key = ?", RewardType.ACCEPT_SUBORDINATW.toString()).first();
        if(settingSystem!=null){
            String value = settingSystem.getKeyValue();
            return parseRuleReward(value, RewardType.ACCEPT_SUBORDINATW);
        }
        return null;
    }
    //3、发表评论
    public static RuleReward rulePostComment(){

        SettingSystem settingSystem = Jdbc.build()
                .query(SettingSystem.class)
                .where("setting_key = ?", RewardType.COMMENT.toString()).first();
        if(settingSystem!=null){
            String value = settingSystem.getKeyValue();
            return parseRuleReward(value, RewardType.COMMENT);
        }
        return null;
    }

    //4、主题帖被上级打回

    public static RulePunish rulePostHigherReject(){

        SettingSystem settingSystem = Jdbc.build()
                .query(SettingSystem.class)
                .where("setting_key = ?", PunishType.HIGHER_REJECT.toString()).first();
        if(settingSystem!=null){
            String value = settingSystem.getKeyValue();
            return parseRulePunish(value, PunishType.HIGHER_REJECT);
        }
        return null;
    }

    //5、主题帖被投诉打回
    public static RulePunish rulePostComplaintReject(){

        SettingSystem settingSystem = Jdbc.build()
                .query(SettingSystem.class)
                .where("setting_key = ?", PunishType.COMPLAINT_REJECT.toString()).first();
        if(settingSystem!=null){
            String value = settingSystem.getKeyValue();
            return parseRulePunish(value, PunishType.COMPLAINT_REJECT);
        }
        return null;
    }

    //6、主题帖超时未审核

    public static RulePunish rulePostTimeout(){

        SettingSystem settingSystem = Jdbc.build()
                .query(SettingSystem.class)
                .where("setting_key = ?", PunishType.TIME_OUT.toString()).first();
        if(settingSystem!=null){
            String value = settingSystem.getKeyValue();
            return parseRulePunish(value, PunishType.TIME_OUT);
        }
        return null;
    }

    //7、主题帖被删
    public static RulePunish rulePostDel(){

        SettingSystem settingSystem = Jdbc.build()
                .query(SettingSystem.class)
                .where("setting_key = ?", PunishType.DEL.toString()).first();
        if(settingSystem!=null){
            String value = settingSystem.getKeyValue();
            return parseRulePunish(value, PunishType.DEL);
        }
        return null;
    }

    //8、主题帖被隐藏

    public static RulePunish rulePostHidden(){

        SettingSystem settingSystem = Jdbc.build()
                .query(SettingSystem.class)
                .where("setting_key = ?", PunishType.HIDDEN.toString()).first();
        if(settingSystem!=null){
            String value = settingSystem.getKeyValue();
            return parseRulePunish(value, PunishType.HIDDEN);
        }
        return null;
    }

    //9、评论被删
    public static RulePunish rulePostCommentDel(){

        SettingSystem settingSystem = Jdbc.build()
                .query(SettingSystem.class)
                .where("setting_key = ?", PunishType.COMMENT_DEL.toString()).first();
        if(settingSystem!=null){
            String value = settingSystem.getKeyValue();
            return parseRulePunish(value, PunishType.COMMENT_DEL);
        }
        return null;
    }

    public static RuleReward parseRuleReward(String json,RewardType rewardType){
        try {
            RuleReward ruleReward = JSON.parse(json,RuleReward.class);
            ruleReward.setRewardType(rewardType);
            return ruleReward;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static RulePunish parseRulePunish(String json,PunishType punishType){
        try {
            RulePunish rulePunish = JSON.parse(json,RulePunish.class);
            rulePunish.setPunishType(punishType);
            return rulePunish;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
