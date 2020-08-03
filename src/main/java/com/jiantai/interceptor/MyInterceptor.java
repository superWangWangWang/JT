package com.jiantai.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class MyInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String path = request.getServletPath();
        HttpSession session = request.getSession();
        //登录页面和登录接口的url不拦截
        if ("/jianTai/login.html".equals(path) || "/user/companyLogin".equals(path)){
            return true;
        }else {
           // System.out.println(request.getSession().getAttribute("LOGIN_USER")+"=========");
            if (null == session.getAttribute("LOGIN_USER")) {//session里面没有用户对象，说明没有登录，跳转到登录页面
                response.sendRedirect(request.getContextPath() + "/login.html");
                return false;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //System.out.println("postHandle==========================");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //System.out.println("afterCompletion==========================");
    }
}
