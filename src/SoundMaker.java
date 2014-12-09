
import java.nio.ByteBuffer;
import javax.sound.sampled.*;

/**
 * @author Tomek
 */
public class SoundMaker {

    final int SAMPLING_RATE = 44100;            // Audio sampling rate
    final int SAMPLE_SIZE = 2;                  // Audio sample size in bytes
    float volumeLevel=0;
    SourceDataLine line;
    double fFreq = 440;                         // Frequency of sine wave in hz
    FloatControl volume=null;
    FloatControl balance=null;
    int currentChanel=0;
    
    //Position through the sine wave as a percentage (i.e. 0 to 1 is 0 to 2*PI)
    double fCyclePosition = 0;

    public void play() throws LineUnavailableException {

        //Open up audio output, using 44100hz sampling rate, 16 bit samples, mono, and big 
        // endian byte ordering
        AudioFormat format = new AudioFormat(SAMPLING_RATE, 16, 2, true, true); 
       
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
        if (!AudioSystem.isLineSupported(info)) {
            System.out.println("Line matching " + info + " is not supported.");
            throw new LineUnavailableException();
        }
        line = (SourceDataLine) AudioSystem.getLine(info);
        line.open(format);
        
        volume= (FloatControl) line.getControl(FloatControl.Type.MASTER_GAIN); 
        balance= (FloatControl) line.getControl(FloatControl.Type.BALANCE); 
        balance.setValue(-1);
        volume.setValue(volumeLevel);
        line.start();
        // Make our buffer size match audio system's buffer
        ByteBuffer cBuf = ByteBuffer.allocate(line.getBufferSize());

        int ctSamplesTotal = SAMPLING_RATE * 1;         // Output for roughly 1 seconds

        //On each pass main loop fills the available free space in the audio buffer
        //Main loop creates audio samples for sine wave, runs until we tell the thread to exit
        //Each sample is spaced 1/SAMPLING_RATE apart in time
        while (ctSamplesTotal > 0) {
            double fCycleInc = fFreq / SAMPLING_RATE;  // Fraction of cycle between samples

            cBuf.clear();                            // Discard samples from previous pass

            // Figure out how many samples we can add
            int ctSamplesThisPass = line.available() / SAMPLE_SIZE;
            for (int i = 0; i < ctSamplesThisPass; i++) {
                cBuf.putShort((short) (Short.MAX_VALUE * Math.sin(2 * Math.PI * fCyclePosition)));

                fCyclePosition += fCycleInc;
                if (fCyclePosition > 1) {
                    fCyclePosition -= 1;
                }
            }

            //Write sine samples to the line buffer.  If the audio buffer is full, this will 
            // block until there is room (we never write more samples than buffer will hold)
            line.write(cBuf.array(), 0, cBuf.position());
            ctSamplesTotal -= ctSamplesThisPass;     // Update total number of samples written 

            //Wait until the buffer is at least half empty  before we add more
            while (line.getBufferSize() / 2 < line.available()) {
               // Thread.sleep(1);
            }
        }

        //Done playing the whole waveform, now wait until the queued samples finish 
        //playing, then clean up and exit
        line.drain();
        line.close();
    }

    public SoundMaker() {
    }

    public SourceDataLine getLine() {
        return line;
    }

    public void setLine(SourceDataLine line) {
        this.line = line;
    }

    public double getfFreq() {
        return fFreq;
    }

    public void setfFreq(double fFreq) {
        this.fFreq = fFreq;
    }

    public double getfCyclePosition() {
        return fCyclePosition;
    }

    public void setfCyclePosition(double fCyclePosition) {
        this.fCyclePosition = fCyclePosition;
    }

    public float getVolumeLevel() {
        return volumeLevel;
    }

    public void setVolumeLevel(float volumeLevel) {
        this.volumeLevel = volumeLevel*86/100-80;
    }

    public int getCurrentChanel() {
        return currentChanel;
    }

    public void setCurrentChanel(int currentChanel) {
        this.currentChanel = currentChanel;
    }
    
}
