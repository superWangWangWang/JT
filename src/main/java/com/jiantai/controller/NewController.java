package com.jiantai.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class NewController {
    @RequestMapping("login")
    public String login(){
        return "login";
    }
}
