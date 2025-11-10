package com.paulo.ecommerce.product;

public record ProductResponseDTO(
        Long id,
        String name,
        String description,
        Integer priceInCents,
        Long quantityAvailable,
        Long categoryId,
        String categoryName
) {
    public static ProductResponseDTO fromEntity(Product product) {
        Category category = product.getCategory();
        Long categoryId = category != null ? category.getId() : null;
        String categoryName = category != null ? category.getName() : null;

        return new ProductResponseDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPriceInCents(),
                product.getQuantityAvailable(),
                categoryId,
                categoryName
        );
    }
}
