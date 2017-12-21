package cn.jkm.provider.eb;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.DigestUtils;

/**
 * Created by werewolf on 2016/11/17.
 */
public class Main {
    private static Logger log = LoggerFactory.getLogger("ed-provider");

    public static void main(String[] args) {
    	log.info("------------ed-provider stard-------------");
        com.alibaba.dubbo.container.Main.main(args);
    }


}
