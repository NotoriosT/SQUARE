package com.react.react.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocket implements WebSocketMessageBrokerConfigurer{

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/canal");
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry config) {
        config.addEndpoint("conect").setAllowedOriginPatterns("http://localhost:63343", "http://tupa.tech");
        config.addEndpoint("conect").setAllowedOriginPatterns("http://localhost:63343","http://tupa.tech").withSockJS();
    }
}
