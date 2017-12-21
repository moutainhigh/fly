package com.xinfang.controller.temp;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xinfang.dao.FdCodeRepository;
import com.xinfang.log.ApiLog;
import com.xinfang.model.FdCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zemal-tan
 * @description
 * @create 2017-03-23 9:23
 **/
@Service
public class JsonConvert {

    @Autowired
    FdCodeRepository fdCodeRepository;

    @Transactional
    public List<FdCode> inputFdCode(){
        // 读取原始json文件并进行操作和输出
        try {
            BufferedReader br = new BufferedReader(new FileReader(
                    "src/main/java/com/xinfang/controller/temp/fdcode.json"));// 读取原始json文件
            BufferedWriter bw = new BufferedWriter(new FileWriter(
                    "src/main/java/com/xinfang/controller/temp/fdcode_result.json"));// 输出新的json文件
            String jsonStr = null, ws = null;
            StringBuilder stringBuilder = new StringBuilder();
            while ((jsonStr = br.readLine()) != null) {
                System.out.println(jsonStr);
                stringBuilder.append(jsonStr);
            }
            int codeType = 5;
            JSONArray jsonArray= JSON.parseArray(stringBuilder.toString());

            List<FdCode> fdCodeList = new ArrayList<>();

            // 获取fdCodeList
            for (int i=0; i<jsonArray.size(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                FdCode fdCode = new FdCode();
                fdCode.setCode(500+i);
                fdCode.setParentCode(-1);
                fdCode.setName(jsonObject.get("name").toString());
                fdCode.setType(codeType);
                fdCode.setState(1);
                fdCodeList.add(fdCode);
                // 第二层
                if (jsonObject.get("children")!= null &&
                        JSON.parseArray(jsonObject.get("children").toString()).size()>0){
                    JSONArray jsonArrayChildren = JSON.parseArray(jsonObject.get("children").toString());
                    for (int j=0; j<jsonArrayChildren.size(); j++) {
                        JSONObject jsonObjectChildren = jsonArrayChildren.getJSONObject(j);
                        FdCode fdCodeChildren = new FdCode();
                        fdCodeChildren.setCode((500+i)*100 + j);
                        fdCodeChildren.setParentCode(fdCode.getCode());
                        fdCodeChildren.setName(jsonObjectChildren.get("name").toString());
                        fdCodeChildren.setType(codeType);
                        fdCodeChildren.setState(1);
                        fdCodeList.add(fdCodeChildren);
                        // 第三层
                        if (jsonObjectChildren.get("children")!= null &&
                                JSON.parseArray(jsonObjectChildren.get("children").toString()).size()>0){
                            JSONArray jsonArrayGrandson = JSON.parseArray(jsonObjectChildren.get("children").toString());
                            for (int k=0; k<jsonArrayGrandson.size(); k++) {
                                JSONObject jsonObjectGrandson = jsonArrayGrandson.getJSONObject(k);
                                FdCode fdCodeGrandson = new FdCode();
                                fdCodeGrandson.setCode(((500+i)*100+j)*100 + k);
                                fdCodeGrandson.setParentCode(fdCodeChildren.getCode());
                                fdCodeGrandson.setName(jsonObjectGrandson.get("name").toString());
                                fdCodeGrandson.setType(codeType);
                                fdCodeGrandson.setState(1);
                                fdCodeList.add(fdCodeGrandson);
                            }
                        }
                    }
                }
            }
            try {
                bw.write("[");
                for (FdCode fdCode: fdCodeList){
                    bw.write(JSON.toJSONString(fdCode));
                    bw.write(",");
                    fdCodeRepository.save(fdCode);
                }
                bw.write("]");
//                fdCodeRepository.save(fdCodeList);
            }catch (Exception e){
                e.printStackTrace();
                ApiLog.log(e.toString());
            }

            bw.flush();
            br.close();
            bw.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args){

    }
}
