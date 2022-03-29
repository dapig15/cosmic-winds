package sf;
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
}