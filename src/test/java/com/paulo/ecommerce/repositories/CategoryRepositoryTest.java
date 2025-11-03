package com.paulo.ecommerce.repositories;

import com.paulo.ecommerce.entities.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void CategoryRepository_SaveAndFind_SaveAndFindsCategory() {
        Category category = sampleCategory("Repository Category");
        Category saved = categoryRepository.save(category);
        Optional<Category> found = categoryRepository.findById(saved.getId());

        assertNotNull(saved.getId());
        assertTrue(found.isPresent());
    }

    @Test
    void CategoryRepository_Delete_RemovesCategory() {
        Category saved = categoryRepository.save(sampleCategory("Removable Category"));

        categoryRepository.deleteById(saved.getId());

        assertFalse(categoryRepository.existsById(saved.getId()));
    }

    @Test
    void CategoryRepository_FindAll_ReturnMoreThanOneCategory() {
        Category category1 = sampleCategory("Category One");
        Category category2 = sampleCategory("Category Two");

        categoryRepository.save(category1);
        categoryRepository.save(category2);

        List<Category> categoryList = categoryRepository.findAll();

        assertNotNull(categoryList);
        assertEquals(2, categoryList.size());
    }

    private Category sampleCategory(String name) {
        return Category.builder()
                .name(name)
                .build();
    }
}
