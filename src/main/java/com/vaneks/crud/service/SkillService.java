package com.vaneks.crud.service;

import com.vaneks.crud.db.Util;
import com.vaneks.crud.model.Skill;
import com.vaneks.crud.repository.SkillRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SkillService extends Util implements SkillRepository {

    Connection connection = getConnection();

    @Override
    public List<Skill> getAll() throws SQLException {
        List<Skill> skills = new ArrayList<>();
        String sql = "SELECT * FROM skills";
        Statement statement = null;
        try{
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Skill skill = new Skill();
                skill.setId(resultSet.getLong("id"));
                skill.setName(resultSet.getString("skillName"));
                skills.add(skill);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return skills;
    }

    @Override
    public Skill getById(Long id) throws SQLException {
        PreparedStatement preparedStatement = null;
        String sql = "SELECT * FROM skills WHERE id = ?";
        Skill skill = new Skill();
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1 ,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            skill.setId(resultSet.getLong("id"));
            skill.setName(resultSet.getString("skillName"));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return skill;
    }

    @Override
    public Skill save(Skill skill) throws SQLException {
        PreparedStatement preparedStatement = null;
        String sql = "INSERT INTO skills (skillName) VALUES (?)";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1 , skill.getName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if( preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return skill;
    }

    @Override
    public Skill update(Skill skill) throws SQLException {
        PreparedStatement preparedStatement = null;
        String sql = "UPDATE skills SET skillName = ? WHERE id = ?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1 , skill.getName());
            preparedStatement.setLong(2, skill.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            if( preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return skill;
    }

    @Override
    public void deleteById(Long id) throws SQLException {
        PreparedStatement preparedStatement = null;
        String sql = "DELETE FROM skills WHERE id = ?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if( preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }
}
