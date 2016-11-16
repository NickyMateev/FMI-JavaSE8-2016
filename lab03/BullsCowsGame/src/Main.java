import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Main {

	private static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) throws InvalidCombinationException {
		runApplication();
	}

	private static void runApplication() throws InvalidCombinationException {

		BullsCowsGame<Integer> game = new BullsCowsGame<>();
		int combinationLength = BullsCowsGame.DEFAULT_COMBINATION_LENGTH;
		System.out.println("A " + combinationLength + " digit number was generated by the computer.");

		Integer[] userCombination = new Integer[combinationLength];
		while (!game.isOver()) {
			System.out.println("\n\nGuess: ");
			for (int i = 0; i < userCombination.length; i++) {
				System.out.print("Element #" + (i + 1) + ": ");
				try {
					userCombination[i] = Integer.parseInt(scanner.nextLine());
				} catch (Exception ex) {
					System.out.println("Invalid input! Try again...");
					i--;
					continue;
				}
			}

			CombinationResult result = game.evaluateCombination(Arrays.asList(userCombination));
			System.out.println("\nResult:\n" + result);
		}

		System.out.print("\n\nYou win! The combination was: ");
		System.out.println(Arrays.asList(userCombination));
	}

}