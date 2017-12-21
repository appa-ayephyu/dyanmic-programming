package securitygameslimitsurveil;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.Random;


public class OPTSMain {
	public static final int OPTS = 1;
	public static final int SGLS = 2;
	public static final int DOBSS = 3;
	public static final int BI = 10;
	public static final int BIFS = 11;
	public static final int ABIFS = 12;
	
	public static void main(String[] args){
		int option = Integer.parseInt(args[0]);
		
		if(option == 1){
			int numTargets = Integer.parseInt(args[1]);
			int numResources = Integer.parseInt(args[2]);
			
			AttackerDefenderComparison(System.currentTimeMillis(), numTargets, numResources, false);
		}
		else if(option == 2){
			int numTargets = Integer.parseInt(args[1]);
			int numResources = Integer.parseInt(args[2]);
				
			AttackerDefenderComparison_ZeroSum(System.currentTimeMillis(), numTargets, numResources);
		}
		else if(option == 3){
			int numTargets = Integer.parseInt(args[1]);
			int numResources = Integer.parseInt(args[2]);
				
			AttackerAlgorithmComparison_Epsilon(System.currentTimeMillis(), numTargets, numResources);
		}
		else if(option == 4){
			int numTargets = Integer.parseInt(args[1]);
			int numResources = Integer.parseInt(args[2]);
			double observationCost = Double.parseDouble(args[3]);
			
			AttackerDefenderComparison_Alpha(System.currentTimeMillis(), numTargets, numResources, observationCost);
		}
		else if (option == 5){
			int numTargets = Integer.parseInt(args[1]);
			int numResources = Integer.parseInt(args[2]);
			
			AttackerDefenderComparison_PayoffUncertainty(System.currentTimeMillis(), numTargets, numResources);
		}
		else if (option == 6){
			int numTargets = Integer.parseInt(args[1]);
			int numResources = Integer.parseInt(args[2]);
			
			AttackerDefenderComparison_ExecutionUncertainty(System.currentTimeMillis(), numTargets, numResources);
		}
		else if (option == 7){
			int numTargets = Integer.parseInt(args[1]);
			int numResources = Integer.parseInt(args[2]);
			
			AttackerDefenderComparison_ObservationCostUncertainty(System.currentTimeMillis(), numTargets, numResources);
		}
	}
	
	public static void Figure1(String[] args){
    	long seed = System.currentTimeMillis();
    	int numTargets = Integer.parseInt(args[1]);
		int numResources = Integer.parseInt(args[2]);
		double observationCost = Double.parseDouble(args[3]);
		double alphaVal = Double.parseDouble(args[4]);
		
		SecurityGame sg = new SecurityGame(numTargets, numResources);
		sg.initializeRandom(seed);
		
		List<Double> alpha = new ArrayList<Double>();
		
		for(int i = 0; i < CombinationGenerator.getNChooseR(sg.numTargets, sg.numResources).intValue(); i++){
			alpha.add(alphaVal);
		}
		
		OPTSAttackerProblem attProblem = OPTSAttackerProblem.Algorithm1(sg, alpha, observationCost, 0.0001);
		
    	OPTSDefenderProblem defProblem = new OPTSDefenderProblem(attProblem);
    	
    	defProblem.Solve();
    	
    	initializePayoffs(sg);
    	
    	for(int depth = 1; depth <= 10; depth++){
	        GenerateGameFiles.InitStrategiesObservations(numTargets, depth, numResources);
	        GenerateGameFiles.GeneratePrior(0, 0);
	        P1.GenerateAttackerStrategy();
	        
	        P2Nonconvex.Solve();
	        
	        double payoffDiff = defProblem.defenderPayoff - P2Nonconvex.objective;
	        
	        System.out.print(seed + " " + sg.numTargets + " " + sg.numResources + " " + attProblem.numPureStrategies + " ");
	    	System.out.print(observationCost + " " + attProblem.depth + " " + depth + " ");
	    	System.out.print(defProblem.defenderPayoff + " " + P2Nonconvex.objective +  " " + payoffDiff + " ");
	        System.out.println((attProblem.runTime + defProblem.defRunTime) + " " + P2Nonconvex.time);
    	}
	}
	
	public static void AttackerAlgorithmComparison_Epsilon(long seed, int numTargets, int numResources){
    	double beta = 2.0;
		
		SecurityGame sg = new SecurityGame(numTargets, numResources);
		sg.initializeRandom(seed);
		
		List<Double> alpha = new ArrayList<Double>();
		
		int pureStrategies = CombinationGenerator.getNChooseR(sg.numTargets, sg.numResources).intValue();
		
		for(int i = 0; i < pureStrategies; i++){
			alpha.add(0.0);
		}
		
		double[] epsilonValues = {1.0, 0.1, 0.01, 0.001, 0.0001};
		double[] lambdaValues = {.6, .55, .5, .45, .4, .35, .3, .25, .2, .15, .1};
		
		for(int j = 0; j < lambdaValues.length; j++){
			for(int i = 0; i < epsilonValues.length; i++){
				System.out.print(seed + " " + ABIFS + " " + pureStrategies + " " + lambdaValues[j] + " "); 
			
				OPTSAttackerProblem attProblem2 = OPTSAttackerProblem.Algorithm2(sg, alpha, lambdaValues[j], beta, epsilonValues[i]);
			
				System.out.print(attProblem2.depth + " " + attProblem2.maxDepth + " " + attProblem2.finalMinValue + " " + attProblem2.runTime + " ");
				System.out.println(beta + " " + epsilonValues[i]);
			}
		}
	}
	
