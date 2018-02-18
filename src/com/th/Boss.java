package com.th;
public class Boss {

    private double health;
    private int xpos;
    private int ypos;

    private final int SCREENWIDTH = 960;

    public Boss() {
        health = 10.0;
        xpos = 960;
        ypos = 30;
    }

    //getters and setters
    public double getHealth(){ return health; }
    public int getXPos(){ return xpos; }
    public int getYPos(){ return ypos; }

    public void setHealth(double h){ health = h; }
    public void setXPos(int x){ xpos = x; }
    public void setYPos(int y){ ypos = y; }
}
