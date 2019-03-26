package com.soft.ware.rest.modular.auth.controller;

import com.soft.ware.core.base.controller.BaseController;
import com.soft.ware.rest.common.persistence.model.TblOwner;
import com.soft.ware.rest.common.persistence.model.TblOwnerStaff;
import com.soft.ware.rest.modular.auth.service.AuthService;
import com.soft.ware.rest.modular.auth.service.TblOwnerService;
import com.soft.ware.rest.modular.auth.wrapper.AuthWrapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.soft.ware.core.exception.PauException;
import com.soft.ware.core.support.HttpKit;
import com.soft.ware.core.util.DateUtil;
import com.soft.ware.core.util.ToolUtil;
import com.soft.ware.rest.common.exception.BizExceptionEnum;
import com.soft.ware.rest.config.properties.JwtProperties;
import com.soft.ware.rest.modular.auth.controller.dto.AuthRequest;
import com.soft.ware.rest.modular.auth.controller.dto.AuthResponse;
import com.soft.ware.rest.modular.auth.util.JwtTokenUtil;
import com.soft.ware.rest.modular.auth.util.WXUtils;
import com.soft.ware.rest.modular.auth.validator.IReqValidator;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.Map;

/**
 * 请求验证的
 *
 * @author paulo
 * @Date 2017/8/24 14:22
 */
@RestController
public class AuthController extends BaseController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Resource(name = "simpleValidator")
    private IReqValidator reqValidator;

    @Autowired
    private AuthService authService;

    @Autowired
    private TblOwnerService ownerService;
    
    @Autowired
    private JwtProperties jwtProperties;

    @RequestMapping(value = "${jwt.auth-path}")
    public Object createAuthenticationToken(AuthRequest authRequest) {
    	 boolean validate = false;
        if(ToolUtil.isNotEmpty(authRequest.getPhone())){
        	validate = reqValidator.validate(authRequest);
        }else{
        	validate = reqValidator.validate(authRequest);
        }
        if (validate) {
            final String randomKey = jwtTokenUtil.getRandomKey();
            final String token = jwtTokenUtil.generateToken(authRequest.getUserName(), randomKey);
            //authRequest.setPhone("15136757969");
            return returnTokenParams(authRequest,token,randomKey);
        }else{
        	throw new PauException(BizExceptionEnum.AUTH_REQUEST_ERROR);
        }
    }

    protected Object returnTokenParams(AuthRequest authRequest,String token,String randomKey) {
    	if(ToolUtil.isNotEmpty(authRequest.getPhone())){
    		TblOwnerStaff user = authService.findByUsername(authRequest.getPhone());
    		if(ToolUtil.isEmpty(user)){
            	throw new PauException(BizExceptionEnum.NO_USER);
            }
    		 TblOwner owner = ownerService.find(user.getOwner());
             AuthResponse resp = new AuthResponse(token, randomKey);
             //return ResponseEntity.ok(resp);
             Map<String,Object> map = new HashMap<>();
             map.put("code", SUCCESS);
             map.put("payload", WXUtils.getPayLoad());
             map.put("token", resp.getToken());
             map.put("owner", user.getOwner());
             HttpKit.getRequest().setAttribute("owner", user.getOwner());
             map.put("app_name", owner.getAppName());
             map.put("app_qr", owner.getAppName());
             return super.warpObject(new AuthWrapper(map));
    		
    	}else if(ToolUtil.isNotEmpty(authRequest.getUserName())){
    		TblOwnerStaff user = authService.findByUsername(authRequest.getUserName());
    		if(ToolUtil.isEmpty(user)){
            	throw new PauException(BizExceptionEnum.NO_USER);
            }
    		 TblOwner owner = ownerService.find(user.getOwner());
             AuthResponse resp = new AuthResponse(token, randomKey);
             //return ResponseEntity.ok(resp);
             Map<String,Object> map = new HashMap<>();
             map.put("code", SUCCESS);
             map.put("token", resp.getToken());
             map.put("owner", user.getOwner());
             HttpKit.getRequest().setAttribute("owner", user.getOwner());
             map.put("app_name", owner.getAppName());
             map.put("app_qr", owner.getAppName());
             return super.warpObject(new AuthWrapper(map));
    	}else {
    		throw new PauException(BizExceptionEnum.NO_USER);
    	}
    	
	}
    
    @RequestMapping(value = "${jwt.logout-path}")
    public void clearToken(HttpServletRequest request,String owner){
    	 String requestHeader = request.getHeader(jwtProperties.getHeader());
    	 String authToken = null;
    	 if (requestHeader != null && requestHeader.startsWith("Bearer ")) {
             authToken = requestHeader.substring(7);
             if(!jwtTokenUtil.isTokenExpired(authToken)){
            	 request.setAttribute("claims", "");
             }
    	 }
    	 
    	
    }
}
