import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;

public class Pro5_vedrines {

	public static BufferedReader cin = new BufferedReader (new InputStreamReader ( System.in ) );
	static boolean poly_entered = false;
	static boolean has_run =false;
	static int nb_polynomials =0;
	static int nb_variables_first_line = 0;
	static boolean consistent = false;
	static boolean Armijo_converge = false;
	
	
			
	public static void main(String[] args) throws NumberFormatException, IOException {
		ArrayList < Polynomial> P = new ArrayList < Polynomial >() ;
		Polynomial Polynomial_obj = new Polynomial();
		SteepestDescent SD = new SteepestDescent();
		SDFixed SDF =  new SDFixed();
		SDArmijo SDA = new SDArmijo();	
		SDGSS SDG = new SDGSS();
		
		
		while (true) {
			display_menu();
			
			System.out.print( "Enter choice: ");
			
			//reading in a string
			String option = cin.readLine();
			
			if (option.equals("L") == true || option.equals("l") == true ) {
				poly_entered = loadPolynomialFile(P);	
				has_run = false;
			}
			
			else if (option.equals("F") == true || option.equals("f") == true ) {
				printPolynomials(P) ;
			}
			
			else if (option.equals("C") == true || option.equals("c") == true ) {
				P.clear();
				System.out.print( "\n"
						+ "All polynomials cleared.\n"
						+ "\n");
				poly_entered = false;
				nb_polynomials =0;
				has_run = false;
			}
			
			else if (option.equals("S") == true || option.equals("s") == true ) {
				getAllParams(SDF,SDA,SDG);
			}
			
			else if (option.equals("P") == true || option.equals("p") == true ) {
				 SDF.print();
				 SDA.print();
				 SDG.print();
			}
			else if (option.equals("R") == true || option.equals("r") == true ) {
				System.out.print("\n");
				if (nb_polynomials ==0) {
					System.out.print("ERROR: No polynomial functions are loaded!\n"
							+ "\n");
				}
				
				else {
					SDF.init(P);
					SDA.init(P);
					SDG.init(P);
					
					System.out.print("Running SD with a fixed line search:" + "\n");
					for (int i =0; i < nb_polynomials; i++) {
						Polynomial_obj = P.get(i);
						SDF.run(i,Polynomial_obj);
					}
					
					System.out.print("\n" +"Running SD with an Armijo line search:"+ "\n");
					for (int j =0; j < nb_polynomials; j++) {
						Polynomial_obj = P.get(j);
						SDA.run(j, Polynomial_obj); //not displaying the right outputs
					 }
					
					
					System.out.print("\n" +"Running SD with a golden section line search:"+ "\n");
					for (int j =0; j < nb_polynomials; j++) {
						Polynomial_obj = P.get(j);
						SDG.run(j, Polynomial_obj); 
					 }
					
					
					System.out.println("\n"+"All polynomials done.\n");
				}
	
				
			}
			
			else if (option.equals("D") == true || option.equals("d") == true ) {
				if (has_run == false) {
					System.out.print("\n"
							+ "ERROR: Results do not exist for all line searches!\n"
							+ "\n"
							+ "");
				}
				
				else {
					printAllResults(SDF, SDA, SDG,P);
					
				}
			}
			
			else if (option.equals("Q") == true || option.equals("q") == true ) {
				 Quit();
				 break;
			}
			
			else if (option.equals("X") == true || option.equals("x") == true ) {
				if (has_run == false) {
					System.out.println("\n"
							+ "ERROR: Results do not exist for all line searches!\n");
				}
				else {
					compare(SDF, SDA, SDG);
				}
			}
			
			else {
				System.out.print("\n"
						+ "ERROR: Invalid menu choice!\n"
						+ "\n"
						+ "");
			}

		}
	}
	
	//Exit function
	public static void Quit() {
		System.out.println("\n" +"Arrivederci.");
	}
	
	public static void display_menu() {
		System.out.println("   JAVA POLYNOMIAL MINIMIZER (STEEPEST DESCENT)\n"
				+ "L - Load polynomials from file\n"
				+ "F - View polynomial functions\n"
				+ "C - Clear polynomial functions\n"
				+ "S - Set steepest descent parameters\n"
				+ "P - View steepest descent parameters\n"
				+ "R - Run steepest descent algorithms\n"
				+ "D - Display algorithm performance\n"
				+ "X - Compare average algorithm performance\n"
				+ "Q - Quit\n");
		
	}
	
