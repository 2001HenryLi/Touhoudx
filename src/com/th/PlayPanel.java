package com.th;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

class PlayPanel extends JPanel implements KeyListener, FocusListener, ActionListener{
    private int backgroundScroll = 0;
    private final double MASTER_SCALE = 1.0;  //scale for the whole thing
    private final int WIDTH = (int)(1280 * 3 / 5 * MASTER_SCALE);
    private final int HEIGHT = (int)(960 * MASTER_SCALE);

    private final int[] INPUT_CODES = {KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_SHIFT, KeyEvent.VK_Z, KeyEvent.VK_X};
    public boolean[] keysDown = new boolean[INPUT_CODES.length];

    private Player p;
    public ArrayList<Bullet> projectiles = new ArrayList<>();

    public PlayPanel(Player p){
        setBackground(new Color(255,255,255));
        setPreferredSize(new Dimension(WIDTH,HEIGHT));
        addKeyListener(this);
        addFocusListener(this);

        this.p = p;
        requestFocus();
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        makeBackground(g);

        g.drawImage(p.sprite, p.getSpriteX(), p.getSpriteY(), p.spriteWidth, p.spriteHeight,this);
        if(keysDown[4]) g.drawImage(p.hitbox, p.getHitboxX(), p.getHitboxY(), p.hitboxWidth, p.hitboxHeight,this);

        backgroundScroll = (backgroundScroll+5) % 1280;
    }

    public void makeBackground(Graphics g) {
        g.drawImage(Toolkit.getDefaultToolkit().getImage("background.png"),0, backgroundScroll,960,1280,this);
        g.drawImage(Toolkit.getDefaultToolkit().getImage("background.png"),0,0,960, backgroundScroll,this);
        g.drawImage(Toolkit.getDefaultToolkit().getImage("Resources/misc/lines.PNG"),0,0,960,1280,this);
    }

    public void update(){
        if(!isFocusOwner()) requestFocus();
        System.out.println("up");
        p.update();
        System.out.println("date");
        for(Bullet b : projectiles) b.update();
        repaint();
    }

    public void keyTyped(KeyEvent e) {}
    public void keyPressed(KeyEvent e){
        int key = e.getKeyCode();
        for(int i = 0; i < keysDown.length; i++) if(key == INPUT_CODES[i]) keysDown[i] = true;
    }
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        for(int i = 0; i < keysDown.length; i++) if(key == INPUT_CODES[i]) keysDown[i] = false;
    }
    public void focusGained(FocusEvent e) {}
    public void focusLost(FocusEvent e) {}
    public void actionPerformed(ActionEvent e){}
}