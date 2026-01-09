package com.faculty.view;

import javax.swing.*;
import java.awt.*;

public class LecturersPanel extends JPanel {
    
    private final Color PURPLE = new Color(138, 78, 255);
    
    public LecturersPanel() {
        setLayout(null);
        setBackground(Color.WHITE);
        
        JLabel titleLabel = new JLabel("Lecturers");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
        titleLabel.setForeground(PURPLE);
        titleLabel.setBounds(50, 30, 300, 50);
        add(titleLabel);
    }
}
