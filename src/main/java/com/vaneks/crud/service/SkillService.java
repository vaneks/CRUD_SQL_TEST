package com.vaneks.crud.service;

import com.vaneks.crud.model.Skill;

import java.util.List;

public interface SkillService {
    List<Skill> getAll();

    Skill getById(Long id);

    Skill save(Skill t);

    Skill update(Skill t);

    void deleteById(Long id);
}