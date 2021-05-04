import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseEvent;

public class ConnectFourGame {
    private final ConnectFourGrid grid = new ConnectFourGrid();
    private final JFrame frame = new JFrame("Connect Four");

    private boolean isGameOver = false;
    private boolean isFirstPlayer = true;
    private final int[] turns = new int[2];
    private final int[] wins = new int[2];
    private String display = null;

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

        if (!isGameOver && x > 0 && x < totalWidth && y > 0 && y < totalHeight) {
            grid.colClicked(x / ConnectFourGrid.SQ);
        }
    }

    protected void drawGameInfo(Graphics g) {
        g.setFont(new Font("TimesRoman", Font.BOLD, 20));

        int xVal = ConnectFourGrid.SQ * (ConnectFourGrid.GRID_WIDTH + 1);
        int yVal = ConnectFourGrid.SQ;

        if (display != null) {
            g.setColor(Color.RED);
            g.drawString(display, xVal, yVal);
            yVal += ConnectFourGrid.SQ;
        }

        g.setColor(Color.BLACK);
        g.drawString((isFirstPlayer ? "Black" : "Red") + "'s Turn", xVal, yVal);

        g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        yVal += ConnectFourGrid.SQ;
        g.drawString("Total checkers played: " + (turns[0] + turns[1]), xVal, yVal);

        yVal += ConnectFourGrid.SQ;
        g.drawString("Black wins: " + wins[0] + ", Red wins: " + wins[1], xVal, yVal);
    }

    public void setDisplay(String d) {
        this.display = d;
    }

    public void nextTurn() {
        this.turns[isFirstPlayer ? 0 : 1]++;
        this.isFirstPlayer = !this.isFirstPlayer;
        frame.repaint();
    }

    public void setGameOver(boolean gameOver) {
        this.isGameOver = gameOver;

        if(this.isGameOver) {
            this.display = (isFirstPlayer ? "Black" : "Red") + " Wins!";
            this.wins[isFirstPlayer ? 0 : 1]++;
            frame.repaint();
        }
    }

    public boolean getFirstPlayer() {
        return this.isFirstPlayer;
    }

    public boolean getGameOver() {
        return this.isGameOver;
    }
}
