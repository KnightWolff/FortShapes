import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;

public class Game extends JFrame{

    Board board;

    ImageIcon img = new ImageIcon("download.png");
    int positionX, positionY;
    long moment;
    boolean mouseClick = false;

    public Game(){
        setTitle("FortShape");
        setVisible(true);
        setResizable(false);
        setIconImage(img.getImage());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        board = new Board(this);
        add(board);

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

}
