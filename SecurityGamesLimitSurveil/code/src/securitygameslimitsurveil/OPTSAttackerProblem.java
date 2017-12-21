package securitygameslimitsurveil;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;

public class OPTSAttackerProblem {
	public SecurityGame sg;
	public double observationCost;
	public int numPureStrategies;
	public List<Double> alpha;
	public List<Set<Integer>> pureStrategies;
	double alphaSum;
	public int depth;
	
	public Map<Map<Integer, Integer>, Double> cachedMaxValueMap;
	public Map<Map<Integer, Integer>, Double> cachedMinValueMap;
	
	public Map<Map<Integer, Integer>, Integer> attackedTargetMaxMap;
	public Map<Map<Integer, Integer>, Integer> attackedTargetMinMap;
	
	public double finalMaxValue;
	public double finalMinValue;
	
	public double runTime;
	
	public double maxDepth;
	
	public final static int MAX = 1;
	public final static int MIN = -1;
	
	public static void main(String[] args){
		int numTargets = 5;
		int numResources = 1;
		
		List<Double> alpha = new ArrayList<Double>();
		
		for(int i = 0; i < CombinationGenerator.getNChooseR(numTargets, numResources).intValue(); i++){
			alpha.add(0.0);
		}
		
		double[] observationCosts = {0.2, 0.3, 0.4, 0.5, 0.6};
		
		for(int i = 0; i < 100; i++){
			long seed = System.currentTimeMillis();
			SecurityGame sg = new SecurityGame(numTargets, numResources);
			sg.initializeRandom(seed);
			
			double rMax = Double.NEGATIVE_INFINITY;
			
			for(Target t : sg.setTargets){
				if(t.attUncovPayoff - t.attCovPayoff > rMax){
					rMax = t.attUncovPayoff - t.attCovPayoff;
				}
			}
			
			double alphaSum = 0.0;
			
			for(int k = 0; k < alpha.size(); k++){
				alphaSum += alpha.get(k);
			}
			
			for(int j = 0; j < observationCosts.length; j++){
				double maxDepth = Math.max(0.0, (rMax / observationCosts[j]) - alphaSum - sg.numTargets - 1);
				
				System.out.println(seed + " " + observationCosts[j] + " " + maxDepth);
			}
			
			while(System.currentTimeMillis() - seed < 100){
				
			}
		}
		
		
		
		
		
		/*
		System.out.println(observationCost);
		
		//OPTSAttackerProblem attProblem1 = OPTSAttackerProblem.Algorithm1(sg, alpha, observationCost, 0.0001);
		//System.out.println("BI-FS: " + " " + attProblem1.depth + " " + attProblem1.finalMinValue);
		
		double epsilon = 0.001;
		
		for(int i = 0; i < 6; i++){
			OPTSAttackerProblem attProblem2 = OPTSAttackerProblem.Algorithm2(sg, alpha, observationCost, 2.0, epsilon);
			System.out.println("A-BI-FS: " + epsilon + " " + attProblem2.depth + " " + attProblem2.finalMinValue);
			
			epsilon = epsilon * 10.0;
		}
		*/
	}
	
	public OPTSAttackerProblem(SecurityGame sg, List<Double> alpha, int depth, double observationCost){
		this.sg = sg;
		this.depth = depth;
		this.observationCost = observationCost;

        CombinationGenerator cg = new CombinationGenerator(sg.numTargets, sg.numResources);
        
        this.numPureStrategies = cg.getTotal().intValue();
        this.pureStrategies = new ArrayList<Set<Integer>>();
        
        this.cachedMaxValueMap = new HashMap<Map<Integer, Integer>, Double>();
        this.cachedMinValueMap = new HashMap<Map<Integer, Integer>, Double>();
        
        this.attackedTargetMaxMap = new HashMap<Map<Integer, Integer>, Integer>();
        this.attackedTargetMinMap = new HashMap<Map<Integer, Integer>, Integer>();
        
        while (cg.hasMore()) {
            int[] strategy = cg.getNext();
            
            Set<Integer> strategySet = new HashSet<Integer>();
            
            for(int i = 0; i < strategy.length; i++){
            	strategySet.add(strategy[i]);
            }
            
            this.pureStrategies.add(strategySet);
        }

        this.alpha = alpha;

        this.alphaSum = 0.0;
        
        for(int i = 0; i < alpha.size(); i++){
        	this.alphaSum += alpha.get(i);
        }
	}
	
