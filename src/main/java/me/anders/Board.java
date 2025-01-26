package me.anders;

import java.util.Random;

public class Board {

	private int[][] board = new int[BOARD_SIZE][BOARD_SIZE];
	private boolean[][] initialSquares = new boolean[BOARD_SIZE][BOARD_SIZE];

	static private final String LINE = "--------+-------+--------";
	static private final String THICK_LINE = "=========================";
	static private final int BOARD_SIZE = 9;
	static private final int BOX_SIZE = 3;

	public Board() {
		for (int row = 0; row < BOARD_SIZE; row++) {
			for (int column = 0; column < BOARD_SIZE; column++) {
				board[row][column] = 0;
				initialSquares[row][column] = false;
			}
		}
	}

	// region Core Functionalities
	public int getSquare(int row, int column) {
		return board[row][column];
	}

	public int getSquare(int[] squareIndexes) {
		int row = squareIndexes[0];
		int column = squareIndexes[1];
		return getSquare(row, column);
	}

	public boolean[][] getInitialSquares() {
		return initialSquares;
	}

	public void assignNumber(int row, int column, int number) {
		board[row][column] = number;
	}

	public void assignNumber(Move move) {
		assignNumber(move.getRow(), move.getColumn(), move.getNumber());
	}

	public void setInitialSquare(int row, int column, boolean value) {
		initialSquares[row][column] = value;
	}

	public boolean checkIfSquareInitial(int[] rowAndColumnIndex) {
		return initialSquares[rowAndColumnIndex[0]][rowAndColumnIndex[1]];
	}

	public Board createCopy() {
		Board copyBoard = new Board();
		for (int row = 0; row < BOARD_SIZE; row++) {
			for (int column = 0; column < BOARD_SIZE; column++) {
				copyBoard.board[row][column] = this.board[row][column];
				copyBoard.initialSquares[row][column] = this.initialSquares[row][column];
			}
		}
		return copyBoard;
	}
	// endregion

	// region Validation
	public boolean checkIfMoveLegal(Move move) {
		Board testBoard = this.createCopy();
		testBoard.assignNumber(move);
		return Main.isLegalBoard(testBoard);
	}
	// endregion

	// region Subset/Extraction Methods
	public int[] getRow(int n) {
		return board[n];
	}

	public int[] getColumn(int n) {
		int[] columnValues = new int[BOARD_SIZE];
		for (int row = 0; row < BOARD_SIZE; row++) {
			columnValues[row] = board[row][n];
		}
		return columnValues;
	}

	public int[] getBox(int n) {
		int baseRow = (n / BOX_SIZE) * BOX_SIZE;
		int baseColumn = (n % BOX_SIZE) * BOX_SIZE;
		int[] box = new int[BOARD_SIZE];
		int index = 0;
		for (int rowIncrement = 0; rowIncrement < BOX_SIZE; rowIncrement++) {
			for (int columnIncrement = 0; columnIncrement < BOX_SIZE; columnIncrement++) {
				box[index++] = board[baseRow + rowIncrement][baseColumn + columnIncrement];
			}
		}
		return box;
	}
	// endregion

	// region Printing/Display Methods
	public void print() {
		StringBuilder output = new StringBuilder();
		output.append("    0 1 2   3 4 5   6 7 8\n");
		output.append("  ").append(THICK_LINE).append("\n");

		for (int row = 0; row < BOARD_SIZE; row++) {
			output.append(row).append(" | ");
			for (int column = 0; column < BOARD_SIZE; column++) {
				if (board[row][column] == 0)
					output.append("  ");
				else
					output.append(board[row][column]).append(" ");
				if ((column + 1) % BOX_SIZE == 0) {
					output.append("| ");
				}
			}
			output.append("\n");
			if ((row + 1) % BOX_SIZE == 0 && row != BOARD_SIZE - 1) {
				output.append("  ").append(LINE).append("\n");
			}
		}
		output.append("  ").append(THICK_LINE).append("\n");
		System.out.println(output.toString());
	}

	public void printInitialSquares() {
		StringBuilder builder = new StringBuilder();
		builder.append("[");
		for (int row = 0; row < BOARD_SIZE; row++) {
			builder.append("\n");
			builder.append("[");
			for (int column = 0; column < BOARD_SIZE; column++) {
				builder.append(initialSquares[row][column] ? "T" : "F");
				if (column != BOARD_SIZE - 1)
					builder.append(", ");
			}
			builder.append("]");
		}
		builder.append("\n]");
		System.out.println(builder.toString());
	}
	// endregion

	// region Testing/Debugging Methods
	public void randomize() {
		Random random = new Random();
		for (int row = 0; row < BOARD_SIZE; row++) {
			for (int column = 0; column < BOARD_SIZE; column++) {
				board[row][column] = random.nextInt(1, 10);
			}
		}
	}

	public void makeLegalTestBoard() {
		board = new int[][] {
				{ 5, 3, 4, 6, 7, 8, 9, 1, 2 },
				{ 6, 7, 2, 1, 9, 5, 3, 4, 8 },
				{ 1, 9, 8, 3, 4, 2, 5, 6, 7 },
				{ 8, 5, 9, 7, 6, 1, 4, 2, 3 },
				{ 4, 2, 6, 8, 5, 3, 7, 9, 1 },
				{ 7, 1, 3, 9, 2, 4, 8, 5, 6 },
				{ 9, 6, 1, 5, 3, 7, 2, 8, 4 },
				{ 2, 8, 7, 4, 1, 9, 6, 3, 5 },
				{ 3, 4, 5, 2, 8, 6, 1, 7, 9 }
		};

		for (int row = 0; row < BOARD_SIZE; row++) {
			for (int column = 0; column < BOARD_SIZE; column++) {
				setInitialSquare(row, column, true);
			}
		}
	}

	public void makeNumberedSquares() {
		for (int row = 0; row < BOARD_SIZE; row++) {
			for (int column = 0; column < BOARD_SIZE; column++) {
				assignNumber(row, column, BOARD_SIZE * row + column);
			}
		}
	}
	// endregion

	public static void main(String[] args) {

	}
}