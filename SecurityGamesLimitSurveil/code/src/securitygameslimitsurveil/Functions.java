/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package securitygameslimitsurveil;

import java.util.ArrayList;
import java.util.Stack;

/**
 *
 * @author teamcore
 */
public class Functions {

    public static long ComputePreProb(Stack<Long> numerator, Stack<Long> denom) {

        long x;
        long b;

        // if numerator or denominator is empty
        if (numerator.isEmpty()) {
            return 1;
        }
        if (denom.isEmpty()) {
            x = 1;
            while (!(numerator.isEmpty())) {
                x = x*numerator.pop();
            }
            return x;
        }

        x = numerator.pop();
        b = denom.peek();

        while (!(numerator.isEmpty()) && !(denom.isEmpty())) {
            if (x % b == 0) {
                x = x/b;
                denom.pop();
                if (!(denom.isEmpty())) {
                    b = denom.peek();
                }
            } else {
                x = x*numerator.pop();
            }
        }

        while (!(numerator.isEmpty())) {
            x = x*numerator.pop();
        }

        while (!(denom.isEmpty())) {
            x = x/denom.pop();
        }

        if (x < 0) {
            System.out.println("Preprob less than 0");
        }
        return x;

    }

    public static int attackSetSize(ArrayList<Double> defStrategy) {
        double tolerance = 0.00001;
        
        int count = 0;
        for (double d : defStrategy) {
            if (d > tolerance) {
                count++;
            }
        }
        return count;
    }

    public static double coverageEntropy(ArrayList<Double> defStrategy) {
        double tolerance = 0.00001;
        double ent = 0;
        
        for (double d : defStrategy) {
            if (d > tolerance) {
                ent += d*(Math.log(d)/Math.log(2));
            }
        }
//        if (-ent < 0.01) {
//            System.out.println(defStrategy);
//        }
        return -ent;
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



}
