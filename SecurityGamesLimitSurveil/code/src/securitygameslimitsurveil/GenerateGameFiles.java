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
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Stack;
import java.util.StringTokenizer;

/**
 *
 * @author teamcore
 */
public class GenerateGameFiles {
    public static int nTargets;
    public static int nObserves;
    public static int nResources;
    public static ArrayList<Double> defCov;
    public static ArrayList<Double> defUncov;
    public static ArrayList<Double> attCov;
    public static ArrayList<Double> attUncov;
    public static ArrayList<Long> preProbList;
    public static ArrayList<Double> alpha;

    public static void GenerateDobssDataFile(String filename) {
        try {
            PrintWriter out = new PrintWriter(new File(filename));

            out.write("param n1 := " + PureStrategies.globalList.size() + ";\n");
            out.write("param n2 := " + nTargets + ";\n");
            out.write("param ADV := 1;\n");
            out.write("param MM := 80000;\n");
            out.write("param p\n");
            out.write("1 1.0;\n");
            out.write("\n");

            //String tempStr = "";
            out.write("param Reward\n \n");
            String tempStr = "";
            for (int i = 1; i <= nTargets; i++) {
                tempStr = tempStr + i + " ";
            }
            out.write("[1, *, *]: " + tempStr + ":= \n");

            tempStr = "";
            for (int i = 1; i <= PureStrategies.globalList.size(); i++) {
                tempStr = tempStr + i + " ";
                int[] strat = PureStrategies.globalList.get(i - 1);
                for (int j = 0; j < nTargets; j++) {
                    if (strat[j] == 0) {
                        tempStr = tempStr + defUncov.get(j) + " ";
                    } else {
                        tempStr = tempStr + defCov.get(j) + " ";
                    }
                }
                if (i < PureStrategies.globalList.size()) {
                    out.write(tempStr + "\n");
                } else {
                    out.write(tempStr);
                }
                tempStr = "";
            }
            out.write(";\n \n");

            out.write("param AdvReward\n \n");
            tempStr = "";
            for (int i = 1; i <= nTargets; i++) {
                tempStr = tempStr + i + " ";
            }
            out.write("[1, *, *]: " + tempStr + ":= \n");

            tempStr = "";
            for (int i = 1; i <= PureStrategies.globalList.size(); i++) {
                tempStr = tempStr + i + " ";
                int[] strat = PureStrategies.globalList.get(i - 1);
                for (int j = 0; j < nTargets; j++) {
                    if (strat[j] == 0) {
                        tempStr = tempStr + attUncov.get(j) + " ";
                    } else {
                        tempStr = tempStr + attCov.get(j) + " ";
                    }
                }
                if (i < PureStrategies.globalList.size()) {
                    out.write(tempStr + "\n");
                } else {
                    out.write(tempStr);
                }
                tempStr = "";
            }

            out.write(";\n \n");

            out.flush();
            out.close();
        }
        catch (IOException e) {
            throw new RuntimeException("Error writing data to file: " + e.getMessage());
        }
    }

    public static void GenerateDobssBinaryDataFile(String filename) {
        try {
            PrintWriter out = new PrintWriter(new File(filename));

            out.write("param n1 := " + PureStrategies.globalList.size() + ";\n");
            out.write("param n2 := " + nTargets + ";\n");
            out.write("param ADV := 1;\n");
            out.write("param MM := 80000;\n");
            out.write("param p\n");
            out.write("1 1.0;\n");
            out.write("\n");

            //String tempStr = "";
            out.write("param Reward\n \n");
            String tempStr = "";
            for (int i = 1; i <= nTargets; i++) {
                tempStr = tempStr + i + " ";
            }
            out.write("[1, *, *]: " + tempStr + ":= \n");

            tempStr = "";
            for (int i = 1; i <= PureStrategies.globalList.size(); i++) {
                tempStr = tempStr + i + " ";
                int[] strat = PureStrategies.globalList.get(i - 1);
                for (int j = 0; j < nTargets; j++) {
                    if (strat[j] == 0) {
                        tempStr = tempStr + (-attUncov.get(j)) + " ";
                    } else {
                        tempStr = tempStr + (-attCov.get(j)) + " ";
                    }
                }
                if (i < PureStrategies.globalList.size()) {
                    out.write(tempStr + "\n");
                } else {
                    out.write(tempStr);
                }
                tempStr = "";
            }
            out.write(";\n \n");

            out.write("param AdvReward\n \n");
            tempStr = "";
            for (int i = 1; i <= nTargets; i++) {
                tempStr = tempStr + i + " ";
            }
            out.write("[1, *, *]: " + tempStr + ":= \n");

            tempStr = "";
            for (int i = 1; i <= PureStrategies.globalList.size(); i++) {
                tempStr = tempStr + i + " ";
                int[] strat = PureStrategies.globalList.get(i - 1);
                for (int j = 0; j < nTargets; j++) {
                    if (strat[j] == 0) {
                        tempStr = tempStr + attUncov.get(j) + " ";
                    } else {
                        tempStr = tempStr + attCov.get(j) + " ";
                    }
                }
                if (i < PureStrategies.globalList.size()) {
                    out.write(tempStr + "\n");
                } else {
                    out.write(tempStr);
                }
                tempStr = "";
            }
            out.write(";\n \n");


            out.flush();
            out.close();


        } catch (IOException e) {
            throw new RuntimeException("Error writing data to file: " + e.getMessage());
        }

    }

