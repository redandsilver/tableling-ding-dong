package com.example.tablelingdingdong.config;


import com.example.tablelingdingdong.config.security.JwtAuthenticationProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {
   @Bean
   public JwtAuthenticationProvider jwtAuthenticationProvider(){
      return new JwtAuthenticationProvider();
   }
}
