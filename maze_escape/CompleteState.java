/* The last state of the game. It thanks the user and allows to replay or quit.
------------------------------------------------------------------------------*/

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

public class CompleteState extends GameState {

  private String[] options = {"Play Again", "Quit"};
  private int currentSelection = 0;
  private Background background;

  public CompleteState(GameStateManager gsm) {
    super(gsm);
    this.music = new SoundLibrary();
    this.music.load("/sfx/cursor.wav", "cursor");
  }

  public void init() {}

  public void tick() {}

  public void draw(Graphics g) {
    this.background = new Background("/gfx/end.png");
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
        // SoundLibrary.close("theme");
        System.exit(0);
        gsm.states.push(new HelpState(gsm));
      }
    }
  }

  public void keyReleased(int k) {}

}
