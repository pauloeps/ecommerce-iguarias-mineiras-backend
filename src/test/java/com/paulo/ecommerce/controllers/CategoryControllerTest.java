package com.paulo.ecommerce.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paulo.ecommerce.entities.Category;
import com.paulo.ecommerce.repositories.UserRepository;
import com.paulo.ecommerce.security.TokenService;
import com.paulo.ecommerce.services.CategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CategoryController.class)
@AutoConfigureMockMvc(addFilters = false)
public class CategoryControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    CategoryService categoryService;

    @MockitoBean
    TokenService tokenService;

    @MockitoBean
    UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void CategoryController_CreateCategory_ReturnCreated() throws Exception {
        Category toCreate = sampleCategory(null, "Controller Category");
        Category created = sampleCategory(5L, "Controller Category");
        given(categoryService.saveCategory(any(Category.class))).willReturn(created);

        mockMvc
                .perform(
                        post("/api/v1/categories")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(toCreate))
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(5L))
                .andExpect(jsonPath("$.name").value("Controller Category"));
    }

    @Test
    void CategoryController_GetCategoryById_ReturnsOk() throws Exception {
        Category category = sampleCategory(2L, "Controller Category");
        given(categoryService.getCategoryById(2L)).willReturn(category);

        mockMvc
                .perform(get("/api/v1/categories/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2L))
                .andExpect(jsonPath("$.name").value("Controller Category"));
    }

    @Test
    void CategoryController_GetAllCategories_ReturnsOk() throws Exception {
        Category category = sampleCategory(1L, "Controller Category");
        given(categoryService.getAllCategories()).willReturn(List.of(category));

        mockMvc
                .perform(get("/api/v1/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(1L));

    }

    @Test
    void CategoryController_UpdateCategory_ReturnsOk() throws Exception {
        Category toUpdate = sampleCategory(null, "Updated Category");
        Category updated = sampleCategory(8L, "Updated Category");
        given(categoryService.updateCategory(eq(8L), any(Category.class))).willReturn(updated);

        mockMvc
                .perform(
                        put("/api/v1/categories/8")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(toUpdate))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(8L))
                .andExpect(jsonPath("$.name").value("Updated Category"));
    }

    @Test
    void deleteCategoryReturnsNoContent() throws Exception {
        doNothing().when(categoryService).deleteCategory(4L);

        mockMvc.perform(delete("/api/v1/categories/4")).andExpect(status().isNoContent());
    }

    private Category sampleCategory(Long id, String name) {
        Category category = Category.builder()
                .name(name)
                .build();

        if (id != null) {
            category.setId(id);
        }

        return category;
    }
}
