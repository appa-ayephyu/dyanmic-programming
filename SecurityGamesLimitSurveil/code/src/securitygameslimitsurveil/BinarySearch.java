/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package securitygameslimitsurveil;

import java.util.Collections;
import java.util.HashMap;

/**
 *
 * @author teamcore
 */
public class BinarySearch {

    protected static HashMap<Integer, Double> attackUtil;
//    protected static HashMap<Integer, Double> defUtil;
    protected static int optimalObservations;
    protected static double optimalAttackerUtil;
    protected static double optimalDefenderUtil;

    //protected static double attackerUtilityLower;
    public static int FindUpperBoundBinary(int formulation, int numTargets,
            int numResources, double discountFactor, double maxAlpha, int alphaOption) {

        double n = -1;
        int upper = -1;

        double maxReward = Collections.max(GenerateGameFiles.attUncov);

//        System.out.println("max attacker reward: " + maxReward);

        GenerateGameFiles.InitStrategiesObservations(numTargets, 0, numResources);
        GenerateGameFiles.GeneratePrior(maxAlpha, alphaOption);
        P1.GenerateAttackerStrategy();

        if (formulation == 2) {
//            P2Nonconvex.Solve();
            P2NonconvexBinary.Solve();
            AttackerUtility.Solve(discountFactor, P2NonconvexBinary.defStrategy);

        } else if (formulation == 3) {
            P3Convex.Solve();
            AttackerUtility.Solve(discountFactor, P3Convex.defStrategy);
//            // Need to read in the defender strategy for P3
//            System.out.println("TODO");

        } else {
            System.out.println("Formulation incorrect, " + formulation);
        }

        n = AttackerUtility.finalUtility;
//        attackerUtilityLower = AttackerUtility.finalUtility;
        upper = (int) Math.ceil(Math.log(n / ((double) maxReward)) / Math.log(discountFactor));
        System.out.println("Upper value: " + upper + ", n=" + n);

        if ((upper > 20) || (upper < 0)) {
            System.out.println("Upper value greater than 20: " + upper);
            upper = 20;
        }

        return upper;
    }

