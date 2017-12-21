/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package securitygameslimitsurveil;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 *
 * @author teamcore
 */
public class Dobss {

	public static double defPayoff;
    public static double runTime;
//    public static boolean isInfeasible;
    public static ArrayList<Double> defStrategy;
    public static int attTarget;
    public static int attackSetSize;
    public static double entropy;

    public static double GetUpdatedDefUtil() {
        return ObjectiveValue(defStrategy);
    }

    public static double ObjectiveValue(ArrayList<Double> x) {
        double[] c = new double[GenerateGameFiles.nTargets];
        double[] d = new double[Observations.globalList.size()];
        ArrayList<Long> preProb = GenerateGameFiles.preProbList;

        // Compute c_j (or the marginal)
        for (int j = 0; j < GenerateGameFiles.nTargets; j++) {

            double coverage = 0.0;
            for (int i = 0; i < PureStrategies.globalList.size(); i++) {
                int[] strategy = PureStrategies.globalList.get(i);
                coverage += strategy[j]*x.get(i);
            }
            c[j] = coverage;
        }

        // Compute d^o
        for (int i = 0; i < Observations.globalList.size(); i++) {
            int j = P1.attackList.get(i) - 1;
            d[i] = c[j]*(GenerateGameFiles.defCov.get(j) - GenerateGameFiles.defUncov.get(j)) +
                    GenerateGameFiles.defUncov.get(j);
        }

        // Compute objective value
        double objective = 0.0;
        for (int i = 0; i < Observations.globalList.size(); i++) {

            double prod = 1.0;
            for (int j = 0; j < PureStrategies.globalList.size(); j++) {
                prod = prod * Math.pow(x.get(j), Observations.globalList.get(i).get(j));
            }
            prod = prod * preProb.get(i) * d[i];

            objective += prod;
        }

        return objective;

    }


    public static void ReadOutput() {
    	String timeFile = "timeDOBSS.txt";
        String objectiveFile = "objective_dobss.txt";
        String defStrategyFile = "defender_strat_dobss.txt";
        String attStrategyFile = "attacker_strat_dobss.txt";

        String line = null;

        try {
        	BufferedReader timeReader = new BufferedReader(new FileReader(timeFile));
            //BufferedReader objectiveReader = new BufferedReader(new FileReader(objectiveFile));
            BufferedReader defStrategyReader = new BufferedReader(new FileReader(defStrategyFile));
            BufferedReader attStrategyReader = new BufferedReader(new FileReader(attStrategyFile));

            // Retrieve which target will be attacked by the attacker
            int count = 1;
            while ((line = attStrategyReader.readLine()) != null) {
                System.out.println(attStrategyReader.readLine());
                int value = Integer.parseInt(line);
                if (value == 1) {
                    attTarget = count;
                    break;
                }
                count++;
            }

            defStrategy = new ArrayList<Double>();

            while ((line = defStrategyReader.readLine()) != null) {
                StringTokenizer st = new StringTokenizer(line);
                count = 1;
                while (st.hasMoreTokens()) {
                    //String s = st.nextToken();
                    double d = Double.parseDouble(st.nextToken());
                    if (count == attTarget) {
                        defStrategy.add(d);
                        break;
                    }
                    count++;
                }
            }

            attackSetSize = Functions.attackSetSize(defStrategy);
            entropy = Functions.coverageEntropy(defStrategy);

            if ((line = timeReader.readLine()) != null) {
            	runTime = Double.parseDouble(line);
            } else {
            	System.out.println("Error reading file");
            }
            
            /*
            if ((line = objectiveReader.readLine()) != null) {
                defPayoff = Double.parseDouble(line);
            } else {
                System.out.println("Error reading file");
            }
            */

            defPayoff = ObjectiveValue(defStrategy);
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void Solve() {
        String dobssFilename = "dobss.dat";
        GenerateGameFiles.GenerateDobssDataFile(dobssFilename);
        
        String scriptFilename = "dobss.run";
        RunSolver.run(scriptFilename);
        ReadOutput();
    }
}
