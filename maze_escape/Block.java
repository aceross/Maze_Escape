import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.Color;

public class Block extends Rectangle {
  private static final long serialVersionUID = 1L;

  // define the block size
  public static final int blockSize = 32;
  private int id;

  // type of image for collision
  public static final int PASS    = 0;
  public static final int COLLIDE = 1;
  
  public Block(int x, int y, int id) {
    setBounds(x, y, blockSize, blockSize);
    this.id = id;
  }

  public void tick() {}

  public void draw(Graphics g) {
    if (id != 0) {
      g.drawImage(Images.imageSet[id-1], x - (int)GameState.xOffset,
                  y - (int)GameState.yOffset, width, height, null);
    }
  }

/* Get and Set Block IDs
------------------------------------------------------------------------------*/

  public void setID(int id) { this.id = id; }
  public int getID()        { return id; }

}
