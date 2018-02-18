package com.th;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Boss {

    private long startTime = System.nanoTime();
    private long elapsedTime = 0;
    private long previousShot = 0;

    public PlayPanel pp;

    public double health;
    public int x;
    public int y;
    public int width, height;

    public Boss(PlayPanel p) {
        pp = p;
        health = 10.0;
        x = 500;
        y= 30;
        width = 240;
        height = 240;
    }

    public void makeBullet()
    {
        pp.bossProjectiles.add(new Bullet("Resources\\ProjectileSprites\\BasicShot.png", x, y, 64, 64, new MovePath() {
            @Override
            public int[] move(long t, int x0, int y0) {
                int[] pos = {x0, y0};
                pos[0] = x0 * (int)Math.cos(TimeUnit.SECONDS.convert(t,TimeUnit.NANOSECONDS));
                pos[1] = y0 * (int)Math.cos(TimeUnit.SECONDS.convert(t, TimeUnit.NANOSECONDS));
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
