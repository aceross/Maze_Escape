/* The Map class read in a level file which allows it to be drawn. This provides
   the layout for the collision between the player and the blocks. 
------------------------------------------------------------------------------*/

import java.awt.Graphics;
import java.io.*;
import java.util.ArrayList;

public class Map {

  // 'path' is the string to resource
  // 'line' is a line being read in on the level file
  private String path;
  private String line;

  // numer of rows and columns on the tile sheet
  private int rows, columns;

  // the width and height of the tilesheet 
  private int width, height;

  // the array and array
  private Block[][] blocks;
  private ArrayList<MovingBlock> movingBlocks;

  private boolean hasMovingBlock;

  public Map(String loadpath) {
    path = loadpath;
    blocks = new Block[height][width];
    hasMovingBlock = false;
    loadMap();
  }

  public void draw(Graphics g) {
    for (int i = 0; i < blocks.length; ++i) {
      for (int j = 0; j < blocks[0].length; ++j) {
        blocks[i][j].draw(g);
      }
    }
    drawMovingBlocks(g);
  }

  /* if a moving block is read into the level, this is used by the draw method to
     include into the update */
  private void drawMovingBlocks(Graphics g) {
    if (hasMovingBlock) {
      for(int k = 0; k < movingBlocks.size(); ++k) {
        movingBlocks.get(k).draw(g);
      }
    }
  }

  /* Reading in moving blocks is optional. If there are no extra signalling lines
     then the map is read in without the moving block. */
  public void loadMap() {
  	InputStream is = this.getClass().getResourceAsStream(path);
  	BufferedReader reader = new BufferedReader(new InputStreamReader(is));
  	try {
  		width = Integer.parseInt(reader.readLine());
  		height = Integer.parseInt(reader.readLine());
  		blocks = new Block[height][width];
  		for (int y = 0; y < height; ++y) {
        line = reader.readLine();
  		  String[] tokens = line.split("\\s+");
        for (int x = 0; x < width; ++x) {
          blocks[y][x] = new Block(x * Block.blockSize, y * Block.blockSize, 
                                   Integer.parseInt(tokens[x]));
        }
  		}
      //check for moving block mapping, if a line is read, then proceed
      line = reader.readLine();
      line = reader.readLine();
      if (line != null) {
        hasMovingBlock = true;
        int length = Integer.parseInt(line);
        movingBlocks = new ArrayList<MovingBlock>();
        for(int i = 0; i < length; i++) {
          line = reader.readLine();
          String[] tokens = line.split("\\s+");
          movingBlocks.add(new MovingBlock(Integer.parseInt(tokens[0]) * Block.blockSize,
                                           Integer.parseInt(tokens[1]) * Block.blockSize,
                                           Integer.parseInt(tokens[2]),
                                           Integer.parseInt(tokens[3]) * Block.blockSize,
                                           Integer.parseInt(tokens[4]) * Block.blockSize));
        }

      }
  	} catch (NumberFormatException | IOException e) { e.printStackTrace(); }
  }

  // map only needs to update if there are moving blocks to draw new position
  public void tick() {
    if (hasMovingBlock) {
      for(int i = 0; i < movingBlocks.size(); i++) {
        movingBlocks.get(i).tick();
      }
    }
  }


/* Getters
------------------------------------------------------------------------------*/

  public int getHeight()                          { return height; }
  public int getWidth()                           { return width; }
  public Block[][] getBlocks()                    { return blocks; }
  public ArrayList<MovingBlock> getMovingBlocks() { return movingBlocks; }

}
