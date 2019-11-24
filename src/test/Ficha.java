package test;

/**
 * @author nikolay.dimitrov@estudiants.urv.cat
 */
public class Ficha implements Cloneable{
	
	private int num1, num2;
	private boolean flipped;
	
	
	
	/**
	 * Constructor de la classe Ficha
	 * @param num1 - Enter que identifica un dels números d'una fitxa de dòmino
	 * @param num2 - Enter que identifica l'altre número d'una fitxa de dòmino
	 */
	public Ficha(int num1, int num2) {
		this.num1 = num1;
		this.num2 = num2;
		this.flipped = false;
	}
	
	
	
	/**
	 * Serveix per indicar si la fitxa conté a una de les dues posicions un enter passat per paràmetre.
	 * @param x - enter a buscar
	 * @return true si el conté, false si no el conté
	 */
	public boolean hasInt(int x) {
		if ((x == this.num1) || (x == this.num2)) return true;
		else return false;
	}
	
	
	
	/**
	 * Aquest mètode serveix per girar una fitxa i canviar-li la orientació.
	 * Reverteix una variable que indica si està girada o no i intercanvia el valor de les
	 * dues variables dels enters.
	 */
	public void flip() {
		this.flipped = !this.flipped;
		this.num1 = this.num1 + this.num2;
		this.num2 = this.num1 - this.num2;
		this.num1 = this.num1 - this.num2;
	}
	
	
	
	/**
	 * Indica si aquesta fitxa conté els números dels dos costats del taulell per
	 * saber si pot anar als dos costats i hem de fer a l'usuari escollir el costat.
	 * @param num1 - Costat esquerre del taulell
	 * @param num2 - Costat dret del taulell
	 * @return - true si la fitxa té els dos costats del taulell, false si no
	 */
	public boolean canBeBothSides(int num1, int num2) {
		return this.hasInt(num1) && this.hasInt(num2);
	}

	
	
	/**
	 * GETTERS I SETTERS
	 */	

	public int getNum1() {
		return num1;
	}
	
	public int getNum2() {
		return num2;
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