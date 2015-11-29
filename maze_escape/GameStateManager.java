/* The Game State Manager controls the transitions from menus and between 
   different levels. This is managed by a stack. The class uses the stack's peek
   method in order to look ahead at the incoming methods from the current 
   gamestate.
------------------------------------------------------------------------------*/

import java.awt.Graphics;
import java.util.Stack;

public class GameStateManager {

  public Stack<GameState> states;

  public GameStateManager () {
    states = new Stack<GameState>();
    states.push(new MenuState(this));
  }

  public void tick() {
    states.peek().tick();
  }

  public void draw(Graphics g) {
    states.peek().draw(g);
  }

  public void keyPressed(int k) {
    states.peek().keyPressed(k);
  }

  public void keyReleased(int k) {
    states.peek().keyReleased(k);
  }

}
