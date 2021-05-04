import java.awt.*;

public class ConnectFourGrid {
	private static ConnectFourGame sharedGame;
	public static final int GRID_WIDTH = 7;
	public static final int GRID_HEIGHT = 6;
	public static final int SQ = 60;

	private final Checker[][] grid = new Checker[GRID_HEIGHT][GRID_WIDTH];

	/**
	 * This method is called when a column is clicked. It processes the click in the following order.  
	 * 1.  The column is  checked to see if it is full.  
	 * If it is full, then the click is ignored.
	 * If not full, then the correct color checker is placed in the lowest 
	 * row possible (biggest row number) in that column.  
	 *
	 * If a checker was placed, then we need to check to see if there is 
	 * four in a row. I recommend checking to see if there is a four
	 * in a row vertically from the latest checker placed, then four in a row horizontally
	 * then four in a row along each diagonal from the latest checker location.  I have 
	 * created one function below called fourVerts which is intended to return true if the latest 
	 * checker is part of four in a row, vertically.  I recommend making 3 other methods, at least.
	 *
	 * If the game is not over, then the turn is switched so that the next click will 
	 * place a checker of the opposite color.  If the game is over, then a message is 
	 * displayed and the game should reset on the next click.
	 * 
	 * @param col The column that the user clicked
	 */
	protected void colClicked(int col) {
		Location loc = lowestEmptyLoc(col);

		if (loc == null) {
			sharedGame.setDisplay("Column " + (col + 1) + " is full!");
			return;
		}

		grid[loc.getRow()][loc.getCol()] = sharedGame.getFirstPlayer() ? new BlackChecker() : new RedChecker();
		sharedGame.setGameOver(checkGameOver(loc));

		if (!sharedGame.getGameOver()) sharedGame.nextTurn();
	}
	
	// prints the board contents in the console and prints who won
	private void displayStateInConsole() {

	}
	/**
	 * This method checks to see if the game is over.  It checks to see if the game has been 
	 * won by either player.  More advanced groups should consider how to determine if the 
	 * game can't be won (tie game)
	 * @param loc This is the latest Location where a Checker was added.
	 * @return This returns true if the game is over.  More advanced groups may change this
	 *  to return an int, where 0 means keep going , 1 means latest player won, 2 means tie game
	 *  Alternatively, you can write a gameTied method that is called AFTER checkGameOver...
	 */
	private boolean checkGameOver(Location loc) {
		return fourVerts(loc);
	}

	/**
	 * Checks to see if there are four checkers in a row that match each other starting 
	 * with loc and going to the South (because loc was the last checker played).  What is the
	 * maximum value of loc.getRow() such that you don't need to even check the places below?
	 * @param loc The Location of the latest Checker added to the Grid
	 * @return true if loc is the top of a four-in-row (all the same color)
	 */
	private boolean fourVerts(Location loc) {
		System.out.println("CHECKING VERTS");
		int row = loc.getRow();
		int col = loc.getCol();

		Checker curr = grid[row][col];

		if (grid.length - row <= 3) return false;

		for (int r = loc.getRow() + 1; r < loc.getRow() + 4; r++) {
			System.out.println(r);
			Checker checker = grid[r][col];
			if (!checker.getColor().equals(curr.getColor())) return false;
		}

		return true;
	}


	/** Finds the lowest empty Location in the specified column or null if the column is full
	 *  The "lowest" column is the column with the largest row (or furthest South)
	 * @param col Column to scan
	 * @return Location that is lowest in the column or null if the column is full
	 */
	private Location lowestEmptyLoc(int col) {
		for (int row = grid.length - 1; row >= 0; row--) {
			Checker checker = grid[row][col];
			if (checker == null) return new Location(row, col);
		}

		return null;
	}

	/**
	 * This method will be called when it is time to start a new game.
	 */
	private void clearBoard() {
		
		
	}

	public String toString(){
		String s = "";

		return s;
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
}
