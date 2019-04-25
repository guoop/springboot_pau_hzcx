package com.soft.ware.rest.modular.auth.filter;

import com.soft.ware.core.base.tips.ErrorTip;
import com.soft.ware.core.util.RenderUtil;
import com.soft.ware.rest.common.exception.BizExceptionEnum;
import com.soft.ware.rest.config.properties.JwtProperties;
import com.soft.ware.rest.modular.auth.util.JwtTokenUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * 对客户端请求的jwt token验证过滤器
 *
 * @author paulo
 * @Date 2017/8/24 14:04
 */
@Configuration
public class AuthFilter extends OncePerRequestFilter implements Filter {

    private final Log logger = LogFactory.getLog(this.getClass());

    public final static String customerUrlPrefix = "/customer";
    public final static String ownerUrlPrefix = "/owner";

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtProperties jwtProperties;

    public static Set<String> whiteUrlSet = new HashSet<>();
    public static Set<String> whitePrefixUrlSet = new HashSet<>();

    static {
        whiteUrlSet.add("/test/xxxx");
        whiteUrlSet.add("/im/init");
        whiteUrlSet.add("/customer/v1/banner/list");
        whiteUrlSet.add("/customer/v1/category/list");
        whiteUrlSet.add("/customer/v1/goods/list");
        whiteUrlSet.add("/customer/v1/shop");
        whiteUrlSet.add("/customer/v1/cart");
        whiteUrlSet.add("/customer/v2/orders");whiteUrlSet.add("/customer/v1/orders");
        whiteUrlSet.add("/customer/v1/address");
        whiteUrlSet.add("/customer/v1/address/man");
        whiteUrlSet.add("/customer/v1/question");
        whiteUrlSet.add("/customer/v1/order");
        whiteUrlSet.add("/customer/v1/order/delete");
        whiteUrlSet.add("/customer/v1/order/cancel");
        whiteUrlSet.add("/customer/v1/wxpay/unifiedorder");//下单
        whiteUrlSet.add("/customer-pay");//支付回调
        whiteUrlSet.add("/customer-pay/pickup");//支付回调,到店自提
        whiteUrlSet.add("/customer-pay/money-diff");//差额支付回调,到店自提
        whiteUrlSet.add("/customer/v1/wx_identifier");//获取openId
        whiteUrlSet.add("/customer/v1/order/address");
        whiteUrlSet.add("/customer/v1/diff/wxpay/unifiedorder");//补差价

        whiteUrlSet.add("/owner/share/login");
        whiteUrlSet.add("/owner/im/init");
        whiteUrlSet.add("/owner/share/wx_identifier");

        whiteUrlSet.add("/version/check");

        whitePrefixUrlSet.add("/pau-rest/swagger");
        whitePrefixUrlSet.add("/swagger");
        whitePrefixUrlSet.add("/customer/v2/orders/");
        whitePrefixUrlSet.add("/customer/v1/orders/");
        whitePrefixUrlSet.add("/customer/v1/goods/");
        whitePrefixUrlSet.add("/customer/v1/address/");
        whitePrefixUrlSet.add("/owner/share/code");
        whitePrefixUrlSet.add("/hello/api");
        whitePrefixUrlSet.add("/owner/v1/share/code");

    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String path = request.getServletPath();
         if (request.getServletPath().equals("/" + jwtProperties.getAuthPath()) || whiteUrlSet.contains(request.getServletPath())) {
            chain.doFilter(request, response);
            return;
        }
       for (String s : whitePrefixUrlSet) {
            if (path.startsWith(s)) {
                chain.doFilter(request, response);
                return;
            }
        }
        final String requestHeader = request.getHeader(jwtProperties.getHeader());
        String authToken = null;
        if (requestHeader != null && requestHeader.startsWith("Bearer ")) {
            authToken = requestHeader.substring(7);

            //验证token是否过期,包含了验证jwt是否正确
            try {
                boolean flag = jwtTokenUtil.isTokenExpired(authToken);
                
                if (flag) {
                    RenderUtil.renderJson(response, new ErrorTip(BizExceptionEnum.TOKEN_EXPIRED.getCode(), BizExceptionEnum.TOKEN_EXPIRED.getMessage()));
                    return;
                }else{
                    Claims c = jwtTokenUtil.getClaimFromToken(authToken);
                    request.setAttribute("claims", c);
                }
            } catch (JwtException e) {
                //有异常就是token解析失败
                RenderUtil.renderJson(response, new ErrorTip(BizExceptionEnum.TOKEN_ERROR.getCode(), BizExceptionEnum.TOKEN_ERROR.getMessage()));
                return;
            }
        } else {
            //header没有带Bearer字段
            RenderUtil.renderJson(response, new ErrorTip(BizExceptionEnum.TOKEN_ERROR.getCode(), BizExceptionEnum.TOKEN_ERROR.getMessage()));
            return;
        }
        chain.doFilter(request, response);
    }
}