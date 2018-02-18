package com.th;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Boss {

    private long startTime = System.nanoTime();
    private long elapsedTime = 0;
    private long previousShot = 0;

    public PlayPanel pp;

    private double health;
    private int xpos;
    private int ypos;
    public int width, height;

    public Boss(PlayPanel p) {
        pp = p;
        health = 10.0;
        xpos = 500;
        ypos = 30;
        width = 240;
        height = 240;
    }

    //getters and setters
    public double getHealth(){ return health; }
    public int getXPos(){ return xpos; }
    public int getYPos(){ return ypos; }

    public void setHealth(double h){ health = h; }
    public void setXPos(int x){ xpos = x; }
    public void setYPos(int y){ ypos = y; }

    public void makeBullet()
    {
        pp.bossProjectiles.add(new Bullet("Resources\\ProjectileSprites\\BasicShot.png", xpos, ypos, 64, 64, new MovePath() {
            @Override
            public int[] move(long t, int x0, int y0) {
                int[] pos = {x0, y0};
                pos[0] = x0;
                pos[1] = y0 + (int)(TimeUnit.MILLISECONDS.convert(t, TimeUnit.NANOSECONDS)/30);
                return pos;
            }
        }));
    }

    public void update()
    {
        elapsedTime = System.nanoTime()-startTime;
        if(elapsedTime > previousShot+1000000000) {
            previousShot += 1000000000;
            makeBullet();
        }
    }

}
