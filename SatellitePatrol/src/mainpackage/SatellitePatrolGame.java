package mainpackage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import basicdatastructures.UndirectedGraph;
import gurobi.GRBEnv;
import gurobi.GRBException;
import gurobi.GRBModel;
import ilog.concert.IloException;
import ilog.concert.IloIntVar;
import ilog.concert.IloLinearIntExpr;
import ilog.concert.IloLinearNumExpr;
import ilog.concert.IloNumVar;
import ilog.concert.IloRange;
import ilog.cplex.IloCplex;
import sun.font.CreatedFontTracker;

public class SatellitePatrolGame {
	Game game;
	List<AttackerPureStrategy> attackerStrategyList = new ArrayList<AttackerPureStrategy>();
	DefenderPureStrategy defendersBestResponse;
	List<AttackerPureStrategy> attackersBestResponse;

	public GameSolution run(Game g, boolean CLP, boolean AOonly) {
		return run(g, CLP, AOonly, false);
	}

	public GameSolution run(Game game, boolean CLP, boolean AOonly, boolean smartAO) {
		initialize(game, CLP, AOonly);

		GameSolution currentSolution = new GameSolution();
		currentSolution = CLP(game.g, attackerStrategyList);
		return currentSolution;
	}

	public GameSolution CLP(UndirectedGraph g, List<AttackerPureStrategy> attackersPureStrategies) {
		GameSolution result = new GameSolution();
		int[][] tildeR = new int[this.game.n][this.game.T];

		try {
			IloCplex cplex = new IloCplex();
			cplex.setOut(null);

			// Variables utility, defenderStrategy
			IloNumVar[][][] defenderStrategy = new IloNumVar[this.game.V][this.game.T][this.game.n];
			for (int i = 0; i < this.game.V; i++) {
				for (int k = 0; k < this.game.T; k++) {
					for (int j = 0; j < this.game.n; j++) {
						defenderStrategy[i][k][j] = cplex.numVar(0, 1);
					}
				}
				
			}

			// objective
			IloNumVar utility = cplex.numVar(0.0, Double.MAX_VALUE);
			cplex.addMinimize(utility);
			

			// constraint
			//for maximization equation
			List<IloRange> optimality = new ArrayList<IloRange>();
			optimality.add(cplex.addGe(utility, 0, "c1"));
			IloLinearNumExpr num_expr;
			for (AttackerPureStrategy a : attackersPureStrategies) {
					tildeR = new int[this.game.n][this.game.T];
					int serial=0;
					for (int t = a.decisionTime; t < this.game.T; t=t+game.m) {
						if(a.path.size() > serial) {
							tildeR[a.path.get(serial)][t] = 1;
							serial++;
						}
					}
				
				
				for (int i = 0; i < this.game.n; i++) {
					for (int t = 0; t < this.game.T; t++) {
						System.out.print(tildeR[i][t]);
					}
					System.out.println();
				}
				System.out.println();
				
				num_expr = cplex.linearNumExpr();
				num_expr.addTerm(1.0, utility);
				for (int i = 0; i < this.game.V; i++) {
					for (int k = 0; k < this.game.T; k++) {
						for (int j = 0; j < this.game.n; j++) {
							num_expr.addTerm(defenderStrategy[i][k][j], this.game.U * tildeR[j][k]);
						}
					}
				}
				num_expr.setConstant(-this.game.U);
				optimality.add(cplex.addGe(num_expr, 0.0,  "c2"));
			}
			Integer[][][] eta = new Integer[this.game.V][this.game.T][this.game.n];
			for (int i = 0; i < this.game.V; i++) {
				for (int j = 0; j < this.game.T; j++) {
					for (int k = 0; k < this.game.n; k++) {
						if(j>= this.game.startingTime[k][i] && j<= this.game.endingTime[k][i]) {
							eta[i][j][k] = 1;
						}else {
							eta[i][j][k] = 0;
						}
					}
				}
			}
			
			System.out.println("eta");
			for (int i = 0; i < this.game.V; i++) {
				for (int j = 0; j < this.game.T; j++) {
					for (int k = 0; k < this.game.n; k++) {
						System.out.print(eta[i][j][k]);
					}
					System.out.println();
				}
			}
			
			
			for (int i = 0; i < this.game.V; i++) {
				for (int j = 0; j < this.game.T; j++) {
					for (int k = 0; k < this.game.n; k++) {
						cplex.addGe(eta[i][j][k] , defenderStrategy[i][j][k],  "c3");
					}
				}
			}
			 
			
			
			
			for (int i = 0; i < this.game.V; i++) {
				for (int k = 0; k < this.game.T; k++) {
					cplex.addLe(cplex.sum(defenderStrategy[i][k]), 1.0, "c4");
				}
			}
			num_expr = cplex.linearNumExpr();
			for (int i = 0; i < this.game.V; i++) {
				for (int j = 0; j < this.game.T; j++) {
					for (int k = 0; k < this.game.n; k++) {
						num_expr.addTerm(this.game.imageSize, defenderStrategy[i][j][k]);
					}
				}
				cplex.addLe(num_expr, this.game.totalStorageCapacity, "c5");
			}
			cplex.exportModel("file_name.lp");
			cplex.solve();
			double solutionValue = cplex.getObjValue();
			double[][][] defenderStrategyValue = new double[this.game.V][this.game.T][this.game.n];
			for (int i = 0; i < this.game.V; i++) {
				for (int j = 0; j < this.game.T; j++) {
					for (int k = 0; k < this.game.n; k++) {
						defenderStrategyValue[i][j][k] = cplex.getValue(defenderStrategy[i][j][k]);
					}
				}
			}
			for (int i = 0; i < this.game.V; i++) {
				for (int j = 0; j < this.game.T; j++) {
					for (int k = 0; k < this.game.n; k++) {
						System.out.print(defenderStrategyValue[i][j][k] + " ");
					}
					System.out.println();
				}
				System.out.println();
			}

			System.out.println(solutionValue);
			result = new GameSolution(defenderStrategyValue, solutionValue);
			return result;
		} catch (IloException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void initialize(Game game, boolean CLP, boolean AOonly) {
		initialize(game, CLP, AOonly, new ArrayList<AttackerPureStrategy>());
	}

	public void initialize(Game game, boolean CLP, boolean AOonly, List<AttackerPureStrategy> attackerStrategyList) {
		this.game = game;
		if (CLP) {
			this.attackerStrategyList = createAllAttackersStrategies();
		}

	}

	public List<AttackerPureStrategy> createAllAttackersStrategies() {
		List<AttackerPureStrategy> attackerStrategies = new ArrayList<AttackerPureStrategy>();
		for (int v = 0; v < game.g.getNumberOfNodes(); v++) {
			for (int t = 1; t <= game.T; t++) {
				List<List<Integer>> paths = createAllAttackersStrategyPaths(v, t, game.t, game.m);
				for (List<Integer> path : paths) {
					Collections.reverse(path);
					attackerStrategies.add(new AttackerPureStrategy(t, path));
				}
			}
		}
 		return attackerStrategies;
	}

	public List<List<Integer>> createAllAttackersStrategyPaths(int v, int t, int satelliteStep, int shipStep) {
		List<List<Integer>> result = new ArrayList<List<Integer>>();
		if (t > game.T) {
			return result;
 		} else if (game.importantCells.contains(v)) {
			List<Integer> temp = new ArrayList<Integer>();
			temp.add(v);
			result.add(temp);
			return result;
		}
		else {
			for(int j:game.g.getNeighbors(v)) {
				for(List<Integer> l:createAllAttackersStrategyPaths(j, t + 5, satelliteStep, shipStep)) {
					l.add(v);
					result.add(l);
				}
			
			}
			return result;
		}
	}
}
