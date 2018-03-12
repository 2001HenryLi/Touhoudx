package com.th;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

class UIPanel extends JPanel{
    private Player p;
    private BufferedImage back;
    private BufferedImage heart;
    private BufferedImage card;

    public UIPanel(Player p){
        back = ImageLoader.openImage("UISprites/backUI.png");
        heart = ImageLoader.openImage("UISprites/Heart.png");
        card = ImageLoader.openImage("UISprites/Spellcard.png");
		setPreferredSize(new Dimension(ScaleDimentions.UIWIDTH, ScaleDimentions.HEIGHT));
	    this.p = p;
    }
	
    public void paintComponent(Graphics g){
		super.paintComponent(g);

        g.drawImage(back, 0,0, ScaleDimentions.UIWIDTH, ScaleDimentions.HEIGHT, this);
        g.setFont(new Font(Font.SANS_SERIF, Font.TRUETYPE_FONT, 80));
        g.drawImage(heart, 60, 165, 100, 100,this);
        g.drawString(""+p.lives, 200, 230);
        g.drawImage(card, 60, 295, 100, 100,this);
        g.drawString(""+p.bombs, 200, 390);
        g.setColor(Color.WHITE);
        g.fillRect(70,ScaleDimentions.HEIGHT - 230, 400, 100);
        g.setColor(Color.BLACK);
        g.drawString(p.pp.f.getFunction(), 90, ScaleDimentions.HEIGHT - 160);
    }

    public void update(){
        repaint();
    }
}
