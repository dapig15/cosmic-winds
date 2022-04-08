package streetfighter;
import javax.swing.JPanel;

import utility.*;
public class Arena extends JPanel {
    private int playerWins, opponentWins;
    private final Fighter player, opponent;
    public static final int boundx = 1080, boundy = 640;
    public Arena() {
        player = new Fighter(new Hitbox(0, 0, 160, 320));
        opponent = new Fighter(new Hitbox(0, 0, 160, 320));
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
    public void update() {

    }
}