package com.soft.ware.rest.modular.auth.controller;

import cn.binarywang.wx.miniapp.api.WxMaService;
import com.soft.ware.core.base.controller.BaseController;
import com.soft.ware.core.exception.PauException;
import com.soft.ware.core.support.HttpKit;
import com.soft.ware.core.util.Kv;
import com.soft.ware.core.util.ResultView;
import com.soft.ware.core.util.ToolUtil;
import com.soft.ware.rest.common.exception.BizExceptionEnum;
import com.soft.ware.rest.common.persistence.model.TblOwner;
import com.soft.ware.rest.common.persistence.model.TblOwnerStaff;
import com.soft.ware.rest.config.properties.JwtProperties;
import com.soft.ware.rest.modular.auth.controller.dto.AuthRequest;
import com.soft.ware.rest.modular.auth.controller.dto.AuthResponse;
import com.soft.ware.rest.modular.auth.service.AuthService;
import com.soft.ware.rest.modular.auth.service.HzcxWxService;
import com.soft.ware.rest.modular.auth.service.TblOwnerService;
import com.soft.ware.rest.modular.auth.service.TblOwnerStaffService;
import com.soft.ware.rest.modular.auth.util.BeanMapUtils;
import com.soft.ware.rest.modular.auth.util.JwtTokenUtil;
import com.soft.ware.rest.modular.auth.util.WXContants;
import com.soft.ware.rest.modular.auth.util.WXUtils;
import com.soft.ware.rest.modular.auth.validator.IReqValidator;
import com.soft.ware.rest.modular.auth.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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

    @Autowired
    private TblOwnerStaffService tblOwnerStaffService;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    private HzcxWxService hzcxWxService;

    /**
     * 收银appDenglu
     * @param authRequest
     * @return
     */
    @RequestMapping(value = "${jwt.auth-path}",headers = {"Content-Type=application/x-www-form-urlencoded"})
    public Object createAuthenticationToken(@Valid AuthRequest authRequest, HttpServletRequest request, BindingResult result) {
        Validator.valid(result);
    	 boolean validate = false;
        if(ToolUtil.isNotEmpty(authRequest.getPhone())){
        	validate = reqValidator.validate(authRequest);
        }else{
        	validate = reqValidator.validate(authRequest);
        }
        if (validate) {
            final String randomKey = jwtTokenUtil.getRandomKey();
            final String token = jwtTokenUtil.generateToken(authRequest.getUserName(), randomKey);
            return returnTokenParams(authRequest,token,randomKey);
        }else{
        	throw new PauException(BizExceptionEnum.AUTH_REQUEST_ERROR);
        }
    }

    /**
     * 商家小程序登录
     * @param param
     * @param request
     * @return
     */
    @RequestMapping(value = "${jwt.auth-path}",headers = {"Content-Type=application/json"})
    public Object maLogin(@RequestBody @Valid AuthRequest param, HttpServletRequest request, BindingResult result) throws Exception {
        Validator.valid(result);
        String phone = param.getPhone();
        String s = redisTemplate.opsForValue().get(WXContants.loginCodePrefix + phone);
   /*     if (!param.getPassword().equals(s)) {
            return warpObject(render(false, "验证码错误"));
        }*/
        WxMaService service = hzcxWxService.getWxMaService();
        String appId = service.getWxMaConfig().getAppid();
        TblOwnerStaff user = tblOwnerStaffService.findByPhone(phone);
        if (user == null) {
            return render(false, "用户不存在");
        }
        if (TblOwnerStaff.status_1.equals(user.getStatus())) {
            return render(false, "账户被禁用");
        }
        if (TblOwnerStaff.status_2.equals(user.getStatus())) {
            return render(false, "账户不存在");
        }
        final String randomKey = jwtTokenUtil.getRandomKey();
        final String token = jwtTokenUtil.generateToken(phone, randomKey);

        Kv<String,Object> map = render(true,"认证通过");
        map.set("token", token);
        map.set("payload", WXUtils.getPayLoad());
        map.set("user", BeanMapUtils.toMap(user, true));
        map.set("owner", user.getOwner());
        HttpKit.getRequest().setAttribute("owner", user.getOwner());
        return map;
    }

    protected Object returnTokenParams(AuthRequest authRequest,String token,String randomKey) {
    	if(ToolUtil.isNotEmpty(authRequest.getPhone())){
    		TblOwnerStaff user = authService.findByUsername(authRequest.getPhone());
    		if(ToolUtil.isEmpty(user)){
            	throw new PauException(BizExceptionEnum.NO_USER);
            }
    		 TblOwner owner = ownerService.find(user.getOwner());
             AuthResponse resp = new AuthResponse(token, randomKey);
             ResultView view = render();
             view.put("payload", WXUtils.getPayLoad());
             view.put("token", resp.getToken());
             view.put("owner", user.getOwner());
             HttpKit.getRequest().setAttribute("owner", user.getOwner());
             view.put("app_name", owner.getAppName());
             view.put("app_qr", owner.getAppName());
             return view;
    	} else {
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
