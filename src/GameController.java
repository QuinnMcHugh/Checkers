/**
 * Created by quinn on 12/30/15.
 */
public class GameController {
	private Game game;
	private GameUI ui;
	
	public GameController(){
		game = new Game();
		ui = new GameUI();
		ui.setGame(game);
	}
	
	public void play(){
		/* Outer loop runs turn by turn */
		while (!game.isGameOver()){
			
			/* Inner Loop ensures that a valid move is entered */
			Move move;
			do {
				ui.printGameState();
				move = ui.getPlayerMove(true);
			} while (move == null || !move.isComplete());
			
			if (game.processMove(move)){
				game.changeTurn();
			}
		}
	}
}