	public static void AttackerAlgorithmComparison_ObservationCost(String[] args){
    	long seed = System.currentTimeMillis();
    	int option = Integer.parseInt(args[0]);
    	int numTargets = Integer.parseInt(args[1]);
		int numResources = Integer.parseInt(args[2]);
		double beta = Double.parseDouble(args[3]);
		double epsilon = Double.parseDouble(args[4]);
		
		SecurityGame sg = new SecurityGame(numTargets, numResources);
		sg.initializeRandom(seed);
		
		List<Double> alpha = new ArrayList<Double>();
		
		int pureStrategies = CombinationGenerator.getNChooseR(sg.numTargets, sg.numResources).intValue();
		
		for(int i = 0; i < pureStrategies; i++){
			alpha.add(0.0);
		}
		
		double[] observationCosts = {1.0, 0.8, 0.6, 0.4, 0.2};
		
		for(int i = 0; i < observationCosts.length; i++){
		
			System.out.print(seed + " " + option + " " + BIFS + " " + pureStrategies + " " + observationCosts[i] + " ");
			
			OPTSAttackerProblem attProblem1 = OPTSAttackerProblem.Algorithm1(sg, alpha, observationCosts[i], 0.0001);
			
			System.out.println(attProblem1.depth + " " + attProblem1.maxDepth + " " + attProblem1.finalMinValue + " " + attProblem1.runTime);
		    System.out.print(seed + " " + option + " " + ABIFS + " " + pureStrategies + " " + observationCosts[i] + " "); 
			
			OPTSAttackerProblem attProblem2 = OPTSAttackerProblem.Algorithm2(sg, alpha, observationCosts[i], beta, epsilon);
			
			System.out.print(attProblem2.depth + " " + attProblem2.maxDepth + " " + attProblem2.finalMinValue + " " + attProblem2.runTime + " ");
			System.out.println(beta + " " + epsilon);
			
			if(observationCosts[i] >= 0.4){
				System.out.print(seed + " " + option + " " + BI + " " + pureStrategies + " " + observationCosts[i] + " ");
				
				int depth;
				
				if(attProblem1.maxDepth == (int)attProblem1.maxDepth){
					depth = (int)attProblem1.maxDepth;
				}
				else{
					depth = ((int)attProblem1.maxDepth) + 1;
				}
				
				OPTSAttackerProblem attProblem3 = new OPTSAttackerProblem(sg, alpha, depth, observationCosts[i]);
		    	
				long startTime = System.currentTimeMillis();
				
		    	attProblem3.computeOptimalObservationTree(2);
		    	
		    	double runTime = (System.currentTimeMillis() - startTime) / 1000.0;
		    	
			    System.out.println(attProblem3.depth + " " + attProblem1.maxDepth + " " + attProblem3.finalMinValue + " " + runTime);
			}
		}
	}
	
	// Compares OPTS, SGLS with tau estimation, DOBSS
	public static void DefenderAlgorithmComparison_ObservationCost(String[] args){
    	long seed = System.currentTimeMillis();
    	int option = Integer.parseInt(args[0]);
    	int numTargets = Integer.parseInt(args[1]);
		int numResources = Integer.parseInt(args[2]);
		
		SecurityGame sg = new SecurityGame(numTargets, numResources);
		sg.initializeRandom(seed);
		
		List<Double> alpha = new ArrayList<Double>();
		
		for(int i = 0; i < CombinationGenerator.getNChooseR(sg.numTargets, sg.numResources).intValue(); i++){
			alpha.add(0.0);
		}
		
		double[] observationCosts = {1.0, 0.8, 0.6, 0.4};
		
		initializePayoffs(sg);
		
		for(int i = 0; i < observationCosts.length; i++){
			OPTSAttackerProblem attProblem = OPTSAttackerProblem.Algorithm1(sg, alpha, observationCosts[i], 0.0001);
			
	    	OPTSDefenderProblem defProblem = new OPTSDefenderProblem(attProblem);
	    	
	    	defProblem.Solve();
	    	
	    	System.out.print(seed + " " + option + " " + OPTS + " " + attProblem.numPureStrategies + " " + observationCosts[i] + " ");
	    	System.out.print(attProblem.depth + " " + attProblem.maxDepth + " " + defProblem.defenderPayoff + " ");
	    	System.out.println((attProblem.runTime + defProblem.defRunTime));
	    	
	    	int estimatedDepth = estimateDepth(sg, observationCosts[i]);
	    	
	    	GenerateGameFiles.InitStrategiesObservations(numTargets, estimatedDepth, numResources);
	    	GenerateGameFiles.GeneratePrior(0, 0);
	    	P1.GenerateAttackerStrategy();
		        
	    	P2Nonconvex.Solve();
		        
	    	System.out.print(seed + " " + option + " " + SGLS + " " + attProblem.numPureStrategies + " " + observationCosts[i] + " ");
	    	System.out.print(estimatedDepth + " " + attProblem.maxDepth + " " + P2Nonconvex.objective + " ");
	    	System.out.println(P2Nonconvex.time);
	    	
	    	Dobss.Solve();
	    	
	    	System.out.print(seed + " " + option + " " + DOBSS + " " + attProblem.numPureStrategies + " " + observationCosts[i] + " ");
	    	System.out.print(estimatedDepth + " " + attProblem.maxDepth + " " + Dobss.defPayoff + " ");
	    	System.out.println(Dobss.runTime);
		}
	}
	
	public static void DefenderAlgorithmComparison_NumObservations(String[] args){
    	long seed = System.currentTimeMillis();
    	int option = Integer.parseInt(args[0]);
    	int numTargets = Integer.parseInt(args[1]);
		int numResources = Integer.parseInt(args[2]);
		double observationCost = Double.parseDouble(args[3]);
		
		SecurityGame sg = new SecurityGame(numTargets, numResources);
		sg.initializeRandom(seed);
		
		List<Double> alpha = new ArrayList<Double>();
		
		for(int i = 0; i < CombinationGenerator.getNChooseR(sg.numTargets, sg.numResources).intValue(); i++){
			alpha.add(0.0);
		}
		
		OPTSAttackerProblem attProblem = OPTSAttackerProblem.Algorithm1(sg, alpha, observationCost, 0.0001);
		
    	OPTSDefenderProblem defProblem = new OPTSDefenderProblem(attProblem);
    	
    	defProblem.Solve();
		
		initializePayoffs(sg);
		
		for(int depth = 5; depth <= 12; depth++){
	    	System.out.print(seed + " " + option + " " + OPTS + " " + attProblem.numPureStrategies + " " + observationCost + " ");
	    	System.out.print(depth + " " + attProblem.maxDepth + " " + defProblem.defenderPayoff + " ");
	    	System.out.println((attProblem.runTime + defProblem.defRunTime));
	    	
	    	GenerateGameFiles.InitStrategiesObservations(numTargets, depth, numResources);
	    	GenerateGameFiles.GeneratePrior(0, 0);
	    	P1.GenerateAttackerStrategy();
		        
	    	P2Nonconvex.Solve();
		        
	    	System.out.print(seed + " " + option + " " + SGLS + " " + attProblem.numPureStrategies + " " + observationCost + " ");
	    	System.out.print(depth + " " + attProblem.maxDepth + " " + P2Nonconvex.objective + " ");
	    	System.out.println(P2Nonconvex.time);
	    	
	    	Dobss.Solve();
	    	
	    	System.out.print(seed + " " + option + " " + DOBSS + " " + attProblem.numPureStrategies + " " + observationCost + " ");
	    	System.out.print(depth + " " + attProblem.maxDepth + " " + Dobss.defPayoff + " ");
	    	System.out.println(Dobss.runTime);
		}
	}
	
