package com.gamemini.api.configs.springWeb.jwt;

import com.gamemini.api.configs.springWeb.authentication.CustomUserDetailService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JWTGenerator tokenGenerator;
    private CustomUserDetailService customUserDetailsService;

    @Autowired
    public JwtAuthenticationFilter(JWTGenerator tokenGenerator, CustomUserDetailService customUserDetailsService) {
        this.tokenGenerator = tokenGenerator;
        this.customUserDetailsService = customUserDetailsService;
    }

    ///  Lay Token tu Header basic_auth:
    private String getJWTFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        System.out.println("bearerToken: " + bearerToken);

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }

    /// BE lay Token tu Cookie trong moi request cua client (Client tu gui kem cookie trong moi request).
    private String getTokenFromCookieOrigin(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        if (cookies == null) return null;

        for (Cookie cookie : cookies) {
            logger.debug(cookie.toString() + " from Class " + this.getClass().getSimpleName());
            if ("access_token".equals(cookie.getName()))
                return cookie.getValue();
        }
        return null;
    }

    @Override
    public void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain
    ) throws IOException, ServletException {
        String jwtToken = getTokenFromCookieOrigin(request);

        if (jwtToken != null && tokenGenerator.validateToken(jwtToken)) {
            System.out.println("jwtToken: " + jwtToken);
            String username = tokenGenerator.getUsernameFromJWT(jwtToken);

            /// UserDetails lay thong tin nguoi dung tu DB va kiem tra lao Role cua nguoi dung, nhu:
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

            /// Tao doi tuong "Trang thai nguoi dung dang dang nhap thanh cong" tu Class Object UsernamePasswordAuthenticationToken
            /// Trang thai dang nhap thanh cong cua nguoi dung voi tham so "credentials" la null vi password chi can cho lan dang nhap dau tien. Khi nguoi dung
            /// o thai dang nhap thanh cong thi nhung phien truy cap tiep theo chi can token cho moi request.
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

            /// Set request cua nguoi dung co trang thai truy cap thanh cong nguon truy cap web
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            /// Dua doi tuong "trang thai nguoi dung dang nhap thanh cong" vao Spring SecurityContext
            /// Dieu nay giup cho Spring Security biet duoc request nay da duoc xac thuc
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        } else {
            System.out.println("jwtToken: " + jwtToken);
            System.out.println("Token was removed in the cookie or expired!");
        }
        logger.debug("doFilterInternal filter >>>");
        chain.doFilter(request, response);
    }
}


