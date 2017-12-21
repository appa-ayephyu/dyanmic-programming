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
public class P4CollectionPoints {

    public static ArrayList<ArrayList<Integer>> tempGlobalList;
    public static ArrayList<ArrayList<Double>> collectionPoints;

    public static void Iterate(ArrayList<Integer> currList, int remainingPieces,
            int remainingStrategies) {
        if (remainingStrategies == 1) {
            ArrayList<Integer> tempList = new ArrayList<Integer>();
            tempList.addAll(currList);
            tempList.add(remainingPieces);
            tempGlobalList.add(tempList);
        } else {
            for (int i = 0; i <=remainingPieces; i++) {
                ArrayList<Integer> tempList = new ArrayList<Integer>();
                tempList.addAll(currList);
                tempList.add(i);
                Iterate(tempList, remainingPieces-i, remainingStrategies-1);
            }
        }
    }

    public static void GenerateUniformCollectionPoints(int numStrategies,
            int numPieces) {
        tempGlobalList = new ArrayList<ArrayList<Integer>>();
        collectionPoints = new ArrayList<ArrayList<Double>>();

        for (int i = 0; i <= numPieces; i++) {
                ArrayList<Integer> tempList = new ArrayList<Integer>();
                tempList.add(i);
                Iterate(tempList, numPieces-i, numStrategies-1);
        }

        // Convert the "observation" list of integers to probabilities
        for (ArrayList<Integer> list : tempGlobalList) {
            ArrayList<Double> collectList = new ArrayList<Double>();
            for (int value : list) {
                collectList.add(((double)value)/((double)numPieces));
            }
            collectionPoints.add(collectList);
        }

    }

}
