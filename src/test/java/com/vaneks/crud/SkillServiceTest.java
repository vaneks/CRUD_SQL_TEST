package com.vaneks.crud;

import com.vaneks.crud.model.Skill;
import com.vaneks.crud.repository.SkillRepository;
import com.vaneks.crud.service.SkillService;
import com.vaneks.crud.service.impl.SkillServiceImpl;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class SkillServiceTest {
    SkillService skillRepository = mock(SkillService.class);

    @Test
    public void getByIdTest() {
        skillRepository.getById(1L);
        verify(skillRepository, times(1)).getById(1L);
    }
}
