package test;



public class Ficha implements Cloneable{
	
	private int num1, num2;
	private boolean flipped;
	
	public Ficha(int num1, int num2) {
		this.num1 = num1;
		this.num2 = num2;
		this.flipped = false;
	}
	
	

	public int getNum1() {
		return num1;
	}
	
	public int getNum2() {
		return num2;
	}

	public boolean hasInt(int x) {
		if ((x == this.num1) || (x == this.num2)) return true;
		else return false;
	}
	
	public void flip() {
		this.flipped = !this.flipped;
		this.num1 = this.num1 + this.num2;
		this.num2 = this.num1 - this.num2;
		this.num1 = this.num1 - this.num2;
	}
	
	
	public boolean canBeBothSides(int num1, int num2) {
		return this.hasInt(num1) && this.hasInt(num2);
	}
	

	@Override
	public String toString() {
		return "[" + num1 + "|" + num2 + "]";
	}

	
	@Override
    public boolean equals(Object o) { 
  
        // If the object is compared with itself then return true   
        if (o == this) { return true; } 
  
        /* Check if o is an instance of Ficha or not "null instanceof [type]" also returns false */
        if (!(o instanceof Ficha)) { return false; }
          
        // typecast o to Ficha so that we can compare data members  
        Ficha c = (Ficha) o; 
          
        // Compare the data members and return accordingly  
        return ( Integer.compare(num1, c.getNum1()) == 0 && Double.compare(num2, c.getNum2()) == 0 ) ||
        		( Integer.compare(num2, c.getNum1()) == 0 && Double.compare(num1, c.getNum2()) == 0 );
    }
	
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		
		return super.clone();
	}
	
	
	
}