	public static OPTSAttackerProblem Algorithm1(SecurityGame sg, List<Double> alpha, double observationCost, double tolerance){
		if(alpha.size() != CombinationGenerator.getNChooseR(sg.numTargets, sg.numResources).intValue()){
			return null;
		}
		
		double rMax = Double.NEGATIVE_INFINITY;
		
		for(Target t : sg.setTargets){
			if(t.attUncovPayoff - t.attCovPayoff > rMax){
				rMax = t.attUncovPayoff - t.attCovPayoff;
			}
		}
		
		double alphaSum = 0.0;
		
		for(int i = 0; i < alpha.size(); i++){
			alphaSum += alpha.get(i);
		}
		
		double maxDepth = Math.max(0.0, (rMax / observationCost) - alphaSum - sg.numTargets - 1);
		
		int mDepth;
		
		if(maxDepth == (int)maxDepth){
			mDepth = (int)maxDepth;
		}
		else{
			mDepth = ((int)maxDepth) + 1;
		}
		
		int depth = 0;
		
		long startTime = System.currentTimeMillis();
		
		while(depth < mDepth){
			OPTSAttackerProblem opts = new OPTSAttackerProblem(sg, alpha, depth, observationCost);
			
			opts.computeOptimalObservationTree(0);
		
			if(Math.abs(opts.finalMaxValue - opts.finalMinValue) < tolerance){
				opts.runTime = (System.currentTimeMillis() - startTime) / 1000.0;
				opts.maxDepth = maxDepth;
				return opts;
			}
			
			if(depth == 0){
				depth = 1;
			}
			else{
				depth = 2 * depth;
			}
		}
		
		OPTSAttackerProblem opts = new OPTSAttackerProblem(sg, alpha, mDepth, observationCost);
		opts.computeOptimalObservationTree(2);
		opts.runTime = (System.currentTimeMillis() - startTime) / 1000.0;
		opts.maxDepth = maxDepth;
		
		return opts;
	}

	public static OPTSAttackerProblem Algorithm2(SecurityGame sg, List<Double> alpha, double observationCost, double beta, double epsilon){
		if(alpha.size() != CombinationGenerator.getNChooseR(sg.numTargets, sg.numResources).intValue()){
			return null;
		}
		
		double rMax = Double.NEGATIVE_INFINITY;
		
		for(Target t : sg.setTargets){
			if(t.attUncovPayoff - t.attCovPayoff > rMax){
				rMax = t.attUncovPayoff - t.attCovPayoff;
			}
		}
		
		double alphaSum = 0.0;
		
		for(int i = 0; i < alpha.size(); i++){
			alphaSum += alpha.get(i);
		}
		
		double maxDepth = Math.max(0.0, (rMax / observationCost) - alphaSum - sg.numTargets - 1);
		
		int depth = 1;
		int depthPrime;
		
		if(beta == (int)beta){
			depthPrime = (int)beta;
		}
		else{
			depthPrime = ((int)beta) + 1;
		}
		
		long startTime = System.currentTimeMillis();
		
		while(depth <= 2 * maxDepth){
			depth = Math.min(depth, (int)maxDepth);
			
			OPTSAttackerProblem opts = new OPTSAttackerProblem(sg, alpha, depth, observationCost);
			OPTSAttackerProblem optsPrime = new OPTSAttackerProblem(sg, alpha, depthPrime, observationCost);
			
			opts.computeOptimalObservationTree(2);
			optsPrime.computeOptimalObservationTree(2);
			
			//System.out.println("******");
			//System.out.println(opts.depth + ": " + opts.finalMinValue);
			//System.out.println(optsPrime.depth + ": " + optsPrime.finalMinValue);
		
			if(Math.abs(opts.finalMinValue - optsPrime.finalMinValue) < epsilon){
				opts.runTime = (System.currentTimeMillis() - startTime) / 1000.0;
				opts.maxDepth = maxDepth;
				return opts;
			}
			
			depth = depthPrime;
			
			if(beta * depthPrime == (int)(beta * depthPrime)){
				depthPrime = (int)(beta * depthPrime);
			}
			else{
				depthPrime = (int)(beta * depthPrime) + 1;
			}
		}
		
		return null;
	}
	
	public OPTSTreeNode computeOptimalObservationTree(int mode){
		OPTSTreeNode observationTree = new OPTSTreeNode();
		
		if(mode == 0){
			finalMaxValue = computeMaxValue(observationTree);
			finalMinValue = computeMinValue(observationTree);
		}
		else if(mode == 1){
			finalMaxValue = computeMaxValue(observationTree);
		}
		else{
			finalMinValue = computeMinValue(observationTree);
		}
			
		return observationTree;
	}
	
