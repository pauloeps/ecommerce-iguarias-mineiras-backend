package com.paulo.ecommerce.cart;

public record CartItemResponseDTO(
        Long id,
        Long productId,
        String productNameSnapshot,
        String productPriceSnapshot,
        Long quantity
) {
    public static CartItemResponseDTO fromEntity(CartItem cartItem) {
        Long productIdValue = cartItem.getProduct() != null ? cartItem.getProduct().getId() : null;
        return new CartItemResponseDTO(
                cartItem.getId(),
                productIdValue,
                cartItem.getProductNameSnapshot(),
                cartItem.getProductPriceSnapshot(),
                cartItem.getQuantity()
        );
    }
}
