
public class SDFixed extends SteepestDescent {
	//SteepestDescent SD = new SteepestDescent(); //DO I NEED THIS
	
	
	private double alpha; // fixed step size
	
	// constructors
	public SDFixed () {
		super();
		this.alpha= 0.01;
	}
	
	public SDFixed(double alpha) {
		super(); //ADD THIS?
		this.alpha = alpha;
	}
	
	// getters
	public double getAlpha () {return this.alpha;}
	
	
	// setters
	public void setAlpha(double a)  {this.alpha = a;}
	
	
	// other methods
	
	// fixed step size
	@Override
	public double lineSearch(Polynomial P, double [] x)   {
		return getAlpha();
	}
	
	
	// get algorithm parameters from user void print()
	@Override
	public boolean getParamsUser() {
		
		setHasResults ( true);
		
		double ss = 0.00;
		
		
		System.out.print("\n"+"Set parameters for SD with a fixed line search:"+"\n");
		
		ss = Pro5_vedrines.getDouble("Enter fixed step size (0 to cancel): ", 0.00, Double.MAX_VALUE);
		//If 0 is entered, inputting process cancelled -> function is exited 
		
		if (ss == 0.00) {
			System.out.println("\n"+"Process canceled. No changes made to algorithm parameters."+"\n");
			setHasResults ( false);
		}
		
		else {
		
			
			
			super.getParamsUser();
			
			
			
							
			if (hasResults ()  == true) {
				Pro5_vedrines.has_run = false;
				//Set all values
				setAlpha(ss);
				System.out.println("\n" + "Algorithm parameters set!" + "\n");
				setHasResults ( true);
			}
		}
		
		return true;
	}
	
	
	// print parameters
	@Override //USING THIS CORRECTLY?
	public void print() {
		System.out.print("\n"+ "SD with a fixed line search:\n");
		super.print();
		System.out.print("Fixed step size (alpha): " + alpha);
	}
	

	
	
}
