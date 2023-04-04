package Map;

import javax.swing.*;
import java.awt.*;

public class Destination extends Item{

    private Image image;

    public Destination (int x, int y){
        super(x, y);
        ImageIcon icon = new ImageIcon("res/img/star.png");
        image = icon.getImage();
        setImage(image);
    }
}
