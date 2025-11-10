package com.paulo.ecommerce.product;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void ProductRepository_SaveAndFind_SaveAndFindsProduct() {
        Product product = sampleProduct("save-find");
        Product saved = productRepository.save(product);
        Optional<Product> found = productRepository.findById(saved.getId());

        assertNotNull(saved.getId());
        assertTrue(found.isPresent());
    }

    @Test
    void ProductRepository_Delete_RemovesProduct() {
        Product saved = productRepository.save(sampleProduct("delete"));

        productRepository.deleteById(saved.getId());

        assertFalse(productRepository.existsById(saved.getId()));
    }

    @Test
    void ProductRepository_FindAll_ReturnMoreThanOneProduct() {
        productRepository.save(sampleProduct("first"));
        productRepository.save(sampleProduct("second"));

        List<Product> productList = productRepository.findAll();

        assertNotNull(productList);
        assertEquals(2, productList.size());
    }

    private Product sampleProduct(String suffix) {
        Category category = categoryRepository.save(
                Category.builder()
                        .name("Category " + suffix)
                        .build()
        );

        return Product.builder()
                .name("Repository product " + suffix)
                .description("Repository Product Description " + suffix)
                .priceInCents(999)
                .quantityAvailable(25L)
                .category(category)
                .build();
    }
}
