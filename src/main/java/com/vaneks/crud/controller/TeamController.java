package com.vaneks.crud.controller;

import com.vaneks.crud.model.Developer;
import com.vaneks.crud.model.Team;
import com.vaneks.crud.model.TeamStatus;
import com.vaneks.crud.repository.DeveloperRepository;
import com.vaneks.crud.repository.TeamRepository;
import com.vaneks.crud.service.DeveloperService;
import com.vaneks.crud.service.TeamService;
import com.vaneks.crud.service.impl.DeveloperServiceImpl;
import com.vaneks.crud.service.impl.TeamServiceImpl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TeamController {
    TeamService controllerTeam = new TeamServiceImpl();
    DeveloperService developerRepository = new DeveloperServiceImpl();

    public List<Team> ControllerAll() {
        return controllerTeam.getAll();
    }

    public Team controllerId(String id) {
        return controllerTeam.getById(strToLong(id));
    }

    public Team controllerUpdate(String id, String[] developers) {
        try {
            return controllerTeam.update(new Team(TeamStatus.UNDER_REVIEW,strToLong(id), controllerTeam.getById(strToLong(id)).getName(), developerCount(developers)));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void controllerDeleteById(String id) {
        controllerTeam.deleteById(strToLong(id));
    }
    public void controllerDeleteAll() {
        controllerTeam.deleteAll();
    }
    public Team controllerSave(String name, String[] developers) {
        try {
            return controllerTeam.save(new Team(TeamStatus.ACTIVE,null, name, developerCount(developers)));
        } catch (SQLException e) {
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
