package com.th;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class SelectPanel extends JPanel implements KeyListener, FocusListener{

	private final double MASTER_SCALE = 1.0;
	private final int WIDTH = (int)(1280 * MASTER_SCALE);
	private final int HEIGHT = (int)(960 * MASTER_SCALE);
	private int choice = 1;

	private boolean gotInput;

	private Player guy;
	
	public SelectPanel() {
		gotInput = false;
		requestFocus();
		setPreferredSize(new Dimension(WIDTH,HEIGHT));
		setBackground(Color.BLACK);
		addKeyListener(this);
		addFocusListener(this);
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		render(g);
	}
	
	public void render(Graphics g){
		Image choicey = Toolkit.getDefaultToolkit().getImage("Resources/Background/characterselect.png");
		if(choice == 1)
			g.drawImage(choicey, 0, 0, 640, 960, 0, 0, 640, 960, this);
		else
			g.drawImage(choicey, 640, 0, 1280, 960, 640, 0, 1280, 960, this);
		g.setFont(new Font(Font.SANS_SERIF, Font.TRUETYPE_FONT, 50));
		g.setColor(Color.WHITE);
		g.drawString("Select a character (Space to toggle)", 375, 100);
	}
	
	public void update(){
		requestFocus();
		repaint();
	}

	public Player waitForFocus()
	{
		while(!gotInput){requestFocus();}
		return guy;
	}
	
	public void focusGained(FocusEvent e) {
	}
	public void focusLost(FocusEvent e) {
	}
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == VK_ENTER){
			if(choice == 1) {
				guy = new Player("cirno");
				gotInput = true;
			}
			if(choice == 2) {
				guy = new Player("reimu");
				gotInput = true;
			}
		}
		if(e.getKeyCode() == VK_SPACE){
			if(choice == 1)
				choice = 2;
			else
				choice = 1;
		}
	}
	public void keyReleased(KeyEvent e) {
	}
	public void keyTyped(KeyEvent e) {
	}
}
