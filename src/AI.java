public class AI {
  private static ConnectFourGame sharedGame;
  private static ConnectFourGrid sharedGrid;

  public enum Difficulty {
    EASY,
    HARD,
    MEDIUM
  }
  private final Difficulty difficulty;

  public AI(Difficulty d) {
    this.difficulty = d;
  }

  public void move() {
    switch(this.difficulty) {
      case EASY: {
        moveEasy();
      }
    }
  }

  private void moveEasy() {
    while(true) {
      int col = (int) (Math.random() * ConnectFourGrid.GRID_WIDTH);
      if(sharedGrid.lowestEmptyLoc(col) != null) {
        sharedGrid.colClicked(col);
        break;
      }
    }
  }

  public static void setGame(ConnectFourGame game) {
    sharedGame = game;
  }

  public static void setGrid(ConnectFourGrid grid) {
    sharedGrid = grid;
  }
}
