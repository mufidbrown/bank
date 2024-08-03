package com.dbs.controller;

import com.dbs.config.BaseResponse;
import com.dbs.dto.LoginRequestDTO;
import com.dbs.dto.RegisterRequestDTO;
import com.dbs.entity.User;
import com.dbs.jwt.JwtResponse;
import com.dbs.jwt.TokenRefreshRequest;
import com.dbs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


//import com.dbs.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

//    @PostMapping("/register")
//    public ResponseEntity<?> register(@RequestBody RegisterRequestDTO registerRequestDTO) {
//        userService.registerUser(registerRequestDTO.getUsername(), registerRequestDTO.getPassword(), registerRequestDTO.getEmail(), Roles.USER);
//        return ResponseEntity.ok("User registered successfully");
//    }


    @PostMapping("/register")
    public ResponseEntity<BaseResponse<User>> register(@RequestBody RegisterRequestDTO registerRequestDTO) {
        User user = userService.registerUser(registerRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(BaseResponse.ok("User registered successfully", user));
    }

    @PostMapping("/login")
    public ResponseEntity<BaseResponse<JwtResponse>> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        try {
            String token = userService.login(loginRequestDTO.getUsername(), loginRequestDTO.getPassword());
            return ResponseEntity.ok(BaseResponse.ok("Login successful", new JwtResponse(token)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(BaseResponse.error("Failed to login: " + e.getMessage()));
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<BaseResponse<JwtResponse>> refresh(@RequestBody TokenRefreshRequest request) {
        try {
            String newToken = userService.refresh(request.getOldToken());
            return ResponseEntity.ok(BaseResponse.ok("Token refreshed successfully", new JwtResponse(newToken)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(BaseResponse.error("Failed to refresh token: " + e.getMessage()));
        }
    }

//    @PostMapping("/authenticate")
//    public ResponseEntity<String> authenticateUser(@RequestBody UserAuthenticationRequest request) {
//        try {
//            // Autentikasi pengguna
//            Authentication authentication = authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
//            );
//
//            // Generate JWT token
//            final String jwt = jwtUtil.generateToken(authentication.getName());
//
//            return ResponseEntity.ok(jwt);
//        } catch (AuthenticationException e) {
//            return ResponseEntity.status(401).body("Authentication failed");
//        }
//    }



    @PostMapping("/logout")
    public ResponseEntity<String> logoutUser() {
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok("Logout berhasil");
    }


}