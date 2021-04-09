package com.vaneks.crud;

import com.vaneks.crud.db.Util;
import com.vaneks.crud.view.MainView;

import java.sql.SQLException;

public class AppRunner {
    public static void main(String[] args) throws SQLException {
        Util util = new Util();
        util.getConnection();
        MainView mainView = new MainView();
        mainView.showMainMenu();
    }
}
