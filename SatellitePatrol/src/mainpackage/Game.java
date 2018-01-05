package mainpackage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import basicdatastructures.UndirectedGraph;

public class Game {
	
	public UndirectedGraph g;
	int n; //size of graph, number of nodes
	int V=1; //number of satellites
	int T=10; //total time played
	double U= 8; //value of the important cells
	int t =1; //time step for the satellite to do imaging task
	int m = 5; //time steps required for the attacker ship to make the next move
	List<Integer> importantCells;
	int[][] startingTime;
	int[][] endingTime;
	int period = 5;
	int imageSize = 3; 	//image size for one
	int totalStorageCapacity = 30; //total storage for satellite
	
	public Game() {
		g = UndirectedGraph.createGridGraph(3, 1);
		n = g.getNumberOfNodes();
		createRandomImportantCells(n/3);
		setRandomImagingTimeWindow(n, V);
	}
	
	public Game(int length,int width) {
		g = UndirectedGraph.createGridGraph(length, width);
		n = g.getNumberOfNodes();
		createRandomImportantCells(n/3);
		setRandomImagingTimeWindow(n, V);
	}
	
	public Game(int length, int width, int T) {
		g = UndirectedGraph.createGridGraph(length, width);
		n = g.getNumberOfNodes();
		this.T = T;
		createRandomImportantCells(n/3);
		setRandomImagingTimeWindow(n, V);
	}
	
	public Game(UndirectedGraph g, int n, int V, int T, int t, int m) {
		this.g = g;
		this.n = n;
		this.V = V;
		this.T = T;
		this.t = t;
		this.m = m;
	}
	
	public void setRandomImagingTimeWindow(int numberOfNodes, int numberOfSatellites) {
		Random r = new Random();
		startingTime = new int[numberOfNodes][numberOfSatellites];
		endingTime = new int[numberOfNodes][numberOfSatellites];
		for(int i=0; i< numberOfNodes; i++) {
			for (int v=0; v < numberOfSatellites; v++) {
				startingTime[i][v]= r.nextInt(T-5);
				endingTime[i][v] = startingTime[i][v] +5;
			}
		}
		
	}
	
	public void createRandomImportantCells(int numberOfImportantCells) {

		importantCells = new ArrayList<Integer>();
		Random r = new Random();
		while(importantCells.size() < numberOfImportantCells) {
			int cell =  3; //r.nextInt(n);
			if(!importantCells.contains(cell)) {
				importantCells.add(cell);
			}
		}
	}
	
}
