/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package securitygameslimitsurveil;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author teamcore
 */
public class ReadOutput {

    public static double time;
    public static double objective;

    public static void run() {
        String timeFile = "time.txt";
        String objectiveFile = "objective.txt";

        String line = null;

        try {
            BufferedReader timeReader = new BufferedReader(new FileReader(timeFile));
            BufferedReader objectiveReader = new BufferedReader(new FileReader(objectiveFile));


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
}
