package com.dans.javaonboard.authentication.configs;


import com.dans.javaonboard.authentication.interceptors.JwtInterceptor;
import com.dans.javaonboard.authentication.interceptors.UserInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class GuardConfig implements WebMvcConfigurer {

    @Autowired
    private JwtInterceptor jwtInterceptor;
    @Autowired
    private UserInterceptor userInterceptor;

    @Override
    public void addInterceptors(org.springframework.web.servlet.config.annotation.InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/authentication/v1/**")
                .excludePathPatterns(
                        "/authentication/v1/login",
                        "/authentication/v1/register"
                );

        registry.addInterceptor(userInterceptor)
                .addPathPatterns("/authentication/v1/**")
                .excludePathPatterns(
                        "/authentication/v1/login",
                        "/authentication/v1/register",
                        "/authentication/v1/verify"
                );
    }
}
