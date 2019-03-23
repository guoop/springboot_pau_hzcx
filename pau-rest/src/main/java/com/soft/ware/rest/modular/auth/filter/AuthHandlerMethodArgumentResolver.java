package com.soft.ware.rest.modular.auth.filter;

import com.soft.ware.core.support.HttpKit;
import com.soft.ware.core.util.SpringContextHolder;
import com.soft.ware.rest.common.exception.BizExceptionEnum;
import com.soft.ware.rest.common.persistence.model.TblOwnerStaff;
import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;
import com.soft.ware.rest.modular.auth.service.AuthService;
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
        String referer = req.getHeader("Referer");
        if (req.getServletPath().startsWith("/customer")) {
            //买家端用户
            String[] split = referer.split("/");
            String domain = split[2];
            String appId = split[3];
            SessionUser user = new SessionUser(SessionUser.type_customer,"16d0a4b0dcd411e8b2e187bf6b98e5cd");
            user.setId("o_Eds5ZAxJb5ayqXKea410smUXuY");
            user.setUsername("o_Eds5ZAxJb5ayqXKea410smUXuY");
            user.setOwner("16d0a4b0dcd411e8b2e187bf6b98e5cd");
            return user;
        } else {
            //收银端用户
            Object claims = request.getAttribute("claims",0);
            if (claims instanceof DefaultClaims) {
                DefaultClaims c = (DefaultClaims) claims;
                String username = (String)c.get("sub");
                AuthService authService = SpringContextHolder.getBean(AuthService.class);
                TblOwnerStaff user = authService.findByUsername(username);
                if (user != null) {
                    SessionUser u = new SessionUser(SessionUser.type_staff, user.getOwner());
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
