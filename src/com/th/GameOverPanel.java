package com.th;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GameOverPanel extends JPanel implements KeyListener, FocusListener, ActionListener {
    public boolean restart = false;
    private BufferedImage img;

    public GameOverPanel(){
        setPreferredSize(new Dimension(ScaleDimentions.WIDTH, ScaleDimentions.HEIGHT));
        addKeyListener(this);
        addFocusListener(this);
        img = ImageLoader.openImage("Background/gameoverscreen.png");
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        g.drawImage(img,0,0, ScaleDimentions.WIDTH, ScaleDimentions.HEIGHT, this);
    }

    public void update(){
        repaint();
    }

    public void actionPerformed(ActionEvent e) {}
    public void focusGained(FocusEvent e) {}
    public void focusLost(FocusEvent e) {}
    public void keyTyped(KeyEvent e) {}
    public void keyPressed(KeyEvent e) {
        if( e.getKeyCode() == KeyEvent.VK_ENTER){
            restart = true;
        }
    }
    public void keyReleased(KeyEvent e) {}
}
