package bullethell;

import java.awt.Dimension;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import utility.FontGenerator;

public class Main {

    private GamePanel gamePanel;
    Timer timer;

    public GamePanel getGamePanel() {
        return gamePanel;
    }

    public static void main(String[] args) throws Exception {
        FontGenerator.prime();
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Main m = new Main();
                m.run();
            }
        });
    }

    public void run() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // frame.setLayout(null);

        gamePanel = new GamePanel();
        frame.setContentPane(gamePanel);

        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);

        timer = new Timer();
        timer.schedule(new GameTick(), 0, 42);
    }

    class GameTick extends TimerTask {

        @Override
        public void run() {
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    gamePanel.update();
                    gamePanel.repaint();
                }
            });
        }

    }
}
