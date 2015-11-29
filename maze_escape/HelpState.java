import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

public class HelpState extends GameState {
  private Background background;

  public HelpState(GameStateManager gsm) {
    super(gsm);
  }

  public void init() {}

  public void tick() {}

  public void draw(Graphics g) {
    background = new Background("/gfx/help.png");
    background.draw(g);
  }

  public void keyPressed(int k) {
    if (k == KeyEvent.VK_ENTER)  { gsm.states.push(new MenuState(gsm)); } 
    if (k == KeyEvent.VK_ESCAPE) { gsm.states.pop(); } 
  }

  public void keyReleased(int k) {}

}
