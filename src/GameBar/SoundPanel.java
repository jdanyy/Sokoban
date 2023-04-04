/*=========================================================================*/
/*Project: Sokoban game*/
/*Name: Jako Daniel*/
/*Id: jdim2141*/
/*Group: 522/1*/
/* This is the sound panel -> It start the background sound */
/*=========================================================================*/
package GameBar;

import javax.sound.sampled.*;
import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class SoundPanel extends JPanel {
    public SoundPanel() {
        try {
            AudioInputStream audioInputStream  = AudioSystem.getAudioInputStream(new File("res/sounds/soko.wav"));
            Clip clip = AudioSystem.getClip();
            if (!clip.isActive()) {
                clip.open(audioInputStream);
                clip.start();
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            System.out.println("IO error");
        }
    }
}