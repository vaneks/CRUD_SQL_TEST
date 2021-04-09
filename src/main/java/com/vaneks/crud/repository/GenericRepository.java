package com.vaneks.crud.repository;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface GenericRepository<T, ID> {
    List<T> getAll() throws SQLException;
    T getById(ID id) throws SQLException;
    T save(T t) throws IOException, SQLException;
    T update(T t) throws IOException, SQLException;
    void deleteById(ID id) throws IOException, SQLException;
}
