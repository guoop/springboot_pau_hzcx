package com.soft.ware.rest.modular.auth.util;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.Base64;

public class PasswordUtils {

    public static String encode(String phone,String password){
        password = "^a9682150f2e011e8uy572f1cf5acecff-" + phone + "-" + password + "$";
        password = Base64.getEncoder().encodeToString(DigestUtils.updateDigest(DigestUtils.getMd5Digest(), password).digest());
        return password;
    }

}
