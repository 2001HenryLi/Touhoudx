package com.th;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class UIPanel extends JPanel{
	
    private Player guy;
    private final double MASTER_SCALE = 1.0;  //scale for the whole thing
    private final int WIDTH = (int)(1280 * 2 / 5 * MASTER_SCALE);
    private final int HEIGHT = (int)(960 * MASTER_SCALE);
	
    public UIPanel(Player p){
        setBackground(Color.WHITE);
        requestFocus();
        setPreferredSize(new Dimension(WIDTH,HEIGHT));
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
		g.drawImage(back, 0,0, WIDTH, HEIGHT, this);
		g.setFont(new Font(Font.SANS_SERIF, Font.TRUETYPE_FONT, 35));
		g.drawImage(heart, 10, 195, 45, 45,this);
		g.drawString(""+guy.lives, 50, 218);
		g.drawImage(card, 10, 255, 45, 45,this);
		g.drawString(""+guy.bombs, 50, 270);
	}
	
	//getters and setters
	
	public double getPower(){ return power; }
	public int getLives(){ return lives; }
	public int getSpells(){ return spells; }
	public int getLifeDrop(){ return lifedrop; }
	public int getSpellDrop(){ return spelldrop; }
	
	public void setPower(double p){ power = p; }
	public void setLives(int h){ lives = h; }
	public void setSpells(int s){ spells = s; }
	public void setLifeDrop(int hd){ lifedrop = hd; }
	public void setSpellDrop(int sd){ spelldrop = sd; }

    public void update(){
        repaint();
    }
}
