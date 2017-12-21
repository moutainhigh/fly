package cn.jkm.provider.user;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by werewolf on 2016/11/17.
 */
public class Main {
    private static Logger log = LoggerFactory.getLogger("user-provider");

    public static void main(String[] args) {
        com.alibaba.dubbo.container.Main.main(args);
    }


}
