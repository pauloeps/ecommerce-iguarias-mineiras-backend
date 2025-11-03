package com.paulo.ecommerce.services;

import com.paulo.ecommerce.dto.UserResponseDTO;
import com.paulo.ecommerce.dto.UserUpdateRequestDTO;
import com.paulo.ecommerce.entities.User;
import com.paulo.ecommerce.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final AddressService addressService;

    public UserResponseDTO getCurrentUser(Authentication authentication) {
        User authenticatedUser = resolveAuthenticatedUser(authentication);

        User user = userRepository.findById(authenticatedUser.getId())
                .orElseThrow(() -> new IllegalStateException("Usuário autenticado inválido"));

        return UserResponseDTO.fromEntity(user);
    }

    @Transactional
    public UserResponseDTO updateCurrentUser(Authentication authentication, UserUpdateRequestDTO request) {
        User authenticatedUser = resolveAuthenticatedUser(authentication);

        User user = userRepository.findById(authenticatedUser.getId())
                .orElseThrow(() -> new IllegalStateException("Usuário autenticado inválido"));

        user.setName(request.name());
        user.setEmail(request.email());
        user.setCpf(request.cpf());

        user.setAddress(addressService.syncUserAddress(user, request.address()));

        User savedUser = userRepository.save(user);
        return UserResponseDTO.fromEntity(savedUser);
    }

    private User resolveAuthenticatedUser(Authentication authentication) {
        if (authentication == null || authentication.getPrincipal() == null) {
            throw new IllegalStateException("Usuário autenticado inválido");
        }

        Object principal = authentication.getPrincipal();

        if (principal instanceof User user) {
            return user;
        }

        throw new IllegalStateException("Usuário autenticado inválido");
    }
}
