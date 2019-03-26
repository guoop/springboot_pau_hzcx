package com.soft.ware.rest.modular.auth.validator.impl;

import com.soft.ware.core.support.HttpKit;
import com.soft.ware.rest.modular.auth.validator.IReqValidator;
import com.soft.ware.rest.modular.auth.validator.dto.Credence;

import org.springframework.stereotype.Service;

/**
 * 直接验证账号密码是不是admin
 *
 * @author paulo
 * @date 2017-08-23 12:34
 */
@Service
public class SimpleValidator implements IReqValidator {

    private static String USER_NAME = "15639004097";

    private static String PASSWORD = "admin";

    @Override
    public boolean validate(Credence credence) {

        String userName = credence.getCredenceName();
        String password = credence.getCredenceCode();
        String phone = credence.getPhoneName();
        String user = (String) HttpKit.getRequest().getSession().getAttribute(phone);
        if(user != null && user.equals(password)){
        	return true;
        }else if(USER_NAME.equals(userName) && PASSWORD.equals(password)){
        	 return true;
        }else{
        	 return false;
        }
    }
}
