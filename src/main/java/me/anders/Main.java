package me.anders;

import java.util.HashSet;
import java.util.Scanner;

public class Main {

	// region Gamelogic
	public static boolean isFilled(Board board) {
		for (int i = 0; i < 81; i++) {
			int[] indexes = Solver.getIndexFromNumber(i);
			if (board.getSquare(indexes[0], indexes[1]) == 0) {
				return false;
			}
		}
		return true;
	}

	public static boolean isLegalSequence(int[] sequence) {

		// assuming no number n s.t. !(0 <= n <= 9)
		HashSet<Integer> seen = new HashSet<>();

		for (int element : sequence) {
			if (element == 0)
				continue;
			if (!seen.add(element))
				return false;
		}
		return true;
	}

	public static boolean isLegalBoard(Board board) {
		for (int i = 0; i < 9; i++) {
			if (!isLegalSequence(board.getRow(i)) ||
					!isLegalSequence(board.getColumn(i)) ||
					!isLegalSequence(board.getBox(i)))

				return false;
		}

		return true;
	}
	// endregion

	public static void runGame() {
		System.out.print("\033[2J\033[1;1H");
		final int TOTAL_SQUARES = 80;
		final int SQUARES_TO_SOLVE = 5;

		Scanner scanner = new Scanner(System.in);
		Solver solver = new Solver();
		Board board = solver.createStartingBoard(TOTAL_SQUARES - SQUARES_TO_SOLVE);
		board.print();
		System.out.println("isLegalBoard(board) = " + isLegalBoard(board));
		System.out.println("isFilled(board) = " + isFilled(board));

		while (!isLegalBoard(board) || !isFilled(board)) {

			System.out.print("\033[2J\033[1;1H");
			board.print();
			Move currentMove = Move.createMove(board.getInitialSquares(), scanner);

			board.assignNumber(currentMove);

			// ? DEBUG STATEMENTS:
			// System.out.println("\nBoard became");
			// board.print();
			// System.out.println("isLegalBoard(board) = " + isLegalBoard(board));
			// System.out.println("isFilled(board) = " + isFilled(board));
		}
		scanner.close();
		System.out.println("\nYOU WON!");

	}

	public static void testAllMethods() {
		// Test Board
		Board board = new Board();
		Scanner scanner = new Scanner(System.in);
		board.makeNumberedSquares();
		board.print();
		board.randomize();
		board.print();
		board.makeLegalTestBoard();
		board.print();

		// Test Move
		boolean[][] initialSquares = new boolean[9][9];
		Move move = Move.createMove(initialSquares, scanner);
		System.out.println(move);
		board.assignNumber(move);
		board.print();
		// Test game logic methods
		System.out.println("Is board filled: " + isFilled(board));
		System.out.println("Is board legal: " + isLegalBoard(board));

	}

	public static void main(String[] args) {
		System.out.print("\033[2J\033[1;1H");

		runGame();
	}

}
