package com.cyl.test.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@Slf4j
public class RedisDataAccessController {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @ResponseBody
    @RequestMapping(value = "/keys", method = RequestMethod.GET)
    public Object keysHandler(String patten) {
        log.info("get keys");
        return redisTemplate.keys("*");
    }

    @ResponseBody
    @RequestMapping(value = "/key/{id}", method = RequestMethod.GET)
    public Object getKeyHandler(@PathVariable String id) {
        log.info("get key " + id);
        log.info("" + redisTemplate.opsForValue().get(id));
        return redisTemplate.opsForValue().get(id);
    }

    @ResponseBody
    @RequestMapping(value = "/key", method = RequestMethod.POST)
    public Object postKeyHandler(@RequestBody String value) {
        log.info("post value" + value);
        redisTemplate.opsForValue().set(UUID.randomUUID().toString(), value);
        return null;
    }

    @ResponseBody
    @RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
    public Object putKeyHandler(@PathVariable String id, @RequestBody String value) {
        log.info("put key " + id);
        redisTemplate.opsForValue().set(id, value);
        return null;
    }

    @ResponseBody
    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    public Object deleteKeyHandler(@PathVariable String id) {
        log.info("delete key " + id);
        return redisTemplate.delete(id);
    }
}
