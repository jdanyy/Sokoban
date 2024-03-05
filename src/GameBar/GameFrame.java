/*=========================================================================*/
/* Project: Sokoban game */
/* Name: Jako Daniel*/
/* Id: jdim2141 */
/* Group: 522/1 */
/* This file contain the main page of the game */
/*=========================================================================*/
package GameBar;

/*=========================================================================*/
/*Imports*/
/*=========================================================================*/
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
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GameFrame extends JFrame implements ActionListener {
    private final JButton gameButton;
    private final JButton loadButton;
    private final JButton exit;
    private final Color back1;
    private final Color back2;
    public GameFrame(){

        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setBounds(600, 175, 500, 700);
        this.setTitle("Sokoban");
        /*=========================================================================*/
        /* Adding background music -> with SoundPanel */
        /* Opaque is set false to not disturb the layout of front page */
        /*=========================================================================*/
        SoundPanel soundPanel = new SoundPanel();
        soundPanel.setOpaque(false);
        this.add(soundPanel);
        /*=========================================================================*/
        /* Background image reading with error checking */
        /*=========================================================================*/
        try {
            BufferedImage image = ImageIO.read(new File("res/img/background.png"));
            this.setContentPane(new JLabel(new ImageIcon(image)));
        } catch (IOException e){
            e.printStackTrace();
            System.exit(-1);
        }
        /*=========================================================================*/
        /* I used GridBagLayout to put in wanted positions the buttons */
        /* We need to introduce a GridBagConstraints object, which store the
        * parameters for every object */
        /*=========================================================================*/
        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        /*=========================================================================*/
        /* Design the title of the main page */
        /* I get an icon with error check, after we change the font and the style of
        * the JLabel */
        /* I set up the GridBagConstraints to set a position for the JLabel */
        /*=========================================================================*/
        try {
            BufferedImage image = ImageIO.read(new File("res/img/icon.png"));
            ImageIcon i = new ImageIcon(image);
            JLabel title = new JLabel("Sokoban", i, SwingConstants.LEFT);
            title.setFont(new Font("Arial", Font.BOLD, 40));
            title.setVerticalAlignment(SwingConstants.CENTER);
            title.setHorizontalAlignment(SwingConstants.CENTER);
            c.insets = new Insets(5, 5, 95, 5);
            c.ipadx = 40;
            c.ipady = 40;
            c.gridx = 0;
            c.gridy = 0;
            c.gridwidth = 4;
            c.fill = GridBagConstraints.HORIZONTAL;
            this.add(title, c);

        } catch (IOException e){
            e.printStackTrace();
            System.exit(-1);
        }
        /*=========================================================================*/
        /* We have 3 buttons, each under other */
        /* I set up the Constraints for each button separately */
        /* ipadx, ipady -> we can set the size of component */
        /* gridx, gridy -> we can set in which grid to put the component */
        /* gridwidth, gridheight -> how many grids to fill the component */
        /*=========================================================================*/
        back1 = new Color(0xFFBE2934, true);
        back2  = new Color(0xFFFFFF);
        c.insets = new Insets(5, 5, 5, 5);
        gameButton = new JButton("New Game");
        gameButton.addActionListener(this);
        gameButton.addMouseListener(new CMouseAdapter(gameButton));
        gameButton.setBackground(back1);
        gameButton.setForeground(new Color(0x18184F));
        gameButton.setFont(new Font("Brush Script MT", Font.ITALIC, 25));
        gameButton.setBorder(new BevelBorder(BevelBorder.RAISED));
        gameButton.setFocusPainted(false);
        c.ipadx = 40;
        c.ipady = 40;
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 4;
        c.fill = GridBagConstraints.HORIZONTAL;
        this.add(gameButton, c);

        loadButton = new JButton("Load Game");
        loadButton.setBackground(back1);
        loadButton.setForeground(new Color(0x18184F));
        loadButton.setFont(new Font("Brush Script MT", Font.ITALIC, 25));
        loadButton.setBorder(new BevelBorder(BevelBorder.RAISED));
        loadButton.setFocusPainted(false);
        loadButton.addActionListener(this);
        loadButton.addMouseListener(new CMouseAdapter(loadButton));
        c.ipadx = 40;
        c.ipady = 40;
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 4;
        c.fill = GridBagConstraints.HORIZONTAL;
        this.add(loadButton, c);

        exit = new JButton("Exit");
        exit.setBackground(back1);
        exit.setForeground(new Color(0x18184F));
        exit.setFont(new Font("Brush Script MT", Font.ITALIC, 25));
        exit.setBorder(new BevelBorder(BevelBorder.RAISED));
        exit.setFocusPainted(false);
        exit.addActionListener(this);
        exit.addMouseListener(new CMouseAdapter(exit));
        c.ipadx = 40;
        c.ipady = 40;
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 4;
        c.fill = GridBagConstraints.HORIZONTAL;
        this.add(exit, c);
    }
    /*=========================================================================*/
    /* Action Listeners for buttons
    * exit -> close the program
    * New Game -> start a LevelsPage JFrame
    * Load Game -> open a FileChooser and you can upload a game from a file */
    /*=========================================================================*/
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == exit){
            new ClickSound();
            System.exit(0);
        }

        if (e.getSource() == gameButton){
            new ClickSound();
            this.setVisible(false);
            new LevelsPage(this).setVisible(true);
        }

        if (e.getSource() == loadButton){
            new ClickSound();
            JFileChooser jfc = new JFileChooser();
            jfc.setCurrentDirectory(new File(""));
            jfc.setApproveButtonText("Load");
            int ret = jfc.showSaveDialog(null);

            if (ret == JFileChooser.APPROVE_OPTION){
                try(Stream<String> lines = Files.lines(Paths.get(jfc.getSelectedFile().toURI()))){
                    List<String> result = lines.skip(1)
                            .collect(Collectors.toList());

                    this.setVisible(false);
                    new LevelsPage(result, this).setVisible(true);
                }catch(IOException ex){
                    ex.printStackTrace();
                }
            }
        }
    }
    /*=========================================================================*/
    /* MousAdapter -> change the color of the button when the cursor is inside
    * his area */
    /*=========================================================================*/
    private class CMouseAdapter extends MouseAdapter{
        private JButton button;
        public CMouseAdapter(JButton button){
            this.button = button;
        }
        @Override
        public void mouseEntered(MouseEvent e) {
            button.setBackground(back2);
        }

        @Override
        public void mouseExited(MouseEvent e){
            button.setBackground(back1);
        }
    }

}


