package cn.jkm.core.domain.mongo;


import cn.jkm.core.domain.type.Status;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Configurable;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by werewolf on 2017/7/13.
 */

public class MongoBasic<T> implements Serializable {

    private Long id;
    private Long createAt = System.currentTimeMillis() / 1000;
    private Long updateAt = System.currentTimeMillis() / 1000;
    private Status status = Status.ACTIVE;


    public T setId(Long id) {
        this.id = id;
        return (T) this;
    }

    public T setCreateAt(Long createAt) {
        this.createAt = createAt;
        return (T) this;
    }

    public T setUpdateAt(Long updateAt) {
        this.updateAt = updateAt;
        return (T) this;
    }

    public T setStatus(Status status) {
        this.status = status;
        return (T) this;
    }


    public Long getId() {
        return id;
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
