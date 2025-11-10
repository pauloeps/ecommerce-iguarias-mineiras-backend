package com.paulo.ecommerce.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductResponseDTO saveProduct(ProductRequestDTO request) {
        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));

        Product product = Product.builder()
                .name(request.name())
                .description(request.description())
                .priceInCents(request.priceInCents())
                .quantityAvailable(request.quantityAvailable())
                .category(category)
                .build();

        Product created = productRepository.save(product);
        return ProductResponseDTO.fromEntity(created);
    }

    public List<ProductResponseDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products
                .stream()
                .map(ProductResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public ProductResponseDTO getProductById(Long id) {
        Product product = productRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        return ProductResponseDTO.fromEntity(product);
    }

    public ProductResponseDTO updateProduct(Long id, ProductRequestDTO request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));

        product.setName(request.name());
        product.setDescription(request.description());
        product.setPriceInCents(request.priceInCents());
        product.setQuantityAvailable(request.quantityAvailable());
        product.setCategory(category);

        Product updated = productRepository.save(product);

        return ProductResponseDTO.fromEntity(updated);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
