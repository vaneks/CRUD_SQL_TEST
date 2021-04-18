package com.vaneks.crud;

import com.vaneks.crud.repository.SkillRepository;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class SkillServiceTest {
    SkillRepository skillRepository = mock(SkillRepository.class);
    @Test
    public void getByIdTest() {
        skillRepository.getById(1L);
        verify(skillRepository, times(1)).getById(1L);

    }
}
