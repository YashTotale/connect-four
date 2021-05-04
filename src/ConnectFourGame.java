import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseEvent;

public class ConnectFourGame {
    private final ConnectFourGrid grid = new ConnectFourGrid();
    private final JFrame frame = new JFrame("Connect Four");

    private boolean isFirstPlayer = true;
    private int[] turns = new int[2];
    private final int[] wins = new int[3];
    private String display = null;

    // Game Over variables
    public enum GameOver {
        BLACK,
        RED,
        TIED
    }
    private GameOver isGameOver;
    private static final int GAME_OVER_WIDTH = 200;
    private static final int GAME_OVER_HEIGHT = 50;
    private int gameOverX;
    private int gameOverY;

    public static void main(String[] args) {
        new ConnectFourGame().start();
    }

    private void start() {
        ConnectFourGrid.setGame(this);
        setUpPanel();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private void setUpPanel() {
        JPanel panel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                grid.draw(g);
                drawGameInfo(g);
            }
        };
        panel.addMouseListener(new MouseInputAdapter(){
            @Override
            public void mousePressed(MouseEvent me){
                clickedAt(me);
            }
        });
        panel.setPreferredSize(new Dimension(1000, 1200));
        panel.setBackground(Color.WHITE);
        frame.add(panel);
        frame.pack();
    }

    protected void clickedAt(MouseEvent me) {
        int x = me.getX();
        int y = me.getY();

        int totalWidth = ConnectFourGrid.SQ * ConnectFourGrid.GRID_WIDTH;
        int totalHeight = ConnectFourGrid.SQ * ConnectFourGrid.GRID_HEIGHT;

        if (isGameOver == null && x > 0 && x < totalWidth && y > 0 && y < totalHeight) {
            grid.colClicked(x / ConnectFourGrid.SQ);
        }
        else if (isGameOver != null && x > gameOverX && x < gameOverX + GAME_OVER_WIDTH && y > gameOverY && y < gameOverY + GAME_OVER_HEIGHT) {
            newGame();
        }
    }

    protected void newGame() {
        this.isGameOver = null;
        this.display = null;
        this.turns = new int[2];
        grid.reset();
        frame.repaint();
    }

    protected void drawGameInfo(Graphics g) {
        Font boldFont = new Font("TimesRoman", Font.BOLD, 20);
        Font plainFont = new Font("TimesRoman", Font.PLAIN, 20);
        g.setFont(boldFont);

        int xVal = ConnectFourGrid.SQ * (ConnectFourGrid.GRID_WIDTH + 1);
        int yVal = ConnectFourGrid.SQ;

        if (display != null) {
            g.setColor(Color.RED);
            g.drawString(display, xVal, yVal);
            yVal += ConnectFourGrid.SQ;
        }

        g.setColor(Color.BLACK);
        g.drawString((isFirstPlayer ? "Black" : "Red") + "'s Turn", xVal, yVal);

        g.setFont(plainFont);
        yVal += ConnectFourGrid.SQ;
        g.drawString("Total checkers played: " + (turns[0] + turns[1]), xVal, yVal);

        yVal += ConnectFourGrid.SQ;
        g.drawString("Black wins: " + wins[0] + ", Red wins: " + wins[1] + ", Ties: " + wins[2], xVal, yVal);

        if(isGameOver != null) {
            String text = "New Game";
            yVal += ConnectFourGrid.SQ;

            g.setFont(boldFont);
            FontMetrics metrics = g.getFontMetrics(boldFont);

            gameOverX = xVal;
            gameOverY = yVal;

            int x = xVal + (GAME_OVER_WIDTH - metrics.stringWidth(text)) / 2;
            int y = yVal + ((GAME_OVER_HEIGHT - metrics.getHeight()) / 2) + metrics.getAscent();

            g.drawRect(xVal, yVal, GAME_OVER_WIDTH, GAME_OVER_HEIGHT);
            g.setColor(Color.GREEN);
            g.fillRect(xVal, yVal, GAME_OVER_WIDTH, GAME_OVER_HEIGHT);
            g.setColor(Color.BLACK);
            g.drawString("New Game", x, y);
        }
    }

    public void setDisplay(String d) {
        this.display = d;
    }

    public void nextTurn() {
        this.display = null;
        this.turns[isFirstPlayer ? 0 : 1]++;
        this.isFirstPlayer = !this.isFirstPlayer;
        frame.repaint();
    }

    public void setGameOver(GameOver gameOver) {
        this.isGameOver = gameOver;

        if(this.isGameOver != null) {
            this.turns[isFirstPlayer ? 0 : 1]++;

            if(this.isGameOver.equals(GameOver.TIED)) {
                this.display = "Tie!";
                this.wins[2]++;
            } else if(this.isGameOver.equals(GameOver.BLACK)) {
                this.display = "Black Wins!";
                this.wins[0]++;
            } else {
                this.display = "Red Wins!";
                this.wins[1]++;
            }

            frame.repaint();
        }
    }

    public boolean getFirstPlayer() {
        return this.isFirstPlayer;
    }

    public GameOver getGameOver() {
        return this.isGameOver;
    }

    public void repaint() {
        frame.repaint();
    }

    public int[] getTurns() {
        return this.turns;
    }
}
