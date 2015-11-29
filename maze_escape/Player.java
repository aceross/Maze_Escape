/* The Player class draws the sprite (along with animations) on screen and
   handles the collision with blocks (walls, ground).
------------------------------------------------------------------------------*/

import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.Point;
import java.util.ArrayList;

public class Player {

  // define the sprite size in pixels
  public static final int SPRITESIZE = 32;

  // image ID determines which sprite sheet cell is chosen
  int imageID;

  /* Position variable used to determine the completion of the level.
     Checked by the level object. */
  Block[][] position;

  // movement Boolean values
  private boolean right        = false;
  private boolean left         = false;
  private boolean jumping      = false;
  private boolean falling      = false;
  private boolean topCollision = false;
  private boolean grounded     = true;
  private boolean lockSpace    = false;

  // movement speed
  private double moveSpeed = 1.5;

  // bounds
  private double x, y;
  private int width, height;
  private int xBorder, yBorder;

  // jump speed values
  private double jumpSpeed = 4;
  private double currentJumpSpeed = jumpSpeed;

  // fall speed values
  private double maxFallSpeed = 5;
  private double currentFallSpeed = 0.1;

  // player sound fx library
  private SoundLibrary spriteSound;

  public Player (int width, int height, int startX, int startY) {
    x = startX;
    y = startY;
    this.width  = width;
    this.height = height;
    imageID = 1;
    position = new Block[300][300];
    loadSFX();
  }

  private void loadSFX() {
   spriteSound = new SoundLibrary();
   spriteSound.load("/sfx/jump.wav", "jump");
  }

  public void draw (Graphics g) {
    if (imageID != 0) {
      g.drawImage(Images.spriteSet[imageID-1], (int)x, (int)y, width, height, null);
    }
  }

  public void keyPressed(int k) {
    if (k == KeyEvent.VK_RIGHT) {
      right = true;
      if (imageID != 6) { imageID = 6;}
      else { imageID = 9; }
    }
    if (k == KeyEvent.VK_LEFT) {
      left = true;
      if (imageID != 10) { imageID = 10;}
      else { imageID = 11; }
    }
    if (k == KeyEvent.VK_SPACE && !lockSpace) {
                jumping = true;
                spriteSound.play("jump");
                grounded = false;
                lockSpace = true;
    }
  }

  public void keyReleased(int k) {
    if (k == KeyEvent.VK_RIGHT) { right = false; }
    if (k == KeyEvent.VK_LEFT)  { left = false; }
  }

  public void setXBorder(int width)  { xBorder = width; }
  public void setYBorder(int height) { yBorder = height; }
  public boolean checkFalling()      { return falling; }



/* Tick method:
   This calls the collision class and check whether the
   sprite has come in contact with a block. If it has, the movement boolean is
   set to false and the sprite stops moving in the direction (i.e. left, right
   up/jump, down/fall).
------------------------------------------------------------------------------*/