	public static void DefenderAlgorithmComparison_DefenderStrategy(String[] args){
    	long seed = System.currentTimeMillis();
    	int option = Integer.parseInt(args[0]);
    	int numTargets = Integer.parseInt(args[1]);
		int numResources = Integer.parseInt(args[2]);
		
		SecurityGame sg = new SecurityGame(numTargets, numResources);
		sg.initializeRandom(seed);
		
		List<Double> alpha = new ArrayList<Double>();
		
		for(int i = 0; i < CombinationGenerator.getNChooseR(sg.numTargets, sg.numResources).intValue(); i++){
			alpha.add(0.0);
		}
		
		double[] observationCosts = {1.0, 0.8, 0.6, 0.4};
		
		initializePayoffs(sg);
		
		for(int i = 0; i < observationCosts.length; i++){
			OPTSAttackerProblem attProblem = OPTSAttackerProblem.Algorithm1(sg, alpha, observationCosts[i], 0.0001);
			
	    	OPTSDefenderProblem defProblem = new OPTSDefenderProblem(attProblem);
	    	
	    	defProblem.Solve();
	    	
	    	System.out.print(seed + " " + option + " " + OPTS + " " + attProblem.numPureStrategies + " " + observationCosts[i] + " ");
	    	System.out.print(attProblem.depth + " " + attProblem.maxDepth + " " + defProblem.defenderPayoff + " ");
	    	
	    	for(int j = 0; j < attProblem.numPureStrategies; j++){
	    		System.out.print(0.0 + " ");
	    	}
	    	
	    	System.out.println((attProblem.runTime + defProblem.defRunTime));
	    	
	    	int estimatedDepth = estimateDepth(sg, observationCosts[i]);
	    	
	    	GenerateGameFiles.InitStrategiesObservations(numTargets, estimatedDepth, numResources);
	    	GenerateGameFiles.GeneratePrior(0, 0);
	    	P1.GenerateAttackerStrategy();
		        
	    	P2Nonconvex.Solve();
	    	
	    	List<Double> sglsDiff = compareStrategies(defProblem.defStrategy, P2Nonconvex.defStrategy);
		        
	    	System.out.print(seed + " " + option + " " + SGLS + " " + attProblem.numPureStrategies + " " + observationCosts[i] + " ");
	    	System.out.print(estimatedDepth + " " + attProblem.maxDepth + " " + P2Nonconvex.objective + " ");
	    	
	    	for(int j = 0; j < sglsDiff.size(); j++){
	    		System.out.print(sglsDiff.get(j) + " ");
	    	}
	    	
	    	System.out.println(P2Nonconvex.time);
	    	
	    	Dobss.Solve();
	    	
	    	List<Double> dobssDiff = compareStrategies(defProblem.defStrategy, Dobss.defStrategy);
	    	
	    	System.out.print(seed + " " + option + " " + DOBSS + " " + attProblem.numPureStrategies + " " + observationCosts[i] + " ");
	    	System.out.print(estimatedDepth + " " + attProblem.maxDepth + " " + Dobss.defPayoff + " ");
	    	
	    	for(int j = 0; j < dobssDiff.size(); j++){
	    		System.out.print(dobssDiff.get(j) + " ");
	    	}
	    	
	    	System.out.println(Dobss.runTime);
		}
	}
	
	public static void AttackerDefenderComparison_Alpha(long seed, int numTargets, int numResources, double observationCost){
		SecurityGame sg = new SecurityGame(numTargets, numResources);
		sg.initializeRandom(seed);
		
		int pureStrategies = CombinationGenerator.getNChooseR(sg.numTargets, sg.numResources).intValue();
		
		double[] alphaValues = {0, 0.01, 0.05, 0.1, 0.25, 0.5, 1.0, 2.0, 5.0};
		
		initializePayoffs(sg);
		
		for(int i = 0; i < alphaValues.length; i++){
			List<Double> alpha = new ArrayList<Double>();
			
			for(int j = 0; j < CombinationGenerator.getNChooseR(sg.numTargets, sg.numResources).intValue(); j++){
				alpha.add(alphaValues[i]);
			}
			
			System.out.print(seed + "\t" + "\t" + pureStrategies + "\t" + observationCost + "\t" + alphaValues[i] + "\t");
			
			OPTSAttackerProblem attProblem1 = OPTSAttackerProblem.Algorithm1(sg, alpha, observationCost, 0.0001);
			
			if(attProblem1 != null){
				System.out.print(attProblem1.depth + "\t" + attProblem1.finalMinValue + "\t" + attProblem1.runTime + "\t");
			
		    	OPTSDefenderProblem defProblem = new OPTSDefenderProblem(attProblem1);
		    	
		    	defProblem.Solve();
		    	
		    	System.out.print(defProblem.defenderPayoff + "\t" + defProblem.defRunTime + "\t");
		    	
		    	int estimatedDepth = estimateDepth(sg, observationCost);
		    	
		    	System.out.print(estimatedDepth + "\t");
		    	
		    	GenerateGameFiles.InitStrategiesObservations(numTargets, estimatedDepth, numResources);
		    	GenerateGameFiles.GeneratePrior(alphaValues[i], 0);
		    	P1.GenerateAttackerStrategy();
			        
		    	P2Nonconvex.Solve();
		    	
		    	System.out.print(DefenderObjectiveValue(defProblem, P2Nonconvex.defStrategy, sg) + "\t" + P2Nonconvex.time + "\t");
		    	
		    	Dobss.Solve();
		    	
		    	System.out.println(DefenderObjectiveValue(defProblem, Dobss.defStrategy, sg) + "\t" + Dobss.runTime);
			}
			else{
				System.out.println();
			}
		}
	}
	
