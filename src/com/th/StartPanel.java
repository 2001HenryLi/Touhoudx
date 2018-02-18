package com.th;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class StartPanel extends JPanel implements KeyListener, FocusListener, ActionListener{

    public Image pic;
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
        
        //this prints the title
        render(g);
        doText(g);
        updategraphic();
        //Image border = Toolkit.getDefaultToolkit().getImage("resources\\board\\start_menu\\border.png");
        //g.drawImage(border,-bo,-bo,WIDTH+2*bo,HEIGHT+2*bo,this);
    }

    public void updategraphic(){ //ryan calls this method
        ang += (Math.PI/90);
        repaint();
    }

    public boolean waitForInput(){
        while(!gotInput){requestFocus();}
        bgm.stop();
        return true;
    }
    public void keyTyped(KeyEvent e) {}
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ENTER) gotInput = true;
    }
    public void keyReleased(KeyEvent e) {}
    public void focusGained(FocusEvent e) {}
    public void focusLost(FocusEvent e) {}
    public void actionPerformed(ActionEvent e){}

    public void render(Graphics g){
        pic = Toolkit.getDefaultToolkit().getImage("titleresized.PNG");
        double ypos = Math.cos(ang)*70+140;
        g.drawImage(pic, 225, (int)ypos, 500, 140, this);
    }

    public void doText(Graphics g){
        g.setFont(new Font(Font.SANS_SERIF, Font.TRUETYPE_FONT, 30));
        double ypos = Math.sin(ang)*20+700;
        g.drawString("Press Enter to Play", 360, (int)ypos);
    }
}