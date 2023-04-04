package Map;

import javax.swing.*;
import java.awt.*;

public class Item {

    private final int DIFF = 40;

    private int x;
    private int y;
    private Image image;

    public Item(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public boolean topCollision (Item item){
        return this.getY() - DIFF == item.getY() && this.getX() == item.getX();
    }

    public boolean bottomCollision (Item item){
        return this.getY() + DIFF == item.getY() && this.getX() == item.getX();
    }

    public boolean leftCollision (Item item){
        return this.getX() - DIFF == item.getX() && this.getY() == item.getY();
    }

    public boolean rightCollision (Item item){
        return this.getX() + DIFF == item.getX() && this.getY() == item.getY();
    }
}
