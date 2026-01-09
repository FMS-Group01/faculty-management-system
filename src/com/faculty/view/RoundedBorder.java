package com.faculty.view;

import javax.swing.border.AbstractBorder;
import java.awt.*;

public class RoundedBorder extends AbstractBorder {
    private Color color;
    private int thickness;
    private int radius;

    public RoundedBorder(Color color, int thickness, int radius) {
        this.color = color;
        this.thickness = thickness;
        this.radius = radius;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        if (thickness > 0) {
            g2d.setColor(color);
            g2d.setStroke(new BasicStroke(thickness));
            g2d.drawRoundRect(x + thickness / 2, y + thickness / 2, 
                            width - thickness, height - thickness, radius, radius);
        }
        
        g2d.dispose();
    }

    @Override
    public Insets getBorderInsets(Component c) {
        int value = thickness + radius / 2;
        return new Insets(value, value, value, value);
    }

    @Override
    public Insets getBorderInsets(Component c, Insets insets) {
        int value = thickness + radius / 2;
        insets.left = insets.right = insets.top = insets.bottom = value;
        return insets;
    }
}
