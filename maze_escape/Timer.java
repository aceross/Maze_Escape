/* A timer object is created by a LevelState as a lose condition for the game.
   The class uses the system to keep track of time and draws the remaining time 
   to screen. It has pause functionality. 
------------------------------------------------------------------------------*/

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Timer {
  private int time;
  private int pausedTime;
  private long start;
  private long end;
  private long lastTick;
  private boolean timeOut;
  public boolean timePause;
    
  public Timer(int time) {
    this.time     = time;
    this.start    = System.currentTimeMillis();
    this.end      = System.currentTimeMillis() + (time * 1000);
    this.lastTick = start;
    this.timeOut  = false;
  }
  
  // This method is called by the level object to start the timer for level.
  public void start() {
    if (timeOut == false) {
      if (System.currentTimeMillis() >= (lastTick + 1000)) {
        lastTick += 1000;
        --time;
      }
      if (lastTick == end) { timeOut = true; }
    }
  }

  /* Draw the timer on screen. When it is zero, the letters should be drawn in 
     red */
  public void draw (Graphics g) {
    if (!timeOut) {
      g.setColor(Color.BLACK);
      g.setFont(new Font("Arial", Font.TRUETYPE_FONT, 25));
      g.drawString("Time: " + Integer.toString(time), 20, 20);
    }
    else {
      g.setColor(Color.RED);
      g.setFont(new Font("Arial", Font.TRUETYPE_FONT, 25));
      g.drawString("Time Out!", 20, 20);
    }
  }

  /* Methods relating to pause and time out */
  public void pause()      { timePause = true; }
  public void unpause()    { timePause = false; }
  public boolean timeOut() { return timeOut; }
    
}
