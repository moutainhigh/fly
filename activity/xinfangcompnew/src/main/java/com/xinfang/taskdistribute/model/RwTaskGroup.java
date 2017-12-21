package com.xinfang.taskdistribute.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author zemal-tan
 * @description
 * @create 2017-06-01 15:03
 **/
@Entity
@Table(name = "rw_task_group")
public class RwTaskGroup {
    private int groupId;
    private String groupName;
    private String groupDescribe;
    private Byte isFlag;

    private Set<RwTaskPersonGroup> rwTaskPersonGroupSet = new HashSet<>();

    @Id
    @GeneratedValue
    @Column(name = "group_id", nullable = false)
    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    @Basic
    @Column(name = "group_name", nullable = true, length = 50)
    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @Basic
    @Column(name = "group_describe", nullable = true, length = 1000)
    public String getGroupDescribe() {
        return groupDescribe;
    }

    public void setGroupDescribe(String groupDescribe) {
        this.groupDescribe = groupDescribe;
    }

    @Basic
    @Column(name = "is_flag", nullable = true)
    public Byte getIsFlag() {
        return isFlag;
    }

    public void setIsFlag(Byte isFlag) {
        this.isFlag = isFlag;
    }

//    @OneToMany(cascade={CascadeType.ALL})
//    @JoinColumn(name = "group_id", referencedColumnName = "group_id")

    @OneToMany(cascade=CascadeType.DETACH,fetch=FetchType.LAZY)//级联保存、更新、删除、刷新;延迟加载
    @JoinColumn(name = "group_id", referencedColumnName = "group_id", updatable = false, insertable = false,
            foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))
    public Set<RwTaskPersonGroup> getRwTaskPersonGroupSet() {
        return rwTaskPersonGroupSet;
    }

    public void setRwTaskPersonGroupSet(Set<RwTaskPersonGroup> rwTaskPersonGroupSet) {
        this.rwTaskPersonGroupSet = rwTaskPersonGroupSet;
    }
}
