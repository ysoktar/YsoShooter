package GameClasses;

import java.awt.event.*;

public class GameInput implements KeyListener, MouseMotionListener, MouseListener {
    Player player;
    GameState state;
    GamePanel panel;

    public GameInput(GameState state, GamePanel panel) {
        this.state = state;
        this.player = state.player;
        this.panel = panel;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getExtendedKeyCode();
        if (code == KeyEvent.VK_UP || code == KeyEvent.VK_W) {
            player.up = true;
        }
        if (code == KeyEvent.VK_DOWN || code == KeyEvent.VK_S) {
            player.down = true;
        }
        if (code == KeyEvent.VK_LEFT || code == KeyEvent.VK_A) {
            player.left = true;
        }
        if (code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_D) {
            player.right = true;
        }

        if (code == KeyEvent.VK_X) {
            player.shoot = true;
        }
        if (code == KeyEvent.VK_R) {
            player.reload = true;
        }

        if (code == KeyEvent.VK_ESCAPE) {
            state.esc();
        }
        if (code == KeyEvent.VK_G) {
            state.refresh();
        }

        if (code == KeyEvent.VK_F) {
            panel.save();
        }
        if (code == KeyEvent.VK_L) {
            panel.load();
        }

        if (code == KeyEvent.VK_C) {
            state.nextWave();
        }

        if (code == KeyEvent.VK_1) {
            player.changeGun(1);
        }
        if (code == KeyEvent.VK_2) {
            player.changeGun(2);
        }
        if (code == KeyEvent.VK_3) {
            player.changeGun(3);
        }
        if (code == KeyEvent.VK_4) {
            player.changeGun(4);
        }
        if (code == KeyEvent.VK_5) {
            player.changeGun(5);
        }

        if (code == KeyEvent.VK_Q) {
            player.changeGunLeft();
        }
        if (code == KeyEvent.VK_E) {
            player.changeGunRight();
        }

        if (code == KeyEvent.VK_M) {
            state.testZombie(300);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getExtendedKeyCode();
        if (code == KeyEvent.VK_UP || code == KeyEvent.VK_W) {
            player.up = false;
        }
        if (code == KeyEvent.VK_DOWN || code == KeyEvent.VK_S) {
            player.down = false;
        }
        if (code == KeyEvent.VK_LEFT || code == KeyEvent.VK_A) {
            player.left = false;
        }
        if (code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_D) {
            player.right = false;
        }

        if (code == KeyEvent.VK_X) {
            player.shoot = false;
        }
        if (code == KeyEvent.VK_R) {
            player.reload = false;
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        player.mouseX = (e.getX());
        player.mouseY = (e.getY());
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        player.mouseX = (e.getX());
        player.mouseY = (e.getY());
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        player.mouseX = (e.getX());
        player.mouseY = (e.getY());
    }

    @Override
    public void mousePressed(MouseEvent e) {

        if (e.getButton() == MouseEvent.BUTTON1) {
            player.shoot = true;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            player.shoot = false;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}