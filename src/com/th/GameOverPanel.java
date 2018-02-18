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

    public boolean restart;
    private Image img = null;

    public GameOverPanel()
    {
        restart = false;
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        try {
            img = ImageIO.read(new File("Resources/Background/GameOverPlaceholder.png"));
        } catch(IOException e) {
            System.out.println("failed");
            System.exit(-1);
        }
        g.drawImage(img,0,0,this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {}
    @Override
    public void focusGained(FocusEvent e) {}
    @Override
    public void focusLost(FocusEvent e) {}
    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyPressed(KeyEvent e)
    {
        if(e.getKeyCode() == KeyEvent.VK_ENTER)
            restart =true;
    }
    @Override
    public void keyReleased(KeyEvent e) {}
}
