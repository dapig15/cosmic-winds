package bullethell;

import java.awt.Color;
import java.awt.Dimension;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main {

    private GamePanel gamePanel;
    Timer timer;

    public GamePanel getGamePanel() {
        return gamePanel;
    }

    public static void main(String[] args) throws Exception {
        Main m = new Main();
        m.run();
    }

    public void run() {
        JFrame frame = new JFrame();
        frame.setPreferredSize(new Dimension(1080, 720));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        gamePanel = new GamePanel();
        frame.add(gamePanel);

        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);

        timer = new Timer();
        timer.schedule(new GameTick(), 0, 42);
    }

    class GameTick extends TimerTask {

        @Override
        public void run() {
            gamePanel.update();
            gamePanel.repaint();
        }

    }
}
