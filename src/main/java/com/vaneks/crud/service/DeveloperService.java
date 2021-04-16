package com.vaneks.crud.service;

import com.vaneks.crud.model.Developer;

import java.util.List;

public interface DeveloperService {
    List<Developer> getAll();

    Developer getById(Long id);

    Developer save(Developer t);

    Developer update(Developer t);

    void deleteById(Long id);
}
