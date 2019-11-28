package com.cyl.test.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cyl.test.entity.Car;
import com.cyl.test.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Map;

/**
 * 处理http请求基本场景
 */
@Controller
@RequestMapping("/")
public class MainController {

    // post json object(parse and bind to pojo object with @RequestBody)
    // response json
    // support json content type
    @PostMapping("/json")
    @ResponseBody
    public JSONObject postBodyHandler(@RequestBody User user) {
        JSONObject jo = (JSONObject) JSON.toJSON(user);
        JSONObject res = new JSONObject();
        res.put("user", jo);
        System.out.println(res.toJSONString());
        return res;
    }

    // get/post parameters string, parse and bind to a Map/MultiValueMap with @RequestParam
    // response json
    // combine search parameters and form data
    // may drop value for same key with Map(pick the first one)
    // support form data/urlencoded content type
    @RequestMapping("/params")
    @ResponseBody
    public Object requestParamsMapHandler(@RequestParam /*Map*/MultiValueMap<String, String> params, Model model) {
        System.out.println(params.toString());
        System.out.println(model.toString());
//        params.put("modified", "params");
        params.put("modified", new ArrayList<String>() {{
            add("params");
        }});
        return params;
    }

    // get/post parameters string, bind to pojo without @RequestParam
    // can bind out multiple pojo
    // support array value(same key with multiple value)
    // bind out multi value to non-list params(String), combine with comma split (like a=1,2,3)
    // response json
    // support form data/urlencoded content type
    @RequestMapping("/params-obj")
    @ResponseBody
    public Object requestParamsObjectHandler(User user, Car car, HttpServletRequest request) {
        JSONObject ujo = (JSONObject) JSON.toJSON(user);
        JSONObject cjo = (JSONObject) JSON.toJSON(car);
        JSONObject res = new JSONObject();
        res.put("user", ujo);
        res.put("car", cjo);
        res.put("modified", "params");
        // set session here for session part
        request.getSession().setAttribute("user", user);
        return res;
    }

    // client side redirect
    // return 301/302 http response
    @RequestMapping("/redirect")
    @ResponseBody
    public String redirectHandler(@RequestParam Map<String, String> params, HttpServletResponse response, RedirectAttributes redirectAttributes) throws IOException {
        System.out.println(params.toString());
        // not work for sendRedirect to HttpServletResponse
        redirectAttributes.addAllAttributes(params);
        redirectAttributes.addFlashAttribute("message", "redirect from /redirect");
        response.sendRedirect("params");
        // return value would be drop for Redirect in HttpServletResponse
        // update: redirect setting is prior to content response
        return null;
    }

    @RequestMapping("/redirect2")
    public String redirect2Handler(@RequestParam Map params, RedirectAttributes redirectAttributes) throws IOException {
        System.out.println(params.toString());
        // work in resolve redirect view
        redirectAttributes.addAllAttributes(params);
        redirectAttributes.addFlashAttribute("message", "redirect from /redirect");
        // string view name resolve
        return "redirect:/params";
    }

    // server side forward
    @RequestMapping("/forward")
    @ResponseBody
    public String forwardHandler(@RequestParam Map params, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println(params.toString());
        System.out.println("enter forward");
        // support relative or absolute path
        request.getRequestDispatcher("params").forward(request, response);
        // return value would be drop for setting forward path in RequestDispatcher
        // update: RequestDispatcher
        return null;
    }

    @RequestMapping("/forward2")
    public String forward2Handler(@RequestParam Map params, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println(params.toString());
        System.out.println("enter forward2");
        // string view name resolve
        return "forward:/params";
    }

    // handle headers
    @RequestMapping("/header")
    @ResponseBody
    public Object headerHandler(@RequestHeader Map headers) throws ServletException, IOException {
        System.out.println(headers.toString());
        return headers;
    }

    // handle PathVariable
    // parse out path variable
    @RequestMapping("/path/{name}/{id}")
    @ResponseBody
    public Object pathValueHandler(@PathVariable Map pathVariables) throws ServletException, IOException {
        System.out.println(pathVariables.toString());
        return pathVariables;
    }

    // handle cookies
    // @CookieValue can bind out the specific cookie
    // can fetch cookies from HttpServletRequest directly
    // the only way to set cookie to response is bind out the HttpServletResponse in method parameter
    @RequestMapping("/cookie")
    @ResponseBody
    public Object cookieHandler(@CookieValue Cookie session, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println(JSONObject.toJSONString(session));
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            System.out.println(JSONObject.toJSONString(cookie));
        }
        Cookie resCookie = new Cookie("token", "123456");
        resCookie.setHttpOnly(true);
        response.addCookie(resCookie);
        return cookies;
    }

    // session handle
    // only for single servlet container
    // need external storage to support distribute session
    @RequestMapping("/session")
    @ResponseBody
    public Object sessionHandler(@SessionAttribute User user, HttpServletRequest request) throws ServletException, IOException {
        // may throw exception if session not exist
        System.out.println(user);
        // get session from request may be better
        System.out.println(JSONObject.toJSONString(request.getSession()));
        return user;
    }

    // handle multipart file
    @RequestMapping("/file")
    @ResponseBody
    public Object multiPartHandler(@RequestParam("file") MultipartFile file) throws IOException {
        System.out.println(String.format("get file [%s] with size [%s]", file.getOriginalFilename(), file.getSize()));
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        InputStream inputStream = file.getInputStream();
        while ((length = inputStream.read(buffer)) != -1) {
            result.write(buffer, 0, length);
        }
        return result.toString("UTF-8");
    }
}
