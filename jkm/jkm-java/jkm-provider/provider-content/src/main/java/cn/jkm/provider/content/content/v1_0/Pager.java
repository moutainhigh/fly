package cn.jkm.provider.content.content.v1_0;

import java.util.List;

/**
 * Created by zhengzb on 2017/7/24.
 * 分页封装对象
 */
public class Pager<T> {
    private Integer total;
    private List<T> rows;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
