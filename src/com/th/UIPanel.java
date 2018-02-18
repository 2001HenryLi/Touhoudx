package com.th;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class UIPanel extends JPanel{

    private double power;
    private int lives;
    private int spells;
    private int lifedrop;
    private int spelldrop;
	
    private final double TOTALPOWER = 2.0;
    private final int NEWLIFE = 3; //amount needed for another life
    private final int NEWSPELL = 8; //need this many for a new spell
    private final double MASTER_SCALE = 1.0;  //scale for the whole thing
    private final int WIDTH = (int)(1280 * 2 / 5 * MASTER_SCALE);
    private final int HEIGHT = (int)(960 * MASTER_SCALE);
	
    public UIPanel(){
        setBackground(Color.WHITE);
        requestFocus();
        setPreferredSize(new Dimension(WIDTH,HEIGHT));
        power = 2.0;
		lives = 4;
		spells = 3;
		lifedrop = 0;
		spelldrop = 0;
    }
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		render(g);
	}
	
	public void render(Graphics g){
		Image back = Toolkit.getDefaultToolkit().getImage("backUI.PNG");
		Image heart = Toolkit.getDefaultToolkit().getImage("heart.PNG");
		Image card = Toolkit.getDefaultToolkit().getImage("spellcard.PNG");
		g.drawImage(back, 0,0, WIDTH, HEIGHT, this);
		g.setFont(new Font(Font.SANS_SERIF, Font.TRUETYPE_FONT, 25));
		g.drawString("Pwr: "+power+" / "+TOTALPOWER, 10, 60);
		g.drawImage(heart, 10, 80, 30, 30,this);
		g.drawString(""+lives, 50, 103);
		g.drawString(lifedrop+" / "+NEWLIFE, 90, 103);
		g.drawImage(card, 10, 120, 30, 30,this);
		g.drawString(""+spells, 50, 145);
		g.drawString(spelldrop+" / "+NEWSPELL, 90, 145);
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
