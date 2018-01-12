package mainpackage;

public class GameSolution {
	double[][][] defenderStrategy;
	AttackerPureStrategy r;
	double solutionValue;
	
	public GameSolution(double[][][] defenderStrategy,  double solutionValue) {
		super();
		this.defenderStrategy = defenderStrategy;
		//this.r = r;
		this.solutionValue = solutionValue;
	}
	
	public GameSolution() {
		
	}
}
