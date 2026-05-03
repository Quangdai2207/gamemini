package com.gamemini.api.services.auth;

import com.gamemini.api.configs.springWeb.jwt.JWTGenerator;
import com.gamemini.api.dtos.requestes.auth.RequestLogin;
import com.gamemini.api.dtos.requestes.auth.RequestRegister;
import com.gamemini.api.dtos.responses.ApiResponse;
import com.gamemini.api.dtos.responses.auth.AuthData;
import com.gamemini.api.entities.Role;
import com.gamemini.api.entities.UserEntity;
import com.gamemini.api.entities.UserRole;
import com.gamemini.api.exceptions.BadRequestException;
import com.gamemini.api.mappers.UserMapper;
import com.gamemini.api.repositories.RoleRepository;
import com.gamemini.api.repositories.UserRepository;
import com.gamemini.api.repositories.UserRoleRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements IAuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTGenerator jwtGenerator;


    @Override
    public ResponseEntity<ApiResponse<AuthData>> register(RequestRegister body) {
        if (userRepository.findByUsername(body.getUsername()).isPresent())
            throw new BadRequestException("Username " + body.getPassword() + " is already in use");

        if (!body.getPassword().equals(body.getConfirmPassword()))
            throw new BadRequestException("Password and confirmPassword not match.");

        UserEntity user = UserMapper.requestRegisterToUser(body);
        String password = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(password);

        user = userRepository.save(user);
        Role isUser = roleRepository.findByName("USER");

        UserRole userRole = UserRole.builder()
                .role(isUser)
                .user(user)
                .build();

        userRoleRepository.save(userRole);
        return ApiResponse.ok(AuthData
                .builder()
                .username(user.getUsername())
                .token("")
                .build(), "Registered successfully.");
    }

    @Override
    public ResponseEntity<ApiResponse<AuthData>> login(
            RequestLogin body,
            HttpServletResponse response
    ) {
        /// Ngay tai buoc nay, neu nguoi dung nhap sai username hoac password thi loi ngoai le
        /// BadCredentialException duoc GlobalException xu ly ngay lap tuc.
        /// Neu ten nguoi dung va password sau khi hash khop voi DB thi nguoi dung dang nhap thanh cong
        Authentication authentication = authenticationManager.authenticate( new UsernamePasswordAuthenticationToken(body.getUsername(), body.getPassword()));

        /// Co du lieu nguoi dung sau khi authentication thanh cong -> Dang nhap thanh cong
        UserDetails user = (UserDetails) authentication.getPrincipal();

        /**
         * Sau khi co du lieu nguoi dung tu AuthenticationManager, co the dung du lieu nay de truy van du lieu cho mot so cac tinh nang nhu:
         *  1. Kiem tra nguoi dung da bi vo hieu hoa chua
         *  2. Kiem tra status nguoi dung da active thong qua xac thuc email chua.
         *  3. Mot so cac tinh nang khac trong viec xac thuc nguoi dung.
         *
         *  =>> Neu sau khi xac minh toan bo thong nguoi nguoi dung hop le thi thuc hien buoc set token len cookie cho origin hoac co the cap tokem
         *  cho nguoi dung thong qua header (Neu cap token cho Client thi nen cap thong qua Header thay vi body Response)
         * */

        System.out.println("Test UserDetails Info: " + user.toString());
        System.out.println("Test authentication Info: " + authentication.toString());

        System.out.println("UserDetails Object => " + user.getUsername()); /// Kiem tra thong tin username cua user
        System.out.println("Get authorities() from userDetails => " + user.getAuthorities().toArray().toString()); /// Kiem tra thong tin username cua user
        user.getAuthorities().forEach(ga -> System.out.println(ga.getAuthority()));

        /// Tao token cho nguoi dung sau khi xac thuc va login thanh cong
        String token = jwtGenerator.generateToken(authentication);

        /// Thiet lap cau hinh cho Cookie.
        Cookie cookie = new Cookie("access_token", token);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);

        /// dung Response Object push token len Cookie Origin
        response.addCookie(cookie);


        return ApiResponse.ok(AuthData.builder()
                .token("abc")
                .username(authentication.getName())
                .build(), "Authenticated successfully.");
    }
}
