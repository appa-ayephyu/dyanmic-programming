package mainpackage;

import java.util.ArrayList;
import java.util.List;

import basicdatastructures.Tuple;

public class MixedDefenderStrategy {
	int m;
	List<Tuple<Double,DefenderStrategy>> strategy;
	
	public MixedDefenderStrategy(){
		strategy = new ArrayList<Tuple<Double,DefenderStrategy>>();
	}
	
	public MixedDefenderStrategy(List<Tuple<Double,DefenderStrategy>> strat){
		strategy = strat;
	}
}
