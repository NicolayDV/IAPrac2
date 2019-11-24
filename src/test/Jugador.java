package test;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * @author nikolay.dimitrov@estudiants.urv.cat
 */
public class Jugador implements Cloneable{

	private String nom;
	private List<Ficha> fichasJugador;
	private List<Ficha> possiblesTiradas;
	private boolean maquina;	
	
	
	
	/**
	 * @param nom - String amb el nom del jugador
	 * @param maquina - Boolean que indica true si el jugador és la maquina o false si és huma.
	 */
	public Jugador(String nom, boolean maquina) {
		this.nom = nom;
		this.maquina = maquina;
		this.fichasJugador = new ArrayList<Ficha>();
		this.possiblesTiradas = new ArrayList<Ficha>();		
	}
	
	
	
	/**
	 * Mètode per afegir una Ficha a la llista que té el jugaddor.
	 * @param ficha - fitxa de classe Ficha que vols afegir a la mà del jugador.
	 */
	public void addFicha(Ficha ficha) {
		this.fichasJugador.add(ficha);
	}

	
	
	/**
	 * Comprova si la fitxa passada per paràmetre existeix a la mà del jugador.
	 * @param ficha
	 * @return true si existeix, false si no existeix
	 */
	public boolean hasFicha(Ficha ficha) {
		return fichasJugador.contains(ficha) ? true : false;
	}
	
	
	
	/**
	 * Retorna el valor heurístic del taulell passat per paràmetre.
	 * Implementa 3 heurístiques explicades a la documentació (H1, H2, H3).
	 * A partir del String passat per paràmetre, escull una heurística o una altra.
	 * @param tablero - Taulell que li passes per paràmetre
	 * @param heuristic - String que serveix per escollit la heurística: H1, H2 o H3
	 * @return - enter amb el valor heurístic
	 */
	public int getHeuristic(Tablero tablero, String heuristic) {
		
		if (heuristic.equals("H1")) {
			
			Ficha ficha = tablero.getLastMove();
			//System.out.println("\nH1" + "Fitxa a jugar: " + ficha + " , " + "Valor de la heurística: " + ((ficha.getNum1()+1) * (ficha.getNum2()+1)));
			return (ficha.getNum1()+1) * (ficha.getNum2()+1);
			
		} else if (heuristic.equals("H2")) {
			
			Hashtable<Integer,Integer> countFichas = new Hashtable<Integer,Integer>();
			for (int i=0; i<7; i++) countFichas.put(i, 0);
			for (Ficha ficha : tablero.getTornActual().getFichasJugador()) {
				countFichas.put(ficha.getNum1(), countFichas.get(ficha.getNum1())+1);
				countFichas.put(ficha.getNum2(), countFichas.get(ficha.getNum2())+1);
			}
			countFichas.put(tablero.getLastMove().getNum1(), countFichas.get(tablero.getLastMove().getNum1())+1);
			countFichas.put(tablero.getLastMove().getNum2(), countFichas.get(tablero.getLastMove().getNum2())+1);
			//System.out.println("\nH2HashTable: " + countFichas + " , " + "Valor heurística: " + (countFichas.get(tablero.getLastMove().getNum1()) + countFichas.get(tablero.getLastMove().getNum2())) + " , " + "Fitxa a jugar: " + tablero.getLastMove());
			return countFichas.get(tablero.getLastMove().getNum1()) + countFichas.get(tablero.getLastMove().getNum2());
			
		} else {
			
			Hashtable<Integer,Integer> countFichas = new Hashtable<Integer,Integer>();
			for (int i=0; i<7; i++) countFichas.put(i, 0);
			for (Ficha ficha : tablero.getFichasTablero()) {
				countFichas.put(ficha.getNum1(), countFichas.get(ficha.getNum1())+1);
				countFichas.put(ficha.getNum2(), countFichas.get(ficha.getNum2())+1);
			}
			for (Ficha ficha : tablero.getTornActual().getFichasJugador()) {
				countFichas.put(ficha.getNum1(), countFichas.get(ficha.getNum1())+1);
				countFichas.put(ficha.getNum2(), countFichas.get(ficha.getNum2())+1);
			}		
			//System.out.println("\nH3HashTable: " + countFichas + " , " + "Fitxa a jugar: " + tablero.getLastMove() + " , " + "Valor heurístic: " + (countFichas.get(tablero.getLado1()) * countFichas.get(tablero.getLado2())));
			return countFichas.get(tablero.getLado1()) * countFichas.get(tablero.getLado2());
			
		}		
	}
	
	
	
