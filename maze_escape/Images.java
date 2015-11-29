import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Images {

  public static BufferedImage[] imageSet;
  public static BufferedImage[] spriteSet;
  private int numBlocksAcross;

  public Images() {}

  /*  load image into imageset variable
   *  get the number of blocks across
   *  read in images into array */
  public void loadMapGFX(String s) {
    try {
      BufferedImage image = ImageIO.read(getClass().getResourceAsStream(s));
      numBlocksAcross = image.getWidth() / Block.blockSize;
      imageSet = new BufferedImage[numBlocksAcross];
      for (int column = 0; column < numBlocksAcross; ++column) {
        imageSet[column] = image.getSubimage(column * Block.blockSize, 0, 
                                             Block.blockSize, Block.blockSize);
      }
    } catch (IOException e) { e.printStackTrace(); }
  }

  public void loadSprite(String s) {
    try {
      // load image into imageset variable
      // get the number of blocks across
      BufferedImage sprite = ImageIO.read(getClass().getResourceAsStream(s));
      numBlocksAcross = sprite.getWidth() / Player.SPRITESIZE;
      spriteSet = new BufferedImage[numBlocksAcross];

      // read in images into array
      for (int column = 0; column < numBlocksAcross; ++column) {
        spriteSet[column] = sprite.getSubimage(column * Player.SPRITESIZE, 0, 
                                               Player.SPRITESIZE, Player.SPRITESIZE);
      }
    } catch (IOException e) { e.printStackTrace(); }

  }

}
