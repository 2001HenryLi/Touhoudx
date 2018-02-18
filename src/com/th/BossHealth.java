package com.th;

import java.awt.*;
import javax.swing.JPanel;

public class BossHealth extends JPanel{

    private int pixels;

    private final int HEIGHT = 100; //change these accordingly these are test dimensions
    private final int WIDTH = 500;

    public BossHealth() {
        pixels = (WIDTH-4);
        setBackground(Color.WHITE);
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawRect(0, 0, WIDTH, HEIGHT);
        g.setColor(Color.WHITE);
        g.fillRect(2, 2, WIDTH-4, HEIGHT-4);
        if(pixels < WIDTH/4)
            g.setColor(Color.RED);
        else if(pixels< WIDTH/2)
            g.setColor(Color.ORANGE);
        else if(pixels< WIDTH*(3.0/4.0))
            g.setColor(Color.YELLOW);
        else
            g.setColor(Color.GREEN);
        g.fillRect(2, 2, pixels, HEIGHT-4);
    }
	/*
	public void update(Boss b){
		pixels = (int)((b.getHealth()/1000)*(WIDTH-4));
		repaint();
	}
	*/
}
