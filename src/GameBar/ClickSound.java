package GameBar;

import javax.sound.sampled.*;
import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class ClickSound extends JPanel {
    public ClickSound(){
        try {
            AudioInputStream audioInputStream  = AudioSystem.getAudioInputStream(new File("res/sounds/click.wav"));
            Clip clip = AudioSystem.getClip();
            if (!clip.isActive()) {
                clip.open(audioInputStream);
                clip.start();
            }
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            System.out.println("IO error");
        }
    }
}
