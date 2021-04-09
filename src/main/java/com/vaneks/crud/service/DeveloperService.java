package com.vaneks.crud.service;

import com.vaneks.crud.db.Util;
import com.vaneks.crud.model.Developer;
import com.vaneks.crud.model.Skill;
import com.vaneks.crud.repository.DeveloperRepository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DeveloperService extends Util implements DeveloperRepository {
    Connection connection = getConnection();

    @Override
    public List<Developer> getAll() throws SQLException {

        List<Developer> developers = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        try{
            String sql = "SELECT * FROM developers";
            preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery(sql);
            while (resultSet.next()) {
                Developer developer = new Developer();
                List<Skill> skills = new ArrayList<>();
                developer.setId(resultSet.getLong("id"));
                developer.setFirstName(resultSet.getString("firstName"));
                developer.setLastName(resultSet.getString("lastName"));
                String sql_ = "SELECT * FROM skills WHERE id IN " +
                        "(SELECT skill_id FROM dev_skills WHERE dev_id = " + resultSet.getLong("id") + ")";
                preparedStatement = connection.prepareStatement(sql_);
                ResultSet resultSetDevSkills = preparedStatement.executeQuery(sql_);
                while (resultSetDevSkills.next()) {
                    Skill skill = new Skill();
                    skill.setId(resultSetDevSkills.getLong("id"));
                    skill.setName(resultSetDevSkills.getString("skillName"));
                    skills.add(skill);
                }
                developer.setSkills(skills);
                developers.add(developer);
            }
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
        return developers;
    }

    @Override
    public Developer getById(Long id) throws SQLException {
        PreparedStatement preparedStatement = null;
        Developer developer = new Developer();
        try {
            String sql = "SELECT * FROM developers WHERE id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1 ,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                List<Skill> skills = new ArrayList<>();
                developer.setId(resultSet.getLong("id"));
                developer.setFirstName(resultSet.getString("firstName"));
                developer.setLastName(resultSet.getString("lastName"));
                String sql_ = "SELECT * FROM skills WHERE id IN " +
                        "(SELECT skill_id FROM dev_skills WHERE dev_id = " + resultSet.getLong("id") + ")";
                preparedStatement = connection.prepareStatement(sql_);
                ResultSet resultSetDevSkills = preparedStatement.executeQuery(sql_);
                while (resultSetDevSkills.next()) {
                    Skill skill = new Skill();
                    skill.setId(resultSetDevSkills.getLong("id"));
                    skill.setName(resultSetDevSkills.getString("skillName"));
                    skills.add(skill);
                }
                developer.setSkills(skills);
            }
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
        return developer;
    }

    @Override
    public Developer save(Developer developer) throws  SQLException {
        PreparedStatement preparedStatement = null;

        try {
            String sql = "INSERT INTO developers (firstName, lastName) VALUES (?, ?)";
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, developer.getFirstName());
            preparedStatement.setString(2, developer.getLastName());

            preparedStatement.executeUpdate();
            String lastIdSql = "SELECT last_insert_id() FROM developers";
            ResultSet resultSet = preparedStatement.executeQuery(lastIdSql);
            resultSet.next();
            developer.setId((long) resultSet.getInt("last_insert_id()"));

            for(Skill skill : developer.getSkills()) {
                sql = "INSERT INTO dev_skills (dev_id, skill_id) VALUES (?, ?)";
                System.out.println(skill.getId());
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setLong(1 , resultSet.getInt("last_insert_id()"));
                preparedStatement.setLong(2 , skill.getId());
                preparedStatement.executeUpdate();
            }
            connection.commit();

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
        return developer;
    }

    @Override
    public Developer update(Developer developer) throws SQLException {
        PreparedStatement preparedStatement = null;

        try {
            String sql = "UPDATE developers SET firstName = ?, lastName = ? WHERE id = ?";
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1 , developer.getFirstName());
            preparedStatement.setString(2, developer.getLastName());
            preparedStatement.setLong(3 , developer.getId());
            preparedStatement.executeUpdate();

            sql = "DELETE FROM dev_skills  WHERE dev_id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1 , developer.getId());
            preparedStatement.executeUpdate();


            for(Skill skill : developer.getSkills()) {
                sql = "INSERT INTO dev_skills (dev_id, skill_id) VALUES (?, ?)";
                System.out.println(skill.getId());
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setLong(1 , developer.getId());
                preparedStatement.setLong(2 , skill.getId());
                preparedStatement.executeUpdate();
            }
            connection.commit();
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
        return developer;
    }

    @Override
    public void deleteById(Long id) throws  SQLException {
        PreparedStatement preparedStatement = null;

        try {
            String sql = "DELETE FROM developers WHERE id = ?";
            preparedStatement = connection.prepareStatement(sql);
            connection.setAutoCommit(false);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();

            sql = "DELETE FROM dev_skills  WHERE dev_id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1 , id);
            preparedStatement.executeUpdate();
            connection.commit();

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
