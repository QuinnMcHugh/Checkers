import java.awt.*;

/**
 * Created by quinn on 12/19/15.
 */
public class Game {
	private Board board;
	private char turn;
	
	public Board getBoard(){ return board; }
	public char getTurn(){ return turn; }
	
	public Game(){
		board = new Board();
		turn = Move.RED;
	}
	
	public boolean isGameOver(){
		int redCount = 0, blackCount = 0;
		for (int i = 0; i < Board.ROWS; i++){
			for (int j = 0; j < Board.COLUMNS; j++){
				int spot = board.matrix[i][j];
				if (spot != Board.EMPTY){
					if (spot == Board.RED_KING || spot == Board.RED_SOLDIER){
						redCount++;
					}
					else {
						blackCount++;
					}
				}
			}
		}
		return (redCount == 0) || (blackCount == 0);
	}
	
	private void changeTurn(){
		if (turn == Move.RED){
			turn = Move.BLACK;
		}
		else {
			turn = Move.RED;
		}
	}
	
	public boolean processMove(Move move){
		int[][] matrixCopy = board.getMatrixCopy();
		if (makeMove(move)){
			return true;
		}
		else {
			board.setMatrix(matrixCopy);
			return false;
		}
	}
	
	private boolean makeMove(Move move){
		/* If every previous jump has executed successfully, return true. */
		if (move.jumps.size() == 0){
			return true;
		}
		else {
			/* Else, there are still more jumps to examine to ensure the rules are being followed */
			
			/* The start point of the piece being moved must be on the board */
			if (move.startPoint.x >= Board.COLUMNS || move.startPoint.x < 0){
				return false;
			}
			else if (move.startPoint.y >= Board.ROWS || move.startPoint.y < 0){
				return false;
			}
		
			/* The piece trying to be moved must match the color of the turn */
			int piece = board.matrix[move.startPoint.y][move.startPoint.x];
			if (move.turnColor == Move.RED){
				if (piece != Board.RED_SOLDIER && piece != Board.RED_KING){
					return false;
				}
			}
			else if (move.turnColor == Move.BLACK){
				if (piece != Board.BLACK_SOLDIER && piece != Board.BLACK_KING){
					return false;
				}
			}
		
			/* Verify the next move is valid */
			Point nextPoint = move.jumps.get(0);
		
			/* Verify that "nextPoint" is within the dimensions of the board*/
			if (nextPoint.x >= Board.COLUMNS || nextPoint.x < 0){
				return false;
			}
			if (nextPoint.y >= Board.ROWS || nextPoint.y < 0){
				return false;
			}
			
			/* Verify that "nextPoint" is currently not inhabited by anything */
			if (board.matrix[nextPoint.y][nextPoint.x] != Board.EMPTY){
				return false;
			}
			
			int deltaX = nextPoint.x - move.startPoint.x;
			int deltaY = nextPoint.y - move.startPoint.y;

			/* Movements must be diagonal */
			if (Math.abs(deltaX) != Math.abs(deltaY)){
				return false;
			}
			
			/* At most you can move two squares diagonally in a single movement */
			if (Math.abs(deltaX) > 2){
				return false;
			}
			
			/* A jump must consist of actually moving a piece to somewhere */
			if (deltaX == 0){
				return false;
			}
			
			/* Make sure that any backwards movement is being done by a "King" */			
			if (move.turnColor == Move.BLACK){
				/* Because the board is set up with black occupying the top half, 
				* a positive deltaY would indicate a black piece moving forward, 
				* and a negative one would indicate backwards movement (only 
				* allowed by Kings.)*/
				
				if (deltaY < 0){
					if (board.matrix[move.startPoint.y][move.startPoint.x] != Board.BLACK_KING){
						return false;
					}
				}
 			}
			else if (move.turnColor == Move.RED){
				/* Because the board is set up with red occupying the bottom half, 
				* a positive deltaY would indicate a red piece moving backwards 
				* (only allowed by Kings),	 and a negative one would indicate 
				* forward movement. */

				if (deltaY > 0){
					if (board.matrix[move.startPoint.y][move.startPoint.x] != Board.RED_KING){
						return false;
					}
				}
 			}
			
			/* Now must handle all legitimate single movements which can be made */
			
			/* If no jump is being made, just an advancement of a piece */
			if (Math.abs(deltaX) == 1){
				/* Move the piece one square */
				board.matrix[nextPoint.y][nextPoint.x] = board.matrix[move.startPoint.y][move.startPoint.x];
				board.matrix[move.startPoint.y][move.startPoint.x] = Board.EMPTY;
			}
			else {
				/* A jump is being made */
				if (!processJump(move.startPoint, nextPoint, move.turnColor)){
					return false;
				}
			}
			
			move.startPoint = nextPoint;
			move.jumps.remove(0);
			
			return makeMove(move);
		}
	}
	
	/* This method will simulate the jump being requested by removing captured pieces */
	public boolean processJump(Point startPoint, Point endPoint, char turnColor){
		Point capturedPoint = new Point((endPoint.x + startPoint.x) / 2, (endPoint.y + startPoint.y) / 2);
		
		/* The color of the captured piece must be of enemy color */
		int capturedColor = board.matrix[capturedPoint.y][capturedPoint.x];
		if (turnColor == Move.RED){
			if (capturedColor != Board.BLACK_KING && capturedColor != Board.BLACK_SOLDIER){
				return false;
			}
		}
		else if (turnColor == Move.BLACK){
			if (capturedColor != Board.RED_KING && capturedColor != Board.RED_SOLDIER){
				return false;
			}
		}
		
		/* Eliminate the captured piece and advance chip */
		board.matrix[capturedPoint.y][capturedPoint.x] = Board.EMPTY;
		board.matrix[endPoint.y][endPoint.x] = board.matrix[startPoint.y][startPoint.x];
		board.matrix[startPoint.y][startPoint.x] = Board.EMPTY;
		
		/* Check to see if piece must be "kinged" */
		if (turnColor == Move.RED){
			if (endPoint.y == 0){
				board.matrix[endPoint.y][endPoint.x] = Board.RED_KING;
			}
		}
		else if (turnColor == Move.BLACK){
			if (endPoint.y == 7){
				board.matrix[endPoint.y][endPoint.x] = Board.BLACK_KING;
			}
		}
		
		return true;
	}
}
