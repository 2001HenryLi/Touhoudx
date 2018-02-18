import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

class PlayPanel extends JPanel implements KeyListener, FocusListener, ActionListener{
    private int backgroundScroll = 0;
    private int fofx = 0;
    private final double MASTER_SCALE = 1.0;
    private final int WIDTH = (int)(1280 * 3 / 5 * MASTER_SCALE);
    private final int HEIGHT = (int)(960 * MASTER_SCALE);
    
    private final int[] INPUT_CODES = {KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_SHIFT, KeyEvent.VK_Z, KeyEvent.VK_X};
    public boolean[] keysDown = new boolean[INPUT_CODES.length];

    public Function f;
    private Player p;
    private Boss b;
    public volatile ArrayList<Bullet> projectiles = new ArrayList<>();
    public volatile ArrayList<Bullet> bossProjectiles = new ArrayList<>();
    public volatile ArrayList<Coordinate> points = new ArrayList<>();

    public PlayPanel(Player p, Boss b){
        setBackground(new Color(255,255,255));
        setPreferredSize(new Dimension(WIDTH,HEIGHT));
        addKeyListener(this);
        addFocusListener(this);
        this.p = p;
        this.b = b;
        f.chooseRandom();
        requestFocus();
    }
    
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        makeBackground(g);
        
        g.drawImage(b.sprite, b.getSpriteX(), b.getSpriteY(), b.spriteWidth, b.spriteHeight,this);

        g.drawImage(p.sprite, p.getSpriteX(), p.getSpriteY(), p.spriteWidth, p.spriteHeight,this);
        if(keysDown[4]) g.drawImage(p.hitbox, p.getHitboxX(), p.getHitboxY(), p.hitboxWidth, p.hitboxHeight,this);

        for(Bullet bull : projectiles) g.drawImage(bull.sprite, bull.getSpriteX(), bull.getSpriteY(), bull.spriteWidth, bull.spriteHeight,this);
        for(Bullet bull : bossProjectiles) g.drawImage(bull.sprite, bull.getSpriteX(), bull.getSpriteY(), bull.spriteWidth, bull.spriteHeight,this);
        g.setColor(Color.BLACK);
        g.setFont(new Font(Font.SANS_SERIF, Font.TRUETYPE_FONT, 40));
        g.drawString(f.getFunction(), 10, 50);
        for(Coordinate c : points) g.fillRect(c.getX(), c.getY(), 3,3);
        
        backgroundScroll = (backgroundScroll+5) % 1280;
    }

    public void makeBackground(Graphics g){
        g.drawImage(Toolkit.getDefaultToolkit().getImage("Resources/Background/background.png"),0, backgroundScroll,960,1280,this);
        g.drawImage(Toolkit.getDefaultToolkit().getImage("Resources/Background/background.png"),0,0,960, backgroundScroll,this);
        g.drawImage(Toolkit.getDefaultToolkit().getImage("Resources/misc/lines.PNG"),0,0,960,1280,this);
    }

    public void update(){
        if(!isFocusOwner()) requestFocus();
        p.update();
        b.update();
        for(int i = 0; i < projectiles.size(); i++){
            Bullet bull = projectiles.get(i);
            bull.update();
            if(!bull.isOnscreen()) projectiles.remove(bull);
            else if(b.takeDamage(bull)){
                projectiles.remove(bull);
                i--;
            }
        }
        for(int i = 0; i < bossProjectiles.size(); i++){
            Bullet bull = bossProjectiles.get(i);
            bull.update();
            if(!bull.isOnscreen()) bossProjectiles.remove(bull);
            if(p.takeDamage(bull)) bossProjectiles = new ArrayList<Bullet>();
        }
        fofx+= 0.01;
        if(fofx<10)
        	points.add(new Coordinate((int)(fofx*96),(int)(f.getValue(fofx)));
        else{
        	f.chooseRandom();
        	points.clear();
        }
        repaint();
    }

    public void keyTyped(KeyEvent e) {}
    public void keyPressed(KeyEvent e){
        int key = e.getKeyCode();
        for(int i = 0; i < keysDown.length; i++) if(key == INPUT_CODES[i]) keysDown[i] = true;
    }
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        for(int i = 0; i < keysDown.length; i++) if(key == INPUT_CODES[i]) keysDown[i] = false;
    }
    public void focusGained(FocusEvent e) {}
    public void focusLost(FocusEvent e) {}
    public void actionPerformed(ActionEvent e){}
}