	public static boolean loadPolynomialFile(ArrayList <Polynomial> P)  {
		ArrayList<double[]> temp = new ArrayList<>();
		int display =0;
		boolean load = false;
		int nb_poly_print = 0;
		
		
		System.out.print( "\n"+"Enter file name (0 to cancel): ");
		
		try {
			//read file using buffered reader
			String filename = cin.readLine();
			
			
			if (filename.equals("0")) {
				System.out.println("\n"
						+ "File loading process canceled.\n");
				return false;
			}
				
			else {
				
				File file = new File ( filename ) ;
				
				//Then use scanner to read file input
				Scanner scan = new Scanner(file);
				
				//check if file exits		
				boolean exists = file.exists () ;
				
				if (! exists ) {
					System.out.println ("ERROR: File not found!" + "\n") ;
				}
				
				
				else {
					
					//while there is an inputed line keep going
					while (scan.hasNext()) {
						
						//string of one line of the file
						 String data = scan.next();
				
						 //values: array with each element of a line
						 String[] values = data.split(",");
						 
						//length: nb of elements in one line
						 int length = values.length;
						 
						 //initialize the size of temp
						 double arr[] = new double[length];
								 
						 if (!data.equals("*")) {
							 
								 //add values of in the line into an array arr
								 for (int i =0; i<length; i++) {
									 double number = Double.parseDouble(values[i]);
									 arr[i] = number;
								 }
								 
								 //add array to arraylist temp
								 temp.add(arr);
								
						 } // end if not equal to *
						 
						 
						 //when data is an *
						 else {
							 display +=1;
							 //check if the polynomial is consistent 
							int degree=0;
							int checkdegree=0;
							boolean same = true;
							
							
							for (int o = 0; o < temp.size() ; o++) {
								
								if (degree ==0 && checkdegree==0) {
									degree = temp.get(o).length;
									checkdegree = temp.get(o).length;
								}
								
								else {
									checkdegree = temp.get(o).length;
								}
									
								if (degree != checkdegree ) {
									same = false;
								}
								
								
							}// end of loop to check consistency
								
							 
							 if (same == true) {
							 
								 //initialize object and add element of temp into the object
								 Polynomial poly = new Polynomial();
								 poly.setN(temp.size());
								 poly.setDegree((temp.get(0).length)-1);
								 poly.init();
								 
								 for (int k =0; k< poly.getN(); k++) {
									 for (int j =0; j<poly.getDegree() +1; j++) {
										 poly.setCoef(k, j, temp.get(k)[j]);
									 }
								 }
								 
								 //add the polynomial object into the arraylist P
								 P.add(poly);
								 nb_polynomials +=1;
								 nb_poly_print +=1;
								 
								 
								 } // end if consistent 
							 
							 else {
								 System.out.printf("\nERROR: Inconsistent dimensions in polynomial %d!\n", display);
							 }
							 
							 temp= new ArrayList<>();
						 
						 }//end else : when data is an *
						 
							}//end of while
						
							// same procedure as above -> for the last polynomial as there is no * to mark the end of a polynomial
								display +=1;
					
								//check if the polynomial is consistent 
								int degree=0;
								int checkdegree=0;
								boolean same = true;
								
								
								for (int o = 0; o < temp.size() ; o++) {
									
									if (degree ==0 && checkdegree==0) {
										degree = temp.get(o).length;
										checkdegree = temp.get(o).length;
									}
									
									else {
										checkdegree = temp.get(o).length;
									}
										
									if (degree != checkdegree ) {
										same = false;
									}
									
								} // end of loop to check consistency
				
				
								 if (same == true) {
									 Polynomial poly = new Polynomial();
									 poly.setN(temp.size());
									 poly.setDegree((temp.get(0).length)-1);
									 poly.init();
									 
									 for (int k =0; k< poly.getN(); k++) {
										 for (int j =0; j<poly.getDegree() +1; j++) {
											 poly.setCoef(k, j, temp.get(k)[j]);
										 }
									 }
									 
									 P.add(poly);
									 nb_polynomials +=1;
									 nb_poly_print +=1;
									 temp.clear();
									 scan.close();
							
									
								 
								 } // end add last poly to object
								 
								 else {
									
									 System.out.printf("\nERROR: Inconsistent dimensions in polynomial %d!\n", display);
									 
								 }
						
						
						temp= new ArrayList<>();
					
			
				} // end of biggest else
			} //end of try statement 
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("\n"+"ERROR: File not found!"+"\n");
			return false;
		}
		
		System.out.println("\n" + nb_poly_print + " polynomials loaded!" +"\n");
		
		return true;
	
	}
	
	
		
