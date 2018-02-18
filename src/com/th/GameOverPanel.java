package com.th;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by henry on 2/17/2018.
 */
public class GameOverPanel extends JPanel implements KeyListener, FocusListener, ActionListener {

    public GameOverPanel()
    {
        repaint();
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.drawImage(Toolkit.getDefaultToolkit().getImage("Resources/Background/GameOverPlaceholder.png"),0,0,this);
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
    public void keyPressed(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {}
}
