package bullethell;

import java.awt.Color;

import javax.swing.JPanel;

public class GamePanel extends JPanel {
    private BulletPanel bulletPanel = new BulletPanel();

    public GamePanel() {
        this.setBackground(Color.RED);
        this.setLayout(null);
        this.add(bulletPanel);
    }

    public BulletPanel getBulletPanel() {
        return bulletPanel;
    }
}
