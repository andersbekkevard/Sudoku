package me.anders;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SolverTest {

	private Solver solver;
	private Board board;

	@BeforeEach
	void setUp() {
		solver = new Solver();
		board = new Board();
	}

	@Test
	void testSolveCell() {
		board.makeLegalTestBoard();
		assertTrue(solver.solveCell(board, 0));
	}

	@Test
	void testSolveCellRandomized() {
		Random random = new Random();
		board.makeLegalTestBoard();
		assertTrue(solver.solveCellRandomized(random, board, 0));
	}

	@Test
	void testCreateSolvedBoard() {
		Board solvedBoard = solver.createSolvedBoard();
		for (int i = 0; i < 9; i++) {
			assertTrue(Main.isLegalSequence(solvedBoard.getRow(i)));
			assertTrue(Main.isLegalSequence(solvedBoard.getColumn(i)));
			assertTrue(Main.isLegalSequence(solvedBoard.getBox(i)));
		}
	}

	@Test
	void testCreateSolvedBoardWithBacktrack() {
		Board solvedBoard = solver.createSolvedBoardWithBacktrack();
		for (int i = 0; i < 9; i++) {
			assertTrue(Main.isLegalSequence(solvedBoard.getRow(i)));
			assertTrue(Main.isLegalSequence(solvedBoard.getColumn(i)));
			assertTrue(Main.isLegalSequence(solvedBoard.getBox(i)));
		}
	}

	@Test
	void testCreateStartingBoard() {
		int filledSquares = 30;
		Board startingBoard = solver.createStartingBoard(filledSquares);
		int nonZeroCount = 0;

		for (int row = 0; row < 9; row++) {
			for (int column = 0; column < 9; column++) {
				if (startingBoard.getSquare(row, column) != 0) {
					nonZeroCount++;
				}
			}
		}

		assertEquals(filledSquares, nonZeroCount);
	}

	@Test
	void testGetUnsolvedIndexes() {
		board.makeLegalTestBoard();
		List<Integer> unsolvedIndexes = solver.getUnsolvedIndexes(board);
		assertTrue(unsolvedIndexes.isEmpty());

		board.assignNumber(0, 0, 0);
		unsolvedIndexes = solver.getUnsolvedIndexes(board);
		assertFalse(unsolvedIndexes.isEmpty());
		assertTrue(unsolvedIndexes.contains(0));
	}

	@Test
	void testGetIndexFromNumber() {
		int[] index = Solver.getIndexFromNumber(10);
		assertArrayEquals(new int[] { 1, 1 }, index);

		index = Solver.getIndexFromNumber(80);
		assertArrayEquals(new int[] { 8, 8 }, index);
	}
}
