package com.th;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class UIPanel extends JPanel{
    private Player guy;
	private final int WIDTH = ScaleDimentions.WIDTH * 2 / 5;
    public UIPanel(Player p){
        requestFocus();
		setPreferredSize(new Dimension(WIDTH, ScaleDimentions.HEIGHT));
	    guy = p;
    }
	
    public void paintComponent(Graphics g){
		super.paintComponent(g);
		render(g);
    }
	
	public void render(Graphics g){
		Image back = Toolkit.getDefaultToolkit().getImage("Resources/UISprites/backUI.png");
		Image heart = Toolkit.getDefaultToolkit().getImage("Resources/UISprites/Heart.png");
		Image card = Toolkit.getDefaultToolkit().getImage("Resources/UISprites/Spellcard.png");
		g.drawImage(back, 0,0, WIDTH, ScaleDimentions.HEIGHT, this);
		g.setFont(new Font(Font.SANS_SERIF, Font.TRUETYPE_FONT, 80));
		g.drawImage(heart, 60, 165, 100, 100,this);
		g.drawString(""+guy.lives, 200, 230);
		g.drawImage(card, 60, 295, 100, 100,this);
		g.drawString(""+guy.bombs, 200, 390);
		g.setColor(Color.WHITE);
		g.fillRect(70,ScaleDimentions.HEIGHT - 230, 400, 100);
		g.setColor(Color.BLACK);
        g.drawString(guy.pp.f.getFunction(), 90, ScaleDimentions.HEIGHT - 160);
	}

    public void update(){
        repaint();
    }
}