	public double computeMaxValue(OPTSTreeNode node){
		if(cachedMaxValueMap.containsKey(node.observationMap)){
			return cachedMaxValueMap.get(node.observationMap);
		}
		else if(node.getNumObservations() == depth){
			double w = computeImmediateMaxValue(node);
			node.maxValue = w;
			node.maxDecision = OPTSTreeNode.ATTACK;
			
			attackedTargetMaxMap.put(node.observationMap, node.maxAttackedTarget);
		}
		else{
			double v = computeFutureMaxValue(node);
			double w = computeImmediateValue(node, MAX);
			
			if(v > w){
				node.maxValue = v;
				node.maxDecision = OPTSTreeNode.OBSERVE;
			}
			else{
				node.maxValue = w;
				node.maxDecision = OPTSTreeNode.ATTACK;
				
				////////////////////////////////
				List<Map<Integer, Integer>> dominatedObservationVectors = new ArrayList<Map<Integer, Integer>>();
				
				for(Map<Integer, Integer> observationMap : attackedTargetMaxMap.keySet()){
					boolean dominated = true;
					
					for(Integer pureStrategy : node.observationMap.keySet()){
						if(!observationMap.containsKey(pureStrategy) || node.observationMap.get(pureStrategy) > observationMap.get(pureStrategy)){
							dominated = false;
						}
					}
					
					if(dominated){
						dominatedObservationVectors.add(observationMap);
					}
				}
				
				for(Map<Integer, Integer> observationVector : dominatedObservationVectors){
					attackedTargetMaxMap.remove(observationVector);
				}
				////////////////////////////////
				
				attackedTargetMaxMap.put(node.observationMap, node.maxAttackedTarget);
				
				/*
				for(int i = 0; i < numPureStrategies; i++){
					Map<Integer, Integer> childObservationMap = new HashMap<Integer, Integer>(node.observationMap);
					
					if(childObservationMap.containsKey(i)){
						childObservationMap.put(i, childObservationMap.get(i) + 1);
					}
					else{
						childObservationMap.put(i, 1);
					}
					
					if(attackedTargetMaxMap.containsKey(childObservationMap)){
						attackedTargetMaxMap.remove(childObservationMap);
					}
				}
				*/
			}
		}
		
		cachedMaxValueMap.put(node.observationMap, node.maxValue);
		
		return node.maxValue;
	}
	
	public double computeMinValue(OPTSTreeNode node){
		if(cachedMinValueMap.containsKey(node.observationMap)){
			return cachedMinValueMap.get(node.observationMap);
		}
		
		double w = computeImmediateValue(node, MIN);
		
		if(node.getNumObservations() == depth){
			node.minValue = w;
			node.minDecision = OPTSTreeNode.ATTACK;
			
			attackedTargetMinMap.put(node.observationMap, node.minAttackedTarget);
		}
		else{
			double v = computeFutureMinValue(node);
			
			if(v > w){
				node.minValue = v;
				node.minDecision = OPTSTreeNode.OBSERVE;
			}
			else{
				node.minValue = w;
				node.minDecision = OPTSTreeNode.ATTACK;
				
				////////////////////////////////
				List<Map<Integer, Integer>> dominatedObservationVectors = new ArrayList<Map<Integer, Integer>>();
				
				for(Map<Integer, Integer> observationMap : attackedTargetMinMap.keySet()){
					boolean dominated = true;
					
					for(Integer pureStrategy : node.observationMap.keySet()){
						if(!observationMap.containsKey(pureStrategy) || node.observationMap.get(pureStrategy) > observationMap.get(pureStrategy)){
							dominated = false;
						}
					}
					
					if(dominated){
						dominatedObservationVectors.add(observationMap);
					}
				}
				
				for(Map<Integer, Integer> observationVector : dominatedObservationVectors){
					attackedTargetMinMap.remove(observationVector);
				}
				////////////////////////////////
				
				attackedTargetMinMap.put(node.observationMap, node.minAttackedTarget);
				
				/*
				for(int i = 0; i < numPureStrategies; i++){
					Map<Integer, Integer> childObservationMap = new HashMap<Integer, Integer>(node.observationMap);
					
					if(childObservationMap.containsKey(i)){
						childObservationMap.put(i, childObservationMap.get(i) + 1);
					}
					else{
						childObservationMap.put(i, 1);
					}
					
					if(attackedTargetMinMap.containsKey(childObservationMap)){
						attackedTargetMinMap.remove(childObservationMap);
					}
				}
				*/
			}
		}
		
		cachedMinValueMap.put(node.observationMap, node.minValue);
		
		return node.minValue;
	}
	
	public double computeFutureMaxValue(OPTSTreeNode node){
		double value = 0;
		
		for(int i = 0; i < numPureStrategies; i++){
			OPTSTreeNode child = new OPTSTreeNode(node, i);
			
			value += computeStrategyProbability(node, i) * computeMaxValue(child);
		}
		
		return value;
	}
	
	public double computeFutureMinValue(OPTSTreeNode node){
		double value = 0;
		
		for(int i = 0; i < numPureStrategies; i++){
			OPTSTreeNode child = new OPTSTreeNode(node, i);
			
			value += computeStrategyProbability(node, i) * computeMinValue(child);
		}
		
		return value;
	}
	
