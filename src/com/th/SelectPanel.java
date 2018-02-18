package com.th;


import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class SelectPanel extends JPanel implements KeyListener, FocusListener{

	private final double MASTER_SCALE = 1.0;
	private final int WIDTH = (int)(1280 * MASTER_SCALE);
	private final int HEIGHT = (int)(960 * MASTER_SCALE);

	private boolean gotInput;

	private Player guy;
	
	public SelectPanel() {
		gotInput = false;
		requestFocus();
		setPreferredSize(new Dimension(WIDTH,HEIGHT));
		addKeyListener(this);
		addFocusListener(this);
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		render(g);
	}
	
	public void render(Graphics g){
		Image cirno = Toolkit.getDefaultToolkit().getImage("Resources/CharacterSprites/cirno.png");
		Image reimu = Toolkit.getDefaultToolkit().getImage("Resources/CharacterSprites/reimu.png");
		g.drawImage(cirno, 100, 300, 500, 500, this);
		g.drawImage(reimu, 700, 300, 500, 500, this);
		g.setFont(new Font(Font.SANS_SERIF, Font.TRUETYPE_FONT, 50));
		g.drawString("Select a character (Press 1 or 2)", 375, 100);
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
		if(e.getKeyCode() == KeyEvent.VK_1) {
			guy = new Player("cirno");
			gotInput = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_2) {
            guy = new Player("reimu");
			gotInput = true;
		}
	}
	public void keyReleased(KeyEvent e) {
	}
	public void keyTyped(KeyEvent e) {
	}
}
