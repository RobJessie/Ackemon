import java.util.ArrayList;

public class Player {
	
	private int wins;
	private int losses;
	private ArrayList<Card> cardCollection;
	private Deck playerDeck;
	private int currentCoins;
	private int maxCoins;
	private int lifePoints;
	private Hand playerHand;
	private ArrayList<Card> played;
	private ArrayList<Card> discard;
	private String name;
	private boolean creaturePlayed=false;
	private boolean enhancementPlayed=false;
	
	public Player() {
		wins = 0;
		losses = 0;
		currentCoins = 1;
		maxCoins = 1;
		lifePoints = 50;
		playerDeck=new Deck();
		playerHand=new Hand();
		cardCollection=new ArrayList<>();
		played=new ArrayList<>();
		discard=new ArrayList<>();
	}

	public int getWins() {
		return wins;
	}

	public void addToWins() {
		wins = wins + 1;
	}

	public int getLosses() {
		return losses;
	}

	public void addToLosses() {
		losses = losses + 1;
	}

	public ArrayList<Card> getCardCollection() {
		return cardCollection;
	}

	public void addToCardCollection (Card e) {
		cardCollection.add(e);
	}
	
	public void removeFomCardCollection (Card r) {
		cardCollection.remove(r);
	}

	public Deck getPlayerDeck() {
		return playerDeck;
	}

	public void addToPlayerDeck (Card q) {
		playerDeck.addToDeck(q);
	}
	
	public void removeFomPlayerDeck (Card w) {
		playerDeck.removeFromDeck(w);
	}

	public int getCurrentCoins() {
		return currentCoins;
	}
	
	public void removeCurrentCoins(Card c) {
		int cost = c.getCost();
		currentCoins = currentCoins - cost;
	} 

	public void resetCurrentCoins() {
		currentCoins = maxCoins;
	}

	public int getMaxCoins() {
		return maxCoins;
	}

	public void addToMaxCoins() {
		maxCoins = maxCoins + 1;
	}

	public int getLifePoints() {
		return lifePoints;
	}

	public void removeLifePoints(Creature a) {
		int attackPoints = a.getAttack();
		lifePoints = lifePoints - attackPoints;
	}
	
	public void removeLifePoints(int a) {
		lifePoints = lifePoints - a;
	}
	
	public Hand getPlayerHand() {
		return playerHand;
	}
	
	public void addToPlayerHand (Card x) {
		playerHand.addToHand(x);
	}
	
	public void removeFromPlayerHand (Card y) {
		playerHand.removeFromHand(y);
	}
	
	public ArrayList<Card> getPlayed(){
		return played;
	}
	
	public void addToPlayed(Card c) {
		played.add(c);
	}
	
	public void removeFromPlayed(Card c) {
		played.remove(c);
	}
	
	public ArrayList<Card> getDiscard(){
		return played;
	}
	
	public void addToDiscard(Card c) {
		discard.add(c);
	}
	
	public void removeFromDiscard(Card c) {
		played.add(c);
	}
	
	public void setName(String s) {
		name=s;
	}
	
	public String getName() {
		return name;
	}
	
	public boolean isCreaturePlayed() {
		return creaturePlayed;
	}

	public void setCreaturePlayed(boolean creaturePlayed) {
		this.creaturePlayed = creaturePlayed;
	}

	public boolean isEnhancementPlayed() {
		return enhancementPlayed;
	}

	public void setEnhancementPlayed(boolean enhancementPlayed) {
		this.enhancementPlayed = enhancementPlayed;
	}
	
	public void removeAllLP() {
		lifePoints=0;
	}
	
	public void resetLP() {
		currentCoins = 1;
		maxCoins = 1;
		lifePoints = 50;
	}
	public void resetDeck() {
		playerDeck.setDeckCollection(cardCollection);
	}
}