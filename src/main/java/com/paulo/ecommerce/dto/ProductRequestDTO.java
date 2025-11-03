package com.paulo.ecommerce.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record ProductRequestDTO(
        @NotBlank(message = "O nome do produto não pode estar vazio")
        @Size(max = 100, message = "O nome do produto deve ter no máximo 100 caracteres")
        String name,

        @Size(max = 500, message = "A descrição deve ter no máximo 500 caracteres")
        String description,

        @NotNull(message = "O preço é obrigatório")
        @Positive(message = "O preço deve ser maior que zero")
        Integer priceInCents,

        @NotNull(message = "A quantidade disponível é obrigatória")
        @PositiveOrZero(message = "A quantidade disponível deve ser maior ou igual a zero")
        Long quantityAvailable,

        @NotNull(message = "A categoria é obrigatória")
        Long categoryId
) {
}
