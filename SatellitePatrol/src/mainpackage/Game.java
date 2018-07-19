package mainpackage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import basicdatastructures.AttackersGraph;

public class Game {
	
	public AttackersGraph attackerGraph;
	int n; //size of graph, number of nodes
	int m=2; //number of satellites
	int tau=12; //total time played //time steps 4 hours
	double epsilon = 0.5; //global detecting faillure probability of cell 
	double reward = 10; //reward for attacker
	double penalty = 5; //penalty for attacker
	List<Integer> targets; //List of possible targets in the surveillance area
	int timeToSail = 2; //for ship it takes 900 steps to get to another cell
	List<HashMap<Integer, List<Integer>>> candidateCells; //candidate cells for each satellite with respect to time step
	int delta = 1; //number of consecutive time steps to catch the attacker
	int numberofImages = 2; //storage capacity
	int gamma = 3; //downloading station points
	
	int randomTargets = 2;
	
	public Game(AttackersGraph attackerGraph, int m, int tau, double epsilon, double reward, double penalty, List<Integer> targets, List<HashMap<Integer, List<Integer>>> candidateCells) {
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
		createRandomCandidateCells();
		attackerGraph = AttackersGraph.createGridGraph(7, 7);
		n = attackerGraph.getNumberOfNodes();
		createRandomTargets(randomTargets);
	}
	
	public Game(int length,int width) {
		createRandomCandidateCells();
		attackerGraph = AttackersGraph.createGridGraph(length, width);
		n = attackerGraph.getNumberOfNodes();
		createRandomTargets(randomTargets);
		
	}
	
	public Game(int length, int width, int tau) {
		
		
		attackerGraph = AttackersGraph.createGridGraph(length, width);
		n = attackerGraph.getNumberOfNodes();
		this.tau = tau;
		createRandomCandidateCells();
		//		printing
//		for(int r=0; r<candidateCells.size(); r++) {
//			System.out.println("For satellite" + r);
//			HashMap<Integer, List<Integer>> hash = candidateCells.get(r);
//			for(int t=0; t<this.tau; t++) {
//				System.out.print("Time: " + t + "Candidate Cells :  {");
//				for(int k=0; k<hash.get(t).size(); k++) {
//					System.out.print(hash.get(t).get(k) + ",");
//				}
//				System.out.println();
//			}
//		}
		createRandomTargets(randomTargets);
	}
	
	public void createRandomTargets(int numberOfTargets) {
		targets = new ArrayList<Integer>();
		Random r = new Random();
		while(targets.size() < numberOfTargets) {
			int location = r.nextInt(n);
			if(!targets.contains(location)) {
				targets.add(location);
			}
		}
	}
	
	public void createRandomCandidateCells() {
		candidateCells = new ArrayList<HashMap<Integer, List<Integer>>>();
		for (int r = 0; r < this.m; r++) {
			HashMap<Integer, List<Integer>> hashMap = new HashMap<Integer, List<Integer>>();
			Random rand = new Random();
			for (int t = 0; t < this.tau; t++) {
				List<Integer> cells = new ArrayList<Integer>();
				while (cells.size()<2) {
					int haha = rand.nextInt(this.n);
					if (!cells.contains(haha)) {
						cells.add(haha);
					}
				}
				hashMap.put(t, cells);
			}
			candidateCells.add(hashMap);
		}
	}
	
	public int getNumberOfNodes() {
		return attackerGraph.getNumberOfNodes();
	}
	
	public String toString(){
		String result ="";
		result=result+"n = "+n+"\n";
		result=result+"m = "+m+"\n";
		result=result+"tau = "+tau+"\n";
		result=result+"fe = "+epsilon+"\n";
		result=result+"]\n";
		result.trim();
		result=result+"]\n";
		result=result+"U = [ ";
		result.trim();
		result=result+"]\n";
		result=result+"Targets = {";
		for (int i:targets){
			result=result+i+" ";
		}
		result.trim();
		result=result+"}\n";
		return result;
	}
	
}