	public static void AttackerDefenderComparison(long seed, int numTargets, int numResources, boolean zeroSum){
		double beta = 2.0;
		double epsilon = 0.001;
		
		SecurityGame sg = new SecurityGame(numTargets, numResources);
		
		if(zeroSum){
			sg.initializeRandomZeroSum(seed);
		}
		else{
			sg.initializeRandom(seed);
		}
		
		List<Double> alpha = new ArrayList<Double>();
		
		for(int i = 0; i < CombinationGenerator.getNChooseR(sg.numTargets, sg.numResources).intValue(); i++){
			alpha.add(0.0);
		}
		
		int pureStrategies = CombinationGenerator.getNChooseR(sg.numTargets, sg.numResources).intValue();
		
		double[] observationCosts = {1.0, 0.6, 0.5, 0.4, 0.3, 0.25, 0.2};
		
		initializePayoffs(sg);
		
		for(int i = 0; i < observationCosts.length; i++){
			System.out.print(seed + "\t" + zeroSum + "\t" + pureStrategies + "\t" + observationCosts[i] + "\t");
			
			OPTSAttackerProblem attProblem1 = OPTSAttackerProblem.Algorithm1(sg, alpha, observationCosts[i], 0.0001);
			System.out.print(attProblem1.depth + "\t" + attProblem1.finalMinValue + "\t" + attProblem1.runTime + "\t"); 
			
			OPTSAttackerProblem attProblem2 = OPTSAttackerProblem.Algorithm2(sg, alpha, observationCosts[i], beta, epsilon);
			System.out.print(attProblem2.depth + "\t" + attProblem2.finalMinValue + "\t" + attProblem2.runTime + "\t");
			
			if(observationCosts[i] >= 0.4){
				int depth;
				
				if(attProblem1.maxDepth == (int)attProblem1.maxDepth){
					depth = (int)attProblem1.maxDepth;
				}
				else{
					depth = ((int)attProblem1.maxDepth) + 1;
				}
				
				OPTSAttackerProblem attProblem3 = new OPTSAttackerProblem(sg, alpha, depth, observationCosts[i]);
		    	
				long startTime = System.currentTimeMillis();
				
		    	attProblem3.computeOptimalObservationTree(2);
		    	
		    	double runTime = (System.currentTimeMillis() - startTime) / 1000.0;
		    	
			    System.out.print(attProblem3.depth + "\t" + attProblem3.finalMinValue + "\t" + runTime + "\t");
			}
			else{
				System.out.print("\t\t\t");
			}
			
	    	OPTSDefenderProblem defProblem = new OPTSDefenderProblem(attProblem1);
	    	
	    	defProblem.Solve();
	    	
	    	System.out.print(defProblem.defenderPayoff + "\t" + defProblem.defRunTime + "\t");
	    	
	    	int estimatedDepth = estimateDepth(sg, observationCosts[i]);
	    	
	    	System.out.print(estimatedDepth + "\t");
	    	
	    	GenerateGameFiles.InitStrategiesObservations(numTargets, estimatedDepth, numResources);
	    	GenerateGameFiles.GeneratePrior(0, 0);
	    	P1.GenerateAttackerStrategy();
		        
	    	P2Nonconvex.Solve();
	    	
	    	List<Double> sglsDiff = compareStrategies(defProblem.defStrategy, P2Nonconvex.defStrategy);
	    	
	    	for(int j = 0; j < sglsDiff.size(); j++){
	    		System.out.print(sglsDiff.get(j) + "\t");
	    	}
	    	
	    	System.out.print(DefenderObjectiveValue(defProblem, P2Nonconvex.defStrategy, sg) + "\t" + P2Nonconvex.time + "\t");
	    	
	    	Dobss.Solve();
	    	
	    	List<Double> dobssDiff = compareStrategies(defProblem.defStrategy, Dobss.defStrategy);
	    	
	    	for(int j = 0; j < dobssDiff.size(); j++){
	    		System.out.print(dobssDiff.get(j) + "\t");
	    	}
	    	
	    	System.out.println(DefenderObjectiveValue(defProblem, Dobss.defStrategy, sg) + "\t" + Dobss.runTime);
		}
	}
	
	public static void AttackerDefenderComparison_ZeroSum(long seed, int numTargets, int numResources){
		SecurityGame sg = new SecurityGame(numTargets, numResources);
		
		sg.initializeRandomZeroSum(seed);
		
		List<Double> alpha = new ArrayList<Double>();
		
		for(int i = 0; i < CombinationGenerator.getNChooseR(sg.numTargets, sg.numResources).intValue(); i++){
			alpha.add(0.0);
		}
		
		int pureStrategies = CombinationGenerator.getNChooseR(sg.numTargets, sg.numResources).intValue();
		
		double[] observationCosts = {1.0, 0.6, 0.5, 0.4, 0.3, 0.25, 0.2};
		
		initializePayoffs(sg);
		
		for(int i = 0; i < observationCosts.length; i++){
			System.out.print(seed + "\t" + pureStrategies + "\t" + observationCosts[i] + "\t");
			
			OPTSAttackerProblem attProblem1 = OPTSAttackerProblem.Algorithm1(sg, alpha, observationCosts[i], 0.0001);
			System.out.print(attProblem1.depth + "\t" + attProblem1.finalMinValue + "\t" + attProblem1.runTime + "\t"); 
			
			OPTSDefenderProblem defProblem = new OPTSDefenderProblem(attProblem1);
	    	
	    	defProblem.Solve();
	    	
	    	System.out.print(defProblem.defenderPayoff + "\t" + defProblem.defRunTime + "\t");
	    	
	    	int estimatedDepth = estimateDepth(sg, observationCosts[i]);
	    	
	    	System.out.print(estimatedDepth + "\t");
	    	
	    	GenerateGameFiles.InitStrategiesObservations(numTargets, estimatedDepth, numResources);
	    	GenerateGameFiles.GeneratePrior(0, 0);
	    	P1.GenerateAttackerStrategy();
		        
	    	P2Nonconvex.Solve();
	    	
	    	List<Double> sglsDiff = compareStrategies(defProblem.defStrategy, P2Nonconvex.defStrategy);
	    	
	    	for(int j = 0; j < sglsDiff.size(); j++){
	    		System.out.print(sglsDiff.get(j) + "\t");
	    	}
	    	
	    	System.out.print(DefenderObjectiveValue(defProblem, P2Nonconvex.defStrategy, sg) + "\t" + P2Nonconvex.time + "\t");
	    	
	    	Dobss.Solve();
	    	
	    	List<Double> dobssDiff = compareStrategies(defProblem.defStrategy, Dobss.defStrategy);
	    	
	    	for(int j = 0; j < dobssDiff.size(); j++){
	    		System.out.print(dobssDiff.get(j) + "\t");
	    	}
	    	
	    	System.out.println(DefenderObjectiveValue(defProblem, Dobss.defStrategy, sg) + "\t" + Dobss.runTime);
		}
	}
	
