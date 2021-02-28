package com.windsnowli.workserver.config;

import com.windsnowli.workserver.interceptor.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 配置拦截器
 *
 * @author windSnowLi
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    private CrosInterceptor cros;

    @Autowired
    public void setCros(CrosInterceptor cros) {
        this.cros = cros;
    }

    private MappingInterceptor mappingInterceptor;

    @Autowired
    public void setMappingInterceptor(MappingInterceptor mappingInterceptor) {
        this.mappingInterceptor = mappingInterceptor;
    }

    private PassTokenInterceptor passTokenInterceptor;

    @Autowired
    public void setPassTokenInterceptor(PassTokenInterceptor passTokenInterceptor) {
        this.passTokenInterceptor = passTokenInterceptor;
    }

    private PowerInterceptor powerInterceptor;

    @Autowired
    public void setPowerInterceptor(PowerInterceptor powerInterceptor) {
        this.powerInterceptor = powerInterceptor;
    }

    private CodeInterceptor codeInterceptor;

    @Autowired
    public void setCodeInterceptor(CodeInterceptor codeInterceptor) {
        this.codeInterceptor = codeInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(codeInterceptor).addPathPatterns("/**");
        registry.addInterceptor(cros).addPathPatterns("/**");
        registry.addInterceptor(mappingInterceptor).addPathPatterns("/**");
        registry.addInterceptor(passTokenInterceptor)
                // 拦截所有请求，通过判断是否有 @LoginRequired 注解 决定是否需要登录
                .addPathPatterns("/**");
        registry.addInterceptor(powerInterceptor).addPathPatterns("/**");
    }
}
