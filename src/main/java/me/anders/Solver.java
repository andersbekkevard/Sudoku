package me.anders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Solver {

	public void solveBruteForce(Board board) {
		List<Integer> valuesToTry = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
		List<Integer> unsolvedIndexes = getUnsolvedIndexes(board);

		int progressOnUnsolvedIndexes = 0;

		while (progressOnUnsolvedIndexes < unsolvedIndexes.size()) {
			boolean madeChange = false;

			int workingIndex = unsolvedIndexes.get(progressOnUnsolvedIndexes);
			int[] currentSquare = getIndexFromNumber(workingIndex);

			Collections.shuffle(valuesToTry);

			for (int value : valuesToTry) {
				board.assignNumber(currentSquare[0], currentSquare[1], value);

				if (Main.isLegalBoard(board)) {
					madeChange = true;
					progressOnUnsolvedIndexes++;
					break;
				}
			}

			if (!madeChange) {
				progressOnUnsolvedIndexes--;

				if (progressOnUnsolvedIndexes < 0) {
					throw new IllegalStateException("The board is unsolvable.");
				}

				int previousIndex = unsolvedIndexes.get(progressOnUnsolvedIndexes);
				int[] previousSquare = getIndexFromNumber(previousIndex);
				board.assignNumber(previousSquare[0], previousSquare[1], 0);
			}
		}
	}

	public boolean solveCell(Board board, int cellNumber) {

		if (cellNumber == 81) {
			return true;
		}

		int[] valuesToTry = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		int[] cellIndexes = getIndexFromNumber(cellNumber);
		boolean furtherSolveWorks;

		if (board.checkIfSquareInitial(cellIndexes)) {
			return solveCell(board, cellNumber + 1);
		}

		int rowIndex = cellIndexes[0];
		int columnIndex = cellIndexes[1];

		for (int value : valuesToTry) {
			board.assignNumber(rowIndex, columnIndex, value);
			if (Main.isLegalBoard(board)) {
				// If number works, we continue further
				furtherSolveWorks = solveCell(board, cellNumber + 1);
				if (furtherSolveWorks) {
					return true;
				}
				// If the further solve doesnt work, we proceed through the values
			}
		}

		// If no values work, we have to backtrack
		board.assignNumber(rowIndex, columnIndex, 0);
		return false;
	}

	public boolean solveCellRandomized(Random random, Board board, int cellNumber) {

		if (cellNumber == 81) {
			return true;
		}

		List<Integer> valuesToTry = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
		Collections.shuffle(valuesToTry, random);

		int[] cellIndexes = getIndexFromNumber(cellNumber);
		boolean furtherSolveWorks;

		if (board.checkIfSquareInitial(cellIndexes)) {
			return solveCellRandomized(random, board, cellNumber + 1);
		}

		int rowIndex = cellIndexes[0];
		int columnIndex = cellIndexes[1];

		for (int value : valuesToTry) {
			board.assignNumber(rowIndex, columnIndex, value);
			if (Main.isLegalBoard(board)) {
				// If number works, we continue further
				furtherSolveWorks = solveCellRandomized(random, board, cellNumber + 1);
				if (furtherSolveWorks) {
					return true;
				}
				// If the further solve doesnt work, we proceed through the values
			}
		}

		// If no values work, we have to backtrack
		board.assignNumber(rowIndex, columnIndex, 0);
		return false;
	}

	public List<Integer> getUnsolvedIndexes(Board board) {
		List<Integer> unsolvedIndexes = new ArrayList<>();

		for (int n = 0; n <= 80; n++) {
			if (!board.checkIfSquareInitial(getIndexFromNumber(n)) || board.getSquare(getIndexFromNumber(n)) == 0) {
				unsolvedIndexes.add(n);
			}
		}

		return unsolvedIndexes;
	}

	public static int[] getIndexFromNumber(int number) {
		int row = number / 9;
		int column = number % 9;
		return new int[] { row, column };
	}

	public Board createSolvedBoard() {
		Board board = new Board();
		List<Integer> randomRow = new ArrayList<>();

		// Populating the initial random row
		for (int i = 1; i < 10; i++)
			randomRow.add(i);

		Collections.shuffle(randomRow);

		// Filling out the board with the initial random row at offsets
		for (int rowIndex = 0; rowIndex < 9; rowIndex++) {
			for (int columnIndex = 0; columnIndex < 9; columnIndex++) {
				board.assignNumber(rowIndex, columnIndex, randomRow.get(columnIndex));
				board.setInitialSquare(rowIndex, columnIndex, true);
			}
			if (rowIndex == 2 || rowIndex == 5)
				Collections.rotate(randomRow, 4);
			else
				Collections.rotate(randomRow, 3);
		}

		return board;
	}

	public Board createSolvedBoardWithBacktrack() {
		Random random = new Random();
		Board board = new Board();
		solveCellRandomized(random, board, 0);
		return board;
	}

	public Board createStartingBoard(int filledSquares) {
		int NUMBER_OF_SQUARES = 80;
		Board board = createSolvedBoard();
		Random random = new Random();
		List<Integer> listOfSquareNumbers = new ArrayList<>();

		// Make a list with all the possible squareNumbers of the board
		for (int squareNumber = 0; squareNumber <= NUMBER_OF_SQUARES; squareNumber++) {
			listOfSquareNumbers.add(squareNumber);
		}

		// Eliminate all squares we want to be filled
		for (int i = 0; i < filledSquares; i++) {
			listOfSquareNumbers.remove(random.nextInt(0, listOfSquareNumbers.size()));

		}

		// Clear each of the squares left in listOfSquareNumbers
		int[] squareToClear;
		int rowIndex;
		int columnIndex;

		for (int squareNumber : listOfSquareNumbers) {
			squareToClear = getIndexFromNumber(squareNumber);
			rowIndex = squareToClear[0];
			columnIndex = squareToClear[1];

			board.assignNumber(rowIndex, columnIndex, 0);
			board.setInitialSquare(rowIndex, columnIndex, false);
		}

		return board;
	}

	public static void main(String[] args) {
		System.out.print("\033[2J\033[1;1H");
		Solver solver = new Solver();
		Board board = solver.createStartingBoard(50);
		board.print();
		solver.solveBruteForce(board);
		board.print();
	}
}
