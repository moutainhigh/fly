package cn.jkm.core.domain.mysql;


import cn.jkm.core.domain.type.Status;
import cn.jkm.framework.core.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Configurable;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by werewolf on 2017/7/13.
 */
@MappedSuperclass
@Configurable(autowire = Autowire.BY_TYPE)
public class MySqlBasic<T> implements Serializable {

    @Id
    @Column(name = "id" ,length = 64)
    private String id;

    @Column(name = "create_at", columnDefinition = "BIGINT(20) COMMENT '创建时间'")
    private Long createAt = System.currentTimeMillis() / 1000;

    @Column(name = "update_at", columnDefinition = "BIGINT(20) COMMENT '修改时间'")
    private Long updateAt = System.currentTimeMillis() / 1000;

    @Column(name = "status", columnDefinition = "varchar(32) COMMENT '数据状态:DELETE,ACTIVE'")
    @Enumerated(EnumType.STRING)
    private Status status = Status.ACTIVE;

    public String uuid(){
        return  UUIDUtils.uuid();
    }

    public T setId(String id) {
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


    public String getId() {
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
