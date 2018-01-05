package basicdatastructures;

import java.util.ArrayList;
import java.util.List;

public class UndirectedGraph {
	public class Node{
		List<Integer> neighbors;
		int number;
		int xPosition;
		int yPosition;
		
		public Node(int number) {
			this.number = number;
			this.neighbors = new ArrayList<Integer>();
			
		}
		
		public int getNumber() {
			return this.number;
		}

	}
	
	private int numberOfNodes = 0;
	private List<Node> graph;
	private int numberOfEdges;
	private int[][] distanceMatrix;
	private boolean Isgrid =false;
	public int width;
	public int length;
	
	public UndirectedGraph(int numberOfNodes) {
		graph =new ArrayList<Node>();
		addNodes(numberOfNodes);
	}
	
	public void addNodes(int n) {
		for(int i=1;i<=n;i++) {
			addNode();
		}
	}
	
	public void addNode() {
		Node newNode = new Node(numberOfNodes);
		//newNode.neighbors.add(newNode.number);
		this.graph.add(newNode);
		this.numberOfNodes++;
	}
	
	public void addEdge(int i, int j) {
		this.numberOfEdges++;
		if(!areNeighbors(i,j)) {
			graph.get(i).neighbors.add(graph.get(j).number);
			graph.get(j).neighbors.add(graph.get(i).number);
		}
	}
	
	public boolean areNeighbors(int i, int j) {
		return (graph.get(i).neighbors.contains(j) && graph.get(j).neighbors.contains(i));
	}
	
	
	
	public static UndirectedGraph createGridGraph(int length, int width) {
		UndirectedGraph grid = new UndirectedGraph(length*width);
		grid.Isgrid= true;
		grid.length = length;
		grid.width = width;
		for(int i=0;i<width-1; i++) {
			for(int j=0; j<length; j++) {
				grid.graph.get(i*length+j).xPosition=i;
				grid.graph.get(i*length+j).yPosition=j;
				grid.addEdge(i*length+j, i*length+j+1);
				grid.addEdge(i*length+j, i*length+j+length);
			}
		}
		for(int j=0; j<length-1; j++) {
			grid.addEdge((width-1)*length+j, (width-1)*length+j+1);
			grid.graph.get((width-1)*length+j).xPosition = width -1;
			grid.graph.get((width-1)*length+j).yPosition = j;
		}
		for(int i=0;i<width-1;i++) {
			grid.addEdge(i*length+length-1, (i+1)*length+length-1);
			grid.graph.get(i*length+length-1).xPosition=i;
			grid.graph.get(i*length+length-1).yPosition = length -1;
		}
		grid.graph.get((width-1)*length+length-1).xPosition = width-1;
		grid.graph.get((width-1)*length+length-1).yPosition = length -1;
		grid.makeDistanceMatrix();
		return grid;
	}
	
	public void makeDistanceMatrix() {
		int n = this.numberOfNodes;
		int[][] distanceMatrix = new int[n][n];
		
		for(int i=0; i<n; i++) {
			for (int j=0; j<n; j++) {
				distanceMatrix[i][j] = Integer.MAX_VALUE-n;
			}
			distanceMatrix[i][i]=0;
			for(int k=1;k<n;k++) {
				for(int u=0;u<n;u++) {
					for(int v:graph.get(u).neighbors) {
						if(distanceMatrix[i][u]+1<distanceMatrix[i][v]) {
							distanceMatrix[i][v] = distanceMatrix[i][u]+1;
						}
					}
				}
			}
			this.distanceMatrix = distanceMatrix;
		}
	}
	
	//getters
	public List<Integer> getNeighbors(int i){
		return graph.get(i).neighbors;
	}
	
	public int getNumberOfNeighbors(int i) {
		return (graph.get(i).neighbors.size());
	}
	
	public int getNumberOfNodes() {
		return numberOfNodes;
	}
	
	public int getNumberOfEdges() {
		return numberOfEdges;
	}
	
	public List<Node> getGraph(){
		return graph;
	}
	
}
