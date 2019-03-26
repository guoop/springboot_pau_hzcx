package com.soft.ware.rest.modular.auth.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.soft.ware.core.util.DateUtil;
import com.soft.ware.core.util.MD5Util;
import com.soft.ware.core.util.ToolUtil;

public class WXUtils {
	
	public static Map<String,Object> getPayLoad(){
		Map<String,Object> resultMap = new HashMap<String, Object>();
		try {
			resultMap.put("appkey", WXContants.JG_APPKEY);
			resultMap.put("random_str", ToolUtil.getRandomString(36));
			resultMap.put("timestamp", DateUtil.dateToStamp(DateUtil.getTime()));
			//signature = md5(appkey={appkey}&timestamp={timestamp}&random_str={random_str}&key={secret})
			resultMap.put("signature", MD5Util.encrypt("appkey="+WXContants.JG_APPKEY+"&timestamp="+resultMap.get("timestamp")+"&random_str="+resultMap.get("random_str")+"&key="+WXContants.JG_MASTER_SECRET));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		return resultMap;
	}
	
	public static void main(String[] args) {
		System.out.println();
	}

}
