/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package securitygameslimitsurveil;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Scanner;

public class RunSolver {

    private static ArrayList<String> cmd = new ArrayList<String>();
    private static final String SOLVER = "ampl";
    private static Process proc = null;
    private static boolean DEBUG = false;
    public static double time;
    public static double objective;
    public static boolean isInfeasible;

//
//    public static void execute(String filename) {
//        run(filename);
//
//    }
    public static void run(String filename) {
        // set up the external DOBSS command (using glpsol)
        isInfeasible = false;
        cmd.clear();
        cmd.add(SOLVER);
        //cmd.add("-P");
        cmd.add(filename);
        //cmd.add("--math");
        //cmd.add("--memlim");
        //cmd.add(Integer.toString(MEM_LIMIT));
        //cmd.add("--tmlim 1200");
        //cmd.add("--model");
        //cmd.add(modelFile);
        //cmd.add("--data");
        //cmd.add(dataFile);

        try {
            ProcessBuilder pb = new ProcessBuilder(cmd);
            proc = pb.start();
            Thread.yield();
            parseOutput(proc.getInputStream());
            proc.waitFor();

            proc.getInputStream().close();
            proc.getOutputStream().close();
            proc.getErrorStream().close();

        } catch (InterruptedException ie) {
            proc.destroy();
        } catch (Exception e) {
            throw new RuntimeException("Exception while running Solver: " + e.getMessage());
        }
    }

