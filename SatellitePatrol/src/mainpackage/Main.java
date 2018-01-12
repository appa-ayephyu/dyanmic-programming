package mainpackage;

public class Main {
	
	static Game game;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		createGame();
		//Example2.solveMe();
	}
	
	public static void createGame() {
		game = new Game(15,15,10);
		//game.U = 20;
		GameSolution sol = new SatellitePatrolGame().run(game, true, true);
	}

}

