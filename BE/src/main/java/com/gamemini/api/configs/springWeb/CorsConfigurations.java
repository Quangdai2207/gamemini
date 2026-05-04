package com.gamemini.api.configs.springWeb;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * Doi voi <strong style="color:#EEEEE">configuration.setExposedHeaders(exposedHeaders)</strong> nghia la BE cho phep FE doc du lieu trong
 * header khi BE gui du lieu
 * vao trong header.
 * <h6>Vi Du:</h6>
 * <p>
 *    exposedHeaders la "Authorization", nghia la BE cho phep FE doc du lieu trong hedaer co ten la "Authorization"
 *    Trong thuc te, khi nguoi dung Login thanh cong, Neu BE khong set token len Cookie cho Origin Client, thi BE co the gui
 *    Token thong qua Header thay vi trong phan body Response.
 * </p>
 *        <p>BE: return ResponseEntity.ok().header("Authorization", token).body("Login success");</p>
 *
 * <pre>
 * FE lay token tu Header BE dinh kem:
 *              var URL = "http://localhost:8080"
 *              fetch(URL, {
 *                  method: "GET",
 *                  credential: true
 *              }).then((res) => console.log(res.header("Authorization"))) ✅ đọc được
 *
 *              Hoac FE co the doc bang Axios
 *              axios.get(URL, {
 *                  withCredentials: true
 *              })
 *              .then(res => {
 *                  console.log(res.headers["Authorization"]); // ✅ đọc được
 *              });
 *        </pre>
 * */
@Configuration
public class CorsConfigurations implements WebMvcConfigurer {
    private final List<String> allowedOrigins = List.of("http://localhost:5173", "http://localhost:8080", "ttp://localhost:3000");
    private final List<String> allowedMethods = List.of("GET", "POST", "PUT", "DELETE", "OPTIONS");
    private final List<String> allowedHeaders = List.of("Authorization", "Content-Type", "X-Captcha-Token", "X-VerifyAccount-Token", "X-Register-Token");
    private final List<String> exposedHeaders = List.of("Authorization");
    private final Long MAX_AGE = 3600L;

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(allowedOrigins);    /// Cho Phep cac client truy cap
        configuration.setAllowedMethods(allowedMethods);    /// Cho phep cac giai thuc HTTP
        configuration.setAllowedHeaders(allowedHeaders);    /// Cho phep cac HEADER Custom gui kem
        configuration.setAllowCredentials(true);            /// Cho phep FE set Credential khi gui request
        configuration.setMaxAge(MAX_AGE);                   /// Chong spam request
        configuration.setExposedHeaders(exposedHeaders);    ///  Cho phep FE doc Authorization tu BE

        /// Dang ky ap dung cho tat ca end-points khi client truy cap
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
