package com.example.footgate.controller;

import com.example.footgate.config.JwtUtilsHelper;
import com.example.footgate.entities.Cart;
import com.example.footgate.entities.USER_ROLE;
import com.example.footgate.entities.User;
import com.example.footgate.repository.CartRepository;
import com.example.footgate.repository.UserRepository;
import com.example.footgate.request.LoginRequest;
import com.example.footgate.response.AuthResponse;
import com.example.footgate.service.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CustomUserDetailService customUserDetailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtilsHelper jwtUtilsHelper;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CartRepository cartRepository;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) {
        User isEmailExist = userRepository.findByEmail(user.getEmail());
        if (isEmailExist != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new AuthResponse(null, "Email is already used with another account", null));
        }

        User savedUser = new User();
        savedUser.setEmail(user.getEmail());
        savedUser.setFullName(user.getFullName());
        savedUser.setRole(user.getRole());
        savedUser.setPassword(passwordEncoder.encode(user.getPassword()));
        savedUser = userRepository.save(savedUser);

        Cart cart = new Cart();
        cart.setCustomer(savedUser);
        cartRepository.save(cart);

        Authentication authentication = new UsernamePasswordAuthenticationToken(savedUser.getEmail(), savedUser.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);


        // Generate token từ authentication
        String jwt = jwtUtilsHelper.generateToken(authentication);
        AuthResponse authResponse = new AuthResponse(jwt, "Register success", savedUser.getRole());
        return ResponseEntity.status(HttpStatus.CREATED).body(authResponse);
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> signin(@RequestBody LoginRequest req) {
        String username = req.getEmail();
        String password = req.getPassword();

        // Perform authentication with username and password
        Authentication authentication = authenticate(username, password);

        // Generate token from authentication
        String jwt = jwtUtilsHelper.generateToken(authentication);

        // Get role from authentication to set in AuthResponse
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String role = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElse("");

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setMessage("Login success");
        authResponse.setRole(USER_ROLE.valueOf(role)); // Set role into AuthResponse

        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }

    private Authentication authenticate(String username, String password) {
        UserDetails userDetails = customUserDetailService.loadUserByUsername(username);
        if (userDetails == null) {
            throw new BadCredentialsException("Invalid username........");
        }
        if(!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid password........");
        }

        // Authenticate user information with password
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());

        // Return authentication if information is valid
        return authenticationManager.authenticate(authenticationToken);
    }



}
