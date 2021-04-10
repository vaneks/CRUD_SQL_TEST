package com.vaneks.crud.controller;

import com.vaneks.crud.model.Developer;
import com.vaneks.crud.model.Team;
import com.vaneks.crud.model.TeamStatus;
import com.vaneks.crud.repository.DeveloperRepository;
import com.vaneks.crud.repository.TeamRepository;
import com.vaneks.crud.service.DeveloperService;
import com.vaneks.crud.service.TeamService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TeamController {
    TeamRepository controllerTeam = new TeamService();
    DeveloperRepository developerRepository = new DeveloperService();

    public List<Team> ControllerAll() throws SQLException {
        return controllerTeam.getAll();
    }

    public Team controllerId(String id) throws SQLException {
        return controllerTeam.getById(strToLong(id));
    }

    public Team controllerUpdate(String id, String[] developers) {
        try {
            return controllerTeam.update(new Team(TeamStatus.UNDER_REVIEW,strToLong(id), controllerTeam.getById(strToLong(id)).getName(), developerCount(developers)));
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void controllerDeleteById(String id) {
        try {
            controllerTeam.deleteById(strToLong(id));
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }
    public void controllerDeleteAll() {
        try {
            controllerTeam.deleteAll();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }
    public Team controllerSave(String name, String[] developers) {
        try {
            return controllerTeam.save(new Team(TeamStatus.ACTIVE,null, name, developerCount(developers)));
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<Developer> developerCount(String[] developers) throws SQLException {
        List<Developer> listDeveloper = new ArrayList<Developer>();
        for(int i = 0; i < developers.length ; i++) {
            listDeveloper.add(developerRepository.getById(strToLong(developers[i])));
        }
        return listDeveloper;
    }

    private Long strToLong(String string) {
        return Long.parseLong(string);
    }
}
