package sf;
import utility.*;
public class Move {
    private final Hitbox attackArea; //(area of effective attack)
    private final int startup, active, recovery; //(# of frames for move startup, attacking period, recovery period)
    private final int fullDamage, blockDamage, critDamage; //(amount of damage dealt when opp vulnerable/blocking/critically hit)
    private final double critChance;
    private final int comboPos; //(# of possible consecutive follow-up moves can be registered after this attack)
    private final int oppRecovery; //(# of frames for which opponent is vulnerable if attack is successful)
    private final double launchx, launchy; //(velocity at which opp is launched)
    private final double selflaunchy; //(velocity at which self is launched)
    
    public Move(Hitbox attackArea, int startup, int active, int recovery, int fullDamage, int blockDamage,
            int critDamage, double critChance, int comboPos, int oppRecovery, double launchx, double launchy, double selflaunchy) {
        this.attackArea = attackArea;
        this.startup = startup;
        this.active = active;
        this.recovery = recovery;
        this.fullDamage = fullDamage;
        this.blockDamage = blockDamage;
        this.critDamage = critDamage;
        this.critChance = critChance;
        this.comboPos = comboPos;
        this.oppRecovery = oppRecovery;
        this.launchx = launchx;
        this.launchy = launchy;
        this.selflaunchy = selflaunchy;
    }
    public Hitbox getAttackArea() {
        return attackArea;
    }
    public int getStartup() {
        return startup;
    }
    public int getActive() {
        return active;
    }
    public int getRecovery() {
        return recovery;
    }
    public int getFullDamage() {
        return fullDamage;
    }
    public int getBlockDamage() {
        return blockDamage;
    }
    public int getCritDamage() {
        return critDamage;
    }
    public double getCritChance() {
        return critChance;
    }
    public int getComboPos() {
        return comboPos;
    }
    public int getOppRecovery() {
        return oppRecovery;
    }
    public double getLaunchx() {
        return launchx;
    }
    public double getLaunchy() {
        return launchy;
    }
    public double getSelflaunchy() {
        return selflaunchy;
    }
}