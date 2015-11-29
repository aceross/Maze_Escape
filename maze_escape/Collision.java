/* The collision class is called by the Player class. Its methods determines 
   whether the players position are in contact with a blocks position.
------------------------------------------------------------------------------*/
import java.awt.Point;

public class Collision {

  public static boolean playerBlock(Point p, Block b) {
    return b.contains(p);
  }

  public static boolean movingBlock(Point p, MovingBlock b) {
    return b.contains(p);
  }

}
