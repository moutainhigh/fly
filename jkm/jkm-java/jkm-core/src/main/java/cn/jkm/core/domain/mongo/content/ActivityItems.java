package cn.jkm.core.domain.mongo.content;

import cn.jkm.core.domain.mongo.MongoBasic;
import cn.jkm.core.domain.type.ActivityItemType;
import cn.jkm.core.domain.type.Required;
import cn.jkm.framework.core.annotation.Document;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * @author zhong
 * @version V1.0
 * @project jkm-root
 * @package cn.jkm.core.domain.mongo.content
 * @description //活动的定制表
 * @update 2017/7/19 15:11
 */
@Document(name = "activityItems")
public class ActivityItems extends MongoBasic<ActivityItems>{

    private String activityId;//活动id
    private String field;     //定制名称
    @Enumerated(EnumType.STRING)
    private ActivityItemType type;//定制菜单的类型
    @Enumerated(EnumType.STRING)
    private Required required;       //是否必选
    private String itemsJson;//保存每个菜单的子选项["男"，"女"],如果为但文本或者多行文本则为空

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public ActivityItemType getType() {
        return type;
    }

    public void setType(ActivityItemType type) {
        this.type = type;
    }

    public Required getRequired() {
        return required;
    }

    public void setRequired(Required required) {
        this.required = required;
    }

    public String getItemsJson() {
        return itemsJson;
    }

    public void setItemsJson(String itemsJson) {
        this.itemsJson = itemsJson;
    }

    @Override
    public String toString() {
        return "ActivityItems{" +
                "activityId='" + activityId + '\'' +
                ", field='" + field + '\'' +
                ", type=" + type +
                ", required=" + required +
                ", itemsJson='" + itemsJson + '\'' +
                '}';
    }
}
