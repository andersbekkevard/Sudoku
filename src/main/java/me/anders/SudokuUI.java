package me.anders;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SudokuUI extends Application {

	private static final int GRID_SIZE = 9;
	private TextField[][] boardFields = new TextField[GRID_SIZE][GRID_SIZE];
	private Board board;
	private Solver solver;

	@Override
	public void start(Stage primaryStage) {
		// Initialize game logic
		board = new Board();
		solver = new Solver();
		board = solver.createStartingBoard(79);

		// Create UI components
		VBox root = new VBox(10);
		root.setStyle("-fx-padding: 20;");

		GridPane gridPane = createBoardGrid();
		HBox buttonBox = createButtons();

		// Add components to the root
		root.getChildren().addAll(gridPane, buttonBox);

		// Set up the stage
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Sudoku Solver");
		primaryStage.show();
	}

	private GridPane createBoardGrid() {
		GridPane gridPane = new GridPane();
		gridPane.setStyle("-fx-grid-lines-visible: true; -fx-padding: 10;");
		gridPane.setHgap(5);
		gridPane.setVgap(5);

		for (int row = 0; row < GRID_SIZE; row++) {
			for (int col = 0; col < GRID_SIZE; col++) {
				TextField field = new TextField();
				field.setPrefSize(40, 40);
				field.setStyle("-fx-alignment: center;");

				int value = board.getSquare(row, col);
				if (value != 0) {
					field.setText(String.valueOf(value));
					field.setEditable(false);
					field.setStyle("-fx-background-color: lightgray; -fx-font-weight: bold;");
				}

				boardFields[row][col] = field;
				gridPane.add(field, col, row);
			}
		}

		return gridPane;
	}

	private HBox createButtons() {
		HBox buttonBox = new HBox(10);

		Button checkButton = new Button("Check");
		checkButton.setOnAction(e -> checkBoard());

		Button solveButton = new Button("Solve");
		solveButton.setOnAction(e -> solveBoard());

		Button resetButton = new Button("Reset");
		resetButton.setOnAction(e -> resetBoard());

		buttonBox.getChildren().addAll(checkButton, solveButton, resetButton);

		return buttonBox;
	}

	private void checkBoard() {
		try {
			updateBoardFromFields();
			boolean isValid = Main.isLegalBoard(board);

			if (isValid && Main.isFilled(board)) {
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setTitle("Game Over");
				alert.setHeaderText(null);
				alert.setContentText("You won!");

				alert.showAndWait();
				return;
			}

			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Validation Result");
			alert.setHeaderText(isValid ? "Board is valid!" : "Board is invalid!");
			alert.showAndWait();
		} catch (NumberFormatException e) {
			showError("Invalid input. Please enter numbers between 1 and 9.");
		}
	}

	private void solveBoard() {
		if (solver.solveCell(board, 0)) {
			updateFieldsFromBoard();

			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Solved");
			alert.setHeaderText("The Sudoku puzzle has been solved!");
			alert.showAndWait();
		} else {
			showError("This Sudoku puzzle cannot be solved.");
		}
	}

	private void resetBoard() {
		board = solver.createStartingBoard(79);
		updateFieldsFromBoard();
	}

	private void updateBoardFromFields() {
		for (int row = 0; row < GRID_SIZE; row++) {
			for (int col = 0; col < GRID_SIZE; col++) {
				String text = boardFields[row][col].getText();
				int value = text.isEmpty() ? 0 : Integer.parseInt(text);
				board.assignNumber(row, col, value);
			}
		}
	}

	private void updateFieldsFromBoard() {
		for (int row = 0; row < GRID_SIZE; row++) {
			for (int col = 0; col < GRID_SIZE; col++) {
				int value = board.getSquare(row, col);
				boardFields[row][col].setText(value == 0 ? "" : String.valueOf(value));
			}
		}
	}

	private void showError(String message) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText(message);
		alert.showAndWait();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
