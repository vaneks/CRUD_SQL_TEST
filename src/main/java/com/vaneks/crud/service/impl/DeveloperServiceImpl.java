package com.vaneks.crud.service.impl;

import com.vaneks.crud.model.Developer;
import com.vaneks.crud.repository.DeveloperRepository;
import com.vaneks.crud.repository.jdbc.JdbcDeveloperRepositoryImpl;
import com.vaneks.crud.service.DeveloperService;

import java.util.List;

public class DeveloperServiceImpl implements DeveloperService {

    private final DeveloperRepository developerRepository = new JdbcDeveloperRepositoryImpl();

    @Override
    public List<Developer> getAll() {
        return developerRepository.getAll();
    }

    @Override
    public Developer getById(Long id) {
        return developerRepository.getById(id);
    }

    @Override
    public Developer save(Developer developer) {
        return developerRepository.save(developer);
    }

    @Override
    public Developer update(Developer developer) {
        return developerRepository.update(developer);
    }

    @Override
    public void deleteById(Long id) {
        developerRepository.deleteById(id);
    }
}
