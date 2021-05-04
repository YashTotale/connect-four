import java.awt.*;

public class ConnectFourGrid {
	private static ConnectFourGame sharedGame;
	public static final int GRID_WIDTH = 7;
	public static final int GRID_HEIGHT = 6;
	public static final int SQ = 60;

	private Checker[][] grid = new Checker[GRID_HEIGHT][GRID_WIDTH];

	protected void colClicked(int col) {
		Location loc = lowestEmptyLoc(col);

		if (loc == null) {
			sharedGame.setDisplay("Column " + (col + 1) + " is full!");
			sharedGame.repaint();
			return;
		}

		grid[loc.getRow()][loc.getCol()] = sharedGame.getFirstPlayer() ? new BlackChecker() : new RedChecker();
		sharedGame.setGameOver(checkGameOver(loc));

		if (sharedGame.getGameOver() == null) sharedGame.nextTurn();
	}

	/**
	 * This method checks to see if the game is over.  It checks to see if the game has been
	 * won by either player.  More advanced groups should consider how to determine if the
	 * game can't be won (tie game)
	 *
	 * @param loc This is the latest Location where a Checker was added.
	 * @return This returns true if the game is over.  More advanced groups may change this
	 * to return an int, where 0 means keep going , 1 means latest player won, 2 means tie game
	 * Alternatively, you can write a gameTied method that is called AFTER checkGameOver...
	 */
	private ConnectFourGame.GameOver checkGameOver(Location loc) {
		boolean win = fourVerts(loc) || fourHorz(loc);
		boolean tied = checkTied();

		return win ?
				sharedGame.getFirstPlayer()
						? ConnectFourGame.GameOver.BLACK
						: ConnectFourGame.GameOver.RED
				: tied
					? ConnectFourGame.GameOver.TIED
					: null;
	}

	private boolean fourVerts(Location loc) {
		int row = loc.getRow();
		int col = loc.getCol();

		Checker curr = grid[row][col];

		if (grid.length - row <= 3) return false;

		for (int r = loc.getRow() + 1; r < loc.getRow() + 4; r++) {
			Checker checker = grid[r][col];
			if (!checker.getColor().equals(curr.getColor())) return false;
		}

		return true;
	}

	private boolean fourHorz(Location loc) {
		Checker[] row = grid[loc.getRow()];
		Checker curr = row[loc.getCol()];
		boolean goRight = true;
		boolean goLeft = true;
		int i = 1;
		int amount = 1;

		while (goRight || goLeft) {
			if (goRight) {
				int right = loc.getCol() + i;
				if (right >= row.length) goRight = false;
				else {
					Checker r = row[right];
					if (r == null || r.getColor() != curr.getColor()) {
						goRight = false;
					} else {
						amount++;
					}
				}
			}

			if (goLeft) {
				int left = loc.getCol() - i;
				if (left < 0) goLeft = false;
				else {
					Checker l = row[left];
					if (l == null || l.getColor() != curr.getColor()) {
						goLeft = false;
					} else {
						amount++;
					}
				}
			}
			i++;
		}

		return amount >= 4;
	}

	private boolean checkTied() {
		int[] turns = sharedGame.getTurns();
		int totalTurns = turns[0] + turns[1];

		if(totalTurns + 1 >= GRID_WIDTH * GRID_HEIGHT) {
			return true;
		}
		return false;
	}

	private Location lowestEmptyLoc(int col) {
		for (int row = grid.length - 1; row >= 0; row--) {
			Checker checker = grid[row][col];
			if (checker == null) return new Location(row, col);
		}

		return null;
	}

	public void draw(Graphics g) {
		g.setColor(Color.YELLOW);
		g.fillRect(0, 0, SQ * grid[0].length, SQ * grid.length);

		for(int r = 0; r < grid.length; r++) {
			for(int c = 0; c < grid[r].length; c++) {
				int yVal = r * SQ;
				int xVal = c * SQ;
				Checker curr = grid[r][c];

				g.setColor(Color.BLACK);
				g.drawRect(xVal, yVal, SQ, SQ);

				if (curr == null) {
					g.setColor(Color.WHITE);
				} else if (curr.getColor().equals(Checker.CheckerColor.RED)) {
					g.setColor(Color.RED);
				} else {
					g.setColor(Color.BLACK);
				}

				int centerX = xVal + SQ / 2;
				int centerY = yVal + SQ / 2;
				int radius = SQ / 3;

				g.fillOval(centerX - radius, centerY - radius, radius * 2, radius * 2);
			}
		}
	}

	public static void setGame(ConnectFourGame game) {
		sharedGame = game;
	}

	public void reset() {
		this.grid = new Checker[GRID_HEIGHT][GRID_WIDTH];
	}
}
