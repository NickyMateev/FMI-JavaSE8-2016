import java.time.LocalDate;

public class FootballPlayer extends Employee {

	private static final double PLAYER_SALARY = 10000;
	private static final double SALARY_PER_GAME = 250;
	private PlayerPosition playerPosition;
	private int gamesPlayed;

	public FootballPlayer(String name, int age, LocalDate contractTerminationDate, PlayerPosition playerPosition,
			int gamesPlayed) {
		super(name, age, contractTerminationDate);
		this.playerPosition = playerPosition;
		this.gamesPlayed = gamesPlayed;
	}

	public PlayerPosition getPosition() {
		return playerPosition;
	}

	public int getGamesPlayed() {
		return gamesPlayed;
	}
	
	@Override
	public double calculateSalary() {
		return PLAYER_SALARY + (gamesPlayed * SALARY_PER_GAME);
	}
	
}
