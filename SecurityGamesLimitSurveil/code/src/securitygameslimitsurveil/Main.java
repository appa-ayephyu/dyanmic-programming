/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package securitygameslimitsurveil;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Random;

/**
 *
 * @author teamcore
 */
public class Main {

    public static void runSimple() {
    	//int numTargets = 6;
    	//int numObservations = 25;
    	//int numResources = 1;
        int numTargets = 5;
        int numObservations = 5;
        int numResources = 2;
        double maxAlpha = 10.0;
        int alphaOption = 0;
        double discountFactor = 0.95;


//
//        GenerateGameFiles.GeneratePositiveNegativePayoffs(numTargets);

        GenerateGameFiles.GenerateHardcodedPayoffs(numTargets);
//        GenerateGameFiles.GenerateSampleHardcodedPayoffs(numTargets);
//        BinarySearch.Solve(2, numTargets, numResources, discountFactor,
//                maxAlpha, alphaOption, "P2_nonconvex_10ms.run");


//
//                    alphaOption = 0;
//                    GenerateGameFiles.InitStrategiesObservations(numTargets,
//                            observations[j], numResources);
//                    GenerateGameFiles.GeneratePrior(maxAlpha, alphaOption);
//                    P1.GenerateAttackerStrategy();
//
//                    P2Nonconvex.Solve("P2_nonconvex_1ms.run");
//                    sumDefUtil[0][j] += P2Nonconvex.objective;
//                    sumAttackSetSize[0][j] += P2Nonconvex.attackSetSize;
//                    sumEntropy[0][j] += P2Nonconvex.entropy;
//
//
//

//        for (int i = 0; i <= 40; i++) {
        GenerateGameFiles.InitStrategiesObservations(numTargets, numObservations, numResources);
        GenerateGameFiles.GeneratePrior(maxAlpha, alphaOption);
        P1.GenerateAttackerStrategy();

        P2Nonconvex.Solve("P2_nonconvex_10ms.run");

        System.out.println(P2Nonconvex.defStrategy);

//            P2NonconvexBinary.Solve();
//            AttackerUtility.Solve(discountFactor, P2NonconvexBinary.defStrategy);
//            System.out.println(i);
//            System.out.println("Attacker utility: " + AttackerUtility.finalUtility);
//            P2Nonconvex.Solve("P2_nonconvex_10ms.run");
//            System.out.println("Defender utility: " + P2Nonconvex.objective + ", coverage" + P2Nonconvex.defStrategy);
//
//        }
//
//        Dobss.Solve();
//        System.out.println("Coverage: " + Dobss.defStrategy);
//



//        AttackerUtility.Solve(discountFactor, Dobss.defStrategy);

//        GenerateGameFiles.InitStrategiesObservations(numTargets, numObservations+1, numResources);
//        GenerateGameFiles.GeneratePrior(maxAlpha, alphaOption);
//        P1.GenerateAttackerStrategy();
//        P2NonconvexBinary.Solve();
//        AttackerUtility.Solve(discountFactor, P2NonconvexBinary.defStrategy);
//        System.out.println("Attacker utility: " + AttackerUtility.finalUtility);
//        P2Nonconvex.Solve("P2_nonconvex_10ms.run");
//        System.out.println("Defender utility: " + P2Nonconvex.objective);



//        BinarySearch.Solve(2, numTargets, numResources, discountFactor,
//                maxAlpha, alphaOption, "P2_nonconvex_1ms.run");


//        GenerateGameFiles.GeneratePayoffsProp3(numTargets);
////        GenerateGameFiles.GeneratePayoffs(numTargets);
////        GenerateGameFiles.GenerateHardcodedPayoffs(numTargets);
//        GenerateGameFiles.InitStrategiesObservations(numTargets, numObservations, numResources);
//        GenerateGameFiles.GeneratePrior(maxAlpha, alphaOption);
//        P1.GenerateAttackerStrategy();
//        P2Nonconvex.Solve();
//        //GenerateGameFiles.GenerateDobssDataFile("dobss.dat");

//        P3Convex.Solve();
//        P4.Solve();

//        ArrayList<Double> tempX = new ArrayList<Double>();
//        tempX.add(0.305627);
//        tempX.add(0.227878);
//        tempX.add(0.466495);
//
//        System.out.println("objective: " + P4.ObjectiveValue(tempX));

    }

    public static void testDobss() {
        int numTargets = 3;
        int numObservations = 5;
        int numResources = 1;
        double maxAlpha = 0.1;


//        GenerateGameFiles.GeneratePayoffs(numTargets);
        GenerateGameFiles.GenerateHardcodedPayoffs(numTargets);

        GenerateGameFiles.InitStrategiesObservations(numTargets, numObservations, numResources);
        GenerateGameFiles.GeneratePrior(maxAlpha, 0);
        P1.GenerateAttackerStrategy();
//        P2Nonconvex.Solve();
        Dobss.Solve();

    }

    public static void testPerformanceVaryTargets() {
        int numResources = 1;
        double discountFactor = 1.0;
        int formulation = 2;
        double maxAlpha = 10.0;
        int alphaOption = 0;
//        GenerateGameFiles.alpha = 0.0;

        String outputFile = "outputFilePerfVaryTargets.csv";
        String rawDefUtil = "rawDefUtilPerfVaryTargets.csv";
        String rawAttUtil = "rawAttUtilPerfVaryTargets.csv";
        String rawAttackSetSize = "rawAttackSetSizePerfVaryTargets.csv";
        String rawEntropy = "rawEntropyPerfVaryTargets.csv";

        int numIterations = 100;


        int numLines = 5;
        int maxTargets = 6;
        int payoffMagnitude = 100;


//        int numTargets = 5;
//        int numObservations = 5;
//        int numResources = 1;

        double[] sumDefUtil;
//        double[] sumAttUtil;
        int[] sumAttackSetSize;
        double[] sumEntropy;
        int[] sumOptimalObservations;

        sumDefUtil = new double[numLines];
//        sumAttUtil = new double[3];
        sumAttackSetSize = new int[numLines];
        sumEntropy = new double[numLines];
        sumOptimalObservations = new int[numLines];

        try {
            PrintWriter out = new PrintWriter(new File(outputFile));
            PrintWriter outDefUtil = new PrintWriter(new File(rawDefUtil));
            PrintWriter outAttUtil = new PrintWriter(new File(rawAttUtil));
            PrintWriter outAttackSetSize = new PrintWriter(new File(rawAttackSetSize));
            PrintWriter outEntropy = new PrintWriter(new File(rawEntropy));


//            P1.GenerateAttackerStrategy(numTargets, numObservations, numResources);
//            P2Nonconvex.Solve();
//            P3Convex.Solve();

            Random generator;
            generator = new Random();

            ArrayList<ArrayList<Double>> defCovList = new ArrayList<ArrayList<Double>>();
            ArrayList<ArrayList<Double>> defUncovList = new ArrayList<ArrayList<Double>>();
            ArrayList<ArrayList<Double>> attCovList = new ArrayList<ArrayList<Double>>();
            ArrayList<ArrayList<Double>> attUncovList = new ArrayList<ArrayList<Double>>();

            for (int x = 0; x < numIterations; x++) {
                ArrayList<Double> defCov = new ArrayList<Double>();
                ArrayList<Double> defUncov = new ArrayList<Double>();
                ArrayList<Double> attCov = new ArrayList<Double>();
                ArrayList<Double> attUncov = new ArrayList<Double>();

                for (int i = 1; i <= maxTargets; i++) {

                    defCov.add(generator.nextInt(payoffMagnitude) + 1.0);
                    defUncov.add(-generator.nextInt(payoffMagnitude) - 1.0);
                    attCov.add(-generator.nextInt(payoffMagnitude) - 1.0);
                    attUncov.add(generator.nextInt(payoffMagnitude) + 1.0);

//                    defCov.add(generator.nextInt(payoffMagnitude) + 101.0);
//                    defUncov.add((double) generator.nextInt(payoffMagnitude));
//
//                    attCov.add((double) generator.nextInt(payoffMagnitude));
//                    attUncov.add(generator.nextInt(payoffMagnitude) + 101.0);
                }
                defCovList.add(defCov);
                defUncovList.add(defUncov);
                attCovList.add(attCov);
                attUncovList.add(attUncov);
            }




            System.out.println("Varying number of targets");
            out.write("Vary num Targets\n");


//            out.write("Number of Targets,Defender Utility(P2: 1ms),Defender Utility(P2: 5ms),"
//                    + "Defender Utility(P2: 5ms),Defender Utility(P3),Defender Utility(SSE),"
//                    + ",,P2(1ms),P2(5ms),P2(10ms),P3,SSE,"
//                    + ",,P2(1ms),P2(5ms),P2(10ms),P3,SSE,"
//                    + ",,P2(1ms),P2(5ms),P2(10ms),P3\n");

            out.write("Number of Targets,P1(Uniform),SSE(Uniform),P1(SSE),SSE(SSE),"
                    + ",,P1(Uniform),SSE(Uniform),P1(SSE),SSE(SSE),"
                    + ",,P1(Uniform),SSE(Uniform),P1(SSE),SSE(SSE),"
                    + ",,P1(Uniform),P1(SSE)\n");


            // 18 targets w/ 5 observations is 23 seconds
            for (int i = 3; i <= maxTargets; i++) {
//            for (int i = 8; i <= 15; i++) {
                System.out.println(i);

                for (int x = 0; x < numLines; x++) {
                    sumDefUtil[x] = 0.0;
//                    sumAttUtil[x] = 0.0;
                    sumAttackSetSize[x] = 0;
                    sumEntropy[x] = 0.0;
                    sumOptimalObservations[x] = 0;
                }

                for (int j = 0; j < numIterations; j++) {
//                    GenerateGameFiles.GeneratePayoffs(i);
                    GenerateGameFiles.SetPayoffsVaryTargets(i, defCovList.get(j),
                            defUncovList.get(j), attCovList.get(j), attUncovList.get(j));

                    alphaOption = 0;
                    BinarySearch.Solve(2, i, numResources, discountFactor,
                            maxAlpha, alphaOption, "P2_nonconvex_1ms.run");
                    sumDefUtil[0] += BinarySearch.optimalDefenderUtil;
                    sumAttackSetSize[0] += P2Nonconvex.attackSetSize;
                    sumEntropy[0] += P2Nonconvex.entropy;
                    sumOptimalObservations[0] += BinarySearch.optimalObservations;

                    Dobss.Solve();
                    AttackerUtility.Solve(discountFactor, Dobss.defStrategy);
                    sumDefUtil[1] += Dobss.GetUpdatedDefUtil();
                    sumAttackSetSize[1] += Dobss.attackSetSize;
                    sumEntropy[1] += Dobss.entropy;

                    outDefUtil.write(BinarySearch.optimalDefenderUtil + "," + Dobss.GetUpdatedDefUtil() + ",");
//                    outAttUtil.write(BinarySearch.optimalAttackerUtil + "," + AttackerUtility.finalUtility + ",");
                    outAttackSetSize.write(P2Nonconvex.attackSetSize + "," + Dobss.attackSetSize + ",");
                    outEntropy.write(P2Nonconvex.entropy + "," + Dobss.entropy + ",");


                    alphaOption = 1;
                    BinarySearch.Solve(2, i, numResources, discountFactor,
                            maxAlpha, alphaOption, "P2_nonconvex_1ms.run");
                    sumDefUtil[2] += BinarySearch.optimalDefenderUtil;
                    sumAttackSetSize[2] += P2Nonconvex.attackSetSize;
                    sumEntropy[2] += P2Nonconvex.entropy;
                    sumOptimalObservations[2] += BinarySearch.optimalObservations;

                    Dobss.Solve();
                    AttackerUtility.Solve(discountFactor, Dobss.defStrategy);
                    sumDefUtil[3] += Dobss.GetUpdatedDefUtil();
                    sumAttackSetSize[3] += Dobss.attackSetSize;
                    sumEntropy[3] += Dobss.entropy;




//                    System.out.println(prior[j] + ", " + BinarySearch.optimalDefenderUtil + ", " + P1.attackList);

//                    BinarySearch.Solve(2, i, numResources, discountFactor,
//                            maxAlpha, alphaOption, "P2_nonconvex_5ms.run");
//                    sumDefUtil[1] += BinarySearch.optimalDefenderUtil;
//                    sumAttackSetSize[1] += P2Nonconvex.attackSetSize;
//                    sumEntropy[1] += P2Nonconvex.entropy;
//                    sumOptimalObservations[1] += BinarySearch.optimalObservations;



//                    Dobss.Solve();
//                    AttackerUtility.Solve(discountFactor, Dobss.defStrategy);
//                    sumDefUtil[4] += Dobss.GetUpdatedDefUtil();
////                    sumAttUtil[2][j] += AttackerUtility.finalUtility;
//                    sumAttackSetSize[4] += Dobss.attackSetSize;
//                    sumEntropy[4] += Dobss.entropy;



//                    BinarySearch.Solve(2, i, numResources, discountFactor,
//                            maxAlpha, alphaOption, "P2_nonconvex_10ms.run");
//                    sumDefUtil[2] += BinarySearch.optimalDefenderUtil;
//                    sumAttackSetSize[2] += P2Nonconvex.attackSetSize;
//                    sumEntropy[2] += P2Nonconvex.entropy;
//                    sumOptimalObservations[2] += BinarySearch.optimalObservations;
//

                    outDefUtil.write(BinarySearch.optimalDefenderUtil + "," + Dobss.GetUpdatedDefUtil() + "\n");
//                    outAttUtil.write(BinarySearch.optimalAttackerUtil + "," + AttackerUtility.finalUtility + ",");
                    outAttackSetSize.write(P2Nonconvex.attackSetSize + "," + Dobss.attackSetSize + "\n");
                    outEntropy.write(P2Nonconvex.entropy + "," + Dobss.entropy + "\n");

//                    BinarySearch.Solve(3, i, numResources, discountFactor,
//                            maxAlpha, alphaOption, "");
//                    sumDefUtil[3] += BinarySearch.optimalDefenderUtil;
////                    sumAttUtil[1][j] += BinarySearch.optimalAttackerUtil;
//                    sumAttackSetSize[3] += P3Convex.attackSetSize;
//                    sumEntropy[3] += P3Convex.entropy;
//                    sumOptimalObservations[3] += BinarySearch.optimalObservations;

//                    outDefUtil.write(BinarySearch.optimalDefenderUtil + "\n");
//                    outAttUtil.write(BinarySearch.optimalAttackerUtil + "\n");
//                    outAttackSetSize.write(P3Convex.attackSetSize + "\n");
//                    outEntropy.write(P3Convex.entropy + "\n");


                    outDefUtil.flush();
                    outAttUtil.flush();
                    outAttackSetSize.flush();
                    outEntropy.flush();

                }

                outDefUtil.write("\n");
                outAttUtil.write("\n");
                outAttackSetSize.write("\n");
                outEntropy.write("\n");

                out.write(i + "," + (sumDefUtil[0] / numIterations) + ","
                        + (sumDefUtil[1] / numIterations) + ","
                        + (sumDefUtil[2] / numIterations) + ","
                        + (sumDefUtil[3] / numIterations) + ", ,"
                        + i + ","
                        + ((double) sumAttackSetSize[0] / numIterations) + ","
                        + ((double) sumAttackSetSize[1] / numIterations) + ","
                        + ((double) sumAttackSetSize[2] / numIterations) + ","
                        + ((double) sumAttackSetSize[3] / numIterations) + ", ,"
                        + i + ","
                        + (sumEntropy[0] / numIterations) + ","
                        + (sumEntropy[1] / numIterations) + ","
                        + (sumEntropy[2] / numIterations) + ","
                        + (sumEntropy[3] / numIterations) + ", ,"
                        + i + ","
                        + ((double) sumOptimalObservations[0] / numIterations) + ","
                        + ((double) sumOptimalObservations[2] / numIterations) + "\n");

//                out.write(i + "," + (sumDefUtil[0] / numIterations) + ","
//                        + (sumDefUtil[1] / numIterations) + ","
//                        + (sumDefUtil[2] / numIterations) + ","
//                        + (sumDefUtil[3] / numIterations) + ","
//                        + (sumDefUtil[4] / numIterations) + ", ,"
//                        + i + ","
//                        + ((double) sumAttackSetSize[0] / numIterations) + ","
//                        + ((double) sumAttackSetSize[1] / numIterations) + ","
//                        + ((double) sumAttackSetSize[2] / numIterations) + ","
//                        + ((double) sumAttackSetSize[3] / numIterations) + ","
//                        + ((double) sumAttackSetSize[4] / numIterations) + ", ,"
//                        + i + ","
//                        + (sumEntropy[0] / numIterations) + ","
//                        + (sumEntropy[1] / numIterations) + ","
//                        + (sumEntropy[2] / numIterations) + ","
//                        + (sumEntropy[3] / numIterations) + ","
//                        + (sumEntropy[4] / numIterations) + ", ,"
//                        + i + ","
//                        + ((double) sumOptimalObservations[0] / numIterations) + ","
//                        + ((double) sumOptimalObservations[1] / numIterations) + ","
//                        + ((double) sumOptimalObservations[2] / numIterations) + ","
//                        + ((double) sumOptimalObservations[3] / numIterations) + "\n");


                out.flush();
                outDefUtil.flush();
                outAttUtil.flush();
                outAttackSetSize.flush();
                outEntropy.flush();
            }

            outDefUtil.flush();
            outAttUtil.flush();
            outAttackSetSize.flush();
            outEntropy.flush();
            outDefUtil.close();
            outAttUtil.close();
            outAttackSetSize.close();
            outEntropy.close();

            out.flush();
            out.close();
        } catch (IOException e) {
            throw new RuntimeException("Error writing security game to file: " + e.getMessage());
        }
    }

