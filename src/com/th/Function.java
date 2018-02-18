package com.th;

/*types of functions:
	 * 1) sinx
	 * 2) cosx
	 * 3) tanx
	 * 4) e^x
	 * 5) x
	 * 6) x^2
	 * 7) x^3
	 * 8) sqrt(x)
	 * 9) lnx
	 * 
	 * add more if you want to
	 */

public class Function {
	
	private int functiontype;
	private int constant;
	private int constant2;
	private final double BOXHEIGHT = 83.3;
	
	public Function(){
		chooseRandom();
	}
	
	public void chooseRandom(){
		functiontype = (int)(Math.random()*9+1);
		constant = (int)(Math.random()*5+1);
		constant2 = (int)(Math.random()*2+1);
	}
	
	//returns values in pixels
	public double getValue(double xval){
		if(functiontype == 1) return constant2 * (int)(Math.sin(constant*xval)*BOXHEIGHT);
		if(functiontype == 2) return constant2 * (int)(Math.cos(constant*xval)*BOXHEIGHT);
		if(functiontype == 3) return constant2 * (int)(Math.tan(constant*xval)*BOXHEIGHT);
		if(functiontype == 5) return (int)(constant*xval*BOXHEIGHT);
		if(functiontype == 6) return (int)(Math.pow(constant*xval, 2)*BOXHEIGHT);
		if(functiontype == 7) return (int)(Math.pow(constant*xval, 3)*BOXHEIGHT);
		if(functiontype == 8) return constant2 * (int)(Math.pow(constant*xval, 0.5)*BOXHEIGHT);
		if(functiontype == 9) return constant2 * (int)(Math.log(constant*xval)*BOXHEIGHT);
		return (int)(Math.pow(Math.E, constant*xval)*BOXHEIGHT);
	}
	
	//detects if coordinates fit function
	public boolean onLine(double xval, int y){
		if(functiontype == 1) return y == constant2 * (int)Math.sin(constant*xval);
		if(functiontype == 2) return y == constant2 * (int)Math.cos(constant*xval);
		if(functiontype == 3) return y == constant2 * (int)Math.tan(constant*xval);
		if(functiontype == 5) return y == (int)constant*xval;
		if(functiontype == 6) return y == (int)Math.pow(constant*xval, 2);
		if(functiontype == 7) return y == (int)Math.pow(constant*xval, 3);
		if(functiontype == 8) return y == constant2 * (int)Math.pow(constant*xval, 0.5);
		if(functiontype == 9) return y == constant2 * (int)Math.log(constant*xval);
		return y == (int)Math.pow(Math.E, constant*xval);
	}
	
	public int getFuncType(){ return functiontype; }
	public double getConstant(){ return constant; }
	
	public String getFunction(){ 
		String c = ""+constant;
		String c2 = ""+constant;
		if(constant == 1) c = "";
		if(constant2 == 1) c2 = "";

		if(functiontype == 1) return "y = " + c2 + "sin("+c+"x)";
		if(functiontype == 2) return "y = " + c2 + "cos("+c+"x)";
		if(functiontype == 3) return "y = " + c2 + "tan("+c+"x)";
		if(functiontype == 5) return "y = " +c + "x";
		if(functiontype == 6) return "y = ("+c+"x)^2";
		if(functiontype == 7) return "y = ("+c+"x)^3";
		if(functiontype == 8) return "y = " + c2 + "sqrt("+c+"x)";
		if(functiontype == 9) return "y = " + c2 + "ln("+c+"x)";
		return "y = e^("+c+"x)";
	}
}
