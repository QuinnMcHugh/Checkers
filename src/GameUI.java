import java.awt.*;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by quinn on 12/24/15.
 */
public class GameUI {
	Game game;
	private Point selectedPoint;
	
	public GameUI(){
		game = new Game();
	}
	
	public void printGameState(){
		/* Clears Screen */
		System.out.print("\033[H\033[2J");
		System.out.flush();
		
		/* Prints out the game header */
		System.out.println(" ######  ##     ## ########  ######  ##    ## ######## ########   ######  \n" +
						   "##    ## ##     ## ##       ##    ## ##   ##  ##       ##     ## ##    ## \n" +
						   "##       ##     ## ##       ##       ##  ##   ##       ##     ## ##       \n" +
						   "##       ######### ######   ##       #####    ######   ########   ######  \n" +
						   "##       ##     ## ##       ##       ##  ##   ##       ##   ##         ## \n" +
						   "##    ## ##     ## ##       ##    ## ##   ##  ##       ##    ##  ##    ## \n" +
						   " ######  ##     ## ########  ######  ##    ## ######## ##     ##  ######  ");
		
		System.out.println();
		System.out.println();
		System.out.println();
		
		/* Add game commands to output */
		String[] lines = game.getBoard().toStringLines();
		lines[2] = lines[2].substring(0, lines[2].length() - 1) + "               Game Commands\n";
		lines[3] = lines[3].substring(0, lines[3].length() - 1) + "   -------------------------------------\n";
		lines[4] = lines[4].substring(0, lines[4].length() - 1) + "   - SELECT (x, y) - Selects the piece \n";
		lines[5] = lines[5].substring(0, lines[5].length() - 1) + "      at (x, y) so it may be moved.\n";
		lines[6] = lines[6].substring(0, lines[6].length() - 1) + "   - MOVE (x, y) - Advances the selected\n";
		lines[7] = lines[7].substring(0, lines[7].length() - 1) + "      piece to (x, y)  \n";
		lines[8] = lines[8].substring(0, lines[8].length() - 1) + "   - JUMP [(x, y)] - Jumps the selected\n";
		lines[9] = lines[9].substring(0, lines[9].length() - 1) + "      piece to every coordinate pair \n";
		lines[10] = lines[10].substring(0, lines[10].length() - 1) + "      listed\n";
		
		/* Print out game board with commands */
		for (int i = 0; i < lines.length; i++){
			System.out.print(lines[i]);
		}
		
		/* Print out the player prompt */
		System.out.println();
		
		String colorOfTurn = "";
		if (game.getTurn() == Move.RED){
			colorOfTurn = "RED";
		}
		else if (game.getTurn() == Move.BLACK){
			colorOfTurn = "BLACK";
		}
		
		System.out.print(colorOfTurn + " Player's turn\n" +
			"-----------------\n" +
			">");
		
	}
	
	public Move getPlayerMove(){
		Scanner s = new Scanner(System.in);
		String line = s.nextLine();
		
		s = new Scanner(line);
		String command = s.next().toUpperCase();
		
		Move move = new Move();
		if (command.equals("SELECT")){
			move.turnColor = game.getTurn();
		}
		else if (command.equals("MOVE")){
			
		}
		else if (command.equals("JUMP")){
			
		}
		return move;
	}
}
