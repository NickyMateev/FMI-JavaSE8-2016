import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
	
	public static void main(String[] args) {
		runApplication();
	}

	public static void runApplication() {

		TodoList todoList = new TodoList(generateTasks());

		Scanner scanner = new Scanner(System.in);
		int userInput;

		while (true) {
			todoList.printHelp();

			System.out.print("\n\nInput: ");
			try {
				userInput = scanner.nextInt();
				switch (userInput) {
				case 1:
					todoList.printTasksByPriority();
					break;
				case 2:
					todoList.printTasksInProcess();
					break;
				case 3:
					todoList.printDueTasks();
					break;
				case 4:
					System.err.println("\n\nApplication stopped.");
					return;
				default:
					throw new InvalidInputException("ERROR: Invalid argument! Try again...");
				}
			} catch (Exception ex) {
				System.out.println(ex.getMessage() + "\n\n\n");
			}
		}

	}

	// hardcoded tasks
	public static List<Task> generateTasks() {
		List<Task> tasks = new ArrayList<>();
		tasks.add(new Task("Task1", "Thing to do #1", TaskStatus.INITIAL, 3, LocalDate.now()));
		tasks.add(new Task("Task2", "Thing to do #2", TaskStatus.INITIAL, 1, LocalDate.now().minusDays(1)));
		tasks.add(new Task("Task3", "Thing to do #3", TaskStatus.IN_PROCESS, 5, LocalDate.now().plusDays(3)));
		tasks.add(new Task("Task4", "Thing to do #4", TaskStatus.DONE, 2, LocalDate.now().plusDays(12)));
		tasks.add(new Task("Task5", "Thing to do #5", TaskStatus.IN_PROCESS, 4, LocalDate.now().minusDays(6)));
		tasks.add(new Task("Task6", "Thing to do #6", TaskStatus.IN_PROCESS, 1, LocalDate.now().minusDays(1)));
		tasks.add(new Task("Task7", "Thing to do #7", TaskStatus.INITIAL, 1, LocalDate.now().plusDays(1)));

		return tasks;
	};
}
