package com.atsu.filter;


import org.springframework.http.HttpRequest;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Classname BaseFilter
 * @author: 我心
 * @Description:
 * @Date 2022/7/26 21:59
 * @Created by Lenovo
 */
public abstract class BaseFilter  implements Filter {
    public abstract void myFilter(HttpServletRequest request, HttpServletResponse response);
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req= (HttpServletRequest) request;
        HttpServletResponse resp= (HttpServletResponse) response;
        myFilter(req,resp);
        chain.doFilter(req,resp);
    }

    @Override
    public void destroy() {

    }
}
