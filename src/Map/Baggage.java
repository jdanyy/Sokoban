package Map;

import javax.swing.*;
import java.awt.*;

public class Baggage extends Item{
    private Image image;

    public Baggage (int x, int y){
        super(x,y);
        ImageIcon icon = new ImageIcon("res/img/logo1.png");
        image = icon.getImage();
        setImage(image);
    }

    public void move (int x, int y){
        setX(getX() + x);
        setY(getY() + y);
    }
}
