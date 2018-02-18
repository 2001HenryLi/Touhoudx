package com.th;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Title extends JPanel implements MouseListener, FocusListener, KeyListener{
    public Image pic;

    private double ang;

    public Title(){
        ang = Math.PI;
        setBackground(Color.WHITE);
    }

    public void updategraphic(){ //ryan calls this method
        ang += (Math.PI/90);
        repaint();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        render(g);
        doText(g);
    }

    public void render(Graphics g){
        pic = Toolkit.getDefaultToolkit().getImage("titleresized.PNG");
        double ypos = Math.cos(ang)*70+140;
        g.drawImage(pic, 225, (int)ypos, 500, 140, this);
    }

    public void doText(Graphics g){
        g.setFont(new Font(Font.SANS_SERIF, Font.TRUETYPE_FONT, 30));
        double ypos = Math.sin(ang)*20+700;
        g.drawString("Press Space to Play", 360, (int)ypos);
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyChar() == ' '){
            //start game
        }
    }

    public void mouseEntered(MouseEvent e) {
        requestFocus();
    }

    public void mouseExited(MouseEvent e) {}
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void keyTyped(KeyEvent e) {}
    public void focusGained(FocusEvent e) {}
    public void focusLost(FocusEvent e) {}
    public void keyReleased(KeyEvent e) {}
    public void mouseClicked(MouseEvent e) {}
}