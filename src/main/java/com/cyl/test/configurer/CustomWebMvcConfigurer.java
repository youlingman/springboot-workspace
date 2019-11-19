package com.cyl.test.configurer;

import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebMvc
public class CustomWebMvcConfigurer implements WebMvcConfigurer {
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        // set FastJson converter
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        FastJsonConfig config = new FastJsonConfig();
        converter.setFastJsonConfig(config);
        // need to override the default MediaType.ALL support
        // springmvc forbid Content-Type cannot contain wildcard type '*' in writeWithMessageConverters
        converter.setSupportedMediaTypes(new ArrayList<MediaType>(){{
            add(MediaType.APPLICATION_JSON);
        }});
        converters.add(0, converter);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        // 这里可以新增自定义HandlerMethodArgumentResolver
        // resolvers.add(0, null);
    }
}
