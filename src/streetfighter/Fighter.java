import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;

public class Fighter {
	class PlayerKeyAdapter extends KeyAdapter {
        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            if (key == right && !player || key == altRight && player) {
                moveRight = true;
            }
            if (key == left && !player || key == altLeft && player) {
                moveLeft = true;
            }
            if (key == down && !player || key == altDown && player) {
                moveDown = true;
            }
            if (key == up && !player || key == altUp && player) {
                moveUp = true;
            }
            if (key == light && player || key == altLight && !player) {
                moveLight = true;
            }
            if (key == heavy && player || key == altHeavy && !player) {
                moveHeavy = true;
            }
            if (key == special && player || key == altSpecial && !player) {
                moveSpecial = true;
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            int key = e.getKeyCode();
            if (key == right && !player || key == altRight && player) {
                moveRight = false;
            }
            if (key == left && !player || key == altLeft && player) {
                moveLeft = false;
            }
            if (key == down && !player || key == altDown && player) {
                moveDown = false;
            }
            if (key == up && !player || key == altUp && player) {
                moveUp = false;
            }
            if (key == light && player || key == altLight && !player) {
                moveLight = false;
            }
            if (key == heavy && player || key == altHeavy && !player) {
                moveHeavy = false;
            }
            if (key == special && player || key == altSpecial && !player) {
                moveSpecial = false;
            }
        }
    }
    
	private boolean moveRight, moveLeft, moveDown, moveUp, moveLight, moveHeavy, moveSpecial;
	private int right = KeyEvent.VK_RIGHT, altRight = KeyEvent.VK_D;
    private int left = KeyEvent.VK_LEFT, altLeft = KeyEvent.VK_A;
    private int down = KeyEvent.VK_DOWN, altDown = KeyEvent.VK_S;
    private int up = KeyEvent.VK_UP, altUp = KeyEvent.VK_W;
    private int light = KeyEvent.VK_J, altLight = KeyEvent.VK_B;
    private int heavy = KeyEvent.VK_K, altHeavy = KeyEvent.VK_N;
    private int special = KeyEvent.VK_L, altSpecial = KeyEvent.VK_M;
    private PlayerKeyAdapter pka = new PlayerKeyAdapter();
    
    private Move lightMove = new Move(new int[] {0, 50, 50, 100}, 10),
    		heavyMove = new Move(new int[] {0, 0, 50, 50}, 20),
    		specialMove = new Move(new int[] {0, 100, 50, 150}, 30),
    		currMove;
    
    private Hitbox body;
    private Hitbox attack;
    
    private boolean player;
    private int width = 50, height = 150;
    private int speed = 15, jump = 70, gravity = 13;
    private int yspeed;
    private int freeze = 0;
    
    public Fighter(Hitbox body, boolean player) {
        this.body = body;
        this.player = player;
    }
    
    public Hitbox getBody() {
        return body;
    }
    
    public void setBody(Hitbox body) {
        this.body = body;
    }
    
    public Hitbox getAttack() {
    	return attack;
    }
    
    public PlayerKeyAdapter getPKA() {
    	return pka;
    }
    
    public Move getCurrMove() {
    	return currMove;
    }
    
    public void update(Fighter other) {
    	if (moveSpecial) {
	   		currMove = specialMove;
	   	} else if (moveHeavy) {
	   		currMove = heavyMove;
	   	} else if (moveLight) {
	    	currMove = lightMove;
	    } else {
	   		currMove = null;
	   	}	
    	
    	if (currMove == null) {
	    	int xInc = 0;
			if (moveRight)
				xInc += speed;
			if (moveLeft)
				xInc += -speed;
			if (body.getY1() == 0) {
				if (moveUp)
					yspeed = jump;
			} else
				yspeed -= gravity;
				body.shiftHitbox(xInc, yspeed);
			if (body.getY1() < 0)
				body.shiftHitbox(0, -body.getY1());
			if (body.getX1() < 0)
				body.shiftHitbox(-body.getX1(), 0);
			if (body.getX2() < 0)
				body.shiftHitbox(-body.getX2(), 0);
			if (body.getX1() > Arena.boundx)
				body.shiftHitbox(Arena.boundx - body.getX1(), 0);
			if (body.getX2() > Arena.boundx)
				body.shiftHitbox(Arena.boundx - body.getX2(), 0);
    	}
		
		
		if (currMove != null) {
	    	int updates[] = currMove.getHitbox();
	    	if (other.getBody().getX1() < body.getX1()) {
	    		attack = new Hitbox(body.getX1() - updates[2], body.getY1() + updates[1], body.getX1() - updates[0], body.getY1() + updates[3]);
	    	} else {
	    		attack = new Hitbox(body.getX2() + updates[0], body.getY1() + updates[1], body.getX2() + updates[2], body.getY1() + updates[3]);
	    	}		
	    } else {
	    	attack = null;
	    }
    }
}