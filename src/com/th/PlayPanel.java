package com.th;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class PlayPanel extends JPanel implements KeyListener, FocusListener, ActionListener{
    private final double MASTER_SCALE = 1.0;  //scale for the whole thing
    private final int WIDTH = (int)(1280 * 3 / 5 * MASTER_SCALE);
    private final int HEIGHT = (int)(960 * MASTER_SCALE);

    private Player p;

    public PlayPanel(Player p){
        setBackground(new Color(255,255,255));
        requestFocus();
        setPreferredSize(new Dimension(WIDTH,HEIGHT));
        addKeyListener(this);
        addFocusListener(this);

        this.p = p;
        requestFocus();
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(p.sprite, p.x, p.y, p.sprite.getWidth(this), p.sprite.getHeight(this),this);
    }

    public void update(){
        if(!isFocusOwner()) requestFocus();
        //System.out.println(p.x);
    }
    public void keyTyped(KeyEvent e) {}
    public void keyPressed(KeyEvent e){
        int key = e.getKeyCode();
        if(key == KeyEvent.VK_LEFT) p.move(-1,0);
        if(key == KeyEvent.VK_RIGHT) p.move(1,0);
        if(key == KeyEvent.VK_UP) p.move(0,1);
        if(key == KeyEvent.VK_DOWN) p.move(0,-1);
    }
    public void keyReleased(KeyEvent e) {}
    public void focusGained(FocusEvent e) {}
    public void focusLost(FocusEvent e) {}
    public void actionPerformed(ActionEvent e){}
}