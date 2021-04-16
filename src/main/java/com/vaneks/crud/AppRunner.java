package com.vaneks.crud;

import com.vaneks.crud.utils.JdbcUtils;
import com.vaneks.crud.view.MainView;

import java.sql.SQLException;

public class AppRunner {
    public static void main(String[] args) throws SQLException {
        MainView mainView = new MainView();
        JdbcUtils.getJdbcUtils();
        mainView.showMainMenu();
        JdbcUtils.getConnection().close();
    }
}
