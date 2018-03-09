package mainpackage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;


import basicdatastructures.SurveillancePatrols;
import ilog.concert.IloException;
import ilog.concert.IloLinearNumExpr;
import ilog.concert.IloNumVar;
import ilog.cplex.IloCplex;

public class CDOG {
	Game game;
	double failvalue = 1010101; // I don't know what is this
	List<AttackersPureStrategy> setOfattackersStrategiesY = new ArrayList<AttackersPureStrategy>();
	List<DefenderStrategy> defendersPureStrategies = new ArrayList<DefenderStrategy>();

	public void initialize(Game game, boolean CLP, boolean AOonly) {
		initialize(game, CLP, AOonly, new ArrayList<AttackersPureStrategy>());
		setOfattackersStrategiesY = createAllAttackersStrategies();
		defendersPureStrategies = createAllDefendersPureStrategies();
	}

	public void initialize(Game game, boolean CLP, boolean AOonly,
			List<AttackersPureStrategy> setOfattackersStrategiesY) {

		this.game = game;
		this.setOfattackersStrategiesY = setOfattackersStrategiesY;

	}

	public GameSolution run(Game g, boolean CLP, boolean AOonly) {
		return run(g, CLP, AOonly, false);
	}

	public GameSolution run(Game g, boolean CLP, boolean AOonly, boolean smartAO) {
		initialize(g, CLP, AOonly);
		GameSolution currentSolution = new GameSolution();
		boolean graphChanged = true;
		double ctime;
		double AOtime = 0;
		double DOtime = 0;
		double CLPtime = 0;
		while (graphChanged) {
			graphChanged = false;
			ctime = System.nanoTime();
			currentSolution = linearProgram(defendersPureStrategies, setOfattackersStrategiesY);
		}
		return currentSolution;
	}

