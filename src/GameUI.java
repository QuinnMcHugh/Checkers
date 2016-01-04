import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by quinn on 12/24/15.
 */
public class GameUI {
	private Game game;
	private Point selectedPoint;
	
	public void setGame(Game game){ this.game = game; }
	
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
		System.out.println("     0   1   2   3   4   5   6   7");
		
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
		lines[10] = lines[10].substring(0, lines[10].length() - 1) + "      listed, pairs separated by a space\n";
		
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
	
	private boolean inputSyntaxIsValid(String line){
		Scanner s = new Scanner(line);
		String command = s.next().toUpperCase();
		
		/* The cases for SELECT and MOVE commands are nearly identical in checking for syntax validity */
		if (command.equals("SELECT") || command.equals("MOVE")){
			int endCommandIndex;
			
			if (command.equals("SELECT")){
				endCommandIndex = line.toUpperCase().indexOf("SELECT") + "SELECT".length();
			}
			else {
				endCommandIndex = line.toUpperCase().indexOf("MOVE") + "MOVE".length();
			}
			
			line = line.substring(endCommandIndex);
			
			/* Remove all whitespace from string */
			line = line.replaceAll("\\s+","");
			int index = 0;
			
			/* If first non-whitespace character following SELECT is '(' */
			if (line.charAt(index) == '('){
				index++;
			}
			else {
				return false;
			}
			
			Point input = new Point();
			try {
				/* Get x coordinate as everything between opening '(' and ',' */
				int x = Integer.parseInt(line.substring(index, line.indexOf(',')));
				index = line.indexOf(',') + 1;

				int y = Integer.parseInt(line.substring(index, line.indexOf(')')));
				index = line.indexOf(')') + 1;
				
				input.x = x;
				input.y = y;
			}
			catch (NumberFormatException e){
				e.printStackTrace();
				return false;
			}
			
			/* If there is anything following the closing paren ')', invalid input */
			if (index != line.length()){
				return false;
			}
		}
		else if (command.equals("JUMP")){
			int endCommandIndex = line.toUpperCase().indexOf("JUMP") + "JUMP".length();
			line = line.substring(endCommandIndex);
			
			/* Remove all whitespace from string */
			line = line.replaceAll("\\s+","");
			int index;
			
			while (line.length() > 0){
				index = 0;
				
				/* If first non-whitespace character following MOVE is '(' */
				if (line.charAt(index) == '('){
					index++;
				}
				else {
					return false;
				}
				
				Point input = new Point();
				try {
					/* Get x coordinate as everything between opening '(' and ',' */
					int x = Integer.parseInt(line.substring(index, line.indexOf(',')));
					index = line.indexOf(',') + 1;

					int y = Integer.parseInt(line.substring(index, line.indexOf(')')));
					index = line.indexOf(')') + 1;

					input.x = x;
					input.y = y;
				}
				catch (NumberFormatException e){
					e.printStackTrace();
					return false;
				}
				
				line = line.substring(index);
			}
		}
		else {
			return false;
		}
		return true;
	}
	
	public Move getPlayerMove(boolean firstCall){
		if (!firstCall){
			System.out.print(">");
		}
		
		Scanner s = new Scanner(System.in);
		String line = s.nextLine();
		
		s = new Scanner(line);
		String command = s.next().toUpperCase();
		
		Move move = new Move();
		move.turnColor = game.getTurn();
		
		if (inputSyntaxIsValid(line)){
			if (command.equals("SELECT")){
				int endCommandIndex = line.toUpperCase().indexOf("SELECT") + "SELECT".length();
				line = line.substring(endCommandIndex);
			
				/* Remove all whitespace from string */
				line = line.replaceAll("\\s+","");
				int index = 1;
				
				/* Get x coordinate as everything between opening '(' and ',' */
				int x = Integer.parseInt(line.substring(index, line.indexOf(',')));
				index = line.indexOf(',') + 1;

				int y = Integer.parseInt(line.substring(index, line.indexOf(')')));
				index = line.indexOf(')') + 1;

				selectedPoint = new Point(x, y);
				move = getPlayerMove(false);
			}
			else if (command.equals("MOVE") && selectedPoint != null){
				int endCommandIndex = line.toUpperCase().indexOf("MOVE") + "MOVE".length();
				line = line.substring(endCommandIndex);
				
				/* Remove all whitespace from string */
				line = line.replaceAll("\\s+","");
				int index = 1;
				
				/* Get x coordinate as everything between opening '(' and ',' */
				int x = Integer.parseInt(line.substring(index, line.indexOf(',')));
				index = line.indexOf(',') + 1;

				int y = Integer.parseInt(line.substring(index, line.indexOf(')')));
				index = line.indexOf(')') + 1;
				
				Point destination = new Point(x, y);
				ArrayList<Point> jumps = new ArrayList<Point>();
				jumps.add(destination);
				
				move.startPoint = selectedPoint;
				move.jumps = jumps;
				
				if (Math.abs(move.jumps.get(0).x - move.startPoint.x) > 1){
					move = null;
				}
			}
			else if (command.equals("JUMP") && selectedPoint != null){
				ArrayList<Point> jumps = new ArrayList<Point>();
				
				int endCommandIndex = line.toUpperCase().indexOf("JUMP") + "JUMP".length();
				line = line.substring(endCommandIndex);
			
				/* Remove all whitespace from string */
				line = line.replaceAll("\\s+","");
				int index = 0;
				
				while (line.length() > 0){
					/* First non-whitespace character is '(' */
					index = 1;

					Point input = new Point();
					try {
						/* Get x coordinate as everything between opening '(' and ',' */
						int x = Integer.parseInt(line.substring(index, line.indexOf(',')));
						index = line.indexOf(',') + 1;

						int y = Integer.parseInt(line.substring(index, line.indexOf(')')));
						index = line.indexOf(')') + 1;

						input.x = x;
						input.y = y;
					}
					catch (NumberFormatException e){
						e.printStackTrace();
					}
					jumps.add(input);
					
					line = line.substring(index);
				}
				
				move.jumps = jumps;
				move.startPoint = selectedPoint;
			}
		}
		else {
			return null;
		}
		
		/* This resets which piece is selected each turn */
		selectedPoint = null;
		
		return move;
	}
}
