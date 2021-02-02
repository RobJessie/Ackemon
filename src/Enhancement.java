public class Enhancement extends Card{

	int enhancementHealth;
	int enhancementAttack;
	
	public Enhancement(String name, int cost, int a, int h) {
		super(name,cost);
		enhancementHealth=h;
		enhancementAttack=a;
	}
	
	public void setEnhancementHealth(int health) {
		enhancementHealth = health;
	}
	
	public void setEnhancementAttack(int attack) {
		enhancementAttack = attack;
	}
	
	public int getEnhancementHealth() {
		return enhancementHealth;
	}
	
	public int getEnhancementAttack() {
		return enhancementAttack;
	}
	
	public void enhance(Creature c) throws EnhancementKillException{
		int effectiveness=1;
		
		if(c.getIsRare())
			effectiveness=2;
		
		if((c.getHealth()+(enhancementHealth*effectiveness))>0) {
			c.setHealth(c.getHealth()+(enhancementHealth*effectiveness));
			
			if(c.getAttack()+(enhancementAttack*effectiveness)>0)
			c.setAttack(c.getAttack()+(enhancementAttack*effectiveness));
			else
			c.setAttack(0);
		}
		else
			throw new EnhancementKillException();
	}
	
	public String toString() {
		return "Enhancement: "+name+", Atk: "+enhancementAttack+", HP: "+enhancementHealth;
	}
}