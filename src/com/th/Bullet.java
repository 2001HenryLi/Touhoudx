package com.th;

import javax.imageio.ImageIO;
import java.awt.image.*;
import java.io.*;

public class Bullet {
    private long startTime = System.nanoTime();
    private long elapsedTime = 0;

    public BufferedImage sprite;
    public int spriteWidth = 32;
    public int spriteHeight = 32;

    private int x0;
    private int y0;
    private int x;
    private int y;
    private MovePath mp;

    public Bullet(int X, int Y, MovePath MP){
        try {
            sprite = ImageIO.read(new File("Resources/ProjectileSprites/Cusp.png"));
        } catch(IOException e) {
            System.out.println("failed");
            System.exit(-1);
        }
        x = X;
        x0 = X;
        y = Y;
        y0 = Y;
        mp = MP;
    }
    public void update(){
        elapsedTime = System.nanoTime() - startTime;
        int[] pos = mp.move(elapsedTime, x0, y0);
        x = pos[0];
        y = pos[1];
    }

    public int getSpriteX(){
        return x - spriteWidth/2;
    }
    public int getSpriteY(){
        return y - spriteWidth/2;
    }

    public boolean isOnscreen(){
        return x >= 0 && x <= (1280 * 3 / 5) && y >= 0 && y <= 960;
    }
}
