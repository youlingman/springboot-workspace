package com.cyl.test.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionController {

    // we can handle all exception in this handler
    @ResponseBody
    @ExceptionHandler
    Object exceptionHandler(Exception e) {
        System.out.println("[caught Exception]");
        System.out.println(e.toString());
        JSONObject res = new JSONObject();
        res.put("code", "500");
        res.put("message", "内部异常");
        return res;
    }
}
