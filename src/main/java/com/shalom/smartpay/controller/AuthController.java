package com.shalom.smartpay.controller;

import com.shalom.smartpay.dto.AuthResponse;
import com.shalom.smartpay.dto.LoginRequest;
import com.shalom.smartpay.dto.RegisterRequest;
import com.shalom.smartpay.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public AuthResponse register(@Valid @RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request);
    }

}