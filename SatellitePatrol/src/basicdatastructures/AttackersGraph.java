package basicdatastructures;

import java.util.ArrayList;
import java.util.List;

public class AttackersGraph {
	private int numberOfNodes = 0;
	private List<Node> graph;
	private int numberOfEdges;
	private boolean Isgrid = false;
	private int width;
	private int length;
	
	public AttackersGraph(int numberOfNodes) {
		graph = new ArrayList<Node>();
		addNodes(numberOfNodes);
	}
	
	public void addNodes(int numberOfNodes) {
		for(int i=1;i<=numberOfNodes; i++) {
			addNode();
		}
	}
	
	public void addNode() {
		Node newNode = new Node(numberOfNodes);
		newNode.getNeighbors().add(newNode.getNumber());
		this.graph.add(newNode);
		this.numberOfNodes++;
	}
	
	public static AttackersGraph createGridGraph(int length, int width) {
		AttackersGraph grid = new AttackersGraph(length*width);
		grid.Isgrid = true;
		grid.width = width;
		grid.length = length;
		for(int i=0; i<width-1; i++) {
			for(int j=0;j<length-1; j++) {
				grid.graph.get(i*length+j).setxPosition(i);
				grid.graph.get(i*length+j).setyPosiiton(j);
				grid.addEdge(i*length+j, i*length+j+1);
				grid.addEdge(i*length+j, i*length+j+length);
			}
		}
		for(int j=0; j<length-1; j++) {
			grid.graph.get((width-1)*length+j).setxPosition(width-1);
			grid.graph.get((width-1)*length+j).setyPosiiton(j);
			grid.addEdge((width-1)*length+j, (width-1)*length+j+1);
		}
		for(int i=0; i< width-1; i++) {
			grid.graph.get(i*length+length-1).setxPosition(i);
			grid.graph.get(i*length+length-1).setyPosiiton(length-1);
			grid.addEdge(i*length+length-1, (i+1)*length+length-1);
			
		}
		grid.graph.get((width-1)*length+length-1).setxPosition(width-1);
		grid.graph.get((width-1)*length+length-1).setyPosiiton(length-1);
		return grid;
	}
	
	public void addEdge(int i, int j) {
		this.numberOfEdges++;
		if(!areNeighbors(i,j)) {
			graph.get(i).getNeighbors().add(graph.get(j).getNumber());
			graph.get(j).getNeighbors().add(graph.get(i).getNumber());
		}
	}
	
	public boolean areNeighbors(int i, int j) {
		return (graph.get(i).getNeighbors().contains(j) && graph.get(j).getNeighbors().contains(i));
	}
	
	public int getNumberOfNeighbors(int i) {
		return graph.get(i).getNeighbors().size();
	}

	public List<Integer> getNeighbors(int i) {
		return graph.get(i).getNeighbors();
	}

	public int getNumberOfNodes() {
		return numberOfNodes;
	}

	public int getNumberOfEdges() {
		return numberOfEdges;
	}

	public int getXPosition(int i) {
		return graph.get(i).getxPosition();
	}

	public int getYPosition(int i) {
		return graph.get(i).getyPosiiton();
	}
}
