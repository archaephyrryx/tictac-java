import java.util.Scanner;

public class TicTacToe {
	private int player;
	private int board[][];
	private final char playerOne = 'X';
	private final char playerTwo = 'O';
	private final char unclaimed = '.';
	private boolean done;

	public TicTacToe() {
		player = 1;
		done = false;
		board = new int[3][3];
		for (int i = 0; i < 9; ++i) {
			board[i/3][i%3] = 0;
		}
	}

	public boolean move(int n) {
		if (n >= 0 && n < 9 && !done && board[n/3][n%3] == 0) {
			board[n/3][n%3] = player;
			return true;
		} return false;
	}

	private void update() {
		if (done) {
			return;
		}
		done = (countOpen() == 0 || win() != 0);
		player *= -1;
	}

	private int countOpen() {
		int count = 0;
		for (int i = 0; i < 9; ++i) {
			count += (board[i/3][i%3] == 0) ? 1 : 0;
		}
		return count;
	}

	private int win() {
		int i, j, k;
		for (int xi = 0; xi < 3; ++xi) {
			for (int xf = (xi % 2); xf < 3; xf += 2) {
				for (int yi = 0; yi < 2; ++yi) {
					for (int yf = (yi % 2); yf < 3; yf += 2) {
						if (xi == xf && yi == yf) { continue; }
						i = board[yi][xi]; j = board[(yi+yf)/2][(xi+xf)/2]; k = board[yf][xf];
						if (i * (i + j + k) == 3) { return i; }
					}
				}
			}
		}
		return 0;
	}

	public String toString() {
		String s = "";
		for (int i = 0; i < 3; ++i) {
			s += " ";
			for (int j = 0; j < 3; ++j) {
				s += (board[i][j] == 0) ? unclaimed :
					 (board[i][j] == 1) ? playerOne :
					 (board[i][j] == -1) ? playerTwo :
					 					   "!";
				s += " ";
			}
			s += "\n\n";
		}
		return s;
	}

	private static int getMove(Scanner scanner) {
		while (true) {
			System.out.print("Please enter a move: ");
			String raw_input = scanner.nextLine();
			try {
				int input = Integer.parseInt(raw_input);
				return input;
			} catch (NumberFormatException e) {
				System.err.println("Invalid entry.");
				continue; 
 			}
		}
	}

	private static void noop() {}

	private static void reject() {
		System.out.println("Invalid selection; try again.");
	}

	public static void main ( String[] args ) {

		TicTacToe game = new TicTacToe();

		Scanner scanner = new Scanner( System.in );

		while (!game.done) {
			System.out.print(game);
			boolean moved = false;
			while (true) {
				int m = getMove(scanner);
				moved = game.move(m);
				if (moved) { break; }
				else { reject(); }
			}
			game.update();
		}
		System.out.print(game);
		System.out.println(game.win());
	}

}