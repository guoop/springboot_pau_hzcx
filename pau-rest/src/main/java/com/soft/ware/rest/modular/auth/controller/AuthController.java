package com.soft.ware.rest.modular.auth.controller;

import cn.binarywang.wx.miniapp.api.WxMaService;
import com.google.common.collect.Lists;
import com.soft.ware.core.base.controller.BaseController;
import com.soft.ware.core.exception.PauException;
import com.soft.ware.core.util.Kv;
import com.soft.ware.core.util.ToolUtil;
import com.soft.ware.rest.common.exception.BizExceptionEnum;
import com.soft.ware.rest.common.persistence.model.TblOwnerStaff;
import com.soft.ware.rest.config.properties.JwtProperties;
import com.soft.ware.rest.modular.auth.controller.dto.AuthRequest;
import com.soft.ware.rest.modular.auth.controller.dto.AuthResponse;
import com.soft.ware.rest.modular.auth.service.HzcxWxService;
import com.soft.ware.rest.modular.auth.util.JwtTokenUtil;
import com.soft.ware.rest.modular.auth.util.WXUtils;
import com.soft.ware.rest.modular.auth.validator.IReqValidator;
import com.soft.ware.rest.modular.owner.model.TOwner;
import com.soft.ware.rest.modular.owner.service.ITOwnerService;
import com.soft.ware.rest.modular.owner_staff.model.TOwnerStaff;
import com.soft.ware.rest.modular.owner_staff.service.TOwnerStaffService;
import com.soft.ware.rest.modular.wx_app.model.SWxApp;
import com.soft.ware.rest.modular.wx_app.service.ISWxAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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
    private ITOwnerService ownerService;
    
    @Autowired
    private JwtProperties jwtProperties;

    @Autowired
    private TOwnerStaffService tOwnerStaffService;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    private HzcxWxService hzcxWxService;

    @Autowired
    private ISWxAppService appService;


    /**
     * 收银appDenglu
     * @param params
     * @return
     */
    @RequestMapping(value = "${jwt.auth-path}",headers = {"Content-Type=application/x-www-form-urlencoded"})
    public Object appLogin(AuthRequest params) throws Exception {
        //todo yancc 没有验证码不安全
    	TOwnerStaff user;
        if(ToolUtil.isNotEmpty(params.getPhone())){
            user = (TOwnerStaff) reqValidator.validate(params);
        }else{
            user = (TOwnerStaff) reqValidator.validate(params);
        }
        if (user != null) {
            final String randomKey = jwtTokenUtil.getRandomKey();
            final String token = jwtTokenUtil.generateToken(params.getPhone(), randomKey);
            TOwner owner = ownerService.find(user);
            SWxApp app = appService.find(owner);
            appService.find(owner);
            AuthResponse resp = new AuthResponse(token, randomKey);
            return render()
                    .set("payload", WXUtils.getPayLoad())
                    .set("token", resp.getToken())
                    .set("owner", user.getOwnerId())
                    .set("app_name", app.getAppName())
                    .set("app_qr", app.getAppQr())
                    .set("users", Lists.newArrayList(user));
        } else {
            throw new PauException(BizExceptionEnum.AUTH_REQUEST_ERROR);
        }
    }

    /**
     * 商家小程序登录
     * @param param
     * @return
     */
    @RequestMapping(value = "${jwt.auth-path}")
    public Object maLogin(@RequestBody AuthRequest param) throws Exception {
        String phone = param.getPhone();
        //调试验证码写成123456
        //String s = redisTemplate.opsForValue().get(WXContants.loginCodePrefix + phone);
        String s = "123456";
   /*     if (!param.getPassword().equals(s)) {
            return warpObject(render(false, "验证码错误"));
        }*/
        WxMaService service = hzcxWxService.getWxMaService();
        TOwnerStaff user = tOwnerStaffService.findByPhone(phone);
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
        map.set("user", user);
        map.set("ownerId", user.getOwnerId());
        //HttpKit.getRequest().setAttribute("ownerId", user.getOwnerId());
        return map;
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
