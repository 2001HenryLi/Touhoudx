package com.th;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Collections;

class PlayPanel extends JPanel implements KeyListener, FocusListener, ActionListener{
    private final int WIDTH = ScaleDimentions.WIDTH * 3 / 5;
    private int backgroundScroll = 0;

    public boolean gameOver;
    public boolean win;

    private final int[] INPUT_CODES = {KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_SHIFT, KeyEvent.VK_Z};
    public boolean[] keysDown = new boolean[INPUT_CODES.length];

    private Player p;
    private Boss b;
    public volatile List<Bullet> playerProjectiles = Collections.synchronizedList(new ArrayList<Bullet>());
    public volatile List<Bullet> bossProjectiles = Collections.synchronizedList(new ArrayList<Bullet>());
    public volatile List<Bomb> bombProjectiles = Collections.synchronizedList(new ArrayList<Bomb>());

    public Function f = new Function();
    private double fofx = -1;
    public volatile ArrayList<Coordinate> points = new ArrayList<>();
    public int pixels = 0;

    public PlayPanel(Player p, Boss b){
        setBackground(new Color(255,255,255));
        setPreferredSize(new Dimension(WIDTH, ScaleDimentions.HEIGHT));
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

        drawProjectiles(playerProjectiles, g);
        drawProjectiles(bossProjectiles, g);
        drawProjectiles(points, g);
        drawProjectiles(bombProjectiles, g);

        g.drawRect(10, 10, 250, 50);
        g.setColor(Color.WHITE);
        g.fillRect(12, 12, 250-4, 50-4);
        g.setColor(new Color(255-(int)((Math.abs(b.health/5000))*255), (int)((Math.abs(b.health/5000))*255),0));
        g.fillRect(12, 12, pixels, 50);

        backgroundScroll = (backgroundScroll+5) % 1280;
    }

    public void makeBackground(Graphics g){
        g.drawImage(Toolkit.getDefaultToolkit().getImage("Resources/Background/background.png"),0, backgroundScroll,960,1280,this);
        g.drawImage(Toolkit.getDefaultToolkit().getImage("Resources/Background/background.png"),0,0,960, backgroundScroll,this);
        g.drawImage(Toolkit.getDefaultToolkit().getImage("Resources/misc/lines.PNG"),0,0,960,1280,this);
    }

    public void drawProjectiles(List l, Graphics g){
        synchronized (l){
            Iterator iterator = l.iterator();
            while (iterator.hasNext()) {
                Bullet bull = (Bullet)iterator.next();
                g.drawImage(bull.sprite, bull.getSpriteX(), bull.getSpriteY(), bull.spriteWidth, bull.spriteHeight,this);
            }
        }
    }

    public void update(){
        if(!isFocusOwner()) requestFocus();
        p.update();
        b.update();
        updateAllProjectiles();
        updateFunction();
        repaint();
    }

    public void updateAllProjectiles(){
        synchronized (playerProjectiles) {
            Iterator<Bullet> iterator = playerProjectiles.iterator();
            while (iterator.hasNext()) {
                Bullet bull = iterator.next();
                bull.update();
                if(!bull.isOnscreen()) iterator.remove();
                else if(b.takeDamage(bull)){
                    iterator.remove();
                    if(!b.isAlive()) win = true;
                }
            }
        }
        synchronized (bossProjectiles) {
            Iterator<Bullet> iterator = bossProjectiles.iterator();
            while (iterator.hasNext()) {
                Bullet bull = iterator.next();
                bull.update();
                if(!bull.isOnscreen()) iterator.remove();
                if(p.takeDamage(bull)){
                    bossProjectiles = new ArrayList<Bullet>();
                    points = new ArrayList<Coordinate>();
                    fofx = 100000;
                    if(!p.isAlive()) gameOver = true;
                }
            }
        }
        synchronized (points) {
            Iterator<Coordinate> iterator = points.iterator();
            while (iterator.hasNext()) {
                Bullet bull = iterator.next();
                bull.update();
                if(!bull.isOnscreen()) iterator.remove();
                if(p.takeDamage(bull)){
                    bossProjectiles = new ArrayList<Bullet>();
                    points = new ArrayList<Coordinate>();
                    fofx = 100000;
                    if(!p.isAlive()) gameOver = true;
                }
            }
        }
        synchronized (bombProjectiles) {
            Iterator<Bomb> iterator = bombProjectiles.iterator();
            while (iterator.hasNext()) {
                Bomb bomb = iterator.next();
                bomb.update();
                synchronized (bossProjectiles){
                    Iterator<Bullet> iterator2 = bossProjectiles.iterator();
                    while (iterator2.hasNext()) if (bomb.collide(iterator2.next())) iterator2.remove();
                }
                synchronized (points){
                    Iterator<Coordinate> iterator2 = points.iterator();
                    while (iterator2.hasNext()) if (bomb.collide(iterator2.next())) iterator2.remove();
                }
                if(!bomb.isOnscreen() || b.takeDamage(bomb)) iterator.remove();
            }
        }
    }
    public void updateFunction(){
        fofx += 0.04;
        if(fofx < WIDTH / 40 + 1) {
            try {
                BufferedImage b = ImageIO.read(new File("Resources/ProjectileSprites/Graph.png"));
                points.add(new Coordinate(b, (int) (fofx * 100), ScaleDimentions.HEIGHT - 40 - (int) (f.getValue(fofx)), 16, 16, new MovePath() {
                    @Override
                    public int[] move(long t, int x0, int y0) {
                        int[] pos = {x0, y0};
                        return pos;
                    }
                }));
            } catch(IOException e) {
                System.out.println("failed");
                System.exit(-1);
            }

            if(points.size() > 400) points.remove(0);
        }
        else{
            f.chooseRandom();
            points.clear();
            fofx = -1;
        }
        pixels = (int)((b.health/5000)*(250));
    }

    public void keyTyped(KeyEvent e) {}
    public void keyPressed(KeyEvent e){
        int key = e.getKeyCode();
        for(int i = 0; i < keysDown.length; i++) if(key == INPUT_CODES[i]) keysDown[i] = true;
        if(key == KeyEvent.VK_X) p.bomb();
    }
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        for(int i = 0; i < keysDown.length; i++) if(key == INPUT_CODES[i]) keysDown[i] = false;
    }
    public void focusGained(FocusEvent e) {}
    public void focusLost(FocusEvent e) {}
    public void actionPerformed(ActionEvent e){}
}
