package com.vaneks.crud.view;

import com.vaneks.crud.controller.TeamController;
import com.vaneks.crud.model.Team;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class TeamView extends TemplateView{
    TeamController teamController = new TeamController();
    private Scanner scanner = new Scanner(System.in);
    private List<Team> teamList;
    private Team team;


    public void getAll() throws SQLException {
        teamList = teamController.ControllerAll();
        for (Team team: teamList) System.out.println(team.toString());
    }

    public void getById() throws SQLException {
        System.out.println("Введите Team's ID");
        String id = scanner.nextLine();
        team = teamController.controllerId(id);
        System.out.println(team.toString());
    }

    public void update() {
        System.out.println("Введите Team's ID");
        String id = scanner.nextLine();
        System.out.println("Введите Developer's ID через 'Пробел");
        String developers = scanner.nextLine();
        String[] idDevelopers = developers.split(" ");
        team = teamController.controllerUpdate(id, idDevelopers);
        System.out.println(team.toString());
    }

    public void delete() {
        System.out.println("Введите Team's ID");
        String id = scanner.nextLine();
        teamController.controllerDeleteById(id);
        System.out.println("Установлен статус 'DELETED'");
    }
    private void deleteAll() {
        teamController.controllerDeleteAll();
        System.out.println("Teams со статусом 'DELETED' успешно удалены");
    }
    public void save() {
        System.out.println("Введите Имя Команды");
        String name = scanner.nextLine();
        System.out.println("Введите Developers's ID через 'Пробел");
        String developer = scanner.nextLine();
        String[] idDevelopers = developer.split(" ");
        team = teamController.controllerSave( name, idDevelopers);
        System.out.println(team.toString());
    }
}
