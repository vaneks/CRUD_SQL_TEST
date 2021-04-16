package com.vaneks.crud;

import com.vaneks.crud.model.Skill;
import com.vaneks.crud.repository.SkillRepository;
import com.vaneks.crud.service.SkillService;
import com.vaneks.crud.service.impl.SkillServiceImpl;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class SkillServiceTest {
    SkillRepository skillRepository = mock(SkillRepository.class);
    SkillService serviceUnderTest = new SkillServiceImpl();

    @Test
    public void getByIdTest() {
        when(serviceUnderTest.getById((long) 1)).thenReturn(new Skill(1L, "Java"));
        serviceUnderTest.getById(1L);
        verify(skillRepository, times(1)).getById(1L);
    }
}
