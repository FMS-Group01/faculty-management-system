package com.faculty.main;

import javax.swing.SwingUtilities;
import com.faculty.view.LoginView;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginView loginView = new LoginView();
            loginView.setVisible(true);
        });
    }
}
