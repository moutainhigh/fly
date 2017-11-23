package cn.jkm.service.core.domain;

import java.io.Serializable;
import java.util.List;

/**
 * Created by werewolf on 2017/7/24.
 */
public class ListResult<T> implements Serializable{

    private Long count;
    private List<T> list;

    public ListResult(long count, List<T> list) {
        this.count = count;
        this.list = list;
    }

    public ListResult() {
    }

    public ListResult(List<T> list) {
        this.count = 0L;
        this.list = list;
    }

    public ListResult setCount(long count) {
        this.count = count;
        return this;
    }

    public ListResult setList(List<T> list) {
        this.list = list;
        return this;
    }

    public long getCount() {
        return count;
    }

    public List<T> getList() {
        return list;
    }
}
