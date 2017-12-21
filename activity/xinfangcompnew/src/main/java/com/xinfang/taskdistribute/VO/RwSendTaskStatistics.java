package com.xinfang.taskdistribute.VO;

/**
 * @author zemal-tan
 * @description  统计： 发送的任务统计
 * @create 2017-06-06 21:22
 **/

import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Immutable
@Subselect("SELECT\n" +
        "\tCOUNT(t.create_person_id) create_person_count,\n" +
        "\tCOUNT(tp.task_flag <> 0 OR NULL) reply_count\n" +
        "FROM\n" +
        "\t`rw_task` t\n" +
        "LEFT JOIN rw_task_person tp ON tp.task_id = t.task_id")
public class RwSendTaskStatistics {

    @Id
    @Column(name = "create_person_count")
    private Integer createPersonCount; // 发送的任务

    @Column(name = "reply_count")
    private Integer replyCount; // 收到的回复

    public Integer getCreatePersonCount() {
        return createPersonCount;
    }

    public void setCreatePersonCount(Integer createPersonCount) {
        this.createPersonCount = createPersonCount;
    }

    public Integer getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(Integer replyCount) {
        this.replyCount = replyCount;
    }
}
