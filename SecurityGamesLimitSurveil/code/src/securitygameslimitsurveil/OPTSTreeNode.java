package securitygameslimitsurveil;

import java.util.*;

public class OPTSTreeNode {
	public final static int OBSERVE = 1;
	public final static int ATTACK = 2;
	
	Map<Integer, Integer> observationMap;
	int maxAttackedTarget;
	int minAttackedTarget;
	int maxDecision;
	int minDecision;
	double maxValue;
	double minValue;
	
	public OPTSTreeNode(){
		this.observationMap = new HashMap<Integer, Integer>();
		
		this.maxAttackedTarget = -1;
		this.minAttackedTarget = -1;
	}
	
	public OPTSTreeNode(OPTSTreeNode parent, int observation){
		observationMap = new HashMap<Integer, Integer>(parent.observationMap);
		
		if(observationMap.containsKey(observation)){
			observationMap.put(observation, observationMap.get(observation) + 1);
		}
		else{
			observationMap.put(observation, 1);
		}
		
		this.maxAttackedTarget = -1;
		this.minAttackedTarget = -1;
	}
	
	public int getNumObservations(){
		int numObservations = 0;
		
		for(Integer pureStrategy : observationMap.keySet()){
			numObservations += observationMap.get(pureStrategy);
		}
		
		return numObservations;
	}
}
