
public class EnhancementKillException extends Exception {

private String s="Playing that enhancement would kill that creature.";
	
	public EnhancementKillException() {
		toString();
	}
	
	public EnhancementKillException(String s) {
		this.s=s;
		toString();
	}
	
	public String toString() {
		return s;
	}
}
