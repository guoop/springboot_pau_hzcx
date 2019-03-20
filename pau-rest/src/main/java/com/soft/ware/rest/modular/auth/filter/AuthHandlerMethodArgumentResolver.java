package com.soft.ware.rest.modular.auth.filter;

import com.soft.ware.core.util.SpringContextHolder;
import com.soft.ware.rest.common.exception.BizExceptionEnum;
import com.soft.ware.rest.common.persistence.dao.TblOwnerStaffMapper;
import com.soft.ware.rest.common.persistence.model.TblOwnerStaff;
import com.soft.ware.rest.modular.auth.service.AuthService;
import com.soft.ware.rest.modular.auth.util.JwtTokenUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.impl.DefaultClaims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * 自定义controller 方法参数解析器
 */
public class AuthHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.getParameterType() == TblOwnerStaff.class;
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
        Object claims = request.getAttribute("claims",0);
        if (claims instanceof DefaultClaims) {
            DefaultClaims c = (DefaultClaims) claims;
            String username = (String)c.get("sub");
            AuthService authService = SpringContextHolder.getBean(AuthService.class);
            TblOwnerStaff user = authService.findByUsername(username);
            if (user != null) {
                return user;
            }
        }
        RequestParam param = methodParameter.getParameterAnnotation(RequestParam.class);
        if (param == null || param.required()) {
            throw new JwtException(BizExceptionEnum.TOKEN_EXPIRED.getMessage());
        }
        return null;
    }
}
