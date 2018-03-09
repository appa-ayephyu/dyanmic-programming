package mainpackage;

import basicdatastructures.SurveillancePatrols;

public class DefenderStrategy {
	int[][] defenderStrategy;

	public DefenderStrategy(int n, int tau, SurveillancePatrols[] patrols) {
		defenderStrategy = new int[n][tau];
		for (int i = 0; i < n; i++) {
			for (int k = 0; k < tau; k++) {
				for (int r = 0; r < patrols.length; r++) {
					if (patrols[r].getSurveillanceMatrix()[i][k] == 1) {
						defenderStrategy[i][k] = 1;
					}
				}
			}
		}

	}
	
	public int[][] getDefenderStrategy(){
		return defenderStrategy;
	}
}