    public static void testPerformanceVaryScheduleConstraints() {
        int numResources = 1;
        double discountFactor = 1.0;
        int formulation = 2;
        double maxAlpha = 0.0;
        int alphaOption = 0;
//        GenerateGameFiles.alpha = 0.0;

        String outputFile = "outputFilePerfVaryTargets.csv";
        String rawDefUtil = "rawDefUtilPerfVaryTargets.csv";
        String rawAttUtil = "rawAttUtilPerfVaryTargets.csv";
        String rawAttackSetSize = "rawAttackSetSizePerfVaryTargets.csv";
        String rawEntropy = "rawEntropyPerfVaryTargets.csv";

        int numIterations = 100;


        int numLines = 5;
        int maxTargets = 4;
        int payoffMagnitude = 100;


//        int numTargets = 5;
//        int numObservations = 5;
//        int numResources = 1;

        double[] sumDefUtil;
//        double[] sumAttUtil;
        int[] sumAttackSetSize;
        double[] sumEntropy;
        int[] sumOptimalObservations;

        sumDefUtil = new double[numLines];
//        sumAttUtil = new double[3];
        sumAttackSetSize = new int[numLines];
        sumEntropy = new double[numLines];
        sumOptimalObservations = new int[numLines];

        try {
            PrintWriter out = new PrintWriter(new File(outputFile));
            PrintWriter outDefUtil = new PrintWriter(new File(rawDefUtil));
            PrintWriter outAttUtil = new PrintWriter(new File(rawAttUtil));
            PrintWriter outAttackSetSize = new PrintWriter(new File(rawAttackSetSize));
            PrintWriter outEntropy = new PrintWriter(new File(rawEntropy));


//            P1.GenerateAttackerStrategy(numTargets, numObservations, numResources);
//            P2Nonconvex.Solve();
//            P3Convex.Solve();

            Random generator;
            generator = new Random();

            ArrayList<ArrayList<Double>> defCovList = new ArrayList<ArrayList<Double>>();
            ArrayList<ArrayList<Double>> defUncovList = new ArrayList<ArrayList<Double>>();
            ArrayList<ArrayList<Double>> attCovList = new ArrayList<ArrayList<Double>>();
            ArrayList<ArrayList<Double>> attUncovList = new ArrayList<ArrayList<Double>>();

            for (int x = 0; x < numIterations; x++) {
                ArrayList<Double> defCov = new ArrayList<Double>();
                ArrayList<Double> defUncov = new ArrayList<Double>();
                ArrayList<Double> attCov = new ArrayList<Double>();
                ArrayList<Double> attUncov = new ArrayList<Double>();

                for (int i = 1; i <= maxTargets; i++) {

                    defCov.add(generator.nextInt(payoffMagnitude) + 1.0);
                    defUncov.add(-generator.nextInt(payoffMagnitude) - 1.0);
                    attCov.add(-generator.nextInt(payoffMagnitude) - 1.0);
                    attUncov.add(generator.nextInt(payoffMagnitude) + 1.0);

//                    defCov.add(generator.nextInt(payoffMagnitude) + 101.0);
//                    defUncov.add((double) generator.nextInt(payoffMagnitude));
//
//                    attCov.add((double) generator.nextInt(payoffMagnitude));
//                    attUncov.add(generator.nextInt(payoffMagnitude) + 101.0);
                }
                defCovList.add(defCov);
                defUncovList.add(defUncov);
                attCovList.add(attCov);
                attUncovList.add(attUncov);
            }




            System.out.println("Varying number of targets");
            out.write("Vary num Targets\n");


//            out.write("Number of Targets,Defender Utility(P2: 1ms),Defender Utility(P2: 5ms),"
//                    + "Defender Utility(P2: 5ms),Defender Utility(P3),Defender Utility(SSE),"
//                    + ",,P2(1ms),P2(5ms),P2(10ms),P3,SSE,"
//                    + ",,P2(1ms),P2(5ms),P2(10ms),P3,SSE,"
//                    + ",,P2(1ms),P2(5ms),P2(10ms),P3\n");

            out.write("Number of Targets,P1(Uniform),SSE(Uniform),P1(SSE),SSE(SSE),"
                    + ",,P1(Uniform),SSE(Uniform),P1(SSE),SSE(SSE),"
                    + ",,P1(Uniform),SSE(Uniform),P1(SSE),SSE(SSE),"
                    + ",,P1(Uniform),P1(SSE)\n");


            // 18 targets w/ 5 observations is 23 seconds
            for (int i = 4; i <= maxTargets; i++) {
//            for (int i = 8; i <= 15; i++) {
                System.out.println(i);

                for (int x = 0; x < numLines; x++) {
                    sumDefUtil[x] = 0.0;
//                    sumAttUtil[x] = 0.0;
                    sumAttackSetSize[x] = 0;
                    sumEntropy[x] = 0.0;
                    sumOptimalObservations[x] = 0;
                }

                for (int j = 0; j < numIterations; j++) {
//                    GenerateGameFiles.GeneratePayoffs(i);
                    GenerateGameFiles.SetPayoffsVaryTargets(i, defCovList.get(j),
                            defUncovList.get(j), attCovList.get(j), attUncovList.get(j));

                    alphaOption = 0;
                    BinarySearch.Solve(2, i, numResources, discountFactor,
                            maxAlpha, alphaOption, "P2_nonconvex_1ms.run");
                    sumDefUtil[0] += BinarySearch.optimalDefenderUtil;
                    sumAttackSetSize[0] += P2Nonconvex.attackSetSize;
                    sumEntropy[0] += P2Nonconvex.entropy;
                    sumOptimalObservations[0] += BinarySearch.optimalObservations;

                    Dobss.Solve();
                    AttackerUtility.Solve(discountFactor, Dobss.defStrategy);
                    sumDefUtil[1] += Dobss.GetUpdatedDefUtil();
                    sumAttackSetSize[1] += Dobss.attackSetSize;
                    sumEntropy[1] += Dobss.entropy;

                    outDefUtil.write(BinarySearch.optimalDefenderUtil + "," + Dobss.GetUpdatedDefUtil() + ",");
//                    outAttUtil.write(BinarySearch.optimalAttackerUtil + "," + AttackerUtility.finalUtility + ",");
                    outAttackSetSize.write(P2Nonconvex.attackSetSize + "," + Dobss.attackSetSize + ",");
                    outEntropy.write(P2Nonconvex.entropy + "," + Dobss.entropy + ",");


                    alphaOption = 1;
                    BinarySearch.Solve(2, i, numResources, discountFactor,
                            maxAlpha, alphaOption, "P2_nonconvex_1ms.run");
                    sumDefUtil[2] += BinarySearch.optimalDefenderUtil;
                    sumAttackSetSize[2] += P2Nonconvex.attackSetSize;
                    sumEntropy[2] += P2Nonconvex.entropy;
                    sumOptimalObservations[2] += BinarySearch.optimalObservations;

                    Dobss.Solve();
                    AttackerUtility.Solve(discountFactor, Dobss.defStrategy);
                    sumDefUtil[3] += Dobss.GetUpdatedDefUtil();
                    sumAttackSetSize[3] += Dobss.attackSetSize;
                    sumEntropy[3] += Dobss.entropy;




//                    System.out.println(prior[j] + ", " + BinarySearch.optimalDefenderUtil + ", " + P1.attackList);

//                    BinarySearch.Solve(2, i, numResources, discountFactor,
//                            maxAlpha, alphaOption, "P2_nonconvex_5ms.run");
//                    sumDefUtil[1] += BinarySearch.optimalDefenderUtil;
//                    sumAttackSetSize[1] += P2Nonconvex.attackSetSize;
//                    sumEntropy[1] += P2Nonconvex.entropy;
//                    sumOptimalObservations[1] += BinarySearch.optimalObservations;



//                    Dobss.Solve();
//                    AttackerUtility.Solve(discountFactor, Dobss.defStrategy);
//                    sumDefUtil[4] += Dobss.GetUpdatedDefUtil();
////                    sumAttUtil[2][j] += AttackerUtility.finalUtility;
//                    sumAttackSetSize[4] += Dobss.attackSetSize;
//                    sumEntropy[4] += Dobss.entropy;



//                    BinarySearch.Solve(2, i, numResources, discountFactor,
//                            maxAlpha, alphaOption, "P2_nonconvex_10ms.run");
//                    sumDefUtil[2] += BinarySearch.optimalDefenderUtil;
//                    sumAttackSetSize[2] += P2Nonconvex.attackSetSize;
//                    sumEntropy[2] += P2Nonconvex.entropy;
//                    sumOptimalObservations[2] += BinarySearch.optimalObservations;
//

                    outDefUtil.write(BinarySearch.optimalDefenderUtil + "," + Dobss.GetUpdatedDefUtil() + "\n");
//                    outAttUtil.write(BinarySearch.optimalAttackerUtil + "," + AttackerUtility.finalUtility + ",");
                    outAttackSetSize.write(P2Nonconvex.attackSetSize + "," + Dobss.attackSetSize + "\n");
                    outEntropy.write(P2Nonconvex.entropy + "," + Dobss.entropy + "\n");

//                    BinarySearch.Solve(3, i, numResources, discountFactor,
//                            maxAlpha, alphaOption, "");
//                    sumDefUtil[3] += BinarySearch.optimalDefenderUtil;
////                    sumAttUtil[1][j] += BinarySearch.optimalAttackerUtil;
//                    sumAttackSetSize[3] += P3Convex.attackSetSize;
//                    sumEntropy[3] += P3Convex.entropy;
//                    sumOptimalObservations[3] += BinarySearch.optimalObservations;

//                    outDefUtil.write(BinarySearch.optimalDefenderUtil + "\n");
//                    outAttUtil.write(BinarySearch.optimalAttackerUtil + "\n");
//                    outAttackSetSize.write(P3Convex.attackSetSize + "\n");
//                    outEntropy.write(P3Convex.entropy + "\n");


                    outDefUtil.flush();
                    outAttUtil.flush();
                    outAttackSetSize.flush();
                    outEntropy.flush();

                }

                outDefUtil.write("\n");
                outAttUtil.write("\n");
                outAttackSetSize.write("\n");
                outEntropy.write("\n");

                out.write(i + "," + (sumDefUtil[0] / numIterations) + ","
                        + (sumDefUtil[1] / numIterations) + ","
                        + (sumDefUtil[2] / numIterations) + ","
                        + (sumDefUtil[3] / numIterations) + ", ,"
                        + i + ","
                        + ((double) sumAttackSetSize[0] / numIterations) + ","
                        + ((double) sumAttackSetSize[1] / numIterations) + ","
                        + ((double) sumAttackSetSize[2] / numIterations) + ","
                        + ((double) sumAttackSetSize[3] / numIterations) + ", ,"
                        + i + ","
                        + (sumEntropy[0] / numIterations) + ","
                        + (sumEntropy[1] / numIterations) + ","
                        + (sumEntropy[2] / numIterations) + ","
                        + (sumEntropy[3] / numIterations) + ", ,"
                        + i + ","
                        + ((double) sumOptimalObservations[0] / numIterations) + ","
                        + ((double) sumOptimalObservations[2] / numIterations) + "\n");

//                out.write(i + "," + (sumDefUtil[0] / numIterations) + ","
//                        + (sumDefUtil[1] / numIterations) + ","
//                        + (sumDefUtil[2] / numIterations) + ","
//                        + (sumDefUtil[3] / numIterations) + ","
//                        + (sumDefUtil[4] / numIterations) + ", ,"
//                        + i + ","
//                        + ((double) sumAttackSetSize[0] / numIterations) + ","
//                        + ((double) sumAttackSetSize[1] / numIterations) + ","
//                        + ((double) sumAttackSetSize[2] / numIterations) + ","
//                        + ((double) sumAttackSetSize[3] / numIterations) + ","
//                        + ((double) sumAttackSetSize[4] / numIterations) + ", ,"
//                        + i + ","
//                        + (sumEntropy[0] / numIterations) + ","
//                        + (sumEntropy[1] / numIterations) + ","
//                        + (sumEntropy[2] / numIterations) + ","
//                        + (sumEntropy[3] / numIterations) + ","
//                        + (sumEntropy[4] / numIterations) + ", ,"
//                        + i + ","
//                        + ((double) sumOptimalObservations[0] / numIterations) + ","
//                        + ((double) sumOptimalObservations[1] / numIterations) + ","
//                        + ((double) sumOptimalObservations[2] / numIterations) + ","
//                        + ((double) sumOptimalObservations[3] / numIterations) + "\n");


                out.flush();
                outDefUtil.flush();
                outAttUtil.flush();
                outAttackSetSize.flush();
                outEntropy.flush();
            }

            outDefUtil.flush();
            outAttUtil.flush();
            outAttackSetSize.flush();
            outEntropy.flush();
            outDefUtil.close();
            outAttUtil.close();
            outAttackSetSize.close();
            outEntropy.close();

            out.flush();
            out.close();
        } catch (IOException e) {
            throw new RuntimeException("Error writing security game to file: " + e.getMessage());
        }
    }

    public static void testRobustnessTable() {

        int numResources = 1;
        double discountFactor = 0.95;
//        int formulation = 2;
        double maxAlpha = 10.0;
        int alphaOption = 0;

        int numIterations = 200;


//        int numLines = 5;
        int numTargets = 5;

        String outputFile = "outputRobustness.csv";
        String rawDefUtil = "rawDefUtilRobustness.csv";
//        String rawAttUtil = "rawAttUtilPerfVaryObservation.csv";
//        String rawAttackSetSize = "rawAttackSetSizePerfVaryObservation.csv";
//        String rawEntropy = "rawEntropyPerfVaryObservation.csv";


        int numLines = 5;
        int xDataPoints = 10;


        double[][][] sumDefUtil;
        double[][] sumAttUtil;
        int[][] sumAttackSetSize;
        double[][] sumEntropy;
        int[][] sumOptimalObservations;
        double[][] sumTime;
        double[][] sumObjective;


        int observations[];

        sumDefUtil = new double[numLines][xDataPoints][xDataPoints + 1];
        sumAttUtil = new double[numLines][xDataPoints];
        sumAttackSetSize = new int[numLines][xDataPoints];
        sumEntropy = new double[numLines][xDataPoints];
        sumOptimalObservations = new int[numLines][xDataPoints];
        sumTime = new double[numLines][xDataPoints];
        sumObjective = new double[numLines][xDataPoints];
        observations = new int[xDataPoints];


        System.out.println("Test Robustness");

        try {
            PrintWriter out = new PrintWriter(new File(outputFile));
            PrintWriter outDefUtil = new PrintWriter(new File(rawDefUtil));
//            PrintWriter outAttUtil = new PrintWriter(new File(rawAttUtil));
//            PrintWriter outAttackSetSize = new PrintWriter(new File(rawAttackSetSize));
//            PrintWriter outEntropy = new PrintWriter(new File(rawEntropy));


            out.write("Test Robustness\n");
//            out.write(",P2(1ms),P2(5ms),P2(10ms),P3,"
//                    + ",,P2(1ms),P2(5ms),P2(10ms),P3\n");

            out.write(",1,2,3,4,5,6,7,8,9,10,SSE,"
                    + ",,1,2,3,4,5,6,7,8,9,10,SSE,"
                    + ",,1,2,3,4,5,6,7,8,9,10,SSE,"
                    + ",,1,2,3,4,5,6,7,8,9,10,SSE\n");


            for (int x = 0; x < numLines; x++) {
                for (int i = 0; i < xDataPoints; i++) {
                    sumTime[x][i] = 0.0;
                    sumObjective[x][i] = 0;
                    for (int j = 0; j < xDataPoints + 1; j++) {
                        sumDefUtil[x][i][j] = 0.0;
                    }
                    sumAttUtil[x][i] = 0.0;
                    sumAttackSetSize[x][i] = 0;
                    sumEntropy[x][i] = 0.0;
                    sumOptimalObservations[x][i] = 0;
                }
            }


            for (int i = 0; i < xDataPoints; i++) {
                observations[i] = i + 1;
            }


            for (int i = 0; i < numIterations; i++) {
                System.out.println(i);
                GenerateGameFiles.GeneratePositiveNegativePayoffs(numTargets);

                for (int j = 0; j < xDataPoints; j++) {

//                GenerateGameFiles.GeneratePayoffs(numTargets);
                    alphaOption = 0;
                    GenerateGameFiles.InitStrategiesObservations(numTargets,
                            observations[j], numResources);
                    GenerateGameFiles.GeneratePrior(maxAlpha, alphaOption);
                    P1.GenerateAttackerStrategy();

                    P2Nonconvex.Solve("P2_nonconvex_1ms.run");
                    ArrayList<Double> defStratP2 = (ArrayList<Double>) P2Nonconvex.defStrategy.clone();

                    for (int k = 0; k < xDataPoints; k++) {

                        GenerateGameFiles.InitStrategiesObservations(numTargets,
                                observations[k], numResources);
                        GenerateGameFiles.GeneratePrior(maxAlpha, alphaOption);
                        P1.GenerateAttackerStrategy();

                        double defUtilP2 = Functions.ObjectiveValue(defStratP2);
                        sumDefUtil[0][k][j] += defUtilP2;
                        outDefUtil.write(defUtilP2 + ",");

                    }



                    alphaOption = 1;
                    GenerateGameFiles.InitStrategiesObservations(numTargets,
                            observations[j], numResources);
                    GenerateGameFiles.GeneratePrior(maxAlpha, alphaOption);
                    P1.GenerateAttackerStrategy();

                    P2Nonconvex.Solve("P2_nonconvex_1ms.run");
//                    sumDefUtil[2][j] += P2Nonconvex.objective;
//                    sumAttackSetSize[2][j] += P2Nonconvex.attackSetSize;
//                    sumEntropy[2][j] += P2Nonconvex.entropy;
//
//                    Dobss.Solve();
//                    sumDefUtil[3][j] += Dobss.GetUpdatedDefUtil();
//                    sumAttackSetSize[3][j] += Dobss.attackSetSize;
//                    sumEntropy[3][j] += Dobss.entropy;



//                    GenerateGameFiles.InitStrategiesObservations(numTargets,
//                            observations[j], numResources);
//                    GenerateGameFiles.GeneratePrior(maxAlpha, alphaOption);
//                    P1.GenerateAttackerStrategy();
//
//                    P2Nonconvex.Solve("P2_nonconvex_1ms.run");
//                    Dobss.Solve();
                    defStratP2 = (ArrayList<Double>) P2Nonconvex.defStrategy.clone();
//                    defStratSSE = (ArrayList<Double>) Dobss.defStrategy.clone();

                    outDefUtil.write(",");

                    for (int k = 0; k < xDataPoints; k++) {

                        GenerateGameFiles.InitStrategiesObservations(numTargets,
                                observations[k], numResources);
                        GenerateGameFiles.GeneratePrior(maxAlpha, alphaOption);
                        P1.GenerateAttackerStrategy();

                        double defUtilP2 = Functions.ObjectiveValue(defStratP2);
//                        double defUtilSSE = Functions.ObjectiveValue(defStratSSE);

                        sumDefUtil[1][k][j] += defUtilP2;
                        outDefUtil.write(defUtilP2 + ",");

                    }

                    outDefUtil.write("\n");
                    outDefUtil.flush();


//                    outDefUtil.write(P2Nonconvex.objective + ","
//                            + Dobss.GetUpdatedDefUtil() + "\n");


                    //                    sumTime[0][j] = sumTime[0][j] + P2Nonconvex.time + P1.time;
//                    sumObjective[0][j] += P2Nonconvex.objective;

//                    P2Nonconvex.Solve("P2_nonconvex_5ms.run");
//                    sumTime[1][j] = sumTime[1][j] + P2Nonconvex.time + P1.time;
//                    sumObjective[1][j] += P2Nonconvex.objective;
//
//                    P2Nonconvex.Solve("P2_nonconvex_10ms.run");
//                    sumTime[2][j] = sumTime[2][j] + P2Nonconvex.time + P1.time;
//                    sumObjective[2][j] += P2Nonconvex.objective;
//
//
//                    P3Convex.Solve();
//
//                    sumTime[3][j] = sumTime[3][j] + P3Convex.time + P1.time;
//                    sumObjective[3][j] += P3Convex.objective;

//                    sumTime[0] = sumTime[0] + P2Nonconvex.time + P1.time;
//                    sumObjective[0] += P2Nonconvex.objective;
//                    sumTime[1] = sumTime[1] + P3Convex.time + P1.time;
//                    sumObjective[1] += P3Convex.objective;


//                    outTime.write((P2Nonconvex.time + P1.time) + "," + (P3Convex.time + P1.time) + "\n");
//                    outObjective.write(P2Nonconvex.objective + "," + P3Convex.objective + "\n");
//                    outTime.flush();
//                    outObjective.flush();

                }

                alphaOption = 0;
//                    GenerateGameFiles.InitStrategiesObservations(numTargets,
//                            observations[j], numResources);
//                    GenerateGameFiles.GeneratePrior(maxAlpha, alphaOption);
//                    P1.GenerateAttackerStrategy();
//
                Dobss.Solve();

                ArrayList<Double> defStratSSE = (ArrayList<Double>) Dobss.defStrategy.clone();
//                    ArrayList<Double> defStratSSE = (ArrayList<Double>) Dobss.defStrategy.clone();

                for (int k = 0; k < xDataPoints; k++) {

                    GenerateGameFiles.InitStrategiesObservations(numTargets,
                            observations[k], numResources);
                    GenerateGameFiles.GeneratePrior(maxAlpha, alphaOption);
                    P1.GenerateAttackerStrategy();

                    double defUtilSSE = Functions.ObjectiveValue(defStratSSE);
                    sumDefUtil[0][k][xDataPoints] += defUtilSSE;
                    outDefUtil.write(defUtilSSE + ",");

                }

//                outDefUtil.write(P2Nonconvex.objective + ","
//                        + Dobss.GetUpdatedDefUtil() + ",");


                alphaOption = 1;
//                defStratP2 = (ArrayList<Double>) P2Nonconvex.defStrategy.clone();
//                    defStratSSE = (ArrayList<Double>) Dobss.defStrategy.clone();

                outDefUtil.write(",");

                for (int k = 0; k < xDataPoints; k++) {

                    GenerateGameFiles.InitStrategiesObservations(numTargets,
                            observations[k], numResources);
                    GenerateGameFiles.GeneratePrior(maxAlpha, alphaOption);
                    P1.GenerateAttackerStrategy();

                    double defUtilSSE = Functions.ObjectiveValue(defStratSSE);
                    sumDefUtil[1][k][xDataPoints] += defUtilSSE;
                    outDefUtil.write(defUtilSSE + ",");

                }


                outDefUtil.write("\n\n");

                outDefUtil.flush();


//                out.flush();
//                outTime.flush();
//                outObjective.flush();
            }


            for (int i = 0; i < xDataPoints; i++) {

                for (int j = 0; j < 2; j++) {
                    out.write(observations[i] + ",");
                    for (int k = 0; k < xDataPoints + 1; k++) {
                        out.write(((sumDefUtil[j][i][k] - sumDefUtil[j][i][i]) / numIterations) + ",");
                    }
                    out.write(",");
                }
                out.write("\n");

//                out.write(observations[i] + ","
//                        + (sumDefUtil[0][i] / numIterations) + ","
//                        + (sumDefUtil[1][i] / numIterations) + ","
//                        + (sumDefUtil[2][i] / numIterations) + ","
//                        + (sumDefUtil[3][i] / numIterations) + ", ,"
//                        //                        + (sumDefUtil[2][i] / numIterations) + ", "
//                        //                        + (sumAttUtil[0][i] / numIterations) + ","
//                        //                        + (sumAttUtil[1][i] / numIterations) + ","
//                        //                        + (sumAttUtil[2][i] / numIterations) + ", ,"
//                        + observations[i] + ","
//                        + ((double) sumAttackSetSize[0][i] / numIterations) + ","
//                        + ((double) sumAttackSetSize[1][i] / numIterations) + ","
//                        + ((double) sumAttackSetSize[2][i] / numIterations) + ","
//                        //                        + ((double) sumAttackSetSize[1][i] / numIterations) + ","
//                        + ((double) sumAttackSetSize[3][i] / numIterations) + ", ,"
//                        + observations[i] + ","
//                        + (sumEntropy[0][i] / numIterations) + ","
//                        + (sumEntropy[1][i] / numIterations) + ","
//                        + (sumEntropy[2][i] / numIterations) + ","
//                        //                        + (sumEntropy[1][i] / numIterations) + ","
//                        + (sumEntropy[3][i] / numIterations) + "\n");


//                out.write(observations[i] + ","
//                        + (sumTime[0][i] / numIterations) + ","
//                        + (sumTime[1][i] / numIterations) + ","
//                        + (sumTime[2][i] / numIterations) + ","
//                        + (sumTime[3][i] / numIterations) + ", ,"
//                        + observations[i] + ","
//                        + (sumObjective[0][i] / numIterations) + ","
//                        + (sumObjective[1][i] / numIterations) + ","
//                        + (sumObjective[2][i] / numIterations) + ","
//                        + (sumObjective[3][i] / numIterations) + "\n");

            }
            outDefUtil.flush();
//            outAttUtil.flush();
//            outAttackSetSize.flush();
//            outEntropy.flush();
            outDefUtil.close();
//            outAttUtil.close();
//            outAttackSetSize.close();
//            outEntropy.close();

            out.flush();
            out.close();
        } catch (IOException e) {
            throw new RuntimeException("Error writing security game to file: " + e.getMessage());
        }

    }

