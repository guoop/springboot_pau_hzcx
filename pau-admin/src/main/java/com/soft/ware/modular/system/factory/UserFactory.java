package com.soft.ware.modular.system.factory;

import com.soft.ware.modular.system.transfer.UserDto;
import com.soft.ware.modular.system.model.User;
import org.springframework.beans.BeanUtils;

/**
 * 用户创建工厂
 */
public class UserFactory {

    public static User createUser(UserDto userDto){
        if(userDto == null){
            return null;
        }else{
            User user = new User();
            BeanUtils.copyProperties(userDto,user);
            return user;
        }
    }
}
