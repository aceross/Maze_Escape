import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

public class MenuState extends GameState {

  private String[] options = {"Start Game", "Help", "Quit"};
  private int currentSelection = 0;
  private Background background;

  public MenuState(GameStateManager gsm) {
    super(gsm);
    this.music = new SoundLibrary();
    this.music.load("/sfx/cursor.wav", "cursor");
    this.music.load("/sfx/title.wav", "theme");
    // this.music.play("theme");
  }

  public void init() {}

  public void tick() {}

  // Draws the menu background and items as well as highlights the selection
  public void draw(Graphics g) {
    // this.music.play("theme");
    this.background = new Background("/gfx/title.png");
    this.background.draw(g);
    for (int i = 0; i < options.length; ++i) {
      if (i == currentSelection) { g.setColor(Color.GREEN); }
      else { g.setColor(Color.WHITE); }
      g.setFont(new Font("Arial", Font.PLAIN, 45));
      g.drawString(options[i], GamePanel.WIDTH / 2 - 110, 350 + i * 60);
    }
  }

  public void keyPressed(int k) {
    if (k == KeyEvent.VK_DOWN) {
      ++currentSelection;
      this.music.play("cursor"); 
      if (currentSelection >= options.length) {
        currentSelection = 0;
      } 
    } else if (k == KeyEvent.VK_UP) {
        --currentSelection;
        this.music.play("cursor"); 
        if (currentSelection < 0) {
        currentSelection = options.length - 1;
      }
    }
    if (k == KeyEvent.VK_ENTER) {
      if (currentSelection == 0) {
        gsm.states.push(new Level1State(gsm));
      } else if (currentSelection == 1) {
        // this.music.close("theme");
        gsm.states.push(new HelpState(gsm));
      } else if (currentSelection == 2) {
        System.exit(0);
      }
    }
  }

  public void keyReleased(int k) {}

}
