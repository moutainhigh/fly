package cn.jkm.provider.sys;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.crypto.dsig.DigestMethod;

/**
 * Created by werewolf on 2016/11/17.
 */
public class Main {
    private static Logger log = LoggerFactory.getLogger("user-provider");

    public static void main(String[] args) {
        com.alibaba.dubbo.container.Main.main(args);
    }


}
