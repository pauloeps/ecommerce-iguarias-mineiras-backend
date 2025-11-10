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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    @Test
    void CategoryRepository_Save_SavesCategory() {
        Category toCreate = sampleCategory(null, "Service Category");
        Category saved = sampleCategory(10L, "Service Category");
        when(categoryRepository.save(toCreate)).thenReturn(saved);

        Category result = categoryService.saveCategory(toCreate);

        assertEquals(10L, result.getId());
        verify(categoryRepository).save(toCreate);
    }

    @Test
    void CategoryRepository_GetAll_GetsAllCategories() {
        Category category = sampleCategory(1L, "List Category");
        when(categoryRepository.findAll()).thenReturn(List.of(category));

        List<Category> categories = categoryService.getAllCategories();

        assertEquals(1, categories.size());
        assertEquals(category, categories.get(0));
    }

    @Test
    void CategoryRepository_GetById_GetsCategory() {
        Category category = sampleCategory(1L, "Find Category");
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

        Category found = categoryService.getCategoryById(1L);

        assertNotNull(found);
        assertEquals(1L, found.getId());
    }

    @Test
    void CategoryRepository_Update_UpdatesCategory() {
        Category existing = sampleCategory(1L, "Original Name");
        Category updates = sampleCategory(null, "Updated Name");
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(categoryRepository.save(existing)).thenReturn(existing);

        Category updated = categoryService.updateCategory(1L, updates);

        assertEquals("Updated Name", updated.getName());
        verify(categoryRepository).save(existing);
    }

    @Test
    void CategoryRepository_Delete_DeletesCategory() {
        doNothing().when(categoryRepository).deleteById(4L);

        categoryService.deleteCategory(4L);

        verify(categoryRepository).deleteById(4L);
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
