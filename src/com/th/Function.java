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
	private double constant;
	
	public Function() {
	}
	
	public Function(int f, double c){
		functiontype = f;
		constant = c;
	}
	
	public double getValue(int xval){
		if(functiontype == 1) return Math.sin(constant*xval);
		if(functiontype == 2) return Math.cos(constant*xval);
		if(functiontype == 3) return Math.tan(constant*xval);
		if(functiontype == 5) return constant*xval;
		if(functiontype == 6) return Math.pow(constant*xval, 2);
		if(functiontype == 7) return Math.pow(constant*xval, 3);
		if(functiontype == 8) return Math.pow(constant*xval, 0.5);
		if(functiontype == 9) return Math.log(constant*xval);
		return Math.pow(Math.E, constant*xval);
	}
	
	//detects if coordinates fit function
	public boolean onLine(int xval, int y){
		if(functiontype == 1) return y == Math.sin(constant*xval);
		if(functiontype == 2) return y == Math.cos(constant*xval);
		if(functiontype == 3) return y == Math.tan(constant*xval);
		if(functiontype == 5) return y == constant*xval;
		if(functiontype == 6) return y == Math.pow(constant*xval, 2);
		if(functiontype == 7) return y == Math.pow(constant*xval, 3);
		if(functiontype == 8) return y == Math.pow(constant*xval, 0.5);
		if(functiontype == 9) return y == Math.log(constant*xval);
		return y == Math.pow(Math.E, constant*xval);
	}
	
	public int getFuncType(){ return functiontype; }
	public double getConstant(){ return constant; }
	
	public String getFunction(){ 
		String c = ""+constant;
		if(constant == 0)
			c = "";
		if(functiontype == 1) return "y = sin("+c+"x)";
		if(functiontype == 2) return "y = cos("+c+"x)";
		if(functiontype == 3) return "y = tan("+c+"x)";
		if(functiontype == 5) return "y = "+c+"x";
		if(functiontype == 6) return "y = ("+c+"x)^2";
		if(functiontype == 7) return "y = ("+c+"x)^3";
		if(functiontype == 8) return "y = sqrt("+c+"x)";
		if(functiontype == 9) return "y = ln("+c+"x)";
		return "y = e^("+c+"x)";
	}
}
