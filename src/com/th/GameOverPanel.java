package com.th;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

public class GameOverPanel extends JPanel implements KeyListener, FocusListener, ActionListener {
    public boolean restart = false;
    private Image img;

    public GameOverPanel(){
        requestFocus();
        setBackground(new Color(255,255,255));
        setPreferredSize(new Dimension(ScaleDimentions.WIDTH, ScaleDimentions.HEIGHT));
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
        }
    }
    public void keyReleased(KeyEvent e) {}
}
