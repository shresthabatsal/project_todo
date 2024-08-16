package view.util;

import javax.swing.*;
import java.awt.*;

public class CustomButton extends JButton {
    public static final Color defaultColor = Color.decode("#FFFFFF");
    public static final Color hoverColor = Color.decode("#D3D3D3");

    public CustomButton(String text, ImageIcon icon) {
        setFocusPainted(false);
        setContentAreaFilled(false);
        setOpaque(false);

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Icon on the left
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        JLabel iconLabel = new JLabel(icon);
        add(iconLabel, gbc);

        // Text in the center
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(0, 0, 0, 18);
        gbc.anchor = GridBagConstraints.CENTER;
        JLabel textLabel = new JLabel(text);
        textLabel.setFont(FontLoad.getMontserratMedium(16f));
        add(textLabel, gbc);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (getModel().isPressed()) {
            g2.setColor(defaultColor.darker());
        } else if (getModel().isRollover()) {
            g2.setColor(Color.decode("#E7E7E7"));
        } else {
            g2.setColor(getBackground());
        }
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.BLACK);
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);
    }
}