    public static void testRobustAnalysis() {
        int numResources = 1;
        double discountFactor = 0.95;
        int formulation = 2;
        double maxAlpha = 10.0;
        int alphaOption = 0;
//        GenerateGameFiles.alpha = 0.0;

        String outputFile = "outputFileRobustness.csv";
        String rawDefUtil = "rawDefUtilRobustness.csv";
//        String rawAttUtil = "rawAttUtilRobustness.csv";
        String rawAttackSetSize = "rawAttackSetSizeRobustness.csv";
        String rawEntropy = "rawEntropyRobustness.csv";

        int numIterations = 50;
        int numNoiseIterations = 25;
        double stdDev = 1.5;


        int numLines = 6;
        int maxTargets = 6;
        int payoffMagnitude = 100;


//        int numTargets = 5;
//        int numObservations = 5;
//        int numResources = 1;

        double[] sumDefUtil;
//        double[] sumAttUtil;
        int[] sumAttackSetSize;
        double[] sumEntropy;
        int[] sumOptimalObservations;

        sumDefUtil = new double[numLines];
//        sumAttUtil = new double[3];
        sumAttackSetSize = new int[numLines];
        sumEntropy = new double[numLines];
        sumOptimalObservations = new int[numLines];

        try {
            PrintWriter out = new PrintWriter(new File(outputFile));
            PrintWriter outDefUtil = new PrintWriter(new File(rawDefUtil));
//            PrintWriter outAttUtil = new PrintWriter(new File(rawAttUtil));
            PrintWriter outAttackSetSize = new PrintWriter(new File(rawAttackSetSize));
            PrintWriter outEntropy = new PrintWriter(new File(rawEntropy));


//            P1.GenerateAttackerStrategy(numTargets, numObservations, numResources);
//            P2Nonconvex.Solve();
//            P3Convex.Solve();

            Random generator;
            generator = new Random();

            ArrayList<ArrayList<Double>> defCovList = new ArrayList<ArrayList<Double>>();
            ArrayList<ArrayList<Double>> defUncovList = new ArrayList<ArrayList<Double>>();
            ArrayList<ArrayList<Double>> attCovList = new ArrayList<ArrayList<Double>>();
            ArrayList<ArrayList<Double>> attUncovList = new ArrayList<ArrayList<Double>>();

            for (int x = 0; x < numIterations; x++) {
                ArrayList<Double> defCov = new ArrayList<Double>();
                ArrayList<Double> defUncov = new ArrayList<Double>();
                ArrayList<Double> attCov = new ArrayList<Double>();
                ArrayList<Double> attUncov = new ArrayList<Double>();

                for (int i = 1; i <= maxTargets; i++) {

                    defCov.add(generator.nextInt(payoffMagnitude) + 1.0);
                    defUncov.add(-generator.nextInt(payoffMagnitude) - 1.0);
                    attCov.add(-generator.nextInt(payoffMagnitude) - 1.0);
                    attUncov.add(generator.nextInt(payoffMagnitude) + 1.0);

//                    defCov.add(generator.nextInt(payoffMagnitude) + 101.0);
//                    defUncov.add((double) generator.nextInt(payoffMagnitude));
//
//                    attCov.add((double) generator.nextInt(payoffMagnitude));
//                    attUncov.add(generator.nextInt(payoffMagnitude) + 101.0);
                }
                defCovList.add(defCov);
                defUncovList.add(defUncov);
                attCovList.add(attCov);
                attUncovList.add(attUncov);
            }




            System.out.println("Varying number of targets");
            out.write("Vary num Targets\n");


//            out.write("Number of Targets,Defender Utility(P2: 1ms),Defender Utility(P2: 5ms),"
//                    + "Defender Utility(P2: 5ms),Defender Utility(P3),Defender Utility(SSE),"
//                    + ",,P2(1ms),P2(5ms),P2(10ms),P3,SSE,"
//                    + ",,P2(1ms),P2(5ms),P2(10ms),P3,SSE,"
//                    + ",,P2(1ms),P2(5ms),P2(10ms),P3\n");

            out.write("Number of Targets,P2(Uniform),SSE(Uniform),P2(Error),SSE(Error),"
                    + ",,P2(Uniform),SSE(Uniform),P2(SSE),SSE(SSE),"
                    + ",,P2(Uniform),SSE(Uniform),P2(SSE),SSE(SSE),"
                    + ",,P2(Uniform),P2(SSE)\n");


            // 18 targets w/ 5 observations is 23 seconds
            for (int i = 3; i <= maxTargets; i++) {
//            for (int i = 8; i <= 15; i++) {
                System.out.println(i);

                for (int x = 0; x < numLines; x++) {
                    sumDefUtil[x] = 0.0;
//                    sumAttUtil[x] = 0.0;
                    sumAttackSetSize[x] = 0;
                    sumEntropy[x] = 0.0;
                    sumOptimalObservations[x] = 0;
                }

                for (int j = 0; j < numIterations; j++) {
//                    GenerateGameFiles.GeneratePayoffs(i);
                    GenerateGameFiles.SetPayoffsVaryTargets(i, defCovList.get(j),
                            defUncovList.get(j), attCovList.get(j), attUncovList.get(j));

                    alphaOption = 0;
                    BinarySearch.Solve(2, i, numResources, discountFactor,
                            maxAlpha, alphaOption, "P2_nonconvex_1ms.run");
                    int optimalObservations = BinarySearch.optimalObservations;
                    ArrayList<Double> defStratP2 = (ArrayList<Double>) P2Nonconvex.defStrategy.clone();

                    sumDefUtil[0] += BinarySearch.optimalDefenderUtil;
                    sumAttackSetSize[0] += P2Nonconvex.attackSetSize;
                    sumEntropy[0] += P2Nonconvex.entropy;
                    sumOptimalObservations[0] += BinarySearch.optimalObservations;

                    Dobss.Solve();
                    ArrayList<Double> defStratSSE = (ArrayList<Double>) Dobss.defStrategy.clone();

                    AttackerUtility.Solve(discountFactor, Dobss.defStrategy);
                    sumDefUtil[1] += Dobss.GetUpdatedDefUtil();
                    sumAttackSetSize[1] += Dobss.attackSetSize;
                    sumEntropy[1] += Dobss.entropy;

                    double sumDefUtilNoise[] = new double[2];
                    for (int k = 0; k < 2; k++) {
                        sumDefUtilNoise[k] = 0.0;
                    }
                    System.out.println("optimal obs: " + optimalObservations
                            + ", defUtil: " + BinarySearch.optimalDefenderUtil);

                    outDefUtil.write(BinarySearch.optimalDefenderUtil + "," + Dobss.GetUpdatedDefUtil() + ",");


                    Hashtable<Integer, Double> defUtilP2Map = new Hashtable<Integer, Double>();
                    Hashtable<Integer, Double> defUtilSSEMap = new Hashtable<Integer, Double>();

                    for (int k = 0; k < numNoiseIterations; k++) {

                        int noiseObservations = (int) (Math.round(generator.nextGaussian()
                                * stdDev) + optimalObservations);
//                        System.out.print(noiseObservations + ",");

                        if (noiseObservations < 0) {
                            noiseObservations = 0;
                        }
                        if (noiseObservations > 16) {
                            noiseObservations = 16;
                        }

                        if (!(defUtilP2Map.containsKey(noiseObservations))) {

                            GenerateGameFiles.InitStrategiesObservations(i,
                                    noiseObservations, numResources);
                            GenerateGameFiles.GeneratePrior(maxAlpha, alphaOption);
                            P1.GenerateAttackerStrategy();
                            defUtilP2Map.put(noiseObservations, Functions.ObjectiveValue(defStratP2));
                            defUtilSSEMap.put(noiseObservations, Functions.ObjectiveValue(defStratSSE));

//                            sumDefUtilNoise[0] += defUtilP2Map.get(noiseObservations);
//                            sumDefUtilNoise[1] += defUtilSSEMap.get(noiseObservations);
                        }

                        sumDefUtilNoise[0] += defUtilP2Map.get(noiseObservations);
                        sumDefUtilNoise[1] += defUtilSSEMap.get(noiseObservations);

                    }
                    System.out.println(defUtilP2Map);
                    sumDefUtil[2] += sumDefUtilNoise[0] / numNoiseIterations;
                    sumDefUtil[3] += sumDefUtilNoise[1] / numNoiseIterations;

                    outDefUtil.write((sumDefUtilNoise[0] / numNoiseIterations)
                            + "," + (sumDefUtilNoise[1] / numNoiseIterations) + "\n");


//
//                    alphaOption = 1;
//                    BinarySearch.Solve(2, i, numResources, discountFactor,
//                            maxAlpha, alphaOption, "P2_nonconvex_1ms.run");
//                    sumDefUtil[2] += BinarySearch.optimalDefenderUtil;
//                    sumAttackSetSize[2] += P2Nonconvex.attackSetSize;
//                    sumEntropy[2] += P2Nonconvex.entropy;
//                    sumOptimalObservations[2] += BinarySearch.optimalObservations;
//
//                    Dobss.Solve();
//                    AttackerUtility.Solve(discountFactor, Dobss.defStrategy);
//                    sumDefUtil[3] += Dobss.GetUpdatedDefUtil();
//                    sumAttackSetSize[3] += Dobss.attackSetSize;
//                    sumEntropy[3] += Dobss.entropy;



//                    outDefUtil.write(BinarySearch.optimalDefenderUtil + "," + Dobss.GetUpdatedDefUtil() + ",");
//                    outAttUtil.write(BinarySearch.optimalAttackerUtil + "," + AttackerUtility.finalUtility + ",");
//                    outAttackSetSize.write(P2Nonconvex.attackSetSize + "," + Dobss.attackSetSize + ",");
//                    outEntropy.write(P2Nonconvex.entropy + "," + Dobss.entropy + ",");


//                    outDefUtil.write(BinarySearch.optimalDefenderUtil + "\n");
//                    outAttUtil.write(BinarySearch.optimalAttackerUtil + "\n");
//                    outAttackSetSize.write(P3Convex.attackSetSize + "\n");
//                    outEntropy.write(P3Convex.entropy + "\n");


                    outDefUtil.flush();
//                    outAttUtil.flush();
                    outAttackSetSize.flush();
                    outEntropy.flush();

                }

                outDefUtil.write("\n");
//                outAttUtil.write("\n");
//                outAttackSetSize.write("\n");
//                outEntropy.write("\n");

                out.write(i + "," + (sumDefUtil[0] / numIterations) + ","
                        + (sumDefUtil[1] / numIterations) + ","
                        + (sumDefUtil[2] / numIterations) + ","
                        + (sumDefUtil[3] / numIterations) + "\n");
//                        + i + ","
//                        + ((double) sumAttackSetSize[0] / numIterations) + ","
//                        + ((double) sumAttackSetSize[1] / numIterations) + ","
//                        + ((double) sumAttackSetSize[2] / numIterations) + ","
//                        + ((double) sumAttackSetSize[3] / numIterations) + ", ,"
//                        + i + ","
//                        + (sumEntropy[0] / numIterations) + ","
//                        + (sumEntropy[1] / numIterations) + ","
//                        + (sumEntropy[2] / numIterations) + ","
//                        + (sumEntropy[3] / numIterations) + ", ,"
//                        + i + ","
//                        + ((double) sumOptimalObservations[0] / numIterations) + ","
//                        + ((double) sumOptimalObservations[2] / numIterations) + "\n");

                out.flush();
                outDefUtil.flush();
//                outAttUtil.flush();
                outAttackSetSize.flush();
                outEntropy.flush();
            }

            outDefUtil.flush();
//            outAttUtil.flush();
            outAttackSetSize.flush();
            outEntropy.flush();
            outDefUtil.close();
//            outAttUtil.close();
            outAttackSetSize.close();
            outEntropy.close();

            out.flush();
            out.close();
        } catch (IOException e) {
            throw new RuntimeException("Error writing security game to file: " + e.getMessage());
        }
    }

