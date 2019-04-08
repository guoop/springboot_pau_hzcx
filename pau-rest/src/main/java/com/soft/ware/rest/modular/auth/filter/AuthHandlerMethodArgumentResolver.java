package com.soft.ware.rest.modular.auth.filter;

import com.soft.ware.core.support.HttpKit;
import com.soft.ware.core.util.SpringContextHolder;
import com.soft.ware.rest.common.exception.BizExceptionEnum;
import com.soft.ware.rest.common.persistence.model.TblOwnerStaff;
import com.soft.ware.rest.modular.auth.controller.dto.SessionOwner;
import com.soft.ware.rest.modular.auth.controller.dto.SessionOwnerUser;
import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;
import com.soft.ware.rest.modular.auth.service.AuthService;
import com.soft.ware.rest.modular.auth.util.WXContants;
import com.soft.ware.rest.modular.auth.util.WXUtils;
import com.soft.ware.rest.modular.owner.model.TOwner;
import com.soft.ware.rest.modular.owner.service.ITOwnerService;
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
        return SessionOwner.class.isAssignableFrom(methodParameter.getParameterType());
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
        ITOwnerService ownerService = SpringContextHolder.getBean(ITOwnerService.class);
        //ITOwnerService ownerService = SpringContextHolder.getBean(ISWxAppService.class);
        if (req.getServletPath().startsWith("/customer")) {
            if (SessionUser.class == methodParameter.getParameterType()) {
                //买家端用户
                String appId = WXUtils.getAppId(req);
                TOwner owner = ownerService.findByAppId(appId);
                SessionUser user = new SessionUser(owner.getId());
                String openId = req.getHeader("Hzcx-User");
                user.setAppId(appId);
                user.setId(openId);
                user.setOpenId(openId);
                user.setUsername(openId);
                user.setOwner(user.getOwner());
                return user;
            } else if(SessionOwner.class == methodParameter.getParameterType()) {
                //买家端用户
                String appId = WXUtils.getAppId(req);
                TOwner o = ownerService.findByAppId(appId);
                SessionOwner owner = new SessionOwner(o.getId());
                return owner;
            }

        }  else {
            //收银端用户/商家端用户
            Object claims = request.getAttribute("claims",0);
            if (claims instanceof DefaultClaims) {
                DefaultClaims c = (DefaultClaims) claims;
                String username = (String)c.get("sub");
                AuthService authService = SpringContextHolder.getBean(AuthService.class);
                if (methodParameter.getParameterType() == SessionOwnerUser.class) {
                    TblOwnerStaff user = authService.findByUsername(username);
                    SessionOwnerUser u = new SessionOwnerUser(user.getOwner());
                    u.setId(user.getId().toString() + "");
                    u.setOwner(user.getOwner());
                    u.setUsername(user.getPhone());
                    return u;
                } else if(methodParameter.getParameterType() == SessionUser.class) {
                    //todo yancc 计划删掉,商户端应该不支持
                    TblOwnerStaff user = authService.findByUsername(username);
                    SessionUser u = new SessionUser(user.getOwner());
                    u.setId(user.getId().toString() + "");
                    u.setOwner(user.getOwner());
                    if (req.getServletPath().startsWith("/owner")) {
                        //小程序用户
                        String openId = req.getHeader("Hzcx-User");
                        u.setOpenId(openId);
                        u.setUsername(user.getPhone());
                        u.setAppId(WXContants.OWNER_APP_ID);
                    }
                    return u;
                }else{
                    TblOwnerStaff user = authService.findByUsername(username);
                    SessionOwner owner = new SessionOwner(user.getOwner());
                    return owner;
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
