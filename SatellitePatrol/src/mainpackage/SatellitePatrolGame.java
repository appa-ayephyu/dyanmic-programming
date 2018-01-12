package mainpackage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

import basicdatastructures.QueueElementForDijkstra;
import basicdatastructures.UndirectedGraph;
import basicdatastructures.UndirectedGraph.Node;
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
import basicdatastructures.QueueElementForDijkstraComaparer;
import basicdatastructures.Tuple;
import sun.font.CreatedFontTracker;

public class SatellitePatrolGame {
	Game game;
	List<AttackerPureStrategy> attackerStrategyList = new ArrayList<AttackerPureStrategy>();
	DefenderPureStrategy defendersBestResponse;
	List<AttackerPureStrategy> attackersBestResponse;
	double failvalue = 1000000;

	public GameSolution run(Game g, boolean CLP, boolean AOonly) {
		return run(g, CLP, AOonly, false);
	}

	public GameSolution run(Game game, boolean CLP, boolean AOonly, boolean smartAO) {
		initialize(game, CLP, AOonly);
		
		GameSolution currentSolution = new GameSolution();
		currentSolution = CLP(game.g, attackerStrategyList);
//		if(!CLP) {
//			defendersBestResponse = DefenderOracle(attackerPureStrategy)
//		}
		return currentSolution;
	}

