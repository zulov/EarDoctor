/*

 Audiometria
 Version 0.1
 
 Audiometry: hearing test, equal loudness contours (hearing thresholds), 
 sensitivity and acuity.
 
 Warning 1:
 Start with the master sound low!
    Loud sounds can hurt or damage your ears.
    I do not give any warranty nor take any responsibility about any bad usage
        of this software.
 
 
 Warning 2:
 This test does not, in any manner, substitute a doctor!
    This software is for informational purposes only.
    You can use to compare your left and right ears or to equalize your computer.
    The spectral purity and the sound level depend on your sound card and 
        headphones.
    Daily fluctuations in hearing are common.
    If you are worried about your listening, consult an audiologist.

 
 Copyright © 2012 Marcos Assis
 (except when mentioned)
 
 This program is free software: you can redistribute it and/or modify it under 
 the terms of the GNU General Public License as published by the Free Software 
 Foundation, either version 3 of the License, or (at your option) any later
 version. 
 
 This program is distributed in the hope that it will be useful, but WITHOUT ANY
 WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 PARTICULAR PURPOSE.  See the GNU General Public License for more details. 
 
 You should have received a copy of the GNU General Public License along with
 this program.  If not, see http://www.gnu.org/licenses.

*/

import java.io.ByteArrayInputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import org.tritonus.share.sampled.TConversionTool;

/**
 * This class generates a sinus sampled ready for play in a DataLine.
 * 
 * @author marcos
 */
public class SinusGenerator {
        
    public static enum Channels {
        LEFT_ONLY, RIGHT_ONLY, BOTH, NONE;
        
        public static Channels getChannels(boolean leftOn, boolean rightOn) {
            if(leftOn && rightOn)
                return BOTH;
            if(leftOn)
                return LEFT_ONLY;
            if(rightOn)
                return RIGHT_ONLY;
            return NONE;
        }
        
        public boolean isLeftOn() {
            return this.equals(LEFT_ONLY) || this.equals(BOTH);
        }
        public boolean isRightOn() {
            return this.equals(RIGHT_ONLY) || this.equals(BOTH);
        }
    }
    
    public static AudioInputStream generateSinus(float amplitude, float frequency, float duration, Channels channels) {
        //AudioFormat audioFormat = new AudioFormat(44100, 16, 2, true, false);
        return generateSinus(amplitude, frequency, duration, channels, new AudioFormat(44100, 16, 2, true, false));
    }
            
    public static AudioInputStream generateSinus(float amplitude, float frequency, 
            float duration, Channels wichChannels, float sampleRate, int sampleSizeInBits,
            int channels, boolean signed, boolean bigEndian) {
        return generateSinus(amplitude, frequency, duration, wichChannels,  
                new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian));
    }

    public static AudioInputStream generateSinus(float amplitude, float frequency,
            float duration, Channels channels, AudioFormat audioFormat) {

        int frameSize = audioFormat.getFrameSize();
        int sampleSize = audioFormat.getSampleSizeInBits() / 8;
        float frameRate = audioFormat.getFrameRate();
        int nSamples = (int) (frameRate * duration);
        byte[] buffer = new byte[nSamples * frameSize];
        
        int sample;
        float t;
        for (int i = 0; i < buffer.length; i += frameSize) {
            t = (i / frameSize) / frameRate;
            sample = (int) Math.round((Math.pow(2, sampleSize * 8 - 1) - 1) * amplitude * Math.sin(2 * Math.PI * frequency * t));
            
            if(channels.isLeftOn())
                TConversionTool.intToBytes16(sample, buffer, i, audioFormat.isBigEndian());
            if(channels.isRightOn())
                TConversionTool.intToBytes16(sample, buffer, i + sampleSize, audioFormat.isBigEndian());
        }

        try {
            System.out.println(AudioSystem.getSourceDataLine(audioFormat));
        } catch (LineUnavailableException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }

        return new AudioInputStream(new ByteArrayInputStream(buffer), audioFormat, nSamples);
    }
    
}
