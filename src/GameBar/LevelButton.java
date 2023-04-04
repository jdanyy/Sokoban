package GameBar;

import javax.swing.*;
import java.awt.*;

public class LevelButton extends JButton {
    public LevelButton (String text, String src){
        this.setPreferredSize(new Dimension(80, 80));
        this.setForeground(new Color(0xC5852435, true));
        if (src.equals("")){
            this.setText(text);
        } else {
            ImageIcon icon = new ImageIcon(src);
            this.setIcon(icon);
        }
    }
}