	public static void SeededAttackerDefenderComparison(String[] args){
		long seed = Long.parseLong(args[0]);
    	int numTargets = Integer.parseInt(args[1]);
		int numResources = Integer.parseInt(args[2]);
		double observationCost = Double.parseDouble(args[3]);
		double beta = 2.0;
		double epsilon = 0.001;
		
		SecurityGame sg = new SecurityGame(numTargets, numResources);
		sg.initializeRandom(seed);
		
		List<Double> alpha = new ArrayList<Double>();
		
		for(int i = 0; i < CombinationGenerator.getNChooseR(sg.numTargets, sg.numResources).intValue(); i++){
			alpha.add(0.0);
		}
		
		int pureStrategies = CombinationGenerator.getNChooseR(sg.numTargets, sg.numResources).intValue();
		
		initializePayoffs(sg);
		
		System.out.print(seed + "\t" + pureStrategies + "\t" + observationCost + "\t");
		
		OPTSAttackerProblem attProblem1 = OPTSAttackerProblem.Algorithm1(sg, alpha, observationCost, 0.0001);
		System.out.print(attProblem1.depth + "\t" + attProblem1.finalMinValue + "\t" + attProblem1.runTime + "\t"); 
		
		OPTSAttackerProblem attProblem2 = OPTSAttackerProblem.Algorithm2(sg, alpha, observationCost, beta, epsilon);
		System.out.print(attProblem2.depth + "\t" + attProblem2.finalMinValue + "\t" + attProblem2.runTime + "\t");
		
		if(observationCost >= 0.4){
			int depth;
			
			if(attProblem1.maxDepth == (int)attProblem1.maxDepth){
				depth = (int)attProblem1.maxDepth;
			}
			else{
				depth = ((int)attProblem1.maxDepth) + 1;
			}
			
			OPTSAttackerProblem attProblem3 = new OPTSAttackerProblem(sg, alpha, depth, observationCost);
	    	
			long startTime = System.currentTimeMillis();
			
	    	attProblem3.computeOptimalObservationTree(2);
	    	
	    	double runTime = (System.currentTimeMillis() - startTime) / 1000.0;
	    	
		    System.out.print(attProblem3.depth + "\t" + attProblem3.finalMinValue + "\t" + runTime + "\t");
		}
		else{
			System.out.print("\t\t\t");
		}
		
    	OPTSDefenderProblem defProblem = new OPTSDefenderProblem(attProblem1);
    	
    	defProblem.Solve();
    	
    	System.out.print(defProblem.defenderPayoff + "\t" + defProblem.defRunTime + "\t");
    	
    	int estimatedDepth = estimateDepth(sg, observationCost);
    	
    	System.out.print(estimatedDepth + "\t");
    	
    	GenerateGameFiles.InitStrategiesObservations(numTargets, estimatedDepth, numResources);
    	GenerateGameFiles.GeneratePrior(0, 0);
    	P1.GenerateAttackerStrategy();
	        
    	P2Nonconvex.Solve();
    	
    	List<Double> sglsDiff = compareStrategies(defProblem.defStrategy, P2Nonconvex.defStrategy);
    	
    	for(int j = 0; j < sglsDiff.size(); j++){
    		System.out.print(sglsDiff.get(j) + "\t");
    	}
    	
    	System.out.print(DefenderObjectiveValue(defProblem, P2Nonconvex.defStrategy, sg) + "\t" + P2Nonconvex.time + "\t");
    	
    	Dobss.Solve();
    	
    	List<Double> dobssDiff = compareStrategies(defProblem.defStrategy, Dobss.defStrategy);
    	
    	for(int j = 0; j < dobssDiff.size(); j++){
    		System.out.print(dobssDiff.get(j) + "\t");
    	}
    	
    	System.out.println(DefenderObjectiveValue(defProblem, Dobss.defStrategy, sg) + "\t" + Dobss.runTime);
	}
	
	public static void DefenderAlgorithmComparison_PureStrategies(String[] args){
    	long seed = System.currentTimeMillis();
    	int option = Integer.parseInt(args[0]);
    	int numTargets = Integer.parseInt(args[1]);
		int numResources = Integer.parseInt(args[2]);
		double observationCost = Double.parseDouble(args[3]);
		
		SecurityGame sg = new SecurityGame(numTargets, numResources);
		sg.initializeRandom(seed);
		
		List<Double> alpha = new ArrayList<Double>();
		
		for(int i = 0; i < CombinationGenerator.getNChooseR(sg.numTargets, sg.numResources).intValue(); i++){
			alpha.add(0.0);
		}
		
		initializePayoffs(sg);
		
		OPTSAttackerProblem attProblem = OPTSAttackerProblem.Algorithm1(sg, alpha, observationCost, 0.0001);
			
    	OPTSDefenderProblem defProblem = new OPTSDefenderProblem(attProblem);
    	
    	defProblem.Solve();
    	
    	System.out.print(seed + " " + option + " " + OPTS + " " + attProblem.numPureStrategies + " " + observationCost + " ");
    	System.out.print(attProblem.depth + " " + attProblem.maxDepth + " " + defProblem.defenderPayoff + " ");
    	System.out.println((attProblem.runTime + defProblem.defRunTime));
    	
    	int estimatedDepth = estimateDepth(sg, observationCost);
    	
    	GenerateGameFiles.InitStrategiesObservations(numTargets, estimatedDepth, numResources);
    	GenerateGameFiles.GeneratePrior(0, 0);
    	P1.GenerateAttackerStrategy();
	        
    	P2Nonconvex.Solve();
	        
    	System.out.print(seed + " " + option + " " + SGLS + " " + attProblem.numPureStrategies + " " + observationCost + " ");
    	System.out.print(estimatedDepth + " " + attProblem.maxDepth + " " + P2Nonconvex.objective + " ");
    	System.out.println(P2Nonconvex.time);
    	
    	Dobss.Solve();
    	
    	System.out.print(seed + " " + option + " " + DOBSS + " " + attProblem.numPureStrategies + " " + observationCost + " ");
    	System.out.print(estimatedDepth + " " + attProblem.maxDepth + " " + Dobss.defPayoff + " ");
    	System.out.println(Dobss.runTime);
	}
	