    public static void GenerateP2ModelFile(String modelFilename) {
        try {
            PrintWriter out = new PrintWriter(new File(modelFilename));
            out.println("param nTargets;");
            out.println("param observationSpace;");
            out.println("param nDefStrategies;");
            out.println("set targets := {1..nTargets};");
            out.println("set observations := {1..observationSpace};");
            out.println("set defstrategies := {1..nDefStrategies};");
            out.println("param Ospace {observations, defstrategies};");
            out.println("param preProb {observations};");
            out.println("param A {defstrategies, targets};");
            out.println("param defenderCoveredPayoff {targets};");
            out.println("param defenderUncoveredPayoff {targets};");
            out.println("param attackerUncoveredPayoff {targets};");
            out.println("param attackerCoveredPayoff {targets};");
            out.println(" ");
            out.println("var x {defstrategies} >= 0, <= 1;");
            out.println("var marginal {targets};");
            out.println("var d{observations};");
            out.println(" ");

            //out.println("maximize objective: sum{o in observations}(preProb[o]*(coverage[1]^Ospace[o,1] * coverage[2]^Ospace[o,2] ) * d[o]);");
            out.print("maximize objective: sum{o in observations}(preProb[o]*(");
            out.print("x[1]^Ospace[o,1]");

            for (int i = 2; i <= PureStrategies.globalList.size(); i++) {
                out.print(" * x[" + i + "]^Ospace[o," + i + "]");
            }

            out.print(" ) * d[o]);");

            out.println(" ");
            out.println("subject to");
            out.println(" ");
            out.println("coverage: sum {i in defstrategies} x[i] = 1;");
            out.println("computeMarginals{t in targets}: marginal[t] = sum {i in defstrategies} x[i] * A[i,t];");

            if (P1.attackList.size() != Observations.globalList.size()) {
                System.out.println("Size of attack list from P1 not the right size: "
                        + P1.attackList.size() + ", " + Observations.globalList.size());
                System.exit(1);
            }

            for (int i = 1; i <= Observations.globalList.size(); i++) {
                int target = P1.attackList.get(i - 1);
                out.println("bound" + i + ": d[" + i + "] = marginal[" + target
                        + "] * (defenderCoveredPayoff[" + target
                        + "] - defenderUncoveredPayoff[" + target
                        + "]) + defenderUncoveredPayoff[" + target + "];");
            }

            out.flush();
            out.close();
        }
        catch (IOException e) {
            throw new RuntimeException("Error writing P2 model to file: " + e.getMessage());
        }
    }

    public static void GenerateP2SpeedupModelFile(String modelFilename) {
        try {
            PrintWriter out = new PrintWriter(new File(modelFilename));
            out.println("param nTargets;");
            out.println("param observationSpace;");
            out.println("param nDefStrategies;");
            out.println("set targets := {1..nTargets};");
            out.println("set observations := {1..observationSpace};");
            out.println("set defstrategies := {1..nDefStrategies};");
            out.println("param Ospace {observations, defstrategies};");
            out.println("param preProb {observations};");
            out.println("param A {defstrategies, targets};");
            out.println("param defenderCoveredPayoff {targets};");
            out.println("param defenderUncoveredPayoff {targets};");
            out.println("param attackerUncoveredPayoff {targets};");
            out.println("param attackerCoveredPayoff {targets};");
            out.println(" ");
            out.println("var x {defstrategies} >= 0, <= 1;");
            out.println("var marginal {targets};");
            out.println("var d{observations};");
            out.println(" ");



//            out.println("maximize objective: sum{o in observations}(preProb[o]*(coverage[1]^Ospace[o,1] * coverage[2]^Ospace[o,2] ) * d[o]);");
            out.print("maximize objective: sum{o in observations}(preProb[o]*(");
            out.print("x[1]^Ospace[o,1]");

            for (int i = 2; i <= PureStrategies.globalList.size(); i++) {
                out.print(" * x[" + i + "]^Ospace[o," + i + "]");
            }

            out.print(" ) * d[o]);");


            out.println(" ");
            out.println("subject to");
            out.println(" ");
            out.println("coverage: sum {i in defstrategies} x[i] = 1;");
            out.println("computeMarginals{t in targets}: marginal[t] = sum {i in defstrategies} x[i] * A[i,t];");

            if (P1.attackList.size() != Observations.globalList.size()) {
                System.out.println("Size of attack list from P1 not the right size: "
                        + P1.attackList.size() + ", " + Observations.globalList.size());
                System.exit(1);
            }

            for (int i = 1; i <= Observations.globalList.size(); i++) {
                int target = P1.attackList.get(i - 1);
                out.println("bound" + i + ": d[" + i + "] = marginal[" + target
                        + "] * (defenderCoveredPayoff[" + target
                        + "] - defenderUncoveredPayoff[" + target
                        + "]) + defenderUncoveredPayoff[" + target + "];");
            }

            HashSet<Integer> attackSet = new HashSet<Integer>(P1.attackList);

            for (int i = 1; i <= PureStrategies.globalList.size(); i++) {
                if (!(attackSet.contains(i))) {
                    out.println("prop1bound" + i + ": x[" + i + "] = 0;");
                }
            }

            for (int i = 1; i < PureStrategies.globalList.size(); i++) {
                out.println("prop2bound" + i + ": x[" + i + "] >= x[" + (i + 1) + "];");
            }

            out.flush();
            out.close();


        } catch (IOException e) {
            throw new RuntimeException("Error writing P2 model to file: " + e.getMessage());
        }
    }

