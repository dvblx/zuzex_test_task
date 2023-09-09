package com.zuzex.testTask.rest;

import com.zuzex.testTask.security.AuthRequest;
import com.zuzex.testTask.security.AuthResponse;
import com.zuzex.testTask.security.JWTUtil;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtTokenUtil;

    public AuthController(AuthenticationManager authenticationManager, JWTUtil jwtTokenUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PostMapping("/authenticate")
    @ResponseStatus(HttpStatus.OK)
    public AuthResponse createAuthenticationToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getName(), authRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Неверный логин или пароль", e);
        }
        String jwt = jwtTokenUtil.generateToken((UserDetails) authentication.getPrincipal());
        return new AuthResponse(jwt);
    }
}
