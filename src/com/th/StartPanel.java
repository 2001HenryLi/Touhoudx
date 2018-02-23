package com.th;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class StartPanel extends JPanel implements KeyListener, FocusListener, ActionListener{
    private Image pic;
    private Image side;
    private double ang;

    private volatile boolean gotInput = false;

    public StartPanel(){
        requestFocus();
        setPreferredSize(new Dimension(ScaleDimentions.WIDTH, ScaleDimentions.HEIGHT));
        addKeyListener(this);
        addFocusListener(this);
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        render(g);
        doText(g);
    }

    public void render(Graphics g){
        pic = Toolkit.getDefaultToolkit().getImage("Resources/Background/titlebackground.PNG");
        side = Toolkit.getDefaultToolkit().getImage("Resources/Background/titleside.png");
        g.drawImage(pic, 0, 0,  this);
        g.drawImage(side,770,0,520,930,this);
    }

    public void doText(Graphics g){
        g.setFont(new Font(Font.SANS_SERIF, Font.TRUETYPE_FONT, 46));
        double ypos = Math.sin(ang * 0.75) * 20 + 550;
        g.drawString("Press Enter to Play", 850, (int)ypos);
    }
    public void update(){
        if(!isFocusOwner()) requestFocus();
        ang += (Math.PI/90);
        repaint();
    }
    public void waitForInput(){
        while(!gotInput){requestFocus();}
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
