package todoapplication;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.FileTime;
import java.time.LocalDate;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class TodoList {

	private List<Task> tasks;

	private static Scanner scanner;

	private static final int DAYS_DUE = 3;
	private static final long DAY_IN_MILLIS = 86_400_000;

	public TodoList(List<Task> tasks) {
		this.tasks = new ArrayList<>(tasks);
		this.scanner = new Scanner(System.in);
	}

	private List<Task> getTasksByPriority() {
		List<Task> sortedTasks = new ArrayList<>(tasks);
		Collections.sort(sortedTasks);

		return sortedTasks;
	}

	// Option 1:
	public void printTasksByPriority() {
		List<Task> sortedTasks = getTasksByPriority();
		for (int i = sortedTasks.size() - 1; i >= 0; i--) {
			System.out.println(sortedTasks.get(i));
		}
	}

	// Option 2:
	public void printTasksInProcess() {
		List<Task> sortedTasks = getTasksByPriority(); 
		
		Task currTask;
		for (int i = sortedTasks.size() - 1; i >= 0; i--) {
			currTask = sortedTasks.get(i);
			if (currTask.getStatus() == TaskStatus.IN_PROCESS) {
				System.out.println(currTask);
			}
		}
	}

	// Option 3:
	public void printDueTasks(){
		printDueTasks(DAYS_DUE);
	}
	
	// Option 4:
	public void importTasks(String sourcePath) throws IOException{
	
		tasks.clear();

		try (BufferedReader reader = Files.newBufferedReader(Paths.get(sourcePath))) {
			String line = null;
			while((line = reader.readLine()) != null){
				tasks.add(constructTask(line));
			}
		} 
	}
	
	public Task constructTask(String line){
		String[] elements = line.split(",");
		return new Task(elements[0], elements[1], TaskStatus.valueOf(elements[2].trim()), Integer.parseInt(elements[3].trim()), LocalDate.parse(elements[4].trim()));
	}

	// Option 5:
	public void exportTasks(String destinationPath) throws IOException {
		Path destination = Paths.get(destinationPath);
		if(Files.exists(destination, LinkOption.NOFOLLOW_LINKS)){
			FileTime fileTime = Files.getLastModifiedTime(destination, LinkOption.NOFOLLOW_LINKS);
			long difference = System.currentTimeMillis() - fileTime.toMillis();
			if(difference < DAY_IN_MILLIS){
				Files.copy(Paths.get(destinationPath), Paths.get(destinationPath + "_copy"), StandardCopyOption.REPLACE_EXISTING);
			} else {
				// input file:
		        FileInputStream in = new FileInputStream(destinationPath);

		        // output file:
		        Files.createDirectories(Paths.get(LocalDate.now().toString()));
		        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(LocalDate.now().toString() + "/Backup.zip"));

		        // name of the file inside the ZIP file:
		        out.putNextEntry(new ZipEntry(destinationPath + "_copy")); 

		        // buffer size:
		        byte[] b = new byte[1024];
		        int count;

		        while ((count = in.read(b)) > 0) {
		            out.write(b, 0, count);
		        }
		        out.close();
		        in.close();
			}

		}
		
		try(BufferedWriter writer = Files.newBufferedWriter(destination)){
			String taskStr = null;
			for (Task task : tasks) {
				taskStr = task.toString();
				writer.write(taskStr);
				writer.write(System.getProperty("line.separator"));
			}
		}
	}
	
	
	public void printDueTasks(int days) {
		List<Task> sortedTasks = getTasksByPriority();
		
		Task currTask;
		for (int i = sortedTasks.size() - 1; i >= 0; i--) {
			currTask = sortedTasks.get(i);
			if ((currTask.getStatus() == TaskStatus.IN_PROCESS || currTask.getStatus() == TaskStatus.INITIAL)
					&& isDueIn(currTask.getDeadline(), days)) {
				System.out.println(currTask);
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
		System.out.println("4) Import tasks from file");
		System.out.println("5) Export tasks to file");
		System.out.println("6) Exit");
	}
	
}