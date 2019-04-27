package com.soft.ware.rest.modular.auth.filter;

import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.soft.ware.core.support.HttpKit;
import com.soft.ware.core.util.SpringContextHolder;
import com.soft.ware.rest.common.exception.BizExceptionEnum;
import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;
import com.soft.ware.rest.modular.auth.util.WXContants;
import com.soft.ware.rest.modular.auth.util.WXUtils;
import com.soft.ware.rest.modular.owner.model.TOwner;
import com.soft.ware.rest.modular.owner.service.ITOwnerService;
import com.soft.ware.rest.modular.owner_staff.model.TOwnerStaff;
import com.soft.ware.rest.modular.owner_staff.service.TOwnerStaffService;
import com.soft.ware.rest.modular.wx_app.model.SWxApp;
import com.soft.ware.rest.modular.wx_app.service.ISWxAppService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.impl.DefaultClaims;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

/**
 * 自定义controller 方法参数解析器
 */
public class AuthHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return SessionUser.class.equals(methodParameter.getParameterType());
    }

    /**
     * 自动注入用户参数
     * @param methodParameter
     * @param modelAndViewContainer
     * @param request
     * @param webDataBinderFactory
     * @return
     * @throws Exception
     */
    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest request, WebDataBinderFactory webDataBinderFactory) throws Exception {
        HttpServletRequest req = HttpKit.getRequest();
        ISWxAppService appService = SpringContextHolder.getBean(ISWxAppService.class);
        ITOwnerService ownerService = SpringContextHolder.getBean(ITOwnerService.class);
        if (req.getServletPath().startsWith("/customer/")) {
            if (SessionUser.class == methodParameter.getParameterType()) {
                //买家端用户
                String appId = WXUtils.getAppId(req);
                SWxApp app = appService.findByAppId(appId);
                TOwner owner = ownerService.find(app);
                SessionUser user = new SessionUser();
                String openId = req.getHeader("Hzcx-User");
                user.setAppId(appId);
                user.setId(openId);
                user.setOpenId(openId);
                user.setId(IdWorker.get32UUID());
                user.setName(owner.getName());
                user.setPhone(owner.getPhone());
                user.setOwnerId(owner.getId());
                return user;
            }
        }  else {
            //收银端用户/商家端用户
            Object claims = request.getAttribute("claims",0);
            if (claims instanceof DefaultClaims) {
                DefaultClaims c = (DefaultClaims) claims;
                String username = (String) c.get(Claims.SUBJECT);
                TOwnerStaffService authService = SpringContextHolder.getBean(TOwnerStaffService.class);
                 if(methodParameter.getParameterType() == SessionUser.class) {
                    //todo yancc 计划删掉,商户端应该不支持
                    TOwnerStaff user = authService.findByLoginName(username);
                    SessionUser u = new SessionUser();
                    u.setId(user.getId() + "");
                    u.setOwnerId(user.getOwnerId());
                    if (req.getServletPath().startsWith("/owner")) {
                        //小程序用户
                        String openId = req.getHeader("Hzcx-User");
                        u.setOpenId(openId);
                        u.setPhone(user.getPhone());
                        u.setAppId(WXContants.OWNER_APP_ID);
                    }
                    return u;
                }
            }
            RequestParam param = methodParameter.getParameterAnnotation(RequestParam.class);
            if (param == null || param.required()) {
                throw new JwtException(BizExceptionEnum.TOKEN_EXPIRED.getMessage());
            }
            return null;
        }

        return null;

    }
}
