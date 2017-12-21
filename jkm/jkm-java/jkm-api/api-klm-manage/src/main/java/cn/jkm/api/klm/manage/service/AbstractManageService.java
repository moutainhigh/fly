package cn.jkm.api.klm.manage.service;

import cn.jkm.api.core.helper.ApiService;

import java.util.Map;

/**
 * Created by werewolf on 2017/7/16.
 */
public abstract class AbstractManageService implements ApiService {

    public String formatString(Object obj) {
        if (null == obj) {
            return null;
        }
        return String.valueOf(obj);
    }

    public Long formatLong(Object obj) {
        if (null == obj) {
            return null;
        }
        return Long.valueOf(obj.toString());
    }

    public Integer formatInteger(Object obj) {
        if (null == obj) {
            return null;
        }
        return Integer.valueOf(obj.toString());
    }

    public int page(Map request) {
        Object page = request.get("page");
        if (null == page) {
            return 1;
        }
        try {
            return Integer.valueOf(page.toString());
        } catch (Exception ex) {
        }
        return 1;

    }

    public int limit(Map request) {
        Object limit = request.get("limit");
        if (null == limit) {
            return 10;
        }
        try {
            return Integer.valueOf(limit.toString());
        } catch (Exception ex) {
        }
        return 10;

    }
}
