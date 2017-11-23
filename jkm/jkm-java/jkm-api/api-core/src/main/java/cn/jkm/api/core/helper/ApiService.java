package cn.jkm.api.core.helper;


import java.util.Map;

/**
 * Created by werewolf on 2017/7/15.
 */
public interface ApiService<K extends Map> {
    K service(Map request);
}

