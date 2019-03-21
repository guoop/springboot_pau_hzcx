package com.soft.ware.rest.modular.auth.filter;

import com.soft.ware.rest.modular.auth.util.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * 自定义controller 方法参数解析器
 */
public class PageHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.getParameterType() == Page.class;
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
        Page<?> p = new Page<>();
        String limit = request.getParameter("size");
        String page = request.getParameter("page");
        if (StringUtils.isNotBlank(limit)) {
            p.setLimit(Integer.valueOf(limit));
        }
        if (StringUtils.isNotBlank(page)) {
            p.setPage(Integer.valueOf(page));
        }
        return p;
    }
}
