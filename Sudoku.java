import java.time.Instant;

public class Sudoku {
	private int size;
	private int[][] grid;

	public Sudoku(String sudokuInput) {
		this.size = 9;
		this.grid = new int[getSize()][getSize()];
		create(sudokuInput);
	}
	
	public static void main(String[] args)
	{
		Sudoku s = new Sudoku("000820090500000000308040007100000040006402503000090010093004000004035200000700900");
		s.create("800000000003600000070090200050007000000045700000100030001000068008500010090000400");
	}

	private int getSize() {
		return this.size;
	}

	private void setNumber(int number, int row, int col) {
		this.grid[row][col] = number;
	}

	private int getNumber(int row, int col) {
		return this.grid[row][col];
	}

	public void create(String sudokuInput) {
		char[] inputNumbers = sudokuInput.toCharArray();
		int[] outputNumbers = new int[81];

		for (int i = 0; i < outputNumbers.length; i++) {
			outputNumbers[i] = Character.getNumericValue(inputNumbers[i]);
		}

		putInPlace(outputNumbers);
		display();
	}

	private void putInPlace(int[] numbers) {
		int nextNumber = 0;
		for (int row = 0; row < getSize(); row++) {
			for (int col = 0; col < getSize(); col++) {
				setNumber(numbers[nextNumber], row, col);
				nextNumber++;
			}
		}
	}

	public void display() {
		Instant startTime = Instant.now();
		System.out.println("Initial state:");
		printSudoku();

		if (solve()) {
			System.out.println("Solved state:");
			printSudoku();
		} else {
			System.out.println("Unsolveable sudoku");
		}
		Instant endTime = Instant.now();
		System.out.println("Solved in " + endTime.compareTo(startTime) / 1000000 + " seconds.");
		System.out.println();
	}

	private void printSudoku() {
		for (int row = 0; row < size; row++) {
			System.out.println(" -------------------------------------");

			for (int col = 0; col < size; col++) {
				System.out.print(" | ");
				int number = getNumber(row, col);

				if (number == 0) {
					System.out.print(" ");
				} else {
					System.out.print(number);
				}
			}
			System.out.print(" | ");
			System.out.println();
		}
		System.out.println();
	}

	private boolean solve() {
		for (int row = 0; row < getSize(); row++) {
			for (int col = 0; col < getSize(); col++) {
				if (getNumber(row, col) == 0) {
					return tryValue(row, col);
				}
			}
		}
		return true;
	}

	private boolean tryValue(int row, int col) {
		for (int number = 1; number <= getSize(); number++) {

			if (isPossible(number, row, col)) {
				setNumber(number, row, col);

				if (solve()) {
					return true;
				} else {
					setNumber(0, row, col);
				}
			}
		}
		return false;
	}

	private boolean isPossible(int number, int row, int col) {
		return (!isInRow(number, row) && !isInCol(number, col) && !isInBlock(number, row, col));
	}

	private boolean isInRow(int number, int row) {
		for (int col = 0; col < getSize(); col++) {
			if (getNumber(row, col) == number) {
				return true;
			}
		}
		return false;
	}

	private boolean isInCol(int number, int col) {
		for (int row = 0; row < getSize(); row++) {
			if (getNumber(row, col) == number) {
				return true;
			}
		}
		return false;
	}

	private boolean isInBlock(int number, int row, int col) {
		// make sure you start in the upper left corner of the block
		row = row - (row % 3);
		col = col - (col % 3);

		for (int i = row; i < row + 3; i++) {
			for (int j = col; j < col + 3; j++) {
				if (getNumber(i, j) == number) {
					return true;
				}
			}
		}
		return false;
	}
}
