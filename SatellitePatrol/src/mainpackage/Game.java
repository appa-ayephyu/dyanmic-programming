package mainpackage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import basicdatastructures.AttackersGraph;
import basicdatastructures.Tuple;
import sun.security.util.Length;

public class Game {

	public AttackersGraph attackerGraph;
	int n; // size of graph, number of nodes
	int m = 2; // number of satellites
	int tau = 12; // total time played //time steps 4 hours
	double epsilon = 0.5; // global detecting faillure probability of cell
	double reward = 10; // reward for attacker
	double penalty = 5; // penalty for attacker
	List<Integer> targets; // List of possible targets in the surveillance area
	int timeToSail = 2; // for ship it takes 900 steps to get to another cell
	List<HashMap<Integer, List<Integer>>> candidateCells; // candidate cells for each satellite with respect to time
															// step
	int delta = 2; // number of consecutive time steps to catch the attacker
	int numberofImages = 3; // storage capacity
	int gamma = 5; // downloading station points

	int randomTargets = 2;

	public Game(AttackersGraph attackerGraph, int m, int tau, double epsilon, double reward, double penalty,
			List<Integer> targets, List<HashMap<Integer, List<Integer>>> candidateCells) {
		this.attackerGraph = attackerGraph;
		this.m = m;
		this.tau = tau;
		this.epsilon = epsilon;
		this.reward = reward;
		this.penalty = penalty;
		this.targets = targets;
		this.candidateCells = candidateCells;
	}

	public Game() {

		attackerGraph = AttackersGraph.createGridGraph(7, 7);
		n = attackerGraph.getNumberOfNodes();
		List<Tuple<Tuple<Integer, Integer>, Tuple<Integer, Integer>>> listOfStartEnd = getStartPointEndPoint(this.m, 7,
				7);
		List<List<Integer>> path = createPathForSatellites(listOfStartEnd, 7, 7);
		createRandomCandidateCells(path, 7);
		createRandomTargets(randomTargets);
	}

	public Game(int length, int width) {
		attackerGraph = AttackersGraph.createGridGraph(length, width);
		n = attackerGraph.getNumberOfNodes();
		List<Tuple<Tuple<Integer, Integer>, Tuple<Integer, Integer>>> listOfStartEnd = getStartPointEndPoint(this.m,
				length, width);
		List<List<Integer>> path = createPathForSatellites(listOfStartEnd, length, width);
		createRandomCandidateCells(path, width);
		createRandomTargets(randomTargets);

	}

	public Game(int length, int width, int tau) {

		attackerGraph = AttackersGraph.createGridGraph(length, width);
		n = attackerGraph.getNumberOfNodes();
		this.tau = tau;
		List<Tuple<Tuple<Integer, Integer>, Tuple<Integer, Integer>>> listOfStartEnd = getStartPointEndPoint(this.m,
				length, width);
		List<List<Integer>> path = createPathForSatellites(listOfStartEnd, length, width);
		createRandomCandidateCells(path, width);
		// printing
		for (int r = 0; r < candidateCells.size(); r++) {
			System.out.println("For satellite" + r);
			HashMap<Integer, List<Integer>> hash = candidateCells.get(r);
			for (int t = 0; t < this.tau; t++) {
				System.out.print("Time: " + t + "Candidate Cells :  {");
				for (int k = 0; k < hash.get(t).size(); k++) {
					System.out.print(hash.get(t).get(k) + ",");
				}
				System.out.println();
			}
		}
		createRandomTargets(randomTargets);
	}

