package com.th;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class PlayPanel extends JPanel implements KeyListener, FocusListener, ActionListener{
    private final double MASTER_SCALE = 1.0;  //scale for the whole thing
    private final int WIDTH = (int)(1280 * 3 / 5 * MASTER_SCALE);
    private final int HEIGHT = (int)(960 * MASTER_SCALE);

    public PlayPanel(){
        setBackground(new Color(255,255,255));
        requestFocus();
        setPreferredSize(new Dimension(WIDTH,HEIGHT));
        addKeyListener(this);
        addFocusListener(this);
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        //this prints the title

        //Image border = Toolkit.getDefaultToolkit().getImage("resources\\board\\start_menu\\border.png");
        //g.drawImage(border,-bo,-bo,WIDTH+2*bo,HEIGHT+2*bo,this);
    }
    public void keyTyped(KeyEvent e) {}
    public void keyPressed(KeyEvent e){}
    public void keyReleased(KeyEvent e) {}
    public void focusGained(FocusEvent e) {}
    public void focusLost(FocusEvent e) {}
    public void actionPerformed(ActionEvent e){}
}