	public static void AttackerDefenderComparison_PayoffUncertainty(long seed, int numTargets, int numResources){
		SecurityGame sg = new SecurityGame(numTargets, numResources);
		sg.initializeRandom(seed);
		
		List<Double> alpha = new ArrayList<Double>();
		
		for(int i = 0; i < CombinationGenerator.getNChooseR(sg.numTargets, sg.numResources).intValue(); i++){
			alpha.add(0.0);
		}
		
		double[] observationCosts = {0.6, 0.5, 0.4, 0.3, 0.2};
		double[] noiseLevels = {0.0, 1.0, 1.5};
		
		for(int i = 0; i < observationCosts.length; i++){
			initializePayoffs(sg);
			
			OPTSAttackerProblem noiselessAttProblem = OPTSAttackerProblem.Algorithm1(sg, alpha, observationCosts[i], 0.0001);
			
	    	OPTSDefenderProblem noiselessDefProblem = new OPTSDefenderProblem(noiselessAttProblem);
	    	
	    	noiselessDefProblem.Solve();
			
			for(int j = 0; j < noiseLevels.length; j++){
				SecurityGame nsg = new SecurityGame(numTargets, numResources);
				nsg.initializeNoisy(seed, sg, noiseLevels[j]);
				
				initializePayoffs(nsg);
				
				OPTSAttackerProblem attProblem = OPTSAttackerProblem.Algorithm1(nsg, alpha, observationCosts[i], 0.0001);
				
		    	OPTSDefenderProblem defProblem = new OPTSDefenderProblem(attProblem);
		    	
		    	defProblem.Solve();
		    	
		    	PureStrategies.init(numTargets, numResources);
		        PureStrategies.GeneratePureStrategies();
		    	
		    	System.out.print(seed + " " + OPTS + " " + attProblem.numPureStrategies + " " + observationCosts[i] + " ");
		    	System.out.print(attProblem.depth + " " + attProblem.maxDepth + " " + noiseLevels[j] + " ");
		    	System.out.println(DefenderObjectiveValue(defProblem, defProblem.defStrategy, sg) + " " + (attProblem.runTime + defProblem.defRunTime));
		    	
		    	int estimatedDepth = estimateDepth(nsg, observationCosts[i]);
		    	
		    	GenerateGameFiles.InitStrategiesObservations(numTargets, estimatedDepth, numResources);
		    	GenerateGameFiles.GeneratePrior(0, 0);
		    	P1.GenerateAttackerStrategy();
			        
		    	P2Nonconvex.Solve();
			        
		    	System.out.print(seed + " " + SGLS + " " + attProblem.numPureStrategies + " " + observationCosts[i] + " ");
		    	System.out.print(estimatedDepth + " " + attProblem.maxDepth + " " + noiseLevels[j] + " ");
		    	System.out.println(DefenderObjectiveValue(defProblem, P2Nonconvex.defStrategy, sg) + " " + P2Nonconvex.time);
		    	
		    	Dobss.Solve();
		    	
		    	System.out.print(seed + " " + DOBSS + " " + attProblem.numPureStrategies + " " + observationCosts[i] + " ");
		    	System.out.print(estimatedDepth + " " + attProblem.maxDepth + " " + noiseLevels[j] + " ");	    	
		    	System.out.println(DefenderObjectiveValue(defProblem, Dobss.defStrategy, sg) + " " + Dobss.runTime);
			}
		}
	}
	
	public static void AttackerDefenderComparison_ExecutionUncertainty(long seed, int numTargets, int numResources){
		SecurityGame sg = new SecurityGame(numTargets, numResources);
		sg.initializeRandom(seed);
		
		List<Double> alpha = new ArrayList<Double>();
		
		for(int i = 0; i < CombinationGenerator.getNChooseR(sg.numTargets, sg.numResources).intValue(); i++){
			alpha.add(0.0);
		}
		
		initializePayoffs(sg);
		
		double[] observationCosts = {0.6, 0.5, 0.4, 0.3, 0.2};
		double[] noiseLevels = {0.0, 0.1, 0.2};
		
		for(int i = 0; i < observationCosts.length; i++){
			OPTSAttackerProblem attProblem = OPTSAttackerProblem.Algorithm1(sg, alpha, observationCosts[i], 0.0001);
			
	    	OPTSDefenderProblem defProblem = new OPTSDefenderProblem(attProblem);
	    	
	    	defProblem.Solve();
	    	
	    	PureStrategies.init(numTargets, numResources);
	        PureStrategies.GeneratePureStrategies();
	    	
	    	int estimatedDepth = estimateDepth(sg, observationCosts[i]);
	    	
	    	GenerateGameFiles.InitStrategiesObservations(numTargets, estimatedDepth, numResources);
	    	GenerateGameFiles.GeneratePrior(0, 0);
	    	P1.GenerateAttackerStrategy();
		        
	    	P2Nonconvex.Solve();
		        
	    	Dobss.Solve();
			
			for(int j = 0; j < noiseLevels.length; j++){
				ArrayList<Double> noisyOPTSStrategy = GenerateNoisyStrategy(defProblem.defStrategy, noiseLevels[j]);
				
				System.out.print(seed + " " + OPTS + " " + attProblem.numPureStrategies + " " + observationCosts[i] + " ");
		    	System.out.print(attProblem.depth + " " + attProblem.maxDepth + " " + noiseLevels[j] + " ");
		    	System.out.println(DefenderObjectiveValue(defProblem, noisyOPTSStrategy, sg) + " " + (attProblem.runTime + defProblem.defRunTime));
		    	
		    	ArrayList<Double> noisySGLSStrategy = GenerateNoisyStrategy(P2Nonconvex.defStrategy, noiseLevels[j]);
		    	
		    	System.out.print(seed + " " + SGLS + " " + attProblem.numPureStrategies + " " + observationCosts[i] + " ");
		    	System.out.print(estimatedDepth + " " + attProblem.maxDepth + " " + noiseLevels[j] + " ");
		    	System.out.println(DefenderObjectiveValue(defProblem, noisySGLSStrategy, sg) + " " + P2Nonconvex.time);
		    	
		    	ArrayList<Double> noisyDOBSSStrategy = GenerateNoisyStrategy(Dobss.defStrategy, noiseLevels[j]);
		    	
		    	System.out.print(seed + " " + DOBSS + " " + attProblem.numPureStrategies + " " + observationCosts[i] + " ");
		    	System.out.print(estimatedDepth + " " + attProblem.maxDepth + " " + noiseLevels[j] + " ");	    	
		    	System.out.println(DefenderObjectiveValue(defProblem, noisyDOBSSStrategy, sg) + " " + Dobss.runTime);
			}
		}
	}
	
