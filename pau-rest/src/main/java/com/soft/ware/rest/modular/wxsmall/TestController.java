package com.soft.ware.rest.modular.wxsmall;

import com.soft.ware.core.base.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@RequestMapping(value = "/test")
public class TestController extends BaseController {

    @RequestMapping(value = "/xxx")
    public String xx(){
        return "xx";
    }
}
