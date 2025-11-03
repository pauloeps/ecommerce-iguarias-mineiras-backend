package com.paulo.ecommerce.services;

import com.paulo.ecommerce.dto.ProductRequestDTO;
import com.paulo.ecommerce.entities.Category;
import com.paulo.ecommerce.entities.Product;
import com.paulo.ecommerce.repositories.CategoryRepository;
import com.paulo.ecommerce.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public Product saveProduct(ProductRequestDTO request) {
        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));

        Product product = Product.builder()
                .name(request.name())
                .description(request.description())
                .priceInCents(request.priceInCents())
                .quantityAvailable(request.quantityAvailable())
                .category(category)
                .build();

        return productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
    }

    public Product updateProduct(Long id, ProductRequestDTO request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));

        product.setName(request.name());
        product.setDescription(request.description());
        product.setPriceInCents(request.priceInCents());
        product.setQuantityAvailable(request.quantityAvailable());
        product.setCategory(category);

        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
