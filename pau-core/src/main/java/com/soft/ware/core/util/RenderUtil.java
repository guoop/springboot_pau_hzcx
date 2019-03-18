package com.soft.ware.core.util;

import com.alibaba.fastjson.JSON;
import com.soft.ware.core.exception.PauException;
import com.soft.ware.core.exception.PauExceptionEnum;

import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * 渲染工具类
 */
public class RenderUtil {

    /**
     * 渲染json对象
     */
    public static void renderJson(HttpServletResponse response, Object jsonObject) {
        try {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter writer = response.getWriter();
            writer.write(JSON.toJSONString(jsonObject));
        } catch (IOException e) {
            throw new PauException(PauExceptionEnum.WRITE_ERROR);
        }
    }
}
