package com.th;

import sun.awt.image.BufferedImageGraphicsConfig;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Boss {
    private long startTime = -1;
    private long elapsedTime = 0;
    private long previousShot = 0;
    private long previousMove = 0;

    public PlayPanel pp;

    private String[] stages = {"BossStage1", "BossStage2"};
    private int currStage = 0;
    private BufferedImage sprite;
    private int spriteWidth;
    private int spriteHeight;

    public final double maxHealth = 5000;
    public double health = maxHealth;

    private int x = 1280 * 3 / 5 / 2;
    private int y = 240;
    private int x_f = x;
    private int y_f = y;

    private BufferedImage[] shots = new BufferedImage[4];
    private String[] sfx = {"SFX/ATTACK3.wav", "SFX/ATTACK2.wav", "SFX/ATTACK.wav", "SFX/ATTACK4.wav"};

    private BulletPattern circle = new BulletPattern() {
        @Override
        public ArrayList<Bullet> makePattern() {
            ArrayList<Bullet> pattern = new ArrayList<Bullet>();
            double offset = Math.random()* 2 * Math.PI;
            for(int i = 0; i < 16; i++){
                double radians = 2 * Math.PI * i / 16 + offset;
                pattern.add(new Bullet(shots[2], x - 16, y - 16, 32, 32, new MovePath() {
                    @Override
                    public int[] move(long t, int x0, int y0) {
                        int[] pos = {x0, y0};
                        long tMilli = TimeUnit.MILLISECONDS.convert(t, TimeUnit.NANOSECONDS);
                        pos[0] = x0 + (int)(Math.sin(radians) * tMilli / 1.5);
                        pos[1] = y0 + (int)(Math.cos(radians) * tMilli / 1.5);
                        return pos;
                    }
                }, -1));
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
                    pattern.add(new Bullet(shots[3], x - 16, y - 16, 32, 32, new MovePath() {
                        @Override
                        public int[] move(long t, int x0, int y0) {
                            int[] pos = {x0, y0};
                            long tMilli = TimeUnit.MILLISECONDS.convert(t, TimeUnit.NANOSECONDS);
                            long tDiff = Math.max(tMilli - j0*100, 0);
                            pos[0] = x0 + (int) (Math.sin(radians) * tDiff/Math.pow(tMilli, 0.1));
                            pos[1] = y0 + (int) (Math.cos(radians) * tDiff/Math.pow(tMilli, 0.1));
                            return pos;
                        }
                    }, -1));
                }
            }
            return pattern;
        }
    };
    private BulletPattern circle3 = new BulletPattern() {
        @Override
        public ArrayList<Bullet> makePattern() {
            ArrayList<Bullet> pattern = new ArrayList<Bullet>();
            double offset = Math.random()* 2 * Math.PI;

            int dir = Math.random() >= 0.5 ? -1 : 1;
            for(int j = 0; j < 16; j++) {
                for (int i = 0; i < 4; i++) {
                    double radians = 2 * Math.PI * i / 4 + offset;
                    int j0 = j;
                    pattern.add(new Bullet(shots[0], x - 16, y - 16, 32, 32, new MovePath() {
                        @Override
                        public int[] move(long t, int x0, int y0) {
                            int[] pos = {x0, y0};
                            long tMilli = TimeUnit.MILLISECONDS.convert(t, TimeUnit.NANOSECONDS);
                            long tDiff = Math.max(tMilli - j0*10, 0);
                            pos[0] = x0 + (int) (Math.sin(radians + tDiff/1000.0 * dir) * tDiff/Math.pow(tMilli, 0.2)) ;
                            pos[1] = y0 + (int) (Math.cos(radians + tDiff/1000.0 * dir) * tDiff/Math.pow(tMilli, 0.2));
                            return pos;
                        }
                    }, TimeUnit.NANOSECONDS.convert(5, TimeUnit.SECONDS)));
                }
            }
            return pattern;
        }
    };
    private BulletPattern sweep = new BulletPattern() {
        @Override
        public ArrayList<Bullet> makePattern() {
            ArrayList<Bullet> pattern = new ArrayList<Bullet>();
            for(int j = 0; j < 3; j++) {
                for (int i = 0; i < 16; i++) {
                    int i0 = i;
                    int j0 = j;
                    pattern.add(new Bullet(shots[1], x - 16, y - 16, 32, 32, new MovePath() {
                        @Override
                        public int[] move(long t, int x0, int y0) {
                            int[] pos = {x0, y0};
                            long tMilli = TimeUnit.MILLISECONDS.convert(t, TimeUnit.NANOSECONDS);
                            long tDiff = Math.max(tMilli - 400*j0, 0);
                            pos[0] = x0 + (int) ((i0-8) * tDiff / 10);
                            pos[1] = y0 + (int) (tDiff / 3);
                            return pos;
                        }
                    }, -1));
                }
            }
            return pattern;
        }
    };
    private BulletPattern[] bps = {circle, circle2, circle3, sweep};

    public Boss(PlayPanel p){
        pp = p;
        loadSprites();
    }

    public void loadSprites(){
        sprite = ImageLoader.openImage("BossSprites/"+stages[currStage]+".png");
        shots[0] = ImageLoader.openImage("ProjectileSprites/BasicShot.png");
        shots[1] = ImageLoader.openImage("ProjectileSprites/plusc.png");
        shots[2] = ImageLoader.openImage("ProjectileSprites/PotatoProjectile.png");
        shots[3] = ImageLoader.openImage("ProjectileSprites/CircleLarge.png");
        spriteWidth = sprite.getWidth();
        spriteHeight = sprite.getHeight();
    }

    public void update(){
        if(startTime == -1) startTime = System.nanoTime();
        elapsedTime = System.nanoTime() - startTime;

        if(health < 2500 && currStage == 0) sprite = ImageLoader.openImage("BossSprites/"+stages[++currStage]+".png");

        if(elapsedTime > previousMove + 1000000000 || health <= 2500) move();
        if(elapsedTime > previousShot + 1000000000){
            previousShot += 1000000000;
            int rand;
            if(health > maxHealth/2) rand = (int)(Math.random() * bps.length/2);
            else rand = (int)(Math.random() * bps.length);
            SFX.playOnce(sfx[rand]);
            ArrayList<Bullet> addProjectiles = bps[rand].makePattern();
            synchronized (pp.bossProjectiles){ for(Bullet b : addProjectiles) pp.bossProjectiles.add(b); }
        }
    }

    public void move(){
        if(x > x_f) x--;
        if(y > y_f) y--;
        if(x < x_f) x++;
        if(y < y_f) y++;

        if(x == x_f && y == y_f){
            x_f = Math.random() > 0.5 ? (int)(Math.random() * 1080 * 3 / 5 + 100) : 1280 * 3 / 5 / 2;
            y_f = Math.random() > 0.5 ? (int)(Math.random() * 400 + 40) : 240;
            previousMove = System.nanoTime() - startTime;
        }
    }

    public boolean takeDamage(Bullet b){
        if(b.getRect().intersects(getRect())){
            if (b instanceof Bomb) health -= 100;
            else health--;
            return true;
        }
        return false;
    }

    public void draw(Graphics g, ImageObserver imageObserver){
        g.drawImage(sprite, getSpriteX(), getSpriteY(), spriteWidth, spriteHeight, imageObserver);
    }
    public boolean isAlive(){
        return health > 0;
    }
    public int getSpriteX(){
        return x - spriteWidth/2;
    }
    public int getSpriteY(){
        return y - spriteHeight/2;
    }
    public Rectangle getRect(){
        return new Rectangle(getSpriteX(), getSpriteY(), spriteWidth, spriteHeight);
    }
}
