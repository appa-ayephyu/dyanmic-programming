package basicdatastructures;

import java.util.ArrayList;
import java.util.List;

public class Node {
	private int number;
	private List<Integer> neighbors; //adjacent cell for attacker and candidate cell for defender
	int xPosition;
	int yPosiiton;
	
	public Node(int number) {
		this.number = number;
		this.neighbors = new ArrayList<Integer>();
	}

	public int getxPosition() {
		return xPosition;
	}

	public void setxPosition(int xPosition) {
		this.xPosition = xPosition;
	}

	public int getyPosiiton() {
		return yPosiiton;
	}

	public void setyPosiiton(int yPosiiton) {
		this.yPosiiton = yPosiiton;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public List<Integer> getNeighbors() {
		return neighbors;
	}

	public void setNeighbors(List<Integer> neighbors) {
		this.neighbors = neighbors;
	}
	
	
}