	public static void printPolynomials(ArrayList<Polynomial> P) {
		if (nb_polynomials == 0) {
			System.out.print("\n"
					+ "ERROR: No polynomial functions are loaded!\n"
					+ "\n");
		}
		
		else {
			System.out.print("\n");
			formatted_print(P);
			System.out.print("\n");
		}
	}
	
	

	public static void getAllParams(SDFixed SDF,SDArmijo SDA, SDGSS SDG) {   
		SDF.getParamsUser();
		SDA.getParamsUser();
		SDG.getParamsUser();
		
	}
	
	
	public static void printAllResults(SDFixed SDF, SDArmijo SDA,SDGSS SDG, ArrayList<Polynomial> P) { 
		
		//Print SDF
		System.out.println("\n"+"Detailed results for SD with a fixed line search:");
		System.out.print("-------------------------------------------------------------------------\n"
				+ "Poly no.         f(x)   norm(grad)   # iter   Comp time (ms)   Best point   \n"
				+ "-------------------------------------------------------------------------\n");
	
		for (int i =0; i < nb_polynomials; i++) {
			SDF.printSingleResult(i, poly_entered); //SD.printSingleResult(i, boolean rowOnly) WHAT IS ROWONLY
			System.out.print("\n");
		}
		System.out.println("\n"+"Statistical summary for SD with a fixed line search:");
		SDF.printStats();
		
		
		//Print SDA 
		System.out.println("\n"+"Detailed results for SD with an Armijo line search:");
		System.out.print("-------------------------------------------------------------------------\n"
				+ "Poly no.         f(x)   norm(grad)   # iter   Comp time (ms)   Best point   \n"
				+ "-------------------------------------------------------------------------\n");
		
		for (int j =0; j < nb_polynomials; j++) {
			SDA.printSingleResult(j, poly_entered); //SD.printSingleResult(i, boolean rowOnly) WHAT IS ROWONLY
			System.out.print("\n");
		}
		System.out.println("\n"+"Statistical summary for SD with an Armijo line search:");
		SDA.printStats();
		
		
		//Print SDG
		System.out.println("\n"+"Detailed results for SD with a golden section line search:");
		System.out.print("-------------------------------------------------------------------------\n"
				+ "Poly no.         f(x)   norm(grad)   # iter   Comp time (ms)   Best point   \n"
				+ "-------------------------------------------------------------------------\n");
		
		for (int j =0; j < nb_polynomials; j++) {
			SDG.printSingleResult(j, poly_entered); //SD.printSingleResult(i, boolean rowOnly) WHAT IS ROWONLY
			System.out.print("\n");
		}
		System.out.println("\n"+"Statistical summary for SD with a golden section line search:");
		SDG.printStats();
		
		
	}
	
	
	public static void formatted_print(ArrayList <Polynomial> P) {
		System.out.println("---------------------------------------------------------");
		System.out.println("Poly No.  Degree   # vars   Function");
		System.out.println("---------------------------------------------------------");
		
		for (int i=0; i< P.size(); i++) {
			System.out.printf("%8d", i+1);
			System.out.printf("%8d", P.get(i).getDegree());
			System.out.printf("%9d", P.get(i).getN());
			System.out.print("   ");
			P.get(i).print();
		}
	
	}
	
