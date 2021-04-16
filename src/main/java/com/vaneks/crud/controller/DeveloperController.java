package com.vaneks.crud.controller;

import com.vaneks.crud.model.Developer;
import com.vaneks.crud.model.Skill;
import com.vaneks.crud.repository.DeveloperRepository;
import com.vaneks.crud.service.DeveloperService;
import com.vaneks.crud.service.impl.DeveloperServiceImpl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DeveloperController {
    DeveloperService controllerDeveloper = new DeveloperServiceImpl();

    public List<Developer> ControllerAll() {
        return controllerDeveloper.getAll();
    }

    public Developer controllerId(String id)  {
        return controllerDeveloper.getById(strToLong(id));
    }

    public Developer controllerUpdate(String id, String firstName, String lastName, String[] skills) {
        return controllerDeveloper.update(new Developer(strToLong(id), firstName, lastName, skillsCount(skills)));
    }

    public void controllerDeleteById(String id){
        controllerDeveloper.deleteById(strToLong(id));
    }
    public Developer controllerSave(String firstName, String lastName, String[] skills){
        return controllerDeveloper.save(new Developer(null, firstName, lastName, skillsCount(skills)));
    }

    private List<Skill> skillsCount(String[] skills) {
        List<Skill> listSkills = new ArrayList<>();
        for(int i = 0; i < skills.length; i++) {
            listSkills.add(new Skill(strToLong(skills[i]), null));
        }
        return listSkills;
    }
    private Long strToLong(String string) {
        return Long.parseLong(string);
    }
}
