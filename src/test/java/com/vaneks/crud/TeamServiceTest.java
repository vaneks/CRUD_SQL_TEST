package com.vaneks.crud;

import com.vaneks.crud.model.Team;
import com.vaneks.crud.repository.TeamRepository;
import com.vaneks.crud.service.TeamService;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class TeamServiceTest {
    TeamRepository teamRepository = mock(TeamRepository.class);
    @Test
    public void getByIdTest() {
        teamRepository.getById(1L);
        verify(teamRepository, times(1)).getById(1L);
    }
}
