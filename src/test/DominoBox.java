package test;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class DominoBox {
	
	private List<Ficha> fichas_domino;
	
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
	
	
	public void repartirFichas(Jugador jugador1, Jugador jugador2) {
		Collections.shuffle(this.fichas_domino);
		for (int i = 0; i<28; i++) {
			jugador1.addFicha(this.fichas_domino.get(i++));
			jugador2.addFicha(this.fichas_domino.get(i));
		}
	}

}
