package com.vaneks.crud.service;

import com.vaneks.crud.model.Team;

import java.util.List;

public interface TeamService {
    List<Team> getAll();

    Team getById(Long id);

    Team save(Team t);

    Team update(Team t);

    void deleteById(Long id);
    void deleteAll();
}
