package com.paulo.ecommerce.dto;

import com.paulo.ecommerce.entities.Cart;

import java.util.List;
import java.util.stream.Collectors;

public record CartResponseDTO(
        Long id,
        Long userId,
        List<CartItemResponseDTO> items
) {
    public static CartResponseDTO fromEntity(Cart cart) {
        List<CartItemResponseDTO> itemResponses = cart.getItems().stream()
                .map(CartItemResponseDTO::fromEntity)
                .collect(Collectors.toList());

        return new CartResponseDTO(
                cart.getId(),
                cart.getUserId(),
                itemResponses
        );
    }
}
