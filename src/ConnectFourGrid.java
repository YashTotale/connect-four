import java.awt.*;
import java.util.ArrayList;

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

		grid[loc.getRow()][loc.getCol()] = sharedGame.getIsHumanPlayer() ? new BlackChecker() : new RedChecker();
		sharedGame.setGameOver(checkGameOver(loc));

		if (sharedGame.getGameOver() == null) sharedGame.nextTurn();
	}

	private ConnectFourGame.GameOver checkGameOver(Location loc) {
		boolean win = fourVerts(loc) || fourHorz(loc) || fourDiag(loc);
		boolean tied = checkTied();

		return win ?
				sharedGame.getIsHumanPlayer()
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
		Checker.CheckerColor color = curr.getColor();

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
					if (r == null || !r.getColor().equals(color)) {
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
					if (l == null || !l.getColor().equals(color)) {
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

	private boolean fourDiag(Location loc) {
		int row = loc.getRow();
		int col = loc.getCol();
		Checker curr = grid[row][col];
		Checker.CheckerColor color = curr.getColor();

		for(int x = 0; x < 2; x++) {
			int i = 1;
			int amount = 1;
			boolean goUp = true;
			boolean goDown = true;

			while(goUp || goDown) {
				if(goUp) {
					int r = row - i;
					int c = col + ((x == 0 ? 1 : -1) * i);

					if(!inbounds(r, c)) goUp = false;
					else {
						Checker checker = grid[r][c];
						if(checker == null || !checker.getColor().equals(color)) goUp = false;
						else amount++;
					}
				}

				if(goDown) {
					int r = row + i;
					int c = col - (x == 0 ? 1 : -1) * i;

					if(!inbounds(r, c)) goDown = false;
					else {
						Checker checker = grid[r][c];
						if(checker == null || !checker.getColor().equals(color)) goDown = false;
						else amount++;
					}
				}

				i++;
			}

			if(amount >= 4) return true;
		}

		return false;
	}

	private boolean checkTied() {
		int[] turns = sharedGame.getTurns();
		int totalTurns = turns[0] + turns[1];

		return totalTurns + 1 >= GRID_WIDTH * GRID_HEIGHT;
	}

	public Location lowestEmptyLoc(int col) {
		for (int row = grid.length - 1; row >= 0; row--) {
			Checker checker = grid[row][col];
			if (checker == null) return new Location(row, col);
		}

		return null;
	}

	public void draw(Graphics g) {
    g.setColor(Color.YELLOW);
		g.fillRect(ConnectFourGame.PADDING_LEFT, ConnectFourGame.PADDING_TOP, SQ * grid[0].length, SQ * grid.length);

		for(int r = 0; r < grid.length; r++) {
			for(int c = 0; c < grid[r].length; c++) {
				int yVal = ConnectFourGame.PADDING_TOP + r * SQ;
				int xVal = ConnectFourGame.PADDING_LEFT + c * SQ;
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

	public void reset() {
		this.grid = new Checker[GRID_HEIGHT][GRID_WIDTH];
	}

	public boolean inbounds(int r, int c) {
		return r >= 0 && r < grid.length && c >= 0 && c < grid[r].length;
	}

  public static void setGame(ConnectFourGame game) {
    sharedGame = game;
  }

  public ArrayList<Integer> getAvailableCols() {
	  ArrayList<Integer> available = new ArrayList<>();
	  for(int i = 0; i < grid[0].length; i++) {
	    boolean isAvailable = lowestEmptyLoc(i) != null;
	    if(isAvailable) available.add(i);
    }
	  return available;
  }
}
