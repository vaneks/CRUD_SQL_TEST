package com.vaneks.crud.view;

import com.vaneks.crud.controller.DeveloperController;
import com.vaneks.crud.model.Developer;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class DeveloperView extends TemplateView {
    DeveloperController developerController = new DeveloperController();
    private Scanner scanner = new Scanner(System.in);
    private List<Developer> developerList;
    private Developer developer;

    public void getAll() throws SQLException {
        developerList = developerController.ControllerAll();
        for (Developer developer: developerList) System.out.println(developer.toString());
    }

    public void getById() throws SQLException {
        System.out.println("Введите Developer's ID");
        String id = scanner.nextLine();
        developer = developerController.controllerId(id);
        System.out.println(developer.toString());
    }

    public void update() {
        System.out.println("Введите Developer's ID");
        String id = scanner.nextLine();
        System.out.println("Введите FirstName");
        String firstname = scanner.nextLine();
        System.out.println("Введите LastName");
        String lastname = scanner.nextLine();
        System.out.println("Введите Skill's ID через 'Пробел");
        String skills = scanner.nextLine();
        String[] idSkills = skills.split(" ");
        developer = developerController.controllerUpdate(id, firstname, lastname, idSkills);
        System.out.println(developer.toString());
    }

    public void delete() {
        System.out.println("Введите Developer's ID");
        String id = scanner.nextLine();
        developerController.controllerDeleteById(id);
        System.out.println("Developer успешно удален");
    }

    public void save() {
        System.out.println("Введите FirstName");
        String firstname = scanner.nextLine();
        System.out.println("Введите LastName");
        String lastname = scanner.nextLine();
        System.out.println("Введите Skill's ID через 'Пробел'");
        String skills = scanner.nextLine();
        String[] idSkills = skills.split(" ");
        developer = developerController.controllerSave( firstname, lastname, idSkills);
        System.out.println(developer.toString());
    }
}
