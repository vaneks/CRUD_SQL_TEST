package com.vaneks.crud.service;

import com.vaneks.crud.db.Util;
import com.vaneks.crud.model.Developer;
import com.vaneks.crud.model.Team;
import com.vaneks.crud.model.TeamStatus;
import com.vaneks.crud.repository.TeamRepository;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TeamService extends Util implements TeamRepository {


    @Override
    public void deleteAll() throws  SQLException {
        Connection connection = getConnection();
        PreparedStatement preparedStatement = null;

        try {
            String sql = "DELETE FROM teams WHERE status = 'DELETED'";
            preparedStatement = connection.prepareStatement(sql);
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

    @Override
    public List<Team> getAll() throws SQLException {
        Connection connection = getConnection();
        List<Team> teams = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        try{
            String sql = "SELECT * FROM teams";
            preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery(sql);
            while (resultSet.next()) {
                Team team = new Team();
                List<Developer> developers = new ArrayList<>();
                team.setId(resultSet.getLong("id"));
                team.setName(resultSet.getString("name"));
                TeamStatus teamStatus = TeamStatus.valueOf(resultSet.getString("status"));
                team.setTeamStatus(teamStatus);
                String sql_ = "SELECT * FROM developers WHERE id IN " +
                        "(SELECT dev_id FROM team_dev WHERE team_id = " + resultSet.getLong("id") + ")";
                preparedStatement = connection.prepareStatement(sql_);
                ResultSet resultSetTeamDev = preparedStatement.executeQuery(sql_);
                while (resultSetTeamDev.next()) {
                    Developer developer = new Developer();
                    developer.setId(resultSetTeamDev.getLong("id"));
                    developer.setFirstName(resultSetTeamDev.getString("firstName"));
                    developer.setLastName(resultSetTeamDev.getString("lastName"));
                    developers.add(developer);
                }
                team.setDevelopers(developers);
                teams.add(team);
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
        return teams;
    }

    @Override
    public Team getById(Long id) throws SQLException {
        Connection connection = getConnection();
        Team team = new Team();
        PreparedStatement preparedStatement = null;
        try{
            String sql = "SELECT * FROM teams WHERE id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                List<Developer> developers = new ArrayList<>();
                team.setId(resultSet.getLong("id"));
                team.setName(resultSet.getString("name"));
                TeamStatus teamStatus = TeamStatus.valueOf(resultSet.getString("status"));
                team.setTeamStatus(teamStatus);
                String sql_ = "SELECT * FROM developers WHERE id IN " +
                        "(SELECT dev_id FROM team_dev WHERE team_id = " + resultSet.getLong("id") + ")";
                preparedStatement = connection.prepareStatement(sql_);
                ResultSet resultSetTeamDev = preparedStatement.executeQuery(sql_);
                while (resultSetTeamDev.next()) {
                    Developer developer = new Developer();
                    developer.setId(resultSetTeamDev.getLong("id"));
                    developer.setFirstName(resultSetTeamDev.getString("firstName"));
                    developer.setLastName(resultSetTeamDev.getString("lastName"));
                    developers.add(developer);
                }
                team.setDevelopers(developers);
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
        return team;
    }

    @Override
    public Team save(Team team) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement preparedStatement = null;

        try {
            connection.setAutoCommit(false);

            String sql = "INSERT INTO teams (name, status) VALUES (?, ?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1 , team.getName());
            preparedStatement.setString(2 , team.getTeamStatus().name());
            preparedStatement.executeUpdate();

            String lastIdSql = "SELECT last_insert_id() FROM teams";
            ResultSet resultSet = preparedStatement.executeQuery(lastIdSql);
            resultSet.next();
            team.setId((long) resultSet.getInt("last_insert_id()"));

            for(Developer developer : team.getDevelopers()) {
                sql = "INSERT INTO team_dev (team_id, dev_id) VALUES (?, ?)";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setLong(1 , resultSet.getInt("last_insert_id()"));
                preparedStatement.setLong(2, developer.getId());
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
        return team;
    }


    @Override
    public Team update(Team team) throws  SQLException {
        Connection connection = getConnection();
        PreparedStatement preparedStatement = null;
        try {
            connection.setAutoCommit(false);
            String sql = "UPDATE teams SET status = 'UNDER_REVIEW' WHERE id=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1 , team.getId());
            preparedStatement.executeUpdate();
            sql = "DELETE FROM team_dev  WHERE team_id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1 , team.getId());
            preparedStatement.executeUpdate();

            for(Developer developer : team.getDevelopers()) {
                sql = "INSERT INTO team_dev (team_id, dev_id) VALUES (?,?)";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setLong(1 , team.getId());
                preparedStatement.setLong(2 , developer.getId());
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
        return team;
    }

    @Override
    public void deleteById(Long id) throws IOException, SQLException {
        Connection connection = getConnection();
        PreparedStatement preparedStatement = null;

        try {
            String sql = "UPDATE teams SET status='DELETED' WHERE id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1 , id);
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
