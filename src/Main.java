import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main {
    public static void main(String[] args) throws Exception {
        JFrame frame = new JFrame();
        frame.setPreferredSize(new Dimension(1080, 720));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.RED);
        mainPanel.setLayout(null);
        frame.add(mainPanel);
        JPanel gamePanel = new JPanel();
        gamePanel.setLayout(null);
        gamePanel.setBounds(50, 0, 540, 720);
        mainPanel.add(gamePanel);
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
    }
}
