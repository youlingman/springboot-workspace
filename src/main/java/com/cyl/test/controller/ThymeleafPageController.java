package com.cyl.test.controller;

import com.cyl.test.entity.User;
import com.cyl.test.repository.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Slf4j
@RequestMapping("/thymeleaf")
public class ThymeleafPageController {

    @Autowired
    private UserMapper userMapper;

    @GetMapping
    public String get(@ModelAttribute("user") User user) {
        log.info(user.toString());
        return "index";
    }

    @PostMapping
    public String postUser(@ModelAttribute("user") User user, RedirectAttributes redirectAttributes) {
        log.info(user.toString());
        try {
            userMapper.insert(user);
        } catch (Exception e) {
            e.printStackTrace();
            // todo show some error message
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/thymeleaf";
        }
        // todo show some success message, share model attribute with redirect
        redirectAttributes.addFlashAttribute("message", "success add user " + user.getName() + "!");
        return "redirect:/thymeleaf";
    }
}
