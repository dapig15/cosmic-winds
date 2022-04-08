package streetfighter;
import utility.*;
import java.awt.Dimension;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;

public class SFMain {

    private Arena arena;
    Timer timer;

    public Arena getArena() {
        return arena;
    }

    public static void main(String[] args) throws Exception {
        SFMain m = new SFMain();
        m.run();
    }

    public void run() {
        JFrame frame = new JFrame();
        frame.setPreferredSize(new Dimension(1080, 720));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        arena = new Arena();
        frame.add(arena);

        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);

        timer = new Timer();
        timer.schedule(new GameTick(), 0, 42);
    }

    class GameTick extends TimerTask {

        @Override
        public void run() {
            arena.update();
            arena.repaint();
        }
    }
}