package com.xinfang.meetingmodule.controller;

import com.alibaba.fastjson.JSON;

import java.util.List;
import java.util.Map;

/**
 * @author zemal-tan
 * @description
 * @create 2017-05-23 11:57
 **/
public class TestJson {
    public static void main(String args[]){
        System.out.println("=====");
        String typeB =  "[{\"dwName\":\"贵阳市直机关事务管理局\",\"dwId\":\"745\"},{\"dwName\":\"贵阳市民族宗教事务委员会\",\"dwId\":\"746\"}]";
        List<Map> typeBList = JSON.parseArray(typeB, Map.class);
        for (Map map: typeBList){
            System.out.print(map.get("dwName")+","+map.get("dwId"));
        }
    }
}
