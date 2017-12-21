/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package securitygameslimitsurveil;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author teamcore
 */
public class P2NonconvexBinary {
    public static double objective;
    public static double time;
    public static ArrayList<Double> defStrategy;
    public static int attackSetSize;
    public static double entropy;


    public static void ReadOutput() {
        String timeFile = "timeP2_binary.txt";
        String objectiveFile = "objectiveP2_binary.txt";
        String strategyFile = "defender_stratP2_binary.txt";

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


        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    public static void Solve() {
        GenerateGameFiles.GenerateFilesP2Binary();

        String scriptFilename = "P2_nonconvex_binary.run";
        RunSolver.run(scriptFilename);
        if (RunSolver.isInfeasible) {
            System.out.println("Infeasible in P2");
        }
        ReadOutput();

    }

    public static void Solve(String scriptFilename) {
        GenerateGameFiles.GenerateFilesP2Binary();

        RunSolver.run(scriptFilename);
        ReadOutput();

    }

}
