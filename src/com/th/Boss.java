package com.th;

import sun.awt.image.BufferedImageGraphicsConfig;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Boss {
    private long startTime = System.nanoTime();
    private long elapsedTime = 0;
    private long previousShot = 0;

    public PlayPanel pp;

    public String name = "BossStage1";
    public BufferedImage sprite;
    public double health;
    public int x = 1280 * 3 / 5 / 2;
    public int y = 240;
    public int spriteWidth;
    public int spriteHeight;

    private BulletPattern circle = new BulletPattern() {
        @Override
        public ArrayList<Bullet> makePattern() {
            ArrayList<Bullet> pattern = new ArrayList<Bullet>();
            for(int i = 0; i < 16; i++){
                double radians = 2*Math.PI * i / 16;
                pattern.add(new Bullet("Resources\\ProjectileSprites\\BasicSQUARE.png", x - 16, y - 16, 32, 32, new MovePath() {
                    @Override
                    public int[] move(long t, int x0, int y0) {
                        int[] pos = {x0, y0};
                        pos[0] = x0 + (int)(Math.sin(radians) * TimeUnit.MILLISECONDS.convert(t, TimeUnit.NANOSECONDS));
                        pos[1] = y0 + (int)(Math.cos(radians) * TimeUnit.MILLISECONDS.convert(t, TimeUnit.NANOSECONDS));
                        return pos;
                    }
                }));
            }
            return pattern;
        }
    };

    public Boss(PlayPanel p){
        pp = p;
        try {
            sprite = ImageIO.read(new File("Resources/BossSprites/"+name+".png"));
        } catch(IOException e) {
            System.out.println("failed");
            System.exit(-1);
        }
        health = 10.0;
        spriteWidth = sprite.getWidth();
        spriteHeight = sprite.getHeight();
    }

    public void update(){
        elapsedTime = System.nanoTime()-startTime;
        if(elapsedTime > previousShot+1000000000){
            previousShot += 1000000000;
            ArrayList<Bullet> addProjectiles = circle.makePattern();
            for(Bullet b : addProjectiles) pp.bossProjectiles.add(b);
        }
    }

    public boolean takeDamage(Bullet b){
        Rectangle bRect = new Rectangle(b.getSpriteX(), b.getSpriteY(), b.spriteWidth, b.spriteHeight);
        Rectangle pRect = new Rectangle(getSpriteX(), getSpriteY(), spriteWidth, spriteHeight);
        if(bRect.intersects(pRect)){
            //oof
            return true;
        }
        return false;
    }

    public int getSpriteX(){
        return x - spriteWidth/2;
    }
    public int getSpriteY(){
        return y - spriteHeight/2;
    }
}
