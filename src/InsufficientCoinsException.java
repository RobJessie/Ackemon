
public class InsufficientCoinsException extends Exception {

	private String s="Insufficient coins.";
	
	public InsufficientCoinsException() {
		toString();
	}
	
	public InsufficientCoinsException(String s) {
		this.s=s;
		toString();
	}
	
	public String toString() {
		return s;
	}
}
