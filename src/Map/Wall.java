package Map;

import javax.swing.*;
import java.awt.*;

public class Wall extends Item{

    private Image image;

    public Wall (int x, int y){
        super(x, y);
        ImageIcon icon  = new ImageIcon("res/img/wall.jpg");
        image = icon.getImage();
        setImage(image);
    }
}
