
public class CombinationResult {
	
	private int bulls;
	private int cows;

	public CombinationResult(int bulls, int cows) {
		this.bulls = bulls;
		this.cows = cows;
	}

	public int getBulls() {
		return bulls;
	}

	public int getCows() {
		return cows;
	}
	
	@Override
	public String toString() {
		return "Bulls: " + this.bulls + "\nCows: " + this.cows;
	}
	
}
