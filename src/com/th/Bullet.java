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

    private int x;
    private int y;
    public Runnable move;

    public Bullet(int X, int Y){
        try {
            sprite = ImageIO.read(new File("Resources/CharacterSprites/cirno.png"));
        } catch(IOException e) {
            System.out.println("failed");
            System.exit(-1);
        }
        x = X;
        y = Y;
    }
    public void update(){
        elapsedTime = System.nanoTime();

    }
    public void move(long t){


    }
}
