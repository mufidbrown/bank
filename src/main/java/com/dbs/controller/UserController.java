package com.dbs.controller;

import com.dbs.config.JwtUtil;
import com.dbs.entity.User;
import com.dbs.entity.UserProfile;
import com.dbs.payload.UserAuthenticationRequest;
import com.dbs.payload.UserRegistrationRequest;
import com.dbs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


//import com.dbs.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserRegistrationRequest request) {
        userService.registerUser(request);
        return ResponseEntity.ok("Pengguna baru berhasil didaftarkan");
    }

    @PostMapping("/authenticate")
    public ResponseEntity<String> authenticateUser(@RequestBody UserAuthenticationRequest request) {
        try {
            // Autentikasi pengguna
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

            // Generate JWT token
            final String jwt = jwtUtil.generateToken(authentication.getName());

            return ResponseEntity.ok(jwt);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).body("Authentication failed");
        }
    }

//    @PostMapping("/authenticate")
//    public ResponseEntity<String> authenticateUser(@RequestBody UserAuthenticationRequest request) {
//        AuthenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
//        );
//
//        final UserDetails userDetails = userService.loadUserByUsername(request.getUsername());
//        final String jwt = jwtUtil.generateToken(userDetails.getUsername());
//
//        return ResponseEntity.ok(jwt);
//    }

    @PostMapping("/refresh-token")
    public ResponseEntity<String> refreshToken(@RequestHeader("Authorization") String token) {
        String refreshedToken = jwtUtil.refreshToken(token.substring(7));
        return ResponseEntity.ok(refreshedToken);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logoutUser() {
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok("Logout berhasil");
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserProfile> getUserProfile(@PathVariable Long userId) {
        UserProfile profile = userService.getUserProfileById(userId);
        return ResponseEntity.ok(profile);
    }

    @GetMapping("/profile")
    public ResponseEntity<User> getUserProfile(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.getUserByUsername(userDetails.getUsername());
        return ResponseEntity.ok(user);
    }


}