   public void tick(Block[][] b, ArrayList<MovingBlock> mb) {
    int drawX = (int)x;
    int drawY = (int)y;
    for (int i = 0; i < b.length; ++i) {
      for (int j = 0; j < b[0].length; ++j) {
        if (b[i][j].getID() != 0) {

          // right collision
          if (Collision.playerBlock(new Point(drawX + (width) + (int)GameState.xOffset - 5,
              drawY + (int)GameState.yOffset + 2), b[i][j]) ||
              Collision.playerBlock(new Point(drawX + width + (int)GameState.xOffset - 5,
              drawY + height + (int)GameState.yOffset - 1), b[i][j])) {
              right = false;
                position[i][j] = b[i-1][j];
                // System.out.format("Current position: [%d][%d]", i, j);
          }

          // left collision
          if (Collision.playerBlock(new Point(drawX + (int)GameState.xOffset - 2,
              drawY + (int)GameState.yOffset + 2), b[i][j]) ||
              Collision.playerBlock(new Point(drawX + (int)GameState.xOffset - 2,
              drawY + height + (int)GameState.yOffset - 1), b[i][j])) {
                left = false;
                position[i][j] = b[i][j];
                // System.out.format("Current position: [%d][%d]", i, j);
          }

          // top collision
          if (Collision.playerBlock(new Point(drawX + (int)GameState.xOffset + 8,
              drawY + (int)GameState.yOffset), b[i][j]) ||
              Collision.playerBlock(new Point(drawX + width + (int)GameState.xOffset - 2,
              drawY + (int)GameState.yOffset), b[i][j])) {
                jumping = false;
                falling = true;
                lockSpace = true;
              grounded = true;
          }
          // bottom collision
          if (Collision.playerBlock(new Point(drawX + (int)GameState.xOffset + 8,
              drawY + height + (int)GameState.yOffset + 1), b[i][j]) ||
              Collision.playerBlock(new Point(drawX + width + (int)GameState.xOffset - 2,
              drawY + height + (int)GameState.yOffset + 1), b[i][j])) {
                y = b[i][j].getY() - height - GameState.yOffset;
                falling = false;
                topCollision = true;
                position[i][j] = b[i][j];
                // System.out.format("Current position: [%d][%d]", i, j);
          }
          else {
              if (!topCollision && !jumping) {
                falling = true;
              }
          }
        }
      }
    }

    // collision for the moving block
    for (int i = 0; i < mb.size(); i++) {
      if (mb.get(i).getID() != 0) {
           if (Collision.movingBlock(new Point(drawX + width + (int)GameState.xOffset + 8,
              drawY + (int)GameState.yOffset + 2), mb.get(i)) ||
              Collision.movingBlock(new Point(drawX + width + (int)GameState.xOffset + 5,
              drawY + height + (int)GameState.yOffset - 1),
              mb.get(i))) { right = false; }

          // left collision
          if (Collision.movingBlock(new Point(drawX + (int)GameState.xOffset - 3,
              drawY + (int)GameState.yOffset + 2), mb.get(i)) ||
              Collision.movingBlock(new Point(drawX + (int)GameState.xOffset - 5,
              drawY + height + (int)GameState.yOffset - 1),
              mb.get(i))) { left = false; }


          // top collision
          if (Collision.movingBlock(new Point(drawX + (int)GameState.xOffset + 8,
              drawY + (int)GameState.yOffset), mb.get(i)) ||
              Collision.movingBlock(new Point(drawX + width + (int)GameState.xOffset - 5,
              drawY + (int)GameState.yOffset), mb.get(i))) {
                jumping = false;
                falling = true;
                lockSpace = true;
                grounded = true;
          }


          // bottom collision
          if (Collision.movingBlock(new Point(drawX + (int)GameState.xOffset + 8,
              drawY + height + (int)GameState.yOffset + 1), mb.get(i)) ||
              Collision.movingBlock(new Point(drawX + width + (int)GameState.xOffset - 5,
              drawY + height + (int)GameState.yOffset + 1), mb.get(i))) {
                y = mb.get(i).getY() - height - GameState.yOffset;
                falling = false;
                topCollision = true;
                // ensure that the player can move with the moving block
                GameState.xOffset += mb.get(i).getMove();
          } else { if (!topCollision && !jumping) { falling = true; } }
      }

      topCollision = false;
      if (right) {
        if(x > 700) { GameState.xOffset += moveSpeed; }
        else x += moveSpeed;

      }
      if (left)  {
        if(x < 100) { GameState.xOffset -= moveSpeed; }
        else x -= moveSpeed;

      }
      if (jumping) {
        GameState.yOffset -= currentJumpSpeed;
        currentJumpSpeed -= 0.1;
        if (currentJumpSpeed <= 0) {
          currentJumpSpeed = jumpSpeed;
          jumping = false;
          falling = true;
          lockSpace = true;
        }
      }
      if (falling) {

        GameState.yOffset += currentFallSpeed;
        if (currentFallSpeed < maxFallSpeed) { currentFallSpeed += 0.1; }
      }


        if (!falling && !jumping) { currentFallSpeed = 0.0; lockSpace = false;}

    }
  }

}
