
public class Creature extends Card {

	private boolean isRare;
	private int defaultHealth;
	private int health;
	private int defaultAttack;
	private int attack;
	private int cost;
	private int type;
	private int atkCount=1;

	
	public Creature(String name, int cost, int attack, int health, int type, boolean isRare) {
		super(name, cost);
		this.isRare=isRare;
		this.attack=attack;
		defaultAttack=attack;
		this.health=health;
		defaultHealth=health;
		this.type=type;
	}

	public boolean getIsRare() {
		return isRare;
	}

	public void setIsRare(boolean isRare) {
		this.isRare = isRare;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public int getAttack() {
		return attack;
	}

	public void setAttack(int attack) {
		this.attack = attack;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	public int getAtkCount() {
		return atkCount;
	}

	public void decrementAtkCount() {
		atkCount--;
	}
	
	public void resetAtkCount() {
		atkCount=1;
	}
	
	public void resetStats() {
		attack=defaultAttack;
		health=defaultHealth;
	}
	
	public String toString() {
		return "Creature: "+name+", Type: "+type+", Atk: "+attack+", HP: "+health+", Cost: "+cost+", Rare: "+isRare;
	}


}