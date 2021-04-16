package com.vaneks.crud.repository;

import com.vaneks.crud.model.Team;

import java.io.IOException;
import java.sql.SQLException;

public interface TeamRepository extends GenericRepository<Team, Long> {
    void deleteAll();
}