	public double computeImmediateMaxValue(OPTSTreeNode node){
		int numObservations = node.getNumObservations();
		List<Target> targetList = new ArrayList<Target>(sg.setTargets);
		
		Collections.sort(targetList);
		
		double highestAttackerPayoff = Double.NEGATIVE_INFINITY;
		
		for(int i = 0; i < targetList.size(); i++){
			if(targetList.get(i).attUncovPayoff > highestAttackerPayoff){
				highestAttackerPayoff = targetList.get(i).attUncovPayoff;
				node.maxAttackedTarget = i;
			}
		}
		
		return highestAttackerPayoff - (numObservations * observationCost);
	}
	
	public double computeImmediateValue(OPTSTreeNode node, int mode){
		int numObservations = node.getNumObservations();
		
		List<Double> coverageVector = computeCoverageVector(node);
		List<Target> targetList = new ArrayList<Target>(sg.setTargets);
		
		Collections.sort(targetList);
		
		double highestAttackerPayoff = Double.NEGATIVE_INFINITY;
		int attackedTarget = -1;
		
		for(int i = 0; i < targetList.size(); i++){
			double attackerCoveredPayoff = coverageVector.get(i) * targetList.get(i).attCovPayoff;
			double attackerUncoveredPayoff = (1 - coverageVector.get(i)) * targetList.get(i).attUncovPayoff;
			double attackerPayoff = attackerCoveredPayoff + attackerUncoveredPayoff;
			
			if(attackerPayoff > highestAttackerPayoff){
				highestAttackerPayoff = attackerPayoff;
				attackedTarget = i;
			}
			else if(attackerPayoff == highestAttackerPayoff){
				double previousDefenderCoveredPayoff = coverageVector.get(attackedTarget) * targetList.get(attackedTarget).defCovPayoff;
				double previousDefenderUncoveredPayoff = (1 - coverageVector.get(attackedTarget)) * targetList.get(attackedTarget).defUncovPayoff;
				double previousDefenderPayoff = previousDefenderCoveredPayoff + previousDefenderUncoveredPayoff;
				
				double currentDefenderCoveredPayoff = coverageVector.get(i) * targetList.get(i).defCovPayoff;
				double currentDefenderUncoveredPayoff = (1 - coverageVector.get(i)) * targetList.get(i).defUncovPayoff;
				double currentDefenderPayoff = currentDefenderCoveredPayoff + currentDefenderUncoveredPayoff; 
				
				if(currentDefenderPayoff > previousDefenderPayoff){
					attackedTarget = i;
				}
			}
		}
		
		if(mode == MAX){
			node.maxAttackedTarget = attackedTarget;
		}
		else if(mode == MIN){
			node.minAttackedTarget = attackedTarget;
		}
		
		return highestAttackerPayoff - (observationCost * numObservations);
	}
	
	public double computeStrategyProbability(OPTSTreeNode node, int pureStrategy){
		int timesObserved = 0;
		
		if(node.observationMap.containsKey(pureStrategy)){
			timesObserved = node.observationMap.get(pureStrategy);
		}
		
		double numerator = alpha.get(pureStrategy) + timesObserved + 1;
		double denominator = alphaSum + numPureStrategies + node.getNumObservations();
		
		return numerator / denominator;
	}

	public List<Double> computeCoverageVector(OPTSTreeNode node){
		List<Double> coverageVector = new ArrayList<Double>();
		
		double denominator = alphaSum + numPureStrategies + node.getNumObservations();
		
		for(int i = 0; i < sg.numTargets; i++){
			double numerator = 0;
			
			for(int j = 0; j < numPureStrategies; j++){
				if(pureStrategies.get(j).contains(i)){
					int timesObserved = 0;
					
					if(node.observationMap.containsKey(j)){
						timesObserved = node.observationMap.get(j);
					}
					numerator += (alpha.get(j) + timesObserved + 1);
				}
			}
			
			coverageVector.add(numerator / denominator);
		}
		
		return coverageVector;
	}
	
	public int maxAttackDepth(int mode){
		int maxNumObservations = Integer.MIN_VALUE;
		
		Map<Map<Integer, Integer>, Integer> attackedTargetMap = null;
		
		if(mode == MAX){
			attackedTargetMap = attackedTargetMaxMap;
		}
		else if(mode == MIN){
			attackedTargetMap = attackedTargetMinMap;
		}
		
		for(Map<Integer, Integer> observationVector : attackedTargetMap.keySet()){
			int numObservations = 0;
			
			for(Integer pureStrategy : observationVector.keySet()){
				numObservations += observationVector.get(pureStrategy);
			}
			
			if(numObservations > maxNumObservations){
				maxNumObservations = numObservations;
			}
		}
		
		return maxNumObservations;
	}
}
