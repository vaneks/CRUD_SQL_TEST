package com.vaneks.crud;


import com.vaneks.crud.model.Skill;
import com.vaneks.crud.repository.SkillRepository;
import com.vaneks.crud.service.SkillService;
import org.junit.Test;

import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class SkillServiceTest {
    SkillRepository skillService = mock(SkillService.class);

    @Test
    public void getByIdTest() throws SQLException {
        when(skillService.getById((long) 1)).thenReturn(new Skill((long) 1,"Java"));
        assertEquals(new Skill((long) 1,"Java"), skillService.getById((long) 1));
    }
}
