/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package securitygameslimitsurveil;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

/**
 *
 * @author teamcore
 */
public class P2Nonconvex {

    public static double objective;
    public static double time;
    public static ArrayList<Double> defStrategy;
    public static int attackSetSize;
    public static double entropy;

    //public static ArrayList<Double> marginals;
    public static void ReadOutputConj3() {
        String timeFile = "timeP2.txt";
        String objectiveFile = "objectiveP2.txt";
        String strategyFile = "defender_stratP2.txt";
        //String marginalsFile = "marginalsP2.txt";

        String line = null;

        try {
            BufferedReader timeReader = new BufferedReader(new FileReader(timeFile));
            BufferedReader objectiveReader = new BufferedReader(new FileReader(objectiveFile));
            BufferedReader strategyReader = new BufferedReader(new FileReader(strategyFile));

            int count = 1;
            double tolerance = 0.0000001;
            HashSet<Integer> strategySet = new HashSet<Integer>();
            defStrategy = new ArrayList<Double>();
            //marginals = new ArrayList<Double>();

            while ((line = strategyReader.readLine()) != null) {

                double cov = Double.parseDouble(line);

                defStrategy.add(cov);
                if (cov > tolerance) {
                    strategySet.add(count);
                }
                count++;
            }

            //System.out.println("def strategy: " + strategySet);

            HashSet<Integer> attackSet = new HashSet<Integer>(P1.attackList);
            //System.out.println("att strategy: " + attackSet);

            if (!(strategySet.containsAll(attackSet))) {
                System.out.println("def strategy: " + strategySet);
                System.out.println("att strategy: " + attackSet);
                System.out.println("Error condition for Conjecture 3!!");
                //System.exit(1);
            }

            if ((line = timeReader.readLine()) != null) {
                time = Double.parseDouble(line);
            } else {
                System.out.println("Error reading file");
            }

            if ((line = objectiveReader.readLine()) != null) {
                objective = Double.parseDouble(line);
            } else {
                System.out.println("Error reading file");
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void ReadOutput() {
        String timeFile = "timeP2.txt";
        String objectiveFile = "objectiveP2.txt";
        String strategyFile = "defender_stratP2.txt";

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
                time = Double.parseDouble(line);
            } else {
                System.out.println("Error reading file");
            }

            if ((line = objectiveReader.readLine()) != null) {
                objective = Double.parseDouble(line);
            } else {
                System.out.println("Error reading file");
            }


            if (Math.abs(objective) > 300) {
                System.out.println("objective value too large.");
            }
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void TestConj3() {
        GenerateGameFiles.GenerateFilesP2();

        String scriptFilename = "P2_nonconvex_conj3.run";
        RunSolver.run(scriptFilename);
        if (RunSolver.isInfeasible) {
            System.out.println("Infeasible in P2");
        }
        
        ReadOutputConj3();
    }

    public static void Solve() {
        GenerateGameFiles.GenerateFilesP2();

        String scriptFilename = "P2_nonconvex.run";
        RunSolver.run(scriptFilename);
        if (RunSolver.isInfeasible) {
            System.out.println("Infeasible in P2");
        }
        
        ReadOutput();
    }


    public static void SolveSpeedup(String scriptFilename) {
        GenerateGameFiles.GenerateFilesP2Speedup();

        RunSolver.run(scriptFilename);
        ReadOutput();
    }


    public static void Solve(String scriptFilename) {
        GenerateGameFiles.GenerateFilesP2();

        RunSolver.run(scriptFilename);
        ReadOutput();
    }
}
