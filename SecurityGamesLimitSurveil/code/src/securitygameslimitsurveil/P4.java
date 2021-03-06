/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package securitygameslimitsurveil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 *
 * @author teamcore
 */
public class P4 {

    protected static int nTargets;
    protected static int nObservationSpace;
    protected static int nStrategies;
    protected static int nAffinePoints;
    protected static ArrayList<Double> gList;
    protected static ArrayList<ArrayList<Double>> sampleCoveragePoints;
    protected static double timeAffine;
    protected static ArrayList<ArrayList<Double>> affineB;
    protected static ArrayList<Double> affineE;


    public static void ReadOutputAffine() {
        String timeFile = "timegenAffineP4.txt";
        String bFile = "affine_b.txt";
        String eFile = "affine_e.txt";

        String line = null;

        try {
            BufferedReader timeReader = new BufferedReader(new FileReader(timeFile));
            BufferedReader bReader = new BufferedReader(new FileReader(bFile));
            BufferedReader eReader = new BufferedReader(new FileReader(eFile));

            affineE = new ArrayList<Double>();

            while ((line = eReader.readLine()) != null) {
                affineE.add(Double.parseDouble(line));
            }

            affineB = new ArrayList<ArrayList<Double>>();
                    
            while ((line = bReader.readLine()) != null) {
                StringTokenizer st = new StringTokenizer(line);
                ArrayList<Double> list = new ArrayList<Double>();
                while (st.hasMoreTokens()) {
                    //String s = st.nextToken();
                    list.add(Double.parseDouble(st.nextToken()));
                }
                affineB.add(list);
            }


            if ((line = timeReader.readLine()) != null) {
                timeAffine = Double.parseDouble(line);
            } else {
                System.out.println("Error reading file");
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }



    public static double ObjectiveValue(ArrayList<Double> x) {

        double[] c = new double[nTargets];
        double[] d = new double[nObservationSpace];
        ArrayList<Long> preProb = GenerateGameFiles.preProbList;

        // Compute c_j (or the marginal)
        for (int j = 0; j < nTargets; j++) {

            double coverage = 0.0;
            for (int i = 0; i < nStrategies; i++) {
                int[] strategy = PureStrategies.globalList.get(i);
                coverage += strategy[j]*x.get(i);
            }
            c[j] = coverage;
        }

        // Compute d^o
        for (int i = 0; i < nObservationSpace; i++) {
            int j = P1.attackList.get(i) - 1;
            d[i] = c[j]*(GenerateGameFiles.defCov.get(j) - GenerateGameFiles.defUncov.get(j)) +
                    GenerateGameFiles.defUncov.get(j);
        }

        // Compute objective value
        double objective = 0.0;
        for (int i = 0; i < nObservationSpace; i++) {

            double prod = 1.0;
            for (int j = 0; j < nStrategies; j++) {
                prod = prod * Math.pow(x.get(j), Observations.globalList.get(i).get(j));
            }
            prod = prod * preProb.get(i) * d[i];

            objective += prod;
        }

        return objective;

    }

    public static void GenerateFunctionG() {

        // How to divide the coverage/probabilities of x up
        int numPieces = 10;

        gList = new ArrayList<Double>();
        
        P4CollectionPoints.GenerateUniformCollectionPoints(nStrategies, numPieces);
        
        sampleCoveragePoints = P4CollectionPoints.collectionPoints;

        for (ArrayList<Double> sampleCoverage : P4CollectionPoints.collectionPoints) {
            double objectiveValue = ObjectiveValue(sampleCoverage);
            gList.add(objectiveValue);
        }
    }

    public static void GenerateAffineDataFile(String dataFilename) {

//        DecimalFormat df = new DecimalFormat("#.###");

        try {

            PrintWriter out = new PrintWriter(new File(dataFilename));
            out.println("data;");
            out.println("param nDefStrategies := " + nStrategies + ";");
            out.println("param nColPoints := " + gList.size() + ";");
            out.println("param nFunctionPoints := " + nAffinePoints + ";");

            out.print("param x : ");
            for (int i = 1; i <= nStrategies; i++) {
                out.print(i + " ");
            }
            out.print(":= \n");

            for (int i = 1; i <= sampleCoveragePoints.size(); i++) {
                out.print(i + " ");
                for (double j : sampleCoveragePoints.get(i - 1)) {
                    out.print(j + " ");
                }
                out.print("\n");
            }
            out.println(";");


            out.println("param g");
            for (int i = 1; i <= gList.size(); i++) {
                out.println(i + "  " + gList.get(i - 1));
            }
            out.println(";");


            out.println("end;");

            out.flush();
            out.close();


        } catch (IOException e) {
            throw new RuntimeException("Error writing security game to file: " + e.getMessage());
        }

    }


    public static void GenerateAffineFunction() {

        GenerateFunctionG();

        String affineFilename = "genAffineP4.dat";
        GenerateAffineDataFile(affineFilename);

        String scriptFilename = "genAffineP4.dat";
        RunSolver.run(scriptFilename);
        if (RunSolver.isInfeasible) {
            System.out.println("Infeasible in generate affine");
        }

        ReadOutputAffine();

    }

    public static void Solve() {
        nTargets = GenerateGameFiles.nTargets;
        nObservationSpace = Observations.globalList.size();
        nStrategies = PureStrategies.globalList.size();
        nAffinePoints = 10;

        GenerateAffineFunction();
    }
}
