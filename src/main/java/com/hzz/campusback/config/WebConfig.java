package com.hzz.campusback.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        System.out.println("配置文件已经生效");
        //关于图片上传后需要重启服务器才能刷新图片
        //这是一种保护机制，为了防止绝对路径被看出来，目录结构暴露
        //解决方法:将虚拟路径static/message/
        //        向绝对路径 (C:\Users\hzz\Desktop\campus_forum\campus-back\src\main\resources\static\message\image)映射


        registry.addResourceHandler("/static/message/**").addResourceLocations("file:C:\\Users\\hzz\\Desktop\\campus_forum\\campus-back\\src\\main\\resources\\static\\message\\");

    }
}

