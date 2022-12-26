
public class Polynomial {
	private int n ; 
	private int degree ; 
	private double[][] coefs ; 
	
	
	// constructors
	public Polynomial () {
		n = 0;
		degree = 0;
		double coefs[][] = new double[0][0];
	}
		
	public Polynomial ( int n , int degree , double [][] coefs ) {
		this.n = n;
		this.degree = degree;
		this.coefs = coefs;
	}
		
	// getters
	public int getN () {return this.n;}
	public int getDegree () {return this.degree;}
	public double [][] getCoefs () {return this.coefs;}
		
	// setters
	public void setN ( int a ) {this.n = a;}
	public void setDegree ( int a ) {this.degree = a;}
	public void setCoef ( int j , int d , double a ) {this.coefs[j][d] = a;}
	
	// init member arrays to correct size 
	public void init ()  {
		this.coefs = new double[this.n][this.degree+1];
	}
	
	// calculate function value at point x
	public double f ( double [] x ) {
		int i;
		int j;
		double summation=0;
		int second_index =0;
			 
	//Loops through the number of variables, then loop through the nb of degrees for each variable to
	//Sum the coefficients for each variable
		for (i=0; i < this.n; i++ ) {		 
			for (j=this.degree; j >=0; j--) {
			//	System.out.println("ind" +i);
			//	System.out.println("coef"+this.coefs[i][second_index]);
				summation += Math.pow(x[i],j)*this.coefs[i][second_index];  //ERROR IS HERE ON 3RD LINE, x IS OF LENGTH 2 BUT 3 VARIABLES. 	weird cause i should be on 1st polynomial but coefs are of the second poly
				
				
				second_index+=1;
			} 
			second_index = 0;
		}
			 
		return summation;
	}
 
	

	//calculate gradient at point x
	public double [] gradient ( double [] x ) {
		 int i =0;
		 int j=0;
		 int x_i = 0;
		 double[] input_x = x; //can delete this
		 
		 //Initialize the size of the gradient array to the number of variables
		 double gradient_f[] = new double[this.n];
		 
		 //Loop through each value (row) of the gradient
		 for (x_i=0; x_i < this.n ; x_i++) {
			 double sum=0.0;
			 
			//Loop through the number variables in the polynomial to later sum to one value for one row of the gradient
			 for (i=0; i < this.n; i++ ) {
				 int index =0;
				 
				 //Loop through the degrees for one variable of the polynomial
				 for (j=this.degree; j > 0; j--) {
					 
					 //If the row of the gradient is equal to the row of the polynomial (after partial derivative, only the row 
					 // to which the gradient derives from respects of will be non zer0
					 if (x_i == i) {
						 
						 // if degree is zero, the derivative will yield 0 
						 if (j!=0) {
							 
							 //If the degree is one, the derivative will yield a constant, so just the coefficient 
							 if (j==1) {
								 sum += j*this.coefs[i][index];
							 }
							 
							 //Else, multiply the coefficient by is degree, but also by the starting point to the power j-1
							 else {
								 sum += j*this.coefs[i][index]*Math.pow(input_x[i],j-1); 
							 }
				
							 index += 1;
							 
						 	}
		
					 }
				 }	
			 }
			 // Each row of the gradient is filled by the sum of the polynomial with respects to one variable every iteration
			 gradient_f[x_i] = sum;
		 }
	
		return gradient_f;
	}

	
	//calculate norm of gradient at point x
	public double gradientNorm ( double [] x ) {
		 double sum_squarred = 0;
		 double norm =0;
		 double [] g = gradient(x);
		 int i =0;
		 
		 //Sum of each element in the array x passed into the gradient function to the power of 2
		 for (i=0; i < this.n; i++ ) {
			 sum_squarred += Math.pow(g[i], 2) ;	 
		 }
		 
		 //Square root of the total sum
		 norm = Math.sqrt(sum_squarred) ;
		 
		 return norm;
		 
	 }

	
	// indicate whether polynomial is set
	public boolean isSet () {		 
	
		//If n is equal to 0, then no polynomial has been inputed by the user
		if (n == 0) {
			System.out.println("\n"+"ERROR: Polynomial function has not been entered!"+"\n");
			return false;
		}
		else {
			return true;
		}
	}
	
	// print out the polynomial
	public void print () {
		int i;
		int j;
		
		if (isSet() == false) {
			return;
		}
			 
		//Formatting
		
		System.out.print("f(x) = (");
		
		//First loop through the nb of variables in the polynomial
		for (i=0; i < this.n; i++ ) {
			int d = this.degree;
				 
			if (i !=0 ) {
				System.out.print(" ) + (");
			}
			
			// Second loop through the degrees for each variable in the polynomial
			for (j=0; j < this.degree+1; j++) {
					 
			//If degree is 0, print the constant with no variable 
			if (d == 0) {
				System.out.printf(" %.2f", this.coefs[i][j]);
				d-=1;
			 }
					 
			 //Otherwise print coef with a variable and a degree
			else {
				int print_x_indice = i+1;
				System.out.printf(" %.2fx%d^%d +", this.coefs[i][j], print_x_indice,d);
				d-=1;
				}
					 
			 }
				 
		}
		System.out.print(" )");
		System.out.print("\n");
	}

	
}


