package com.th;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class SelectPanel extends JPanel implements KeyListener, FocusListener{

	private final double MASTER_SCALE = 1.0;
	private final int WIDTH = (int)(1280 * MASTER_SCALE);
	private final int HEIGHT = (int)(960 * MASTER_SCALE);
	private int choice = 1;

	private volatile boolean gotInput;

	private Player guy;
	
	public SelectPanel() {
		gotInput = false;
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
		g.drawString("Select a character (Arrow keys to toggle)", 275, 100);
	}
	
	public void update(){
		repaint();
	}

	public Player waitForInput()
	{
		while(!gotInput){}

		return guy;
	}
	
	public void focusGained(FocusEvent e) {
	}
	public void focusLost(FocusEvent e) {
	}
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ENTER){
			if(choice == 1) {
				guy = new Player("cirno");
				gotInput = true;
			}
			if(choice == 2) {
				guy = new Player("reimu");
				gotInput = true;
			}
		}
		if((e.getKeyCode() == KeyEvent.VK_LEFT) && (choice == 2)) choice = 1;
		else if((e.getKeyCode() == KeyEvent.VK_RIGHT) && (choice == 1)) choice = 2;
	}
	public void keyReleased(KeyEvent e) {
	}
	public void keyTyped(KeyEvent e) {
	}
}
