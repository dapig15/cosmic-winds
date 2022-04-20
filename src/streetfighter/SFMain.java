package streetfighter;

import java.awt.Color;
import java.awt.Dimension;
import java.util.Timer;
import java.util.TimerTask;
import utility.*;
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
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBackground(Color.black);
        arena = new Arena();
        arena.setBackground(Color.white);
        frame.add(arena);

        frame.setResizable(false);
        frame.setSize(new Dimension(1024, 720));
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
