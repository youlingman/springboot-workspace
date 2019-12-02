package com.cyl.test.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cyl.test.entity.User;
import com.cyl.test.repository.CarRepository;
import com.cyl.test.repository.UserMapper;
import com.cyl.test.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * basic jpa & mysql
 */
@Controller
@RequestMapping("/jpa")
public class JPADataAccessController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CarRepository carRepository;

    @ResponseBody
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public Object usersHandler() {
        return userRepository.findAll();
    }

    @ResponseBody
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public Object getUserHandler(@PathVariable Integer id) {
        return userRepository.getOne(id);
    }

    @ResponseBody
    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public Object postUserHandler(@RequestBody User user) {
        return userRepository.save(user);
    }

    @ResponseBody
    @RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
    public Object putUserHandler(@PathVariable String id, @RequestBody User user) {
        return userRepository.save(user);
    }

    @ResponseBody
    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    public Object deleteUserHandler(@PathVariable Integer id) {
        userRepository.deleteById(id);
        return "delete success";
    }
}
