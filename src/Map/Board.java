/*=========================================================================*/
/* Projekt: Sokoban game
* Name: Jako Daniel
* Id: jdim2141
* Group: 522/1 */
/*=========================================================================*/
package Map;
/*=========================================================================*/
/* Imports */
/*=========================================================================*/
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Board extends JPanel {
    /*=========================================================================*/
    /* The offset represents the distance between the panel and the map
    * the DIFF - the size of the photos, and teh distance between components
    * the constants represents the Collision types */
    /*=========================================================================*/
    private final int OFFSET = 40;
    private final int DIFF = 40;
    private final int L_COLLISION = 1;
    private final int R_COLLISION = 2;
    private final int T_COLLISION = 3;
    private final int B_COLLISION = 4;
    /*=========================================================================*/
    /* The elements from the map */
    /*=========================================================================*/
    private ArrayList<Destination> destinations;
    private ArrayList<Wall> walls;
    private ArrayList<Baggage> baggages;
    private Player player;
    /*=========================================================================*/
    /* The size of the map */
    /*=========================================================================*/
    private int width;
    private int height;
    /*=========================================================================*/
    /* The level represents the map syntax */
    /*=========================================================================*/
    private final String level;
    private boolean Completed;
    private final Color color;
    public Board (String level){
        this.addKeyListener(new KAdapter());
        this.setFocusable(true);

        this.level = level;
        width = 0;
        height = 0;
        color = new Color(0xECB07E);

        initializeBoard();
    }
    public void initializeBoard(){
        /*=========================================================================*/
        /* Inicialize the destinations, walls, and baggages arrays
        * We iterate through the string and we populate the elements array
        * between these steps, we calculate the width and height of the map */
        /*=========================================================================*/
        destinations = new ArrayList<>();
        walls = new ArrayList<>();
        baggages = new ArrayList<>();

        this.requestFocusInWindow();
        Completed = false;

        int x = OFFSET;
        int y = OFFSET;

        Wall wall;
        Baggage baggage;
        Destination destination;

        for (int i = 0; i < level.length(); i++){
            char step = level.charAt(i);

            switch (step) {
                case '\n' -> {
                    y += DIFF;
                    if (this.width < x) {
                        this.width = x;
                    }
                    x = OFFSET;
                }
                case '#' -> {
                    wall = new Wall(x, y);
                    walls.add(wall);
                    x += DIFF;
                }
                case '$' -> {
                    baggage = new Baggage(x, y);
                    baggages.add(baggage);
                    x += DIFF;
                }
                case '.' -> {
                    destination = new Destination(x, y);
                    destinations.add(destination);
                    x += DIFF;
                }
                case '@' -> {
                    player = new Player(x, y);
                    x += DIFF;
                }
                case ' ' -> x += DIFF;
                default -> {
                }
            }
            height = y;
        }
    }

    public int getBoardWidth(){ return this.width; }
    public int getBoardHeight(){ return this.height; }
    public Color getColor() { return this.color; }
    public Boolean getCompleted () { return this.Completed; }
    public void isCompleted(){
        /*=========================================================================*/
        /* We check the coordinates of the baggages and destinations and
        * if all the destinations all occupated we declare the map ready */
        /*=========================================================================*/
        int numberOfBags = baggages.size();
        int finishBags = 0;

        for (Baggage baggage : baggages) {
            for (int j = 0; j < numberOfBags; j++) {
                Destination destination = destinations.get(j);

                if (baggage.getX() == destination.getX() && baggage.getY() == destination.getY()) {
                    finishBags += 1;
                }
            }
        }

        if (finishBags == numberOfBags){
            Completed = true;
            repaint();
        }
    }
    private void buildWorld (Graphics g) {
        /*=========================================================================*/
        /* This function is called when we call de repaint method of the class */
        /* We get an array list, and we put together all the components
        * if we get ready the map, we draw out an image */
        /*=========================================================================*/
        g.setColor(color);
        g.fillRect(0, 0,900, 700);

        ArrayList<Item> world = new ArrayList<>();
        world.addAll(destinations);
        world.addAll(baggages);
        world.addAll(walls);
        world.add(player);

        for (Item item : world) {
            g.drawImage(item.getImage(), item.getX(), item.getY(), this);

            if (Completed) {
                g.fillRect(getBoardWidth() / 2 - OFFSET, getBoardHeight() / 2 - OFFSET, 150, 150);
                try {
                    g.drawImage(ImageIO.read(new File("res/img/completed.png")), getBoardWidth() / 2 - OFFSET, getBoardHeight() / 2 - OFFSET, this);
                } catch (IOException e) {
                    e.printStackTrace();
                    System.exit(-2);
                }
            }
        }
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        buildWorld(g);
    }
    private boolean checkWallCollision (Item item, int type){
        /*=========================================================================*/
        /* We get an item and the type of the probable collision
        * we check all the walls object and we check after the type of collision */
        /*=========================================================================*/
        switch (type){
            case L_COLLISION:
                for (Wall wall : walls) {
                    if (item.leftCollision(wall)) {
                        return true;
                    }
                }
                break;
            case T_COLLISION:
                for (Wall wall : walls) {
                    if (item.topCollision(wall)) {
                        return true;
                    }
                }
                break;
            case R_COLLISION:
                for (Wall wall : walls) {
                    if (item.rightCollision(wall)) {
                        return true;
                    }
                }
                break;
            case B_COLLISION:
                for (Wall wall : walls) {
                    if (item.bottomCollision(wall)) {
                        return true;
                    }
                }
                break;
            default: break;
        }

        return false;
    }
    private boolean checkBaggageCollision (int type){
        /*=========================================================================*/
        /* The baggageCollision check is a little bit complicated
        * first we check if we collide a baggage and after we have the baggage
        * we check if we can collide with baggage a wall
        * if we dont have collision we can move the object */
        /*=========================================================================*/
        switch (type){
            case L_COLLISION:
                for (int i = 0; i < baggages.size(); i++){
                    Baggage baggage = baggages.get(i);
                    if (player.leftCollision(baggage)){

                        if (checkWallCollision(baggage, L_COLLISION)){
                            return true;
                        }

                        for (Baggage baggage1 : baggages) {
                            if (!baggage.equals(baggage1)) {
                                if (baggage.leftCollision(baggage1)) {
                                    return true;
                                }
                            }
                        }
                        baggage.move(-DIFF, 0);
                        isCompleted();
                    }
                }
                break;
            case T_COLLISION:
                for (int i = 0; i < baggages.size(); i++){
                    Baggage baggage = baggages.get(i);
                    if (player.topCollision(baggage)){

                        if (checkWallCollision(baggage, T_COLLISION)){
                            return true;
                        }

                        for (Baggage baggage1 : baggages) {
                            if (!baggage.equals(baggage1)) {
                                if (baggage.topCollision(baggage1)) {
                                    return true;
                                }
                            }
                        }

                        baggage.move(0, -DIFF);
                        isCompleted();
                    }
                }
                break;
            case R_COLLISION:
                for (int i = 0; i < baggages.size(); i++){
                    Baggage baggage = baggages.get(i);
                    if (player.rightCollision(baggage)) {

                        if (checkWallCollision(baggage, R_COLLISION)) {
                            return true;
                        }

                        for (Baggage baggage1 : baggages) {
                            if (!baggage.equals(baggage1)) {
                                if (baggage.rightCollision(baggage1)) {
                                    return true;
                                }
                            }
                        }

                        baggage.move(DIFF, 0);
                        isCompleted();
                    }
                }
                break;
            case B_COLLISION:
                for (int i = 0; i < baggages.size(); i++){
                    Baggage baggage = baggages.get(i);
                    if (player.bottomCollision(baggage)){

                        if (checkWallCollision(baggage,B_COLLISION)){
                            return true;
                        }

                        for (Baggage baggage1 : baggages) {
                            if (!baggage.equals(baggage1)) {
                                if (baggage.bottomCollision(baggage1)) {
                                    return true;
                                }
                            }
                        }
                        baggage.move(0, DIFF);
                        isCompleted();
                    }
                }
                break;
            default: break;
        }
        return false;
    }
    private class KAdapter extends KeyAdapter{
        /*=========================================================================*/
        /* This is a private class which extend the KeyAdapter
        * We check which key was pressed and we check the collisions and if
        * we dont have collision we move the player */
        /*=========================================================================*/
        @Override
        public void keyPressed(KeyEvent e){

            if (Completed){
                return;
            }
            int key = e.getKeyCode();
            switch (key) {
                case KeyEvent.VK_LEFT -> {
                    if (checkWallCollision(player, L_COLLISION)) {
                        return;
                    }
                    if (checkBaggageCollision(L_COLLISION)) {
                        return;
                    }
                    player.move(-DIFF, 0);
                }
                case KeyEvent.VK_UP -> {
                    if (checkWallCollision(player, T_COLLISION)) {
                        return;
                    }
                    if (checkBaggageCollision(T_COLLISION)) {
                        return;
                    }
                    player.move(0, -DIFF);
                }
                case KeyEvent.VK_RIGHT -> {
                    if (checkWallCollision(player, R_COLLISION)) {
                        return;
                    }
                    if (checkBaggageCollision(R_COLLISION)) {
                        return;
                    }
                    player.move(DIFF, 0);
                }
                case KeyEvent.VK_DOWN -> {
                    if (checkWallCollision(player, B_COLLISION)) {
                        return;
                    }
                    if (checkBaggageCollision(B_COLLISION)) {
                        return;
                    }
                    player.move(0, DIFF);
                }
                default -> {
                }
            }
            repaint();
        }
    }
    public void restartLevel(){
        walls.clear();
        baggages.clear();
        destinations.clear();

        initializeBoard();
        repaint();
    }
}
