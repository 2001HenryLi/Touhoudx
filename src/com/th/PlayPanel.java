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
	
	public Function(int f, int c){
		functiontype = f;
		constant = c;
	}
	
	//testing method
	public static void main(String [] args){
		Function f = new Function(4,5);
		System.out.println(f.getFunction());
		System.out.println(f.onLine(0.3, 4));
	}
	
	//returns values in pixels
	public double getValue(int xval){
		if(functiontype == 1) return (int)(Math.sin(constant*xval)*83.3);
		if(functiontype == 2) return (int)(Math.cos(constant*xval)*83.3);
		if(functiontype == 3) return (int)(Math.tan(constant*xval)*83.3);
		if(functiontype == 5) return (int)(constant*xval*83.3);
		if(functiontype == 6) return (int)(Math.pow(constant*xval, 2)*83.3);
		if(functiontype == 7) return (int)(Math.pow(constant*xval, 3)*83.3);
		if(functiontype == 8) return (int)(Math.pow(constant*xval, 0.5)*83.3);
		if(functiontype == 9) return (int)(Math.log(constant*xval)*83.3);
		return (int)(Math.pow(Math.E, constant*xval)*83.3);
	}
	
	//detects if coordinates fit function
	public boolean onLine(double xval, int y){
		if(functiontype == 1) return y == (int)Math.sin(constant*xval);
		if(functiontype == 2) return y == (int)Math.cos(constant*xval);
		if(functiontype == 3) return y == (int)Math.tan(constant*xval);
		if(functiontype == 5) return y == (int)constant*xval;
		if(functiontype == 6) return y == (int)Math.pow(constant*xval, 2);
		if(functiontype == 7) return y == (int)Math.pow(constant*xval, 3);
		if(functiontype == 8) return y == (int)Math.pow(constant*xval, 0.5);
		if(functiontype == 9) return y == (int)Math.log(constant*xval);
		return y == (int)Math.pow(Math.E, constant*xval);
	}
	
	public int getFuncType(){ return functiontype; }
	public double getConstant(){ return constant; }
	
	public String getFunction(){ 
		String c = ""+constant;
		if(constant == 1)
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