    public static void GenerateP3ModelFile(String modelFilename) {
        try {
            PrintWriter out = new PrintWriter(new File(modelFilename));
            out.println("param nTargets;");
            out.println("param observationSpace;");
            out.println("param nDefStrategies;");
            out.println("set targets := {1..nTargets};");
            out.println("set observations := {1..observationSpace};");
            out.println("set defstrategies := {1..nDefStrategies};");
            out.println("param Ospace {observations, defstrategies};");
            out.println("param preProb {observations};");
            out.println("param A {defstrategies, targets};");
            out.println("param defenderCoveredPayoff {targets};");
            out.println("param defenderUncoveredPayoff {targets};");
            out.println("param attackerUncoveredPayoff {targets};");
            out.println("param attackerCoveredPayoff {targets};");
            out.println(" ");
            out.println("var x {defstrategies} >= 0, <= 1;");
            out.println("var marginal {targets};");
            out.println("var d{observations};");
            out.println(" ");



            out.println("minimize objective: sum{o in observations}(-log(preProb[o]) - sum {i in defstrategies}(Ospace[o,i] * log(x[i])) - log(d[o]));");

            out.println(" ");
            out.println("subject to");
            out.println(" ");
            out.println("coverage: sum {i in defstrategies} x[i] = 1;");
            out.println("computeMarginals{t in targets}: marginal[t] = sum {i in defstrategies} x[i] * A[i,t];");

            for (int i = 1; i <= Observations.globalList.size(); i++) {
                int target = P1.attackList.get(i - 1);
                out.println("bound" + i + ": d[" + i + "] = marginal[" + target
                        + "] * (defenderCoveredPayoff[" + target
                        + "] - defenderUncoveredPayoff[" + target
                        + "]) + defenderUncoveredPayoff[" + target + "];");
            }

            out.flush();
            out.close();


        } catch (IOException e) {
            throw new RuntimeException("Error writing P3 model to file: " + e.getMessage());
        }
    }

    public static void GenerateP2DataFile(String dataFilename) {

        DecimalFormat df = new DecimalFormat("#.###");

        try {

            PrintWriter out = new PrintWriter(new File(dataFilename));
            out.println("data;");
            out.println("param nTargets := " + nTargets + ";");
            out.println("param observationSpace := " + Observations.globalList.size() + ";");
            out.println("param nDefStrategies := " + PureStrategies.globalList.size() + ";");

            out.print("param Ospace : ");
            for (int i = 1; i <= PureStrategies.globalList.size(); i++) {
                out.print(i + " ");
            }
            out.print(":= \n");

            for (int i = 1; i <= Observations.globalList.size(); i++) {
                out.print(i + " ");
                for (int j : Observations.globalList.get(i - 1)) {
                    out.print(j + " ");
                }
                out.print("\n");
            }
            out.println(";");


            out.println("param preProb");

            for (int i = 1; i <= Observations.globalList.size(); i++) {
                out.println(i + "  " + preProbList.get(i - 1));
            }
            out.println(";");


            out.print("param A : ");
            for (int i = 1; i <= nTargets; i++) {
                out.print(i + " ");
            }
            out.print(":= \n");

            for (int i = 1; i <= PureStrategies.globalList.size(); i++) {
                out.print(i + " ");

                int[] strategy = PureStrategies.globalList.get(i - 1);
                for (int j = 0; j < nTargets; j++) {
                    out.print(strategy[j] + " ");
                }
                out.print("\n");
            }
            out.println(";");

            out.println("param defenderCoveredPayoff");
            for (int i = 1; i <= nTargets; i++) {
                out.println(i + "  " + defCov.get(i - 1));
            }
            out.println(";");

            out.println("param defenderUncoveredPayoff");
            for (int i = 1; i <= nTargets; i++) {
                out.println(i + "  " + defUncov.get(i - 1));
            }
            out.println(";");

            out.println("param attackerCoveredPayoff");
            for (int i = 1; i <= nTargets; i++) {
                out.println(i + "  " + (attCov.get(i - 1)));
            }
            out.println(";");

            out.println("param attackerUncoveredPayoff");
            for (int i = 1; i <= nTargets; i++) {
                out.println(i + "  " + (attUncov.get(i - 1)));
            }
            out.println(";");

            out.println("end;");

            out.flush();
            out.close();


        } catch (IOException e) {
            throw new RuntimeException("Error writing security game to file: " + e.getMessage());
        }
    }

