package com.vaneks.crud;

import com.vaneks.crud.repository.DeveloperRepository;
import com.vaneks.crud.repository.SkillRepository;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class DeveloperServiceTest {
    DeveloperRepository  developerRepository = mock(DeveloperRepository.class);
    @Test
    public void getByIdTest() {
        developerRepository.getById(1L);
        verify(developerRepository, times(1)).getById(1L);
    }
}
