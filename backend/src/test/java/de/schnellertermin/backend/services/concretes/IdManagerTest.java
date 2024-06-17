package de.schnellertermin.backend.services.concretes;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class IdManagerTest {
    @InjectMocks IdManager idManager;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void whenGenerateCategoryId_returnUniqId(){
        String id1 = idManager.generateCategoryId();
        String id2 = idManager.generateCategoryId();

        assertNotEquals(id1, id2);
    }

    @Test
    void whenGenerateCategoryId_returnsIdWithCorrectPrefix() {
        String id = idManager.generateCategoryId();

        assertTrue(id.startsWith("CAT-"));
    }
}