package streetfighter;

import java.awt.Color;
import java.awt.Graphics;
import utility.*;

import javax.swing.JPanel;

public class Arena extends JPanel {
    private int playerWins, opponentWins;
    private Fighter player, opponent;
    public static final int boundx = 1024, boundy = 576;

    public Arena() {
        player = new Fighter(new Hitbox(0, 0, 50, 150), true);
        opponent = new Fighter(new Hitbox(700, 0, 750, 150), false);
        this.addKeyListener(player.getPKA());
        this.addKeyListener(opponent.getPKA());
        this.setFocusable(true);
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
        player.update(opponent);
        opponent.update(player);
    }

    public void paintComponent(Graphics g) {
        g.fillRect(0, boundy, boundx, boundy + 100);
        g.setColor(Color.white);
        g.fillRect(0, 0, boundx, boundy);
        g.setColor(Color.blue);
        g.drawRect(player.getBody().getX1(), boundy - player.getBody().getY2(),
                player.getBody().getX2() - player.getBody().getX1(),
                player.getBody().getY2() - player.getBody().getY1());
        g.setColor(Color.red);
        g.drawRect(opponent.getBody().getX1(), boundy - opponent.getBody().getY2(),
                opponent.getBody().getX2() - opponent.getBody().getX1(),
                opponent.getBody().getY2() - opponent.getBody().getY1());
        if (player.getCurrMove() != null) {
            g.setColor(Color.blue);
            g.drawRect(player.getAttack().getX1(), boundy - player.getAttack().getY2(),
                    player.getAttack().getX2() - player.getAttack().getX1(),
                    player.getAttack().getY2() - player.getAttack().getY1());
        }
        if (opponent.getCurrMove() != null) {
            g.setColor(Color.red);
            g.drawRect(opponent.getAttack().getX1(), boundy - opponent.getAttack().getY2(),
                    opponent.getAttack().getX2() - opponent.getAttack().getX1(),
                    opponent.getAttack().getY2() - opponent.getAttack().getY1());
        }
    }
}