    public static void testPerformanceVaryDiscountFactor() {

        int numResources = 1;
        //double discountFactor = 0.9;
        int formulation = 2;
        int numTargets = 5;
        double maxAlpha = 10.0;
        int alphaOption = 0;


        int numLines = 5;

//        GenerateGameFiles.alpha = 0.0;

        String outputFile = "outputFilePerfVaryDiscountFactor.csv";
        String rawDefUtil = "rawDefUtilPerfVaryDiscountFactor.csv";
        String rawAttUtil = "rawAttUtilPerfVaryDiscountFactor.csv";
        String rawAttackSetSize = "rawAttackSetSizePerfVaryDiscountFactor.csv";
        String rawEntropy = "rawEntropyPerfVaryDiscountFactor.csv";

        int numIterations = 200;

//        int numTargets = 5;
//        int numObservations = 5;
//        int numResources = 1;

        int xDataPoints = 6;
        double[] discountFactor;
        discountFactor = new double[xDataPoints];

        double[][] sumDefUtil;
        double[][] sumAttUtil;
        int[][] sumAttackSetSize;
        double[][] sumEntropy;
        int[][] sumOptimalObservations;

        sumDefUtil = new double[numLines][xDataPoints];
        sumAttUtil = new double[numLines][xDataPoints];
        sumAttackSetSize = new int[numLines][xDataPoints];
        sumEntropy = new double[numLines][xDataPoints];
        sumOptimalObservations = new int[numLines][xDataPoints];

        try {
            PrintWriter out = new PrintWriter(new File(outputFile));
            PrintWriter outDefUtil = new PrintWriter(new File(rawDefUtil));
            PrintWriter outAttUtil = new PrintWriter(new File(rawAttUtil));
            PrintWriter outAttackSetSize = new PrintWriter(new File(rawAttackSetSize));
            PrintWriter outEntropy = new PrintWriter(new File(rawEntropy));


//            P1.GenerateAttackerStrategy(numTargets, numObservations, numResources);
//            P2Nonconvex.Solve();
//            P3Convex.Solve();


            System.out.println("Varying discount");
            out.write("Vary discount\n");


            out.write("Discount Factor,P1(Uniform),SSE(Uniform),P1(SSE),SSE(SSE),"
                    + ",,P2(Uniform),SSE(Uniform),P2(SSE),SSE(SSE),"
                    + ",,P2(Uniform),SSE(Uniform),P2(SSE),SSE(SSE),"
                    + ",,P2(Uniform),P2(SSE)\n");


//            out.write("Discount Factor,P2,SSE,"
//                    //                    + "Attacker Utility(P2),Attacker Utility(P3),Attacker Utility(SSE),"
//                    + ",,P2,SSE,"
//                    + ",,P2,SSE,"
//                    + ",,P2\n");
            // 18 targets w/ 5 observations is 23 seconds
//            for (int i = 0; i < xDataPoints; i++) {
//                discountFactor[i] = 0.9 + i * 0.02;
//            }

            discountFactor[0] = 0.1;
            discountFactor[1] = 0.5;
            discountFactor[2] = 1.0;
            discountFactor[3] = 2.0;
            discountFactor[4] = 5.0;
            discountFactor[5] = 10.0;

            for (int x = 0; x < numLines; x++) {
                for (int i = 0; i < xDataPoints; i++) {
                    sumDefUtil[x][i] = 0.0;
                    sumAttUtil[x][i] = 0.0;
                    sumAttackSetSize[x][i] = 0;
                    sumEntropy[x][i] = 0.0;
                    sumOptimalObservations[x][i] = 0;
                }
            }


            for (int i = 0; i < numIterations; i++) {
                System.out.println(i);
//                GenerateGameFiles.GeneratePayoffs(numTargets);
                GenerateGameFiles.GeneratePositiveNegativePayoffs(numTargets);

                for (int j = 0; j < xDataPoints; j++) {
                    alphaOption = 0;

                    BinarySearch.Solve(2, numTargets, numResources, discountFactor[j],
                            maxAlpha, alphaOption, "P2_nonconvex_1ms.run");
                    sumDefUtil[0][j] += BinarySearch.optimalDefenderUtil;
                    sumAttUtil[0][j] += BinarySearch.optimalAttackerUtil;
                    sumAttackSetSize[0][j] += P2Nonconvex.attackSetSize;
                    sumEntropy[0][j] += P2Nonconvex.entropy;
                    sumOptimalObservations[0][j] += BinarySearch.optimalObservations;

                    Dobss.Solve();
                    AttackerUtility.Solve(discountFactor[j], Dobss.defStrategy);
                    sumDefUtil[1][j] += Dobss.GetUpdatedDefUtil();
                    sumAttUtil[1][j] += AttackerUtility.finalUtility;
                    sumAttackSetSize[1][j] += Dobss.attackSetSize;
                    sumEntropy[1][j] += Dobss.entropy;


                    outDefUtil.write(BinarySearch.optimalDefenderUtil + "," + Dobss.GetUpdatedDefUtil() + ",");
                    outAttackSetSize.write(P2Nonconvex.attackSetSize + "," + Dobss.attackSetSize + ",");
                    outEntropy.write(P2Nonconvex.entropy + "," + Dobss.entropy + ",");


                    alphaOption = 1;
                    BinarySearch.Solve(2, numTargets, numResources, discountFactor[j],
                            maxAlpha, alphaOption, "P2_nonconvex_1ms.run");
                    sumDefUtil[2][j] += BinarySearch.optimalDefenderUtil;
                    sumAttUtil[2][j] += BinarySearch.optimalAttackerUtil;
                    sumAttackSetSize[2][j] += P2Nonconvex.attackSetSize;
                    sumEntropy[2][j] += P2Nonconvex.entropy;
                    sumOptimalObservations[2][j] += BinarySearch.optimalObservations;

                    Dobss.Solve();
                    AttackerUtility.Solve(discountFactor[j], Dobss.defStrategy);
                    sumDefUtil[3][j] += Dobss.GetUpdatedDefUtil();
                    sumAttUtil[3][j] += AttackerUtility.finalUtility;
                    sumAttackSetSize[3][j] += Dobss.attackSetSize;
                    sumEntropy[3][j] += Dobss.entropy;

                    outDefUtil.write(BinarySearch.optimalDefenderUtil + "," + Dobss.GetUpdatedDefUtil() + "\n");
                    outAttackSetSize.write(P2Nonconvex.attackSetSize + "," + Dobss.attackSetSize + "\n");
                    outEntropy.write(P2Nonconvex.entropy + "," + Dobss.entropy + "\n");


//
//                    Dobss.Solve();
//                    AttackerUtility.Solve(discountFactor[j], Dobss.defStrategy);
//                    sumDefUtil[2][j] += Dobss.GetUpdatedDefUtil();
//                    sumAttUtil[2][j] += AttackerUtility.finalUtility;
//                    sumAttackSetSize[2][j] += Dobss.attackSetSize;
//                    sumEntropy[2][j] += Dobss.entropy;


//                    outDefUtil.write(BinarySearch.optimalDefenderUtil + "," + Dobss.GetUpdatedDefUtil() + ",");
//                    outAttUtil.write(BinarySearch.optimalAttackerUtil + "," + AttackerUtility.finalUtility + ",");
//                    outAttackSetSize.write(P2Nonconvex.attackSetSize + "," + Dobss.attackSetSize + ",");
//                    outEntropy.write(P2Nonconvex.entropy + "," + Dobss.entropy + ",");

//                    BinarySearch.Solve(3, numTargets, numResources, discountFactor[j],
//                            maxAlpha, alphaOption, "");
//                    sumDefUtil[1][j] += BinarySearch.optimalDefenderUtil;
//                    sumAttUtil[1][j] += BinarySearch.optimalAttackerUtil;
//                    sumAttackSetSize[1][j] += P3Convex.attackSetSize;
//                    sumEntropy[1][j] += P3Convex.entropy;
//                    sumOptimalObservations[1][j] += BinarySearch.optimalObservations;

//                    outDefUtil.write(BinarySearch.optimalDefenderUtil + "\n");
//                    outAttUtil.write(BinarySearch.optimalAttackerUtil + "\n");
//                    outAttackSetSize.write(P3Convex.attackSetSize + "\n");
//                    outEntropy.write(P3Convex.entropy + "\n");

                }

                outDefUtil.write("\n");
                outAttUtil.write("\n");
                outAttackSetSize.write("\n");
                outEntropy.write("\n");

                outDefUtil.flush();
                outAttUtil.flush();
                outAttackSetSize.flush();
                outEntropy.flush();
            }


            for (int i = 0; i < xDataPoints; i++) {

                out.write(discountFactor[i] + ","
                        + (sumDefUtil[0][i] / numIterations) + ","
                        + (sumDefUtil[1][i] / numIterations) + ","
                        + (sumDefUtil[2][i] / numIterations) + ","
                        + (sumDefUtil[3][i] / numIterations) + ", ,"
                        //                        + (sumDefUtil[2][i] / numIterations) + ", "
                        //                        + (sumAttUtil[0][i] / numIterations) + ","
                        //                        + (sumAttUtil[1][i] / numIterations) + ","
                        //                        + (sumAttUtil[2][i] / numIterations) + ", ,"
                        + discountFactor[i] + ","
                        + ((double) sumAttackSetSize[0][i] / numIterations) + ","
                        + ((double) sumAttackSetSize[1][i] / numIterations) + ","
                        + ((double) sumAttackSetSize[2][i] / numIterations) + ","
                        //                        + ((double) sumAttackSetSize[1][i] / numIterations) + ","
                        + ((double) sumAttackSetSize[3][i] / numIterations) + ", ,"
                        + discountFactor[i] + ","
                        + (sumEntropy[0][i] / numIterations) + ","
                        + (sumEntropy[1][i] / numIterations) + ","
                        + (sumEntropy[2][i] / numIterations) + ","
                        //                        + (sumEntropy[1][i] / numIterations) + ","
                        + (sumEntropy[3][i] / numIterations) + ", ,"
                        + discountFactor[i] + ","
                        + ((double) sumOptimalObservations[0][i] / numIterations) + ","
                        + ((double) sumOptimalObservations[2][i] / numIterations) + "\n");


//                out.write(discountFactor[i] + "," + (sumDefUtil[0][i] / numIterations) + ","
//                        + (sumDefUtil[1][i] / numIterations) + ", "
//                        + (sumDefUtil[2][i] / numIterations) + ", "
//                        + (sumAttUtil[0][i] / numIterations) + ","
//                        + (sumAttUtil[1][i] / numIterations) + ","
//                        + (sumAttUtil[2][i] / numIterations) + ", ,"
//                        + discountFactor[i] + ","
//                        + ((double) sumAttackSetSize[0][i] / numIterations) + ","
//                        + ((double) sumAttackSetSize[1][i] / numIterations) + ","
//                        + ((double) sumAttackSetSize[2][i] / numIterations) + ", ,"
//                        + discountFactor[i] + ","
//                        + (sumEntropy[0][i] / numIterations) + ","
//                        + (sumEntropy[1][i] / numIterations) + ","
//                        + (sumEntropy[2][i] / numIterations) + ", ,"
//                        + discountFactor[i] + ","
//                        + ((double) sumOptimalObservations[0][i] / numIterations) + ","
//                        + ((double) sumOptimalObservations[1][i] / numIterations) + "\n");

            }

            outDefUtil.flush();
            outAttUtil.flush();
            outAttackSetSize.flush();
            outEntropy.flush();
            outDefUtil.close();
            outAttUtil.close();
            outAttackSetSize.close();
            outEntropy.close();

            out.flush();
            out.close();
        } catch (IOException e) {
            throw new RuntimeException("Error writing security game to file: " + e.getMessage());
        }




//
//
//
//
//
//
//
//
//        int numResources = 1;
//        int numTargets = 5;
//        double discountFactor = 0.9;
//        GenerateGameFiles.alpha = 0.0;
////        int formulation = 2;
//
//        String outputFile = "outputFilePerfVaryDiscount.csv";
//        String rawDefUtil = "rawDefUtilPerfVaryDiscount.csv";
//        String rawAttUtil = "rawAttUtilPerfVaryDiscount.csv";
//
//        int numIterations = 200;
//
////        int numTargets = 5;
////        int numObservations = 5;
////        int numResources = 1;
//
//        double[] sumDefUtil;
//        double[] sumAttUtil;
//
//        sumDefUtil = new double[2];
//        sumAttUtil = new double[2];
//
//        try {
//            PrintWriter out = new PrintWriter(new File(outputFile));
//            PrintWriter outDefUtil = new PrintWriter(new File(rawDefUtil));
//            PrintWriter outAttUtil = new PrintWriter(new File(rawAttUtil));
//
//
////            P1.GenerateAttackerStrategy(numTargets, numObservations, numResources);
////            P2Nonconvex.Solve();
////            P3Convex.Solve();
//
//
//            System.out.println("Varying discount factor");
//            out.write("Vary discount factor\n");
//
//            out.write("Discount Factor, Defender Utility(P2), Defender Utility(P3), "
//                    + "Attacker Utility(P2), Attacker Utility(P3)\n");
//            // 18 targets w/ 5 observations is 23 seconds
//            for (double i = 0.95; i >= 0.7; i = i - 0.05) {
//                System.out.println(i);
//
//                for (int x = 0; x < 2; x++) {
//                    sumDefUtil[x] = 0.0;
//                    sumAttUtil[x] = 0.0;
//                }
//
//                for (int j = 0; j < numIterations; j++) {
//                    GenerateGameFiles.GeneratePayoffs(numTargets);
//
//                    BinarySearch.Solve(2, numTargets, numResources, i);
//                    sumDefUtil[0] += BinarySearch.optimalDefenderUtil;
//                    sumAttUtil[0] += BinarySearch.optimalAttackerUtil;
//
//                    outDefUtil.write(BinarySearch.optimalDefenderUtil + ",");
//                    outAttUtil.write(BinarySearch.optimalAttackerUtil + ",");
//
//                    BinarySearch.Solve(3, numTargets, numResources, i);
//                    sumDefUtil[1] += BinarySearch.optimalDefenderUtil;
//                    sumAttUtil[1] += BinarySearch.optimalAttackerUtil;
//
//                    outDefUtil.write(BinarySearch.optimalDefenderUtil + "\n");
//                    outAttUtil.write(BinarySearch.optimalAttackerUtil + "\n");
//
//                }
//
//                outDefUtil.write("\n");
//                outAttUtil.write("\n");
//
//                out.write(i + "," + (sumDefUtil[0] / numIterations) + "," + (sumDefUtil[1] / numIterations) + ", "
//                        + (sumAttUtil[0] / numIterations) + "," + (sumAttUtil[1] / numIterations) + "\n");
//
//                out.flush();
//                outDefUtil.flush();
//                outAttUtil.flush();
//
//            }
//
//            outDefUtil.flush();
//            outAttUtil.flush();
//            outDefUtil.close();
//            outAttUtil.close();
//
//            out.flush();
//            out.close();
//        } catch (IOException e) {
//            throw new RuntimeException("Error writing security game to file: " + e.getMessage());
//        }


    }

    public static void testPerformanceVaryPrior() {

        int numResources = 1;
//        double discountFactor = 0.95;
        double discountFactor = 1.0;
        int numTargets = 5;
        int alphaOption = 0;
//        GenerateGameFiles.alpha = 0.0;

        String outputFile = "outputFilePerfVaryPrior.csv";
        String rawDefUtil = "rawDefUtilPerfVaryPrior.csv";
        String rawAttUtil = "rawAttUtilPerfVaryPrior.csv";
        String rawAttackSetSize = "rawAttackSetSizePerfVaryPrior.csv";
        String rawEntropy = "rawEntropyPerfVaryPrior.csv";

        int numIterations = 200;

//        int numTargets = 5;
//        int numObservations = 5;
//        int numResources = 1;

        int xDataPoints = 8;
        int numLines = 5;
        double[] prior;
        prior = new double[xDataPoints];

        double[][] sumDefUtil;
//        double[][] sumAttUtil;
        int[][] sumAttackSetSize;
        double[][] sumEntropy;
        int[][] sumOptimalObservations;

        sumDefUtil = new double[numLines][xDataPoints];
//        sumAttUtil = new double[3][xDataPoints];
        sumAttackSetSize = new int[numLines][xDataPoints];
        sumEntropy = new double[numLines][xDataPoints];
        sumOptimalObservations = new int[numLines][xDataPoints];

        try {
            PrintWriter out = new PrintWriter(new File(outputFile));
            PrintWriter outDefUtil = new PrintWriter(new File(rawDefUtil));
            PrintWriter outAttUtil = new PrintWriter(new File(rawAttUtil));
            PrintWriter outAttackSetSize = new PrintWriter(new File(rawAttackSetSize));
            PrintWriter outEntropy = new PrintWriter(new File(rawEntropy));


//            P1.GenerateAttackerStrategy(numTargets, numObservations, numResources);
//            P2Nonconvex.Solve();
//            P3Convex.Solve();


            System.out.println("Varying prior");
            out.write("Vary prior\n");


            out.write("Prior,P1(Uniform),SSE(Uniform),P1(SSE),SSE(SSE),"
                    + ",,P2(Uniform),SSE(Uniform),P2(SSE),SSE(SSE),"
                    + ",,P2(Uniform),SSE(Uniform),P2(SSE),SSE(SSE),"
                    + ",,P2(Uniform),P2(SSE)\n");

//            out.write("Prior,P2,SSE,"
//                    + ",,P2,SSE,"
//                    + ",,P2,SSE,"
//                    + ",,P2\n");

//            out.write("Prior,Defender Utility(P2: 1ms),Defender Utility(P2: 5ms),"
//                    + "Defender Utility(P2: 10ms),Defender Utility(P3),Defender Utility(SSE),"
//                    + ",,P2(1ms),P2(5ms),P2(10ms),P3,SSE,"
//                    + ",,P2(1ms),P2(5ms),P2(10ms),P3,SSE,"
//                    + ",,P2(1ms),P2(5ms),P2(10ms),P3\n");

            // 18 targets w/ 5 observations is 23 seconds

            prior[0] = 0.0;
            prior[1] = 1.0;
            prior[2] = 5.0;
            prior[3] = 10.0;
            prior[4] = 50.0;
            prior[5] = 100.0;
            prior[6] = 250.0;
            prior[7] = 500.0;

            for (int x = 0; x < numLines; x++) {
                for (int i = 0; i < xDataPoints; i++) {
                    sumDefUtil[x][i] = 0.0;
//                    sumAttUtil[x][i] = 0.0;
                    sumAttackSetSize[x][i] = 0;
                    sumEntropy[x][i] = 0.0;
                    sumOptimalObservations[x][i] = 0;
                }
            }


            for (int i = 0; i < numIterations; i++) {
                System.out.println(i);
//                GenerateGameFiles.GeneratePayoffs(numTargets);
                GenerateGameFiles.GeneratePositiveNegativePayoffs(numTargets);

                for (int j = 0; j < xDataPoints; j++) {

//                    GenerateGameFiles.alpha = prior[j];

                    alphaOption = 0;
                    BinarySearch.Solve(2, numTargets, numResources, discountFactor,
                            prior[j], alphaOption, "P2_nonconvex_1ms.run");
                    sumDefUtil[0][j] += BinarySearch.optimalDefenderUtil;
//                    sumAttUtil[0][j] += BinarySearch.optimalAttackerUtil;
                    sumAttackSetSize[0][j] += P2Nonconvex.attackSetSize;
                    sumEntropy[0][j] += P2Nonconvex.entropy;
                    sumOptimalObservations[0][j] += BinarySearch.optimalObservations;

                    Dobss.Solve();
                    AttackerUtility.Solve(discountFactor, Dobss.defStrategy);
                    sumDefUtil[1][j] += Dobss.GetUpdatedDefUtil();
//                    sumAttUtil[2][j] += AttackerUtility.finalUtility;
                    sumAttackSetSize[1][j] += Dobss.attackSetSize;
                    sumEntropy[1][j] += Dobss.entropy;

                    outDefUtil.write(BinarySearch.optimalDefenderUtil + "," + Dobss.GetUpdatedDefUtil() + ",");
                    outAttackSetSize.write(P2Nonconvex.attackSetSize + "," + Dobss.attackSetSize + ",");
                    outEntropy.write(P2Nonconvex.entropy + "," + Dobss.entropy + ",");

                    alphaOption = 1;
                    BinarySearch.Solve(2, numTargets, numResources, discountFactor,
                            prior[j], alphaOption, "P2_nonconvex_1ms.run");
                    sumDefUtil[2][j] += BinarySearch.optimalDefenderUtil;
//                    sumAttUtil[0][j] += BinarySearch.optimalAttackerUtil;
                    sumAttackSetSize[2][j] += P2Nonconvex.attackSetSize;
                    sumEntropy[2][j] += P2Nonconvex.entropy;
                    sumOptimalObservations[2][j] += BinarySearch.optimalObservations;

                    Dobss.Solve();
                    AttackerUtility.Solve(discountFactor, Dobss.defStrategy);
                    sumDefUtil[3][j] += Dobss.GetUpdatedDefUtil();
//                    sumAttUtil[2][j] += AttackerUtility.finalUtility;
                    sumAttackSetSize[3][j] += Dobss.attackSetSize;
                    sumEntropy[3][j] += Dobss.entropy;


                    outDefUtil.write(BinarySearch.optimalDefenderUtil + "," + Dobss.GetUpdatedDefUtil() + "\n");
                    outAttackSetSize.write(P2Nonconvex.attackSetSize + "," + Dobss.attackSetSize + "\n");
                    outEntropy.write(P2Nonconvex.entropy + "," + Dobss.entropy + "\n");

                    //                    System.out.println(prior[j] + ", " + BinarySearch.optimalDefenderUtil + ", " + P1.attackList);

//                    BinarySearch.Solve(2, numTargets, numResources, discountFactor,
//                            prior[j], alphaOption, "P2_nonconvex_5ms.run");
//                    sumDefUtil[1][j] += BinarySearch.optimalDefenderUtil;
////                    sumAttUtil[0][j] += BinarySearch.optimalAttackerUtil;
//                    sumAttackSetSize[1][j] += P2Nonconvex.attackSetSize;
//                    sumEntropy[1][j] += P2Nonconvex.entropy;
//                    sumOptimalObservations[1][j] += BinarySearch.optimalObservations;



//                    BinarySearch.Solve(2, numTargets, numResources, discountFactor,
//                            prior[j], alphaOption, "P2_nonconvex_10ms.run");
//                    sumDefUtil[2][j] += BinarySearch.optimalDefenderUtil;
////                    sumAttUtil[0][j] += BinarySearch.optimalAttackerUtil;
//                    sumAttackSetSize[2][j] += P2Nonconvex.attackSetSize;
//                    sumEntropy[2][j] += P2Nonconvex.entropy;
//                    sumOptimalObservations[2][j] += BinarySearch.optimalObservations;



//                    BinarySearch.Solve(3, numTargets, numResources, discountFactor,
//                            prior[j], alphaOption, "");
//                    sumDefUtil[3][j] += BinarySearch.optimalDefenderUtil;
////                    sumAttUtil[1][j] += BinarySearch.optimalAttackerUtil;
//                    sumAttackSetSize[3][j] += P3Convex.attackSetSize;
//                    sumEntropy[3][j] += P3Convex.entropy;
//                    sumOptimalObservations[3][j] += BinarySearch.optimalObservations;
//
//                    outDefUtil.write(BinarySearch.optimalDefenderUtil + "\n");
//                    outAttUtil.write(BinarySearch.optimalAttackerUtil + "\n");
//                    outAttackSetSize.write(P3Convex.attackSetSize + "\n");
//                    outEntropy.write(P3Convex.entropy + "\n");

                }

                outDefUtil.write("\n");
                outAttUtil.write("\n");
                outAttackSetSize.write("\n");
                outEntropy.write("\n");

                outDefUtil.flush();
                outAttUtil.flush();
                outAttackSetSize.flush();
                outEntropy.flush();
            }


            for (int i = 0; i < xDataPoints; i++) {

                out.write(prior[i] + ","
                        + (sumDefUtil[0][i] / numIterations) + ","
                        + (sumDefUtil[1][i] / numIterations) + ", "
                        + (sumDefUtil[2][i] / numIterations) + ", "
                        //                        + (sumDefUtil[3][i] / numIterations) + ", "
                        + (sumDefUtil[3][i] / numIterations) + ", ,"
                        //                        + (sumAttUtil[0][i] / numIterations) + ","
                        //                        + (sumAttUtil[1][i] / numIterations) + ","
                        //                        + (sumAttUtil[2][i] / numIterations) + ", ,"
                        + prior[i] + ","
                        + ((double) sumAttackSetSize[0][i] / numIterations) + ","
                        + ((double) sumAttackSetSize[1][i] / numIterations) + ","
                        + ((double) sumAttackSetSize[2][i] / numIterations) + ","
                        //                        + ((double) sumAttackSetSize[3][i] / numIterations) + ","
                        + ((double) sumAttackSetSize[3][i] / numIterations) + ", ,"
                        + prior[i] + ","
                        + (sumEntropy[0][i] / numIterations) + ","
                        + (sumEntropy[1][i] / numIterations) + ","
                        + (sumEntropy[2][i] / numIterations) + ","
                        //                        + (sumEntropy[3][i] / numIterations) + ","
                        + (sumEntropy[3][i] / numIterations) + ", ,"
                        + prior[i] + ","
                        + ((double) sumOptimalObservations[0][i] / numIterations) + ","
                        //                        + ((double) sumOptimalObservations[1][i] / numIterations) + ","
                        //                        + ((double) sumOptimalObservations[2][i] / numIterations) + ","
                        + ((double) sumOptimalObservations[2][i] / numIterations) + "\n");

//
//                out.write(prior[i] + "," + (sumDefUtil[0][i] / numIterations) + ","
//                        + (sumDefUtil[1][i] / numIterations) + ", "
//                        + (sumDefUtil[2][i] / numIterations) + ", "
//                        + (sumDefUtil[3][i] / numIterations) + ", "
//                        + (sumDefUtil[4][i] / numIterations) + ", ,"
//                        //                        + (sumAttUtil[0][i] / numIterations) + ","
//                        //                        + (sumAttUtil[1][i] / numIterations) + ","
//                        //                        + (sumAttUtil[2][i] / numIterations) + ", ,"
//                        + prior[i] + ","
//                        + ((double) sumAttackSetSize[0][i] / numIterations) + ","
//                        + ((double) sumAttackSetSize[1][i] / numIterations) + ","
//                        + ((double) sumAttackSetSize[2][i] / numIterations) + ","
//                        + ((double) sumAttackSetSize[3][i] / numIterations) + ","
//                        + ((double) sumAttackSetSize[4][i] / numIterations) + ", ,"
//                        + prior[i] + ","
//                        + (sumEntropy[0][i] / numIterations) + ","
//                        + (sumEntropy[1][i] / numIterations) + ","
//                        + (sumEntropy[2][i] / numIterations) + ","
//                        + (sumEntropy[3][i] / numIterations) + ","
//                        + (sumEntropy[4][i] / numIterations) + ", ,"
//                        + prior[i] + ","
//                        + ((double) sumOptimalObservations[0][i] / numIterations) + ","
//                        + ((double) sumOptimalObservations[1][i] / numIterations) + ","
//                        + ((double) sumOptimalObservations[2][i] / numIterations) + ","
//                        + ((double) sumOptimalObservations[3][i] / numIterations) + "\n");

            }

            outDefUtil.flush();
            outAttUtil.flush();
            outAttackSetSize.flush();
            outEntropy.flush();
            outDefUtil.close();
            outAttUtil.close();
            outAttackSetSize.close();
            outEntropy.close();

            out.flush();
            out.close();
        } catch (IOException e) {
            throw new RuntimeException("Error writing security game to file: " + e.getMessage());
        }



//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//        int numResources = 1;
//        int numTargets = 5;
//        double discountFactor = 0.9;
//        GenerateGameFiles.alpha = 0.0;
////        int formulation = 2;
//
//        String outputFile = "outputFilePerfVaryPrior.csv";
//        String rawDefUtil = "rawDefUtilPerfVaryPrior.csv";
//        String rawAttUtil = "rawAttUtilPerfVaryPrior.csv";
//
//        int numIterations = 100;
//
////        int numTargets = 5;
////        int numObservations = 5;
////        int numResources = 1;
//
//        double[] sumDefUtil;
//        double[] sumAttUtil;
//
//        sumDefUtil = new double[2];
//        sumAttUtil = new double[2];
//
//        try {
//            PrintWriter out = new PrintWriter(new File(outputFile));
//            PrintWriter outDefUtil = new PrintWriter(new File(rawDefUtil));
//            PrintWriter outAttUtil = new PrintWriter(new File(rawAttUtil));
//
//
////            P1.GenerateAttackerStrategy(numTargets, numObservations, numResources);
////            P2Nonconvex.Solve();
////            P3Convex.Solve();
//
//
//            System.out.println("Varying Prior");
//            out.write("Vary Prior\n");
//
//            out.write("Prior, Defender Utility(P2), Defender Utility(P3), "
//                    + "Attacker Utility(P2), Attacker Utility(P3)\n");
//            // 18 targets w/ 5 observations is 23 seconds
//            for (double i = 0; i <= 3; i = i + 0.5) {
//                System.out.println(i);
//
//                GenerateGameFiles.alpha = i;
//
//                for (int x = 0; x < 2; x++) {
//                    sumDefUtil[x] = 0.0;
//                    sumAttUtil[x] = 0.0;
//                }
//
//                for (int j = 0; j < numIterations; j++) {
//                    GenerateGameFiles.GeneratePayoffs(numTargets);
//
//                    BinarySearch.Solve(2, numTargets, numResources, discountFactor);
//                    sumDefUtil[0] += BinarySearch.optimalDefenderUtil;
//                    sumAttUtil[0] += BinarySearch.optimalAttackerUtil;
//
//                    outDefUtil.write(BinarySearch.optimalDefenderUtil + ",");
//                    outAttUtil.write(BinarySearch.optimalAttackerUtil + ",");
//
//                    BinarySearch.Solve(3, numTargets, numResources, discountFactor);
//                    sumDefUtil[1] += BinarySearch.optimalDefenderUtil;
//                    sumAttUtil[1] += BinarySearch.optimalAttackerUtil;
//
//                    outDefUtil.write(BinarySearch.optimalDefenderUtil + "\n");
//                    outAttUtil.write(BinarySearch.optimalAttackerUtil + "\n");
//
//                }
//
//                outDefUtil.write("\n");
//                outAttUtil.write("\n");
//
//                out.write(i + "," + (sumDefUtil[0] / numIterations) + "," + (sumDefUtil[1] / numIterations)
//                        + "," + (sumAttUtil[0] / numIterations) + "," + (sumAttUtil[1] / numIterations) + "\n");
//
//                out.flush();
//                outDefUtil.flush();
//                outAttUtil.flush();
//
//            }
//
//            outDefUtil.flush();
//            outAttUtil.flush();
//            outDefUtil.close();
//            outAttUtil.close();
//
//            out.flush();
//            out.close();
//        } catch (IOException e) {
//            throw new RuntimeException("Error writing security game to file: " + e.getMessage());
//        }


    }

