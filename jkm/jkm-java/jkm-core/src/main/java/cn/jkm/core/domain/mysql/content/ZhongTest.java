package cn.jkm.core.domain.mysql.content;

import cn.jkm.core.domain.mysql.MySqlBasic;
import cn.jkm.core.domain.type.ContentStatus;

import javax.persistence.*;

/**
 * Created by zhong on 2017/7/18.
 */

@Entity
@Table(name = "zhong_test", catalog = "jkm")
public class ZhongTest extends MySqlBasic<ZhongTest>{
    @Column(name = "name", length = 64)
    private String name;
    @Column(name = "content_status",length = 32)
    @Enumerated(EnumType.STRING)
    private ContentStatus contentStatus;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ContentStatus getContentStatus() {
        return contentStatus;
    }

    public void setContentStatus(ContentStatus contentStatus) {
        this.contentStatus = contentStatus;
    }
}