    public static void GenerateP2BinaryDataFile(String dataFilename) {

        DecimalFormat df = new DecimalFormat("#.###");

        try {

            PrintWriter out = new PrintWriter(new File(dataFilename));
            out.println("data;");
            out.println("param nTargets := " + nTargets + ";");
            out.println("param observationSpace := " + Observations.globalList.size() + ";");
            out.println("param nDefStrategies := " + PureStrategies.globalList.size() + ";");

            out.print("param Ospace : ");
            for (int i = 1; i <= PureStrategies.globalList.size(); i++) {
                out.print(i + " ");
            }
            out.print(":= \n");

            for (int i = 1; i <= Observations.globalList.size(); i++) {
                out.print(i + " ");
                for (int j : Observations.globalList.get(i - 1)) {
                    out.print(j + " ");
                }
                out.print("\n");
            }
            out.println(";");


            out.println("param preProb");

            for (int i = 1; i <= Observations.globalList.size(); i++) {
                out.println(i + "  " + preProbList.get(i - 1));
            }
            out.println(";");


            out.print("param A : ");
            for (int i = 1; i <= nTargets; i++) {
                out.print(i + " ");
            }
            out.print(":= \n");

            for (int i = 1; i <= PureStrategies.globalList.size(); i++) {
                out.print(i + " ");

                int[] strategy = PureStrategies.globalList.get(i - 1);
                for (int j = 0; j < nTargets; j++) {
                    out.print(strategy[j] + " ");
                }
                out.print("\n");
            }
            out.println(";");


//            ArrayList<Integer> defCov = new ArrayList<Integer>();
//            ArrayList<Integer> defUncov = new ArrayList<Integer>();

            out.println("param defenderCoveredPayoff");
            for (int i = 1; i <= nTargets; i++) {
                out.println(i + "  " + (-attCov.get(i - 1)));
            }
            out.println(";");

            out.println("param defenderUncoveredPayoff");
            for (int i = 1; i <= nTargets; i++) {
                out.println(i + "  " + (-attUncov.get(i - 1)));
            }
            out.println(";");

            out.println("param attackerCoveredPayoff");
            for (int i = 1; i <= nTargets; i++) {
                out.println(i + "  " + (attCov.get(i - 1)));
            }
            out.println(";");

            out.println("param attackerUncoveredPayoff");
            for (int i = 1; i <= nTargets; i++) {
                out.println(i + "  " + (attUncov.get(i - 1)));
            }
            out.println(";");

            out.println("end;");

            out.flush();
            out.close();


        } catch (IOException e) {
            throw new RuntimeException("Error writing security game to file: " + e.getMessage());
        }

    }

    private static long factorial(long b) {
        if (b <= 1) {
            return 1;
        } else {
            return b * factorial(b - 1);
        }
    }

    protected static void GeneratePayoffsProp3(int numTargets) {

        defCov = new ArrayList<Double>();
        defUncov = new ArrayList<Double>();
        attCov = new ArrayList<Double>();
        attUncov = new ArrayList<Double>();

        for (int i = 1; i <= numTargets; i++) {
//            defCov.add(generator.nextInt(payoffMagnitude) + 1);
            defCov.add(0.0);
            attCov.add(0.0);
        }

        defUncov.add(-80.0);
        defUncov.add(-60.0);
        defUncov.add(-49.0);

        // Zero sum game
        for (int i = 0; i < numTargets; i++) {
            attUncov.add(-defUncov.get(i));
        }

    }

    protected static void GenerateHardcodedPayoffs(int numTargets) {

        defCov = new ArrayList<Double>();
        defUncov = new ArrayList<Double>();
        attCov = new ArrayList<Double>();
        attUncov = new ArrayList<Double>();

        // Test for Gaurav

        defCov.add(8.0);
        defCov.add(3.0);
        defCov.add(5.0);
        defCov.add(8.0);
        defCov.add(2.0);

        defUncov.add(-5.0);
        defUncov.add(-1.0);
        defUncov.add(-10.0);
        defUncov.add(-8.0);
        defUncov.add(-4.0);

        attUncov.add(3.0);
        attUncov.add(10.0);
        attUncov.add(10.0);
        attUncov.add(10.0);
        attUncov.add(10.0);

        attCov.add(-4.0);
        attCov.add(-1.0);
        attCov.add(-6.0);
        attCov.add(-5.0);
        attCov.add(-7.0);
    }

    protected static void GenerateConj3Payoffs(int numTargets, int payoffTarget3) {
        int payoffMagnitude = 100;

        Random generator;
        generator = new Random();

        defCov = new ArrayList<Double>();
        defUncov = new ArrayList<Double>();
        attCov = new ArrayList<Double>();
        attUncov = new ArrayList<Double>();

        for (int i = 1; i <= numTargets; i++) {
//            defCov.add(generator.nextInt(payoffMagnitude) + 1);
            defCov.add(0.0);
            attCov.add(0.0);
        }

        defUncov.add(-100.0);
        defUncov.add(-100.0);
        defUncov.add((double) -payoffTarget3);


        //Collections.sort(defUncov);

        // Zero sum game
        for (int i = 0; i < numTargets; i++) {
            attUncov.add(-defUncov.get(i));
        }

    }

