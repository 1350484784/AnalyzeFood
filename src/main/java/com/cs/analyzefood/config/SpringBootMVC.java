package com.cs.analyzefood.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.MultipartConfigElement;

//WebMvcConfigurer control 的增强配置，比如过滤器，文件上传，control 会先执行
//springMVC.xml的替代品

@Configuration
public class SpringBootMVC implements WebMvcConfigurer {

    //虚拟路径
    @Value("${web.upload-path}")
    private String path;

//    //接收上传信息
//    @Bean
//    public MultipartConfigElement multipartConfigElement(){
//        MultipartConfigFactory factory = new MultipartConfigFactory(); //文件最大KB,MB
//        factory.setMaxFileSize("20MB"); //单个文件最大
//        factory.setMaxRequestSize("100MB");//设置总上传数据总大小
//        return factory.createMultipartConfig();
//    }



    @Override
    public void addViewControllers(ViewControllerRegistry registry) {

//        给和html添加一个control
//        给/html/login.html 添加名为toLogin的control，自动添加
//        registry.addViewController("/index").setViewName("/html/login");
//        registry.addViewController("/index").setViewName("/index/findAll");


//        registry.addViewController("/").setViewName("/html/login");
//        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
//        super.addViewControllers(registry);
    }

//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        //  /** 拦截所有
//        registry.addInterceptor(loginIntercepter).addPathPatterns("/**").excludePathPatterns("toLogin","/html/login");
//    }

    //静态资源放行
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/html/**").addResourceLocations("classpath:/html/*");
        //虚拟路径
        registry.addResourceHandler("/upload/**").addResourceLocations("file:///"+path+"/*");
    }



}