	public static void AttackerDefenderComparison_ObservationCostUncertainty(long seed, int numTargets, int numResources){
		SecurityGame sg = new SecurityGame(numTargets, numResources);
		sg.initializeRandom(seed);
		
		List<Double> alpha = new ArrayList<Double>();
		
		for(int i = 0; i < CombinationGenerator.getNChooseR(sg.numTargets, sg.numResources).intValue(); i++){
			alpha.add(0.0);
		}
		
		initializePayoffs(sg);
		
		double[] observationCosts = {0.6, 0.5, 0.4, 0.3, 0.2};
		double[] noiseLevels = {0.0, 0.1, 0.2};
		
		Random r = new Random(seed);
		
		for(int i = 0; i < observationCosts.length; i++){
			//OPTSAttackerProblem attProblem = OPTSAttackerProblem.Algorithm1(sg, alpha, observationCosts[i], 0.0001);
			OPTSAttackerProblem attProblem = OPTSAttackerProblem.Algorithm2(sg, alpha, observationCosts[i], 2.0, 0.001);
			
	    	OPTSDefenderProblem defProblem = new OPTSDefenderProblem(attProblem);
	    	
	    	defProblem.Solve();
	    	
	    	for(int j = 0; j < noiseLevels.length; j++){
	    		//double noise = (r.nextDouble() - 0.5) * 2.0 * noiseLevels[j];
	    		double noise = r.nextDouble() * noiseLevels[j];
				double noisyObservationCost = observationCosts[i] + noise;
				
				System.out.print(seed + " " + OPTS + " " + attProblem.numPureStrategies + " " + observationCosts[i] + " " + noisyObservationCost + " ");
				
				//OPTSAttackerProblem noisyAttProblem = OPTSAttackerProblem.Algorithm1(sg, alpha, noisyObservationCost, 0.0001);
				OPTSAttackerProblem noisyAttProblem = OPTSAttackerProblem.Algorithm2(sg, alpha, noisyObservationCost, 2.0, 0.001);
				
		    	OPTSDefenderProblem noisyDefProblem = new OPTSDefenderProblem(noisyAttProblem);
		    	
		    	noisyDefProblem.Solve();
		    	
		    	PureStrategies.init(numTargets, numResources);
		        PureStrategies.GeneratePureStrategies();
		    	
		    	//System.out.print(seed + " " + OPTS + " " + attProblem.numPureStrategies + " " + observationCosts[i] + " ");
		    	System.out.print(attProblem.depth + " " + attProblem.maxDepth + " " + noiseLevels[j] + " ");
		    	System.out.println(DefenderObjectiveValue(defProblem, noisyDefProblem.defStrategy, sg) + " " + (noisyAttProblem.runTime + noisyDefProblem.defRunTime));
		    	
		    	int estimatedDepth = estimateDepth(sg, noisyObservationCost);
		    	
		    	GenerateGameFiles.InitStrategiesObservations(numTargets, estimatedDepth, numResources);
		    	GenerateGameFiles.GeneratePrior(0, 0);
		    	P1.GenerateAttackerStrategy();
			        
		    	P2Nonconvex.Solve();
		    	
		    	System.out.print(seed + " " + SGLS + " " + attProblem.numPureStrategies + " " + observationCosts[i] + " ");
		    	System.out.print(noisyObservationCost + " " + estimatedDepth + " " + attProblem.maxDepth + " " + noiseLevels[j] + " ");
		    	System.out.println(DefenderObjectiveValue(defProblem, P2Nonconvex.defStrategy, sg) + " " + P2Nonconvex.time);
			        
		    	Dobss.Solve();
				
		    	System.out.print(seed + " " + DOBSS + " " + attProblem.numPureStrategies + " " + observationCosts[i] + " ");
		    	System.out.print(noisyObservationCost + " " + estimatedDepth + " " + attProblem.maxDepth + " " + noiseLevels[j] + " ");	    	
		    	System.out.println(DefenderObjectiveValue(defProblem, Dobss.defStrategy, sg) + " " + Dobss.runTime);
			}
		}
	}
	
	public static int estimateDepth(SecurityGame sg, double observationCost){
		Map<Integer, Double> depthPayoffMap = new HashMap<Integer, Double>();
		int n = 0;
		
		double previousValue = Double.NEGATIVE_INFINITY;
		double currentValue = Double.NEGATIVE_INFINITY; 
		
		initializePayoffs(sg);
		
		int fibDepth;
		
		int lowerBound = 0;
    	
		while(true){
			previousValue = currentValue;
			
			fibDepth = (int)fib(n);
			
			GenerateGameFiles.InitStrategiesObservations(sg.numTargets, fibDepth, sg.numResources);
    	    GenerateGameFiles.GeneratePrior(0, 0);
    	    P1.GenerateAttackerStrategy();
    	        
    	    P2Nonconvex.Solve();
    	    
    	    AttackerUtility.Solve(observationCost, P2Nonconvex.defStrategy);
    	    
    	    currentValue = AttackerUtility.finalUtility;
    	    
    	    depthPayoffMap.put(fibDepth, currentValue);
    	    
    	    if(currentValue <= previousValue){
    	    	break;
    	    }
    	    else{
    	    	lowerBound = fibDepth;
    	    	
    	    	if(n == 0){
    	    		n = 2;
    	    	}
    	    	else{
    	    		n++;
    	    	}
    	    }
		}
	    	
	    int upperBound = fibDepth;
	    	
	    //previousValue = Double.NEGATIVE_INFINITY;
		//currentValue = Double.NEGATIVE_INFINITY;
	    
	    previousValue = depthPayoffMap.get(lowerBound);
		
		try{
			while(upperBound - lowerBound > 1){
	    		int depth = (upperBound + lowerBound) / 2;
	    		
	    		if(depthPayoffMap.containsKey(depth)){
	    			currentValue = depthPayoffMap.get(depth);
	    		}
	    		else{
	    			GenerateGameFiles.InitStrategiesObservations(sg.numTargets, depth, sg.numResources);
	    			GenerateGameFiles.GeneratePrior(0, 0);
	    			P1.GenerateAttackerStrategy();
	    	        
		    	    P2Nonconvex.Solve();
		    	    
		    	    AttackerUtility.Solve(observationCost, P2Nonconvex.defStrategy);
		    	    
		    	    currentValue = AttackerUtility.finalUtility;
	    		}
	    		
	    		if(currentValue > previousValue){
	    			lowerBound = depth + 1;
	    		}
	    		else{
	    			upperBound = depth;
	    		}
	    		
	    		depthPayoffMap.put(depth, currentValue);
	    		
	    		previousValue = currentValue;
	    	}
			
			System.out.println(depthPayoffMap.toString());
			
			if(!depthPayoffMap.containsKey(lowerBound)){
				return lowerBound - 1;
			}
			else if(depthPayoffMap.get(lowerBound) > depthPayoffMap.get(upperBound)){
	    		return lowerBound;
	    	}
	    	else{
	    		return upperBound;
	    	}
		}
		catch(NullPointerException e){
			System.out.println("***** ERROR *****");
			System.out.println(lowerBound + " " + upperBound);
			System.out.println(depthPayoffMap.toString());
			
			return 0;
		}
    }
	
