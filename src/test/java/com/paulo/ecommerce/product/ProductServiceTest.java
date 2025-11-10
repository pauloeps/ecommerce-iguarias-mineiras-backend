package com.paulo.ecommerce.product;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void ProductService_Save_SavesProduct() {
        ProductRequestDTO request = sampleRequest();
        Category category = sampleCategory();
        when(categoryRepository.findById(3L)).thenReturn(Optional.of(category));

        Product saved = sampleProduct(10L);
        when(productRepository.save(any(Product.class))).thenReturn(saved);

        ProductResponseDTO result = productService.saveProduct(request);

        assertEquals(10L, result.id());
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void ProductService_GetAll_GetsAllProducts() {
        Product product = sampleProduct(1L);
        when(productRepository.findAll()).thenReturn(List.of(product));

        List<ProductResponseDTO> products = productService.getAllProducts();

        assertEquals(1, products.size());
        assertEquals(product.getId(), products.get(0).id());
    }

    @Test
    void ProductService_GetById_GetsProduct() {
        Product product = sampleProduct(1L);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        ProductResponseDTO found = productService.getProductById(1L);

        assertNotNull(found);
    }

    @Test
    void ProductService_Update_UpdatesProduct() {
        Product existing = sampleProduct(1L);
        ProductRequestDTO request = sampleRequest();
        Category category = sampleCategory();

        when(productRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(categoryRepository.findById(3L)).thenReturn(Optional.of(category));
        when(productRepository.save(existing)).thenReturn(existing);

        ProductResponseDTO updateReturn = productService.updateProduct(1L, request);

        assertNotNull(updateReturn);
        verify(productRepository).save(existing);
    }

    @Test
    void ProductService_Delete_DeletesProduct() {
        doNothing().when(productRepository).deleteById(4L);

        productService.deleteProduct(4L);

        verify(productRepository).deleteById(4L);
    }

    private Product sampleProduct(Long id) {
        Category category = sampleCategory();

        Product product = Product.builder()
                .name("Service product")
                .description("Service Product Description")
                .priceInCents(999)
                .quantityAvailable(15L)
                .category(category)
                .build();

        if (id != null) {
            product.setId(id);
        }

        return product;
    }

    private Category sampleCategory() {
        Category category = Category.builder()
                .name("Service Category")
                .build();
        category.setId(3L);
        return category;
    }

    private ProductRequestDTO sampleRequest() {
        return new ProductRequestDTO(
                "Service product",
                "Service Product Description",
                999,
                15L,
                3L
        );
    }
}