    protected static void GenerateClosePayoffs(int numTargets) {
//        int payoffMagnitude = 100;

        defCov = new ArrayList<Double>();
        defUncov = new ArrayList<Double>();
        attCov = new ArrayList<Double>();
        attUncov = new ArrayList<Double>();

        for (int i = 1; i <= numTargets; i++) {
//            defCov.add(generator.nextInt(payoffMagnitude) + 1);
            defCov.add(0.0);
            attCov.add(0.0);
        }

        defUncov.add(-90.0);
        defUncov.add(-88.0);
        defUncov.add(-86.0);
        defUncov.add(-84.0);


        //Collections.sort(defUncov);

        // Zero sum game
        for (int i = 0; i < numTargets; i++) {
            attUncov.add(-defUncov.get(i));
        }

    }

    protected static void GenerateSampleHardcodedPayoffs(int numTargets) {
//        int payoffMagnitude = 100;

        defCov = new ArrayList<Double>();
        defUncov = new ArrayList<Double>();
        attCov = new ArrayList<Double>();
        attUncov = new ArrayList<Double>();

        for (int i = 1; i <= numTargets; i++) {
//            defCov.add(generator.nextInt(payoffMagnitude) + 1);
            defCov.add(0.0);
            attCov.add(0.0);
        }

        defUncov.add(-80.0);
        defUncov.add(-60.0);
        defUncov.add(-40.0);


        //Collections.sort(defUncov);

        // Zero sum game
        for (int i = 0; i < numTargets; i++) {
            attUncov.add(-defUncov.get(i));
        }

    }

    protected static void GenerateSpacedPayoffs(int numTargets) {
        int payoffMagnitude = 100;

        defCov = new ArrayList<Double>();
        defUncov = new ArrayList<Double>();
        attCov = new ArrayList<Double>();
        attUncov = new ArrayList<Double>();

        for (int i = 1; i <= numTargets; i++) {
//            defCov.add(generator.nextInt(payoffMagnitude) + 1);
            defCov.add(0.0);
            attCov.add(0.0);
        }

//        defUncov.add(-100.0);
//        defUncov.add(-80.0);
//        defUncov.add(-60.0);
//        defUncov.add(-40.0);
        defUncov.add(-100.0);
        defUncov.add(-90.0);
        defUncov.add(-80.0);
        defUncov.add(-70.0);


        //Collections.sort(defUncov);

        // Zero sum game
        for (int i = 0; i < numTargets; i++) {
            attUncov.add(-defUncov.get(i));
        }

    }

    protected static void GenerateClusterPayoffs(int numTargets) {
        int payoffMagnitude = 100;

        defCov = new ArrayList<Double>();
        defUncov = new ArrayList<Double>();
        attCov = new ArrayList<Double>();
        attUncov = new ArrayList<Double>();

        for (int i = 1; i <= numTargets; i++) {
//            defCov.add(generator.nextInt(payoffMagnitude) + 1);
            defCov.add(0.0);
            attCov.add(0.0);
        }

        defUncov.add(-90.0);
        defUncov.add(-80.0);
        defUncov.add(-60.0);
        defUncov.add(-50.0);


        //Collections.sort(defUncov);

        // Zero sum game
        for (int i = 0; i < numTargets; i++) {
            attUncov.add(-defUncov.get(i));
        }

    }

    protected static void GenerateZeroSumPayoffs(int numTargets) {
        int payoffMagnitude = 100;

        Random generator;
        generator = new Random();

        defCov = new ArrayList<Double>();
        defUncov = new ArrayList<Double>();
        attCov = new ArrayList<Double>();
        attUncov = new ArrayList<Double>();

        for (int i = 1; i <= numTargets; i++) {
//            defCov.add(generator.nextInt(payoffMagnitude) + 1);
            defCov.add(0.0);
            defUncov.add(-generator.nextInt(payoffMagnitude) - 1.0);
        }

        Collections.sort(defUncov);

        // Zero sum game
        for (int i = 0; i < numTargets; i++) {
            attCov.add(-defCov.get(i));
            attUncov.add(-defUncov.get(i));
        }



//
//        for (int i = 1; i <= numTargets; i++) {
//            defCov.add(generator.nextInt(payoffMagnitude) + 101);
//            defUncov.add(generator.nextInt(payoffMagnitude));
//
//            attCov.add(generator.nextInt(payoffMagnitude));
//            attUncov.add(generator.nextInt(payoffMagnitude) + 101);
//        }

    }

    protected static void SetPayoffsVaryTargets(int numTargets,
            ArrayList<Double> tempDefCov, ArrayList<Double> tempDefUncov,
            ArrayList<Double> tempAttCov, ArrayList<Double> tempAttUncov) {

        defCov = new ArrayList<Double>();
        defUncov = new ArrayList<Double>();
        attCov = new ArrayList<Double>();
        attUncov = new ArrayList<Double>();

        for (int i = 0; i < numTargets; i++) {
            defCov.add(tempDefCov.get(i));
            defUncov.add(tempDefUncov.get(i));
            attCov.add(tempAttCov.get(i));
            attUncov.add(tempAttUncov.get(i));
        }

    }

