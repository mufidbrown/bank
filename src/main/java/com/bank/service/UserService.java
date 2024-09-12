package com.bank.service;

import com.bank.config.JwtUtil;
import com.bank.dto.RegisterRequestDTO;
import com.bank.entity.User;
import com.bank.entity.UserProfile;
import com.bank.exception.UserNotFoundException;
import com.bank.repository.UserProfileRepository;
import com.bank.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
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
