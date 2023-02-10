package com.nowcoder.community.community.config;

import com.nowcoder.community.community.controller.interceptor.AInterceptor;
import com.nowcoder.community.community.controller.interceptor.LoginRequiredInterceptor;
import com.nowcoder.community.community.controller.interceptor.LoginTicketInterceptor;
import com.nowcoder.community.community.controller.interceptor.MessageInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMVCConfig implements WebMvcConfigurer {

    @Autowired
    private AInterceptor aInterceptor;

    @Autowired
    private LoginTicketInterceptor loginTicketInterceptor;

//    @Autowired
//    private LoginRequiredInterceptor loginRequiredInterceptor;

    @Autowired
    private MessageInterceptor messageInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(aInterceptor)
                .excludePathPatterns("/*/*.css","/*/*.js","/*/*.jpg","/*/*.jpeg","/*/*.png")
//                .excludePathPatterns("/**/*.css","/**/*.js","/**/*.jpg","/**/*.jpeg","/**/*.png")
                .addPathPatterns("/register","/login","/user");

        registry.addInterceptor(loginTicketInterceptor)
//                .excludePathPatterns("/**/*.css","/**/*.js","/**/*.jpg","/**/*.jpeg","/**/*.png");
                .excludePathPatterns("/*/*.css","/*/*.js","/*/*.jpg","/*/*.jpeg","/*/*.png");

//        registry.addInterceptor(loginRequiredInterceptor)
////                .excludePathPatterns("/**/*.css","/**/*.js","/**/*.jpg","/**/*.jpeg","/**/*.png");
//                .excludePathPatterns("/*/*.css","/*/*.js","/*/*.jpg","/*/*.jpeg","/*/*.png");

        registry.addInterceptor(messageInterceptor)
//                .excludePathPatterns("/**/*.css","/**/*.js","/**/*.jpg","/**/*.jpeg","/**/*.png");
                .excludePathPatterns("/*/*.css","/*/*.js","/*/*.jpg","/*/*.jpeg","/*/*.png");


    }
}
