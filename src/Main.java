
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import bullethell.GamePanel;
import platform.PGamePanel;
import utility.FontGenerator;

public class Main {

    private JFrame frame;
    private PGamePanel pGamePanel;
    private GamePanel gamePanel;
    private Text textPanel;
    private Timer mainTimer, gameTimer;

    private boolean isGaming = false;
    private int stage = -1;
    //private int stage = 0;

    private static Main ref;

    public static void main(String[] args) throws Exception {
        FontGenerator.prime();
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Main m = new Main();
                ref = m;
                m.run();
            }
        });
    }

    public void increaseStage() {
        isGaming = false;
        stage++;
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
    public void createText(int title, String[] text) {
        textPanel = new Text(text, ref, title);
        frame.setContentPane(textPanel);
        frame.pack();
        frame.setVisible(true);
    }

    public void createPlatformer(int maxKills, int width, int maxBoxKills) {
        pGamePanel = new PGamePanel(maxKills, width, maxBoxKills);
        frame.setContentPane(pGamePanel);
        frame.pack();
        frame.setVisible(true);
        gameTimer = new Timer();
        gameTimer.schedule(new PlatformTick(), 0, 20);
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
                    case -1:
                        createText(1, (new String[] {"Welcome soldier. I've been looking forward to meeting with you.",
                        "As you might be aware of, the galactic\nsyndicate has terrorized our worlds for generations.",
                        "Millions have fallen at the hands of the\nsyndicate - their reign of terror must come to an end!",
                        "Fortunately, we may have a solution.\nA small force may be able to take out the heads of the syndicate.",
                        "We have selected you to lead this very force.\nWhile your piloting skills may be a little lacking,\nwe believe your adaptability and courage should more than compensate.",
                        "Unfortunately, due to the syndicate's hold over\nthe supply chains,we can't provide you with any gear. \nYou will have to raid syndicate fortifications and obtain it yourself.",
                        "Good luck soldier - I will provide more\ninformation once you are in the ship.",
                        "Controls:\nUse WASD to navigate and K to attack.\nPress W while against a wall to wall jump.\nYou can press A twice or D twice to dash through attacks.\nFollow the arrows, defeat the enemies, and complete the maze."}));
                        break;
                    case 0:
                        createPlatformer(3, 40, 0);
                        break;
                    case 1:
                        createBulletHell(1);
                        break;
                    case 2:
                        createText(2, new String[] {"Congratulations on a successful first strike!\nCelebrations are in order.",
                        "Unfortunately, we can't get too happy.\nThe syndicate has upped defenses for the remaining fleets.",
                        "Additionally, all syndicate fortifications are now on high alert.\nBetter gear will be much harder to get.",
                        "If you're up to the challenge, we may have zeroed in on \nthe location of a new prototype spread-shooter for your ship.",
                        "The syndicate facility at which it is present is much larger.\nAs such, you will encounter stiffer resistance.",
                        "Once you obtain the new gear, we will send you to your next target.\nGood luck and godspeed!",
                        });
                        break;
                    case 3:
                        createPlatformer(2, 60, 2);
                        break;
                    case 4:
                        createBulletHell(2);
                        break;
                    case 5:
                        createText(3, new String[] {"I'm going to be honest, I didn't think you'd get this far.\nYou really are one hell of a soldier.",
                        "With two heads of the syndicate down, the last one has cocooned.\nThey have surrounded themselves with the most powerful fleet I've ever seen.",
                        "However, you won't be going in completely exposed.\nAfter years of searching, we finally found a mint-condition orb-shooter.",
                        "Orb-shooters shoot powerful bombs that cut through ships.\nDue to its power, the syndicate guards it with its best ground forces.",
                        "Obtain the orb-shooter and take out the final syndicate head.\nThe fate of the galaxy rests in your hands."
                        });
                        break;
                    case 6:
                        createPlatformer(0, 80, 4);
                        break;
                    case 7:
                        createBulletHell(3);
                        break;
                    case 8:
                        createText(4, new String[] {"It-it's over. We're finally free!",
                        "The galaxy owes you a debt of gratitude.",
                        "And I, soldier, I especially owe you one.\nWe appreciate your service. So long!",
                        "","","",
                        "What a fool that soldier was.",
                        "I can't believe he wiped our only competition\nfor domination of the galaxy simply at request.",
                        "Now, it is us that are the sole rulers of the galaxy!\nCry havoc, and let slip the dogs of war!",
                        "Credits:\nEthan Li and Anurag Sodhi", "Credits:\nbrullov.itch.io (sprites)\nTerraria (sprites)\nDeviantArt (sprites)"});
                        break;
                    case 9:
                        System.exit(0);
                    default:
                        isGaming = false;
                }
            }
        }

    }

    class PlatformTick extends TimerTask {

        @Override
        public void run() {
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    if (frame.isFocused()) {
                        pGamePanel.requestFocus();
                        pGamePanel.update();
                    }
                    pGamePanel.repaint();
                    if (pGamePanel.shouldTerminate()) {
                        gameTimer.cancel();
                        isGaming = false;
                        if (!pGamePanel.isGameOver()) {
                            stage++;
                        }
                    }
                }
            });
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
