package com.faculty.main;

import javax.swing.SwingUtilities;
import com.faculty.view.LoginView;
import com.faculty.controller.LoginController;

public class App {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            LoginView loginView = new LoginView();
            new LoginController(loginView);
            loginView.setVisible(true);
        });
    }
}
