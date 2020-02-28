import java.awt.*;

public class Enemy extends Sprite {


   public Enemy(Color color, int x, int y, int width, int height, Board board){
       super(color, x, y, width, height, board);
   }

    @Override
    public void paint(Graphics g) {
        g.setColor(this.getColor());
        g.fillRect(x,y,width,height);
    }
}