	public static void compare(SDFixed SDF, SDArmijo SDA, SDGSS SDG) { 

		//AVERAGE calculation SDF
		double SDF_av_norm =0;
		double SDF_av_iter =0;
		double SDF_av_comp_time = 0;
		//SDA
		double SDA_av_norm =0;
		double SDA_av_iter =0;
		double SDA_av_comp_time = 0;
		//SDG
		double SDG_av_norm =0;
		double SDG_av_iter =0;
		double SDG_av_comp_time = 0;
		
		//1st calculate sum
		//SDF
		double SDF_sum_norm=0;
		double SDF_sum_iter=0;
		long SDF_sum_comp_time=0;
		//SDA
		double SDA_sum_norm=0;
		double SDA_sum_iter=0;
		long SDA_sum_comp_time=0;
		//GSS
		double SDG_sum_norm=0;
		double SDG_sum_iter=0;
		long SDG_sum_comp_time=0;
		
		
		for (int m = 0; m < SDF.getBestGradNorm().length; m++) {
			SDF_sum_norm += SDF.getBestGradNorm()[m];
			SDF_sum_iter += SDF.getNIter()[m];
			SDF_sum_comp_time += SDF.getCompTime()[m];
			
			SDA_sum_norm += SDA.getBestGradNorm()[m];
			SDA_sum_iter += SDA.getNIter()[m];
			SDA_sum_comp_time += SDA.getCompTime()[m];
			
			SDG_sum_norm += SDG.getBestGradNorm()[m];
			SDG_sum_iter += SDG.getNIter()[m];
			SDG_sum_comp_time += SDG.getCompTime()[m];
		}
		
		SDF_av_norm = SDF_sum_norm/(SDF.getBestGradNorm().length);
		SDF_av_iter = SDF_sum_iter/(SDF.getNIter().length);
		SDF_av_comp_time = SDF_sum_iter/(SDF.getCompTime().length);
		
		SDA_av_norm = SDA_sum_norm/(SDA.getBestGradNorm().length);
		SDA_av_iter = SDA_sum_iter/(SDA.getNIter().length);
		SDA_av_comp_time = SDA_sum_iter/(SDA.getCompTime().length);
		
		SDG_av_norm = SDG_sum_norm/(SDG.getBestGradNorm().length);
		SDG_av_iter = SDG_sum_iter/(SDG.getNIter().length);
		SDG_av_comp_time = SDG_sum_iter/(SDG.getCompTime().length);
		
		
		System.out.print("\n"
				+ "---------------------------------------------------\n"
				+ "          norm(grad)       # iter    Comp time (ms)\n"
				+ "---------------------------------------------------\n");
		System.out.format("Fixed  %13.3f%13.3f%18.3f\n", SDF_av_norm, SDF_av_iter, SDF_av_comp_time);
		System.out.format("Armijo %13.3f%13.3f%18.3f\n", SDA_av_norm, SDA_av_iter, SDA_av_comp_time );
		System.out.format("GSS    %13.3f%13.3f%18.3f\n", SDG_av_norm, SDG_av_iter, SDG_av_comp_time );
		System.out.println("---------------------------------------------------");
		
		//find the winner 
		ArrayList<Double> min_norm = new ArrayList<Double>();
		min_norm.add(SDF_av_norm);
		min_norm.add(SDG_av_norm);
		min_norm.add(SDA_av_norm);
		
		double minimum_norm = 0.00;
		
		minimum_norm = Collections.min(min_norm);
		
		String winner_norm = null;

		for (int i=0; i <= min_norm.size(); i++) {
			
			if (minimum_norm == min_norm.get(0)) {
				winner_norm = "Fixed";
			}
			else if (minimum_norm == min_norm.get(1)) {
				winner_norm = "GSS";
			}
			else if (minimum_norm == min_norm.get(2)) {
				winner_norm = "Armijo";
			}
			else {
				System.out.print("bizarre1");
			}
		}
		
		
		//minimum iter
		ArrayList<Double> min_iter = new ArrayList<Double>();
		min_iter.add(SDF_av_iter);
		min_iter.add(SDG_av_iter);
		min_iter.add(SDA_av_iter);
		
		double minimum_iter = 0.00;
		
		minimum_iter = Collections.min(min_iter);
		
		String winner_iter = null;

		for (int i=0; i <= min_iter.size(); i++) {
			
			if (minimum_iter == min_iter.get(0)) {
				winner_iter = "Fixed";
			}
			else if (minimum_iter == min_iter.get(1)) {
				winner_iter = "GSS";
			}
			else if (minimum_iter == min_iter.get(2)) {
				winner_iter = "Armijo";
			}
			else {
				System.out.print("bizarre2");
			}
		}
		
		//minimum comptime
		ArrayList<Double> min_comp = new ArrayList<Double>();
		min_comp.add(SDF_av_comp_time);
		min_comp.add(SDG_av_comp_time);
		min_comp.add(SDA_av_comp_time);
		
		double minimum_comp = 0.00;
		
		minimum_comp = Collections.min(min_comp);
		
		String winner_comp = null;

		for (int i=0; i <= min_comp.size(); i++) {
			
			if (minimum_comp == min_comp.get(0)) {
				winner_comp = "Fixed";
			}
			else if (minimum_comp == min_comp.get(1)) {
				winner_comp = "GSS";
			}
			else if (minimum_comp == min_comp.get(2)) {
				winner_comp = "Armijo";
			}
			else {
				System.out.print("bizarre3");
			}
		}

		//print winner
		System.out.format("%-6s%14s%13s%18s\n", "Winner", winner_norm, winner_iter, winner_comp);
		
		
		
		System.out.println("---------------------------------------------------");
		
		//determine nb of times each algo wins
		int fixed =0;
		int gss =0;
		int armijo =0;
		if (winner_norm == "Fixed") {
			fixed+=1;
		}
		
		else if (winner_norm == "Armijo") {
			armijo+=1;
		}
		
		else if (winner_norm == "GSS") {
			gss+=1;
		}
		else {
			System.out.print("bizarre4");
		}
		
		
		if (winner_iter == "Fixed") {
			fixed+=1;
		}
		else if (winner_iter == "Armijo") {
			armijo+=1;
		}
		else if (winner_iter == "GSS") {
			gss+=1;
		}
		else {
			System.out.print("bizarre5");
		}
		
		
		if (winner_comp == "Fixed") {
			fixed+=1;
		}
		else if (winner_comp == "Armijo") {
			armijo+=1;
		}
		else if (winner_comp == "GSS") {
			gss+=1;
		}
		else {
			System.out.print("bizarre6");
		}
		
		
		//find overall winner
		ArrayList<Integer> points = new ArrayList<Integer>();
		points.add(fixed);
		points.add(gss);
		points.add(armijo);
		
		int maximum = 0;
		String winner_overall = null;
		maximum = Collections.max(points);
		int count =0;
		for (int i=0; i< points.size(); i++) {
			if (points.get(i) == 2) {
				count = 2;
			}
		}
		
		if (count == 2){
			winner_overall = "Unclear";
		}
		
		else {
			if (maximum == points.get(0)) {
				winner_overall = "Fixed";
			}
			else if (maximum == points.get(1)) {
				winner_overall = "Gss";
			}
			else if (maximum == points.get(2)) {
				winner_overall = "Armijo";
			}
			
			else {
				System.out.print("bizarre6");
			}
		}
		
		
		
		System.out.println("Overall winner: " + winner_overall + "\n");
		
	}
	
	
	//Processing user integer input 
	public static int getInteger(String prompt, int LB, int UB) {
		//integer is initialized to the highest number to pass into the infinite while loop. Once an integer within the proper bounds
		//is inputed, the while loop will be exited and the integer will be returned
		// After every error message integer is reinitialized to Integer.MAX_VALUE so that the while loop is entered once more
		int integer = Integer.MAX_VALUE;
		
		while (integer == Integer.MAX_VALUE ) {
			System.out.print(prompt);
			
			try {
				
				//the value of integer is replaced by the value of the input by the user 
				integer = Integer.parseInt( cin.readLine() );
				
				//if the integer is outside of the bounds (LB-UB) return error
				if (integer < LB || integer > UB) {
					
					//if UB set is Integer.MAX_VALUE, then return UB to be infinity
					// Two different types of output: 1 if the upper bound is infinity, 2 all other cases
					if (UB==Integer.MAX_VALUE) {
						System.out.printf( "ERROR: Input must be an integer in [%d, infinity]!\n"  +"\n", LB);
						integer = Integer.MAX_VALUE;
					}
					
					else {
						System.out.printf( "ERROR: Input must be an integer in [%d, %d]!\n"+"\n", LB, UB);
						integer = Integer.MAX_VALUE;
				}	
				}
				
				
			//If the input is something else than an integer return an error message
			// Two different types of output: 1 if the upper bound is infinity, 2 all other cases
			} catch (NumberFormatException e) {
				if (UB==Integer.MAX_VALUE) {
					System.out.printf("ERROR: Input must be an integer in [%d, infinity]!\n"+"\n", LB);
					integer = Integer.MAX_VALUE;
				}
				else {
					System.out.printf("ERROR: Input must be an integer in [%d, %d]!\n"+"\n", LB, UB);
					integer = Integer.MAX_VALUE;
				}
				
			} catch (IOException e) {
				if (UB==Integer.MAX_VALUE) {
					System.out.printf("ERROR: Input must be an integer in [%d, infinity]!\n"+"\n", LB);
					integer = Integer.MAX_VALUE;
				}
				else {
					System.out.printf("ERROR: Input must be an integer in [%d, %d]!\n"+"\n", LB, UB);
					integer = Integer.MAX_VALUE;
				}
			}
		}
		return integer;
	}
	


