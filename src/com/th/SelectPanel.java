import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class SelectPanel extends JPanel implements KeyListener, FocusListener{

	private Player guy;
	
	public SelectPanel(Player p) {
		guy = p;
		addKeyListener(this);
		addFocusListener(this);
		requestFocus();
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
		repaint();
	}
	
	public void focusGained(FocusEvent e) {
	}
	public void focusLost(FocusEvent e) {
	}
	public void keyPressed(KeyEvent e) {
		if(e.getKeyChar() == '1')
			guy.name = "cirno";
		if(e.getKeyChar() == '2')
			guy.name = "reimu";
	}
	public void keyReleased(KeyEvent e) {
	}
	public void keyTyped(KeyEvent e) {
	}
}
