package com.cyl.test.controller;

import com.cyl.test.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * model层访问场景
 */
@Controller
@RequestMapping("/mybatis-plus")
public class DataAccessController {

    @Autowired
    private UserMapper userMapper;

    @ResponseBody
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public Object usersHandler() {
        return userMapper.selectList(null);
    }

    @ResponseBody
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public Object getUserHandler(@PathVariable String id) {
//        return userMapper.selectList(new q);
        return id;
    }

}
