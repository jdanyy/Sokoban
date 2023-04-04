package Map;

import GameBar.ClickSound;
import GameBar.LevelsPage;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MapLevel extends JFrame implements ActionListener {
    private final int map;
    private final int OFFSET = 100;
    private int value;
    private JButton restart;
    private JButton back;
    private JPanel buttonPanel;
    private Board board;
    private LevelsPage levelsPage;
    public MapLevel(LevelsPage levelsPage, String level, int map){

        this.map = map;
        this.levelsPage = levelsPage;

        this.setTitle("Sokoban");
        this.setLayout(new BorderLayout());

        board = new Board(level);
        buttonPanel = new JPanel();

        this.setSize(board.getBoardWidth() + OFFSET / 2, board.getBoardHeight() + 3 * OFFSET);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setBackground(board.getColor());

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0));
        panel.setBackground(board.getColor());
        panel.setBorder(BorderFactory.createEmptyBorder(5, 10, 0, 0));
        JLabel label = new JLabel("Time: ");
        label.setFont(new Font("Tahoma", Font.PLAIN, 20));
        label.setHorizontalAlignment(SwingConstants.LEFT);

        panel.add(label);

        this.add(panel, BorderLayout.NORTH);
        this.add(board,BorderLayout.CENTER);
        this.add(buttonPanel, BorderLayout.SOUTH);

        value = 0;
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                value += 1;
                label.setText("Time: " + Integer.toString(value));
            }
        });
        timer.start();
        final int BWidth = board.getBoardWidth();
        final int BHeight = board.getBoardHeight();

        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, BWidth / 4, BHeight / 10));
        restart = new JButton("Restart");
        restart.addActionListener(this);
        restart.setPreferredSize(new Dimension(BWidth / 4 + 10, 40));
        restart.setBackground(new Color(0xFFF1973F, true));
        restart.setForeground(new Color(0xFFFFFF));
        restart.setFont(new Font("Brush Script MT", Font.ITALIC, 25));
        restart.setBorder(new BevelBorder(BevelBorder.RAISED));
        restart.setFocusPainted(false);


        back = new JButton("Back");
        back.setPreferredSize(new Dimension(BWidth / 4, 40));
        back.addActionListener(this);
        back.setBackground(new Color(0xFFF1973F, true));
        back.setForeground(new Color(0xFFFFFF));
        back.setFont(new Font("Brush Script MT", Font.ITALIC, 25));
        back.setBorder(new BevelBorder(BevelBorder.RAISED));
        back.setFocusPainted(false);

        buttonPanel.add(back);
        buttonPanel.add(restart);

        buttonPanel.setBackground(board.getColor());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == back){
            new ClickSound();
            this.setVisible(false);
            if (board.getCompleted()){
                levelsPage.actualizeCompletedLevels(map);
            }
            levelsPage.setVisible(true);
            dispose();
        }

       if (e.getSource() == restart){
            new ClickSound();
            board.restartLevel();
        }
    }
}
