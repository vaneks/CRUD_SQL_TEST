package com.vaneks.crud.repository.jdbc;

import com.vaneks.crud.utils.JdbcUtils;
import com.vaneks.crud.model.Developer;
import com.vaneks.crud.model.Skill;
import com.vaneks.crud.repository.DeveloperRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.vaneks.crud.utils.JdbcUtils.getConnection;

public class JdbcDeveloperRepositoryImpl implements DeveloperRepository {

    private final String DELETE_DEVELOPER_SQL = "DELETE FROM developers WHERE id = ?";
    private final String SELECT_ALL_DEVELOPERS = "SELECT * FROM developers";
    private final String SELECT_DEVELOPER_ID = "SELECT * FROM developers WHERE id = ?";
    private final String INSERT_DEVELOPERS = "INSERT INTO developers (firstName, lastName) VALUES (?, ?)";
    private final String INSERT_DEV_SKILLS = "INSERT INTO dev_skills (dev_id, skill_id) VALUES (?, ?)";
    private final String UPDATE_DEVELOPERS = "UPDATE developers SET firstName = ?, lastName = ? WHERE id = ?";
    private final String DELETE_DEV_SKILLS_ID = "DELETE FROM dev_skills  WHERE dev_id = ?";

    @Override
    public List<Developer> getAll() {
        Connection connection = JdbcUtils.getConnection();
        List<Developer> developers = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        try {
            String sql = SELECT_ALL_DEVELOPERS;
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
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return developers;
    }

    @Override
    public Developer getById(Long id) {
        Connection connection = getConnection();
        PreparedStatement preparedStatement = null;
        Developer developer = new Developer();
        try {
            String sql = SELECT_DEVELOPER_ID;
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
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
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return developer;
    }

    @Override
    public Developer save(Developer developer) {
        Connection connection = getConnection();
        PreparedStatement preparedStatement = null;

        try {
            String sql = INSERT_DEVELOPERS;
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, developer.getFirstName());
            preparedStatement.setString(2, developer.getLastName());

            preparedStatement.executeUpdate();
            String lastIdSql = "SELECT last_insert_id() FROM developers";
            ResultSet resultSet = preparedStatement.executeQuery(lastIdSql);
            resultSet.next();
            developer.setId((long) resultSet.getInt("last_insert_id()"));

            for (Skill skill : developer.getSkills()) {
                sql = INSERT_DEV_SKILLS;
                System.out.println(skill.getId());
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setLong(1, resultSet.getInt("last_insert_id()"));
                preparedStatement.setLong(2, skill.getId());
                preparedStatement.executeUpdate();
            }
            connection.commit();

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
        return developer;
    }

    @Override
    public Developer update(Developer developer) {
        Connection connection = JdbcUtils.getConnection();
        PreparedStatement preparedStatement = null;

        try {
            String sql = UPDATE_DEVELOPERS;
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, developer.getFirstName());
            preparedStatement.setString(2, developer.getLastName());
            preparedStatement.setLong(3, developer.getId());
            preparedStatement.executeUpdate();

            sql = DELETE_DEV_SKILLS_ID;
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, developer.getId());
            preparedStatement.executeUpdate();


            for (Skill skill : developer.getSkills()) {
                sql = INSERT_DEV_SKILLS;
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setLong(1, developer.getId());
                preparedStatement.setLong(2, skill.getId());
                preparedStatement.executeUpdate();
            }
            connection.commit();
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
        return developer;
    }

    @Override
    public void deleteById(Long id) {
        Connection connection = getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_DEVELOPER_SQL)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
