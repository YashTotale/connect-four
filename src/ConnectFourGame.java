import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.*;

public class ConnectFourGame {
    private ConnectFourGrid grid = new ConnectFourGrid();
    private JFrame frame = new JFrame("Connect Four");
    private JPanel panel;
    public static void main(String[] args){
        new ConnectFourGame().start();
    }
    private void start() {
        setUpPanel();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }
    private void setUpPanel() {
        panel = new JPanel(){
            @Override
            public void paintComponent(Graphics g){
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
        panel.setBackground(Color.black);
        frame.add(panel);
        frame.pack();


    }
    protected void clickedAt(MouseEvent me) {
        System.out.println("You just clicked at: "+me.getX()+", "+me.getY());
        System.out.println("Do something with that info!!");
    }
    /**
     * Who's turn is it?  How many Checkers played?  Who has won the most games?
     * @param g
     */
    protected void drawGameInfo(Graphics g) {

    }



}
