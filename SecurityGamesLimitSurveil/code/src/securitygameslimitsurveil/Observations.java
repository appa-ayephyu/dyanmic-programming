/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package securitygameslimitsurveil;

import java.util.ArrayList;

/**
 *
 * @author teamcore
 */
public class Observations {

    public static int nTargets;
    public static int nObservations;
    public static ArrayList<ArrayList<Integer>> globalList;

    public static void Iterate(ArrayList<Integer> currList, int remainingObserves,
            int remainingTargets) {
        if (remainingTargets == 1) {
            ArrayList<Integer> tempList = new ArrayList<Integer>();
            tempList.addAll(currList);
            tempList.add(remainingObserves);
            globalList.add(tempList);
        } else {
            for (int i = 0; i <=remainingObserves; i++) {
                ArrayList<Integer> tempList = new ArrayList<Integer>();
                tempList.addAll(currList);
                tempList.add(i);
                Iterate(tempList, remainingObserves -i, remainingTargets-1);
            }
        }
    }

    public static void GenerateObservations() {

       // for (int i = 0; i <= nObservations; i++) {
                ArrayList<Integer> tempList = new ArrayList<Integer>();
       //         tempList.add(i);
                Iterate(tempList, nObservations, nTargets);
       // }

    }

    public static void init(int numTargets, int numObserve) {
        nTargets = numTargets;
        nObservations = numObserve;
        globalList = new ArrayList<ArrayList<Integer>>();
    }
}