	public GameSolution CLP(UndirectedGraph g, List<AttackerPureStrategy> attackersPureStrategies) {
		
		GameSolution result = new GameSolution();
//		for(int i=0; i<this.game.importantCells.size(); i++) {
//			System.out.println(this.game.importantCells.get(i).getNumber());
//		}
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
			// for maximization equation
			cplex.addGe(utility, 0, "c1");
			IloLinearNumExpr num_expr;
			for (AttackerPureStrategy a : attackersPureStrategies) {
				int serial = 0;
//				for(int i=0; i<a.path.size(); i++) {
//					System.out.println(a.path.get(i).getNumber());
//				}
//				System.out.println();
				
				// if(a.path.size() > serial) {
				// break;
				// }
				tildeR = new int[this.game.n][this.game.T];
				serial = 0;
				for (int t = a.decisionTime; t < this.game.T; t = t + game.m) {
					if (a.path.size() > serial) {
						tildeR[a.path.get(serial).getNumber()][t] = 1;
						serial++;
					}
				}

//				for (int i = 0; i < this.game.n; i++) {
//					for (int t = 0; t < this.game.T; t++) {
//						System.out.print(tildeR[i][t]);
//					}
//					System.out.println();
//				}
//				System.out.println();

				num_expr = cplex.linearNumExpr();
				num_expr.addTerm(1.0, utility);
				for (int i = 0; i < this.game.V; i++) {
					for (int k = 0; k < this.game.T; k++) {
						for (int j = 0; j < this.game.n; j++) {
//							if(tildeR[j][k]==1)
//							System.out.println(defenderStrategy[i][k][j].getUB() *  this.game.U * tildeR[j][k]);
							num_expr.addTerm(defenderStrategy[i][k][j], this.game.U * tildeR[j][k]);
						}
//						System.out.println();
					}
				}
//				System.out.println();
				num_expr.setConstant(-this.game.U);
				cplex.addLe(0.0, num_expr, "c2");
			}
			Integer[][][] eta = new Integer[this.game.V][this.game.T][this.game.n];
			for (int i = 0; i < this.game.V; i++) {
				for (int j = 0; j < this.game.T; j++) {
					for (int k = 0; k < this.game.n; k++) {
						if (j >= this.game.startingTime[k][i] && j <= this.game.endingTime[k][i]) {
							eta[i][j][k] = 1;
						} else {
							eta[i][j][k] = 0;
						}
					}
				}
			}

//			 System.out.println("eta");
//			 for (int i = 0; i < this.game.V; i++) {
//			 for (int j = 0; j < this.game.T; j++) {
//			 for (int k = 0; k < this.game.n; k++) {
//			 System.out.print(eta[i][j][k]);
//			 }
//			 System.out.println();
//			 }
//			 }

			for (int i = 0; i < this.game.V; i++) {
				for (int j = 0; j < this.game.T; j++) {
					for (int k = 0; k < this.game.n; k++) {
						cplex.addGe(eta[i][j][k], defenderStrategy[i][j][k], "c3");
					}
				}
			}

			for (int i = 0; i < this.game.V; i++) {
				for (int k = 0; k < this.game.T; k++) {
					cplex.addLe(cplex.sum(defenderStrategy[i][k]), 1.0, "c4");
				}
			}
			/*num_expr = cplex.linearNumExpr();
			for (int i = 0; i < this.game.V; i++) {
				for (int j = 0; j < this.game.T; j++) {
					for (int k = 0; k < this.game.n; k++) {
						num_expr.addTerm(this.game.imageSize, defenderStrategy[i][j][k]);
					}
				}
				cplex.addLe(num_expr, this.game.totalStorageCapacity, "c5");
			}*/
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

	public DefenderPureStrategy DefenderOracle(AttackerPureStrategy attackerPureStrategy) {
		DefenderPureStrategy result = new DefenderPureStrategy(this.game.V, this.game.T, this.game.n);
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
			// for maximization equation
			List<IloRange> optimality = new ArrayList<IloRange>();
			optimality.add(cplex.addGe(utility, 0, "c1"));
			IloLinearNumExpr num_expr;

			int[][] tildeR = new int[this.game.n][this.game.T];
			tildeR = new int[this.game.n][this.game.T];
			int serial = 0;
			for (int t = attackerPureStrategy.decisionTime; t < this.game.T; t = t + game.m) {
				if (attackerPureStrategy.path.size() > serial) {
					tildeR[attackerPureStrategy.path.get(serial).getNumber()][t] = 1;
					serial++;
				}
			}

//			for (int i = 0; i < this.game.n; i++) {
//				for (int t = 0; t < this.game.T; t++) {
//					System.out.print(tildeR[i][t]);
//				}
//				System.out.println();
//			}
//			System.out.println();

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
			optimality.add(cplex.addGe(num_expr, 0.0, "c2"));

			Integer[][][] eta = new Integer[this.game.V][this.game.T][this.game.n];
			for (int i = 0; i < this.game.V; i++) {
				for (int j = 0; j < this.game.T; j++) {
					for (int k = 0; k < this.game.n; k++) {
						if (j >= this.game.startingTime[k][i] && j <= this.game.endingTime[k][i]) {
							eta[i][j][k] = 1;
						} else {
							eta[i][j][k] = 0;
						}
					}
				}
			}

			for (int i = 0; i < this.game.V; i++) {
				for (int j = 0; j < this.game.T; j++) {
					for (int k = 0; k < this.game.n; k++) {
						cplex.addGe(eta[i][j][k], defenderStrategy[i][j][k], "c3");
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
			result.imagingStatus = defenderStrategyValue;
		} catch (IloException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;

	}

	public List<AttackerPureStrategy> AttackerOracle(Integer[][][] imagingStatus) {
		double[][] catchProb = new double[this.game.n][this.game.T];
		for (int i = 0; i < game.n; i++) {
			for (int tk = 1; tk <= game.T; tk++) {
				boolean catched = false;
				for (int v = 0; v < this.game.V; v++) {
					if (imagingStatus[v][tk][i] == 1) {
						catched = true;
						break;
					}
				}
				if (catched = true) {
					catchProb[i][tk] = 1;
				}
			}
		}
		List<AttackerPureStrategy> newresponses = new ArrayList<AttackerPureStrategy>();
		AttackerPureStrategy bestResponse = new AttackerPureStrategy(1, new ArrayList<Node>());
		double bestResponseValue = -Double.MAX_VALUE;
		for (int i = 0; i < game.n; i++) {
			for (int tk = 1; tk <= game.T; tk++) {
				Tuple<List<Node>, Double> currentStrategy = bestPath(catchProb, tk, this.game.g.getNodeWithNumber(i));
				if (!isLegal(currentStrategy.x, tk, i)) {
					currentStrategy = null;
				}
				if (game.U * (1 - currentStrategy.y) - bestResponseValue >= 0 && currentStrategy.y < failvalue) {
					newresponses = new ArrayList<AttackerPureStrategy>();
					bestResponse = new AttackerPureStrategy(tk, currentStrategy.x);
					newresponses.add(bestResponse);
				}
			}
		}
		return newresponses;
	}

	public boolean isLegal(List<Node> currentStrategy, int tk, int i) {
		if ((currentStrategy.size() * this.game.m) + tk > game.T + 1)
			return false;
		if (currentStrategy.get(0).getNumber() != i)
			return false;
		boolean hasImportantCell = false;
		for (int j = 0; j <= currentStrategy.size() - 1; j++) {
			int startingNodeIndex = j;
			Node startingNode = currentStrategy.get(startingNodeIndex);
			int endingNodeIndex = j + 1;
			Node endingNode = currentStrategy.get(endingNodeIndex);

			for (int z = 0; z < this.game.m; z++) {
				int newX = startingNode.getXPosition()
						+ (z * ((endingNode.getXPosition() - startingNode.getXPosition()) / this.game.m));
				int newY = startingNode.getYPosition()
						+ (z * ((endingNode.getYPosition() - startingNode.getYPosition()) / this.game.m));
				if (isImportantCell(newX, newY)) {
					hasImportantCell = true;
					break;
				}
			}

		}
		if (!hasImportantCell) {
			return false;
		}
		return true;
	}

	public boolean isImportantCell(int newX, int newY) {

		for (int i = 0; i < this.game.importantCells.size(); i++) {
			Node cell = this.game.importantCells.get(i);
			if (cell.getXPosition() == newX && cell.getYPosition() == newY) {
				return true;
			}
		}
		return false;
	}

	public Tuple<List<Node>, Double> bestPath(double[][] catchingProbability, int tk, Node startingPoint) {
		QueueElementForDijkstra[][] elements = new QueueElementForDijkstra[this.game.n][this.game.T + 1];
		PriorityQueue<QueueElementForDijkstra> queue = new PriorityQueue<QueueElementForDijkstra>(5,
				new QueueElementForDijkstraComaparer());
		QueueElementForDijkstra current = new QueueElementForDijkstra(
				catchingProbability[startingPoint.getNumber()][tk], startingPoint, tk, null, this.game.m);
		if (game.importantCells.contains(startingPoint)) {
			queue.add(new QueueElementForDijkstra(catchingProbability[current.target.getNumber()][tk], current.target,
					tk, null, current.decisionDuration - 1));
		}
		queue.add(current);
		for (int i = 1; i < this.game.m; i++) {
			current = queue.poll();
			tk++;
			if (tk > this.game.T) {
				current.dist = failvalue;
				break;
			} else {
				queue.add(new QueueElementForDijkstra(current.dist + catchingProbability[startingPoint.getNumber()][tk],
						startingPoint, tk, current, this.game.m));
			}
		}
		boolean finished = false;
		while (!queue.isEmpty() && !finished) { // dijkstra algorithm
			current = queue.poll();
			tk = current.time + 1;
			if ((this.game.importantCells.contains(current.target) || current.dist >= 1)) {
				finished = true;
			} else if (tk > this.game.T) {
				current.dist = failvalue;
			} else if (tk <= this.game.T && current.decisionDuration < this.game.m) { // need to wait until decision
																						// time is up to get to next
																						// cell
				elements[current.target.getNumber()][tk] = new QueueElementForDijkstra(
						current.dist + catchingProbability[current.target.getNumber()][tk], current.target, tk, current,
						current.decisionDuration - 1);
				queue.add(elements[current.target.getNumber()][tk]);
			} else if (tk <= this.game.T) { // choose next cell to go
				for (int i : this.game.g.getNeighbors(current.target.getNumber())) {
					if (elements[i][tk] == null) {
						elements[i][tk] = new QueueElementForDijkstra(current.dist + catchingProbability[i][tk],
								this.game.g.getNodeWithNumber(i), tk, current, this.game.m - 1);
						queue.add(elements[i][tk]);
					} else if (elements[i][tk].dist > current.dist + catchingProbability[i][tk]) {
						elements[i][tk].dist = current.dist + catchingProbability[i][tk];
						elements[i][tk].pre = current;
					}
				}
			}
		}

		if (queue.isEmpty() & current.decisionDuration != 0)
			current.dist = 1;
		double value = java.lang.Math.max(0, current.dist); // total escape proability is 0 to 1 only
		List<Node> path = new ArrayList<Node>();
		path.add(current.target);
		while (current.pre != null) {
			current = current.pre;
			path.add(current.target);
		}
		Collections.reverse(path);
		return new Tuple<List<Node>, Double>(path, value);
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
				List<List<Node>> paths = createAllAttackersStrategyPaths(v, t, game.t, game.m);
				for (List<Node> path : paths) {
					if (t < this.game.T) {
						Collections.reverse(path);
						attackerStrategies.add(new AttackerPureStrategy(t, path));
					}
				}
			}
		}
		return attackerStrategies;
	}

	public List<List<Node>> createAllAttackersStrategyPaths(int v, int t, int satelliteStep, int shipStep) {
		List<List<Node>> result = new ArrayList<List<Node>>();
		if (t > game.T) {
			return result;
		} else if (game.importantCells.contains(this.game.g.getNodeWithNumber(v))) {
			List<Node> temp = new ArrayList<Node>();
			temp.add(this.game.g.getNodeWithNumber(v));
			result.add(temp);
			return result;
		} else {
			for (int j : game.g.getNeighbors(v)) {
				for (List<Node> l : createAllAttackersStrategyPaths(j, t + 1, satelliteStep, shipStep)) {
					l.add(this.game.g.getNodeWithNumber(v));
					result.add(l);
				}

			}
			return result;
		}
	}
}
