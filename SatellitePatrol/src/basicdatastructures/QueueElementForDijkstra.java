package basicdatastructures;

import java.util.List;

import basicdatastructures.UndirectedGraph.Node;


public class QueueElementForDijkstra {
	public double dist;
	public Node target;
	public int time;
	public  QueueElementForDijkstra pre;
	public int decisionDuration;
	
	public QueueElementForDijkstra(double dist,Node target,int time,QueueElementForDijkstra pre, int decisionDuration){
		this.dist=dist;
		this.target = target;
		this.time=time;
		this.pre=pre;
		this.decisionDuration = decisionDuration;
	}
}