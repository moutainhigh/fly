package cn.jkm.core.domain.mongo;


import cn.jkm.core.domain.type.Status;

import java.io.Serializable;

/**
 * Created by werewolf on 2017/7/13.
 */

public class MongoBasic<T> implements Serializable {

    private String id;
    private Long createAt = System.currentTimeMillis() / 1000;
    private Long updateAt = System.currentTimeMillis() / 1000;
    private Status status = Status.ACTIVE;


    public void setId(String id) {
        this.id = id;
    }

    public void setCreateAt(Long createAt) {
        this.createAt = createAt;
    }

    public void setUpdateAt(Long updateAt) {
        this.updateAt = updateAt;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getId() {
        return this.id;
    }

    public Long getCreateAt() {
        return createAt;
    }

    public Long getUpdateAt() {
        return updateAt;
    }

    public Status getStatus() {
        return status;
    }

}
