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

    public Function f;
    public volatile List<Coordinate> points = Collections.synchronizedList(new ArrayList<Coordinate>());

    public int pixels = 0;
    private BufferedImage bkg;
    private BufferedImage grid;

    public PlayPanel(Player p, Boss b){
        setPreferredSize(new Dimension(ScaleDimentions.PPWIDTH, ScaleDimentions.HEIGHT));
        addKeyListener(this);
        addFocusListener(this);
        this.p = p;
        this.b = b;
        f = new Function(this);

        bkg = ImageLoader.openImage("Background/background.png");
        grid = ImageLoader.openImage("misc/lines.png");
    }

    public void update(){
        p.update();
        b.update();
        f.update();
        updateAllProjectiles();
        pixels = (int)((b.health/b.maxHealth)*(250));
        repaint();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        makeBackground(g);

        b.draw(g, this);
        p.drawSprite(g, this);
        if(keysDown[4]) p.drawHitbox(g, this);

        drawProjectiles(playerProjectiles, g);
        drawProjectiles(bossProjectiles, g);
        drawProjectiles(points, g);
        drawProjectiles(bombProjectiles, g);

        g.drawRect(10, 10, 250, 50);
        g.setColor(Color.WHITE);
        g.fillRect(12, 12, 250-4, 50-4);
        g.setColor(new Color(255-(int)((Math.abs(b.health/b.maxHealth))*255), (int)((Math.abs(b.health/5000))*255),0));
        g.fillRect(12, 12, pixels, 50);

        backgroundScroll = (backgroundScroll+5) % ScaleDimentions.HEIGHT;
    }

    private void makeBackground(Graphics g){
        g.drawImage(bkg, 0, backgroundScroll, ScaleDimentions.PPWIDTH, ScaleDimentions.HEIGHT,
                0, 0, ScaleDimentions.PPWIDTH, ScaleDimentions.HEIGHT - backgroundScroll,this);
        g.drawImage(bkg, 0,0, ScaleDimentions.PPWIDTH, backgroundScroll,
                0,0, ScaleDimentions.PPWIDTH, backgroundScroll, this);
        g.drawImage(grid,0,0, ScaleDimentions.PPWIDTH, ScaleDimentions.HEIGHT,this);
    }

    private void drawProjectiles(List list, Graphics g){
        synchronized (list){
            Iterator iterator = list.iterator();
            while (iterator.hasNext()) ((Bullet)iterator.next()).draw(g, this);
        }
    }

    private void updateAllProjectiles(){
        synchronized (playerProjectiles) {
            Iterator<Bullet> iterator = playerProjectiles.iterator();
            while (iterator.hasNext()) {
                Bullet bull = iterator.next();
                bull.update();
                if(!bull.timeUp()) iterator.remove();
                else if(b.takeDamage(bull)){
                    iterator.remove();
                }
            }
        }
        synchronized (bossProjectiles) {
            Iterator<Bullet> iterator = bossProjectiles.iterator();
            while (iterator.hasNext()) {
                Bullet bull = iterator.next();
                bull.update();
                if(!bull.timeUp()) iterator.remove();
                if(p.takeDamage(bull)){
                    bossProjectiles = new ArrayList<Bullet>();
                    synchronized (points) { points.clear(); }
                    f.reset();
                }
            }
        }
        synchronized (points) {
            Iterator<Coordinate> iterator = points.iterator();
            while (iterator.hasNext()) {
                Bullet bull = iterator.next();
                bull.update();
                if(!bull.timeUp()) iterator.remove();
                if(p.takeDamage(bull)){
                    synchronized (bossProjectiles){ bossProjectiles.clear(); }
                    points = new ArrayList<Coordinate>();
                    f.reset();
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
                if(!bomb.timeUp() || b.takeDamage(bomb)) iterator.remove();
            }
        }
        if(!b.isAlive()) win = true;
        if(!p.isAlive()) gameOver = true;
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
        if(key == KeyEvent.VK_X) p.canBomb = true;
    }
    public void focusGained(FocusEvent e) {}
    public void focusLost(FocusEvent e) {}
    public void actionPerformed(ActionEvent e){}
}
