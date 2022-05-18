import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Stack;
import java.util.TimerTask;
import java.util.Timer;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import utility.FontGenerator;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Text extends JPanel implements ActionListener {
    class PlayerKeyAdapter extends KeyAdapter {
        private boolean clicked = false;
        public void keyTyped(KeyEvent e) {
		}
        public void keyPressed(KeyEvent e) {
			int key = e.getKeyCode();
			if (key == KeyEvent.VK_SPACE) {
                System.out.println("lol");
                if (!clicked) {
                    i++;
                    clicked = true;
                }
            }
        }
        public void keyReleased(KeyEvent e) {
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_SPACE) {
                clicked = false;
            }
        }
    }

    private String[] text;
    private Main ref;
    private int title;
    private boolean clickable = true;
    private int i = -1;
    public Text(String[] text, Main ref, int title) {
        this.ref = ref;
        this.text = text;
        this.title = title;
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.setPreferredSize(new Dimension(1080, 720));
        //this.addKeyListener(new PlayerKeyAdapter());
        this.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                if (clickable) {
                    i++;
                    clickable = false;
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                clickable = true;
                repaint();
            }
        });
    }
    public void paintComponent(Graphics g) {
        if (i < 0) {
            if (title == 1) {
                FontGenerator.writeCenteredText(g, "COSMIC WINDS\n(click to continue)", this.getWidth()/2, this.getHeight()/2-50, 4, Color.BLACK);
            } else if (title == 2 || title == 3) {
                FontGenerator.writeCenteredText(g, "STAGE " + title + "\n(click to continue)", this.getWidth()/2, this.getHeight()/2-50, 4, Color.BLACK);
            } else {
                FontGenerator.writeCenteredText(g, "THE END\n(click to continue)", this.getWidth()/2, this.getHeight()/2-50, 4, Color.BLACK);
            }
        } else if (i < text.length - 1) {
            FontGenerator.writeCenteredText(g, text[i] + "\n(click to continue)", this.getWidth()/2, this.getHeight()/2, 2, Color.WHITE);
        } else if (i == (text.length - 1)) {
            if (title != 4) {
                FontGenerator.writeCenteredText(g, text[i] + "\n(click to commence stage)", this.getWidth()/2, this.getHeight()/2, 2, Color.WHITE);
            } else {
                FontGenerator.writeCenteredText(g, text[i] + "\n(double-click to exit game)", this.getWidth()/2, this.getHeight()/2, 2, Color.WHITE);
            }
        } else {
            ref.increaseStage();
        }
    }
    public void actionPerformed(ActionEvent e) {
        
	}
    public boolean shouldTerminate() {
        if (i >= text.length) {
            System.out.println("called");
            return true;
        } else {
            return false;
        }
    }
}