    public static void testBinarySearch() {
        int numTargets = 3;
        //int numObservations = 3;
        int numResources = 1;
        double discountFactor = 0.9;
        int formulation = 3;

        GenerateGameFiles.GenerateHardcodedPayoffs(numTargets);

        BinarySearch.Solve(formulation, numTargets, numResources, discountFactor, 0.1, 0, "P2_nonconvex_1ms.run");

    }

    public static void testAttackerUtility() {
        int numTargets = 3;
        //int numObservations = 3;
        int numResources = 1;
        double discountFactor = 0.9;


        String outputFile = "testAttackerUtility.csv";

        try {
            PrintWriter out = new PrintWriter(new File(outputFile));
            out.println("tau, defender utility, raw attacker utility, attacker utility (" + discountFactor + ")");

            for (int numObs = 0; numObs < 17; numObs++) {
                GenerateGameFiles.GenerateHardcodedPayoffs(numTargets);
                GenerateGameFiles.InitStrategiesObservations(numTargets, numObs, numResources);
                GenerateGameFiles.GeneratePrior(0.1, 0);
                P1.GenerateAttackerStrategy();

//                P2Nonconvex.Solve();
//                AttackerUtility.Solve(discountFactor, P2Nonconvex.defStrategy);
//                out.println(numObs + "," + P2Nonconvex.objective + ","
//                        + AttackerUtility.rawUtility + "," + AttackerUtility.finalUtility);


                P3Convex.Solve();
                AttackerUtility.Solve(discountFactor, P3Convex.defStrategy);
                out.println(numObs + "," + P3Convex.objective + ","
                        + AttackerUtility.rawUtility + "," + AttackerUtility.finalUtility);
//                System.out.println("defUtil: " + P2Nonconvex.objective + ", attUtilRaw: "
//                        + AttackerUtility.rawUtility + ", attUtil: " + AttackerUtility.finalUtility);
                out.flush();
            }



            out.flush();
            out.close();
        } catch (IOException e) {
            throw new RuntimeException("Error writing security game to file: " + e.getMessage());
        }
    }

    public static void compareCoveragePerTarget() {
        int numTargets = 4;
        int numResources = 1;
        int alphaOption = 0;
        double alpha = 10.0;

        int numLines = 5;
//        int xDataPoints = 19;
        int xDataPoints = 14;

        int numIterations = 100;


        double[][] sumDiffCoverage;
        int[] observations;

        sumDiffCoverage = new double[numLines][xDataPoints];
        observations = new int[xDataPoints];

        for (int x = 0; x < numLines; x++) {
            for (int i = 0; i < xDataPoints; i++) {
                sumDiffCoverage[x][i] = 0.0;
            }
        }


        for (int i = 0; i < xDataPoints; i++) {
            observations[i] = i + 2;
        }



//        String outputFile = "outputCompareCoverageClose.csv";
        String outputFile = "outputCompareCoverage.csv";
        String outRawFile = "rawDiffCompareCoverage.csv";

        try {
            PrintWriter out = new PrintWriter(new File(outputFile));
            PrintWriter outRaw = new PrintWriter(new File(outRawFile));

//            out.println("Spaced out payoffs");
//
////            out.print("Observations");
//            for (int i = 1; i <= numTargets; i++) {
//                out.print(",Target " + i + "(P1)");
//            }
//            for (int i = 1; i <= numTargets; i++) {
//                out.print(",Target " + i + "(SSE)");
//            }
//            out.print("\n");

            out.write("Compare Coverage\n");
//            out.write(",P2(1ms),P2(5ms),P2(10ms),P3,"
//                    + ",,P2(1ms),P2(5ms),P2(10ms),P3\n");
            out.write(",Target 1,Target 2,Target 3,Target 4\n");


//            GenerateGameFiles.GenerateClosePayoffs(numTargets);

            //GenerateGameFiles.GeneratePayoffs(numTargets);


            for (int i = 0; i < numIterations; i++) {
                System.out.println(i);
//                GenerateGameFiles.GeneratePayoffs(numTargets);
                GenerateGameFiles.GenerateZeroSumPayoffs(numTargets);

                for (int j = 0; j < xDataPoints; j++) {

                    GenerateGameFiles.InitStrategiesObservations(numTargets,
                            observations[j], numResources);
                    GenerateGameFiles.GeneratePrior(alpha, alphaOption);
                    P1.GenerateAttackerStrategy();
                    P2Nonconvex.Solve("P2_nonconvex_5ms.run");
//                    out.println(i + "," + j + "," + P2Nonconvex.defStrategy.get(2) + "," + count);
                    Dobss.Solve();

                    for (int k = 0; k < numTargets; k++) {
                        sumDiffCoverage[k][j] += (P2Nonconvex.defStrategy.get(k) - Dobss.defStrategy.get(k));
                        outRaw.print((P2Nonconvex.defStrategy.get(k) - Dobss.defStrategy.get(k)) + ",");
                    }

//                    out.print(i);
//                    for (double cov : P2Nonconvex.defStrategy) {
//                        out.print("," + cov);
//                    }
//                    for (double cov : Dobss.defStrategy) {
//                        out.print("," + cov);
//                    }
                    outRaw.print("\n");
                    outRaw.flush();
                }


            }


            for (int i = 0; i < xDataPoints; i++) {

                out.write(observations[i] + ","
                        + (sumDiffCoverage[0][i] / numIterations) + ","
                        + (sumDiffCoverage[1][i] / numIterations) + ","
                        + (sumDiffCoverage[2][i] / numIterations) + ","
                        + (sumDiffCoverage[3][i] / numIterations) + "\n");

            }


            outRaw.flush();
            outRaw.close();

            out.flush();
            out.close();
        } catch (IOException e) {
            throw new RuntimeException("Error writing security game to file: " + e.getMessage());
        }

    }

//    public static void compareCoveragePerTarget() {
//        int numTargets = 4;
//        int numResources = 1;
//        int alphaOption = 0;
//        double alpha = 10.0;
//
////        String outputFile = "outputCompareCoverageClose.csv";
//        String outputFile = "outputCompareCoverage.csv";
//
//        try {
//            PrintWriter out = new PrintWriter(new File(outputFile));
//
//            out.println("Spaced out payoffs");
//
////            out.print("Observations");
//            for (int i = 1; i <= numTargets; i++) {
//                out.print(",Target " + i + "(P1)");
//            }
//            for (int i = 1; i <= numTargets; i++) {
//                out.print(",Target " + i + "(SSE)");
//            }
//            out.print("\n");
//
//
//
////            GenerateGameFiles.GenerateClosePayoffs(numTargets);
//
//            //GenerateGameFiles.GeneratePayoffs(numTargets);
//
////            GenerateGameFiles.GenerateZeroSumPayoffs(numTargets);
//
//            GenerateGameFiles.GenerateSpacedPayoffs(numTargets);
//
//            for (int i = 2; i <= 20; i++) {
////            for (int i = 18; i <= 18; i++) {
//                System.out.println(i);
//
////                GenerateGameFiles.GenerateZeroSumPayoffs(i);
////                    GenerateGameFiles.GenerateConj3Payoffs(numTargets, j);
//                GenerateGameFiles.InitStrategiesObservations(numTargets, i, numResources);
//                GenerateGameFiles.GeneratePrior(alpha, alphaOption);
//                P1.GenerateAttackerStrategy();
//                P2Nonconvex.Solve("P2_nonconvex_10ms.run");
////                    out.println(i + "," + j + "," + P2Nonconvex.defStrategy.get(2) + "," + count);
//                Dobss.Solve();
//
//                out.print(i);
//                for (double cov : P2Nonconvex.defStrategy) {
//                    out.print("," + cov);
//                }
//                for (double cov : Dobss.defStrategy) {
//                    out.print("," + cov);
//                }
//                out.print("\n");
//                out.flush();
//
//            }
//
//
//
//            out.print("\n \n");
//
////            out.println("Cluster payoffs");
////
////            out.print("Observations");
////            for (int i = 1; i <= numTargets; i++) {
////                out.print(",Target " + i);
////            }
////            out.print("\n");
////
////            for (int i = 2; i <= 20; i++) {
//////            for (int i = 18; i <= 18; i++) {
////                System.out.println(i);
////
////                GenerateGameFiles.GenerateClusterPayoffs(numTargets);
//////                GenerateGameFiles.GenerateZeroSumPayoffs(i);
//////                    GenerateGameFiles.GenerateConj3Payoffs(numTargets, j);
////                P1.GenerateAttackerStrategy(numTargets, i, numResources);
////                P2Nonconvex.Solve();
//////                    out.println(i + "," + j + "," + P2Nonconvex.defStrategy.get(2) + "," + count);
////                out.print(i);
////                for (double cov : P2Nonconvex.defStrategy) {
////                    out.print("," + cov);
////                }
////                out.print("\n");
////
////            }
//
//
//
//            out.flush();
//            out.close();
//        } catch (IOException e) {
//            throw new RuntimeException("Error writing security game to file: " + e.getMessage());
//        }
//
//    }
    public static void testConjecture3() {

        int numIterations = 20000;
        int numTargets = 3;
        int numObservations = 3;
        int numResources = 1;
        String outputFile = "outputConj3.csv";

        try {
            PrintWriter out = new PrintWriter(new File(outputFile));

            out.println("Observations, payoff value, coverage for t3");

            for (int i = 3; i <= 20; i++) {
//        for (int i = 5; i <= 5; i++) {
                System.out.println(i);

                for (int j = 40; j < 80; j++) {
//                GenerateGameFiles.GenerateZeroSumPayoffs(i);
                    GenerateGameFiles.GenerateConj3Payoffs(numTargets, j);
                    GenerateGameFiles.InitStrategiesObservations(numTargets, i, numResources);
                    GenerateGameFiles.GeneratePrior(0.1, 0);
                    P1.GenerateAttackerStrategy();
                    HashSet<Integer> attackSet = new HashSet<Integer>(P1.attackList);

                    if (attackSet.contains(3)) {
                        int count = 0;
                        for (int z : P1.attackList) {
                            if (z == 3) {
                                count++;
                            }
                        }

                        P2Nonconvex.TestConj3();
                        out.println(i + "," + j + "," + P2Nonconvex.defStrategy.get(2) + "," + count);
                        break;
                    }

                }

            }
            out.flush();
            out.close();
        } catch (IOException e) {
            throw new RuntimeException("Error writing security game to file: " + e.getMessage());
        }




//        for (int i = 10; i <= 10; i++) {
////        for (int i = 5; i <= 5; i++) {
//            System.out.println(i);
//
//            for (int j = 0; j < numIterations; j++) {
////                GenerateGameFiles.GenerateZeroSumPayoffs(i);
//                GenerateGameFiles.GenerateConj3Payoffs(i, numTargets);
//                P1.GenerateAttackerStrategy(numTargets, i, numResources);
//                P2Nonconvex.TestConj3();
//
//            }
//
//        }
//
//
//        System.out.println("Varying number of observations");
//
//        for (int i = 5; i <= 17; i++) {
//            System.out.println(i);
//
//            for (int j = 0; j < numIterations; j++) {
//                GenerateGameFiles.GenerateZeroSumPayoffs(numTargets);
//                P1.GenerateAttackerStrategy(numTargets, i, numResources);
//                P2Nonconvex.TestConj3();
//
//            }
//
//        }

    }

    public static void runExperiments() {

        String scriptFilename = "belief_update_test.run";
        String scriptConvexFilename = "belief_update_test_convex.run";
        String outputFile = "outputFile.csv";
        String rawdataTime = "timeOutput.csv";
        String rawdataObjective = "objectiveOutput.csv";

        int numIterations = 250;

        int numTargets = 5;
        int numObservations = 5;
        int numResources = 1;
        int alphaOption = 0;

        double[] sumTime;
        double[] sumObjective;

        sumTime = new double[2];
        sumObjective = new double[2];

        try {
            PrintWriter out = new PrintWriter(new File(outputFile));
            PrintWriter outTime = new PrintWriter(new File(rawdataTime));
            PrintWriter outObjective = new PrintWriter(new File(rawdataObjective));


//            P1.GenerateAttackerStrategy(numTargets, numObservations, numResources);
//            P2Nonconvex.Solve();
//            P3Convex.Solve();


            System.out.println("Varying number of targets");
            out.write("Vary num Targets\n");

            // 18 targets w/ 5 observations is 23 seconds
            for (int i = 5; i <= 18; i++) {
                System.out.println(i);

                for (int x = 0; x < 2; x++) {
                    sumTime[x] = 0.0;
                    sumObjective[x] = 0.0;
                }

                for (int j = 0; j < numIterations; j++) {
                    GenerateGameFiles.GeneratePayoffs(i);
                    GenerateGameFiles.InitStrategiesObservations(i, numObservations, numResources);
                    GenerateGameFiles.GeneratePrior(0.1, alphaOption);
                    P1.GenerateAttackerStrategy();
                    P2Nonconvex.Solve();
                    P3Convex.Solve();


                    sumTime[0] = sumTime[0] + P2Nonconvex.time + P1.time;
                    sumObjective[0] += P2Nonconvex.objective;
                    sumTime[1] = sumTime[1] + P3Convex.time + P1.time;
                    sumObjective[1] += P3Convex.objective;


                    outTime.write((P2Nonconvex.time + P1.time) + "," + (P3Convex.time + P1.time) + "\n");
                    outObjective.write(P2Nonconvex.objective + "," + P3Convex.objective + "\n");

                    if ((Math.abs(P2Nonconvex.objective) > 250.0)
                            || (Math.abs(P3Convex.objective) > 250.0)) {
                        System.out.println("objective value too large. P2: " + P2Nonconvex.objective
                                + " or P3: " + P3Convex.objective);
                        System.exit(1);
                    }
                }

                outTime.write("\n");
                outObjective.write("\n");

                out.write(i + "," + (sumTime[0] / numIterations) + "," + (sumTime[1] / numIterations) + ", ,"
                        + i + "," + (sumObjective[0] / numIterations) + "," + (sumObjective[1] / numIterations) + "\n");

                out.flush();
                outTime.flush();
                outObjective.flush();

            }



            out.write("\n\n");
            outTime.write("\n\n");
            outObjective.write("\n\n");

            System.out.println("Varying number of observations");

            out.write("Vary num Observations\n");


            for (int i = 5; i <= 20; i++) {
                System.out.println(i);
                for (int x = 0; x < 2; x++) {
                    sumTime[x] = 0.0;
                    sumObjective[x] = 0.0;
                }

                for (int j = 0; j < numIterations; j++) {
                    GenerateGameFiles.GeneratePayoffs(numTargets);
                    GenerateGameFiles.InitStrategiesObservations(numTargets, i, numResources);
                    GenerateGameFiles.GeneratePrior(0.1, alphaOption);
                    P1.GenerateAttackerStrategy();
                    P2Nonconvex.Solve();
                    P3Convex.Solve();

                    sumTime[0] = sumTime[0] + P2Nonconvex.time + P1.time;
                    sumObjective[0] += P2Nonconvex.objective;
                    sumTime[1] = sumTime[1] + P3Convex.time + P1.time;
                    sumObjective[1] += P3Convex.objective;


                    outTime.write((P2Nonconvex.time + P1.time) + "," + (P3Convex.time + P1.time) + "\n");
                    outObjective.write(P2Nonconvex.objective + "," + P3Convex.objective + "\n");

                    if ((Math.abs(P2Nonconvex.objective) > 250.0)
                            || (Math.abs(P3Convex.objective) > 250.0)) {
                        System.out.println("objective value too large. P2: " + P2Nonconvex.objective
                                + " or P3: " + P3Convex.objective);
                        System.exit(1);
                    }
                }

                outTime.write("\n");
                outObjective.write("\n");

                out.write(i + "," + (sumTime[0] / numIterations) + "," + (sumTime[1] / numIterations) + ", ,"
                        + i + "," + (sumObjective[0] / numIterations) + "," + (sumObjective[1] / numIterations) + "\n");

                out.flush();
                outTime.flush();
                outObjective.flush();
            }


            //GenerateGameFiles.GenerateFiles(numTargets, numObservations, numResources);

            //RunSolver.run(scriptFilename);
            //ReadOutput.run();



            outTime.flush();
            outObjective.flush();
            outTime.close();
            outObjective.close();

            out.flush();
            out.close();
        } catch (IOException e) {
            throw new RuntimeException("Error writing security game to file: " + e.getMessage());
        }
    }

