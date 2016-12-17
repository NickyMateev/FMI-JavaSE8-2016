package todoapplication;
import java.time.LocalDate;

public class Task implements Comparable<Task>{
	
	private String title;
	private String description;
	private TaskStatus status;
	private int priority;
	private LocalDate deadline;
	
	
	public Task(String title, String description, TaskStatus status, int priority, LocalDate deadline) {
		this.title = title;
		this.description = description;
		this.status = status;
		this.priority = priority;
		this.deadline = deadline;
	}

	public String getTitle() {
		return title;
	}
	public String getDescription() {
		return description;
	}
	public TaskStatus getStatus() {
		return status;
	}
	public int getPriority() {
		return priority;
	}
	public LocalDate getDeadline() {
		return deadline;
	}

	@Override
	public int compareTo(Task other) {
		if(this.priority < other.priority){
			return 1;
		} else if (this.priority > other.priority){
			return -1;
		} else {
			return 0;
		}
	}

	@Override
	public String toString() {
		return title + ", " + description + ", " + status + ", " + priority + ", " + deadline;
	}

	
}