	public GameSolution linearProgram(List<DefenderStrategy> defendersPureStrategies,
			List<AttackersPureStrategy> setOfAttackersStrategiesY) {
		GameSolution result = new GameSolution();
		try {
			IloCplex cplex = new IloCplex();
			cplex.setOut(null);
			// objective
			IloNumVar utility = cplex.numVar(-Double.MAX_VALUE, Double.MAX_VALUE);
			cplex.addMaximize(utility);
			// variables
			IloNumVar[] x = new IloNumVar[defendersPureStrategies.size()];
			for (AttackersPureStrategy att : setOfAttackersStrategiesY) {

				for (int i = 0; i < x.length; i++) {
					x[i] = cplex.numVar(0.0, 1.0);
				}

				IloLinearNumExpr mixedUtility = cplex.linearNumExpr();
				for (int i = 0; i < x.length; i++) {
					double utilityOfDefenderPureStrategy = calculateUtility(defendersPureStrategies.get(i), att);
					mixedUtility.addTerm(utilityOfDefenderPureStrategy, x[i]);
				}
				mixedUtility.addTerm(1.0, utility);
				cplex.addLe(mixedUtility, 0.0, "c1");
				IloLinearNumExpr totalDistribution = cplex.linearNumExpr();
				for (int i = 0; i < x.length; i++) {
					totalDistribution.addTerm(1.0, x[i]);
				}
				cplex.addLe(totalDistribution, 1.0, "c2");
			}
			cplex.solve();
			double solutionValue = cplex.getObjValue();
			double[] mixedProbability = new double[defendersPureStrategies.size()];
			for (int i = 0; i < mixedProbability.length; i++) {
				mixedProbability[i] = cplex.getValue(x[i]);
			}
			result = new GameSolution(mixedProbability, solutionValue);
		} catch (IloException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	public double calculateUtility(DefenderStrategy defendersStrategy, AttackersPureStrategy attackersStrategy) {

		// create Indicator G_k
		int[] indicatorG = new int[game.tau];
		for (int k = 0; k < game.tau; k++) {
			int cellNumber = attackersStrategy.path.get(k);
			if (defendersStrategy.getDefenderStrategy()[cellNumber][k] == 1) {
				indicatorG[k] = 1;
			}
		}
		// probabilty of consecutive detection
		double probability = 1.0;
		for (int k = 0; k < game.tau - game.delta + 1; k++) {
			double probabilityK = 1.0;
			for (int j = k; j < k + game.delta - 1; j++) {
				probabilityK *= (1 - game.epsilon) * indicatorG[j];
			}
			probability *= 1 - probabilityK;
		}
		probability = 1 - probability;
		double utility = (1 - probability) * game.reward - probability * game.penalty;

		return utility;
	}

	public List<DefenderStrategy> createAllDefendersPureStrategies() {
		List<DefenderStrategy> defenderPureStrategiesS = new ArrayList<DefenderStrategy>();
		List<List<SurveillancePatrols>> setOfSurveillancePatrolForEachSatellite = new ArrayList<List<SurveillancePatrols>>();
		for (int r = 0; r < game.m; r++) {
			SurveillancePatrols[] patrols = createAllDefendersStrategiesForEachSatellite(game.numberofImages,
					game.candidateCells.get(r));
			setOfSurveillancePatrolForEachSatellite
					.add(Arrays.asList(patrols));
		}
		int totalVariantCount = 1;
		for (int r = 0; r < game.m; r++) {
			totalVariantCount *= setOfSurveillancePatrolForEachSatellite.get(r).size();
		}
		if(totalVariantCount <0) {
			System.out.println();
		}
		defenderPureStrategiesS = enumeratePureStrategies(setOfSurveillancePatrolForEachSatellite, totalVariantCount);
		return defenderPureStrategiesS;
	}

	public List<DefenderStrategy> enumeratePureStrategies(List<List<SurveillancePatrols>> set, int totalVariantCount) {
		List<DefenderStrategy> def = new ArrayList<DefenderStrategy>();
		List<SurveillancePatrols[]> listOfPatrols = new ArrayList<SurveillancePatrols[]>();
		for (int c = 0; c < totalVariantCount; c++) {
			SurveillancePatrols[] patrols = new SurveillancePatrols[game.m];
			listOfPatrols.add(patrols);
		}

		for (int r = 0; r < game.m; r++) {
			int variantCountofr = 1;
			for (int rr = r + 1; rr < game.m; rr++) {
				variantCountofr *= set.get(rr).size();
			}
			int strategyNumber = 0;
			for (int c = 0; c < totalVariantCount; c++) {
				SurveillancePatrols[] p = listOfPatrols.get(c);
				p[r] = new SurveillancePatrols(game.n, game.tau);
				for (int cc = 0; cc < variantCountofr; cc++) {
					p[r].setSurveillanceMatrix(set.get(r).get(strategyNumber).getSurveillanceMatrix());
				}
				if (strategyNumber < set.get(0).size() - 1) {
					strategyNumber++;
				} else {
					strategyNumber = 0;
				}

			}
		}

		for (SurveillancePatrols[] surPat : listOfPatrols) {
			def.add(new DefenderStrategy(game.n, game.tau, surPat));
		}
		return def;
	}

	public SurveillancePatrols[] createAllDefendersStrategiesForEachSatellite(int numberOfImagesleft,
			HashMap<Integer, List<Integer>> candidates) {

		int totalStrategyCount = (int) Math.pow(candidates.get(0).size() + 1, game.tau);
		SurveillancePatrols[] satelitePatrols = new SurveillancePatrols[totalStrategyCount];
		for (int c = 0; c < totalStrategyCount; c++) {
			satelitePatrols[c] = new SurveillancePatrols(game.n, game.tau);
		}
		int[] numberofImageLeft = new int[totalStrategyCount];
		for (int i = 0; i < totalStrategyCount; i++) {
			numberofImageLeft[i] = numberOfImagesleft;
		}

		for (int t = 0; t < game.tau; t++) {
			int repeatCount = totalStrategyCount / (int) Math.pow(candidates.get(0).size() + 1, t + 1);
			repeatCount = repeatCount - 1;
			int count = 0;
			int candidateCount = 0;

			for (int c = 0; c < totalStrategyCount; c++) {
				if (candidateCount != 0) {
					if (numberofImageLeft[c] > 0) {
						satelitePatrols[c].getSurveillanceMatrix()[candidates.get(t).get(candidateCount - 1)][t] = 1;
						numberofImageLeft[c]--;
					}
				}
				if (count < repeatCount) {
					count++;
				} else {
					count = 0;
					if (candidateCount < candidates.get(0).size())
						candidateCount++;
					else {
						candidateCount = 0;
					}
				}
				if (t % game.gamma == 0) {
					numberofImageLeft[c] = game.numberofImages;
				}
			}

		}
		return satelitePatrols;
	}

	public List<AttackersPureStrategy> createAllAttackersStrategies() {
		List<AttackersPureStrategy> attackersPureStrategiesY = new ArrayList<AttackersPureStrategy>();
		for (int i = 0; i < game.getNumberOfNodes(); i++) {
			List<List<Integer>> paths = createAllAttackersStrategiesFrom(i, 0);
			paths.size();
			for (List<Integer> path : paths) {
				Collections.reverse(path);
				path = transformAttackersPathToOneStepInterval(path, game.timeToSail);
				attackersPureStrategiesY.add(new AttackersPureStrategy(path));
			}
		}
		return attackersPureStrategiesY;
	}

	public List<Integer> transformAttackersPathToOneStepInterval(List<Integer> path, int timeToSail) {
		
		List<Integer> newPath = new ArrayList<Integer>();
		for (int k = 0; k < path.size() - 1; k++) {
			newPath.add(path.get(k));
			if (timeToSail > 2) {
				for (int j = 0; j < timeToSail / 2; j++) {
					newPath.add(path.get(k));
				}
				for (int j = timeToSail / 2; j < timeToSail; j++) {
					newPath.add(path.get(k + 1));
				}
			}else if(timeToSail ==2) {
				newPath.add(path.get(k));
			}
			
		}
		if(timeToSail==1) {
			newPath.add(path.get(path.size()-1));
		}
		int start = newPath.size();
		for(int t= start; t< game.tau; t++) {
			newPath.add(path.get(path.size()-1));
		}
		if(newPath.size() > game.tau) {
			System.out.println();
		}
		return newPath;

	}

	public List<List<Integer>> createAllAttackersStrategiesFrom(int i, int tk) {
		List<List<Integer>> result = new ArrayList<List<Integer>>();
		if (tk >= game.tau) {
			return result;
		} else if (game.targets.contains(i)) {
			result.add(new ArrayList<Integer>());
			result.get(0).add(i);
			return result;
		} else {
			for (int j : game.attackerGraph.getNeighbors(i)) {
				for (List<Integer> l : createAllAttackersStrategiesFrom(j, tk + game.timeToSail)) {
					l.add(i);
					result.add(l);
				}
			}
		}
		return result;
	}

}
