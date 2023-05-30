package com.atsu.filter;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @Classname BaseUrlFilter
 * @author: 我心
 * @Description:
 * @Date 2022/7/26 22:05
 * @Created by Lenovo
 */
public class BaseUrlFilter extends BaseFilter{
    @Override
    public void myFilter(HttpServletRequest request, HttpServletResponse response) {
//        HttpSession session = request.getSession();
        ServletContext servletContext = request.getServletContext();
        //判断当前工程上下文对象中是否存在baseUrl
        Object baseUrl = servletContext.getAttribute("baseUrl");
        if (baseUrl==null){
            String url=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
            //将当前的项目路径写入工程域中
            servletContext.setAttribute("baseUrl",url);
//            session.setAttribute("baseUrl",url);
        }
    }
}
