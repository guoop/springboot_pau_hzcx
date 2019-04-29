package com.soft.ware.rest.modular.auth.util;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.Base64;

public class PasswordUtils {

    public static String encode(String phone,String password){
        password = "^a9682150f2e011e8uy572f1cf5acecff-" + phone + "-" + password + "$";
        password = Base64.getEncoder().encodeToString(DigestUtils.updateDigest(DigestUtils.getMd5Digest(), password).digest());
        return password;
    }

    public static void main(String[] args) {
        System.out.println(PasswordUtils.encode("15136757969","123456"));
        System.out.println(PasswordUtils.encode("17610831883","123456"));

        String ss = ",749,11224315792076554251556443516a4jquw,750,11224320058061209621556443618z5kmcj,746,112243927370030284915564453514wsnvg,748,11224402330173562891556445580ahwdfn,765,951389ae-5b56-11e9-b358-00ff81941ac9,95225d77-5b56-11e9-b358-00ff81941ac9,764,745,94f4cb9a-5b56-11e9-b358-00ff81941ac9,739,738,94e9db19-5b56-11e9-b358-00ff81941ac9,760,95f594e1-5b56-11e9-b358-00ff81941ac9,761,95e44e79-5b56-11e9-b358-00ff81941ac9,758,94e05ffb-5b56-11e9-b358-00ff81941ac9,737,754,94b4a11c-5b56-11e9-b358-00ff81941ac9,741,94a6aa15-5b56-11e9-b358-00ff81941ac9,759,95ab8b53-5b56-11e9-b358-00ff81941ac9,757,95bbe0d3-5b56-11e9-b358-00ff81941ac9,755,958bbe23-5b56-11e9-b358-00ff81941ac9,744,949a612e-5b56-11e9-b358-00ff81941ac9,736,954e5c11-5b56-11e9-b358-00ff81941ac9,762,94c06ccd-5b56-11e9-b358-00ff81941ac9,763,959d778d-5b56-11e9-b358-00ff81941ac9,752,957bd851-5b56-11e9-b358-00ff81941ac9,743,955a1ea3-5b56-11e9-b358-00ff81941ac9,753,94d6df0a-5b56-11e9-b358-00ff81941ac9,756,948b6de5-5b56-11e9-b358-00ff81941ac9,xx,733,734,95d47b26-5b56-11e9-b358-00ff81941ac9,735,94cafe78-5b56-11e9-b358-00ff81941ac9,795,9532a131-5b56-11e9-b358-00ff81941ac9,95419df7-5b56-11e9-b358-00ff81941ac9,95692d68-5b56-11e9-b358-00ff81941ac9,9465f38c-5b56-11e9-b358-00ff81941ac9,9471da3e-5b56-11e9-b358-00ff81941ac9,947c4272-5b56-11e9-b358-00ff81941ac9,";
        System.out.println(ss.replaceAll("," + 750, ""));
    }

}
