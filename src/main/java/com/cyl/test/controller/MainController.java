package com.cyl.test.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cyl.test.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Controller
public class MainController {

    // post json object(bind to pojo object)
    // response json object
    @PostMapping("/json")
    @ResponseBody
    public JSONObject postBodyHandler(@RequestBody User user) {
        JSONObject jo = (JSONObject)JSON.toJSON(user);
        JSONObject res = new JSONObject();
        res.put("user", jo);
        System.out.println(res.toJSONString());
        return res;
    }

    // get/post parameters string
    // response json string
    // combine search parameters and form data
    // may drop value for same key(pick the first one)
    // support urlencoded content type
    @RequestMapping("/params")
    @ResponseBody
    public Object requestParamsHandler(@RequestParam Map params) {
        System.out.println(params.toString());
        params.put("modified", "params");
        return params;
    }

    // client side redirect
    // return 301/302 http response
    // todo how to redirect with origin params?
    @RequestMapping("/redirect")
    @ResponseBody
    public String redirectHandler(@RequestParam Map params, HttpServletResponse response) throws IOException {
        System.out.println(params.toString());
        if(params.containsKey("action") && "redirect".equals(params.get("action"))) {
            response.sendRedirect("params");
            return null;
        }
        // return value would be drop for Redirect in HttpServletResponse
        return null;
    }

    // server side forward
    // todo how to share model/session between controller/method?
    @RequestMapping("/forward")
    @ResponseBody
    public String forwardHandler(@RequestParam Map params, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println(params.toString());
        if(params.containsKey("action") && "forward".equals(params.get("action"))) {
//            response.sendRedirect("params");
            System.out.println("enter forward");
            request.getRequestDispatcher("params").forward(request, response);
            return null;
        }
        // return value would be drop for Redirect in HttpServletResponse
        return null;
    }

    // handle headers
    @RequestMapping("/header")
    @ResponseBody
    public String headerHandler(@RequestHeader Map headers) throws ServletException, IOException {
        System.out.println(headers.toString());
        return JSONObject.toJSONString(headers);
    }

    // handle cookies
    // @CookieValue can bind out the specific cookie
    // can fetch cookies from HttpServletRequest directly
    // the only way to set cookie to response is bind out the HttpServletResponse in method parameter
    @RequestMapping("/cookie")
    @ResponseBody
    public String cookieHandler(@CookieValue Cookie session, HttpServletRequest request,  HttpServletResponse response) throws ServletException, IOException {
        System.out.println(JSONObject.toJSONString(session));
        Cookie[] cookies = request.getCookies();
        for(Cookie cookie : cookies) {
            System.out.println(JSONObject.toJSONString(cookie));
        }
        Cookie resCookie = new Cookie("token", "123456");
        resCookie.setHttpOnly(true);
        response.addCookie(resCookie);
        return JSONObject.toJSONString(cookies);
    }

    // todo manage multipart
    @RequestMapping("/multi")
    @ResponseBody
    public String multiPartHandler() {
        return "";
    }



}
