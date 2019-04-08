package com.soft.ware.rest.modular.owner.controller;

import com.soft.ware.core.base.controller.BaseController;
import com.soft.ware.core.base.tips.Tip;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/owner/v1")
public class OwnerController extends BaseController {



     @RequestMapping("get-owner-info")
    public Tip getOwnerInfo(){

         return  null;
     }
}