	public List<List<Integer>> createPathForSatellites(
			List<Tuple<Tuple<Integer, Integer>, Tuple<Integer, Integer>>> listOfStartEnd, int length, int width) {
 		List<List<Integer>> path = new ArrayList<List<Integer>>();
		for (Tuple<Tuple<Integer, Integer>, Tuple<Integer, Integer>> se : listOfStartEnd) {
			Tuple<Integer, Integer> start = se.x;
			Tuple<Integer, Integer> end = se.y;
			Integer diff = end.x - start.x;
			List<Integer> satellitePath = new ArrayList<Integer>();
			if (start.x * length + start.y > 0)
				satellitePath.add(start.x * width + start.y - 1);
			for (int d = 1; d < diff+1; d++) {
				satellitePath.add((start.x + d) * width + start.y-1);
			}
			path.add(satellitePath);
		}
		return path;
	}

	public List<Tuple<Tuple<Integer, Integer>, Tuple<Integer, Integer>>> getStartPointEndPoint(int numberOfSatellites,
			int length, int width) {
		List<Tuple<Tuple<Integer, Integer>, Tuple<Integer, Integer>>> satellitePath = new ArrayList<Tuple<Tuple<Integer, Integer>, Tuple<Integer, Integer>>>();
		Tuple<Integer, Integer> startPoint = new Tuple(0, 1);
		Tuple<Integer, Integer> endPoint = new Tuple(length-1, 1);
		Tuple<Tuple<Integer, Integer>, Tuple<Integer, Integer>> startandend = new Tuple(startPoint, endPoint);
		satellitePath.add(startandend);

		startPoint = new Tuple(0, width - 1);
		endPoint = new Tuple(length - 1, width - 1);
		startandend = new Tuple(startPoint, endPoint);
		satellitePath.add(startandend);
		
		startPoint = new Tuple(0, width - 3);
		endPoint = new Tuple(length - 1, width - 3);
		startandend = new Tuple(startPoint, endPoint);
		satellitePath.add(startandend);
		return satellitePath;
	}

	public void createRandomTargets(int numberOfTargets) {
		targets = new ArrayList<Integer>();
		Random r = new Random();
		/*while (targets.size() < numberOfTargets) {
			int location = 20; // r.nextInt(n);
			if (!targets.contains(location)) {
				targets.add(location);
			}
		}*/
		targets.add(3);
		targets.add(4);
	}

	public void createRandomCandidateCells(List<List<Integer>> path, int width) {
		candidateCells = new ArrayList<HashMap<Integer, List<Integer>>>();
		for (int r = 0; r < this.m; r++) {
			HashMap<Integer, List<Integer>> hashMap = new HashMap<Integer, List<Integer>>();
			List<Integer> pathofSatellite = path.get(r);
			for (int t = 0; t < this.tau; t++) {
				int cell = pathofSatellite.get(t % pathofSatellite.size());
				List<Integer> cells = new ArrayList<Integer>();
				if (cell >= width - 1) {
					int prevwidth = cell - width;
					if (prevwidth >= 0 && !cells.contains(prevwidth)) {
						cells.add(prevwidth);
					}
					int prevwidthminusone = cell - width + 1;
					if (prevwidthminusone >= 0 && !cells.contains(prevwidthminusone)) {
						cells.add(prevwidthminusone);
					}
				}
				if (cell % width != width - 1) {
					int cellplusone = cell + 1;
					if (!cells.contains(cellplusone)) {
						cells.add(cellplusone);
					}
				}
				if (!cells.contains(cell)) {
					cells.add(cell);
				}
				hashMap.put(t, cells);
			}
			candidateCells.add(hashMap);
		}
	}

	public int getNumberOfNodes() {
		return attackerGraph.getNumberOfNodes();
	}

	public String toString() {
		String result = "";
		result = result + "n = " + n + "\n";
		result = result + "m = " + m + "\n";
		result = result + "tau = " + tau + "\n";
		result = result + "fe = " + epsilon + "\n";
		result = result + "]\n";
		result.trim();
		result = result + "]\n";
		result = result + "U = [ ";
		result.trim();
		result = result + "]\n";
		result = result + "Targets = {";
		for (int i : targets) {
			result = result + i + " ";
		}
		result.trim();
		result = result + "}\n";
		return result;
	}

}
