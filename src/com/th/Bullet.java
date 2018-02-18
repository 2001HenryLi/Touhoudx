package com.th;

import javax.imageio.ImageIO;
import java.awt.image.*;
import java.io.*;

public class Bullet {
    private long startTime = System.nanoTime();
    private long elapsedTime = 0;

    public BufferedImage sprite;
    public int spriteWidth = 8;
    public int spriteHeight = 8;

    private int x0;
    private int y0;
    private int x;
    private int y;
    private MovePath mp;

    public Bullet(int X, int Y, MovePath MP){
        try {
            sprite = ImageIO.read(new File("Resources/CharacterSprites/cirno.png"));
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
}
