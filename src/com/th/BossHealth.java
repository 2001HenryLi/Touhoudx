package com.th;

import java.awt.*;
import javax.swing.JPanel;

public class BossHealth extends JPanel{

    private int pixels;

    private final int HEIGHT = 50; //change these accordingly these are test dimensions
    private final int WIDTH = 200;

    public BossHealth() {
        pixels = WIDTH-4;
        setBackground(Color.WHITE);
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawRect(0, 0, WIDTH, HEIGHT);
        g.setColor(Color.WHITE);
        g.fillRect(2, 2, WIDTH-4, HEIGHT-4);
        g.setColor(Color.RED);
    }

    public void update(Boss b){
        pixels = (int)((b.getHealth()/1000)*(WIDTH-4));
        repaint();
    }
}
