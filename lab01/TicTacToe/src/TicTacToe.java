import java.awt.Dimension;
import java.util.Arrays;
import java.util.Scanner;

public class TicTacToe {

	enum PlayerMove {
		PLAYER1, PLAYER2
	}

	enum EvaluateWinner {
		PLAYER1, PLAYER2, NONE
	}

	// board info:
	private static final int DIMENSION = 3;
	private char[][] board = new char[DIMENSION][DIMENSION];
	private char player1Symbol = 'X'; // default values
	private char player2Symbol = 'O';

	// game info:
	private boolean isRunning = true;
	private PlayerMove whoIsFirst = PlayerMove.PLAYER1;

	// moves info:
	private int movesCounter = 0;
	private int minimumMovesForWin = (2 * DIMENSION) - 1;
	private int maximumMoves = DIMENSION * DIMENSION;

	// other necessary fields:
	private static TicTacToe instance = null;
	private static Scanner scanner = new Scanner(System.in);

	// this field will help us simulate clearing the console after each move
	private char[] newLines = null;

	private TicTacToe() {
		boardSetUp();
	}

	public static TicTacToe getInstance() {
		if (instance == null) {
			instance = new TicTacToe();
		}

		return instance;
	}

	private void boardSetUp() {
		int counter = 1;
		for (int i = 0; i < DIMENSION; i++) {
			for (int j = 0; j < DIMENSION; j++) {
				board[i][j] = (char) ('0' + counter);
				counter++;
			}
		}
	}

	private void assignPlayers() {
		System.out.println("Decide who's going to use the X symbol in the game(1 or 2):");
		System.out.println("1) Player 1");
		System.out.println("2) Player 2");
		System.out.print("Input: ");
		int input = scanner.nextInt();

		switch (input) {
		case 1:
			player1Symbol = 'X';
			player2Symbol = 'O';
			whoIsFirst = PlayerMove.PLAYER1;
			break;
		case 2:
			player1Symbol = 'O';
			player2Symbol = 'X';
			whoIsFirst = PlayerMove.PLAYER2;
			break;
		default:
			System.out.println("Invalid input! Default settings will be used.");
			break;
		}

		System.out.println("\nGame set up with:");
		System.out.println(String.format("Player 1 -> %c", player1Symbol));
		System.out.println(String.format("Player 2 -> %c\n", player2Symbol));
	}

	public void run() {

		assignPlayers();
		pressAnyKeyToContinue();
		clearScreen();

		while (true) {
			printBoard();
			moveService();
			System.out.print('\n');

			// checking for a winner after each move:
			if (movesCounter >= minimumMovesForWin) {
				EvaluateWinner winnerFound = checkForWinner();

				if ((winnerFound == EvaluateWinner.PLAYER1) || (winnerFound == EvaluateWinner.PLAYER2)) {
					clearScreen();
					printBoard();
					System.err.println(String.format("The winner is Player %d!", (winnerFound.ordinal() + 1)));
					break;
				}

				if (movesCounter == maximumMoves) {
					System.err.println("Game over! Draw.");
					break;
				}
			}

			clearScreen();
		}
	}

	private void pressAnyKeyToContinue() {

		System.err.println("Press any key to start playing...");
		try {
			System.in.read();
		} catch (Exception ex) {
		}
	}

	private void clearScreen() {

		if (this.newLines == null) {
			char c = '\n';
			int length = 25;
			this.newLines = new char[length];
			Arrays.fill(this.newLines, c);
		}

		System.out.print(String.valueOf(newLines));
	}

