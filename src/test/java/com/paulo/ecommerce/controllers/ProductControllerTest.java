package com.paulo.ecommerce.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paulo.ecommerce.entities.Product;
import com.paulo.ecommerce.dto.ProductRequestDTO;
import com.paulo.ecommerce.entities.Category;
import com.paulo.ecommerce.repositories.UserRepository;
import com.paulo.ecommerce.security.TokenService;
import com.paulo.ecommerce.services.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
@AutoConfigureMockMvc(addFilters = false)
//@WithMockUser(username = "user@example.com", roles={"CUSTOMER"})
public class ProductControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    ProductService productService;

    @MockitoBean
    TokenService tokenService;

    @MockitoBean
    UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void ProductController_CreateProduct_ReturnCreated() throws Exception {
        ProductRequestDTO request = sampleRequest();
        Product created = sampleProduct(5L);
        given(productService.saveProduct(any(ProductRequestDTO.class))).willReturn(created);

        mockMvc
                .perform(
                        post("/api/v1/products")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(5L))
                .andExpect(jsonPath("$.quantityAvailable").value(50))
                .andExpect(jsonPath("$.categoryId").value(3))
                .andExpect(jsonPath("$.categoryName").value("Controller Category"));
    }

    @Test
    void ProductController_GetProductById_ReturnsOk() throws Exception {
        Product product = sampleProduct(2L);
        given(productService.getProductById(2L)).willReturn(product);

        mockMvc
                .perform(get("/api/v1/products/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2L))
                .andExpect(jsonPath("$.name").value("Controller product"))
                .andExpect(jsonPath("$.quantityAvailable").value(50))
                .andExpect(jsonPath("$.categoryId").value(3))
                .andExpect(jsonPath("$.categoryName").value("Controller Category"));
    }

    @Test
    void ProductController_GetAllProducts_ReturnsOk() throws Exception {
        Product product = sampleProduct(1L);
        given(productService.getAllProducts()).willReturn(List.of(product));

        mockMvc
                .perform(get("/api/v1/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].quantityAvailable").value(50))
                .andExpect(jsonPath("$[0].categoryId").value(3))
                .andExpect(jsonPath("$[0].categoryName").value("Controller Category"));

    }

    @Test
    void ProductController_UpdateProduct_ReturnsOk() throws Exception {
        ProductRequestDTO toUpdate = sampleRequest();
        Product updated = sampleProduct(8L);
        given(productService.updateProduct(eq(8L), any(ProductRequestDTO.class))).willReturn(updated);

        mockMvc
                .perform(
                        put("/api/v1/products/8")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(toUpdate))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(8L))
                .andExpect(jsonPath("$.name").value("Controller product"))
                .andExpect(jsonPath("$.quantityAvailable").value(50))
                .andExpect(jsonPath("$.categoryId").value(3))
                .andExpect(jsonPath("$.categoryName").value("Controller Category"));
    }

    @Test
    void deleteProductReturnsNoContent() throws Exception {
        doNothing().when(productService).deleteProduct(4L);

        mockMvc.perform(delete("/api/v1/products/4")).andExpect(status().isNoContent());
    }

    private Product sampleProduct(Long id) {
        Category category = Category.builder()
                .id(3L)
                .name("Controller Category")
                .build();

        Product product = Product.builder()
                .name("Controller product")
                .description("Controller Product Description")
                .priceInCents(999)
                .quantityAvailable(50L)
                .category(category)
                .build();

        if (id != null) {
            product.setId(id);
        }

        return product;
    }

    private ProductRequestDTO sampleRequest() {
        return new ProductRequestDTO(
                "Controller product",
                "Controller Product Description",
                999,
                50L,
                3L
        );
    }
}
