package com.th;

public class Coordinate {
	
	private int x;
	private int y;
	
	public Coordinate(int one, int two) {
		x = one;
		y = two;
	}

	public int getX(){ return x; }
	public int getY(){ return y; }
	public void setX(int xpos){ x = xpos;}
	public void setY(int ypos){ y = ypos;}
}
