package basicdatastructures;

import java.util.Comparator;

public class QueueElementForDijkstraComaparer implements Comparator<QueueElementForDijkstra> {

	@Override
	public int compare(QueueElementForDijkstra o1, QueueElementForDijkstra o2) {
		return Double.compare(o1.dist, o2.dist);
	}

}
