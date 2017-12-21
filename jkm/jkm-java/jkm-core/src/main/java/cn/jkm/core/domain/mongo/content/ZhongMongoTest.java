package cn.jkm.core.domain.mongo.content;

/**
 * Created by zhong on 2017/7/18.
 */

import cn.jkm.framework.core.annotation.Document;

import javax.persistence.Transient;


@Document(name = "ZhongTest")
public class ZhongMongoTest {

    private String name;
    private int age;
    @Transient
    private String remark;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
