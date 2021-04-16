package com.vaneks.crud.service.impl;

import com.vaneks.crud.model.Developer;
import com.vaneks.crud.model.Skill;
import com.vaneks.crud.repository.SkillRepository;
import com.vaneks.crud.repository.jdbc.JdbcSkillRepositoryImpl;
import com.vaneks.crud.service.SkillService;

import java.util.List;

public class SkillServiceImpl implements SkillService {
    SkillRepository skillRepository = new JdbcSkillRepositoryImpl();

    @Override
    public List<Skill> getAll() {
        return skillRepository.getAll();
    }

    @Override
    public Skill getById(Long id) {
        return skillRepository.getById(id);
    }

    @Override
    public void deleteById(Long id) {
        skillRepository.deleteById(id);
    }

    @Override
    public Skill update(Skill t) {
        return skillRepository.update(t);
    }

    @Override
    public Skill save(Skill t) {
        return skillRepository.save(t);
    }
}
