package Map;

import javax.swing.*;
import java.awt.*;

public class Player extends Item{

    private Image image;

    public Player (int x, int y){
        super(x, y);
        ImageIcon icon = new ImageIcon("res/img/mario.jpg");
        image = icon.getImage();
        setImage(image);
    }

    public void move (int x, int y){
        int dx = this.getX() + x;
        int dy = this.getY() + y;
        setX(dx);
        setY(dy);
    }
}
