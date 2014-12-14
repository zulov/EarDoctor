import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.*;

/**
 * @author Tomek
 */
public class SoundMaker {
    Clip clip = null;
    
   
    int currentChanel=0;
    
    //
    float volumeLevel=0;
    float freq=0;
   
   
    public  void play(boolean leftear) {
//        //w ten sposób się nie przycina, ale poprzednie dźwięki się nakładają
//        try {
//            clip = AudioSystem.getClip();
//        } catch (LineUnavailableException ex) {
//            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
//        }
        clip.stop();
        clip.close();
        try {
            clip.open(SinusGenerator.generateSinus((float) (Math.pow(10, (volumeLevel-90) / 20)), freq, 2, SinusGenerator.Channels.getChannels(leftear, !leftear)));
        } catch (LineUnavailableException | IOException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
        clip.start();
    }
    public SoundMaker() {
        try {
            clip = AudioSystem.getClip();
        } catch (LineUnavailableException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int getCurrentChanel() {
        return currentChanel;
    }

    public void setCurrentChanel(int currentChanel) {
        this.currentChanel = currentChanel;
    }

    public Clip getClip() {
        return clip;
    }

    public void setClip(Clip clip) {
        this.clip = clip;
    }

    public void setVolumeLevel(float volumeLevel) {
        this.volumeLevel = volumeLevel;
    }

    public float getFreq() {
        return freq;
    }

    public void setFreq(float freq) {
        this.freq = freq;
    }

    
    
}