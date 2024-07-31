package com.dbs.service;

import com.dbs.config.JwtUtil;
import com.dbs.dto.RegisterRequestDTO;
import com.dbs.entity.User;
import com.dbs.entity.UserProfile;
import com.dbs.repository.UserProfileRepository;
import com.dbs.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserService  {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

//    @Autowired
//    private UserDetailsService userDetailsService;
//
//
//
//    public User registerUser(String username, String password, String email, Roles roles) {
//        if (userRepository.existsByUsername(username) || userRepository.existsByEmail(email)) {
//            throw new RuntimeException("Username or Email already exists");
//        }
//
//        User user = new User();
//        UserProfile userProfile = new UserProfile();
//        user.setUsername(username);
//        user.setPassword(passwordEncoder.encode(password));
//        user.setEmail(email);
//        user.setRoles(roles);
///*
//        tambahan 31/07/24
//*/
//        user.setUserProfile(userProfile);
////        userRepository.save(user);
//
//        return userRepository.save(user);
//    }
//
//    public String login(String username, String password) {
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(username, password));
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//        return jwtUtil.generateToken(userDetails);
//    }
//
//    public String refresh(String oldToken) {
//        String username = jwtUtil.extractUsername(oldToken);
//        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//        return jwtUtil.generateToken(userDetails);
//    }

    @Autowired
    private UserDetailsService userDetailsService;

    public User registerUser(RegisterRequestDTO registerRequestDTO) {
        User user = new User();
        user.setUsername(registerRequestDTO.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequestDTO.getPassword()));
        user.setEmail(registerRequestDTO.getEmail());
        user.setRoles(registerRequestDTO.getRoles());

        UserProfile userProfile = new UserProfile();
        userProfile.setFullName(registerRequestDTO.getFullName());
        userProfile.setAddress(registerRequestDTO.getAddress());
        userProfile.setPhoneNumber(registerRequestDTO.getPhoneNumber());
        userProfile.setUser(user);

        user.setUserProfile(userProfile);
        userRepository.save(user);

        return user;
    }


    public String login(String username, String password) throws AuthenticationException {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return jwtUtil.generateToken(userDetails);
    }

    public String refresh(String oldToken) {
        String username = jwtUtil.extractUsername(oldToken);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (jwtUtil.validateToken(oldToken, userDetails)) {
            return jwtUtil.generateToken(userDetails);
        }
        return null;
    }




    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}


//    public String login(String username, String password) {
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(username, password));
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//        return jwtUtil.generateToken(userDetails);
//    }

//    public String refresh(String oldToken) {
//        String username = jwtUtil.extractUsername(oldToken);
//        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//        return jwtUtil.generateToken(userDetails);
//    }
