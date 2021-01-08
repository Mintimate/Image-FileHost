package cn.mintimate.filecloudplus.config;

import cn.mintimate.filecloudplus.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyLoginInterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注册TestInterceptor拦截器
        InterceptorRegistration registration = registry.addInterceptor(new LoginInterceptor());
        registration.addPathPatterns("/imageHost/**","/userAdmin/**","/fileType/**","/fileHost/**"); //所有路径都被拦截
        registration.excludePathPatterns("/imageHost/imageDetail/**","/imageHost/getImage/**","/imageHost/download/**"
                ,"/userAdmin/login","/userAdmin/enter","/userAdmin/register","/userAdmin/email"
                ,"/fileHost/download/**","/fileType/dataInfo","/fileType/homebrew","/fileType/fileTypeDetail/**"
                ,"/userAdmin/logout"
        );
    }
}
