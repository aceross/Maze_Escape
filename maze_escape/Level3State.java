import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.Color;
import java.awt.Font;

public class Level3State extends GameState {

  private Player player;
  private Map map;
  private Images images;
  private Timer timer;

  // level boolean values
  public boolean paused;
  private boolean endSequence;
  private boolean gameOver;
  private boolean gameBegin;

  // user messages
  private Background background; 
  private Background pause;
  private Background gameEnd;
  private Background gameStart;

  public Level3State(GameStateManager gsm) { super(gsm); }

  public void init() {
    paused = false;
    endSequence = false;
    gameOver = false;
    gameBegin = true;
    images = new Images();

    // load background and pause images
    background = new Background("/gfx/background.png");
    pause = new Background("/gfx/pause.png");
    gameEnd = new Background("/gfx/gameover.png");
    gameStart = new Background("/gfx/gamebegin.png");

    // load map with images
    map    = new Map ("level3");
    images.loadMapGFX("/gfx/level1.png");

    // load player with sprite image set and level boundaries
    player = new Player(Player.SPRITESIZE, Player.SPRITESIZE, 300, 500);
    player.setXBorder(map.getWidth());
    player.setYBorder(map.getHeight());
    images.loadSprite("/gfx/sprite.png");

    // set timer
    timer = new Timer(60);
    timer.start();

    // set new sound library
    this.music = new SoundLibrary();
    this.music.load("/sfx/main.wav", "bgmusic");
    this.music.load("/sfx/win.wav", "win");
    this.music.load("/sfx/gameover.wav", "gameover");
    this.music.play("bgmusic");

    // define level view offsets
    xOffset = -100;
    yOffset = -150;
  }
  
  // Provides the win condition for the level
  private void endLevel() {
    Block[][] blocks = map.getBlocks();
    for (int i = 0; i < blocks.length; ++i) {
      for (int j = 0; j < blocks[0].length; ++j) {
        if (player.position[i][j] == blocks[5][14]) {
          System.out.println("Finished level 3 and win the game!");
          this.music.close("bgmusic");
          endSequence = true;
        }
      }
    }
  }

  // transition to the next state upon completion
  private void finish() {
    this.music.load("/sfx/win.wav", "win");
    gsm.states.push(new CompleteState(gsm));
  }

  public void tick() {
    if (!timer.timeOut() && !paused) {
      timer.start();
      map.tick();
      player.tick(map.getBlocks(), map.getMovingBlocks());
      endLevel();
    } 
    if(endSequence) { finish(); }
    if (gameOver) { this.music.close("bgmusic"); }
  }

  public void draw(Graphics g) {
    background.draw(g);
    map.draw(g);
    player.draw(g);
    timer.draw(g);
    if (paused) { pause.draw(g); }
    if (timer.timeOut()) { 
      gameOver = true;
      gameEnd.draw(g); 
    }
  }

  public void keyPressed(int k)  { 
    player.keyPressed(k);
    if (k == KeyEvent.VK_ESCAPE) {
      if (!gameOver) {
        if (!paused) paused = true;
        else paused = false;
      }
    } 
    if (k == KeyEvent.VK_Q) {
      this.music.close("bgmusic");
      gsm.states.push(new MenuState(gsm));
    }
    if (k == KeyEvent.VK_M) {
      this.music.close("bgmusic");
    }
    if (gameOver && k == KeyEvent.VK_ENTER) {
      this.music.close("gameover");
      gsm.states.push(new Level3State(gsm));
    }
  }

  public void keyReleased(int k) { player.keyReleased(k); }

}
