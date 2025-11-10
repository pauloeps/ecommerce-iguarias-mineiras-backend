package com.paulo.ecommerce.user;

public record UserResponseDTO(
        Long id,
        String email,
        String name,
        String cpf,
        Role role,
        AddressResponseDTO address
) {
    public static UserResponseDTO fromEntity(User user) {
        if (user == null) {
            return null;
        }
        return new UserResponseDTO(
                user.getId(),
                user.getEmail(),
                user.getName(),
                user.getCpf(),
                user.getRole(),
                AddressResponseDTO.fromEntity(user.getAddress())
        );
    }
}
