package com.soft.ware.rest.common.aop;

import com.soft.ware.core.aop.BaseControllerExceptionHandler;
import com.soft.ware.core.exception.ParameterException;
import com.soft.ware.core.util.ResultView;
import com.soft.ware.rest.common.exception.BizExceptionEnum;
import io.jsonwebtoken.JwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 全局的的异常拦截器（拦截所有的控制器）（带有@RequestMapping注解的方法上都会拦截）
 *
 * @author paulo
 * @date 2016年11月12日 下午3:19:56
 */
@ControllerAdvice
public class GlobalExceptionHandler extends BaseControllerExceptionHandler {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * 拦截jwt相关异常
     */
    @ExceptionHandler(JwtException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResultView jwtException(JwtException e) {
        return ResultView.view(false, BizExceptionEnum.TOKEN_ERROR.getMessage());
    }


    @ExceptionHandler(ParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResultView parameterException(ParameterException e){
        return ResultView.view(false,e.getMessage());
    }

}
