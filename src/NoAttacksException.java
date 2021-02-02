
public class NoAttacksException extends Exception {

	private String s="That creature has already attacked.";
	
	public NoAttacksException() {
		toString();
	}
	
	public NoAttacksException(String s) {
		this.s=s;
		toString();
	}
	
	public String toString() {
		return s;
	}
}
