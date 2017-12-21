/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package securitygameslimitsurveil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author teamcore
 */
public class PureStrategies {

    public static int nTargets;
    public static int nResources;
    public static ArrayList<int[]> globalList;
    public static ArrayList<int[]> constrainedList;

    public static void GeneratePureStrategies() {

        Random generator;
        generator = new Random();
        double percentage = 0.5;

        CombinationGenerator cg = new CombinationGenerator(nTargets,
                nResources);
        while (cg.hasMore()) {
            int strategy[] = cg.getNext();
            int[] singleStrategy = new int[nTargets];
            Arrays.fill(singleStrategy, 0);
            for (int i = 0; i < strategy.length; i++) {
                singleStrategy[strategy[i]] = 1;
            }
            globalList.add(singleStrategy);
            if (generator.nextDouble() < percentage) {
                constrainedList.add(singleStrategy);
            }
        }



//        for (int i = 0; i <= nObservations; i++) {
//                ArrayList<Integer> tempList = new ArrayList<Integer>();
//                tempList.add(i);
//                Iterate(tempList, nObservations-i, nTargets-1);
//        }

    }

    public static void init(int numTargets, int numResources) {
        nTargets = numTargets;
        nResources = numResources;
        globalList = new ArrayList<int[]>();
        constrainedList = new ArrayList<int[]>();
    }
}
