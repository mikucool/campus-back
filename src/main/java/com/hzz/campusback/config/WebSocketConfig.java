package com.hzz.campusback.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;

@Configuration
public class WebSocketConfig {

    /**
     * 注入一个ServerEndpointExporter,该Bean会自动注册使用@ServerEndpoint注解申明的websocket endpoint
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

    /**
     * websocket 配置信息
     *
     * @return
     */
    @Bean
    public ServletServerContainerFactoryBean createWebSocketContainer()
    {
        ServletServerContainerFactoryBean bean = new ServletServerContainerFactoryBean();
        //文本缓冲区大小
        bean.setMaxTextMessageBufferSize(8192);
        //字节缓冲区大小
        bean.setMaxBinaryMessageBufferSize(8192);
        return bean;
    }
}
