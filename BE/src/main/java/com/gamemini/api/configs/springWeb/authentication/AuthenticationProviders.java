package com.gamemini.api.configs.springWeb.authentication;

import com.gamemini.api.configs.springWeb.PasswordConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * Lop cau hinh load du lieu nguoi dung tu DB cung cap
 * cho SpringFilterChain thuc hien loc xac thuc phan quyen
 * */
@Configuration
public class AuthenticationProviders implements WebMvcConfigurer {
    @Autowired
    private CustomUserDetailService userDetailsService;

    @Autowired
    private PasswordConfig passwordConfig;

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(passwordConfig.bCryptPasswordEncoder());
        return provider;
    }
}
