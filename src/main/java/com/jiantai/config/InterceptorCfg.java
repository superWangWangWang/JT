package com.jiantai.config;

import com.jiantai.interceptor.MyInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class InterceptorCfg implements WebMvcConfigurer {
    @Autowired
    private MyInterceptor interceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //registry.addInterceptor(interceptor);
        //注册TestInterceptor拦截器
        InterceptorRegistration registration = registry.addInterceptor(interceptor);
        registration.addPathPatterns("/**");                      //所有路径都被拦截
        registration.excludePathPatterns(                         //添加不拦截路径

                "/*.html",
                "/**/*.js",            //js静态资源
                "/**/*.css",             //css静态资源
                "/**/*.png",
                "/**/*.jpg",
                "/**/*.woff",
                "/**/*.ttf"
        );
    }
}
