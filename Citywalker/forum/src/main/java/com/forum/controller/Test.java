package com.forum.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        List<String> label = new ArrayList<>();
        label.add("ecnu");
        label.add("man");
        String result = JSON.toJSONString(label);
        System.out.println(result);
    }
}
