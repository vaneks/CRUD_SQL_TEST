package com.vaneks.crud.controller;

import com.vaneks.crud.model.Skill;
import com.vaneks.crud.repository.SkillRepository;
import com.vaneks.crud.service.SkillService;
import com.vaneks.crud.service.impl.SkillServiceImpl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class SkillController {
    SkillService controllerSkill = new SkillServiceImpl();

    public List <Skill> ControllerAll()  {
        return controllerSkill.getAll();
    }

    public Skill controllerId(String id) {
        return controllerSkill.getById(strToLong(id));
    }

    public Skill controllerUpdate(String id, String name){
        return controllerSkill.update(new Skill(strToLong(id), name));
    }

    public void controllerDeleteById(String name) {
        controllerSkill.deleteById(strToLong(name));
    }
    public Skill controllerSave(String name){
        return controllerSkill.save(new Skill(null, name));
    }
    private Long strToLong(String string) {
        return Long.parseLong(string);
    }
}
