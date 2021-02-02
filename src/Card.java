
public abstract class Card {
	
	protected int cost;
	protected String name;
	private boolean isPlayable;
	

	public Card(String name, int cost) {
		this.name = name;
		this.cost = cost;

	}


	public boolean isPlayable() {
		return isPlayable;
	}


	public void setPlayable(boolean isPlayable) {
		this.isPlayable = isPlayable;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

}