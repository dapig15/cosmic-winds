public class Move {
	private int[] hitbox; //x1, y1, x2, y2 compared to lower right
	private int damage;
	public static final double critChance = 0.1, critMult = 1.5, blockMult = 0.25;
	public Move(int[] hitbox, int damage) {
		this.hitbox = hitbox.clone();
		this.damage = damage;
	}
	public int[] getHitbox() {
		return hitbox.clone();
	}
	public int damage() {
		return damage;
	}
}