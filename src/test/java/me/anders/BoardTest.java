package me.anders;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BoardTest {

	private Board board;

	@BeforeEach
	void setUp() {
		board = new Board();
	}

	@Test
	void testInitialBoardIsEmpty() {
		for (int row = 0; row < 9; row++) {
			for (int column = 0; column < 9; column++) {
				assertEquals(0, board.getSquare(row, column));
				assertFalse(board.getInitialSquares()[row][column]);
			}
		}
	}

	@Test
	void testAssignNumber() {
		board.assignNumber(0, 0, 5);
		assertEquals(5, board.getSquare(0, 0));
	}

	@Test
	void testSetInitialSquare() {
		board.setInitialSquare(0, 0, true);
		assertTrue(board.getInitialSquares()[0][0]);
	}

	@Test
	void testCreateCopy() {
		board.assignNumber(0, 0, 5);
		board.setInitialSquare(0, 0, true);

		Board copy = board.createCopy();
		assertEquals(5, copy.getSquare(0, 0));
		assertTrue(copy.getInitialSquares()[0][0]);
	}

	@Test
	void testGetRow() {
		board.assignNumber(0, 0, 1);
		board.assignNumber(0, 1, 2);
		board.assignNumber(0, 2, 3);

		int[] expectedRow = { 1, 2, 3, 0, 0, 0, 0, 0, 0 };
		assertArrayEquals(expectedRow, board.getRow(0));
	}

	@Test
	void testGetColumn() {
		board.assignNumber(0, 0, 1);
		board.assignNumber(1, 0, 2);
		board.assignNumber(2, 0, 3);

		int[] expectedColumn = { 1, 2, 3, 0, 0, 0, 0, 0, 0 };
		assertArrayEquals(expectedColumn, board.getColumn(0));
	}

	@Test
	void testGetBox() {
		board.assignNumber(0, 0, 1);
		board.assignNumber(0, 1, 2);
		board.assignNumber(1, 0, 3);

		int[] expectedBox = { 1, 2, 0, 3, 0, 0, 0, 0, 0 };
		assertArrayEquals(expectedBox, board.getBox(0));
	}

	@Test
	void testRandomize() {
		board.randomize();
		boolean containsNonZero = false;
		for (int row = 0; row < 9; row++) {
			for (int column = 0; column < 9; column++) {
				if (board.getSquare(row, column) != 0) {
					containsNonZero = true;
					break;
				}
			}
		}
		assertTrue(containsNonZero, "Board should contain non-zero values after randomize()");
	}

	@Test
	void testMakeLegalTestBoard() {
		board.makeLegalTestBoard();
		for (int i = 0; i < 9; i++) {
			assertTrue(Main.isLegalSequence(board.getRow(i)));
			assertTrue(Main.isLegalSequence(board.getColumn(i)));
			assertTrue(Main.isLegalSequence(board.getBox(i)));
		}
	}
}
