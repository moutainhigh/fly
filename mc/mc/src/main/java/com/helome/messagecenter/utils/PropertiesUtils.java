package com.helome.messagecenter.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Properties;

public class PropertiesUtils {
    private static Logger Log = LoggerFactory.getLogger(PropertiesUtils.class);
	private final static String UNIX_CFG_PATH = "/dev/application/config.properties";
	private final static String WIN_CFG_PATH = "C:\\workspace\\MessageCenter\\src\\com\\helome\\messagecenter\\config.properties";
    private final static String CONTEXT_CFG_PATH = "/config.properties";

    private static Properties props = new Properties();
	static{
        InputStream is = null;
        try{
            String fs = System.getProperty("file.separator");
            String cfgPath =  System.getProperty("mc.config", fs.equals("/") ? UNIX_CFG_PATH : WIN_CFG_PATH);
            try {
                is = new BufferedInputStream (new FileInputStream(cfgPath));
                Log.info("loading Configuration from " + cfgPath);
            } catch (FileNotFoundException e) {
                is =  PropertiesUtils.class.getResourceAsStream(CONTEXT_CFG_PATH);
                Log.info("loading configuration from " + PropertiesUtils.class.getResource(CONTEXT_CFG_PATH).toString().substring(6));
            }
            props.load(is);
            Log.info("loaded configuration");
        }catch(Exception ex){
            Log.error("Can't load configuration", ex);
        }finally {
            if(is != null){
                try {
                    is.close();
                } catch (IOException e) {
                }
            }
        }
	}
	public static String readValue(String key) {
		         String value = props.getProperty(key);
		            return value;
	}
}