    public static void testPerformanceVaryObservation() {

        int numResources = 1;
        double discountFactor = 0.95;
//        int formulation = 2;
        double maxAlpha = 10.0;
        int alphaOption = 0;

        int numIterations = 200;


//        int numLines = 5;
        int numTargets = 5;

        String outputFile = "outputFilePerfVaryObservation.csv";
        String rawDefUtil = "rawDefUtilPerfVaryObservation.csv";
//        String rawAttUtil = "rawAttUtilPerfVaryObservation.csv";
        String rawAttackSetSize = "rawAttackSetSizePerfVaryObservation.csv";
        String rawEntropy = "rawEntropyPerfVaryObservation.csv";


        int numLines = 6;
        int xDataPoints = 8;


        double[][] sumDefUtil;
        double[][] sumAttUtil;
        int[][] sumAttackSetSize;
        double[][] sumEntropy;
        int[][] sumOptimalObservations;
        double[][] sumTime;
        double[][] sumObjective;


        int observations[];

        sumDefUtil = new double[numLines][xDataPoints];
        sumAttUtil = new double[numLines][xDataPoints];
        sumAttackSetSize = new int[numLines][xDataPoints];
        sumEntropy = new double[numLines][xDataPoints];
        sumOptimalObservations = new int[numLines][xDataPoints];
        sumTime = new double[numLines][xDataPoints];
        sumObjective = new double[numLines][xDataPoints];
        observations = new int[xDataPoints];


        System.out.println("Varying number of observations");

        try {
            PrintWriter out = new PrintWriter(new File(outputFile));
            PrintWriter outDefUtil = new PrintWriter(new File(rawDefUtil));
//            PrintWriter outAttUtil = new PrintWriter(new File(rawAttUtil));
            PrintWriter outAttackSetSize = new PrintWriter(new File(rawAttackSetSize));
            PrintWriter outEntropy = new PrintWriter(new File(rawEntropy));


            out.write("Vary num Observations\n");
//            out.write(",P2(1ms),P2(5ms),P2(10ms),P3,"
//                    + ",,P2(1ms),P2(5ms),P2(10ms),P3\n");
            out.write(",P1(Uniform),SSE(Uniform),P1(SSE),SSE(SSE),P1(Hybrid),SSE(Hybrid),"
                    + ",,P2(Uniform),SSE(Uniform),P2(SSE),SSE(SSE),"
                    + ",,P2(Uniform),SSE(Uniform),P2(SSE),SSE(SSE)\n");


            for (int x = 0; x < numLines; x++) {
                for (int i = 0; i < xDataPoints; i++) {
                    sumTime[x][i] = 0.0;
                    sumObjective[x][i] = 0;
                    sumDefUtil[x][i] = 0.0;
                    sumAttUtil[x][i] = 0.0;
                    sumAttackSetSize[x][i] = 0;
                    sumEntropy[x][i] = 0.0;
                    sumOptimalObservations[x][i] = 0;
                }
            }


            for (int i = 0; i < xDataPoints; i++) {
                observations[i] = i + 5;
            }


            for (int i = 0; i < numIterations; i++) {
                System.out.println(i);
//                GenerateGameFiles.GeneratePayoffs(numTargets);
                GenerateGameFiles.GeneratePositiveNegativePayoffs(numTargets);

                for (int j = 0; j < xDataPoints; j++) {

//                GenerateGameFiles.GeneratePayoffs(numTargets);
                    alphaOption = 0;
                    GenerateGameFiles.InitStrategiesObservations(numTargets,
                            observations[j], numResources);
                    GenerateGameFiles.GeneratePrior(maxAlpha, alphaOption);
                    P1.GenerateAttackerStrategy();

                    P2Nonconvex.Solve("P2_nonconvex_1ms.run");
                    sumDefUtil[0][j] += P2Nonconvex.objective;
                    sumAttackSetSize[0][j] += P2Nonconvex.attackSetSize;
                    sumEntropy[0][j] += P2Nonconvex.entropy;

                    Dobss.Solve();
                    sumDefUtil[1][j] += Dobss.GetUpdatedDefUtil();
                    sumAttackSetSize[1][j] += Dobss.attackSetSize;
                    sumEntropy[1][j] += Dobss.entropy;

                    outDefUtil.write(P2Nonconvex.objective + ","
                            + Dobss.GetUpdatedDefUtil() + ",");


                    alphaOption = 1;
                    GenerateGameFiles.InitStrategiesObservations(numTargets,
                            observations[j], numResources);
                    GenerateGameFiles.GeneratePrior(maxAlpha, alphaOption);
                    P1.GenerateAttackerStrategy();

                    P2Nonconvex.Solve("P2_nonconvex_1ms.run");
                    sumDefUtil[2][j] += P2Nonconvex.objective;
                    sumAttackSetSize[2][j] += P2Nonconvex.attackSetSize;
                    sumEntropy[2][j] += P2Nonconvex.entropy;

                    Dobss.Solve();
                    sumDefUtil[3][j] += Dobss.GetUpdatedDefUtil();
                    sumAttackSetSize[3][j] += Dobss.attackSetSize;
                    sumEntropy[3][j] += Dobss.entropy;

                    outDefUtil.write(P2Nonconvex.objective + ","
                            + Dobss.GetUpdatedDefUtil() + ",");


                    alphaOption = 2;
                    GenerateGameFiles.InitStrategiesObservations(numTargets,
                            observations[j], numResources);
                    GenerateGameFiles.GeneratePrior(maxAlpha, alphaOption);
                    P1.GenerateAttackerStrategy();

                    P2Nonconvex.Solve("P2_nonconvex_1ms.run");
                    sumDefUtil[4][j] += P2Nonconvex.objective;
                    sumAttackSetSize[4][j] += P2Nonconvex.attackSetSize;
                    sumEntropy[4][j] += P2Nonconvex.entropy;

                    Dobss.Solve();
                    sumDefUtil[5][j] += Dobss.GetUpdatedDefUtil();
                    sumAttackSetSize[5][j] += Dobss.attackSetSize;
                    sumEntropy[5][j] += Dobss.entropy;


                    outDefUtil.write(P2Nonconvex.objective + ","
                            + Dobss.GetUpdatedDefUtil() + "\n");

                    outDefUtil.flush();



                    //                    sumTime[0][j] = sumTime[0][j] + P2Nonconvex.time + P1.time;
//                    sumObjective[0][j] += P2Nonconvex.objective;

//                    P2Nonconvex.Solve("P2_nonconvex_5ms.run");
//                    sumTime[1][j] = sumTime[1][j] + P2Nonconvex.time + P1.time;
//                    sumObjective[1][j] += P2Nonconvex.objective;
//
//                    P2Nonconvex.Solve("P2_nonconvex_10ms.run");
//                    sumTime[2][j] = sumTime[2][j] + P2Nonconvex.time + P1.time;
//                    sumObjective[2][j] += P2Nonconvex.objective;
//
//
//                    P3Convex.Solve();
//
//                    sumTime[3][j] = sumTime[3][j] + P3Convex.time + P1.time;
//                    sumObjective[3][j] += P3Convex.objective;

//                    sumTime[0] = sumTime[0] + P2Nonconvex.time + P1.time;
//                    sumObjective[0] += P2Nonconvex.objective;
//                    sumTime[1] = sumTime[1] + P3Convex.time + P1.time;
//                    sumObjective[1] += P3Convex.objective;


//                    outTime.write((P2Nonconvex.time + P1.time) + "," + (P3Convex.time + P1.time) + "\n");
//                    outObjective.write(P2Nonconvex.objective + "," + P3Convex.objective + "\n");
//                    outTime.flush();
//                    outObjective.flush();

                }

//                outTime.write("\n");
//                outObjective.write("\n");


//                out.flush();
//                outTime.flush();
//                outObjective.flush();
            }


            for (int i = 0; i < xDataPoints; i++) {

                out.write(observations[i] + ","
                        + (sumDefUtil[0][i] / numIterations) + ","
                        + (sumDefUtil[1][i] / numIterations) + ","
                        + (sumDefUtil[2][i] / numIterations) + ","
                        + (sumDefUtil[3][i] / numIterations) + ","
                        + (sumDefUtil[4][i] / numIterations) + ","
                        + (sumDefUtil[5][i] / numIterations) + ", ,"
                        //                        + (sumDefUtil[2][i] / numIterations) + ", "
                        //                        + (sumAttUtil[0][i] / numIterations) + ","
                        //                        + (sumAttUtil[1][i] / numIterations) + ","
                        //                        + (sumAttUtil[2][i] / numIterations) + ", ,"
                        + observations[i] + ","
                        + ((double) sumAttackSetSize[0][i] / numIterations) + ","
                        + ((double) sumAttackSetSize[1][i] / numIterations) + ","
                        + ((double) sumAttackSetSize[2][i] / numIterations) + ","
                        //                        + ((double) sumAttackSetSize[1][i] / numIterations) + ","
                        + ((double) sumAttackSetSize[3][i] / numIterations) + ", ,"
                        + observations[i] + ","
                        + (sumEntropy[0][i] / numIterations) + ","
                        + (sumEntropy[1][i] / numIterations) + ","
                        + (sumEntropy[2][i] / numIterations) + ","
                        //                        + (sumEntropy[1][i] / numIterations) + ","
                        + (sumEntropy[3][i] / numIterations) + "\n");


//                out.write(observations[i] + ","
//                        + (sumTime[0][i] / numIterations) + ","
//                        + (sumTime[1][i] / numIterations) + ","
//                        + (sumTime[2][i] / numIterations) + ","
//                        + (sumTime[3][i] / numIterations) + ", ,"
//                        + observations[i] + ","
//                        + (sumObjective[0][i] / numIterations) + ","
//                        + (sumObjective[1][i] / numIterations) + ","
//                        + (sumObjective[2][i] / numIterations) + ","
//                        + (sumObjective[3][i] / numIterations) + "\n");

            }

            outDefUtil.flush();
//            outAttUtil.flush();
            outAttackSetSize.flush();
            outEntropy.flush();
            outDefUtil.close();
//            outAttUtil.close();
            outAttackSetSize.close();
            outEntropy.close();

            out.flush();
            out.close();
        } catch (IOException e) {
            throw new RuntimeException("Error writing security game to file: " + e.getMessage());
        }

    }

    public static void testPerformanceVaryResources() {

//        int numResources = 1;
        double discountFactor = 1.0;
//        int formulation = 2;
        double maxAlpha = 10.0;
        int alphaOption = 0;

        int numIterations = 100;


//        int numLines = 5;

        int numObservations = 5;
        int numTargets = 5;

        String outputFile = "outputFilePerfVaryResources.csv";
        String rawDefUtil = "rawDefUtilPerfVaryResources.csv";
//        String rawAttUtil = "rawAttUtilPerfVaryObservation.csv";
        String rawAttackSetSize = "rawAttackSetSizePerfVaryResources.csv";
        String rawEntropy = "rawEntropyPerfVaryResources.csv";


        int numLines = 5;
        int xDataPoints = 4;


        double[][] sumDefUtil;
        double[][] sumAttUtil;
        int[][] sumAttackSetSize;
        double[][] sumEntropy;
        int[][] sumOptimalObservations;
        double[][] sumTime;
        double[][] sumObjective;


        int resources[];

        sumDefUtil = new double[numLines][xDataPoints];
        sumAttUtil = new double[numLines][xDataPoints];
        sumAttackSetSize = new int[numLines][xDataPoints];
        sumEntropy = new double[numLines][xDataPoints];
        sumOptimalObservations = new int[numLines][xDataPoints];
        sumTime = new double[numLines][xDataPoints];
        sumObjective = new double[numLines][xDataPoints];
        resources = new int[xDataPoints];


        System.out.println("Varying number of resources");

        try {
            PrintWriter out = new PrintWriter(new File(outputFile));
            PrintWriter outDefUtil = new PrintWriter(new File(rawDefUtil));
//            PrintWriter outAttUtil = new PrintWriter(new File(rawAttUtil));
            PrintWriter outAttackSetSize = new PrintWriter(new File(rawAttackSetSize));
            PrintWriter outEntropy = new PrintWriter(new File(rawEntropy));


            out.write("Vary num Resources\n");
//            out.write(",P2(1ms),P2(5ms),P2(10ms),P3,"
//                    + ",,P2(1ms),P2(5ms),P2(10ms),P3\n");
            out.write(",P1(Uniform),SSE(Uniform),P1(SSE),SSE(SSE),"
                    + ",,P1(Uniform),SSE(Uniform),P1(SSE),SSE(SSE),"
                    + ",,P2(Uniform),SSE(Uniform),P2(SSE),SSE(SSE)\n");


            for (int x = 0; x < numLines; x++) {
                for (int i = 0; i < xDataPoints; i++) {
                    sumTime[x][i] = 0.0;
                    sumObjective[x][i] = 0;
                    sumDefUtil[x][i] = 0.0;
                    sumAttUtil[x][i] = 0.0;
                    sumAttackSetSize[x][i] = 0;
                    sumEntropy[x][i] = 0.0;
                    sumOptimalObservations[x][i] = 0;
                }
            }


            for (int i = 0; i < xDataPoints; i++) {
                resources[i] = i + 1;
            }


            for (int i = 0; i < numIterations; i++) {
                System.out.println(i);
//                GenerateGameFiles.GeneratePayoffs(numTargets);
                GenerateGameFiles.GeneratePositiveNegativePayoffs(numTargets);

                for (int j = 0; j < xDataPoints; j++) {

//                GenerateGameFiles.GeneratePayoffs(numTargets);
                    alphaOption = 0;
//                    GenerateGameFiles.InitStrategiesObservations(numTargets,
//                            numObservations, resources[j]);
//                    GenerateGameFiles.GeneratePrior(maxAlpha, alphaOption);
//                    P1.GenerateAttackerStrategy();

                    BinarySearch.Solve(2, numTargets, resources[j], discountFactor,
                            maxAlpha, alphaOption, "P2_nonconvex_1ms.run");

                    System.out.println("optimal observations: " + BinarySearch.optimalObservations);
                    sumDefUtil[0][j] += BinarySearch.optimalDefenderUtil;
                    sumAttackSetSize[0][j] += P2Nonconvex.attackSetSize;
                    sumEntropy[0][j] += P2Nonconvex.entropy;


                    Dobss.Solve();
                    sumDefUtil[1][j] += Dobss.GetUpdatedDefUtil();
                    sumAttackSetSize[1][j] += Dobss.attackSetSize;
                    sumEntropy[1][j] += Dobss.entropy;

//                    P3Convex.Solve();
//                    sumDefUtil[1][j] += P3Convex.objective;
//                    sumAttackSetSize[1][j] += P3Convex.attackSetSize;
//                    sumEntropy[1][j] += P3Convex.entropy;
//                    Dobss.Solve();
//                    sumDefUtil[1][j] += Dobss.GetUpdatedDefUtil();
//                    sumAttackSetSize[1][j] += Dobss.attackSetSize;
//                    sumEntropy[1][j] += Dobss.entropy;

                    outDefUtil.write(P2Nonconvex.objective + ","
                            + Dobss.GetUpdatedDefUtil() + ",");


                    alphaOption = 1;
                    BinarySearch.Solve(2, numTargets, resources[j], discountFactor,
                            maxAlpha, alphaOption, "P2_nonconvex_1ms.run");

                    sumDefUtil[2][j] += BinarySearch.optimalDefenderUtil;
                    sumAttackSetSize[2][j] += P2Nonconvex.attackSetSize;
                    sumEntropy[2][j] += P2Nonconvex.entropy;
//                    GenerateGameFiles.InitStrategiesObservations(numTargets,
//                            numObservations, resources[j]);
//                    GenerateGameFiles.GeneratePrior(maxAlpha, alphaOption);
//                    P1.GenerateAttackerStrategy();
//
//                    P2Nonconvex.Solve("P2_nonconvex_1ms.run");
//                    sumDefUtil[2][j] += P2Nonconvex.objective;


//                    P3Convex.Solve();
//                    sumDefUtil[3][j] += P3Convex.objective;
//                    sumAttackSetSize[3][j] += P3Convex.attackSetSize;
//                    sumEntropy[3][j] += P3Convex.entropy;


                    Dobss.Solve();
                    sumDefUtil[3][j] += Dobss.GetUpdatedDefUtil();
                    sumAttackSetSize[3][j] += Dobss.attackSetSize;
                    sumEntropy[3][j] += Dobss.entropy;




                    outDefUtil.write(P2Nonconvex.objective + ","
                            + Dobss.GetUpdatedDefUtil() + "\n");

                    outDefUtil.flush();



                }

            }


            for (int i = 0; i < xDataPoints; i++) {

                out.write(resources[i] + ","
                        + (sumDefUtil[0][i] / numIterations) + ","
                        + (sumDefUtil[1][i] / numIterations) + ","
                        + (sumDefUtil[2][i] / numIterations) + ","
                        + (sumDefUtil[3][i] / numIterations) + ", ,"
                        //                        + (sumDefUtil[2][i] / numIterations) + ", "
                        //                        + (sumAttUtil[0][i] / numIterations) + ","
                        //                        + (sumAttUtil[1][i] / numIterations) + ","
                        //                        + (sumAttUtil[2][i] / numIterations) + ", ,"
                        + resources[i] + ","
                        + ((double) sumAttackSetSize[0][i] / numIterations) + ","
                        + ((double) sumAttackSetSize[1][i] / numIterations) + ","
                        + ((double) sumAttackSetSize[2][i] / numIterations) + ","
                        //                        + ((double) sumAttackSetSize[1][i] / numIterations) + ","
                        + ((double) sumAttackSetSize[3][i] / numIterations) + ", ,"
                        + resources[i] + ","
                        + (sumEntropy[0][i] / numIterations) + ","
                        + (sumEntropy[1][i] / numIterations) + ","
                        + (sumEntropy[2][i] / numIterations) + ","
                        //                        + (sumEntropy[1][i] / numIterations) + ","
                        + (sumEntropy[3][i] / numIterations) + "\n");


            }

            outDefUtil.flush();
//            outAttUtil.flush();
            outAttackSetSize.flush();
            outEntropy.flush();
            outDefUtil.close();
//            outAttUtil.close();
            outAttackSetSize.close();
            outEntropy.close();

            out.flush();
            out.close();
        } catch (IOException e) {
            throw new RuntimeException("Error writing security game to file: " + e.getMessage());
        }




    }

