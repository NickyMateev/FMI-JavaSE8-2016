package hackathon.voterapplication;

public class TeamAlreadyExistsException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public TeamAlreadyExistsException(String message){
		super(message);
	}
}