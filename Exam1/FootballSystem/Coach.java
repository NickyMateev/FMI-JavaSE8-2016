import java.time.LocalDate;

public class Coach extends Employee {

	private static final double COACH_SALARY = 8000;
	private static final double BONUS_SALARY = 5000;
	private static final double MIN_TITLES = 5;

	private CoachPosition coachPosition;
	private int titlesCount;

	public Coach(String name, int age, LocalDate contractTerminationDate, CoachPosition coachPosition,
			int titlesCount) {
		super(name, age, contractTerminationDate);
		this.coachPosition = coachPosition;
		this.titlesCount = titlesCount;
	}

	public CoachPosition getCoachPosition() {
		return coachPosition;
	}

	public int getTitlesCount() {
		return titlesCount;
	}

	@Override
	public double calculateSalary() {
		if(titlesCount > MIN_TITLES){
			return COACH_SALARY + BONUS_SALARY;
		} else {
			return COACH_SALARY;
		}
	}
}
