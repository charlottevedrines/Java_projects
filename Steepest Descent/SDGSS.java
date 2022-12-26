
public class SDGSS extends SteepestDescent{
	private final double _PHI_ = (1. + Math.sqrt(5))/2.;
	private double maxStep;
	private double minStep;
	private double delta;

	// constructors
	public SDGSS () {
		super(); //ADD THIS?
		maxStep = 1.0;
		minStep = 0.001;
		delta = 0.001;	
	}
	
	public SDGSS(double maxStep , double minStep , double delta) {
		maxStep = 1.0;
		minStep = 0.001;
		delta = 0.001;
	}
	
	//getters
	public double getMaxStep() {return this.maxStep;}
	public double getMinStep() {return this.minStep;}
	public double getDelta() {return this.delta;}
	
	//setters
	public void setMaxStep(double a) {this.maxStep = a;}
	public void setMinStep(double a) {this.minStep = a;}
	public void setDelta(double a) {this.delta = a;}
	
	//other methods
	
	//step size from GSS
	@Override
	public double lineSearch(Polynomial P, double []x) {
		double c = 0.00;
		double [] f_y1 = new double [x.length];
		double [] f_y2 = new double [x.length];
		double [] f_a = new double [x.length];
		double [] f_b = new double [x.length];
		double [] f_c = new double [x.length];
		double alpha = 0.00;
		double [] dir = this.direction(P, x);
		double y1 = 0.00;
		double y2 = 0.00;
		double a = getMinStep();
		double b = getMaxStep();
		
		c = a + (b-a)/this._PHI_;
		
		while(true) {
			
			//C WAS HERE BEFORE
			
			
			for (int i = 0; i < x.length; i++) {
			
				f_a[i] = x[i] + a*dir[i]; //put a plus cause bc direction return - gradient
				f_b[i] = x[i] + b*dir[i];
				f_c[i] = x[i] + c*dir[i];
			}
		
		
			// case where c is bigger than a and b
			if ((P.f(f_c) > P.f(f_b)) || (P.f(f_c) > P.f(f_a))) {
				
				if ( P.f(f_a) > P.f(f_b) ) {
					return b;
				}
				
				else {
					return a;
				}
			}
			
			
			if (b - a < getDelta()) {
				alpha = ( b + a ) / 2;
				return alpha;
			}
		
			
		
			//normal case where c is smaller than a and b
			
			// if [a,c] > [c,b]
			if ((Math.abs(c-a)) > (Math.abs(b-c))) {
				
				y1 = a + (c-a)/this._PHI_;
				
				for (int i =0; i < x.length; i++) {
					f_y1[i] = x[i] + y1*dir[i]; 
				}
				
				
				if (P.f(f_y1) < P.f(f_a) && P.f(f_y1) < P.f(f_c)) {
					b = c;
					c = y1; 
					
				}
				
				else if (P.f(f_y1) < P.f(f_a) && P.f(f_y1) > P.f(f_c)) {
					a = y1;
					
					
				}
				
				else {
					System.out.println("weird1");
				}
				
			} // end if [a,c] > [c,b]
			
			//if [a,c] < [c,b]
			else {
				
				y2 = b - (b - c) / this._PHI_;
				
				for (int i =0; i < x.length; i++) {
					f_y2[i] = x[i] + y2*dir[i]; 
				}
				
				
				if ( P.f(f_y2) < P.f(f_c)) {
					a=c;
					c = y2;
					
				}
				
				else if ( P.f(f_y2) > P.f(f_c)) {
					b= y2;
				}
				
				else {
					System.out.println("weird2");
				}
				
			 }// end if [a,c] < [c,b]
			
			
	} // end of while loop

}
	
	
	public boolean getParamsUser() {
		setHasResults ( true);

		System.out.print("Set parameters for SD with a golden section line search:"+"\n");
		
		double max_temp =0.00;
		
		max_temp = Pro5_vedrines.getDouble("Enter GSS maximum step size (0 to cancel): ", 0.00, Double.MAX_VALUE);
		//If 0 is entered, inputting process cancelled -> function is exited 
		
		if (max_temp == 0.00) {
			System.out.println("\n"+"Process canceled. No changes made to algorithm parameters."+"\n");
			setHasResults ( false);
		}
		
		//Otherwise proceed 
		else {
			double min_temp =0.00;
			
			min_temp = Pro5_vedrines.getDouble("Enter GSS minimum step size (0 to cancel): ", 0.00, max_temp);
			
			//If 0 is entered, inputting process cancelled -> function is exited 
			if (min_temp == 0.00) {
				System.out.println("\n"+"Process canceled. No changes made to algorithm parameters."+"\n");
				setHasResults ( false);
			}
			
			//Otherwise proceed 
			else {
				
				double delta_temp =0.00;
				
				delta_temp = Pro5_vedrines.getDouble("Enter GSS delta (0 to cancel): ", 0.00 , Double.MAX_VALUE );
				
				//If 0 is entered, inputting process cancelled -> function is exited 
				if (delta_temp == 0.00) {
					System.out.println("\n"+"Process canceled. No changes made to algorithm parameters."+"\n");
					setHasResults ( false);
				}
				
				
				else {
					
					//Set all values
					super.getParamsUser();
					
					if (hasResults ()  == true) {
						Pro5_vedrines.has_run = false;
					
						//Boolean setHasResults () to see if parameters for steepest descent are set -> helpful to have in the 
						// print function to determine if there are user inputed parameters to display
						setHasResults (true); //not sure
						setMaxStep(max_temp);
						setMinStep(min_temp);
						setDelta(delta_temp);
						System.out.println("\n" + "Algorithm parameters set!" + "\n");
					}
				}
			}
		}

		return true;
	}

	//print parameters
	@Override
	public void print() {
		System.out.print("SD with a golden section line search:" +"\n");
		super.print();
		System.out.println("GSS maximum step size: "+ this.maxStep + "\n"
				+ "GSS minimum step size: " + this.minStep + "\n"
				+ "GSS delta: " +this.delta+"\n");
	}

}
