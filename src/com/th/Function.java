package com.th;

/*types of functions:
	 * 4) e^x
	 * 6) x^2
	 * 7) x^3
	 * 
	 * add more if you want to
	 */

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Function{
    private final double SCALE = 83.3;

    private PlayPanel pp;
    private long startTime = -1;
    private long elapsedTime = 0;
    private long previousEq = 0;
    private long equationBuffer = TimeUnit.NANOSECONDS.convert(5, TimeUnit.SECONDS);
	private BufferedImage sprite;

    private Equation sin = new Equation() {
        @Override
        public int[] getCoord(long t) {
            int x = (int)(t * ScaleDimentions.PPWIDTH / equationBuffer);
            int y = (int)(Math.sin(x * 2 * Math.PI / (ScaleDimentions.PPWIDTH / 4))*SCALE);
            int[] pos = {x, y};
            return pos;
        }

        @Override
        public String toString() {
            return "y = sin(x)";
        }
    };
    private Equation cos = new Equation() {
        @Override
        public int[] getCoord(long t) {
            int x = (int)(t * ScaleDimentions.PPWIDTH / equationBuffer);
            int y = (int)(Math.cos(x * 2 * Math.PI / (ScaleDimentions.PPWIDTH / 4))*SCALE);
            int[] pos = {x, y};
            return pos;
        }

        @Override
        public String toString() {
            return "y = cos(x)";
        }
    };
    private Equation tan = new Equation() {
        @Override
        public int[] getCoord(long t) {
            int x = (int)(t * ScaleDimentions.PPWIDTH / equationBuffer);
            int y = (int)(Math.tan(x * 2 * Math.PI / (ScaleDimentions.PPWIDTH / 4))*SCALE);
            int[] pos = {x, y};
            return pos;
        }

        @Override
        public String toString() {
            return "y = tan(x)";
        }
    };
    private Equation linear = new Equation() {
        @Override
        public int[] getCoord(long t) {
            int x = (int)(t * ScaleDimentions.PPWIDTH / equationBuffer);
            int y = x;
            int[] pos = {x, y};
            return pos;
        }
        @Override
        public String toString() {
            return "y = x";
        }
    };
    private Equation sqrt = new Equation() {
        @Override
        public int[] getCoord(long t) {
            int x = (int)(t * ScaleDimentions.PPWIDTH / equationBuffer);
            int y = (int) (Math.sqrt(x) * SCALE/4);
            int[] pos = {x, y};
            return pos;
        }
        @Override
        public String toString() {
            return "y = sqrt(x)";
        }
    };
    private Equation ln = new Equation() {
        @Override
        public int[] getCoord(long t) {
            int x = (int)(t * ScaleDimentions.PPWIDTH / equationBuffer);
            int y = (int) (Math.log(x) * SCALE/2);
            int[] pos = {x, y};
            return pos;
        }
        @Override
        public String toString() {
            return "y = log(x)";
        }
    };

	private Equation[] eqs = {sin, cos, tan, linear, sqrt, ln};
	private int eqIndex;

	public Function(PlayPanel p){
	    pp = p;
        sprite = ImageLoader.openImage("ProjectileSprites/Graph.png");
        eqIndex = (int)(Math.random() * eqs.length);
	}
	
	public String getFunction(){ 
		return eqs[eqIndex].toString();
	}

	public void update(){
        if(startTime == -1) startTime = System.nanoTime();
        elapsedTime = System.nanoTime() - startTime;

        if(elapsedTime > previousEq + 2 * equationBuffer) reset();
        if(elapsedTime - previousEq >= 0){
            int[] coords = eqs[eqIndex].getCoord(elapsedTime - previousEq);
            synchronized (pp.points) {
                pp.points.add(new Coordinate(sprite, coords[0], ScaleDimentions.HEIGHT - 40 - coords[1], 16, 16, new MovePath() {
                    @Override
                    public int[] move(long t, int x0, int y0) {
                        int[] pos = {x0, y0};
                        if (t > elapsedTime - previousEq + equationBuffer) {
                            pos[0] = -100;
                            pos[1] = -100;
                        }
                        return pos;
                    }
                }));
            }
        }
	}
	public void reset(){
        startTime = System.nanoTime();
        previousEq = 0;
        synchronized (pp.points){ pp.points.clear(); }
        eqIndex = (int)(Math.random() * eqs.length);
    }
}
