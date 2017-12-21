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
public class P1 {

    public static ArrayList<ArrayList<Integer>> fullAttackList;
    public static ArrayList<Integer> attackList;
    public static double time;

    public static void ReadOutput() {
        try{
            String timeFile = "timeP1.txt";
            String filename = "attacker_strat.txt";

            String line = null;

            BufferedReader fileReader = new BufferedReader(new FileReader(filename));
            BufferedReader timeReader = new BufferedReader(new FileReader(timeFile));

            while ((line = fileReader.readLine()) != null) {
                StringTokenizer st = new StringTokenizer(line);
                ArrayList<Integer> list = new ArrayList<Integer>();
                int count = 1;
                while (st.hasMoreTokens()) {
                    //String s = st.nextToken();
                    int i = Integer.parseInt(st.nextToken());
                    list.add(i);
                    if (i == 1) {
                        attackList.add(count);
                    }
                    count++;
                }
                fullAttackList.add(list);
            }

            if ((line = timeReader.readLine()) != null) {
                time = Double.parseDouble(line);
            } else {
                System.out.println("Error reading file");
            }


        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
    }

    public static void GenerateAttackerStrategy() {
        fullAttackList = new ArrayList<ArrayList<Integer>>();
        attackList = new ArrayList<Integer>();
        GenerateGameFiles.GenerateFilesP1();

        String scriptFilename = "P1.run";
        RunSolver.run(scriptFilename);
        ReadOutput();
        
    }
}
