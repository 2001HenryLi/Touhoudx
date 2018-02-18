package com.th;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by henry on 2/17/2018.
 */
public class GameOverPanel extends JPanel implements KeyListener, FocusListener, ActionListener {
    private SFX sfx = new SFX();
    private boolean music = false;
    private final double MASTER_SCALE = 1.0;
    private final int WIDTH = (int)(1280 * MASTER_SCALE);
    private final int HEIGHT = (int)(960 * MASTER_SCALE);

    public boolean restart = false;
    private Image img;

    public GameOverPanel(){
        requestFocus();
        setBackground(new Color(255,255,255));
        setPreferredSize(new Dimension(WIDTH,HEIGHT));
        addKeyListener(this);
        addFocusListener(this);
        try {
            img = ImageIO.read(new File("Resources/Background/gameoverscreen.png"));
        } catch(IOException e) {
            System.out.println("failed");
            System.exit(-1);
        }

    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(img,0,0, 1280, 930, this);
    }

    public void update(){
        if(!music){
            sfx.playFX("Resources\\BGM\\Game Over - Super Mario World.wav");
            music = true;
        }
        if(!isFocusOwner()) requestFocus();
        repaint();
    }

    public void actionPerformed(ActionEvent e) {}
    public void focusGained(FocusEvent e) {}
    public void focusLost(FocusEvent e) {}
    public void keyTyped(KeyEvent e) {}
    public void keyPressed(KeyEvent e) {
        if( e.getKeyCode() == KeyEvent.VK_ENTER){
            restart = true;
            music = false;
            sfx.stop();
        }
    }
    public void keyReleased(KeyEvent e) {}
}
