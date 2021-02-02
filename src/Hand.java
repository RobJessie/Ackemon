import java.util.ArrayList;
public class Hand {

	private ArrayList<Card> handCollection;
	
	public Hand() {
		handCollection=new ArrayList<>();
	}
	
	public void addToHand(Card e) {
		handCollection.add(e);
	}
	
	public void removeFromHand(Card q) {
		handCollection.remove(q);
	}
	
	
	public ArrayList<Card> getHand() {
		return handCollection;
	}
	
//	public void isPlayable() {
//		if(Player.getCurrentCoins() >= Card.getCost()) {
//			cardIsPlayable = true;
//		}
//	}	
	
}
