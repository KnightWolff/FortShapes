import com.sun.corba.se.impl.protocol.InfoOnlyServantCacheLocalCRDImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Board extends JPanel implements ActionListener {

    Game game;
    Timer timer;
    ArrayList<Sprite> actors;
    ArrayList<Sprite> obstacles;
    ArrayList<Sprite> newFood;
    int paddingNum = 25, maxSize = 25, minSize = 50;
    long nextMoment, nextFood;
    int pSize = 50, currentFood = STATS.getNumFood();


    public Board(Game game){
        this.setBackground(Color.black);
        this.setPreferredSize(new Dimension(500, 800));
        this.game = game;

    }

    public void setup(){
        actors = new ArrayList<>();
        obstacles = new ArrayList<>();
        newFood = new ArrayList<>();

        actors.add(0, new Player(Color.green, getWidth() / 2, getHeight() / 2, pSize, pSize, this, this.game));
        for(int i = 0; i<STATS.getNumFood(); i++){
            actors.add(new Food(Color.orange, (int)(Math.random()*(getWidth()- paddingNum) + paddingNum), (int)(Math.random()*(getHeight()- paddingNum) + paddingNum), 20, 20, this));
        }
        for(int i = 0; i < STATS.getNumEnemies(); i++){
            actors.add(new Enemy(Color.red, (int)(Math.random()*(getWidth()- paddingNum) + paddingNum), (int)(Math.random()*(getHeight()- paddingNum) + paddingNum), 25, 25, this));
        }
        for(int i = 0; i<STATS.getNumObstacles(); i++){
            obstacles.add(new Obstacles(Color.white, (int)(Math.random()*(getWidth()- paddingNum) + paddingNum), (int)(Math.random()*(getHeight()- paddingNum) + paddingNum), (int)(Math.random()*(maxSize- minSize) + minSize), (int)(Math.random()*(maxSize- minSize) + minSize), this));
        }

        timer = new Timer(1000/60, this);
        timer.start();


    }

  public void paintComponent(Graphics g){
       super.paintComponent(g);

       if(Gamestates.isMENU()){
           g.setColor(Color.white);
           g.setFont(new Font("Impact", Font.BOLD, 50));
           printSimpleString("Fort-Shapes", getWidth(), 0, 150, g);
           g.setColor(Color.orange);
           g.setFont(new Font("Arial", Font.ITALIC, 36));
           printSimpleString("Press Enter to Play", getWidth(), 0, 300, g);

       }
       if(Gamestates.isPLAY()) {
           g.setColor(Color.white);
           g.setFont(new Font("Impact", Font.PLAIN,30));
           printSimpleString("Level: " + STATS.getLevel(), 70, 5,paddingNum, g);
           printSimpleString("Num Food: " + currentFood, 70, getWidth() - 120, paddingNum, g);

           for (Sprite thisGuy : actors) {
               thisGuy.paint(g);
           }
           for (Sprite thing : obstacles) {
               thing.paint(g);
           }
           for (Sprite newThing : newFood) {
               newThing.paint(g);
           }
       }
       if(Gamestates.isDEAD()){
           g.setColor(Color.red);
           g.setFont(new Font("Impact", Font.BOLD, 50));
           for(int i = 1; i < getWidth()/10; i++){
               printSimpleString("YOU DEAD BRO", getWidth(), 0, i*50, g);
           }
       }

      if(Gamestates.isWIN()){
          g.setColor(Color.green);
          g.setFont(new Font("Impact", Font.BOLD, 50));
          for(int i = 1; i < getWidth()/10; i++){
              printSimpleString("YOU WIN BRO", getWidth(), 0, i*50, g);
          }
      }

    }

    public void checkCollisions(){
        for(int i = 1; i<actors.size(); i++){

              for(int j = 0; j < obstacles.size(); j ++){
                    if(actors.get(i).collidesWith(obstacles.get(j))){
                        actors.get(i).bounce();
                    }
                }

              if(actors.get(0).collidesWith(actors.get(i))){

                if(actors.get(i) instanceof Enemy){
                    actors.get(0).setRemove();
                    Gamestates.setDEAD(true);
                    Gamestates.setPLAY(false);
                    Gamestates.setMENU(false);
                    game.notClick();
                }
                else if(actors.get(i) instanceof Food){
                    pSize += 20;
                    currentFood +=2;
                    actors.get(i).setRemove();
                    nextFood = System.currentTimeMillis();
                    for(int small = 2; small > 0; small--) {
                        newFood.add(new Food(Color.cyan, (int) (Math.random() * (actors.get(i).getX() - 5) + 5), (int) (Math.random() * (actors.get(i).getY() - 5) + 5), 10, 10, this));
                        }
                    }
                else{
                    actors.get(i).bounce();
                }
            }
        }

        for(int i = actors.size() - 1; i>=0; i--){
            if(actors.get(i).isRemove()){
                actors.remove(i);
            }
        }
    }

    public void checkNewColl(){
        for(int i = 0; i <newFood.size(); i ++){
          if(actors.get(0).collidesWith(newFood.get(i))){
              newFood.remove(i);
              currentFood -= 1;
          }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        nextMoment = System.currentTimeMillis();

            if(game.isEnterPressed() == true){
                Gamestates.setPLAY(true);
                Gamestates.setMENU(false);
                Gamestates.setDEAD(false);
            }

        if ((nextMoment - game.getMoment()) >= 1500){
            checkCollisions();
            if(System.currentTimeMillis() - nextFood >= 500){
                checkNewColl();
            }
        }

        if(Gamestates.isPLAY()) {
                if (game.getIsClick()) {
                    for (Sprite thisGuy : actors) {
                        thisGuy.move();
                    }
                    for (Sprite newThing : newFood) {
                        newThing.move();
                    }
                }

                if (actors.size() == STATS.getNumEnemies() + 1 && obstacles.size() == STATS.getNumObstacles() && newFood.size() == 0) {
                    STATS.setLife(STATS.getLife() - 1);
                    game.notClick();
                    Gamestates.setWIN(true);
                    Gamestates.setPLAY(false);
                }
        }

        repaint();
    }
    private void printSimpleString(String s, int width, int XPos, int YPos, Graphics g){
        int stringLen = (int)g.getFontMetrics().getStringBounds(s, g).getWidth();
        int start = width/2 - stringLen/2;
        g.drawString(s, start + XPos, YPos);
    }



}