	/**
	 * Algoritme de jocs Minimax recursiu que retorna la següent millor jugada.
	 * @param nodo - Estat del taulell actual
	 * @param nivel - Nivell d'exploració en el qual estas (comença per 1)
	 * @param heuristic - Heurística a escollir: H1, H2 o H3
	 * @return - Taulell amb la millor jugada
	 */
	public Tablero minimax(Tablero nodo, int nivel, String heuristic) {
		Tablero nodo_a_devolver = null, F = null, aux = null;
		int valor_a_devolver = 0;
		int masInfinito = 2147483647;
		int menosInfinito = -2147483648;
		
		if (nodo.hasWinner()) {
			nodo_a_devolver = new Tablero(null, null);
//			if (nivel % 2 == 1) nodo_a_devolver.setMinimaxValue(masInfinito);
//			else nodo_a_devolver.setMinimaxValue(menosInfinito);
			nodo_a_devolver.setMinimaxValue(masInfinito);
			return nodo_a_devolver;
		} else if (nivel == Test.MAX_MINIMAX) {
			nodo_a_devolver = new Tablero(null, null);
			nodo_a_devolver.setMinimaxValue(this.getHeuristic(nodo, heuristic));
			return nodo_a_devolver;
		} else {
			
			if (nivel % 2 == 1) valor_a_devolver = menosInfinito;
			else valor_a_devolver = masInfinito;			
			
			nodo.setSuccessors();
			
			if (nodo.getSuccessorsMinimax().isEmpty()) {
				try {
					nodo_a_devolver = (Tablero) nodo.clone();
				} catch(CloneNotSupportedException c){}
			}
			else nodo_a_devolver = nodo.getSuccessorsMinimax().get(0);
			
			while (!nodo.getSuccessorsMinimax().isEmpty()) {
				F = nodo.getSuccessorsMinimax().remove(0);
				aux = minimax(F, nivel+1, heuristic);
				
				if (nivel % 2 == 1) {					
					if (aux.getMinimaxValue() > valor_a_devolver) {
						valor_a_devolver = aux.getMinimaxValue();
						nodo_a_devolver = F;
					}				
				} else {
					if (aux.getMinimaxValue() < valor_a_devolver) {
						valor_a_devolver = aux.getMinimaxValue();
						nodo_a_devolver = F;
					}
				}
			}
			nodo_a_devolver.setMinimaxValue(valor_a_devolver);
		}

		return nodo_a_devolver;
	}
	
		
	
	/**
	 * Algoritme Minimax adaptat de manera que no explori els nodes no necessaris, i per tant és més eficient.
	 * @param nodo - Estat del taulell actual
	 * @param nivel - Nivell d'exploració en el qual estas (comença per 1)
	 * @param alfa - valor per defecte de les variables utilitzades per fer la poda (al principi és menysInfinit)
	 * @param beta - valor per defecte de les variables utilitzades per fer la poda (al principi és mesInfinit)
	 * @param heuristic - Heurística a escollir: H1, H2 o H3
	 * @return - Taulell amb la millor jugada
	 */
	public Tablero podaAlfaBeta(Tablero nodo, int nivel, int alfa, int beta, String heuristic) {
		Tablero nodo_a_devolver = null, F = null, aux = null;
		int masInfinito = 2147483647;
		
		if (nodo.hasWinner()) {
			nodo_a_devolver = new Tablero(null, null);
//			if (nivel % 2 == 1) nodo_a_devolver.setMinimaxValue(masInfinito);
//			else nodo_a_devolver.setMinimaxValue(menosInfinito);
			nodo_a_devolver.setMinimaxValue(masInfinito);
			return nodo_a_devolver;
		} else if (nivel == Test.MAX_ALPHABETA) {
			nodo_a_devolver = new Tablero(null, null);
			nodo_a_devolver.setMinimaxValue(this.getHeuristic(nodo, heuristic));
			return nodo_a_devolver;
		} else {	
			
			nodo.setSuccessors();
			
			if (nodo.getSuccessorsMinimax().isEmpty()) {
				try {
					nodo_a_devolver = (Tablero) nodo.clone();
				} catch(CloneNotSupportedException c){}
			}
			else nodo_a_devolver = nodo.getSuccessorsMinimax().get(0);
			
			while ((!nodo.getSuccessorsMinimax().isEmpty()) && (alfa<beta)) {
				F = nodo.getSuccessorsMinimax().remove(0);
				aux = podaAlfaBeta(F, nivel+1, alfa, beta, heuristic);
				
				if (nivel % 2 == 1) {					
					if (aux.getMinimaxValue() > alfa) {
						alfa = aux.getMinimaxValue();
						nodo_a_devolver = F;
					}				
				} else {
					if (aux.getMinimaxValue() < beta) {
						beta = aux.getMinimaxValue();
						nodo_a_devolver = F;
					}
				}
			}
			
			if (nivel % 2 == 1) {
				nodo_a_devolver.setMinimaxValue(alfa);
			} else nodo_a_devolver.setMinimaxValue(beta);
			
		}

		return nodo_a_devolver;
	}
	
	
	
