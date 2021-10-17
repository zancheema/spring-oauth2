package com.zaincheema.auth.controller;

import com.zaincheema.auth.auth.UserPrincipal;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @GetMapping("user/me")
    public UserPrincipal getCurrentUser(@AuthenticationPrincipal UserPrincipal principal) {
        return principal;
    }
}