    private static void parseOutput(InputStream is) {

        Scanner s = new Scanner(is);
        while (s.hasNextLine()) {
            String line = s.nextLine();
//            System.out.println(line);

            if (line.startsWith("EXIT:")) {
                if (!(line.startsWith("EXIT: Locally optimal solution found.")) &&
                        !(line.startsWith("EXIT: Optimal solution found."))) {
//                    System.out.println(line);
                    isInfeasible = true;
                    //System.exit(1);
                }
            }

            if (line.indexOf("<BREAK>") >= 0) {
                System.out.println("Out of memory!");
                System.exit(1);
            }

//            if (line.startsWith("EXIT: Current infeasible")) {
//                // Run time
//                isInfeasible = true;
//            }
//
//            if (line.startsWith("EXIT: Terminate at infeasible")) {
//                // Run time
//                isInfeasible = true;
////                            System.out.println(line);
////                            System.exit(1);
////				String[] lines = line.split(":");
////				String time = lines[1].substring(0, lines[1].indexOf("secs"));
////				//totalTime[type] += Double.parseDouble(time);
////
////				if (DEBUG) {
////					System.out.println("time in secs = " + Double.parseDouble(time));
////				}
//                //dfstrategies.put(Integer.parseInt(temp), Double.parseDouble(lines[lines.length-1]));
//            }
//			if(line.startsWith("Memory used"))	      {
//				// Memory
//				String[] lines = line.split(":");
//				String memory = lines[1].substring(0, lines[1].indexOf("M"));
//
//				if (DEBUG) {
//					System.out.println("Memory: " + memory);
//				}
//				//totalMemory[1] = Math.max(Double.parseDouble(memory), totalMemory[1]);
//				maxMemory = Math.max(Double.parseDouble(memory), maxMemory);
//
//				//              System.out.println("memory in M = " + Double.parseDouble(memory));
//				//dfstrategies.put(Integer.parseInt(temp), Double.parseDouble(lines[lines.length-1]));
//			}
            //	      if(line.startsWith("d ="))	      {
            //	    	  // Objective
            //	    	  String[] lines = line.split("=");
            //              //System.out.println("objective = " + Double.parseDouble(lines[lines.length-1]));
            //              if (type == 0) {
            //            	  totalObjective[type] += Double.parseDouble(lines[lines.length-1]);
            //              }
            //              else if (type == 1) {
            //            	  oldObjective = Double.parseDouble(lines[lines.length-1]);
            //              }
            //              //dfstrategies.put(Integer.parseInt(temp), Double.parseDouble(lines[lines.length-1]));
            //	      }
            //
            //	      if((type == 1) && line.startsWith("coverage"))	      {
            //	    	  String[] lines = line.split("=");
            //	    	  String temp = lines[0].substring(lines[0].indexOf("[")+1, lines[0].indexOf("]"));
            //	    	  //System.out.println(temp+"   "+lines[lines.length-1]);
            //	    	  dfstrategies.put(Integer.parseInt(temp), Double.parseDouble(lines[lines.length-1]));
            //	      }


//            if (line.startsWith("coverage")) {
//                String[] lines = line.split("=");
//                String temp = lines[0].substring(lines[0].indexOf("[") + 1, lines[0].indexOf("]"));
//                System.out.println(temp + "   " + lines[lines.length - 1]);
//                coverageOutput.put(Integer.parseInt(temp), Double.parseDouble(lines[lines.length-1]));
////				if (type == 1) {
////					dfstrategies.put(Integer.parseInt(temp), Double.parseDouble(lines[lines.length-1]));
////				}
////				else if (type == 2) {
////					constraintDefendStrategy.put(Integer.parseInt(temp), Double.parseDouble(lines[lines.length-1]));
////				}
//
//            }


//			if(line.startsWith("Z["))	      {
//				String[] lines = line.split("=");
//				String[] lines1 = lines[0].split(",");
//				//String temp = lines[0].substring(lines[0].indexOf("[")+1, lines[0].indexOf("]"));
//				int schedule = Integer.parseInt(lines1[1]);
//				double probability = Double.parseDouble(lines[lines.length-1]);
//				if (probability > 0.0) {
//					System.out.println(schedule+"   "+probability);
//					patrolScheduleOutput.put(schedule, probability);
//				}
//
//			}
//

//
//			if(line.startsWith("coverage"))	      {
//				String[] lines = line.split("=");
//				String temp = lines[0].substring(lines[0].indexOf("[")+1, lines[0].indexOf("]"));
//				//System.out.println(temp+"   "+lines[lines.length-1]);
//				if (type == 1) {
//					dfstrategies.put(Integer.parseInt(temp), Double.parseDouble(lines[lines.length-1]));
//				}
//				else if (type == 2) {
//					constraintDefendStrategy.put(Integer.parseInt(temp), Double.parseDouble(lines[lines.length-1]));
//				}
//
//			}
//
//
//			if(line.startsWith("attack"))	      {
//				String[] lines = line.split("=");
//				String temp = lines[0].substring(lines[0].indexOf("[")+1, lines[0].indexOf("]"));
//
//				//					String target=lines[0].substring(lines[0].indexOf("[")+1, lines[0].indexOf(","));
//				//					String type1 = lines[0].substring(lines[0].indexOf(",")+1, lines[0].indexOf("]"));
//
//				if (type == 1) {
//					atstrategies.put(Integer.parseInt(temp), (int)Double.parseDouble(lines[lines.length-1]));
//				}
//				else if (type == 2) {
//					constraintAttackStrategy.put(Integer.parseInt(temp), (int)Double.parseDouble(lines[lines.length-1]));
//				}
//
//
//				//				atstrategies.put(Integer.parseInt(temp), Integer.parseInt(lines[lines.length-1]));
//				//System.out.println(type+"   "+target);
//
//			}
            //	      if(line.startsWith("defenderPayoff"))	      {
            //	    	  String[] lines = line.split("=");
            //              String temp = lines[0].substring(lines[0].indexOf("[")+1, lines[0].indexOf("]"));
            //              System.out.println(temp+"   "+lines[lines.length-1]);
            //              dfstrategies.put(Integer.parseInt(temp), Double.parseDouble(lines[lines.length-1]));
            //	      }
            //


            //if (index != outputProbs.size()) {
            //  System.out.println("WARNING: index != array size when parsing DOBSS output.");
            //}
            //outputProbs.add(prob);
        }
    }
}
