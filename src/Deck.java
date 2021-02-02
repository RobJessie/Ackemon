import java.util.*;
public class Deck {
	
	private ArrayList<Card> DeckCollection = new ArrayList<>();
	
	
	public Deck() {
		
	}
	
	public void addToDeck(Card c) {
		DeckCollection.add(c);
	}
	
	public void removeFromDeck(Card c) {
		DeckCollection.remove(c);
	}
	
	public void setDeckCollection(ArrayList<Card> d) {
		DeckCollection.clear();
		d.forEach(c->DeckCollection.add(c));
	}
	
	public ArrayList<Card> getDeckCollection() {
		return DeckCollection;
		
	}
	
	public int getDeckSize() {
		int x = DeckCollection.size();
		return x;
	}
	
	public Card getCard(int i) {
		return DeckCollection.get(i);
	}

}