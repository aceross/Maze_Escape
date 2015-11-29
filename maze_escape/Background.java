
import java.awt.*;
import java.awt.image.*;
import javax.imageio.ImageIO;

public class Background {
  
  private BufferedImage image;
  
  private double x;
  private double y;
  private double dx;
  private double dy;
  
  private double moveScale;
  
  public Background(String s) {
    
    try {
      image = ImageIO.read(getClass().getResourceAsStream(s));
      moveScale = 0.1;
    } catch(Exception e) { e.printStackTrace(); }
    
  }

  public void draw(Graphics g) { 
    g.drawImage(image, (int)x, (int)y, null);
    if(x < 0) { g.drawImage(image, (int)x + GamePanel.WIDTH, (int)y, null); }
    if(x > 0) { g.drawImage(image, (int)x - GamePanel.WIDTH, (int)y, null); }
  }
  
}
