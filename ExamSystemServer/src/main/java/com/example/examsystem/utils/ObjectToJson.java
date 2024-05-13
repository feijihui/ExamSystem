package com.example.examsystem.utils;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * 对象转json
 */
public class ObjectToJson {

    public static String objectToJson(Object o){

        // 创建一个ObjectMapper对象
        ObjectMapper objectMapper = new ObjectMapper();

        // 将一个对象转换为json字符串
        String value = null;
        try {
            value = objectMapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            System.out.println("将对象转换为 JSON 失败");
            e.printStackTrace();
        }
        return value;
    }
}
