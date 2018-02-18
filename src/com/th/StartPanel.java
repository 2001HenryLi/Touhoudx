package com.th;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class StartPanel extends JPanel implements KeyListener, FocusListener, ActionListener{

    public Image pic;
    public Image side;
    private double ang;
    private BGMusic bgm = new BGMusic();
    private final double MASTER_SCALE = 1.0;  //scale for the whole thing
    private final int WIDTH = (int)(1280 * MASTER_SCALE);
    private final int HEIGHT = (int)(960 * MASTER_SCALE);

    private volatile boolean gotInput = false;

    public StartPanel(){
        setBackground(new Color(200,191,231));
        requestFocus();
        setPreferredSize(new Dimension(WIDTH,HEIGHT));
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
        double ypos = Math.sin(ang*0.75)*20+550;
        g.drawString("Press Enter to Play", 850, (int)ypos);
    }

    public void update(){
        ang += (Math.PI/90);
        repaint();
    }
    public void waitForInput(){
        while(!gotInput){requestFocus();}
        bgm.stop();
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
