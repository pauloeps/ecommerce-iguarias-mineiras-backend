package com.paulo.ecommerce.security;

import com.paulo.ecommerce.user.Role;
import com.paulo.ecommerce.user.User;
import com.paulo.ecommerce.user.UserRepository;
import com.paulo.ecommerce.user.AddressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final AddressService addressService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequestDTO body) {
        User user = this.userRepository.findByEmail(body.email()).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        if(passwordEncoder.matches(body.password(), user.getPasswordHash())) {
            String token = this.tokenService.generateToken(user);
            return ResponseEntity.ok(new ResponseDTO(user.getName(), token));
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/register")
    public ResponseEntity register(@Valid @RequestBody RegisterRequestDTO body) {
        Optional<User> existingUser = this.userRepository.findByEmail(body.email());
        if (existingUser.isPresent()) {
            return ResponseEntity.badRequest().build();
        }

        User newUser = new User();
        newUser.setPasswordHash(passwordEncoder.encode(body.password()));
        newUser.setEmail(body.email());
        newUser.setName(body.name());
        newUser.setCpf(body.cpf());
        newUser.setRole(Role.CUSTOMER);

        User savedUser = this.userRepository.save(newUser);

        var savedAddress = addressService.syncUserAddress(savedUser, body.address());
        if (savedAddress != null) {
            savedUser.setAddress(savedAddress);
            savedUser = this.userRepository.save(savedUser);
        }

        String token = this.tokenService.generateToken(savedUser);
        return ResponseEntity.ok(new ResponseDTO(savedUser.getName(), token));
    }
}
