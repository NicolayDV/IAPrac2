package test;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class Jugador implements Cloneable{

	String nom;
	List<Ficha> fichasJugador;
	List<Ficha> possiblesTiradas;
	boolean maquina;
	
	private static int MAX_MINIMAX = 2;
	
	
	
	public Jugador(String nom, boolean maquina) {
		this.nom = nom;
		this.maquina = maquina;
		this.fichasJugador = new ArrayList<Ficha>();
		this.possiblesTiradas = new ArrayList<Ficha>();		
	}
	
	
	
	public void addFicha(Ficha ficha) {
		this.fichasJugador.add(ficha);
	}

	
	
	public boolean hasFicha(Ficha ficha) {
		return fichasJugador.contains(ficha) ? true : false;
	}
	
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
	
	
	public int sumPoints() {
		int suma = 0;
		for (Ficha ficha : this.fichasJugador) {
			suma = suma + ficha.getNum1() + ficha.getNum2();
		}
		return suma;
	}



	public void setPossiblesTiradas(List<Ficha> possiblesTiradas) {
		List<Ficha> aux = new ArrayList<Ficha>();
		for(Ficha ficha : possiblesTiradas) {
			aux.add(ficha);
		}
		this.possiblesTiradas = aux;
	}


	public void setPossiblesTiradas(int num1, int num2) {
		List<Ficha> aux = new ArrayList<Ficha>();
		for(Ficha ficha : this.fichasJugador) {
			if (ficha.hasInt(num1) || ficha.hasInt(num2)) {
				aux.add(ficha);
			}
		}
		this.possiblesTiradas = aux;
	}
	
	
	public Ficha getPossibleFicha(int id) {
		return this.possiblesTiradas.get(id);
	}
	
	
	public int getHeuristic(Tablero tablero) {
		Ficha ficha = tablero.getLastMove();
		System.out.println(ficha + " + " + ((ficha.getNum1()+1) * (ficha.getNum2()+1)));
		return (ficha.getNum1()+1) * (ficha.getNum2()+1);
		
		
		
//		Hashtable<Integer,Integer> countFichas = new Hashtable<Integer,Integer>();
//		for (int i=0; i<7; i++) countFichas.put(i, 0);
//		for (Ficha ficha : tablero.getFichasTablero()) {
//			countFichas.put(ficha.getNum1(), countFichas.get(ficha.getNum1())+1);
//			countFichas.put(ficha.getNum2(), countFichas.get(ficha.getNum2())+1);
//		}
//		for (Ficha ficha : tablero.getTornActual().getFichasJugador()) {
//			countFichas.put(ficha.getNum1(), countFichas.get(ficha.getNum1())+1);
//			countFichas.put(ficha.getNum2(), countFichas.get(ficha.getNum2())+1);
//		}		
//		System.out.println(countFichas + " + " + tablero.getLastMove() + " + " + tablero.getTornActual() + " + " + (countFichas.get(tablero.getLado1()) * countFichas.get(tablero.getLado2())));
//		return countFichas.get(tablero.getLado1()) * countFichas.get(tablero.getLado2());
		
		
//		Hashtable<Integer,Integer> countFichas = new Hashtable<Integer,Integer>();
//		for (int i=0; i<7; i++) countFichas.put(i, 0);
//		for (Ficha ficha : tablero.getTornActual().getFichasJugador()) {
//			countFichas.put(ficha.getNum1(), countFichas.get(ficha.getNum1())+1);
//			countFichas.put(ficha.getNum2(), countFichas.get(ficha.getNum2())+1);
//		}
//		countFichas.put(tablero.getLastMove().getNum1(), countFichas.get(tablero.getLastMove().getNum1())+1);
//		countFichas.put(tablero.getLastMove().getNum2(), countFichas.get(tablero.getLastMove().getNum2())+1);
//		System.out.println(countFichas + " + " + (countFichas.get(tablero.getLastMove().getNum1()) + countFichas.get(tablero.getLastMove().getNum2())) + " + " + tablero.getLastMove());
//		return countFichas.get(tablero.getLastMove().getNum1()) + countFichas.get(tablero.getLastMove().getNum2());
		
		
		
	}
	
	
	public Tablero minimax(Tablero nodo, int nivel) {
		//Ficha result = null;
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
		} else if (nivel == MAX_MINIMAX) {
			nodo_a_devolver = new Tablero(null, null);
			nodo_a_devolver.setMinimaxValue(this.getHeuristic(nodo));
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
				aux = minimax(F, nivel+1);
				
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
	
	
	
	
//	public Tablero minimax(Tablero nodo, int nivel) {
//		//Ficha result = null;
//		Tablero nodo_a_devolver = null, F = null, aux = null;
//		int valor_a_devolver = 0;
//		int masInfinito = 2147483647;
//		int menosInfinito = -2147483648;
//		
//		if (nodo.hasWinner()) {
//			nodo_a_devolver = new Tablero(null, null);
//			if (nivel % 2 == 1) nodo_a_devolver.setMinimaxValue(masInfinito);
//			else nodo_a_devolver.setMinimaxValue(menosInfinito);
//			return nodo_a_devolver;
//		} else if (nivel == MAX_MINIMAX) {
//			nodo_a_devolver = new Tablero(null, null);
//			nodo_a_devolver.setMinimaxValue(this.getHeuristic(nodo));
//			return nodo_a_devolver;
//		} else {
//		
//			nodo_a_devolver = new Tablero(null, null);
//			
//			if (nivel % 2 == 1) valor_a_devolver = menosInfinito;
//			else valor_a_devolver = masInfinito;
//			
//			nodo.setSuccessors();
//			while (!nodo.getSuccessorsMinimax().isEmpty()) {
//				F = nodo.getSuccessorsMinimax().remove(0);
//				aux = minimax(F, nivel+1);
//				
//				if (nivel % 2 == 1) {					
//					if (aux.getMinimaxValue() > valor_a_devolver) {
//						valor_a_devolver = aux.getMinimaxValue();
//						nodo_a_devolver = F;
//					}				
//				} else {
//					if (aux.getMinimaxValue() < valor_a_devolver) {
//						valor_a_devolver = aux.getMinimaxValue();
//						nodo_a_devolver = F;
//					}
//				}
//			}		
//		}
//
//		return nodo_a_devolver;
//	}
	
	
	
	
	public Tablero podaAlfaBeta (Tablero nodo, int nivel, int alfa, int beta) {
		//Ficha result = null;
		Tablero nodo_a_devolver = null, F = null, aux = null;
		int masInfinito = 2147483647;
		
		if (nodo.hasWinner()) {
			nodo_a_devolver = new Tablero(null, null);
//			if (nivel % 2 == 1) nodo_a_devolver.setMinimaxValue(masInfinito);
//			else nodo_a_devolver.setMinimaxValue(menosInfinito);
			nodo_a_devolver.setMinimaxValue(masInfinito);
			return nodo_a_devolver;
		} else if (nivel == MAX_MINIMAX) {
			nodo_a_devolver = new Tablero(null, null);
			nodo_a_devolver.setMinimaxValue(this.getHeuristic(nodo));
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
				aux = podaAlfaBeta(F, nivel+1, alfa, beta);
				
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