    /**
     *
     * @param formulation, 2 = P2, 3 = P3
     */
    public static void Solve(int formulation, int numTargets,
            int numResources, double discountFactor, double maxAlpha, int alphaOption, 
            String scriptFilename) {


        int lower = 0;
//        int upper = FindUpperBoundBinary(formulation, numTargets, numResources,
//                discountFactor, maxAlpha, alphaOption);
        int upper = 15;
        int observations = 0;
//        double attackUtil = 0;
//        double attackUtilMinus1 = 0;
        attackUtil = new HashMap<Integer, Double>();
//        defUtil = new HashMap<Integer, Double>();

//        System.out.println(upper);

        for (int i = 1; i <= 5; i++) {
            GenerateGameFiles.InitStrategiesObservations(numTargets, i, numResources);
            GenerateGameFiles.GeneratePrior(maxAlpha, alphaOption);
            P1.GenerateAttackerStrategy();
            if (formulation == 2) {
                P2NonconvexBinary.Solve();
                AttackerUtility.Solve(discountFactor, P2NonconvexBinary.defStrategy);
                attackUtil.put(i, AttackerUtility.finalUtility);
            }
        }


        lower = 6;
        while ((upper - lower) > 1) {

            observations = (upper + lower) / 2;

            GenerateGameFiles.InitStrategiesObservations(numTargets, observations, numResources);
            GenerateGameFiles.GeneratePrior(maxAlpha, alphaOption);
            P1.GenerateAttackerStrategy();

            if (formulation == 2) {
//                P2Nonconvex.Solve(scriptFilename);
                P2NonconvexBinary.Solve();
                AttackerUtility.Solve(discountFactor, P2NonconvexBinary.defStrategy);
                attackUtil.put(observations, AttackerUtility.finalUtility);
//                defUtil.put(observations, P2Nonconvex.objective);

                GenerateGameFiles.InitStrategiesObservations(numTargets, observations - 1, numResources);
                GenerateGameFiles.GeneratePrior(maxAlpha, alphaOption);
                P1.GenerateAttackerStrategy();
//                P2Nonconvex.Solve(scriptFilename);
                P2NonconvexBinary.Solve();
                AttackerUtility.Solve(discountFactor, P2NonconvexBinary.defStrategy);
                attackUtil.put(observations - 1, AttackerUtility.finalUtility);
//                defUtil.put(observations - 1, P2Nonconvex.objective);
//                attackUtilMinus1 = AttackerUtility.finalUtility;

            } else if (formulation == 3) {
                P3Convex.Solve();

                AttackerUtility.Solve(discountFactor, P3Convex.defStrategy);
                attackUtil.put(observations, AttackerUtility.finalUtility);
//                defUtil.put(observations, P3Convex.objective);

                GenerateGameFiles.InitStrategiesObservations(numTargets, observations - 1, numResources);
                GenerateGameFiles.GeneratePrior(maxAlpha, alphaOption);
                P1.GenerateAttackerStrategy();
                P3Convex.Solve();
                AttackerUtility.Solve(discountFactor, P3Convex.defStrategy);
                attackUtil.put(observations - 1, AttackerUtility.finalUtility);
//                defUtil.put(observations - 1, P3Convex.objective);


                // Need to read in the defender strategy for P3
//                System.out.println("TODO");

            } else {
                System.out.println("Formulation incorrect, " + formulation);
            }

            //--------- Compare

            if (attackUtil.get(observations - 1) < attackUtil.get(observations)) {
                lower = observations;
            } else {
                upper = observations;
            }

//            System.out.println(lower + ", " + upper + ", " + observations);
        }


        // If the upper=1 for the initial case
//        if (attackUtil.isEmpty()) {
//            observations = upper;
//            GenerateGameFiles.InitStrategiesObservations(numTargets, observations, numResources);
//            GenerateGameFiles.GeneratePrior(maxAlpha, alphaOption);
//            P1.GenerateAttackerStrategy();
//
//            if (formulation == 2) {
//                if (!(attackUtil.containsKey(observations))) {
////                    P2Nonconvex.Solve(scriptFilename);
//                    P2NonconvexBinary.Solve();
//                    AttackerUtility.Solve(discountFactor, P2NonconvexBinary.defStrategy);
//                    attackUtil.put(observations, AttackerUtility.finalUtility);
////                    defUtil.put(observations, P2Nonconvex.objective);
//                }
//
//                if (!(attackUtil.containsKey(observations - 1))) {
//                    GenerateGameFiles.InitStrategiesObservations(numTargets, observations - 1, numResources);
//                    GenerateGameFiles.GeneratePrior(maxAlpha, alphaOption);
//                    P1.GenerateAttackerStrategy();
////                    P2Nonconvex.Solve(scriptFilename);
//                    P2NonconvexBinary.Solve();
//                    AttackerUtility.Solve(discountFactor, P2NonconvexBinary.defStrategy);
//                    attackUtil.put(observations - 1, AttackerUtility.finalUtility);
////                    defUtil.put(observations - 1, P2Nonconvex.objective);
//                }
////                attackUtilMinus1 = AttackerUtility.finalUtility;
//
//            } else if (formulation == 3) {
//                if (!(attackUtil.containsKey(observations))) {
//
//                    P3Convex.Solve();
//                    AttackerUtility.Solve(discountFactor, P3Convex.defStrategy);
//                    attackUtil.put(observations, AttackerUtility.finalUtility);
////                    defUtil.put(observations, P3Convex.objective);
//                }
//
//                if (!(attackUtil.containsKey(observations - 1))) {
//
//                    GenerateGameFiles.InitStrategiesObservations(numTargets, observations - 1, numResources);
//                    GenerateGameFiles.GeneratePrior(maxAlpha, alphaOption);
//                    P1.GenerateAttackerStrategy();
//                    P3Convex.Solve();
//                    AttackerUtility.Solve(discountFactor, P3Convex.defStrategy);
//                    attackUtil.put(observations - 1, AttackerUtility.finalUtility);
////                    defUtil.put(observations - 1, P3Convex.objective);
//                }
//
//                // Need to read in the defender strategy for P3
////                System.out.println("TODO");
//
//            } else {
//                System.out.println("Formulation incorrect, " + formulation);
//            }
//
//        }

        
        if (!(attackUtil.containsKey(upper))) {
            GenerateGameFiles.InitStrategiesObservations(numTargets, upper, numResources);
            GenerateGameFiles.GeneratePrior(maxAlpha, alphaOption);
            P1.GenerateAttackerStrategy();

            if (formulation == 2) {
//                P2Nonconvex.Solve(scriptFilename);
                P2NonconvexBinary.Solve();
                AttackerUtility.Solve(discountFactor, P2NonconvexBinary.defStrategy);
                attackUtil.put(upper, AttackerUtility.finalUtility);
//                defUtil.put(upper, P2Nonconvex.objective);

            } else if (formulation == 3) {

                P3Convex.Solve();
                AttackerUtility.Solve(discountFactor, P3Convex.defStrategy);
                attackUtil.put(upper, AttackerUtility.finalUtility);
//                defUtil.put(upper, P3Convex.objective);

                // Need to read in the defender strategy for P3
//                System.out.println("TODO");

            } else {
                System.out.println("Formulation incorrect, " + formulation);
            }
        }

        if (!(attackUtil.containsKey(lower))) {
            GenerateGameFiles.InitStrategiesObservations(numTargets, lower, numResources);
            GenerateGameFiles.GeneratePrior(maxAlpha, alphaOption);
            P1.GenerateAttackerStrategy();

            if (formulation == 2) {
                P2NonconvexBinary.Solve();
//                P2Nonconvex.Solve(scriptFilename);
                AttackerUtility.Solve(discountFactor, P2NonconvexBinary.defStrategy);
                attackUtil.put(lower, AttackerUtility.finalUtility);
//                defUtil.put(lower, P2Nonconvex.objective);

            } else if (formulation == 3) {

                P3Convex.Solve();
                AttackerUtility.Solve(discountFactor, P3Convex.defStrategy);
                attackUtil.put(lower, AttackerUtility.finalUtility);
//                defUtil.put(lower, P3Convex.objective);

                // Need to read in the defender strategy for P3
//                System.out.println("TODO");

            } else {
                System.out.println("Formulation incorrect, " + formulation);
            }
        }




        if (!(attackUtil.containsKey(upper)) || !(attackUtil.containsKey(lower))) {
            System.out.println(attackUtil);
            System.out.println("upper: " + upper + ", lower: " + lower);
        }


        optimalAttackerUtil = Collections.max(attackUtil.values());

        for (int i : attackUtil.keySet()) {
            if (Math.abs(optimalAttackerUtil - attackUtil.get(i)) < 0.000001) {
                optimalObservations = i;
                break;
            }
        }

//        if (attackUtil.get(upper) > attackUtil.get(lower)) {
//            optimalObservations = upper;
//        } else {
//            optimalObservations = lower;
//        }


        GenerateGameFiles.InitStrategiesObservations(numTargets, optimalObservations, numResources);
        GenerateGameFiles.GeneratePrior(maxAlpha, alphaOption);
        P1.GenerateAttackerStrategy();
        if (formulation == 2) {
            P2Nonconvex.Solve(scriptFilename);
            optimalDefenderUtil = P2Nonconvex.objective;
        } else if (formulation == 3) {
            P3Convex.Solve();
        }

//        System.out.println(optimalDefenderUtil);

//        System.out.println(attackUtil);
//        System.out.println("optimal tau: " + optimalObservations);
//        optimalAttackerUtil = attackUtil.get(optimalObservations);
//        optimalDefenderUtil = defUtil.get(optimalObservations);
    }


