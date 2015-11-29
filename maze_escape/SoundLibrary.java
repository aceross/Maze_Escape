/* The Sound Library class uses a HashMap in order to store loaded files and 
   call them by name. This is convenient and extensible.
------------------------------------------------------------------------------*/
import java.util.HashMap;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SoundLibrary {
  private HashMap<String, Clip> clips;
  private int gap;

  public SoundLibrary() { init(); }
  
  public void init() {
    clips = new HashMap<String, Clip>();
    gap = 0;
  }
  
  public void load(String s, String n) {
    if(clips.get(n) != null) return;
    Clip clip;
    try {     
      AudioInputStream ais =
        AudioSystem.getAudioInputStream(SoundLibrary.class.getResourceAsStream(s));
      AudioFormat baseFormat = ais.getFormat();
      AudioFormat decodeFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
                                                 baseFormat.getSampleRate(),
                                                 16,
                                                 baseFormat.getChannels(),
                                                 baseFormat.getChannels() * 2,
                                                 baseFormat.getSampleRate(),
                                                 false);
      AudioInputStream dais = AudioSystem.getAudioInputStream(decodeFormat, ais);
      clip = AudioSystem.getClip();
      clip.open(dais);
      clips.put(n, clip);
    }
    catch(Exception e) { e.printStackTrace(); }
  }
  
  /* The public play method allows for other classes to call this method, 
     The private play method does the actual sound playing. This provides some 
     safety of variables. */
  public void play(String s) { play(s, gap); }
  
  private void play(String s, int i) {
    Clip c = clips.get(s);
    if(c == null)     { return; }
    if(c.isRunning()) { c.stop(); }
    c.setFramePosition(i);
    while(!c.isRunning()) c.start();
  }
  
  // stops the sound from continuing
  private void stop(String s) {
    if(clips.get(s) == null) return;
    if(clips.get(s).isRunning()) clips.get(s).stop();
  }
  
  // publically available method to close the sound
  public void close(String s) {
    stop(s);
    clips.get(s).close();
  }
  
}
