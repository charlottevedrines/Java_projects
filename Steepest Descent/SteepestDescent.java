import java.util.ArrayList;
import java.util.Arrays;

	public class SteepestDescent {
		private double eps ; // tolerance
		private int maxIter ; // maximum number of iterations
		private double stepSize ; // step size alpha //NO STEP SIZE
		private double x0 ; // starting point
		private ArrayList <double[]> bestPoint = new ArrayList<>(); // best point found for all polynomial
		private double[] bestObjVal ; // best obj fn value found for all polynomial
		private double[] bestGradNorm ; // best gradient norm found for all polynomial
		private long[] compTime ; // computation time needed for all polynomial
		private int[] nIter ; // no. of iterations needed for all polynomial
		private boolean resultsExist ; // whether or not results exist for all polynomial
		
		public double average_norm;
		public double average_iter;
		public double average_comp;
	
		Pro5_vedrines main = new Pro5_vedrines();
	
	
		// constructors
		public SteepestDescent () {
			eps = 0.001;
			maxIter = 100;
			stepSize = 0.01;
			x0 = 1.0;
			
		}
	
		
		public SteepestDescent ( double eps , int maxIter , double stepSize , double x0 ) {
			this.eps = eps;
			this.maxIter = maxIter;
			this.stepSize = stepSize;
			this.x0 = x0;
		}
		
		// getters
		public double getEps ()  {return this.eps;}
		public int getMaxIter ()  {return this.maxIter;}
		public double getStepSize ()  {return this.stepSize;}
		public double getX0 ()  {return this.x0;}
		public double [] getBestObjVal ()  {return this.bestObjVal;}
		public double [] getBestGradNorm () {return this.bestGradNorm;}
		public double [] getBestPoint (int i) {return this.bestPoint.get(i);}
		public int [] getNIter ()  {return this.nIter;} 
		public long [] getCompTime () {return this.compTime;} 
		public boolean  hasResults () {return this.resultsExist;}
		
		
		
		// setters
		public void setEps ( double a ) {this.eps = a;}
		public void setMaxIter ( int a ) {this.maxIter = a;}
		public void setStepSize ( double a ) {this.stepSize = a;}
		public void setX0 ( double a ) {this.x0 = a;}
		public void setBestObjVal ( int i, double a ) {this.bestObjVal[i]=a;}
		public void setBestGradNorm (int i, double a ) {this.bestGradNorm[i] = a;}
		public void setBestPoint ( int i, double [] a ) {this.bestPoint.set(i,a);}
		public void setCompTime (int i, long a ) {this.compTime[i] = a;}
		public void setNIter ( int i, int a ) {this.nIter[i] = a;}
		public void setHasResults ( boolean a ) {this.resultsExist = a;}
	
		
		// initialize member arrays to correct size
		public void init ( ArrayList < Polynomial > P) {
			this.bestObjVal = new double[P.size()];
			this.bestGradNorm = new double[P.size()];
			this.nIter = new int [P.size()];
			this.compTime = new long[P.size()];
			for (int k = 0; k < P.size(); k++) {
				double[] array = new double[((Polynomial)P.get(k)).getN()];
				this.bestPoint.add(k, array);		
			}
		}
		
		
		public void starting_values() {
			setEps (0.001 );
			setMaxIter ( 100 ) ;
			setStepSize ( 0.05 );
			setX0(1.0);
		}
	
		//find the next step size
		public double lineSearch(Polynomial P, double[]x) {
			return getStepSize();
		}
		
		// find the next direction
		public double [] direction ( Polynomial P , double [] x ) {
			double arr[] = new double[P.getN()];
			
			for (int i = 0; i < P.getN(); i++) {
				
				arr[i] = -1 * P.gradient(x)[i];
			}
			
			return arr;
		}

		// Get steepest descent parameters from user 
		public boolean getParamsUser() {
		
			double ep = 0.00;
			
			ep = Pro5_vedrines.getDouble("Enter tolerance epsilon (0 to cancel): ", 0.00, Double.MAX_VALUE);
			
			//If 0 is entered, inputting process cancelled -> function is exited 
			if (ep == 0.00) {
				System.out.println("\n"+"Process canceled. No changes made to algorithm parameters."+"\n");
				setHasResults ( false);
			}
			
			//Otherwise proceed 
			else {
				int nb_iter =0;
				
				nb_iter = Pro5_vedrines.getInteger("Enter maximum number of iterations (0 to cancel): ", 0,10000);
				
				//If 0 is entered, inputting process cancelled -> function is exited 
				if (nb_iter == 0) {
					System.out.println("\n"+"Process canceled. No changes made to algorithm parameters."+"\n");
					setHasResults ( false);
				}
				
				//Otherwise proceed 
				else {
					double x0 = 0.00;
					//Formatting the input of starting points
					x0 = Pro5_vedrines.getDouble("Enter value for starting point (0 to cancel): ", -Double.MAX_VALUE, Double.MAX_VALUE);
					
					if (x0 == 0.00) {
						System.out.println("\n"+"Process canceled. No changes made to algorithm parameters."+"\n");
						setHasResults (false);
					}
					else {
						//Set all values
						setEps(ep);
						setMaxIter(nb_iter);
						setX0(x0);
						
						//Boolean setHasResults () to see if parameters for steepest descent are set -> helpful to have in the 
						// print function to determine if there are user inputed parameters to display
						//setHasResults ( true);
	
					}
					
				}
			}

		return true;
	}
		
		
		// print algorithm parameters
		public void print() {
			
			//Print the predefined parameters if there are no user defined parameters 
			System.out.println(  "Tolerance (epsilon): " + getEps ()+ "\n"
					+ "Maximum iterations: " + getMaxIter ()+ "\n"
					+ "Starting point (x0): " + getX0());
			
		}
	
	
		// run the steepest descent algorithm
		public void run ( int i , Polynomial P ) {
			
			try {
				
				//exit of the function in the case where no polynomial has been recorded
				if (Pro5_vedrines.poly_entered = false) {
					System.out.print("\n"
							+ "ERROR: No polynomial functions are loaded!\n"
							+ "\n");
					return;
				}
						
				//start of the comp time
				long start = System.currentTimeMillis();
								
				//Initialize variables and array
				int nth_iteration = 0;
				main.has_run = true;
				int display = i+1;
				boolean flag = true;
				double [] curr = new double[P.getN()];
				double alpha_temp = 0.00;
				double next[] = new double[curr.length];
				
				
				//Initializing the curr to the starting point (x0) 
				for (int ind = 0; ind < P.getN(); ind++) { 
					curr[ind] = getX0();
				}
				
				//Loop with the first stopping criteria: the while loop will run until the maximum number of iterations is hit
				while (nth_iteration < getMaxIter() && flag ) {
					
					main.Armijo_converge = false; 
				
					
					double norm = P.gradientNorm(curr); 
					
					//Second stopping criteria, if the norm is smaller than epsilon, the while loop is broken out of 
					if (norm <=  getEps ()) {
						flag = false;
						break;
						
					}
					else {
						nth_iteration ++;
					
					
						alpha_temp = 0.00;
						alpha_temp = lineSearch(P, curr);
						
						
						//If alpha returned is Double.MAX_VALUE then set all values as they would for the first iteration
						if (alpha_temp == Double.MAX_VALUE) {
							flag = false;
							double [] temp = new double[P.getN()];
							for (int b = 0; b < P.getN(); b++) { 
								temp[b] = getX0();
							}
							
							System.out.println("   Armijo line search did not converge!");
							setBestPoint(i,temp);
							setBestGradNorm(i,P.gradientNorm(temp));
							setBestObjVal(i, P.f(temp));
							setNIter(i, nth_iteration);
							long elapsedTime = System.currentTimeMillis() - start;
							setCompTime(i, elapsedTime );
							break;
							
							
						}
						
						
						else {
							//Calculation if the current coord
							for (int j=0; j < curr.length; j++ ) {
								next[j] = curr[j] + alpha_temp* this.direction(P,curr)[j]; 
							}
							curr = next;
						}
				
						
						
						//End of comp time 
						long elapsedTime = System.currentTimeMillis() - start;
						setCompTime(i, elapsedTime );
									
						
						
					} // end of else statement (opposed to if norm < eps)
					
					} // end of while loop
				
					
					if (main.Armijo_converge == false) {
						setBestGradNorm(i,P.gradientNorm(curr));
						setBestPoint(i,curr);
						setBestObjVal(i, P.f(curr));
						setNIter(i, nth_iteration);
					}
					
					System.out.print("Polynomial " + display + " done in " + getCompTime()[i] + "ms.\n");
					
			}
			
			
		
			catch (Exception e) {
				System.out.print("error");	
			}
			
	
		}
		
		
		// print final results for all polynomials
		public void printAll() {
		}
		
	    // print iteration results , column header optional
		public void printSingleResult(int i, boolean rowOnly) {
			//If the run function hasn't been run then the number of iterations will be 0 so exit function
			if (main.nb_polynomials ==0) {
				System.out.print("\n"
						+ "ERROR: No results exist!\n"
						+ "\n");
				return;
			}
			
			int display = i+1;
			System.out.printf("%8d%13.6f%13.6f%9d%17d   ",  display, this.bestObjVal[i], this.bestGradNorm[i], this.nIter[i], this.compTime[i]);
			
			//Loop to print the best points
			for (int j=0; j < this.bestPoint.get(i).length; j++ ) {
				
				if (j==0) {
					System.out.print("   ");
					System.out.printf("%.4f", this.bestPoint.get(i)[j]);
				}
				
				else {
					System.out.print(", ");
					System.out.printf("%.4f",getBestPoint(i)[j]);
				}
				
			}
			
		}
	
		// print statistical summary of results	
		public void printStats() {
			System.out.println("---------------------------------------------------\n"
					+ "          norm(grad)       # iter    Comp time (ms)\n"
					+ "---------------------------------------------------");
			
			//AVERAGE calculation
			double av_norm =0;
			double av_iter =0;
			double av_comp_time =0;
			
			//1st calculate sum
			double sum_norm=0;
			double sum_iter=0;
			long sum_comp_time=0;
			
			for (int m = 0; m < this.bestGradNorm.length; m++) {
				sum_norm += this.bestGradNorm[m];
				sum_iter += this.nIter[m];
				sum_comp_time += this.compTime[m];
			}
			
			av_norm = sum_norm/(this.bestGradNorm.length);
			av_iter = sum_iter/(this.nIter.length);
			av_comp_time = sum_iter/(this.compTime.length);
			
			
			//Standard deviation calculation
			
			//Min and max  calculation
			double min_norm = this.bestGradNorm[0];
			int min_iter = this.nIter[0];
			long min_comp_time = this.compTime[0];
			
			double max_norm = this.bestGradNorm[0];
			int max_iter = this.nIter[0];
			long max_comp_time = this.compTime[0];
			
			for (int w =0 ;w < this.bestGradNorm.length; w++) {
				
				if (this.bestGradNorm[w] < min_norm) {
					min_norm = this.bestGradNorm[w];
				}
				
				if (this.nIter[w] < min_iter) {
					min_iter = this.nIter[w];
				}
				
				if (this.compTime[w] < min_comp_time) {
					min_comp_time = this.compTime[w];
				}
				
				
				if (this.bestGradNorm[w] > max_norm) {
					max_norm = this.bestGradNorm[w];
				}
				
				if (this.nIter[w] > max_iter) {
					max_iter = this.nIter[w];
				}
				
				if (this.compTime[w] > max_comp_time) {
					max_comp_time = this.compTime[w];
				}
				
				
			}
			
			double i_norm =0;
			double i_iter =0;
			double i_comp =0;
			
			for (int l = 0 ; l < this.bestGradNorm.length; l++) {
				i_norm += Math.pow((this.bestGradNorm[l] - av_norm),2);
				i_iter += Math.pow((this.nIter[l] - av_iter),2);
				i_comp += Math.pow((this.compTime[l] - av_comp_time),2);
			}
			
			double dev_norm = Math.sqrt(i_norm/(this.bestGradNorm.length-1));
			double dev_iter = Math.sqrt(i_iter/(this.nIter.length-1));
			double dev_comp = Math.sqrt(i_comp/(this.compTime.length-1));
			
			//make the average norm and number of iterations accessible for the compare function
			average_norm= 0.00;
			average_iter = 0.00;
			average_comp = 0.00;
			average_norm = av_norm;
			average_iter = av_iter;
			average_comp = av_comp_time;
			
			
			System.out.format("Average%13.3f%13.3f%18.3f\n", av_norm, av_iter, av_comp_time);
			System.out.format("St Dev%14.3f%13.3f%18.3f\n", dev_norm, dev_iter, dev_comp);
			System.out.format("Min%17.3f%13d%18d\n", min_norm, min_iter, min_comp_time);
			System.out.format("Max%17.3f%13d%18d\n\n", max_norm, max_iter, max_comp_time);
			
		}
	
	
		
		}
		
		
		
		
	
	
	
	
	
	
	