	public static ArrayList<Double> compareStrategies(ArrayList<Double> x1, ArrayList<Double> x2){
		if(x1.size() != x2.size()){
			return null;
		}
		
		ArrayList<Double> diff = new ArrayList<Double>();
		
		for(int i = 0; i < x1.size(); i++){
			diff.add(x1.get(i) - x2.get(i));
		}
		
		return diff;
	}
	
	public static double DefenderObjectiveValue(OPTSDefenderProblem defenderProblem, ArrayList<Double> x, SecurityGame sg) {
		int numTargets = defenderProblem.attackerProblem.sg.numTargets;
		int numPureStrategies = defenderProblem.attackerProblem.numPureStrategies;
		List<Map<Integer, Integer>> observationVectorList = new ArrayList<Map<Integer, Integer>>(defenderProblem.attackMap.keySet());
		List<Set<Integer>> pureStrategies = defenderProblem.attackerProblem.pureStrategies;
		
		double[] d = new double[observationVectorList.size()];
    	double[] c = new double[numTargets];
    
        List<Long> preProb = defenderProblem.probList;

        // Compute c_j (or the marginal)
        for (int j = 0; j < numTargets; j++) {
            double coverage = 0.0;
            
            for (int i = 0; i < numPureStrategies; i++) {
                List<Integer> strategy = defenderProblem.getCoverageVector(pureStrategies.get(i));
            	
                coverage += strategy.get(j)*x.get(i);
            }
            
            c[j] = coverage;
        }
        
        List<Target> targetList = new ArrayList<Target>(sg.setTargets);
		Collections.sort(targetList);

        // Compute d^o
        for(int i = 0; i < observationVectorList.size(); i++){
            int j = defenderProblem.attackMap.get(observationVectorList.get(i));
            //d[i] = c[j]*(GenerateGameFiles.defCov.get(j) - GenerateGameFiles.defUncov.get(j)) +
            //      GenerateGameFiles.defUncov.get(j);
            d[i] = c[j]*(targetList.get(j).defCovPayoff - targetList.get(j).defUncovPayoff) +
            		targetList.get(j).defUncovPayoff;
        }

        // Compute objective value
        double objective = 0.0;
        
        for(int i = 0; i < defenderProblem.observationVectorList.size(); i++){
        	
        	
        	double prod = 1.0;
            for (int j = 0; j < PureStrategies.globalList.size(); j++) {
            	List<Integer> observationVector = defenderProblem.getObservationVector(observationVectorList.get(i));
                prod = prod * Math.pow(x.get(j), observationVector.get(j));
            }
            prod = prod * preProb.get(i) * d[i];

            objective += prod;
        }

        return objective;
	}
	
	public static ArrayList<Double> GenerateNoisyStrategy(ArrayList<Double> strategy, double noiseLevel){
		ArrayList<Double> noisyStrategy = new ArrayList<Double>();
		
		long seed = System.currentTimeMillis();
		
		Random r = new Random(seed);
		
		double totalCoverage = 0.0;
		
		for(int i = 0; i < strategy.size(); i++){
			double noise = (r.nextDouble() - 0.5) * 2.0 * noiseLevel; 
			double coverage = strategy.get(i) + noise;
			coverage = Math.max(0, Math.min(1, coverage));
			
			noisyStrategy.add(coverage);
			
			totalCoverage += coverage;
		}
		
		for(int i = 0; i < strategy.size(); i++){
			double normalizedCoverage = noisyStrategy.get(i) / totalCoverage;
			noisyStrategy.set(i, normalizedCoverage);
		}
		
		return noisyStrategy;
	}
	
	public static double AttackerObjectiveValue(OPTSDefenderProblem defenderProblem, ArrayList<Double> x) {
		int numTargets = defenderProblem.attackerProblem.sg.numTargets;
		int numPureStrategies = defenderProblem.attackerProblem.numPureStrategies;
		List<Map<Integer, Integer>> observationVectorList = new ArrayList<Map<Integer, Integer>>(defenderProblem.attackMap.keySet());
		List<Set<Integer>> pureStrategies = defenderProblem.attackerProblem.pureStrategies;
		
		double[] d = new double[observationVectorList.size()];
    	double[] c = new double[numTargets];
    
        List<Long> preProb = defenderProblem.probList;

        // Compute c_j (or the marginal)
        for (int j = 0; j < numTargets; j++) {
            double coverage = 0.0;
            
            for (int i = 0; i < numPureStrategies; i++) {
                List<Integer> strategy = defenderProblem.getCoverageVector(pureStrategies.get(i));
            	
                coverage += strategy.get(j)*x.get(i);
            }
            
            c[j] = coverage;
        }

        // Compute d^o
        for(int i = 0; i < observationVectorList.size(); i++){
            int j = defenderProblem.attackMap.get(observationVectorList.get(i));
            d[i] = c[j]*(GenerateGameFiles.attCov.get(j) - GenerateGameFiles.attUncov.get(j)) +
                    GenerateGameFiles.attUncov.get(j);
        }

        // Compute objective value
        double objective = 0.0;
        
        for(int i = 0; i < defenderProblem.observationVectorList.size(); i++){
        	double prod = 1.0;
            for (int j = 0; j < PureStrategies.globalList.size(); j++) {
                prod = prod * Math.pow(x.get(j), observationVectorList.get(i).get(j));
            }
            prod = prod * preProb.get(i) * d[i];

            objective += prod;
        }

        return objective;
	}
	
	public static void initializePayoffs(SecurityGame sg){
		List<Target> targetList = new ArrayList<Target>(sg.setTargets);
		Collections.sort(targetList);
		
		GenerateGameFiles.nTargets = sg.numTargets;
		GenerateGameFiles.nResources = sg.numResources;
		
		
		GenerateGameFiles.defCov = new ArrayList<Double>();
		GenerateGameFiles.defUncov = new ArrayList<Double>();
		GenerateGameFiles.attCov = new ArrayList<Double>();
		GenerateGameFiles.attUncov = new ArrayList<Double>();
		
		for(Target t : targetList){
			GenerateGameFiles.defCov.add(t.defCovPayoff);
			GenerateGameFiles.defUncov.add(t.defUncovPayoff);
			GenerateGameFiles.attCov.add(t.attCovPayoff);
			GenerateGameFiles.attUncov.add(t.attUncovPayoff);
		}
	}
	
	public static long fib(int n) {
        if (n <= 1) return n;
        else return fib(n-1) + fib(n-2);
    }
}
