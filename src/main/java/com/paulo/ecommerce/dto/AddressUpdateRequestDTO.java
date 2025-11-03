package com.paulo.ecommerce.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AddressUpdateRequestDTO(
        @NotBlank(message = "O CEP não pode estar vazio")
        @Size(max = 9, message = "O CEP deve ter no máximo 9 caracteres")
        String cep,

        @NotBlank(message = "A rua não pode estar vazia")
        @Size(max = 120, message = "A rua deve ter no máximo 120 caracteres")
        String street,

        @NotBlank(message = "O número não pode estar vazio")
        @Size(max = 10, message = "O número deve ter no máximo 10 caracteres")
        String number,

        @NotBlank(message = "A cidade não pode estar vazia")
        @Size(max = 60, message = "A cidade deve ter no máximo 60 caracteres")
        String city,

        @NotBlank(message = "A UF não pode estar vazia")
        @Size(min = 2, max = 2, message = "A UF deve ter 2 caracteres")
        String uf
) {
}
