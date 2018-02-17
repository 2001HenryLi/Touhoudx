package com.th;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class StartPanel extends JPanel implements KeyListener, FocusListener, ActionListener{
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

        //Image border = Toolkit.getDefaultToolkit().getImage("resources\\board\\start_menu\\border.png");
        //g.drawImage(border,-bo,-bo,WIDTH+2*bo,HEIGHT+2*bo,this);
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
}