    public static void testRuntimeVaryObservations(PrintWriter out,
            PrintWriter outTime, PrintWriter outObjective, int numTargets,
            int numResources, double maxAlpha, int alphaOption, int numIterations) {

        double[][] sumTime;
        double[][] sumObjective;
        int xDataPoints = 8;
        int numLines = 4;
        int observations[];

        System.out.println("Varying number of observations");

        out.write("Vary num Observations\n");
        out.write(",P2(1ms),P2(5ms),P2(10ms),P3,"
                + ",,P2(1ms),P2(5ms),P2(10ms),P3\n");

        sumTime = new double[numLines][xDataPoints];
        sumObjective = new double[numLines][xDataPoints];
        observations = new int[xDataPoints];

        for (int x = 0; x < numLines; x++) {
            for (int i = 0; i < xDataPoints; i++) {
                sumTime[x][i] = 0.0;
                sumObjective[x][i] = 0;
            }
        }


        for (int i = 0; i < xDataPoints; i++) {
            observations[i] = i + 5;
        }


        for (int i = 0; i < numIterations; i++) {
            System.out.println(i);
            GenerateGameFiles.GeneratePayoffs(numTargets);

            for (int j = 0; j < xDataPoints; j++) {

//                GenerateGameFiles.GeneratePayoffs(numTargets);
                GenerateGameFiles.InitStrategiesObservations(numTargets, observations[j], numResources);
                GenerateGameFiles.GeneratePrior(maxAlpha, alphaOption);
                P1.GenerateAttackerStrategy();

                P2Nonconvex.Solve("P2_nonconvex_1ms.run");
                sumTime[0][j] = sumTime[0][j] + P2Nonconvex.time + P1.time;
                sumObjective[0][j] += P2Nonconvex.objective;

                P2Nonconvex.Solve("P2_nonconvex_5ms.run");
                sumTime[1][j] = sumTime[1][j] + P2Nonconvex.time + P1.time;
                sumObjective[1][j] += P2Nonconvex.objective;

                P2Nonconvex.Solve("P2_nonconvex_10ms.run");
                sumTime[2][j] = sumTime[2][j] + P2Nonconvex.time + P1.time;
                sumObjective[2][j] += P2Nonconvex.objective;


                P3Convex.Solve();

                sumTime[3][j] = sumTime[3][j] + P3Convex.time + P1.time;
                sumObjective[3][j] += P3Convex.objective;

//                    sumTime[0] = sumTime[0] + P2Nonconvex.time + P1.time;
//                    sumObjective[0] += P2Nonconvex.objective;
//                    sumTime[1] = sumTime[1] + P3Convex.time + P1.time;
//                    sumObjective[1] += P3Convex.objective;


                outTime.write((P2Nonconvex.time + P1.time) + "," + (P3Convex.time + P1.time) + "\n");
                outObjective.write(P2Nonconvex.objective + "," + P3Convex.objective + "\n");
                outTime.flush();
                outObjective.flush();

            }

            outTime.write("\n");
            outObjective.write("\n");


            out.flush();
            outTime.flush();
            outObjective.flush();
        }

        for (int i = 0; i < xDataPoints; i++) {

            out.write(observations[i] + ","
                    + (sumTime[0][i] / numIterations) + ","
                    + (sumTime[1][i] / numIterations) + ","
                    + (sumTime[2][i] / numIterations) + ","
                    + (sumTime[3][i] / numIterations) + ", ,"
                    + observations[i] + ","
                    + (sumObjective[0][i] / numIterations) + ","
                    + (sumObjective[1][i] / numIterations) + ","
                    + (sumObjective[2][i] / numIterations) + ","
                    + (sumObjective[3][i] / numIterations) + "\n");

        }

    }

    public static void testAttackUtility() {

        String outputFile = "outputTestAttackerUtility.csv";
//        String rawdataTime = "timeOutput.csv";
        String rawdataObjective = "objectiveOutput.csv";

        int numIterations = 1;

        int numTargets = 5;
        int numObservations = 5;
        int numResources = 1;
        int numLines = 6;
        double maxAlpha = 10.0;
        int alphaOption = 0;

        try {
            PrintWriter out = new PrintWriter(new File(outputFile));
//            PrintWriter outTime = new PrintWriter(new File(rawdataTime));
            PrintWriter outObjective = new PrintWriter(new File(rawdataObjective));


//            double[][] sumTime;
            double[][] sumObjective;
            int xDataPoints = 11;
            int observations[];

            System.out.println("Varying number of observations");

            out.write("Vary num Observations\n");
            out.write(",0.1,0.5,1.0,2.0,5.0,10.0\n");

//            sumTime = new double[numLines][xDataPoints];
            sumObjective = new double[numLines][xDataPoints];
            observations = new int[xDataPoints];

            for (int x = 0; x < numLines; x++) {
                for (int i = 0; i < xDataPoints; i++) {
//                    sumTime[x][i] = 0.0;
                    sumObjective[x][i] = 0;
                }
            }


            for (int i = 0; i < xDataPoints; i++) {
//                observations[i] = i + 1;
                observations[i] = i;
            }


            for (int i = 0; i < numIterations; i++) {
                System.out.println(i);
//                GenerateGameFiles.GeneratePayoffs(numTargets);
//                GenerateGameFiles.GenerateZeroSumPayoffs(numTargets);

                GenerateGameFiles.GeneratePositiveNegativePayoffs(numTargets);

//                GenerateGameFiles.GenerateSampleHardcodedPayoffs(numTargets);
                for (int j = 0; j < xDataPoints; j++) {

//                GenerateGameFiles.GeneratePayoffs(numTargets);
                    GenerateGameFiles.InitStrategiesObservations(numTargets, observations[j], numResources);
                    GenerateGameFiles.GeneratePrior(maxAlpha, alphaOption);
                    P1.GenerateAttackerStrategy();
                    P2Nonconvex.Solve("P2_nonconvex_10ms.run");
                    AttackerUtility.Solve(0.1, P2Nonconvex.defStrategy);
//                    sumTime[0][j] = sumTime[0][j] + P2Nonconvex.time + P1.time;
                    sumObjective[0][j] += AttackerUtility.finalUtility;

                    AttackerUtility.Solve(0.5, P2Nonconvex.defStrategy);
                    sumObjective[1][j] += AttackerUtility.finalUtility;

                    AttackerUtility.Solve(1.0, P2Nonconvex.defStrategy);
                    sumObjective[2][j] += AttackerUtility.finalUtility;

                    AttackerUtility.Solve(2.0, P2Nonconvex.defStrategy);
                    sumObjective[3][j] += AttackerUtility.finalUtility;

                    AttackerUtility.Solve(5.0, P2Nonconvex.defStrategy);
                    sumObjective[4][j] += AttackerUtility.finalUtility;

                    AttackerUtility.Solve(10.0, P2Nonconvex.defStrategy);
                    sumObjective[5][j] += AttackerUtility.finalUtility;


//                    outTime.write((P2Nonconvex.time + P1.time) + "," + (P3Convex.time + P1.time) + "\n");
                    outObjective.write(AttackerUtility.finalUtility + "\n");

                    System.out.println(P2Nonconvex.defStrategy);
                    System.out.println(P2Nonconvex.objective);
//                    outTime.flush();
                    outObjective.flush();

                }

//                outTime.write("\n");
                outObjective.write("\n");


                out.flush();
//                outTime.flush();
                outObjective.flush();
            }

            for (int i = 0; i < xDataPoints; i++) {

                out.write(observations[i] + ","
                        //                        + (sumTime[0][i] / numIterations) + ","
                        //                        + (sumTime[1][i] / numIterations) + ","
                        //                        + (sumTime[2][i] / numIterations) + ","
                        //                        + (sumTime[3][i] / numIterations) + ", ,"
                        //                        + observations[i] + ","
                        + (sumObjective[0][i] / numIterations) + ","
                        + (sumObjective[1][i] / numIterations) + ","
                        + (sumObjective[2][i] / numIterations) + ","
                        + (sumObjective[3][i] / numIterations) + ","
                        + (sumObjective[4][i] / numIterations) + ","
                        + (sumObjective[5][i] / numIterations) + "\n");

            }


//            testRuntimeVaryObservations(out, outTime, outObjective, numTargets,
//                    numResources, maxAlpha, alphaOption, numIterations);

            // call function

//            outTime.flush();
            outObjective.flush();
//            outTime.close();
            outObjective.close();

            out.flush();
            out.close();
        } catch (IOException e) {
            throw new RuntimeException("Error writing security game to file: " + e.getMessage());
        }
    }

    public static void testRuntime() {

        String outputFile = "outputTestRuntimeFile.csv";
        String rawdataTime = "timeOutput.csv";
        String rawdataObjective = "objectiveOutput.csv";

        int numIterations = 200;

        int numTargets = 5;
        int numObservations = 5;
        int numResources = 1;
        int numLines = 4;
        double maxAlpha = 10.0;
        int alphaOption = 0;

        int maxTargets = 15;


        double[] sumTime;
        double[] sumObjective;

        sumTime = new double[numLines];
        sumObjective = new double[numLines];

        try {
            PrintWriter out = new PrintWriter(new File(outputFile));
            PrintWriter outTime = new PrintWriter(new File(rawdataTime));
            PrintWriter outObjective = new PrintWriter(new File(rawdataObjective));


//            P1.GenerateAttackerStrategy(numTargets, numObservations, numResources);
//            P2Nonconvex.Solve();
//            P3Convex.Solve();


            System.out.println("Varying number of targets");
            out.write("Vary num Targets\n");
            out.write(",P2,P3,"
                    + ",,P2,P3\n");


            int payoffMagnitude = 100;

            Random generator;
            generator = new Random();

            ArrayList<ArrayList<Double>> defCovList = new ArrayList<ArrayList<Double>>();
            ArrayList<ArrayList<Double>> defUncovList = new ArrayList<ArrayList<Double>>();
            ArrayList<ArrayList<Double>> attCovList = new ArrayList<ArrayList<Double>>();
            ArrayList<ArrayList<Double>> attUncovList = new ArrayList<ArrayList<Double>>();

            for (int x = 0; x < numIterations; x++) {
                ArrayList<Double> defCov = new ArrayList<Double>();
                ArrayList<Double> defUncov = new ArrayList<Double>();
                ArrayList<Double> attCov = new ArrayList<Double>();
                ArrayList<Double> attUncov = new ArrayList<Double>();

                for (int i = 1; i <= maxTargets; i++) {
                    defCov.add(generator.nextInt(payoffMagnitude) + 101.0);
                    defUncov.add((double) generator.nextInt(payoffMagnitude));

                    attCov.add((double) generator.nextInt(payoffMagnitude));
                    attUncov.add(generator.nextInt(payoffMagnitude) + 101.0);
                }
                defCovList.add(defCov);
                defUncovList.add(defUncov);
                attCovList.add(attCov);
                attUncovList.add(attUncov);
            }


//            // 18 targets w/ 5 observations is 23 seconds
//            for (int i = 5; i <= maxTargets; i++) {
//                System.out.println(i);
//
//                for (int x = 0; x < numLines; x++) {
//                    sumTime[x] = 0.0;
//                    sumObjective[x] = 0.0;
//                }
//
//                for (int j = 0; j < numIterations; j++) {
////                    GenerateGameFiles.GeneratePayoffs(i);
//                    GenerateGameFiles.SetPayoffsVaryTargets(i, defCovList.get(j),
//                            defUncovList.get(j), attCovList.get(j), attUncovList.get(j));
//                    GenerateGameFiles.InitStrategiesObservations(i, numObservations, numResources);
//                    GenerateGameFiles.GeneratePrior(maxAlpha, alphaOption);
//                    P1.GenerateAttackerStrategy();
//
//                    P2Nonconvex.Solve("P2_nonconvex_1ms.run");
//                    sumTime[0] = sumTime[0] + P2Nonconvex.time + P1.time;
//                    sumObjective[0] += P2Nonconvex.objective;
//
////                    P2Nonconvex.Solve("P2_nonconvex_5ms.run");
////                    sumTime[1] = sumTime[1] + P2Nonconvex.time + P1.time;
////                    sumObjective[1] += P2Nonconvex.objective;
////
////                    P2Nonconvex.Solve("P2_nonconvex_10ms.run");
////                    sumTime[2] = sumTime[2] + P2Nonconvex.time + P1.time;
////                    sumObjective[2] += P2Nonconvex.objective;
//
//
//                    P3Convex.Solve();
//
//                    sumTime[1] = sumTime[1] + P3Convex.time + P1.time;
//                    sumObjective[1] += P3Convex.objective;
//
//
//                    outTime.write((P2Nonconvex.time + P1.time)
//                            + "," + (P3Convex.time + P1.time)
//                            + "\n");
//                    outObjective.write(P2Nonconvex.objective
//                            + "," + P3Convex.objective
//                            + "\n");
//
//                    outTime.flush();
//                    outObjective.flush();
//
//                }
//
//                outTime.write("\n");
//                outObjective.write("\n");
//
//                out.write(i + ","
//                        + (sumTime[0] / numIterations) + ","
//                        //                        + (sumTime[1] / numIterations) + ","
//                        //                        + (sumTime[2] / numIterations) + ","
//                        + (sumTime[1] / numIterations) + ", ,"
//                        + i + ","
//                        + (sumObjective[0] / numIterations) + ","
//                        //                        + (sumObjective[1] / numIterations) + ","
//                        //                        + (sumObjective[2] / numIterations) + ","
//                        + (sumObjective[1] / numIterations) + "\n");
//
//                out.flush();
//                outTime.flush();
//                outObjective.flush();
//
//            }
//
//            out.write("\n\n");
//            outTime.write("\n\n");
//            outObjective.write("\n\n");

            testRuntimeVaryObservations(out, outTime, outObjective, numTargets,
                    numResources, maxAlpha, alphaOption, numIterations);

            // call function

            outTime.flush();
            outObjective.flush();
            outTime.close();
            outObjective.close();

            out.flush();
            out.close();
        } catch (IOException e) {
            throw new RuntimeException("Error writing security game to file: " + e.getMessage());
        }
    }

    public static void testRuntimeP2Speedup() {

        String outputFile = "outputTestRuntimeFileP2Speedup.csv";
        String rawdataTime = "timeOutput.csv";
        String rawdataObjective = "objectiveOutput.csv";

        int numIterations = 20;

        int numTargets = 5;
        int numObservations = 5;
        int numResources = 1;
        int numLines = 4;
        double maxAlpha = 10.0;
        int alphaOption = 0;

        int maxTargets = 15;


        double[] sumTime;
        double[] sumObjective;

        sumTime = new double[numLines];
        sumObjective = new double[numLines];

        try {
            PrintWriter out = new PrintWriter(new File(outputFile));
            PrintWriter outTime = new PrintWriter(new File(rawdataTime));
            PrintWriter outObjective = new PrintWriter(new File(rawdataObjective));


//            P1.GenerateAttackerStrategy(numTargets, numObservations, numResources);
//            P2Nonconvex.Solve();
//            P3Convex.Solve();


            System.out.println("Varying number of targets");
            out.write("Vary num Targets\n");
            out.write(",P2,P3,"
                    + ",,P2,P3\n");


            int payoffMagnitude = 100;

            Random generator;
            generator = new Random();

            ArrayList<ArrayList<Double>> defCovList = new ArrayList<ArrayList<Double>>();
            ArrayList<ArrayList<Double>> defUncovList = new ArrayList<ArrayList<Double>>();
            ArrayList<ArrayList<Double>> attCovList = new ArrayList<ArrayList<Double>>();
            ArrayList<ArrayList<Double>> attUncovList = new ArrayList<ArrayList<Double>>();



//        for (int i = 1; i <= numTargets; i++) {
////            defCov.add(generator.nextInt(payoffMagnitude) + 1);
//            defCov.add(0.0);
//            defUncov.add(-generator.nextInt(payoffMagnitude) - 1.0);
//        }
//
//        Collections.sort(defUncov);
//
//        // Zero sum game
//        for (int i = 0; i < numTargets; i++) {
//            attCov.add(-defCov.get(i));
//            attUncov.add(-defUncov.get(i));
//        }
//

            for (int x = 0; x < numIterations; x++) {
                ArrayList<Double> defCov = new ArrayList<Double>();
                ArrayList<Double> defUncov = new ArrayList<Double>();
                ArrayList<Double> attCov = new ArrayList<Double>();
                ArrayList<Double> attUncov = new ArrayList<Double>();

                for (int i = 1; i <= maxTargets; i++) {
                    defCov.add(0.0);
                    defUncov.add(-generator.nextInt(payoffMagnitude) - 1.0);
                }
                Collections.sort(defUncov);
                for (int i = 0; i < maxTargets; i++) {
                    attCov.add(-defCov.get(i));
                    attUncov.add(-defUncov.get(i));
                }


                defCovList.add(defCov);
                defUncovList.add(defUncov);
                attCovList.add(attCov);
                attUncovList.add(attUncov);
            }


            // 18 targets w/ 5 observations is 23 seconds
            for (int i = 5; i <= maxTargets; i++) {
                System.out.println(i);

                for (int x = 0; x < numLines; x++) {
                    sumTime[x] = 0.0;
                    sumObjective[x] = 0.0;
                }

                for (int j = 0; j < numIterations; j++) {
//                    GenerateGameFiles.GeneratePayoffs(i);
                    GenerateGameFiles.SetPayoffsVaryTargets(i, defCovList.get(j),
                            defUncovList.get(j), attCovList.get(j), attUncovList.get(j));
                    GenerateGameFiles.InitStrategiesObservations(i, numObservations, numResources);
                    GenerateGameFiles.GeneratePrior(maxAlpha, alphaOption);
                    P1.GenerateAttackerStrategy();

                    P2Nonconvex.Solve("P2_nonconvex_1ms.run");
                    sumTime[0] = sumTime[0] + P2Nonconvex.time + P1.time;
                    sumObjective[0] += P2Nonconvex.objective;

//                    P2Nonconvex.Solve("P2_nonconvex_5ms.run");
//                    sumTime[1] = sumTime[1] + P2Nonconvex.time + P1.time;
//                    sumObjective[1] += P2Nonconvex.objective;
//
//                    P2Nonconvex.Solve("P2_nonconvex_10ms.run");
//                    sumTime[2] = sumTime[2] + P2Nonconvex.time + P1.time;
//                    sumObjective[2] += P2Nonconvex.objective;


                    P2Nonconvex.SolveSpeedup("P2_nonconvex_speedup.run");

                    sumTime[1] = sumTime[1] + P2Nonconvex.time + P1.time;
                    sumObjective[1] += P2Nonconvex.objective;


                    outTime.write((P2Nonconvex.time + P1.time)
                            + "," + (P3Convex.time + P1.time)
                            + "\n");
                    outObjective.write(P2Nonconvex.objective
                            + "," + P3Convex.objective
                            + "\n");

                    outTime.flush();
                    outObjective.flush();

                }

                outTime.write("\n");
                outObjective.write("\n");

                out.write(i + ","
                        + (sumTime[0] / numIterations) + ","
                        //                        + (sumTime[1] / numIterations) + ","
                        //                        + (sumTime[2] / numIterations) + ","
                        + (sumTime[1] / numIterations) + ", ,"
                        + i + ","
                        + (sumObjective[0] / numIterations) + ","
                        //                        + (sumObjective[1] / numIterations) + ","
                        //                        + (sumObjective[2] / numIterations) + ","
                        + (sumObjective[1] / numIterations) + "\n");

                out.flush();
                outTime.flush();
                outObjective.flush();

            }

            out.write("\n\n");
            outTime.write("\n\n");
            outObjective.write("\n\n");

//            testRuntimeVaryObservations(out, outTime, outObjective, numTargets,
//                    numResources, maxAlpha, alphaOption, numIterations);

            // call function

            outTime.flush();
            outObjective.flush();
            outTime.close();
            outObjective.close();

            out.flush();
            out.close();
        } catch (IOException e) {
            throw new RuntimeException("Error writing security game to file: " + e.getMessage());
        }
    }

