package com.paulo.ecommerce.user;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserUpdateRequestDTO(
        @NotBlank(message = "O nome não pode estar vazio")
        @Size(max = 120, message = "O nome deve ter no máximo 120 caracteres")
        String name,

        @NotBlank(message = "O email não pode estar vazio")
        @Email(message = "O email informado é inválido")
        @Size(max = 120, message = "O email deve ter no máximo 120 caracteres")
        String email,

        @NotBlank(message = "O CPF não pode estar vazio")
        @Size(max = 14, message = "O CPF deve ter no máximo 14 caracteres")
        String cpf,

        @Valid
        AddressUpdateRequestDTO address
) {
}
