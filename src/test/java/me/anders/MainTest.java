package me.anders;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class MainTest {

	@Test
	void testIsFilled() {
		Board board = new Board();
		assertFalse(Main.isFilled(board));

		board.makeLegalTestBoard();
		assertTrue(Main.isFilled(board));
	}

	@Test
	void testIsLegalSequence() {
		int[] validSequence = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		int[] invalidSequence = { 1, 2, 3, 4, 5, 6, 7, 8, 8 };
		int[] sequenceWithZeros = { 1, 2, 3, 0, 5, 6, 0, 8, 9 };

		assertTrue(Main.isLegalSequence(validSequence));
		assertFalse(Main.isLegalSequence(invalidSequence));
		assertTrue(Main.isLegalSequence(sequenceWithZeros));
	}

	@Test
	void testIsLegalBoard() {
		Board legalBoard = new Board();
		legalBoard.makeLegalTestBoard();
		assertTrue(Main.isLegalBoard(legalBoard));

		Board illegalBoard = new Board();
		illegalBoard.assignNumber(0, 0, 1);
		illegalBoard.assignNumber(0, 1, 1);
		assertFalse(Main.isLegalBoard(illegalBoard));
	}
}
