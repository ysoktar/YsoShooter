package GameClasses;

import javax.swing.*;
import java.awt.*;

public class Game {
    public static final String NAME = "YsoShooter";
    public static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    public static void main(String[] args) {
        JFrame window = new JFrame(NAME);
        window.setLocation(screenSize.width / 2 - GamePanel.PANEL_WIDTH / 2,
                -30 + screenSize.height / 2 - GamePanel.PANEL_HEIGHT / 2);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setContentPane(new GamePanel());
        window.setResizable(false);

        window.requestFocus();

        window.pack();
        window.setVisible(true);
    }
}