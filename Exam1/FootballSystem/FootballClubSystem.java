import java.time.LocalDate;
import java.util.Arrays;

public class FootballClubSystem {

	private static final int CLUB_CAPACITY = 1000;
	private Employee[] employees;
	private int employeeCount;

	public FootballClubSystem() {
		this.employees = new Employee[CLUB_CAPACITY];
		this.employeeCount = 0;
	}

	public void addEmployee(Employee employee) throws ClubFullException{
		if(employeeCount < CLUB_CAPACITY){
			employees[employeeCount] = employee;
			employeeCount++;
		} else {
			throw new ClubFullException("ERROR: Club is full!");
		}
	}
	
	public void printEmployeesByContract(){
		Arrays.sort(employees);
		for (Employee employee : employees) {
			System.out.println(employee);
		}
	}
	
	public void printEmployeesWithDueContracts(LocalDate date){
		for (Employee employee : employees) {
			if(employee.getContractTerminationDate().isBefore(date)){
				System.out.println(employee);
			}
		}
	}
	
	
	
}
