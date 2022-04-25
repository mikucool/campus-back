package com.hzz.campusback.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;
import java.net.URL;


@Configuration
public class GlobalWebMvcConfigurer implements WebMvcConfigurer {

    /**
     * 跨域
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS")
                .allowCredentials(true)
                .maxAge(3600)
                .allowedHeaders("*");
    }

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        //允许所有域名进行跨域调用
        config.addAllowedOriginPattern("*");
        //允许跨越发送cookie
        config.setAllowCredentials(true);
        //放行全部原始头信息
        config.addAllowedHeader("*");
        //允许所有请求方法跨域调用
        config.addAllowedMethod("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        System.out.println("配置文件已经生效");
        //关于图片上传后需要重启服务器才能刷新图片
        //这是一种保护机制，为了防止绝对路径被看出来，目录结构暴露
        //解决方法:将虚拟路径static/message/
        //向绝对路径 (C:\Users\hzz\Desktop\campus_forum\campus-back\src\main\resources\static\message\image)映射
        String path_2 = this.getClass().getResource("/").getPath();//返回路径最前方和末尾均多一个"/",并且以"/"做目录间隔
//        System.out.println(path_2);
        File file = new File(path_2);//用File对象处理后,会删除头尾的"/",并且将所有的以"\(反斜杠)"间隔目录
//        System.out.println(file);//  D:\work\workspace_set\workspace_javaee\ProjectJavaWay\target\classes
        String s1 = "\\target\\classes";
        String s2 = file.toString();
        String localPath = s2.substring(0, s2.length() - s1.length());
//        System.out.println(localPath);
        registry.addResourceHandler("/static/message/**").addResourceLocations("file:" + localPath + "\\src\\main\\resources\\static\\message\\");
        registry.addResourceHandler("/static/avatar/**").addResourceLocations("file:" + localPath + "\\src\\main\\resources\\static\\avatar\\");
        registry.addResourceHandler("/static/post/**").addResourceLocations("file:" + localPath + "\\src\\main\\resources\\static\\post\\");

        // 解决无法加载图标
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/")
                .addResourceLocations("classpath:/META-INF/resources/");
    }
}
