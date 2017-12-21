package cn.jkm.doc.controller;

import org.markdown4j.Markdown4jProcessor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import java.util.HashMap;

/**
 * Created by werewolf on 2017/7/25.
 */
@Controller
public class ApiController {

    @RequestMapping("/manage")
    public String manage() {
        return "manage";
    }

    @RequestMapping("/app")
    public String app() {
        return "app";
    }


    @RequestMapping("/manage/api")
    @ResponseBody
    public Object manage(@RequestParam(required = false) String service, @RequestParam(required = false) String version, Model model) {
        try {
            return new HashMap() {{
                put("status", 200);
                put("html", new Markdown4jProcessor().process(Application.manage.get(service+version+".md")));
            }};
        } catch (Exception e) {
        }
        return new HashMap() {{
            put("status", 200);
            put("html", "接口文档未定义");
        }};
    }

    @RequestMapping("/app/api")
    @ResponseBody
    public Object app(@RequestParam(required = false) String service, @RequestParam(required = false) String version, Model model) {
        try {
            return new HashMap() {{
                put("status", 200);
                put("html", new Markdown4jProcessor().process(Application.app.get(service+version+".md")));
            }};
        } catch (Exception e) {
        }
        return new HashMap() {{
            put("status", 200);
            put("html", "接口文档未定义");
        }};
    }
}
