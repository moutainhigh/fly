
package com.xinfang.service;

/**
 * Created by Lx on 7/6/17.
 */
import java.io.*;
import java.util.HashMap;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.stereotype.Service;

@Service("CaseDocDownloadService")
public class CaseDocDownloadService {
    private Configuration configuration = null;

    public CaseDocDownloadService(){
        configuration = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
        configuration.setDefaultEncoding("UTF-8");
    }
    public File createWord(Map<String,Object> dataMap) {


        try {
            //configuration.setClassForTemplateLoading(this.getClass(), "/");
            configuration.setDirectoryForTemplateLoading(new File("src/main/resources")); //FTL文件所存在文件夹
        } catch (IOException e) {
            System.out.print("error");
        }

        Template t = null;
        try {
            t = configuration.getTemplate("muban.ftl"); //模板文件文件名
        } catch (IOException e) {
            e.printStackTrace();
        }
        //File outFile = new File("Desktop/new/xinfangcompnew/src/main/resources/out.doc");//输出文件位置
        //File outFile = new File("case.doc");//输出文件位置
        File outFile = new File(System.getProperty("user.dir")+"/head.doc");//输出文件位置
        Writer out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile)));
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }

        try {
            t.process(dataMap, out);
        } catch (TemplateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outFile;
    }
}


