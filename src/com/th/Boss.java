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
    private long previousMove = 0;

    public PlayPanel pp;

    public String name;
    public BufferedImage sprite;
    public double health = 5000;
    public int x = 1280 * 3 / 5 / 2;
    public int y = 240;
    public int x_f = x;
    public int y_f = y;
    public int spriteWidth;
    public int spriteHeight;

    private BufferedImage[] shots = new BufferedImage[4];
    private String[] sfx = {"Resources\\SFX\\ATTACK3.wav", "Resources\\SFX\\ATTACK2.wav", "Resources\\SFX\\ATTACK.wav", "Resources\\SFX\\ATTACK4.wav"};

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
                    }));
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
                    }));
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
                    double offset = Math.random()* 2 * Math.PI;
                    double radians = 2 * Math.PI * i / 16 + offset;
                    int j0 = j;
                    pattern.add(new Bullet(shots[1], x - 16, y - 16, 32, 32, new MovePath() {
                        @Override
                        public int[] move(long t, int x0, int y0) {
                            int[] pos = {x0, y0};
                            long tMilli = TimeUnit.MILLISECONDS.convert(t, TimeUnit.NANOSECONDS);
                            long tDiff = Math.max(tMilli - 400*j0, 0);
                            pos[0] = x0 + (int) (Math.sin(radians) * tDiff / 1.5);
                            pos[1] = y0 + (int) (tDiff / 3);
                            return pos;
                        }
                    }));
                }
            }
            return pattern;
        }
    };

    private BulletPattern[] bps = {circle, circle2, circle3, sweep};
    public Boss(PlayPanel p){
        if(health > 2500)
            name = "BossStage1";
        else
            name = "BossStage2";
        pp = p;
        try {
            sprite = ImageIO.read(new File("Resources/BossSprites/"+name+".png"));
            shots[0] = ImageIO.read(new File("Resources\\ProjectileSprites\\BasicShot.png"));
            shots[1] = ImageIO.read(new File("Resources\\ProjectileSprites\\plusc.png"));
            shots[2] = ImageIO.read(new File("Resources\\ProjectileSprites\\PotatoProjectile.png"));
            shots[3] = ImageIO.read(new File("Resources\\ProjectileSprites\\CircleLarge.png"));
        } catch(IOException e) {
            System.out.println("failed");
            System.exit(-1);
        }
        spriteWidth = sprite.getWidth();
        spriteHeight = sprite.getHeight();
    }

    public void update(){
        if(health > 2500) name = "BossStage1";
        else name = "BossStage2";
        if(startTime == -1) startTime = System.nanoTime();
        elapsedTime = System.nanoTime() - startTime;

        try {
            sprite = ImageIO.read(new File("Resources/BossSprites/"+name+".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(elapsedTime > previousMove + 1000000000 || health <= 2500){
            move();
        }

        if(elapsedTime > previousShot + 1000000000){
            previousShot += 1000000000;
            int rand = (int)(Math.random() * bps.length);
            ArrayList<Bullet> addProjectiles = bps[rand].makePattern();
            pp.sfx.playFX(sfx[rand]);
            for(Bullet b : addProjectiles) pp.bossProjectiles.add(b);
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
        Rectangle bRect = new Rectangle(b.getSpriteX(), b.getSpriteY(), b.spriteWidth, b.spriteHeight);
        Rectangle pRect = new Rectangle(getSpriteX(), getSpriteY(), spriteWidth, spriteHeight);

        if(bRect.intersects(pRect)){
            if (b instanceof Bomb) health -= 100;
            else health--;
            return true;
        }
        return false;
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
}
