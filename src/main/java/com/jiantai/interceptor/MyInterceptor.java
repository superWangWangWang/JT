package com.jiantai.interceptor;


import com.jiantai.entity.User;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 拦截器
 */
@Component
public class MyInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String path = request.getServletPath();
        HttpSession session = request.getSession();
        //登录页面和登录接口的url不拦截
        if ("/jianTai/login.html".equals(path) || "/user/login".equals(path)){
            return true;
        }else {
            if (null == session.getAttribute("user")) {//session里面没有用户对象，说明没有登录，跳转到登录页面
                response.sendRedirect(request.getContextPath() + "/login.html");
                return false;
            }else {
                if (path.indexOf("admin") != -1){
                    //访问的是admin路径下的内容，需要判断其身份是不是管理员
                    User user =  (User)session.getAttribute("user");
                    if (user.getType() == 0){
                        //不是管理员身份，强制跳转到公司界面
                        response.sendRedirect(request.getContextPath() + "/user/index");
                        //response.sendRedirect(request.getContextPath() + "/user/toIndex");
                        return false;
                    }

                }
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