    public static double getDouble(String prompt, double LB, double UB) {
		//the double d is initialized to the highest number to pass into the infinite while loop. Once a double within the proper bounds
		//is inputed, the while loop will be exited and the double will be returned
	 	// After every error message the double d is reinitialized to Double.MAX_VALUE so that the while loop is entered once more
	 	double d = Double.MAX_VALUE;
		
		while (d == Double.MAX_VALUE ) {
			System.out.print(prompt);
			
			try {
				//the value of the double is replaced by the value of the input by the user 
				d = Double.parseDouble( cin.readLine() );
				
				//if the double is outside of the bounds (LB-UB) return error. 
				// Three different types of output: 1 where the lower bound and upper bound is infinity, 2 if the upper bound is infinity 
				// and 3, all other cases
				if (d < LB || d > UB) {
					if (LB ==-Double.MAX_VALUE && UB == Double.MAX_VALUE) {
						System.out.println( "ERROR: Input must be a real number in [-infinity, infinity]!" + "\n" + "\n");
						d = Double.MAX_VALUE;
					}
					else if (UB==Double.MAX_VALUE) {
						System.out.printf("ERROR: Input must be a real number in [%.2f, infinity]!"+ "\n" +"\n", LB);
						d = Double.MAX_VALUE;
					}
					
					else {
						System.out.printf( "ERROR: Input must be a real number in [%.2f, %.2f]!"+ "\n"  + "\n", LB, UB); //WONT PASS THIS LINE FOR LARGE NBS
					//	System.out.println( "ERROR: Input must be a real number in [" + LB + ", " + UB + "]!"+  "\n");
						d = Double.MAX_VALUE;
					}
					
				}
			//if input is something else than a double return an error message 
			// Three different types of output: 1 where the lower bound and upper bound is infinity, 2 if the upper bound is infinity 
			// and 3, all other cases
			} catch (NumberFormatException e) {
				if (LB == -Double.MAX_VALUE && UB == Double.MAX_VALUE) {
					System.out.println( "ERROR: Input must be a real number in [-infinity, infinity]!"+  "\n"  );
					d = Double.MAX_VALUE;
				}
				
				else if (UB==Double.MAX_VALUE) {
					System.out.printf("ERROR: Input must be a real number in [%.2f, infinity]!"+ "\n"+ "\n", LB);
					d = Double.MAX_VALUE;
				}
				
				else {
					System.out.printf( "ERROR: Input must be a real number in [%.2f, %.2f]!"+  "\n"+ "\n" , LB, UB);
					d = Double.MAX_VALUE;
				}
				
				
			} catch (IOException e) {
				if (LB ==-Double.MAX_VALUE && UB ==Double.MAX_VALUE) {
					System.out.println( "ERROR: Input must be a real number inn [-infinity, infinity]!"+ "\n"  );
					d = Double.MAX_VALUE;
				}
				
				else if (UB==Double.MAX_VALUE) {
					System.out.printf("ERROR: Input must be a real number in [%.2f, infinity]!"+ "\n" + "\n", LB);
					d = Double.MAX_VALUE;
				}
				
				else {
					System.out.printf( "ERROR: Input must be a real number in [%.2f, %.2f]!"+ "\n"+ "\n", LB, UB);
					
				}
			}
		}
		return d;
	}

	
	



}