	/**
	 * S'utilitza per sumar tots els puntets de les fitxes en cas de que es quedi en empat.
	 * @return - enter amb la suma de tots els puntets.
	 */
	public int sumPoints() {
		int suma = 0;
		for (Ficha ficha : this.fichasJugador) {
			suma = suma + ficha.getNum1() + ficha.getNum2();
		}
		return suma;
	}



	/**
	 * És un setter de la llista de possibles tirades del jugador a partir d'una llista de fitxes.
	 * @param possiblesTiradas - Llista de fitxes
	 */
	public void setPossiblesTiradas(List<Ficha> possiblesTiradas) {
		List<Ficha> aux = new ArrayList<Ficha>();
		for(Ficha ficha : possiblesTiradas) {
			aux.add(ficha);
		}
		this.possiblesTiradas = aux;
	}
	


	/**
	 * És un setter de la llista de possibles tirades del jugador que ho fa a partir de dos números,
	 * costat esquerre i costat dret del taulell. Amb aquests número mira quines fitxes de les que té a la
	 * mà poden encaixar amb aquests números.
	 * @param num1 - Costat esquerre del taulell
	 * @param num2 - Costat dret del taulell
	 */
	public void setPossiblesTiradas(int num1, int num2) {
		List<Ficha> aux = new ArrayList<Ficha>();
		for(Ficha ficha : this.fichasJugador) {
			if (ficha.hasInt(num1) || ficha.hasInt(num2)) {
				aux.add(ficha);
			}
		}
		this.possiblesTiradas = aux;
	}
	
	
	
	/**
	 * Retorna una fitxa a partir de la seva posició en la llista de possibles tirades del jugador.
	 * @param id - enter que fa referència a la fitxa de la llista
	 * @return fitxa de tipus Ficha
	 */
	public Ficha getPossibleFicha(int id) {
		return this.possiblesTiradas.get(id);
	}
	
	

	/**
	 * GETTERS I SETTERS
	 */	
	
	public List<Ficha> getFichasJugador() {
		return fichasJugador;
	}	
	
	public String getNom() {
		return nom;
	}
	
	public List<Ficha> getPossiblesTiradas() {
		return possiblesTiradas;
	}

	public void setFichasJugador(List<Ficha> fichasJugador) {
		this.fichasJugador = fichasJugador;
	}

	public boolean isMaquina() {
		return maquina;
	}
	

	
	

	@Override
	public String toString() {
		return nom;
	}
	
	
	
	@Override
    public boolean equals(Object o) { 
  
        // If the object is compared with itself then return true   
        if (o == this) { return true; } 
  
        /* Check if o is an instance of Jugador or not "null instanceof [type]" also returns false */
        if (!(o instanceof Jugador)) { return false; }
          
        // typecast o to Jugador so that we can compare data members  
        Jugador c = (Jugador) o; 
          
        // Compare the data members and return accordingly  
        return this.nom.equals(c.getNom());
    }
	
	
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		
		List<Ficha> fichasJugadorCloned = new ArrayList<Ficha>(this.fichasJugador);
		List<Ficha> possiblesTiradasCloned = new ArrayList<Ficha>(this.possiblesTiradas);
		
		Jugador cloned = (Jugador) super.clone();
		cloned.setFichasJugador(fichasJugadorCloned);
		cloned.setPossiblesTiradas(possiblesTiradasCloned);
		return cloned;
	}	
}