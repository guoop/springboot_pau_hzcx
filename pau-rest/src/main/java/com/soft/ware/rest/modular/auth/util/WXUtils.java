package com.soft.ware.rest.modular.auth.util;

import com.soft.ware.core.util.DateUtil;
import com.soft.ware.core.util.MD5Util;
import com.soft.ware.core.util.ToolUtil;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

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


	/**
	 * 根据url获取oen
	 * @param request
	 * @return
	 */
	public static String getAppId(HttpServletRequest request){
		String referer = request.getHeader("Referer");
		String[] split = referer.split("/");
		String domain = split[2];
		String appId = split[3];
		return appId;
	}


	public static void main(String[] args) {
		System.out.println();
	}

}
