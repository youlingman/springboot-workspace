package com.cyl.test.controller;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class ExceptionController {

    // we can handle all exception in this handler
    @ResponseBody
    @ExceptionHandler
    Object exceptionHandler(Exception e) {
        log.warn("[caught Exception]");
        log.warn(e.toString());
        JSONObject res = new JSONObject();
        res.put("code", "500");
        res.put("message", "内部异常");
        return res;
    }
}
