package com.vaneks.crud.view;

import java.sql.SQLException;
import java.util.Scanner;

public class MainView {
    private Scanner scanner = new Scanner(System.in);
    SkillView skillView = new SkillView();
    DeveloperView developerView = new DeveloperView();
    TeamView teamView = new TeamView();

    public void showMainMenu() throws SQLException {
        System.out.println("Выберите сущность, введите соответсвующий номер и нажмите Enter: \n" +
                "1 - Team \n" +
                "2 - Developer \n" +
                "3 - Skill \n");
        String select = scanner.nextLine();
        switch (select) {
            case "1" :
                teamView.view("Team");
                break;
            case "2" :
                developerView.view("Developer");
                break;
            case "3" :
                skillView.view("Skill");
                break;
        }
    }
}
