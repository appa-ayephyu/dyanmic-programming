package securitygameslimitsurveil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public class OPTSDefenderProblem{
	public double defenderPayoff;
    public double defRunTime;
    public ArrayList<Double> defStrategy;
    public int attackSetSize;
    public double entropy;
    
    OPTSAttackerProblem attackerProblem;
    Map<Map<Integer, Integer>, Integer> attackMap;
    List<Map<Integer, Integer>> observationVectorList;
    
    List<Long> probList;
    
    public OPTSDefenderProblem(OPTSAttackerProblem attackerProblem){
    	this.attackerProblem = attackerProblem;
    	this.attackMap = attackerProblem.attackedTargetMinMap;
    	this.observationVectorList = new ArrayList<Map<Integer, Integer>>(attackMap.keySet());
    }
    
    /*
    public static void test(String[] args){
    	long seed = System.currentTimeMillis();
		int numTargets = Integer.parseInt(args[0]);
		int numResources = Integer.parseInt(args[1]);
		int depth = Integer.parseInt(args[2]);
		double lambda = Double.parseDouble(args[3]);
		
		SecurityGame sg = new SecurityGame(numTargets, numResources);
		sg.initializeRandom(seed);
		
		List<Double> alpha = new ArrayList<Double>();
		
		for(int i = 0; i < CombinationGenerator.getNChooseR(sg.numResources, sg.numResources).intValue(); i++){
			alpha.add(0.0);
		}
		
    	OPTSAttackerProblem attProblem = new OPTSAttackerProblem(sg, alpha, depth, lambda);
    	
    	attProblem.computeOptimalObservationTree(0);
    	
    	double attRunTime = (System.currentTimeMillis() - seed) / 1000.0;
    	
    	int maxAttackDepth = attProblem.maxAttackDepth(OPTSAttackerProblem.MAX);
		int minAttackDepth = attProblem.maxAttackDepth(OPTSAttackerProblem.MIN);
		
		int maxAttackNodes = attProblem.attackedTargetMaxMap.size();
		int minAttackNodes = attProblem.attackedTargetMinMap.size();
    	
    	OPTSDefenderProblem defProblem = new OPTSDefenderProblem(attProblem);
    	
    	defProblem.Solve();
    	
    	System.out.print(seed + " " + sg.numTargets + " " + sg.numResources + " " + attProblem.numPureStrategies + " " + depth + " ");
		System.out.print(lambda + " " + attProblem.finalMaxValue + " " + attProblem.finalMinValue + " ");
		System.out.print(maxAttackDepth + " " + minAttackDepth + " " + maxAttackNodes + " " + minAttackNodes + " ");
    	System.out.println(defProblem.defenderPayoff + " " + attRunTime + " " + defProblem.defRunTime);
    	
    	//initializePayoffs(sg);

        GenerateGameFiles.InitStrategiesObservations(numTargets, depth, numResources);
        GenerateGameFiles.GeneratePrior(0, 0);
        P1.GenerateAttackerStrategy();
        
        Dobss.Solve();
        
        System.out.println(Dobss.defPayoff + " " + Dobss.runTime + " ");
        
        P2Nonconvex.Solve();
        
        System.out.println(P2Nonconvex.objective + " " + P2Nonconvex.time);
    }
    */
    
	public void ReadOutput() {
        String timeFile = "timeOPTS.txt";
        String objectiveFile = "objectiveOPTS.txt";
        String strategyFile = "defender_stratOPTS.txt";

        String line = null;

        try {
            BufferedReader timeReader = new BufferedReader(new FileReader(timeFile));
            BufferedReader objectiveReader = new BufferedReader(new FileReader(objectiveFile));
            BufferedReader strategyReader = new BufferedReader(new FileReader(strategyFile));

            defStrategy = new ArrayList<Double>();

            while ((line = strategyReader.readLine()) != null) {
                double cov = Double.parseDouble(line);
                defStrategy.add(cov);
            }

            attackSetSize = Functions.attackSetSize(defStrategy);
            entropy = Functions.coverageEntropy(defStrategy);

            if ((line = timeReader.readLine()) != null) {
                defRunTime = Double.parseDouble(line);
            }
            else {
                System.out.println("Error reading file");
            }

            if((line = objectiveReader.readLine()) != null){
                defenderPayoff = Double.parseDouble(line);
            }
            else{
                System.out.println("Error reading file");
            }

            if (Math.abs(defenderPayoff) > 300) {
                System.out.println("objective value too large.");
            }
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
	
	public void GenerateOPTSModelFile(String modelFilename) {
        try {
            PrintWriter out = new PrintWriter(new File(modelFilename));
            out.println("param nTargets;");
            out.println("param observationSpace;");
            out.println("param nDefStrategies;");
            out.println("set targets := {1..nTargets};");
            out.println("set observations := {1..observationSpace};");
            out.println("set defstrategies := {1..nDefStrategies};");
            out.println("param Ospace {observations, defstrategies};");
            out.println("param preProb {observations};");
            out.println("param A {defstrategies, targets};");
            out.println("param defenderCoveredPayoff {targets};");
            out.println("param defenderUncoveredPayoff {targets};");
            out.println("param attackerUncoveredPayoff {targets};");
            out.println("param attackerCoveredPayoff {targets};");
            out.println(" ");
            out.println("var x {defstrategies} >= 0, <= 1;");
            out.println("var marginal {targets};");
            out.println("var d{observations};");
            out.println(" ");

            //TODO: Figure out objective function
            out.print("maximize objective: sum{o in observations}(preProb[o]*(");
            out.print("x[1]^Ospace[o,1]");
            
            for (int i = 2; i <= attackerProblem.pureStrategies.size(); i++) {
                out.print(" * x[" + i + "]^Ospace[o," + i + "]");
            }

            out.print(" ) * d[o]);");
            
            out.println(" ");
            out.println("subject to");
            out.println(" ");
            out.println("coverage: sum {i in defstrategies} x[i] = 1;");
            out.println("computeMarginals{t in targets}: marginal[t] = sum {i in defstrategies} x[i] * A[i,t];");
            
            for(int i = 0; i < observationVectorList.size(); i++){
                int target = attackMap.get(observationVectorList.get(i)) + 1;
                
                out.println("bound" + (i + 1) + ": d[" + (i + 1) + "] = marginal[" + target
                        + "] * (defenderCoveredPayoff[" + target
                        + "] - defenderUncoveredPayoff[" + target
                        + "]) + defenderUncoveredPayoff[" + target + "];");
            }

            out.flush();
            out.close();
        }
        catch (IOException e) {
            throw new RuntimeException("Error writing OPTS model to file: " + e.getMessage());
        }
    }
	
	public void GenerateOPTSDataFile(String dataFilename) {
		try {
            PrintWriter out = new PrintWriter(new File(dataFilename));
            out.println("data;");
            out.println("param nTargets := " + attackerProblem.sg.numTargets + ";");
            out.println("param observationSpace := " + observationVectorList.size() + ";");
            out.println("param nDefStrategies := " + attackerProblem.pureStrategies.size() + ";");

            out.print("param Ospace : ");
            for (int i = 1; i <= attackerProblem.pureStrategies.size(); i++) {
                out.print(i + " ");
            }
            out.print(":= \n");

            for(int i = 1; i <= observationVectorList.size(); i++){
                out.print(i + " ");
                
                List<Integer> observationVector = getObservationVector(observationVectorList.get(i - 1));
                
                for(int j : observationVector){
                    out.print(j + " ");
                }
                out.print("\n");
            }
            out.println(";");


            out.println("param preProb");

            for(int i = 1; i <= observationVectorList.size(); i++){
                out.println(i + "  " + probList.get(i - 1));
            }
            out.println(";");


            out.print("param A : ");
            for (int i = 1; i <= attackerProblem.sg.numTargets; i++) {
                out.print(i + " ");
            }
            out.print(":= \n");

            for (int i = 1; i <= attackerProblem.pureStrategies.size(); i++) {
                out.print(i + " ");

                List<Integer> strategy = getCoverageVector(attackerProblem.pureStrategies.get(i - 1));
                
                for (int j = 0; j < attackerProblem.sg.numTargets; j++) {
                    out.print(strategy.get(j) + " ");
                }
                out.print("\n");
            }
            out.println(";");

            List<Target> targets = new ArrayList<Target>(attackerProblem.sg.setTargets);
            
            Collections.sort(targets);

            out.println("param defenderCoveredPayoff");
            for(int i = 1; i <= attackerProblem.sg.numTargets; i++){
                out.println(i + "  " + targets.get(i - 1).defCovPayoff);
            }
            out.println(";");

            out.println("param defenderUncoveredPayoff");
            for(int i = 1; i <= attackerProblem.sg.numTargets; i++){
                out.println(i + "  " + targets.get(i - 1).defUncovPayoff);
            }
            out.println(";");

            out.println("param attackerCoveredPayoff");
            for(int i = 1; i <= attackerProblem.sg.numTargets; i++){
                out.println(i + "  " + targets.get(i - 1).attCovPayoff);
            }
            out.println(";");

            out.println("param attackerUncoveredPayoff");
            for(int i = 1; i <= attackerProblem.sg.numTargets; i++){
                out.println(i + "  " + targets.get(i - 1).attUncovPayoff);
            }
            out.println(";");

            out.println("end;");

            out.flush();
            out.close();
        }
        catch (IOException e) {
            throw new RuntimeException("Error writing security game to file: " + e.getMessage());
        }
    }
	
	private static long factorial(long b) {
        if (b <= 1) {
            return 1;
        } else {
            return b * factorial(b - 1);
        }
    }
	
	public void calculateProbabilityVector(){
		probList = new ArrayList<Long>();
		
		for(Map<Integer, Integer> observationMap : observationVectorList){
			long numerator = 0;
			long denominator = 1;
		
			for(Integer pureStrategy : observationMap.keySet()){
				numerator += observationMap.get(pureStrategy);
				denominator *= factorial(observationMap.get(pureStrategy));
			}
			
			probList.add(factorial(numerator) / denominator);
		}
	}
	
	public List<Integer> getObservationVector(Map<Integer, Integer> observationMap){
		List<Integer> observationVector = new ArrayList<Integer>(attackerProblem.numPureStrategies);
		
		for(int i = 0; i < attackerProblem.numPureStrategies; i++){
			if(observationMap.containsKey(i)){
				observationVector.add(observationMap.get(i));
			}
			else{
				observationVector.add(0);
			}
		}
		
		return observationVector;
	}
	
	public List<Integer> getCoverageVector(Set<Integer> coverageSet){
		List<Integer> coverageVector = new ArrayList<Integer>(attackerProblem.sg.numTargets);
		
		for(int i = 0; i < attackerProblem.sg.numTargets; i++){
			if(coverageSet.contains(i)){
				coverageVector.add(1);
			}
			else{
				coverageVector.add(0);
			}
		}
		
		return coverageVector;
	}
	
	public void GenerateFilesOPTS() {
        String dataFilename = "OPTS_nonconvex.dat";
        String modelFilename = "OPTS_nonconvex.mod";

        GenerateOPTSModelFile(modelFilename);
        GenerateOPTSDataFile(dataFilename);
    }
	
	public void Solve() {
		calculateProbabilityVector();
        GenerateFilesOPTS();

        String scriptFilename = "OPTS_nonconvex.run";
        RunSolver.run(scriptFilename);
        if (RunSolver.isInfeasible) {
            System.out.println("Infeasible in OPTS");
        }
        
        ReadOutput();
    }
}
