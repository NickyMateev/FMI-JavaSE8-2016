package hackathon.voterapplication;

public class NonExistingTeamException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public NonExistingTeamException(String message) {
		super(message);
	}
}
