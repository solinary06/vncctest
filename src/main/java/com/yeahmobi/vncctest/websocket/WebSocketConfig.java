package com.yeahmobi.vncctest.websocket;

/**
 * Created by steven.liu on 2016/7/8.
 */
        import org.springframework.context.annotation.Configuration;
        import org.springframework.web.servlet.config.annotation.EnableWebMvc;
        import org.springframework.web.socket.config.annotation.EnableWebSocket;
        import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
        import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebMvc
@EnableWebSocket//开启websocket
public class WebSocketConfig implements WebSocketConfigurer {
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new WebSocketHandlerImpl(),"/verify").addInterceptors(new HandShakeInterceptorImpl()).setAllowedOrigins("*");; //支持websocket 的访问链接
        registry.addHandler(new WebSocketHandlerImpl(), "/sockjs/verify").addInterceptors(new HandShakeInterceptorImpl()).setAllowedOrigins("*").withSockJS(); //不支持websocket的访问链接
    }
}
