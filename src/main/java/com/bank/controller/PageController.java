package com.bank.controller;

import com.bank.dto.AccountRequestDTO;
import com.bank.dto.LoginRequestDTO;
import com.bank.dto.RegisterRequestDTO;
import com.bank.entity.User;
import com.bank.jwt.TokenRefreshRequest;
import com.bank.service.AccountService;
import com.bank.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/auth/v1")
public class PageController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("registerRequestDTO", new RegisterRequestDTO());
        return "register";
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("loginRequestDTO", new LoginRequestDTO());
        return "login";
    }

    @GetMapping("/refresh-token")
    public String showRefreshTokenForm(Model model) {
        model.addAttribute("tokenRefreshRequest", new TokenRefreshRequest());
        return "refresh-token";
    }

    @GetMapping("/logout")
    public String showLogoutPage() {
        return "logout";
    }

    @GetMapping("/create-account")
    public String showCreateAccountPage(Model model) {
        model.addAttribute("accountRequestDTO", new AccountRequestDTO());
        return "create-account"; // Nama file Thymeleaf untuk pembuatan akun
    }

    @GetMapping("/users")
    public String getAllUsers(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "users"; // Nama file Thymeleaf untuk daftar pengguna
    }
}

