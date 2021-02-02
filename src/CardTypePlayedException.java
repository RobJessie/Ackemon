
public class CardTypePlayedException extends Exception {

	private String b="You've already played a(n) ";
	private String e=" this turn.";
	private String d="You've already played a card of that type this turn";
	
	public CardTypePlayedException() {
		toString();
	}
	
	public CardTypePlayedException(String s) {
		d=b+s+e;
		toString();
	}
	
	public String toString() {
		return d;
	}
}
