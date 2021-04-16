package com.vaneks.crud.repository.jdbc;

import com.vaneks.crud.model.Skill;
import com.vaneks.crud.repository.SkillRepository;
import com.vaneks.crud.utils.JdbcUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcSkillRepositoryImpl implements SkillRepository {
    private final String SELECT_SKILL_ALL = "SELECT * FROM skills";
    private final String SELECT_SKILL_ID = "SELECT * FROM skills WHERE id = ?";
    private final String INSERT_SKILL = "INSERT INTO skills (skillName) VALUES (?)";
    private final String UPDATE_SKILL = "UPDATE skills SET skillName = ? WHERE id = ?";
    private final String DELETE_SKILL = "DELETE FROM skills WHERE id = ?";

    @Override
    public List<Skill> getAll() {
        Connection connection = JdbcUtils.getConnection();
        List<Skill> skills = new ArrayList<>();
        String sql = SELECT_SKILL_ALL;
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
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return skills;
    }

    @Override
    public Skill getById(Long id) {
        Connection connection = JdbcUtils.getConnection();
        PreparedStatement preparedStatement = null;
        String sql = SELECT_SKILL_ID;
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
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return skill;
    }

    @Override
    public Skill save(Skill skill){
        Connection connection = JdbcUtils.getConnection();
        PreparedStatement preparedStatement = null;
        String sql = INSERT_SKILL;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1 , skill.getName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if( preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return skill;
    }

    @Override
    public Skill update(Skill skill) {
        Connection connection = JdbcUtils.getConnection();
        PreparedStatement preparedStatement = null;
        String sql = UPDATE_SKILL;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1 , skill.getName());
            preparedStatement.setLong(2, skill.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            if( preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return skill;
    }

    @Override
    public void deleteById(Long id) {
        Connection connection = JdbcUtils.getConnection();
        PreparedStatement preparedStatement = null;
        String sql = DELETE_SKILL;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if( preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}