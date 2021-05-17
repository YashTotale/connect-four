import java.util.ArrayList;

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
      case MEDIUM: {
        moveMedium();
      }
      case HARD: {
        moveHard();
      }
    }
  }

  private void moveEasy() {
    ArrayList<Integer> available = sharedGrid.getAvailableCols();
    int index = (int) (Math.random() * available.size());
    sharedGrid.colClicked(available.get(index));
  }

  private void moveMedium() {

  }

  private void moveHard() {

  }

  public static void setGame(ConnectFourGame game) {
    sharedGame = game;
  }

  public static void setGrid(ConnectFourGrid grid) {
    sharedGrid = grid;
  }
}
