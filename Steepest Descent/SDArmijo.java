
public class SDArmijo extends SteepestDescent {
	public double maxStep;
	private double beta;
	private double tau; 
	private int K; //max nb of iterations
	
	//constructor
	public SDArmijo() {
		super();
		maxStep = 1.0;
		beta = 1.0E-4;
		tau = 0.5;
		K = 10 ;
	}
	
	public SDArmijo(double maxStep, double beta, double tau, int K) {
		super(); //ADD THIS?
		this.maxStep = maxStep;
		this.beta = beta;
		this.tau = tau;
		this.K = K ;
	}
	
	//getters
	public double getMaxStep() {return this.maxStep;}
	public double getBeta() {return this.beta;}
	public double getTau() {return this.tau;}
	public int getK() {return this.K;}
	
	//setters
	public void setMaxStep(double a)  {this.maxStep = a;}
	public void setBeta(double a) {this.beta = a;}
	public void setTau(double a) {this.tau = a;}
	public void setK(int a) {this.K = a;}
	
	
	//object
	Pro5_vedrines main = new Pro5_vedrines();
	
	//other methods
	@Override
	public double lineSearch(Polynomial P, double []x) { 
		double X[] = new double [x.length];
		double LHS =0.00;
		double RHS = 0.00;
		double alpha = this.getMaxStep();
		int counter = 0;
		
		while (counter < K) {
			
			
			//find left hand side
			for (int l=0; l < x.length; l++) {
				X[l] = x[l] - alpha * P.gradient(x)[l]; 
			}
			LHS = P.f(X);
			
			//find right hand side
			RHS = P.f(x) - alpha * beta * Math.pow(P.gradientNorm(x),2);
			
			//break out of loop if Armijo condition is met
			if (LHS <= RHS) {
				return alpha;
			}
			
			//otherwise repeat with a different alpha
			else {
				alpha = alpha * tau;
				counter++;
			}
		}
		
		
		main.Armijo_converge = true;
		
		//If Armijo does converge return a value that could not usually be returned
		return Double.MAX_VALUE;
		// return alpha;
	}
	
	@Override
	public boolean getParamsUser() {
		setHasResults ( true);
		System.out.print("Set parameters for SD with an Armijo line search:"+"\n");
		double max =0.0;
		
		max = Pro5_vedrines.getDouble("Enter Armijo max step size (0 to cancel): ", 0.00, Double.MAX_VALUE);
		//If 0 is entered, inputting process cancelled -> function is exited 
		
		if (max == 0.00) {
			System.out.println("\n"+"Process canceled. No changes made to algorithm parameters."+"\n");
			setHasResults ( false);
		}
		
		//Otherwise proceed 
		else {
			double beta_temp =0.00;
			beta_temp = Pro5_vedrines.getDouble("Enter Armijo beta (0 to cancel): ", 0.00, 1.00);
			
			//If 0 is entered, inputting process cancelled -> function is exited 
			if (beta_temp == 0.00) {
				System.out.println("\n"+"Process canceled. No changes made to algorithm parameters."+"\n");
				setHasResults ( false);
			}
			
			//Otherwise proceed 
			else {
				
				double tau_temp =0.00;
				
				tau_temp = Pro5_vedrines.getDouble("Enter Armijo tau (0 to cancel): ", 0.00 ,1.00);
				
				//If 0 is entered, inputting process cancelled -> function is exited 
				if (tau_temp == 0.00) {
					System.out.println("\n"+"Process canceled. No changes made to algorithm parameters."+"\n");
					setHasResults ( false);
				}
				
				//Otherwise proceed 
				else {
					int K_temp =0;
	
					//Formatting the input of starting points
					K_temp = Pro5_vedrines.getInteger("Enter Armijo K (0 to cancel): ", 0, Integer.MAX_VALUE);
					
					if (K_temp == 0) {
						System.out.println("\n"+"Process canceled. No changes made to algorithm parameters."+"\n");
						setHasResults (false);
					}
					else {
						
						
						super.getParamsUser();
						
						if (hasResults ()  == true) {
							Pro5_vedrines.has_run = false;
							//Boolean setHasResults () to see if parameters for steepest descent are set -> helpful to have in the 
							// print function to determine if there are user inputed parameters to display
							setHasResults ( true); //CHANGED THIS
							//Set all values
							setMaxStep(max);
							setBeta(beta_temp);
							setTau(tau_temp);
							setK(K_temp);
							
							System.out.println("\n" + "Algorithm parameters set!" + "\n");
						}
	
					}
					
				}
			}
		}

		return true;
	}
	
	// print parameters
	@Override 
	public void print() {
		System.out.println("\n"+ "\n"+ "SD with an Armijo line search:");
		super.print();
		System.out.println("Armijo maximum step size: "+ this.maxStep + "\n"
				+ "Armijo beta: " + this.beta + "\n"
				+ "Armijo tau: " +this.tau+"\n"
				+ "Armijo maximum iterations: "+ this.K + "\n");
		
	}
}
