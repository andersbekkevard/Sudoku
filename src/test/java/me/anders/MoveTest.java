package me.anders;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MoveTest {

	private boolean[][] initialSquares;

	@BeforeEach
	void setUp() {
		initialSquares = new boolean[9][9];
		initialSquares[0][0] = true; // Mark (0, 0) as an initial square
	}

	@Test
	void testMoveConstructor() {
		Move move = new Move(1, 2, 5);
		assertEquals(1, move.getRow());
		assertEquals(2, move.getColumn());
		assertEquals(5, move.getNumber());
	}

	@Test
	void testCreateMoveValidInput() {
		// Mocking valid input
		String input = "1\n2\n5\n";
		Scanner scanner = new Scanner(input);
		Move move = Move.createMove(initialSquares, scanner);

		assertEquals(1, move.getRow());
		assertEquals(2, move.getColumn());
		assertEquals(5, move.getNumber());
	}

	@Test
	void testCreateMoveRejectInitialSquare() {
		// Mocking input that attempts to modify an initial square first, then valid
		// input
		String input = "0\n0\n1\n2\n5\n";
		Scanner scanner = new Scanner(input);
		Move move = Move.createMove(initialSquares, scanner);

		assertEquals(1, move.getRow());
		assertEquals(2, move.getColumn());
		assertEquals(5, move.getNumber());
	}

	@Test
	void testHandleInputValidInput() {
		String input = "5\n";
		Scanner scanner = new Scanner(input);
		int result = Move.handleInput(scanner, "Enter a number (1-9): ", 1, 9);
		assertEquals(5, result);
	}

	@Test
	void testHandleInputInvalidInput() {
		// Mocking invalid input followed by valid input
		String input = "-1\n10\n5\n";
		Scanner scanner = new Scanner(input);
		int result = Move.handleInput(scanner, "Enter a number (1-9): ", 1, 9);
		assertEquals(5, result);
	}

	@Test
	void testToString() {
		Move move = new Move(3, 4, 7);
		assertEquals("Move: row=3, column=4, number=7", move.toString());
	}
}