    public static void testLaxData() {
////        GenerateGameFiles.ReadDobssDataFile("FridayA400.dat");
//        int numObservations = 5;
//        int numResources = 1;
//        double maxAlpha = 10.0;
//        int alphaOption = 0;
//        double discountFactor = 0.95;
//
//
//        GenerateGameFiles.ReadDobssDataFile("FridayA400.dat");
//        GenerateGameFiles.InitComputeObservations(numObservations);
//        GenerateGameFiles.GeneratePrior(maxAlpha, alphaOption);
//        P1.GenerateAttackerStrategy();
//        P2Nonconvex.Solve("P2_nonconvex_1ms.run");
////        sumDefUtil[0][j] += P2Nonconvex.objective;
////        sumAttackSetSize[0][j] += P2Nonconvex.attackSetSize;
////        sumEntropy[0][j] += P2Nonconvex.entropy;
//
//        Dobss.Solve();
//


        int numResources = 1;
        //double discountFactor = 0.95;
//        int formulation = 2;
        double maxAlpha = 10.0;
        int alphaOption = 0;
        double multiplicationFactor = 10.0;

        int numIterations = 40;
//        int numIterations = 40;


//        int numLines = 5;
        int numTargets = 5;

        String outputFile = "outputTestLAX.csv";
        String rawDefUtil = "rawDefUtilLAX.csv";
        String rawCoverage = "rawCoverage.csv";
//        String rawAttackSetSize = "rawAttackSetSizePerfVaryObservation.csv";
        String rawOptimalObservations = "rawOptimalObservationsLAX.csv";


        int numLines = 8;
        int xDataPoints = 8;
        boolean testOptimalObservations = true;


        double[][] sumDefUtil;
        double[][] sumAttUtil;
        int[][] sumAttackSetSize;
        double[][] sumEntropy;
        int[][] sumOptimalObservations;
        double[][] sumTime;
        double[][] sumObjective;
        double[][] sumDiffCoverage;
        double[][] sumP1Coverage;
        double[][] sumSSECoverage;
        double[] discountFactor;


        int observations[];

        sumDefUtil = new double[numLines][xDataPoints];
        sumAttUtil = new double[numLines][xDataPoints];
        sumAttackSetSize = new int[numLines][xDataPoints];
        sumEntropy = new double[numLines][xDataPoints];
        sumOptimalObservations = new int[numLines][xDataPoints];
        sumTime = new double[numLines][xDataPoints];
        sumObjective = new double[numLines][xDataPoints];
        observations = new int[xDataPoints];
        sumDiffCoverage = new double[numLines][xDataPoints];
        sumP1Coverage = new double[numLines][xDataPoints];
        sumSSECoverage = new double[numLines][xDataPoints];
        discountFactor = new double[xDataPoints];

        System.out.println("Test LAX");

        try {
            PrintWriter out = new PrintWriter(new File(outputFile));
            PrintWriter outDefUtil = new PrintWriter(new File(rawDefUtil));
            PrintWriter outCoverage = new PrintWriter(new File(rawCoverage));
//            PrintWriter outAttackSetSize = new PrintWriter(new File(rawAttackSetSize));
            PrintWriter outOptimalObservations = new PrintWriter(new File(rawOptimalObservations));


            out.write("Test LAX\n");
//            out.write(",P2(1ms),P2(5ms),P2(10ms),P3,"
//                    + ",,P2(1ms),P2(5ms),P2(10ms),P3\n");
            out.write(",P1(Uniform),SSE(Uniform),P1(SSE),SSE(SSE),P1(Hybrid),SSE(Hybrid)\n");
//            out.write(",Target 1(P1)"
//                    + ",Target 2(P1)"
//                    + ",Target 3(P1)"
//                    + ",Target 4(P1)"
//                    + ",Target 5(P1)"
//                    + ",Target 6(P1)"
//                    + ",Target 7(P1)"
//                    + ",Target 8(P1)"
//                    + ",Target 1(SSE)"
//                    + ",Target 2(SSE)"
//                    + ",Target 3(SSE)"
//                    + ",Target 4(SSE)"
//                    + ",Target 5(SSE)"
//                    + ",Target 6(SSE)"
//                    + ",Target 7(SSE)"
//                    + ",Target 8(SSE)"
//                    + "\n");


            for (int x = 0; x < numLines; x++) {
                for (int i = 0; i < xDataPoints; i++) {
                    sumTime[x][i] = 0.0;
                    sumObjective[x][i] = 0;
                    sumDefUtil[x][i] = 0.0;
                    sumAttUtil[x][i] = 0.0;
                    sumAttackSetSize[x][i] = 0;
                    sumEntropy[x][i] = 0.0;
                    sumOptimalObservations[x][i] = 0;
                    sumDiffCoverage[x][i] = 0.0;
                    sumP1Coverage[x][i] = 0.0;
                    sumSSECoverage[x][i] = 0.0;
                }
            }


            for (int i = 0; i < xDataPoints; i++) {
                observations[i] = i + 2;
            }

            discountFactor[0] = 0.5;
            discountFactor[1] = 1.0;
            discountFactor[2] = 2.0;
            discountFactor[3] = 3.0;
            discountFactor[4] = 5.0;


            for (int i = 0; i < numIterations; i++) {
                System.out.println(i);
//                GenerateGameFiles.GeneratePayoffs(numTargets);
//                String laxDataFilename;
//                laxDataFilename = "FridayA" + (i + 4) + "00.dat"
                if (i < 20) {
                    GenerateGameFiles.ReadDobssDataFile("FridayA" + (i + 4) + "00.dat",
                            multiplicationFactor);
                } else {
                    GenerateGameFiles.ReadDobssDataFile("FridayB" + (i - 16) + "00.dat",
                            multiplicationFactor);
                }

                if (testOptimalObservations) {
                    for (int j = 0; j < 5; j++) {
                        alphaOption = 0;

                        BinarySearch.SolveStrategiesAlreadyRead(2, discountFactor[j],
                                maxAlpha, alphaOption, "P2_nonconvex_1ms.run");
                        sumOptimalObservations[0][j] += BinarySearch.optimalObservations;
                        outOptimalObservations.write(BinarySearch.optimalObservations + ",");

                        alphaOption = 1;

                        BinarySearch.SolveStrategiesAlreadyRead(2, discountFactor[j],
                                maxAlpha, alphaOption, "P2_nonconvex_1ms.run");
                        sumOptimalObservations[1][j] += BinarySearch.optimalObservations;
                        outOptimalObservations.write(BinarySearch.optimalObservations + ",");

                        alphaOption = 2;

                        BinarySearch.SolveStrategiesAlreadyRead(2, discountFactor[j],
                                maxAlpha, alphaOption, "P2_nonconvex_1ms.run");
                        sumOptimalObservations[2][j] += BinarySearch.optimalObservations;
                        outOptimalObservations.write(BinarySearch.optimalObservations + "\n");

                        outOptimalObservations.flush();
                    }


                } else {
                    for (int j = 0; j < xDataPoints; j++) {

//                GenerateGameFiles.GeneratePayoffs(numTargets);
                        alphaOption = 0;

//                    GenerateGameFiles.InitStrategiesObservations(numTargets,
//                            observations[j], numResources);
                        GenerateGameFiles.InitComputeObservations(observations[j]);
                        GenerateGameFiles.GeneratePrior(maxAlpha, alphaOption);
                        P1.GenerateAttackerStrategy();

                        P2Nonconvex.Solve("P2_nonconvex_1ms.run");
                        sumDefUtil[0][j] += P2Nonconvex.objective;
                        sumAttackSetSize[0][j] += P2Nonconvex.attackSetSize;
                        sumEntropy[0][j] += P2Nonconvex.entropy;

                        Dobss.Solve();
                        sumDefUtil[1][j] += Dobss.GetUpdatedDefUtil();
                        sumAttackSetSize[1][j] += Dobss.attackSetSize;
                        sumEntropy[1][j] += Dobss.entropy;

                        for (int k = 0; k < numTargets; k++) {
                            sumDiffCoverage[k][j] += (P2Nonconvex.defStrategy.get(k) - Dobss.defStrategy.get(k));
//                        outRaw.print((P2Nonconvex.defStrategy.get(k) - Dobss.defStrategy.get(k)) + ",");
                            sumP1Coverage[k][j] += P2Nonconvex.defStrategy.get(k);
                            sumSSECoverage[k][j] += Dobss.defStrategy.get(k);
                        }


//                    outDefUtil.write(P1.attackList +  "\n");
//
                        outDefUtil.write(P2Nonconvex.objective + ","
                                + Dobss.GetUpdatedDefUtil() + ",");
//                    outCoverage.write(P2Nonconvex.defStrategy + ";" + Dobss.defStrategy + ";");


                        alphaOption = 1;
                        GenerateGameFiles.InitComputeObservations(observations[j]);
                        GenerateGameFiles.GeneratePrior(maxAlpha, alphaOption);
                        P1.GenerateAttackerStrategy();

                        P2Nonconvex.Solve("P2_nonconvex_1ms.run");
                        sumDefUtil[2][j] += P2Nonconvex.objective;
                        sumAttackSetSize[2][j] += P2Nonconvex.attackSetSize;
                        sumEntropy[2][j] += P2Nonconvex.entropy;

                        Dobss.Solve();
                        sumDefUtil[3][j] += Dobss.GetUpdatedDefUtil();
                        sumAttackSetSize[3][j] += Dobss.attackSetSize;
                        sumEntropy[3][j] += Dobss.entropy;

                        outDefUtil.write(P2Nonconvex.objective + ","
                                + Dobss.GetUpdatedDefUtil() + ",");

                        alphaOption = 2;
                        GenerateGameFiles.InitComputeObservations(observations[j]);
                        GenerateGameFiles.GeneratePrior(maxAlpha, alphaOption);
                        P1.GenerateAttackerStrategy();

                        P2Nonconvex.Solve("P2_nonconvex_1ms.run");
                        sumDefUtil[4][j] += P2Nonconvex.objective;
                        sumAttackSetSize[4][j] += P2Nonconvex.attackSetSize;
                        sumEntropy[4][j] += P2Nonconvex.entropy;

                        Dobss.Solve();
                        sumDefUtil[5][j] += Dobss.GetUpdatedDefUtil();
                        sumAttackSetSize[5][j] += Dobss.attackSetSize;
                        sumEntropy[5][j] += Dobss.entropy;


                        outDefUtil.write(P2Nonconvex.objective + ","
                                + Dobss.GetUpdatedDefUtil() + "\n");
                        outCoverage.write(P2Nonconvex.defStrategy + ";" + Dobss.defStrategy + "\n");

                        outDefUtil.flush();
                        outCoverage.flush();



                        //                    sumTime[0][j] = sumTime[0][j] + P2Nonconvex.time + P1.time;
//                    sumObjective[0][j] += P2Nonconvex.objective;

//                    P2Nonconvex.Solve("P2_nonconvex_5ms.run");
//                    sumTime[1][j] = sumTime[1][j] + P2Nonconvex.time + P1.time;
//                    sumObjective[1][j] += P2Nonconvex.objective;
//
//                    P2Nonconvex.Solve("P2_nonconvex_10ms.run");
//                    sumTime[2][j] = sumTime[2][j] + P2Nonconvex.time + P1.time;
//                    sumObjective[2][j] += P2Nonconvex.objective;
//
//
//                    P3Convex.Solve();
//
//                    sumTime[3][j] = sumTime[3][j] + P3Convex.time + P1.time;
//                    sumObjective[3][j] += P3Convex.objective;

//                    sumTime[0] = sumTime[0] + P2Nonconvex.time + P1.time;
//                    sumObjective[0] += P2Nonconvex.objective;
//                    sumTime[1] = sumTime[1] + P3Convex.time + P1.time;
//                    sumObjective[1] += P3Convex.objective;


//                    outTime.write((P2Nonconvex.time + P1.time) + "," + (P3Convex.time + P1.time) + "\n");
//                    outObjective.write(P2Nonconvex.objective + "," + P3Convex.objective + "\n");
//                    outTime.flush();
//                    outObjective.flush();

                    }
                }

                outDefUtil.write("\n");
                outCoverage.write("\n");


//                outTime.write("\n");
//                outObjective.write("\n");


//                out.flush();
//                outTime.flush();
//                outObjective.flush();
            }


            for (int i = 0; i < xDataPoints; i++) {

//                out.write(observations[i] + ","
//                        + (sumP1Coverage[0][i] / numIterations) + ","
//                        + (sumP1Coverage[1][i] / numIterations) + ","
//                        + (sumP1Coverage[2][i] / numIterations) + ","
//                        + (sumP1Coverage[3][i] / numIterations) + ","
//                        + (sumP1Coverage[4][i] / numIterations) + ","
//                        + (sumP1Coverage[5][i] / numIterations) + ","
//                        + (sumP1Coverage[6][i] / numIterations) + ","
//                        + (sumP1Coverage[7][i] / numIterations) + ","
//                        + (sumSSECoverage[0][i] / numIterations) + ","
//                        + (sumSSECoverage[1][i] / numIterations) + ","
//                        + (sumSSECoverage[2][i] / numIterations) + ","
//                        + (sumSSECoverage[3][i] / numIterations) + ","
//                        + (sumSSECoverage[4][i] / numIterations) + ","
//                        + (sumSSECoverage[5][i] / numIterations) + ","
//                        + (sumSSECoverage[6][i] / numIterations) + ","
//                        + (sumSSECoverage[7][i] / numIterations) + "\n");

//                out.write(observations[i] + ","
//                        + (sumDiffCoverage[0][i] / numIterations) + ","
//                        + (sumDiffCoverage[1][i] / numIterations) + ","
//                        + (sumDiffCoverage[2][i] / numIterations) + ","
//                        + (sumDiffCoverage[3][i] / numIterations) + ","
//                        + (sumDiffCoverage[4][i] / numIterations) + ","
//                        + (sumDiffCoverage[5][i] / numIterations) + ","
//                        + (sumDiffCoverage[6][i] / numIterations) + ","
//                        + (sumDiffCoverage[7][i] / numIterations) + "\n");

                if (testOptimalObservations) {
                    out.write(discountFactor[i] + ","
                            + (sumOptimalObservations[0][i] / numIterations) + ","
                            + (sumOptimalObservations[1][i] / numIterations) + ","
                            + (sumOptimalObservations[2][i] / numIterations) + "\n");

                } else {
                    out.write(observations[i] + ","
                            + (sumDefUtil[0][i] / numIterations) + ","
                            + (sumDefUtil[1][i] / numIterations) + ","
                            + (sumDefUtil[2][i] / numIterations) + ","
                            + (sumDefUtil[3][i] / numIterations) + ","
                            + (sumDefUtil[4][i] / numIterations) + ","
                            + (sumDefUtil[5][i] / numIterations) + "\n");
                }


//                        + (sumDefUtil[3][i] / numIterations) + ", ,"
                //                        + (sumDefUtil[2][i] / numIterations) + ", "
                //                        + (sumAttUtil[0][i] / numIterations) + ","
                //                        + (sumAttUtil[1][i] / numIterations) + ","
                //                        + (sumAttUtil[2][i] / numIterations) + ", ,"
//                        + observations[i] + ","
//                        + ((double) sumAttackSetSize[0][i] / numIterations) + ","
//                        + ((double) sumAttackSetSize[1][i] / numIterations) + ","
//                        + ((double) sumAttackSetSize[2][i] / numIterations) + ","
//                        //                        + ((double) sumAttackSetSize[1][i] / numIterations) + ","
//                        + ((double) sumAttackSetSize[3][i] / numIterations) + ", ,"
//                        + observations[i] + ","
//                        + (sumEntropy[0][i] / numIterations) + ","
//                        + (sumEntropy[1][i] / numIterations) + ","
//                        + (sumEntropy[2][i] / numIterations) + ","
//                        //                        + (sumEntropy[1][i] / numIterations) + ","
//                        + (sumEntropy[3][i] / numIterations) + "\n");


//                out.write(observations[i] + ","
//                        + (sumTime[0][i] / numIterations) + ","
//                        + (sumTime[1][i] / numIterations) + ","
//                        + (sumTime[2][i] / numIterations) + ","
//                        + (sumTime[3][i] / numIterations) + ", ,"
//                        + observations[i] + ","
//                        + (sumObjective[0][i] / numIterations) + ","
//                        + (sumObjective[1][i] / numIterations) + ","
//                        + (sumObjective[2][i] / numIterations) + ","
//                        + (sumObjective[3][i] / numIterations) + "\n");

            }

            outDefUtil.flush();
            outOptimalObservations.flush();
            outOptimalObservations.close();
//            outAttUtil.flush();
//            outAttackSetSize.flush();
//            outEntropy.flush();
            outDefUtil.close();
            outCoverage.flush();
            outCoverage.close();
//            outAttUtil.close();
//            outAttackSetSize.close();
//            outEntropy.close();

            out.flush();
            out.close();
        } catch (IOException e) {
            throw new RuntimeException("Error writing security game to file: " + e.getMessage());
        }



    }

    public static void testMultistart() {

        String outputFile = "outputTestMultistartFile.csv";
        String rawdataTime = "timeOutput.csv";
        String rawdataObjective = "objectiveOutput.csv";

        int numIterations = 200;

        int numTargets = 5;
        int numObservations = 5;
        int numResources = 1;
        double maxAlpha = 10.0;
        int alphaOption = 0;

        double[] sumObjective;
        double[] sumTime;

        sumObjective = new double[4];
        sumTime = new double[4];

        try {
            PrintWriter out = new PrintWriter(new File(outputFile));
            PrintWriter outTime = new PrintWriter(new File(rawdataTime));
            PrintWriter outObjective = new PrintWriter(new File(rawdataObjective));


//            P1.GenerateAttackerStrategy(numTargets, numObservations, numResources);
//            P2Nonconvex.Solve();
//            P3Convex.Solve();


            System.out.println("Varying number of targets");
            out.write("Vary num Targets\n");

            // 18 targets w/ 5 observations is 23 seconds
            for (int i = 4; i <= 10; i++) {
                System.out.println(i);

                for (int x = 0; x < 4; x++) {
                    sumTime[x] = 0.0;
                    sumObjective[x] = 0.0;
                }

                for (int j = 0; j < numIterations; j++) {
                    GenerateGameFiles.GeneratePayoffs(i);
                    GenerateGameFiles.InitStrategiesObservations(i, numObservations, numResources);
                    GenerateGameFiles.GeneratePrior(maxAlpha, alphaOption);
                    P1.GenerateAttackerStrategy();
                    P2Nonconvex.Solve("P2_nonconvex_1ms.run");
                    sumTime[0] = sumTime[0] + P2Nonconvex.time + P1.time;
                    sumObjective[0] += P2Nonconvex.objective;

                    P2Nonconvex.Solve("P2_nonconvex_5ms.run");
                    sumTime[1] = sumTime[1] + P2Nonconvex.time + P1.time;
                    sumObjective[1] += P2Nonconvex.objective;

                    P2Nonconvex.Solve("P2_nonconvex_10ms.run");
                    sumTime[2] = sumTime[2] + P2Nonconvex.time + P1.time;
                    sumObjective[2] += P2Nonconvex.objective;

                    P2Nonconvex.Solve("P2_nonconvex_20ms.run");
                    sumTime[3] = sumTime[3] + P2Nonconvex.time + P1.time;
                    sumObjective[3] += P2Nonconvex.objective;


                    outTime.write((P2Nonconvex.time + P1.time) + "," + (P3Convex.time + P1.time) + "\n");
                    outObjective.write(P2Nonconvex.objective + "," + P3Convex.objective + "\n");

                }

                outTime.write("\n");
                outObjective.write("\n");

                out.write(i + "," + (sumTime[0] / numIterations) + "," + (sumTime[1] / numIterations)
                        + "," + (sumTime[2] / numIterations) + "," + (sumTime[3] / numIterations) + ", ,"
                        + i + "," + (sumObjective[0] / numIterations) + "," + (sumObjective[1] / numIterations)
                        + "," + (sumObjective[2] / numIterations) + "," + (sumObjective[3] / numIterations) + "\n");

                out.flush();
                outTime.flush();
                outObjective.flush();

            }

            outTime.flush();
            outObjective.flush();
            outTime.close();
            outObjective.close();

            out.flush();
            out.close();
        } catch (IOException e) {
            throw new RuntimeException("Error writing security game to file: " + e.getMessage());
        }

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        //runExperiments();
    	
    	String dir = System.getProperty("user.dir");
    	
        runSimple();
        //testConjecture3();
        //compareCoveragePerTarget();
        //testAttackerUtility();
        //testBinarySearch();
        //testPerformanceVaryPrior();
        //testPerformanceVaryObservation();
        //testPerformanceVaryTargets();
        //testPerformanceVaryDiscountFactor();
        //testDobss();
        //testRuntime();
        //testMultistart();
        //testAttackUtility();
        //testRuntimeP2Speedup();
        //testRobustAnalysis();
        //testPerformanceVaryResources();
        //testLaxData();
        //testRobustnessTable();
    }
}
