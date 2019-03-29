package com.soft.ware.rest.modular.auth.filter;

import com.soft.ware.core.support.HttpKit;
import com.soft.ware.core.util.SpringContextHolder;
import com.soft.ware.rest.common.exception.BizExceptionEnum;
import com.soft.ware.rest.common.persistence.model.TblOwner;
import com.soft.ware.rest.common.persistence.model.TblOwnerStaff;
import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;
import com.soft.ware.rest.modular.auth.service.AuthService;
import com.soft.ware.rest.modular.auth.service.TblOwnerService;
import com.soft.ware.rest.modular.auth.util.WXContants;
import com.soft.ware.rest.modular.auth.util.WXUtils;
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
        return methodParameter.getParameterType() == SessionUser.class;
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
        TblOwnerService ownerService = SpringContextHolder.getBean(TblOwnerService.class);
        if (req.getServletPath().startsWith("/customer")) {
            //买家端用户
            String appId = WXUtils.getAppId(req);
            TblOwner owner = ownerService.findByAppId(appId);
            SessionUser user = new SessionUser(SessionUser.type_customer, owner.getOwner());
            String openId = req.getHeader("Hzcx-User");
            user.setAppId(appId);
            user.setId(openId);
            user.setOpenId(openId);
            user.setUsername(openId);
            user.setOwner(user.getOwner());
            return user;
        }  else {
            //收银端用户/商家端用户
            Object claims = request.getAttribute("claims",0);
            if (claims instanceof DefaultClaims) {
                DefaultClaims c = (DefaultClaims) claims;
                String username = (String)c.get("sub");
                AuthService authService = SpringContextHolder.getBean(AuthService.class);
                TblOwnerStaff user = authService.findByUsername(username);
                if (user != null) {
                    SessionUser u = new SessionUser(SessionUser.type_staff, user.getOwner());
                    u.setId(user.getId().toString() + "");
                    u.setOwner(user.getOwner());
                    if (req.getServletPath().startsWith("/owner")) {
                        //小程序用户
                        String openId = req.getHeader("Hzcx-User");
                        u.setType(SessionUser.type_owner);
                        u.setOpenId(openId);
                        u.setUsername(user.getPhone());
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

    }
}