    protected static void GeneratePositiveNegativePayoffs(int numTargets) {
        int payoffMagnitude = 100;

        Random generator;
        generator = new Random();

        defCov = new ArrayList<Double>();
        defUncov = new ArrayList<Double>();
        attCov = new ArrayList<Double>();
        attUncov = new ArrayList<Double>();

        for (int i = 1; i <= numTargets; i++) {
            defCov.add(generator.nextInt(payoffMagnitude) + 1.0);
            defUncov.add(-generator.nextInt(payoffMagnitude) - 1.0);
            attCov.add(-generator.nextInt(payoffMagnitude) - 1.0);
            attUncov.add(generator.nextInt(payoffMagnitude) + 1.0);
        }

//        System.out.println(attCov);
//        System.out.println(attUncov);

    }

    protected static void GeneratePayoffs(int numTargets) {
        int payoffMagnitude = 100;

        Random generator;
        generator = new Random();

        defCov = new ArrayList<Double>();
        defUncov = new ArrayList<Double>();
        attCov = new ArrayList<Double>();
        attUncov = new ArrayList<Double>();

//        for (int i = 1; i <= nTargets; i++) {
//            defCov.add(generator.nextInt(payoffMagnitude) + 1);
//            defUncov.add(-generator.nextInt(payoffMagnitude) - 1);
//        }
//
//        // Zero sum game
//        for (int i = 0; i < nTargets; i++) {
//            attCov.add(-defCov.get(i));
//            attUncov.add(-defUncov.get(i));
//        }


        for (int i = 1; i <= numTargets; i++) {
            defCov.add(generator.nextInt(payoffMagnitude) + 101.0);
            defUncov.add((double) generator.nextInt(payoffMagnitude));

            attCov.add((double) generator.nextInt(payoffMagnitude));
            attUncov.add(generator.nextInt(payoffMagnitude) + 101.0);
        }

    }

    public static void GeneratePreProbs() {

        // Compute tau factorial/numerator for preprob
        preProbList = new ArrayList<Long>();

        Stack<Long> numerator = new Stack<Long>();
        for (long i = 1; i <= nObserves; i++) {
            numerator.push(i);
        }

//        ArrayList<Long> denom = new ArrayList<Long>();
        long prod = 1;

        for (int i = 1; i <= Observations.globalList.size(); i++) {
            prod = 1;
            Stack<Long> denom = new Stack<Long>();
            for (int j : Observations.globalList.get(i - 1)) {
                if (j > 1) {
                    for (long k = 1; k <= j; k++) {
                        denom.push(k);
                    }
                }
            }
            preProbList.add(Functions.ComputePreProb((Stack<Long>) numerator.clone(), denom));

//            denom.add(prod);
        }
//        System.out.println(preProbList);

    }

    public static void GenerateP1DataFile(String dataFilename) {
//        double alpha = 0;
//        int nResources = 1;

        DecimalFormat df = new DecimalFormat("#.###");

        try {

            PrintWriter out = new PrintWriter(new File(dataFilename));
            out.println("data;");
            out.println("param nTargets := " + nTargets + ";");
            out.println("param nResources :=" + nResources + ";");
            out.println("param nObservations := " + nObserves + ";");
            out.println("param observationSpace := " + Observations.globalList.size() + ";");
            out.println("param nDefStrategies := " + PureStrategies.globalList.size() + ";");
            out.println("param alphaD");

            if (alpha.size() != PureStrategies.globalList.size()) {
                System.out.println("alpha size incorrect: " + alpha.size() + ", " + PureStrategies.globalList.size());
                System.exit(1);
            }

            double sumAlpha = 0.0;

            for (int i = 1; i <= PureStrategies.globalList.size(); i++) {
                out.println(i + "  " + alpha.get(i - 1));
                sumAlpha += alpha.get(i - 1);
            }
            out.println(";");

            out.println("param sumAlphaD := " + sumAlpha + ";");

            out.print("param Ospace : ");
            for (int i = 1; i <= PureStrategies.globalList.size(); i++) {
                out.print(i + " ");
            }
            out.print(":= \n");


            for (int i = 1; i <= Observations.globalList.size(); i++) {
                out.print(i + " ");
                for (int j : Observations.globalList.get(i - 1)) {
                    out.print(j + " ");
                }
                out.print("\n");
            }
            out.println(";");


            out.println("param preProb");

//            preProbList = new ArrayList<Long>();
//
//            long observationFactorial = factorial(nObserves);

            for (int i = 1; i <= Observations.globalList.size(); i++) {
                out.println(i + "  " + preProbList.get(i - 1));
            }
            out.println(";");


            out.print("param A : ");
            for (int i = 1; i <= nTargets; i++) {
                out.print(i + " ");
            }
            out.print(":= \n");

            for (int i = 1; i <= PureStrategies.globalList.size(); i++) {
                out.print(i + " ");

                int[] strategy = PureStrategies.globalList.get(i - 1);
                for (int j = 0; j < nTargets; j++) {
                    out.print(strategy[j] + " ");
                }
                out.print("\n");
            }
            out.println(";");


//            ArrayList<Integer> defCov = new ArrayList<Integer>();
//            ArrayList<Integer> defUncov = new ArrayList<Integer>();

            out.println("param defenderCoveredPayoff");
            for (int i = 1; i <= nTargets; i++) {
                out.println(i + "  " + defCov.get(i - 1));
            }
            out.println(";");

            out.println("param defenderUncoveredPayoff");
            for (int i = 1; i <= nTargets; i++) {
                out.println(i + "  " + defUncov.get(i - 1));
            }
            out.println(";");

            out.println("param attackerCoveredPayoff");
            for (int i = 1; i <= nTargets; i++) {
                out.println(i + "  " + (attCov.get(i - 1)));
            }
            out.println(";");

            out.println("param attackerUncoveredPayoff");
            for (int i = 1; i <= nTargets; i++) {
                out.println(i + "  " + (attUncov.get(i - 1)));
            }
            out.println(";");


//            out.println("param defenderCoveredPayoff");
////            for (int i = 1; i <= nTargets; i++) {
//                out.println("1  0");
//                out.println("2  0");
////            }
//            out.println(";");
//
//            out.println("param defenderUncoveredPayoff");
//                out.println("1  -100");
//                out.println("2  -20");
//            out.println(";");
//
//            out.println("param attackerCoveredPayoff");
//                out.println("1  0");
//                out.println("2  0");
//            out.println(";");
//
//            out.println("param attackerUncoveredPayoff");
//                out.println("1  100");
//                out.println("2  20");
//            out.println(";");



            out.println("end;");

            out.flush();
            out.close();


        } catch (IOException e) {
            throw new RuntimeException("Error writing security game to file: " + e.getMessage());
        }

    }

