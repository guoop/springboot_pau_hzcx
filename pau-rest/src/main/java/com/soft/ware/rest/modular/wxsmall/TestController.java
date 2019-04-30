package com.soft.ware.rest.modular.wxsmall;

import com.soft.ware.core.base.controller.BaseController;
import com.soft.ware.core.base.tips.Tip;
import com.soft.ware.rest.modular.auth.controller.dto.OrderDiffParam;
import com.soft.ware.rest.modular.auth.validator.Validator;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@RequestMapping(value = "/test")
public class TestController extends BaseController {

    @RequestMapping(value = "/xxxx")
    public Tip xx(@RequestBody @Valid OrderDiffParam param, BindingResult result){
        Validator.valid(result);
        return render(true).set("a", "b");
    }
}
