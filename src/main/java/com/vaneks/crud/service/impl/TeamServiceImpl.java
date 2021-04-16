package com.vaneks.crud.service.impl;

import com.vaneks.crud.model.Team;
import com.vaneks.crud.repository.TeamRepository;
import com.vaneks.crud.repository.jdbc.JdbcTeamRepository;
import com.vaneks.crud.service.TeamService;

import java.util.List;

public class TeamServiceImpl implements TeamService {
    TeamRepository teamRepository = new JdbcTeamRepository();
    @Override
    public List<Team> getAll() {
        return teamRepository.getAll();
    }

    @Override
    public Team getById(Long id) {
        return teamRepository.getById(id);
    }

    @Override
    public Team save(Team t) {
        return teamRepository.save(t);
    }

    @Override
    public Team update(Team t) {
        return teamRepository.update(t);
    }

    @Override
    public void deleteById(Long id) {
        teamRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        teamRepository.deleteAll();
    }
}
