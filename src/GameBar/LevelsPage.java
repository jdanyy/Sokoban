/*=========================================================================*/
/* Project: Sokoban game */
/* Name: Jako Daniel*/
/* Id: jdim2141 */
/* Group: 522/1 */
/* This file containt the second Frame, where are the levels buttons placed */
/*=========================================================================*/
package GameBar;

import Map.MapLevel;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LevelsPage extends JFrame implements ActionListener {
    /*=========================================================================*/
    /* These are macros for the different levels in game
    * levelsCompleted -> Boolean array to know which level is completed
    * level1..6 -> buttons of levels
    * back -> iterate back to the main page !! we lose the current page
    * save -> we can save the actual completed pages
    * gameFrame -> the source of the first page */
    /*=========================================================================*/
    private final int L1 = 1;
    private final int L2 = 2;
    private final int L3 = 3;
    private final int L4 = 4;
    private Boolean[] levelsCompleted;
    private LevelButton level1;
    private LevelButton level2;
    private LevelButton level3;
    private LevelButton level4;
    private LevelButton level5;
    private LevelButton level6;
    private JButton back;
    private JButton save;
    private GameFrame gameFrame;

    private Color background1;
    private Color background2;
    /*=========================================================================*/
    /* Default constructor */
    /*=========================================================================*/
    public LevelsPage(GameFrame gameFrame) {
        initPage(gameFrame);
    }
    private void initPage(GameFrame gameFrame) {
        /*=========================================================================*/
        /* The function which init the page */
        /* We put a background image with overriding the paint component */
        /*=========================================================================*/
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("Sokoban");
        this.setBounds(600, 175, 500, 700);
        try {
            BufferedImage image = ImageIO.read(new File("res/img/background_levels.jpg"));
            this.setContentPane(new JPanel() {
                @Override
                public void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.drawImage(image, 0, 0, null);
                }
            });
        } catch (IOException e){
            e.printStackTrace();
            System.exit(-1);
        }
        this.gameFrame = gameFrame;
        this.setLayout(new BorderLayout());
        background1 = new Color(0x9148252A, true);
        background2 = new Color(0x96DAA0);

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
        titlePanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));
        titlePanel.setOpaque(false);
        JLabel title = new JLabel("Levels");
        title.setFont(new Font("Brush Script MT", Font.ITALIC, 55));
        title.setVerticalAlignment(SwingConstants.BOTTOM);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setForeground(new Color(0xF3FD2D));

        titlePanel.add(title);
        /*=========================================================================*/
        /* We use a different panel for the levels button and the other 2 buttons */
        /* We use FlowLayout for the back and save buttons panel */
        /*=========================================================================*/
        JPanel buttons = new JPanel();
        buttons.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 50));
        buttons.setBorder(BorderFactory.createEmptyBorder(45, 45, 5, 45));
        buttons.setOpaque(false);
        /*=========================================================================*/
        /* Inside the FlowLayout manager the meaning of the following parameters are
        * hgap -> we can define the distance between components horizontal
        * vgap -> we can define the distance between components vertical */
        /* We can set up a Border for the panel, specifying the distance with 4 param. */
        /*=========================================================================*/
        JPanel exitPanel;
        exitPanel = new JPanel();
        exitPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 120, 0));
        exitPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        exitPanel.setOpaque(false);

        back = new JButton("Back");
        back.setPreferredSize(new Dimension(100, 40));
        back.setBackground(background1);
        back.setForeground(new Color(0xF3FD2D));
        back.setFont(new Font("Brush Script MT", Font.ITALIC, 25));
        back.setBorder(new BevelBorder(BevelBorder.RAISED));
        back.setFocusPainted(false);
        back.addActionListener(this);
        back.addMouseListener(new CMouseAdapter(back));
        exitPanel.add(back);

        save = new JButton("Save");
        save.setPreferredSize(new Dimension(100, 40));
        save.setBackground(background1);
        save.setForeground(new Color(0xF3FD2D));
        save.setFont(new Font("Brush Script MT", Font.ITALIC, 25));
        save.setBorder(new BevelBorder(BevelBorder.RAISED));
        save.setFocusPainted(false);
        save.addActionListener(this);
        save.addMouseListener(new CMouseAdapter(save));
        exitPanel.add(save);
        /*=========================================================================*/
        /* After setting up the buttons we can add them for the panels
        * we add the panels to our frame */
        /*=========================================================================*/
        this.add(titlePanel, BorderLayout.NORTH);
        this.add(exitPanel, BorderLayout.SOUTH);
        this.add(buttons, BorderLayout.CENTER);

        level1 = new LevelButton("1", "res/img/level1.jpg");
        level1.addActionListener(this);
        buttons.add(level1);

        level2 = new LevelButton("2", "res/img/level2.jpg");
        level2.addActionListener(this);
        buttons.add(level2);

        level3 = new LevelButton("3", "res/img/level3.jpg");
        level3.addActionListener(this);
        buttons.add(level3);

        level4 = new LevelButton("4", "res/img/level4.jpg");
        level4.addActionListener(this);
        buttons.add(level4);

        level5 = new LevelButton("5", "");
        level5.addActionListener(this);
        buttons.add(level5);

        level6 = new LevelButton("6", "");
        level6.addActionListener(this);
        buttons.add(level6);

        levelsCompleted = new Boolean[6];
        Arrays.fill(levelsCompleted, false);
    }

    public LevelsPage(List<String> input, GameFrame gameFrame) {
        final String COMPLETED = "c";
        initPage(gameFrame);

        for (String str : input) {
            String[] line = str.split(" ");
            if (line[1].equals(COMPLETED)) {
                actualizeCompletedLevels(Integer.parseInt(line[0]));
            }
        }
    }

    public void actualizeCompletedLevels(int level) {
        switch (level) {
            case L1 -> {
                level1.setIcon(new ImageIcon("res/img/done.png"));
                levelsCompleted[L1 - 1] = true;
            }
            case L2 -> {
                level2.setIcon(new ImageIcon("res/img/done.png"));
                levelsCompleted[L2 - 1] = true;
            }
            case L3 -> {
                level3.setIcon(new ImageIcon("res/img/done.png"));
                levelsCompleted[L3 - 1] = true;
            }
            case L4 -> {
                level4.setIcon(new ImageIcon("res/img/done.png"));
                levelsCompleted[L4 - 1] = true;
            }
            default -> {
            }
        }
    }

    private String levelPageStanding() {
        Stream<String> stringStream = Stream.of("LevelPage");

        for (int i = 0; i < levelsCompleted.length; i++) {
            if (levelsCompleted[i]) {
                stringStream = Stream.concat(stringStream, Stream.of((i + 1) + " c"));
            } else {
                stringStream = Stream.concat(stringStream, Stream.of((i + 1) + " i"));
            }
        }
        return stringStream.collect(Collectors.joining("\n"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == back) {
            new ClickSound();
            this.setVisible(false);
            gameFrame.setVisible(true);
            dispose();
        }

        if (e.getSource() == save) {
            new ClickSound();
            JFileChooser fc = new JFileChooser();
            fc.setCurrentDirectory(new File(""));
            int ret = fc.showSaveDialog(null);

            if (ret == JFileChooser.APPROVE_OPTION) {
                try {
                    FileWriter fw = new FileWriter(fc.getSelectedFile());
                    fw.write(levelPageStanding());
                    fw.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }

        if (e.getSource() == level1) {
            new ClickSound();
            try (Stream<String> stream = Files.lines(Paths.get("res/maps/level1.txt"))) {
                String level = stream.collect(Collectors.joining("\n"));
                this.setVisible(false);
                new MapLevel(this, level, L1).setVisible(true);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }

        if (e.getSource() == level2){
            new ClickSound();
            try (Stream<String> stream = Files.lines(Paths.get("res/maps/level2.txt"))) {
                String level = stream.collect(Collectors.joining("\n"));
                this.setVisible(false);
                new MapLevel(this, level, L2).setVisible(true);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }

        if (e.getSource() == level3){
            new ClickSound();
            try (Stream<String> stream = Files.lines(Paths.get("res/maps/level3.txt"))) {
                String level = stream.collect(Collectors.joining("\n"));
                this.setVisible(false);
                new MapLevel(this, level, L3).setVisible(true);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }

        if (e.getSource() == level4){
            new ClickSound();
            try (Stream<String> stream = Files.lines(Paths.get("res/maps/level4.txt"))) {
                String level = stream.collect(Collectors.joining("\n"));
                this.setVisible(false);
                new MapLevel(this, level, L4).setVisible(true);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }
    private class CMouseAdapter extends MouseAdapter {
        private final JButton button;
        public CMouseAdapter(JButton button){
            this.button = button;
        }
        @Override
        public void mouseEntered(MouseEvent e) {
            button.setBackground(background2);
        }

        @Override
        public void mouseExited(MouseEvent e){
            button.setBackground(background1);
        }
    }
}