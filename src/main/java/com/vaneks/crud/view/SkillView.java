package com.vaneks.crud.view;

import com.vaneks.crud.controller.SkillController;
import com.vaneks.crud.model.Skill;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class SkillView extends TemplateView {
    SkillController skillController = new SkillController();
    private Scanner scanner = new Scanner(System.in);
    private List<Skill> skillList;
    private Skill skill;


    public void getAll() throws SQLException {
        skillList = skillController.ControllerAll();
        for (Skill skill : skillList) System.out.println(skill.toString());
    }

    public void getById() throws SQLException {
        System.out.println("Введите Skill's ID");
        String id = scanner.nextLine();
        skill = skillController.controllerId(id);
        System.out.println(skill.toString());
    }

    public void update() {
        System.out.println("Введите ID");
        String id = scanner.nextLine();
        System.out.println("Введите Название");
        String name = scanner.nextLine();
        skill = skillController.controllerUpdate(id, name);
        System.out.println(skill.toString());
    }

    public void delete() {
        System.out.println("Введите Skill's ID");
        String id = scanner.nextLine();
        skillController.controllerDeleteById(id);
        System.out.println("Skill успешно удален");
    }

    public void save() {
        System.out.println("Введите Название Skill");
        String name = scanner.nextLine();
        skill = skillController.controllerSave(name);
        System.out.println(skill.toString());
    }
}
