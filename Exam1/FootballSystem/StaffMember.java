import java.time.LocalDate;

public class StaffMember extends Employee {

	private static final double STAFF_SALARY = 1000;
	private static final double BONUS_PERCENT_PER_YEAR = 0.05;
	private String position;
	private int yearsWithTeam;

	public StaffMember(String name, int age, LocalDate contractTerminationDate, String position, int yearsWithTeam) {
		super(name, age, contractTerminationDate);
		this.position = position;
		this.yearsWithTeam = yearsWithTeam;
	}

	public String getPosition() {
		return position;
	}

	public int getYearsWithTeam() {
		return yearsWithTeam;
	}

	@Override
	public double calculateSalary() {
		double bonusSalary = STAFF_SALARY * BONUS_PERCENT_PER_YEAR;
		
		return STAFF_SALARY + (yearsWithTeam * bonusSalary);
	}
}
