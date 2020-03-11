import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class Game extends JFrame implements KeyListener {

    Board board;

    int positionX, positionY;
    long moment;
    boolean enterPress, mouseClick = false;

    public Game(){
        setTitle("FortShape");
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        board = new Board(this);
        add(board);
        addKeyListener(this);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                setCursor(getToolkit().createCustomCursor(new BufferedImage(3,3,2), new Point(0,0), "null"));
            }
        });

        addMouseMotionListener(new MouseMotionAdapter(){
            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
                positionX = e.getX();
                positionY = e.getY();
            }
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                mouseClick = true;
                moment = System.currentTimeMillis();
            }
        });

        pack();
        board.setup();
        setLocationRelativeTo(null);
    }

    public int getPositionX(){
        return positionX;
    }

    public int getPositionY(){
        return positionY;
    }

    public boolean getIsClick(){
        return mouseClick;
    }

    public void notClick(){
        mouseClick = false;
    }

    public long getMoment(){
        return moment;
    }

    public static void main(String[] args){
        new Game();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            setEnterPress(true);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            setEnterPress(false);
        }
    }

    public boolean isEnterPressed() {
        return enterPress;
    }

    public void setEnterPress(boolean enterPress) {
        this.enterPress = enterPress;
    }

}
