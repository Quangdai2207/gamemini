package com.gamemini.api.configs.springWeb;

import com.gamemini.api.configs.springWeb.handlers.HandleAccessDeniedCustom;
import com.gamemini.api.configs.springWeb.jwt.JwtAuthenticationFilter;
import com.gamemini.api.configs.springWeb.handlers.HandlerEntryPoint;
import com.gamemini.api.configs.springWeb.authentication.AuthenticationProviders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration implements WebMvcConfigurer {
    private final CorsConfigurations corsConfigurations;
    private final AuthenticationProviders authenticationProviders;
    private final HandlerEntryPoint handlerEntryPoint;
    private final HandleAccessDeniedCustom handleAccessDinedCustom;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    public SecurityConfiguration(
            CorsConfigurations corsConfigurations,
            AuthenticationProviders authenticationProviders,
            HandlerEntryPoint handlerEntryPoint,
            HandleAccessDeniedCustom handleAccessDinedCustom,
            JwtAuthenticationFilter jwtAuthenticationFilter
    ) {
        this.corsConfigurations = corsConfigurations;
        this.authenticationProviders = authenticationProviders;
        this.handlerEntryPoint = handlerEntryPoint;
        this.handleAccessDinedCustom = handleAccessDinedCustom;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                //? Cau hinh Cors Origin Client
                .cors(cors -> cors.configurationSource(corsConfigurations.corsConfigurationSource()))
                //? Disable CSRF Token
                .csrf(AbstractHttpConfigurer::disable)

                //? Cau hinh Xac thuc quyen truy cap WebAPIs
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers(
                                    "/",
                                    "/me",
                                    "/api/v1/auth/**",
                                    "/ws/**",
                                    "/app/**",
                                    "/swagger-ui/**",
                                    "/v3/api-docs/**"
                            ).permitAll()
                            .requestMatchers("/admin/**").hasAnyRole("ADMIN", "SUPER_ADMIN")
                            .requestMatchers("/user/**").hasAnyRole("USER", "ADMIN", "SUPER_ADMIN")
                            .requestMatchers("/super-admin/**").hasAnyRole("SUPER_ADMIN")
                            .anyRequest().authenticated();
                })

                //? Cau hinh Xu ly loi ngoai le Authentication va Authorization
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(handlerEntryPoint)
                        .accessDeniedHandler(handleAccessDinedCustom)
                )

                //? Cau hinh Khong dung Session mac dinh cua Java (JSESSIONID)
                //? Moi Request phai mang theo token
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                //? Thuc hien xac thuc JWT thay vi chay mac dinh bo filter cua Spring Security
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        //? Cau hinh lay du lieu nguoi dung tu DB cho viec xac thuc va phan quyen trong chuoi filter security
        http.authenticationProvider(authenticationProviders.authenticationProvider());

        http.headers(headers -> headers
                .frameOptions(frame -> frame.sameOrigin())
                .contentSecurityPolicy(csp -> csp
                        .policyDirectives("default-src 'self'")
                )
        );

        //? Cau hinh vo hieu hoa
        http.httpBasic(AbstractHttpConfigurer::disable);

        //? Cau hinh vo hieu hoa trang login mac dinh cua spring Security khi ung dung khoi dong
        http.formLogin(AbstractHttpConfigurer::disable);
        return http.build();
    }
}
