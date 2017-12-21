package cn.jkm.doc.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by werewolf on 2017/7/25.
 */
@SpringBootApplication
public class Application {

    public static final Map<String, File> manage = new HashMap();
    public static final Map<String, File> app = new HashMap();

    public static void main(String[] args) {
        try {
            File fileApp = ResourceUtils.getFile("classpath:doc"+File.separator+"app");
            File fileMange = ResourceUtils.getFile("classpath:doc"+File.separator+"manage");
            initFileAPP(fileApp);
            initFileMange(fileMange);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        SpringApplication.run(Application.class, args);
    }

    private static void initFileAPP(File f) {

        if (f != null) {
            if (f.isDirectory()) {
                File[] fileArray = f.listFiles();
                if (fileArray != null) {
                    for (int i = 0; i < fileArray.length; i++) {
                        initFileAPP(fileArray[i]);
                    }
                }
            } else {
                System.out.println(f.getName());
                app.put(f.getName(), f);
            }
        }
    }

    private static void initFileMange(File f) {

        if (f != null) {
            if (f.isDirectory()) {
                File[] fileArray = f.listFiles();
                if (fileArray != null) {
                    for (int i = 0; i < fileArray.length; i++) {
                        initFileMange(fileArray[i]);
                    }
                }
            } else {
                System.out.println(f.getName());
                manage.put(f.getName(), f);
            }
        }
    }

}
