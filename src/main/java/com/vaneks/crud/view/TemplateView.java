package com.vaneks.crud.view;

import com.vaneks.crud.controller.TeamController;

import java.sql.SQLException;
import java.util.Scanner;

public abstract class TemplateView {
    private Scanner scanner = new Scanner(System.in);

    public void view(String name) throws SQLException {

        System.out.print("Выберите действие, и нажмите Enter: \n" +
                "1 - Вывести все " + name + " на экран \n" +
                "2 - Найти " + name + " по его ID \n" +
                "3 - Обновить данные " + name + "  по его ID \n" +
                "4 - Удалить (пометить на удаление) " + name + "  по его ID \n" +
                "5 - Добавить новый " + name + "  \n");
        if (name.equals("Team")) {
            System.out.println("6 - Удалить все Team со статусом'DELETED' по его ID \n");
        }
        String select = scanner.nextLine();
        switch (select) {
            case "1" -> getAll();
            case "2" -> getById();
            case "3" -> update();
            case "4" -> delete();
            case "5" -> save();
            case "6" -> {
                if (name.equals("Team")) {
                    deleteAll();
                }
            }
        }
    }
    public abstract void getAll() throws SQLException;
    public abstract void getById() throws SQLException;
    public abstract void update() ;
    public abstract void delete();
    public abstract void save();
    private void deleteAll() {
        TeamController teamController = new TeamController();
        teamController.controllerDeleteAll();
        System.out.println("Teams со статусом 'DELETED' успешно удалены");
    }
}

