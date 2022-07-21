package com.servlet.admin;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class HttpGetJson {
    /**
     * 获取json数据
     * @param request
     * @return
     */

    public static JSONObject getJson(HttpServletRequest request){
        String result = "";
        BufferedReader in = null;
        try {
            in= new BufferedReader(new InputStreamReader(
            request.getInputStream(),"utf-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return (JSONObject) JSONObject.parse(result);
    }
}