package com.soft.ware.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestController {

    @RequestMapping("/")
    public String test(Model model){
        model.addAttribute("beetl","测试beetl");
        return  "index";
    }



}
