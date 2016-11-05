import java.time.LocalDate;

public abstract class Employee implements Comparable<Employee> {
	
	private static final int MIN_AGE = 18;
	private String name;
	private int age;
	private LocalDate contractTerminationDate;

	public Employee(String name, int age, LocalDate contractTerminationDate) throws InvalidAgeException {
		if(age < MIN_AGE){
			throw new InvalidAgeException("ERROR: Invalid age!");
		}
		
		this.name = name;
		this.age = age;
		this.contractTerminationDate = contractTerminationDate;
	}

	public String getName() {
		return name;
	}

	public int getAge() {
		return age;
	}

	public LocalDate getContractTerminationDate() {
		return contractTerminationDate;
	}
	
	public abstract double calculateSalary();
	
	@Override
	public int compareTo(Employee other) {
		if(this.contractTerminationDate.isBefore(other.contractTerminationDate)){
			return -1;
		} else if (this.contractTerminationDate.isAfter(other.contractTerminationDate)){
			return 1;
		} else {
			return this.name.compareTo(other.name);
		}
	}

	@Override
	public String toString() {
		return "Employee [name=" + name + ", age=" + age + ", contractTerminationDate=" + contractTerminationDate + "]";
	}
}
