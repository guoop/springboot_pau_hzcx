package com.soft.ware.core.base.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.plugins.Page;
import com.soft.ware.core.base.tips.SuccessTip;
import com.soft.ware.core.base.warpper.BaseControllerWarpper;
import com.soft.ware.core.base.warpper.FailWrapper;
import com.soft.ware.core.base.warpper.SuccessWrapper;
import com.soft.ware.core.page.PageInfoBT;
import com.soft.ware.core.support.HttpKit;
import com.soft.ware.core.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Map;

public class BaseController {

    protected Logger logger;

    public BaseController() {
        this.logger = LoggerFactory.getLogger(getClass());
    }

    public static String SUCCESS = "success";
    public static String ERROR = "fail";

    protected static String REDIRECT = "redirect:";
    protected static String FORWARD = "forward:";

    protected static SuccessTip SUCCESS_TIP = new SuccessTip();


    protected HttpServletRequest getHttpServletRequest() {
        return HttpKit.getRequest();
    }

    protected HttpServletResponse getHttpServletResponse() {
        return HttpKit.getResponse();
    }

    protected HttpSession getSession() {
        return HttpKit.getRequest().getSession();
    }

    protected HttpSession getSession(Boolean flag) {
        return HttpKit.getRequest().getSession(flag);
    }

    protected String getPara(String name) {
        return HttpKit.getRequest().getParameter(name);
    }

    protected void setAttr(String name, Object value) {
        HttpKit.getRequest().setAttribute(name, value);
    }

    protected Integer getSystemInvokCount() {
        return (Integer) this.getHttpServletRequest().getServletContext().getAttribute("systemCount");
    }

    /**
     * 把service层的分页信息，封装为bootstrap table通用的分页封装
     */
    protected <T> PageInfoBT<T> packForBT(Page<T> page) {
        return new PageInfoBT<T>(page);
    }

    /**
     * 包装一个list，让list增加额外属性
     */
    protected Object warpObject(BaseControllerWarpper warpper) {
        return warpper.warp();
    }

    /**
     * 删除cookie
     */
    protected void deleteCookieByName(String cookieName) {
        Cookie[] cookies = this.getHttpServletRequest().getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(cookieName)) {
                Cookie temp = new Cookie(cookie.getName(), "");
                temp.setMaxAge(0);
                this.getHttpServletResponse().addCookie(temp);
            }
        }
    }

    /**
     * 返回前台文件流
     */
    protected ResponseEntity<byte[]> renderFile(String fileName, String filePath) {
        byte[] bytes = FileUtil.toByteArray(filePath);
        return renderFile(fileName, bytes);
    }

    /**
     * 返回前台文件流
     */
    protected ResponseEntity<byte[]> renderFile(String fileName, byte[] fileBytes) {
        String dfileName = null;
        try {
            dfileName = new String(fileName.getBytes("gb2312"), "iso8859-1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", dfileName);
        return new ResponseEntity<byte[]>(fileBytes, headers, HttpStatus.CREATED);
    }

    public BaseControllerWarpper render(boolean result){
        if (result) {
            return new SuccessWrapper();
        } else {
            return new FailWrapper();
        }
    }


    public BaseControllerWarpper render(boolean result,String msg){
        if (result) {
            return new SuccessWrapper(msg);
        } else {
            return new FailWrapper(msg);
        }
    }


/*
    public Kv<String,Object> render(){
        return render(true);
    }

    public Kv<String,Object> render(boolean result){
        return Kv.view(result);
    }

    public Kv<String,Object> render(boolean result, String msg){
        return Kv.view(result, msg);
    }

    public Kv<String,Object> render(Page page){
        return render().set("page",page);
    }
*/


    /**
     * 使用post 重定向
     * @param request
     * @param response
     * @param url
     * @param params
     * @throws IOException
     */
    public void postRedirect(HttpServletRequest request, HttpServletResponse response,String url, Map<String,String> params,Map<String,String> headers) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
        out.println("<HTML>");
        out.println(" <HEAD><TITLE>sender</TITLE></HEAD>");
        out.println(" <BODY>");
        out.println("<form name=\"submitForm\" action=\""+url+"\" method=\"post\">");
        String value;
        for (String s : params.keySet()) {
            value = params.get(s);
            if(value==null) value = "";
            out.println("<input type=\"hidden\" name=\"" + s + "\" value=\"" + value + "\"/>");
        }
        out.println("</from>");
        out.println("<script>window.document.submitForm.submit();</script>");
        out.println(" </BODY>");
        out.println("</HTML>");
        out.flush();
        out.close();
    }


    public Object restRedirect(RestTemplate restTemplate,String url,Map<String, String> params, HttpServletRequest request,String... headers){
        MultiValueMap<String,String> hs = new HttpHeaders();
        Enumeration<String> names = request.getParameterNames();
        String name;
        while (names.hasMoreElements()) {
            name = names.nextElement();
            params.put(name, request.getParameter(name));
        }
        if (headers != null) {
            for (String header : headers) {
                hs.put(header, Arrays.asList(request.getHeader(header)));
            }
        }
        HttpEntity<String> http = new HttpEntity<>(JSON.toJSONString(params), hs);
        ResponseEntity<String> entity = restTemplate.postForEntity(url, http, String.class);
        if (entity.getStatusCodeValue() == 200) {
            return JSON.parseObject(entity.getBody(), Map.class);
        }
        return null;
    }


    public String getBasePath(HttpServletRequest request){
        String path = request.getContextPath();
        String port = request.getServerPort() == 80 ? "" : ":"+request.getServerPort() + "";
        if(path.equals("/")) path = "";
        String basePath = request.getScheme() + "s://" + request.getServerName() + port + path;
        return basePath;
    }


}
