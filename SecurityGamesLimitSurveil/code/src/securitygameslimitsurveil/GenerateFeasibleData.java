/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package securitygameslimitsurveil;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author teamcore
 */
public class GenerateFeasibleData {

    
    public static ArrayList<Double> defCov;
    public static ArrayList<Double> defUncov;
    public static ArrayList<Double> attCov;
    public static ArrayList<Double> attUncov;

    protected static void GeneratePayoffs(int nTargets) {
        int payoffMagnitude = 100;

        Random generator;
        generator = new Random();

        defCov = new ArrayList<Double>();
        defUncov = new ArrayList<Double>();
        attCov = new ArrayList<Double>();
        attUncov = new ArrayList<Double>();

        for (int i = 1; i <= nTargets; i++) {
            defCov.add(generator.nextInt(payoffMagnitude) + 1.0);
            defUncov.add(-generator.nextInt(payoffMagnitude) - 1.0);
        }

        // Zero sum game
        for (int i = 0; i < nTargets; i++) {
            attCov.add(-defCov.get(i));
            attUncov.add(-defUncov.get(i));
        }
    }

    protected static void SetPayoffs(int nTargets) {

        ArrayList<Double> tempDefCov = new ArrayList<Double>();
        ArrayList<Double> tempDefUncov = new ArrayList<Double>();
        ArrayList<Double> tempAttCov = new ArrayList<Double>();
        ArrayList<Double> tempAttUncov = new ArrayList<Double>();
        
        for (int i = 0; i < nTargets; i++) {
            tempDefCov.add(defCov.get(i));
            tempDefUncov.add(defUncov.get(i));
            tempAttCov.add(attCov.get(i));
            tempAttUncov.add(attUncov.get(i));
        }
        GenerateGameFiles.defCov = tempDefCov;
        GenerateGameFiles.defUncov = tempDefUncov;
        GenerateGameFiles.attCov = tempAttCov;
        GenerateGameFiles.attUncov = tempAttUncov;

    }

    public static void generateFeasibleData() {
        int numTargets = 3;
        int numObservations = 3;
        int numResources = 1;
        int numFiles = 1;

        int maxTargets = 10;
        int maxObservations = 10;

        for (int j = 0; j < numFiles; j++) {

            GeneratePayoffs(maxTargets);

            for (int i = 2; i <= maxTargets; i++) {
                System.out.println(i);

                SetPayoffs(i);
                P1.GenerateAttackerStrategy();
                P2Nonconvex.Solve();
                P3Convex.Solve();

                while (P3Convex.isInfeasible) {
                    P1.GenerateAttackerStrategy();
                    P2Nonconvex.Solve();
                    P3Convex.Solve();
                }

                if ((Math.abs(P2Nonconvex.objective) > 100.0)
                        || (Math.abs(P3Convex.objective) > 100.0)) {
                    System.out.println("objective value too large. P2: " + P2Nonconvex.objective
                            + " or P3: " + P3Convex.objective);
                    System.exit(1);
                }
            }

        }

    }

}
