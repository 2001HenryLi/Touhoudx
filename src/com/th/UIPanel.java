package com.th;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class UIPanel extends JPanel{
    private final double MASTER_SCALE = 1.0;  //scale for the whole thing
    private final int WIDTH = (int)(1280 * 2 / 5 * MASTER_SCALE);
    private final int HEIGHT = (int)(960 * MASTER_SCALE);

    public UIPanel(){
        setBackground(new Color(0,0,0));
        requestFocus();
        setPreferredSize(new Dimension(WIDTH,HEIGHT));
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        //this prints the title

        //Image border = Toolkit.getDefaultToolkit().getImage("resources\\board\\start_menu\\border.png");
        //g.drawImage(border,-bo,-bo,WIDTH+2*bo,HEIGHT+2*bo,this);
    }
}