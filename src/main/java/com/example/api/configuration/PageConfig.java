package com.example.api.configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class PageConfig implements WebMvcConfigurer {

//	@Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//            // by default uses a Bean by the name of corsConfigurationSource
//            .cors().and()
//            ...
//    }
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
	    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
	    return bCryptPasswordEncoder;
	}
//	@Override
//	public void addViewControllers(ViewControllerRegistry registry) {
//	    registry.addViewController("/home").setViewName("home");
//	    registry.addViewController("/").setViewName("home");
//	    registry.addViewController("/dashboard").setViewName("dashboard");
//	    registry.addViewController("/login").setViewName("login");
//	}
	
}