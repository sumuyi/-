package com.sumui.config.satoken;

import cn.dev33.satoken.interceptor.SaInterceptor;
//import cn.dev33.satoken.jwt.StpLogicJwtForSimple;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpLogic;
import cn.dev33.satoken.stp.StpUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * sa-token 配置
 *
 * @author Lion Li
 */
@RequiredArgsConstructor
@Slf4j
@Configuration
public class SaTokenConfig implements WebMvcConfigurer {

    /**
     * 注册sa-token的拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        // 注册路由拦截器，自定义验证规则
        registry.addInterceptor(new SaInterceptor(handler -> {
                    // 登录验证 -- 排除多个路径
                    // 检查是否登录 是否有token
                    SaRouter
                            // 获取所有的
                            .match("/**")
                            // 对未排除的路径进行检查
                            .check(StpUtil::checkLogin);
                })).addPathPatterns("/**")
                // 排除不需要拦截的路径
                .excludePathPatterns("/auth/**");
    }

//    @Bean
//    public StpLogic getStpLogicJwt() {
//        // Sa-Token 整合 jwt (简单模式)
//        return new StpLogicJwtForSimple();
//    }

}
