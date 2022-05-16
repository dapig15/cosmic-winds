package bullethell;

import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import utility.FontGenerator;

public class Main {

    private JFrame frame;
    private GamePanel gamePanel;
    private Timer mainTimer, gameTimer;

    private boolean isGaming = false;
    private int stage = 0;

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
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);

        mainTimer = new Timer();
        mainTimer.schedule(new MainTick(), 100, 3000);
    }

    public void createBulletHell(int currentStage) {
        gamePanel = new GamePanel(currentStage);
        frame.setContentPane(gamePanel);
        frame.pack();
        frame.setVisible(true);

        gameTimer = new Timer();
        gameTimer.schedule(new BulletHellTick(), 0, 42);
    }

    class MainTick extends TimerTask {

        @Override
        public void run() {
            if (!isGaming) {
                isGaming = true;
                switch (stage) {
                    case 0:
                        createBulletHell(1);
                        break;
                    case 1:
                        createBulletHell(2);
                        break;
                    case 2:
                        createBulletHell(3);
                        break;
                    default:
                        isGaming = false;
                }
            }
        }

    }

    class BulletHellTick extends TimerTask {

        @Override
        public void run() {
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    if (frame.isFocused()) {
                        gamePanel.requestFocus();
                        gamePanel.update();
                    }
                    gamePanel.repaint();
                    if (gamePanel.shouldTerminate()) {
                        gameTimer.cancel();
                        isGaming = false;
                        if (!gamePanel.isGameOver()) {
                            stage++;
                        }
                    }
                }
            });
        }

    }
}
