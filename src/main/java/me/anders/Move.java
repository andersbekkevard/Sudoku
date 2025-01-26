package me.anders;

import java.util.Scanner;

public class Move {
	private final int row;
	private final int column;
	private final int number;

	public Move(int row, int column, int number) {
		this.row = row;
		this.column = column;
		this.number = number;
	}

	// region Getters and Setters
	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}

	public int getNumber() {
		return number;
	}
	// endregion

	// region Factory Methods
	public static Move createMove(boolean[][] initialSquares, Scanner scanner) {
		int row, column, number;

		while (true) {
			System.out.println("Make Move:");
			row = handleInput(scanner, "Enter row (0-8): ", 0, 8);
			column = handleInput(scanner, "Enter column (0-8): ", 0, 8);

			if (!initialSquares[row][column]) {
				break;
			}
			System.out.println("That is one of the initial squares. Pick another one");
		}
		number = handleInput(scanner, "Enter number (1-9): ", 1, 9);

		return new Move(row, column, number);
	}

	public static int handleInput(Scanner scanner, String prompt, int lowerBound, int upperBound) {
		while (true) {
			System.out.print(prompt);
			try {
				int userInput = scanner.nextInt();
				if (userInput < lowerBound || userInput > upperBound) {
					System.out.println("Input out of bounds. Please enter an integer between " + lowerBound + " and "
							+ upperBound + ".");
				} else {
					return userInput;
				}
			} catch (Exception e) {
				System.out.println("Invalid input. Please enter an integer.");
				scanner.nextLine();
			}
		}
	}
	// endregion

	@Override
	public String toString() {
		return String.format("Move: row=%d, column=%d, number=%d", row, column, number);
	}

	public static void main(String[] args) {
		System.out.println("Hello Solver");
	}

}