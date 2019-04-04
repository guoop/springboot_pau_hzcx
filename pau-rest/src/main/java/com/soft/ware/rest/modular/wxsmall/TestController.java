package com.soft.ware.rest.modular.wxsmall;

import com.soft.ware.core.base.controller.BaseController;
import com.soft.ware.core.base.tips.Tip;
import com.soft.ware.core.util.Kv;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/test")
public class TestController extends BaseController {

    @RequestMapping(value = "/xxxx")
    public Tip xx(){
        return Kv.view(true).set("a", "b");
    }
}
