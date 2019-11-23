package test;

import java.util.Scanner;

public class Test {
	
	static Scanner scanner = new Scanner(System.in);
	
	public static void main(String args[]) {
		
		DominoBox joc = new DominoBox();
		
		Jugador jugador1 = new Jugador("Player1", true);
		Jugador jugador2 = new Jugador("Player2", true);
		
		joc.repartirFichas(jugador1, jugador2);
		
		Tablero tablero = new Tablero(jugador1, jugador2);	
		Tablero tableroActual = null;
		
		
		
		while (true) {
			
			System.out.println("\n\n\n\n\n\n\nTorn: " + tablero.getTornActual() + "\n");
			System.out.println(tablero);
			
			
			
			if (!tablero.getTornActual().isMaquina()) {
				System.out.println("\n[" + tablero.getTornActual() + "] Decideix fitxa:");
				int option = Integer.parseInt(scanner.nextLine());
				if (tablero.getTornActual().getPossibleFicha(option).canBeBothSides(tablero.getLado1(), tablero.getLado2())) {
					System.out.println("CanBeBothSides: " + tablero.getTornActual().getPossibleFicha(option).canBeBothSides(tablero.getLado1(), tablero.getLado2()));
					System.out.println("\n[" + tablero.getTornActual() + "] Quin costat? [0 = esquerra, 1 = dreta]:");
					int costat = Integer.parseInt(scanner.nextLine());
					tablero.addFicha(tablero.getTornActual().getPossibleFicha(option), costat);
				} else tablero.addFicha(tablero.getTornActual().getPossibleFicha(option));
			} else {
				System.out.print("\n[" + tablero.getTornActual() + "] Decidint fitxa... ");
				tableroActual = tablero.getTornActual().podaAlfaBeta(tablero, 1, -2147483648, 2147483647);
				System.out.println(tableroActual.getLastMove());
				tablero.addFicha(tableroActual.getLastMove(), tableroActual.getLastSide());
			}
			
			
			tablero.swapPlayer();
			
			
			
			if ((tablero.getTornActual().getPossiblesTiradas().isEmpty()) && (!tablero.getTornActual().getFichasJugador().isEmpty())) {
				System.out.println("\nINFO: " + tablero.getTornActual() + " no te possibles moviments.");
				tablero.swapPlayer();
				if ((tablero.getTornActual().getPossiblesTiradas().isEmpty()) && (!tablero.getTornActual().getFichasJugador().isEmpty())) {
					System.out.println("\nINFO: " + tablero.getTornActual() + " tampoc te possibles moviments.");
				}
			}
			
			if (tablero.hasWinner()) break;
			
			
		}
		
		System.out.println("\n\n\n\n\n\n\n" + tablero);
		System.out.println(tablero.whoIsWinner());
				
	}
}
