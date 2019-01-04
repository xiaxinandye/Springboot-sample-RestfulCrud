package com.yunche.config;

import com.yunche.component.LoginHandlerInterceptor;
import com.yunche.component.MyLocaleResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @ClassName: MyMvcConfig
 * @Description: 练习：使用 WebMvcConfigurer 扩展 SpringMVC的功能
 * @author: yunche
 * @date: 2019/01/01
 */

@Configuration
public class MyMvcConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("login");
        registry.addViewController("/index.html").setViewName("login");
        registry.addViewController("/index").setViewName("login");
        registry.addViewController("/main.html").setViewName("dashboard");

    }

    /**
     * 注册定制组件
     */
    @Bean
    public LocaleResolver localeResolver() {
        return new MyLocaleResolver();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        //排除静态资源的拦截：*.css , *.js
//        registry.addInterceptor(new LoginHandlerInterceptor()).addPathPatterns("/**").excludePathPatterns("/index.html", "/index", "/user/login", "/asserts/**","/webjars/**");

        registry.addInterceptor(new LoginHandlerInterceptor()).addPathPatterns("/main.html", "/emps", "/emp/*");
    }

}