	private EvaluateWinner checkForWinner() {

		// search by rows:
		for (int i = 0; i < DIMENSION; i++) {
			for (int j = 0; j < DIMENSION - 1; j++) {
				if (board[i][j] != board[i][j + 1]) {
					break;
				}
				// check if it's the last iteration:
				if (j == DIMENSION - 2) { // winner found
					return getWinner(board[i][j]);
				}
			}
		}

		// search by cols:
		for (int i = 0; i < DIMENSION; i++) {
			for (int j = 0; j < DIMENSION - 1; j++) {
				if (board[j][i] != board[j + 1][i]) {
					break;
				}

				if (j == DIMENSION - 2) { // winner found
					return getWinner(board[j][i]);
				}
			}

		}

		// search the main diagonal:
		int rows = 0, cols = 0;
		while (rows != DIMENSION - 1) {
			if (board[rows][cols] != board[rows + 1][cols + 1]) {
				break;
			}

			if (rows == DIMENSION - 2) { // winner found
				return getWinner(board[rows][cols]);
			}

			rows++;
			cols++;
		}

		// search the antidiagonal:
		rows = DIMENSION - 1;
		cols = 0;

		while (rows != 0) {
			if (board[rows][cols] != board[rows - 1][cols + 1]) {
				break;
			}

			if (rows == 1) { // winner found
				return getWinner(board[rows][cols]);
			}

			rows--;
			cols++;
		}

		return EvaluateWinner.NONE; // if no winner is found, NONE will be
									// returned
	}

	private EvaluateWinner getWinner(char winnerSymbol) {
		if (player1Symbol == winnerSymbol) {
			return EvaluateWinner.PLAYER1;
		} else {
			return EvaluateWinner.PLAYER2;
		}
	}

	private void moveService() {
		// this method will decide which player's move it is

		/*
		 * the logic is simple and it's the following: - the player who makes
		 * the first move will move throughout the game when the movesCounter is
		 * an even number - the player who makes the second move will move
		 * throughout the game when the movesCounter is an odd number -> this
		 * way we can have a single method which decides each time which of the
		 * players' move it is
		 */

		if (whoIsFirst == PlayerMove.PLAYER1) {
			if (movesCounter % 2 == 0) {
				makeMove(PlayerMove.PLAYER1);
			} else {
				makeMove(PlayerMove.PLAYER2);
			}
		} else {
			if (movesCounter % 2 == 0) {
				makeMove(PlayerMove.PLAYER2);
			} else {
				makeMove(PlayerMove.PLAYER1);
			}
		}

		movesCounter++;
	}

	private void makeMove(PlayerMove player) {

		int input = -1;

		do {

			System.out.print(String.format("Player %d, please make a move (1-9)> ", (player.ordinal() + 1)));

			try {
				input = scanner.nextInt();
				if (isValidInput(input)) {
					break;
				}
			} catch (Exception ex) {
				System.out.println("Invalid input! Try again.");
			}

		} while (true);

		if (player == PlayerMove.PLAYER1) {
			updateBoard(input, player1Symbol);
		} else {
			updateBoard(input, player2Symbol);
		}
	}

	private boolean isValidInput(int input) {
		if (input < 1 || input > DIMENSION * DIMENSION) {
			System.out.println("Invalid input! Please enter a number from the acceptable range!");
			return false;
		}

		if (!isFieldAvailable(input)) {
			System.out.println("Invalid input! Field is already taken.");
			return false;
		}

		return true;
	}

	private boolean isFieldAvailable(int input) {
		int row = (input - 1) / DIMENSION;
		int col = (input - 1) % DIMENSION;

		return (board[row][col] != 'X') && (board[row][col] != 'O');
	}

	private void updateBoard(int input, char symbol) {
		// with the following formula, we decide where to place the next symbol:
		int row = (input - 1) / DIMENSION;
		int col = (input - 1) % DIMENSION;
		board[row][col] = symbol;
	}

	private void printBoard() {

		String offset = "\t\t\t\t";
		for (int i = 0; i < DIMENSION; i++) {
			System.out.print(offset);
			for (int j = 0; j < DIMENSION; j++) {
				System.out.print(String.format(" %c ", board[i][j]));
				if (j != DIMENSION - 1) {
					System.out.print("|");
				} else {
					System.out.print('\n');
				}
			}

			if (i != DIMENSION - 1) {
				System.out.println(offset + "---|---|---");
			}
		}

		System.out.println("\n\n\n\n\n\n");
	}
}
