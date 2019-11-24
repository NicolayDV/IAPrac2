package test;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

/**
 * @author nikolay.dimitrov@estudiants.urv.cat
 */
public class DominoBox {
	
	private List<Ficha> fichas_domino;
	
	
	
	/**
	 * Constructor de la classe que genera totes les fitxes possibles a llista
	 * de fitxes de la caixa del dòmino. 
	 */
	public DominoBox() {
		
		fichas_domino = new ArrayList<Ficha>();
		Ficha ficha_nova;
		
		for(int i = 0; i <= 6; i++) {			
			for(int j = i; j <= 6; j++) {
				ficha_nova = new Ficha(i,j);
				fichas_domino.add(ficha_nova);				
			}			
		}
	}
	
	
	
	/**
	 * Mètode que barreja les fitxes i les reparteix als dos jugadors passats per paràmetre.
	 * @param jugador1 - Un dels jugadors.
	 * @param jugador2 - L'altre jugador.
	 */
	public void repartirFichas(Jugador jugador1, Jugador jugador2) {
		Collections.shuffle(this.fichas_domino);
		for (int i = 0; i<28; i++) {
			jugador1.addFicha(this.fichas_domino.get(i++));
			jugador2.addFicha(this.fichas_domino.get(i));
		}
	}
}