package com.th;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.util.concurrent.TimeUnit;

public class Bullet {
    private long startTime = -1;
    private long elapsedTime = 0;

    private BufferedImage sprite;
    private int spriteWidth = 32;
    private int spriteHeight = 32;

    private int x0;
    private int y0;
    private int x;
    private int y;
    private MovePath mp;

    public Bullet(BufferedImage b, int X, int Y, int w, int h, MovePath MP){
        sprite = b;
        spriteWidth = w;
        spriteHeight = h;
        x = X;
        x0 = X;
        y = Y;
        y0 = Y;
        mp = MP;
    }
    public void update(){
        if(startTime == -1) startTime = System.nanoTime();
        elapsedTime = System.nanoTime() - startTime;
        int[] pos = mp.move(elapsedTime, x0, y0);
        x = pos[0];
        y = pos[1];
    }

    public int getSpriteX(){
        return x - spriteWidth/2;
    }
    public int getSpriteY(){
        return y - spriteHeight/2;
    }

    public boolean isOnscreen(){
        //return x >= 0 && x <= (1280 * 3 / 5) && y >= 0 && y <= 960;
        return startTime != -1 && TimeUnit.SECONDS.convert(System.nanoTime() - startTime, TimeUnit.NANOSECONDS) <= 5;
    }

    public void draw(Graphics g, ImageObserver imageObserver){
        g.drawImage(sprite, getSpriteX(), getSpriteY(), spriteWidth, spriteHeight, imageObserver);
    }
    public Rectangle getRect(){
        return new Rectangle(getSpriteX(), getSpriteY(), spriteWidth, spriteHeight);
    }
}
