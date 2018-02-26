package com.th;

public class ScaleDimentions {
    public static final double MASTER_SCALE = 1.0;
    public static final int WIDTH = (int)(1280 * MASTER_SCALE);
    public static final int PPWIDTH = WIDTH * 3 / 5;
    public static final int UIWIDTH = WIDTH * 2 / 5;
    public static final int HEIGHT = (int)(960 * MASTER_SCALE);
    public static double scale(double d){
        return d*MASTER_SCALE;
    }
}