    public static void GenerateFilesP2() {
        String dataFilename = "P2_nonconvex.dat";
        String modelFilename = "P2_nonconvex.mod";

        //GenerateP2ModelFile(modelFilename);
        GenerateP2DataFile(dataFilename);
    }

    public static void GenerateFilesP2Speedup() {
        String dataFilename = "P2_nonconvex_speedup.dat";
        String modelFilename = "P2_nonconvex_speedup.mod";

        GenerateP2SpeedupModelFile(modelFilename);
        GenerateP2DataFile(dataFilename);
    }

    public static void GenerateFilesP2Binary() {
        String dataFilename = "P2_nonconvex_binary.dat";
        String modelFilename = "P2_nonconvex.mod";

        GenerateP2ModelFile(modelFilename);
        GenerateP2BinaryDataFile(dataFilename);
    }

    public static void GenerateFilesP3() {
        String dataFilename = "P3_convex.dat";
        String modelFilename = "P3_convex.mod";

        GenerateP3ModelFile(modelFilename);
        // Use the same data file as P2
        GenerateP2DataFile(dataFilename);

    }

    public static void ReadDobssDataFile(String filename, double multiplicationFactor) {
        try {

//            String timeFile = "timeP1.txt";
//            String filename = "attacker_strat.txt";

            String line = null;

            ArrayList<ArrayList<Double>> defMatrix = new ArrayList<ArrayList<Double>>();
            ArrayList<ArrayList<Double>> attMatrix = new ArrayList<ArrayList<Double>>();
            ArrayList<int[]> pureStrategyMatrixA = new ArrayList<int[]>();
            HashMap<Integer, Double> dCov = new HashMap<Integer, Double>();
            HashMap<Integer, Double> dUncov = new HashMap<Integer, Double>();
            HashMap<Integer, Double> aCov = new HashMap<Integer, Double>();
            HashMap<Integer, Double> aUncov = new HashMap<Integer, Double>();
            boolean isDefenderMatrix = false;
            boolean isAttackerMatrix = false;
            int numPureStrategies = 0;
            int numAdvActions = 0;
            int numResources = 0;


            BufferedReader fileReader = new BufferedReader(new FileReader(filename));



//            int[] singleStrategy = new int[nTargets];
//            Arrays.fill(singleStrategy, 0);
//            for (int i = 0; i < strategy.length; i++) {
//                singleStrategy[strategy[i]] = 1;
//            }
//            globalList.add(singleStrategy);


            while ((line = fileReader.readLine()) != null) {

                if (line.startsWith("param n1")) {
                    String[] lines = line.split("=");
                    numPureStrategies = Integer.parseInt(lines[1].substring(1, lines[1].indexOf(";")));
                } else if (line.startsWith("param n2")) {
                    String[] lines = line.split("=");
                    numAdvActions = Integer.parseInt(lines[1].substring(1, lines[1].indexOf(";")));
                } else if (line.startsWith("param Reward")) {

                    if (numAdvActions == 0) {
                        System.out.println("Was unable to read input file " + filename);
                        System.exit(1);
                    }
                    isDefenderMatrix = true;
                } else if (line.startsWith("param AdvReward")) {
                    if (numAdvActions == 0) {
                        System.out.println("Was unable to read input file " + filename);
                        System.exit(1);
                    }
                    isAttackerMatrix = true;
                } else if (line.startsWith(";")) {
                    isDefenderMatrix = false;
                    isAttackerMatrix = false;
                } else if (isDefenderMatrix) {
                    if (!(line.startsWith("["))) {
                        StringTokenizer st = new StringTokenizer(line);
                        ArrayList<Double> list = new ArrayList<Double>();
                        int[] singleStrategy = new int[numAdvActions];
                        int strategyNum = Integer.parseInt(st.nextToken());
                        int count = 0;
                        while (st.hasMoreTokens()) {
                            //String s = st.nextToken();
                            double i = multiplicationFactor
                                    * Double.parseDouble(st.nextToken());
                            list.add(i);
                            if (i == 0) {
                                System.out.println("Payoff is zero");
                            }
                            if (i > 0) {
                                singleStrategy[count] = 1;
                                if (!(dCov.containsKey(count))) {
                                    dCov.put(count, i);
                                }
                            } else {
                                singleStrategy[count] = 0;
                                if (!(dUncov.containsKey(count))) {
                                    dUncov.put(count, i);
                                }
                            }
                            count++;
                        }
                        pureStrategyMatrixA.add(singleStrategy);
                        defMatrix.add(list);
                    }

                } else if (isAttackerMatrix) {
                    if (!(line.startsWith("["))) {
                        StringTokenizer st = new StringTokenizer(line);
                        ArrayList<Double> list = new ArrayList<Double>();
                        int strategyNum = Integer.parseInt(st.nextToken());
                        int count = 0;
                        while (st.hasMoreTokens()) {
                            //String s = st.nextToken();
                            double i = multiplicationFactor
                                    * Double.parseDouble(st.nextToken());
//                            double i = Double.parseDouble(st.nextToken());
                            list.add(i);
                            if (i == 0) {
                                System.out.println("Payoff is zero");
                            }
                            if (i > 0) {
                                if (!(aUncov.containsKey(count))) {
                                    aUncov.put(count, i);
                                }
                            } else {
                                if (!(aCov.containsKey(count))) {
                                    aCov.put(count, i);
                                }
                            }
                            count++;

                        }
                        attMatrix.add(list);
                    }
                }
            }

            // Count the number of resources
            int sum = 0;
            int[] tempStrategy = pureStrategyMatrixA.get(0);
            for (int i = 0; i < numAdvActions; i++) {
                sum += tempStrategy[i];
            }
            numResources = sum;
            nTargets = numAdvActions;
//    public static int nObserves;
            nResources = numResources;
            defCov = new ArrayList<Double>();
            defUncov = new ArrayList<Double>();
            attCov = new ArrayList<Double>();
            attUncov = new ArrayList<Double>();

            for (int i = 0; i < nTargets; i++) {
                defCov.add(dCov.get(i));
                defUncov.add(dUncov.get(i));
                attCov.add(aCov.get(i));
                attUncov.add(aUncov.get(i));
            }

            // Set Pure Strategy
            PureStrategies.init(nTargets, nResources);
            PureStrategies.globalList = pureStrategyMatrixA;


//            System.out.println("numPureStrategies " + numPureStrategies
//                    + ", numAdvActions" + numAdvActions
//                    + ", defMatrix" + defMatrix
//                    + ", attMatrix" + attMatrix);

//            for (int[] i : pureStrategyMatrixA) {
//                for (int j = 0; j < numAdvActions; j++) {
//                    System.out.print(i[j] + ",");
//                }
//                System.out.println("");
//            }
//
//            System.out.println(dCov);
//            System.out.println(dUncov);
//            System.out.println(aCov);
//            System.out.println(aUncov);



        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Initially created to be used when reading Dobss file
     * @param numObservations
     */
    public static void InitComputeObservations(int numObservations) {

        nObserves = numObservations;

        Observations.init(PureStrategies.globalList.size(), numObservations);
        Observations.GenerateObservations();

        GeneratePreProbs();
    }

    public static void InitStrategiesObservations(int numTargets, int numObservations, int numResources) {
    	nTargets = numTargets;
        nObserves = numObservations;
        nResources = numResources;

        PureStrategies.init(numTargets, numResources);
        PureStrategies.GeneratePureStrategies();

        Observations.init(PureStrategies.globalList.size(), numObservations);
        Observations.GenerateObservations();

        GeneratePreProbs();
    }

    // option = 0, means uniform.  Option = 1, means based off SSE
    public static void GeneratePrior(double maxAlpha, int option) {
        alpha = new ArrayList<Double>();

        if (option == 0) {
            for (int i = 0; i < PureStrategies.globalList.size(); i++) {
                alpha.add(maxAlpha);
            }

        } else if (option == 1) {

            DobssBinary.Solve();
            ArrayList<Double> cov = (ArrayList<Double>) DobssBinary.defStrategy.clone();
            double maxCov = Collections.max(cov);
//        System.out.println(cov);
//        System.out.println(maxCov);
            double factor = maxAlpha / maxCov;

            for (double i : cov) {
                alpha.add(i * factor);
            }
        } else if (option == 2) {

            // Hybrid Prior
            DobssBinary.Solve();
            ArrayList<Double> cov = (ArrayList<Double>) DobssBinary.defStrategy.clone();
            double maxCov = Collections.max(cov);
//        System.out.println(cov);
//        System.out.println(maxCov);
            double factor = (maxAlpha / 2) / maxCov;

            for (double i : cov) {
                alpha.add(i * factor + (maxAlpha / 2));
            }
        }

//        System.out.println(alpha);
    }

    public static void GenerateFilesP1() {
        String dataFilename = "P1.dat";
        //String modelFilename = "";

        //GeneratePayoffs();

        //GenerateModelFile(modelFilename);
        GenerateP1DataFile(dataFilename);
    }
}