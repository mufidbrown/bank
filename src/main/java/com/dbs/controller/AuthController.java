package com.dbs.controller;

import com.dbs.dto.LoginRequestDTO;
import com.dbs.dto.RegisterRequestDTO;
import com.dbs.entity.Enum.Roles;
import com.dbs.jwt.JwtResponse;
import com.dbs.jwt.TokenRefreshRequest;
import com.dbs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequestDTO registerRequestDTO) {
        userService.registerUser(registerRequestDTO.getUsername(), registerRequestDTO.getPassword(), registerRequestDTO.getEmail(), Roles.USER);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        String token = userService.login(loginRequestDTO.getUsername(), loginRequestDTO.getPassword());
        return ResponseEntity.ok(new JwtResponse(token));
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody TokenRefreshRequest request) {
        String newToken = userService.refresh(request.getOldToken());
        return ResponseEntity.ok(new JwtResponse(newToken));
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



//    @PostMapping("/refresh-token")
//    public ResponseEntity<String> refreshToken(@RequestHeader("Authorization") String token) {
//        String refreshedToken = jwtUtil.refreshToken(token.substring(7));
//        return ResponseEntity.ok(refreshedToken);
//    }



    @PostMapping("/logout")
    public ResponseEntity<String> logoutUser() {
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok("Logout berhasil");
    }

//    @GetMapping("/{userId}")
//    public ResponseEntity<UserProfile> getUserProfile(@PathVariable Long userId) {
//        UserProfile profile = userService.getUserProfileById(userId);
//        return ResponseEntity.ok(profile);
//    }
//
//    @GetMapping("/profile")
//    public ResponseEntity<User> getUserProfile(@AuthenticationPrincipal UserDetails userDetails) {
//        User user = userService.getUserByUsername(userDetails.getUsername());
//        return ResponseEntity.ok(user);
//    }


}
