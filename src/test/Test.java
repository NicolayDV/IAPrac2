package test;

import java.util.Scanner;

/**
 * @author nikolay.dimitrov@estudiants.urv.cat
 */
public class Test {
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////// CONFIGURACIÓ //////////////////////////////////////////////
	public static int MAX_MINIMAX = 8;
	public static int MAX_ALPHABETA = 11;	
	
	public static String nomJugador1 = "Player1";
	public static boolean P1maquina = true; //true(machine) or false(human)
	public static String P1Algorithm = "minimax"; //minimax or alphabeta
	public static String P1Heuristic = "H1"; //H1, H2 or H3
	
	public static String nomJugador2 = "Player2";
	public static boolean P2maquina = true; //true(machine) or false(human)
	public static String P2Algorithm = "alphabeta"; //minimax or alphabeta
	public static String P2Heuristic = "H2"; //H1, H2 or H3
	///////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////
	

	
	
	static Scanner scanner = new Scanner(System.in);
	
	public static void main(String args[]) {
		
		DominoBox joc = new DominoBox();
		
		Jugador jugador1 = new Jugador(nomJugador1, P1maquina);
		Jugador jugador2 = new Jugador(nomJugador2, P2maquina);
		
		joc.repartirFichas(jugador1, jugador2);
		
		Tablero tablero = new Tablero(jugador1, jugador2);	
		Tablero tableroActual = null;
		
		long start = 0;	
		
		while (true) {
			
			System.out.println("\n\n\n\n\n\n\nTorn: " + tablero.getTornActual() + "\n");
			System.out.println(tablero);			
			
			if (!tablero.getTornActual().isMaquina()) {
				System.out.println("\n[" + tablero.getTornActual() + "] Decideix fitxa:");
				int option = Integer.parseInt(scanner.nextLine());
				if (tablero.getTornActual().getPossibleFicha(option).canBeBothSides(tablero.getLado1(), tablero.getLado2())) {
					System.out.println("\n[" + tablero.getTornActual() + "] Quin costat? [0 = esquerra, 1 = dreta]:");
					int costat = Integer.parseInt(scanner.nextLine());
					tablero.addFicha(tablero.getTornActual().getPossibleFicha(option), costat);
				} else tablero.addFicha(tablero.getTornActual().getPossibleFicha(option));
			} else {
				System.out.print("\n[" + tablero.getTornActual() + "] Decidint fitxa... ");
				start = System.currentTimeMillis();
								
				if ((tablero.getTornActual().equals(jugador1)) && (P1Algorithm.equals("minimax"))) {
					tableroActual = tablero.getTornActual().minimax(tablero, 1, P1Heuristic);
				} else if ((tablero.getTornActual().equals(jugador1)) && (P1Algorithm.equals("alphabeta"))) {
					tableroActual = tablero.getTornActual().podaAlfaBeta(tablero, 1, -2147483648, 2147483647, P1Heuristic);
				} else if ((tablero.getTornActual().equals(jugador2)) && (P2Algorithm.equals("minimax"))) {
					tableroActual = tablero.getTornActual().minimax(tablero, 1, P2Heuristic);
				} else {
					tableroActual = tablero.getTornActual().podaAlfaBeta(tablero, 1, -2147483648, 2147483647, P2Heuristic);
				}
				
				
				System.out.println(tableroActual.getLastMove());
				System.out.println("Ha trigat en prendre la decisió: " + (System.currentTimeMillis()-start) + " ms");
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