    /**
     *
     * @param formulation, 2 = P2, 3 = P3
     */
    public static void SolveStrategiesAlreadyRead(int formulation,
            double discountFactor, double maxAlpha, int alphaOption,
            String scriptFilename) {


        int lower = 0;
        int upper = 9;
        int observations = 0;
//        double attackUtil = 0;
//        double attackUtilMinus1 = 0;
        attackUtil = new HashMap<Integer, Double>();
//        defUtil = new HashMap<Integer, Double>();

//        System.out.println(upper);

        for (int i = 1; i <= 5; i++) {
//            GenerateGameFiles.InitStrategiesObservations(numTargets, i, numResources);
            GenerateGameFiles.InitComputeObservations(i);
            GenerateGameFiles.GeneratePrior(maxAlpha, alphaOption);
            P1.GenerateAttackerStrategy();
            if (formulation == 2) {
                P2NonconvexBinary.Solve();
                AttackerUtility.Solve(discountFactor, P2NonconvexBinary.defStrategy);
                attackUtil.put(i, AttackerUtility.finalUtility);
            }
        }


        lower = 6;
        while ((upper - lower) > 1) {

            observations = (upper + lower) / 2;

//            GenerateGameFiles.InitStrategiesObservations(numTargets, observations, numResources);
            GenerateGameFiles.InitComputeObservations(observations);
            GenerateGameFiles.GeneratePrior(maxAlpha, alphaOption);
            P1.GenerateAttackerStrategy();

            if (formulation == 2) {
//                P2Nonconvex.Solve(scriptFilename);
                P2NonconvexBinary.Solve();
                AttackerUtility.Solve(discountFactor, P2NonconvexBinary.defStrategy);
                attackUtil.put(observations, AttackerUtility.finalUtility);
//                defUtil.put(observations, P2Nonconvex.objective);

//                GenerateGameFiles.InitStrategiesObservations(numTargets, observations - 1, numResources);
                GenerateGameFiles.InitComputeObservations(observations-1);
                GenerateGameFiles.GeneratePrior(maxAlpha, alphaOption);
                P1.GenerateAttackerStrategy();
//                P2Nonconvex.Solve(scriptFilename);
                P2NonconvexBinary.Solve();
                AttackerUtility.Solve(discountFactor, P2NonconvexBinary.defStrategy);
                attackUtil.put(observations - 1, AttackerUtility.finalUtility);
//                defUtil.put(observations - 1, P2Nonconvex.objective);
//                attackUtilMinus1 = AttackerUtility.finalUtility;

            } else if (formulation == 3) {
                P3Convex.Solve();

                AttackerUtility.Solve(discountFactor, P3Convex.defStrategy);
                attackUtil.put(observations, AttackerUtility.finalUtility);
//                defUtil.put(observations, P3Convex.objective);

//                GenerateGameFiles.InitStrategiesObservations(numTargets, observations - 1, numResources);
                GenerateGameFiles.InitComputeObservations(observations-1);
                GenerateGameFiles.GeneratePrior(maxAlpha, alphaOption);
                P1.GenerateAttackerStrategy();
                P3Convex.Solve();
                AttackerUtility.Solve(discountFactor, P3Convex.defStrategy);
                attackUtil.put(observations - 1, AttackerUtility.finalUtility);
//                defUtil.put(observations - 1, P3Convex.objective);


                // Need to read in the defender strategy for P3
//                System.out.println("TODO");

            } else {
                System.out.println("Formulation incorrect, " + formulation);
            }

            //--------- Compare

            if (attackUtil.get(observations - 1) < attackUtil.get(observations)) {
                lower = observations;
            } else {
                upper = observations;
            }

        }


        if (!(attackUtil.containsKey(upper))) {
//            GenerateGameFiles.InitStrategiesObservations(numTargets, upper, numResources);
            GenerateGameFiles.InitComputeObservations(upper);
            GenerateGameFiles.GeneratePrior(maxAlpha, alphaOption);
            P1.GenerateAttackerStrategy();

            if (formulation == 2) {
                P2NonconvexBinary.Solve();
                AttackerUtility.Solve(discountFactor, P2NonconvexBinary.defStrategy);
                attackUtil.put(upper, AttackerUtility.finalUtility);

            } else if (formulation == 3) {

                P3Convex.Solve();
                AttackerUtility.Solve(discountFactor, P3Convex.defStrategy);
                attackUtil.put(upper, AttackerUtility.finalUtility);

            } else {
                System.out.println("Formulation incorrect, " + formulation);
            }
        }

        if (!(attackUtil.containsKey(lower))) {
//            GenerateGameFiles.InitStrategiesObservations(numTargets, lower, numResources);
            GenerateGameFiles.InitComputeObservations(lower);
            GenerateGameFiles.GeneratePrior(maxAlpha, alphaOption);
            P1.GenerateAttackerStrategy();

            if (formulation == 2) {
                P2NonconvexBinary.Solve();
//                P2Nonconvex.Solve(scriptFilename);
                AttackerUtility.Solve(discountFactor, P2NonconvexBinary.defStrategy);
                attackUtil.put(lower, AttackerUtility.finalUtility);
//                defUtil.put(lower, P2Nonconvex.objective);

            } else if (formulation == 3) {

                P3Convex.Solve();
                AttackerUtility.Solve(discountFactor, P3Convex.defStrategy);
                attackUtil.put(lower, AttackerUtility.finalUtility);

            } else {
                System.out.println("Formulation incorrect, " + formulation);
            }
        }


        if (!(attackUtil.containsKey(upper)) || !(attackUtil.containsKey(lower))) {
            System.out.println(attackUtil);
            System.out.println("upper: " + upper + ", lower: " + lower);
        }


        optimalAttackerUtil = Collections.max(attackUtil.values());

        for (int i : attackUtil.keySet()) {
            if (Math.abs(optimalAttackerUtil - attackUtil.get(i)) < 0.000001) {
                optimalObservations = i;
                break;
            }
        }

//        GenerateGameFiles.InitStrategiesObservations(numTargets, optimalObservations, numResources);
        GenerateGameFiles.InitComputeObservations(optimalObservations);
        GenerateGameFiles.GeneratePrior(maxAlpha, alphaOption);
        P1.GenerateAttackerStrategy();
        if (formulation == 2) {
            P2Nonconvex.Solve(scriptFilename);
            optimalDefenderUtil = P2Nonconvex.objective;
        } else if (formulation == 3) {
            P3Convex.Solve();
        }

    }


