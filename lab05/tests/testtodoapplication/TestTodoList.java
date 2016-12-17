package testtodoapplication;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Test;

import todoapplication.Task;
import todoapplication.TaskStatus;

public class TestTodoList {
	
	@Test
	public void testConstructTask() {
		String expected = "Swim, Don't die, IN_PROCESS, 1, 2016-12-12";
		Task actual = new Task("Swim", "Don't die", TaskStatus.IN_PROCESS, 1, LocalDate.of(2016, 12, 12));
		
		assertEquals(expected, actual.toString());
	}
}