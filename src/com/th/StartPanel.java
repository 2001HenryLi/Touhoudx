package com.th;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

class StartPanel extends JPanel implements KeyListener, FocusListener, ActionListener{
    private BufferedImage pic;
    private BufferedImage side;
    private double ang;

    private volatile boolean gotInput = false;

    public StartPanel(){
        pic = ImageLoader.openImage("Background/titlebackground.png");
        side = ImageLoader.openImage("Background/titleside.png");
        setPreferredSize(new Dimension(ScaleDimentions.WIDTH, ScaleDimentions.HEIGHT));
        addKeyListener(this);
        addFocusListener(this);
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        g.drawImage(pic, 0, 0,  this);
        g.drawImage(side,770,0,520,930,this);

        g.setFont(new Font(Font.SANS_SERIF, Font.TRUETYPE_FONT, 46));
        double ypos = Math.sin(ang * 0.75) * 20 + 550;
        g.drawString("Press Enter to Play", 850, (int)ypos);
    }
    public void update(){
        ang += (Math.PI/90);
        repaint();
    }
    public void waitForInput(){
        while(!gotInput){}
    }
    public void keyTyped(KeyEvent e) {}
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ENTER) gotInput = true;
    }
    public void keyReleased(KeyEvent e) {}
    public void focusGained(FocusEvent e) {}
    public void focusLost(FocusEvent e) {}
    public void actionPerformed(ActionEvent e){}
}