    /**
     *
     * @param formulation, 2 = P2, 3 = P3
     */
    public static void SolveWithConstraint(int formulation,
            double discountFactor, double maxAlpha, int alphaOption,
            String scriptFilename) {


        int lower = 0;
        int upper = 15;
        int observations = 0;
//        double attackUtil = 0;
//        double attackUtilMinus1 = 0;
        attackUtil = new HashMap<Integer, Double>();
//        defUtil = new HashMap<Integer, Double>();

//        System.out.println(upper);

        for (int i = 1; i <= 5; i++) {
//            GenerateGameFiles.InitStrategiesObservations(numTargets, i, numResources);
            GenerateGameFiles.InitComputeObservations(i);
            GenerateGameFiles.GeneratePrior(maxAlpha, alphaOption);
            P1.GenerateAttackerStrategy();
            if (formulation == 2) {
                P2NonconvexBinary.Solve();
                AttackerUtility.Solve(discountFactor, P2NonconvexBinary.defStrategy);
                attackUtil.put(i, AttackerUtility.finalUtility);
            }
        }


        lower = 6;
        while ((upper - lower) > 1) {

            observations = (upper + lower) / 2;

//            GenerateGameFiles.InitStrategiesObservations(numTargets, observations, numResources);
            GenerateGameFiles.InitComputeObservations(observations);
            GenerateGameFiles.GeneratePrior(maxAlpha, alphaOption);
            P1.GenerateAttackerStrategy();

            if (formulation == 2) {
//                P2Nonconvex.Solve(scriptFilename);
                P2NonconvexBinary.Solve();
                AttackerUtility.Solve(discountFactor, P2NonconvexBinary.defStrategy);
                attackUtil.put(observations, AttackerUtility.finalUtility);
//                defUtil.put(observations, P2Nonconvex.objective);

//                GenerateGameFiles.InitStrategiesObservations(numTargets, observations - 1, numResources);
                GenerateGameFiles.InitComputeObservations(observations-1);
                GenerateGameFiles.GeneratePrior(maxAlpha, alphaOption);
                P1.GenerateAttackerStrategy();
//                P2Nonconvex.Solve(scriptFilename);
                P2NonconvexBinary.Solve();
                AttackerUtility.Solve(discountFactor, P2NonconvexBinary.defStrategy);
                attackUtil.put(observations - 1, AttackerUtility.finalUtility);
//                defUtil.put(observations - 1, P2Nonconvex.objective);
//                attackUtilMinus1 = AttackerUtility.finalUtility;

            } else if (formulation == 3) {
                P3Convex.Solve();

                AttackerUtility.Solve(discountFactor, P3Convex.defStrategy);
                attackUtil.put(observations, AttackerUtility.finalUtility);
//                defUtil.put(observations, P3Convex.objective);

//                GenerateGameFiles.InitStrategiesObservations(numTargets, observations - 1, numResources);
                GenerateGameFiles.InitComputeObservations(observations-1);
                GenerateGameFiles.GeneratePrior(maxAlpha, alphaOption);
                P1.GenerateAttackerStrategy();
                P3Convex.Solve();
                AttackerUtility.Solve(discountFactor, P3Convex.defStrategy);
                attackUtil.put(observations - 1, AttackerUtility.finalUtility);
//                defUtil.put(observations - 1, P3Convex.objective);


                // Need to read in the defender strategy for P3
//                System.out.println("TODO");

            } else {
                System.out.println("Formulation incorrect, " + formulation);
            }

            //--------- Compare

            if (attackUtil.get(observations - 1) < attackUtil.get(observations)) {
                lower = observations;
            } else {
                upper = observations;
            }

        }


        if (!(attackUtil.containsKey(upper))) {
//            GenerateGameFiles.InitStrategiesObservations(numTargets, upper, numResources);
            GenerateGameFiles.InitComputeObservations(upper);
            GenerateGameFiles.GeneratePrior(maxAlpha, alphaOption);
            P1.GenerateAttackerStrategy();

            if (formulation == 2) {
                P2NonconvexBinary.Solve();
                AttackerUtility.Solve(discountFactor, P2NonconvexBinary.defStrategy);
                attackUtil.put(upper, AttackerUtility.finalUtility);

            } else if (formulation == 3) {

                P3Convex.Solve();
                AttackerUtility.Solve(discountFactor, P3Convex.defStrategy);
                attackUtil.put(upper, AttackerUtility.finalUtility);

            } else {
                System.out.println("Formulation incorrect, " + formulation);
            }
        }

        if (!(attackUtil.containsKey(lower))) {
//            GenerateGameFiles.InitStrategiesObservations(numTargets, lower, numResources);
            GenerateGameFiles.InitComputeObservations(lower);
            GenerateGameFiles.GeneratePrior(maxAlpha, alphaOption);
            P1.GenerateAttackerStrategy();

            if (formulation == 2) {
                P2NonconvexBinary.Solve();
//                P2Nonconvex.Solve(scriptFilename);
                AttackerUtility.Solve(discountFactor, P2NonconvexBinary.defStrategy);
                attackUtil.put(lower, AttackerUtility.finalUtility);
//                defUtil.put(lower, P2Nonconvex.objective);

            } else if (formulation == 3) {

                P3Convex.Solve();
                AttackerUtility.Solve(discountFactor, P3Convex.defStrategy);
                attackUtil.put(lower, AttackerUtility.finalUtility);

            } else {
                System.out.println("Formulation incorrect, " + formulation);
            }
        }


        if (!(attackUtil.containsKey(upper)) || !(attackUtil.containsKey(lower))) {
            System.out.println(attackUtil);
            System.out.println("upper: " + upper + ", lower: " + lower);
        }


        optimalAttackerUtil = Collections.max(attackUtil.values());

        for (int i : attackUtil.keySet()) {
            if (Math.abs(optimalAttackerUtil - attackUtil.get(i)) < 0.000001) {
                optimalObservations = i;
                break;
            }
        }

//        GenerateGameFiles.InitStrategiesObservations(numTargets, optimalObservations, numResources);
        GenerateGameFiles.InitComputeObservations(optimalObservations);
        GenerateGameFiles.GeneratePrior(maxAlpha, alphaOption);
        P1.GenerateAttackerStrategy();
        if (formulation == 2) {
            P2Nonconvex.Solve(scriptFilename);
            optimalDefenderUtil = P2Nonconvex.objective;
        } else if (formulation == 3) {
            P3Convex.Solve();
        }

    }



}
