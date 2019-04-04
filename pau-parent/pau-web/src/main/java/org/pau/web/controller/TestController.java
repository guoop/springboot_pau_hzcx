package org.pau.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {


    @GetMapping({"/","/index","/beetl"})
    public String test(Model model){
    	model.addAttribute("beetl","测试一下通过模板引擎传递参数！");
		return "index";	
		}
	
	
}
