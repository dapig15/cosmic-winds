package sf;
import java.awt.*;
import javax.swing.*;

import utility.*;
public class Arena {
    private int playerWins, opponentWins;
    private final Fighter player, opponent;
    public static final int boundx = 1080, boundy = 640;
    public Arena(Fighter player, Fighter opponent) {
        this.player = player;
        this.opponent = opponent;
    }
    public int getPlayerWins() {
        return playerWins;
    }
    public void setPlayerWins(int playerWins) {
        this.playerWins = playerWins;
    }
    public int getOpponentWins() {
        return opponentWins;
    }
    public void setOpponentWins(int opponentWins) {
        this.opponentWins = opponentWins;
    }
    public Fighter getPlayer() {
        return player;
    }
    public Fighter getOpponent() {
        return opponent;
    }
    public void frameUpdate(boolean[] playerInputs, boolean[] opponentInputs) { // left, right, light, heavy, special
        
    }
    public static void main(String[] args) throws Exception {
        JFrame frame = new JFrame();
        frame.setPreferredSize(new Dimension(1080, 748));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.BLACK);
        mainPanel.setBounds(0, 0, 1080, 720);
        mainPanel.setLayout(null);
        frame.add(mainPanel);
        JPanel gamePanel = new JPanel();
        gamePanel.setLayout(null);
        gamePanel.setBounds(0, 40, 1080, 640);
        mainPanel.add(gamePanel);
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
    }
}