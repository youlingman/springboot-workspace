package com.cyl.test.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cyl.test.entity.User;
import com.cyl.test.repository.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * basic mybatis-plus & mysql
 */
@Controller
@RequestMapping("/mybatis-plus")
public class MybatisDataAccessController {

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
        return userMapper.selectOne(new QueryWrapper<User>().eq("id", id));
    }

    @ResponseBody
    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public Object postUserHandler(@RequestBody User user) {
        return userMapper.insert(user);
    }

    @ResponseBody
    @RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
    public Object putUserHandler(@PathVariable String id, @RequestBody User user) {
        return userMapper.update(user, new QueryWrapper<User>().eq("id", id));
    }

    @ResponseBody
    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    public Object deleteUserHandler(@PathVariable String id) {
        return userMapper.delete(new QueryWrapper<User>().eq("id", id));
    }
}
