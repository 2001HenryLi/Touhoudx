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
    private long startTime = -1;
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
            double offset = Math.random()* 2 * Math.PI;
            for(int i = 0; i < 16; i++){
                double radians = 2 * Math.PI * i / 16 + offset;
                pattern.add(new Bullet("Resources\\ProjectileSprites\\BasicShot.png", x - 16, y - 16, 32, 32, new MovePath() {
                    @Override
                    public int[] move(long t, int x0, int y0) {
                        int[] pos = {x0, y0};
                        long tMilli = TimeUnit.MILLISECONDS.convert(t, TimeUnit.NANOSECONDS);
                        pos[0] = x0 + (int)(Math.sin(radians) * tMilli);
                        pos[1] = y0 + (int)(Math.cos(radians) * tMilli);
                        return pos;
                    }
                }));
            }
            return pattern;
        }
    };

    private BulletPattern circle2 = new BulletPattern() {
        @Override
        public ArrayList<Bullet> makePattern() {
            ArrayList<Bullet> pattern = new ArrayList<Bullet>();
            double offset = Math.random()* 2 * Math.PI;
            for(int j = 0; j < 8; j++) {
                for (int i = 0; i < 32; i++) {
                    double radians = 2 * Math.PI * i / 32 + offset;
                    int j0 = j;
                    pattern.add(new Bullet("Resources\\ProjectileSprites\\BasicShot.png", x - 16, y - 16, 32, 32, new MovePath() {
                        @Override
                        public int[] move(long t, int x0, int y0) {
                            int[] pos = {x0, y0};
                            long tMilli = TimeUnit.MILLISECONDS.convert(t, TimeUnit.NANOSECONDS);
                            pos[0] = x0 + (int) (Math.sin(radians) * Math.max(tMilli - j0*100, 0)/Math.pow(tMilli, 0.1));
                            pos[1] = y0 + (int) (Math.cos(radians) * Math.max(tMilli - j0*100, 0)/Math.pow(tMilli, 0.1));
                            return pos;
                        }
                    }));
                }
            }
            return pattern;
        }
    };

    private BulletPattern[] bps = {circle, circle2};
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
        if(startTime == -1) startTime = System.nanoTime();
        elapsedTime = System.nanoTime() - startTime;

        if(elapsedTime > previousShot + 1000000000){
            previousShot += 1000000000;
            ArrayList<Bullet> addProjectiles = bps[(int)(Math.random() * bps.length)].makePattern();
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
