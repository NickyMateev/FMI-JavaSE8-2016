import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class TodoList {

	private List<Task> tasks;
	private List<Task> cachedSortedTasks;

	private static Scanner scanner;

	private static final int DAYS_DUE = 3;

	public TodoList(List<Task> tasks) {
		this.tasks = new ArrayList<>(tasks);
		this.scanner = new Scanner(System.in);
	}

	private void printTaskInfo(Task task) {
		System.out.println("***Task info***");
		System.out.println("Title: " + task.getTitle());
		System.out.println("Status: " + task.getStatus());
		System.out.println("Priority: " + task.getPriority());
		System.out.println("Deadline: " + task.getDeadline() + '\n');
	}

	// due to the nature of the task, TodoList is a immutable class, it is not inteded to be modified; so it's okay for us to cache the sorted tasks after the first sort
	private List<Task> getTasksByPriority() {
		if (cachedSortedTasks == null) {
			cachedSortedTasks = new ArrayList<>(tasks);
			Collections.sort(cachedSortedTasks);
		}

		return cachedSortedTasks;
	}

	// Option 1:
	public void printTasksByPriority() {
		List<Task> sortedTasks = getTasksByPriority();
		for (int i = sortedTasks.size() - 1; i >= 0; i--) {
			printTaskInfo(tasks.get(i));
		}
	}

	// Option 2:
	public void printTasksInProcess() {
		List<Task> sortedTasks = getTasksByPriority(); 
		
		Task currTask;
		for (int i = sortedTasks.size() - 1; i >= 0; i--) {
			currTask = sortedTasks.get(i);
			if (currTask.getStatus() == TaskStatus.IN_PROCESS) {
				printTaskInfo(currTask);
			}
		}
	}

	// Option 3:
	public void printDueTasks(){
		printDueTasks(DAYS_DUE);
	}
	
	public void printDueTasks(int days) {
		List<Task> sortedTasks = getTasksByPriority();
		
		Task currTask;
		for (int i = sortedTasks.size() - 1; i >= 0; i--) {
			currTask = sortedTasks.get(i);
			if ((currTask.getStatus() == TaskStatus.IN_PROCESS || currTask.getStatus() == TaskStatus.INITIAL)
					&& isDueIn(currTask.getDeadline(), days)) {
				printTaskInfo(currTask);
			}
		}
	}

	private boolean isDueIn(LocalDate date, int days) {
		return date.isAfter(LocalDate.now()) && date.minusDays(days).isBefore(LocalDate.now());
	}

	public void printHelp() {
		System.out.println("Make a choice:");
		System.out.println("1) Print tasks ordered by priority");
		System.out.println("2) Print tasks with status IN_PROCESS");
		System.out.println("3) Print tasks due in 3 days");
		System.out.println("4) Exit");
	}
	
}