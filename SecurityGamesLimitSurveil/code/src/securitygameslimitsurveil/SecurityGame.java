package securitygameslimitsurveil;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Random;

public class SecurityGame implements Comparable<SecurityGame> {
	private static int counter = 1;
	public int id;
	public int numTargets;
	public int numResources;
	public Set<Target> setTargets;
	
	static Random rand = new Random();
	
	public void end() {
		setTargets.clear();
	}
	
	public static void resetCounter(){
		counter = 1;
	}

	public SecurityGame(int numTargets, int numResources) {
		super();
		this.id = counter;
		this.numTargets = numTargets;
		this.numResources = numResources;
		counter++;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public void initializeRandom(long seed) {
		Target.resetCounter();
		Target.setSeed(seed);
		setTargets = new HashSet<Target>();
		
		for (int i = this.setTargets.size(); i < this.numTargets; i++) {
			Target t = new Target(id);
			t.setRandomPayoffs();			
			setTargets.add(t);
		}
	}
	
	public void initializeRandomZeroSum(long seed){
		Target.resetCounter();

		setTargets = new HashSet<Target>();
		
		List<Integer> payoffs = new ArrayList<Integer>();
		
		Random r = new Random(seed);
		
		for (int i = 0; i < this.numTargets; i++) {
			int payoff = (int)(Target.DEF_COV_MIN + (r.nextDouble() * (Target.DEF_COV_MAX - Target.DEF_COV_MIN)));
			payoffs.add(payoff);
		}
		
		Collections.sort(payoffs);
		
		for (int i = this.numTargets - 1; i >= 0; i--) {
			Target t = new Target(id);
			t.attCovPayoff =  -1 * payoffs.get(i);
			t.attUncovPayoff = payoffs.get(i);
			t.defCovPayoff = payoffs.get(i);
			t.defUncovPayoff = -1 * payoffs.get(i);
			
			setTargets.add(t);
		}
	}
	
	public void initializeNoisy(long seed, SecurityGame sg, double noiseLevel){
		Target.resetCounter();

		setTargets = new HashSet<Target>();
		
		Random r = new Random(seed);
		
		List<Target> targetList = new ArrayList<Target>(sg.setTargets);
		Collections.sort(targetList);
		
		for (int i = this.setTargets.size(); i < this.numTargets; i++) {
			Target t = new Target(id);
			t.attCovPayoff = targetList.get(i).attCovPayoff + (r.nextGaussian() * noiseLevel);
			t.attUncovPayoff = targetList.get(i).attUncovPayoff + (r.nextGaussian() * noiseLevel);
			t.defCovPayoff = targetList.get(i).defCovPayoff;
			t.defUncovPayoff = targetList.get(i).defUncovPayoff;
			//t.defCovPayoff = targetList.get(i).defCovPayoff + (r.nextGaussian() * noiseLevel);
			//t.defUncovPayoff = targetList.get(i).defUncovPayoff + (r.nextGaussian() * noiseLevel);
			
			t.attCovPayoff = Math.max(Target.ATT_COV_MIN, Math.min(Target.ATT_COV_MAX, t.attCovPayoff));
			t.attUncovPayoff = Math.max(Target.ATT_UNCOV_MIN, Math.min(Target.ATT_UNCOV_MAX, t.attUncovPayoff));
			//t.defCovPayoff = Math.max(Target.DEF_COV_MIN, Math.min(Target.DEF_COV_MAX, t.defCovPayoff));
			//t.defUncovPayoff = Math.max(Target.DEF_UNCOV_MIN, Math.min(Target.DEF_UNCOV_MAX, t.defUncovPayoff));
			
			t.id = targetList.get(i).id;
			
			setTargets.add(t);
		}
	}

	@Override
	public String toString() {
		String s = "SecurityGame [id=" + id + ", numTargets=" + numTargets
				+ ", numResources=" + numResources + "]\n";

		List<Target> targetList = new ArrayList<Target>(this.setTargets);
		Collections.sort(targetList);

		for (Target t : targetList) {
			s += t.toString() + "\n";
		}
		return s;
	}

	@Override
	public int compareTo(SecurityGame arg0) {
		if (this.equals(arg0)) {
			return 0;
		} else if (this.id < arg0.id) {
			return -1;
		} else
			return 1;
	}
	
	// *********** write random payoff to file ************ //
	public void WriteGame2File (String file_path) throws IOException{
	    
	    FileWriter fstream = new FileWriter(file_path);
	    for (int i=0; i<this.numTargets; i++ ) {
			Target t = new Target(id);
			//t.setRandomPayoffs();			
			fstream.append(Double.toString(t.defCovPayoff));
			fstream.append(',');
			fstream.append(Double.toString(t.defUncovPayoff));
			fstream.append(',');
			fstream.append(Double.toString(t.attUncovPayoff));
			fstream.append(',');
			fstream.append(Double.toString(t.attCovPayoff));
	        fstream.append('\n');
	    }
	    fstream.close();
	}
	
	// *********** load game payoff from file ************ //
	public void LoadGameFromFile (String file_path) throws IOException{
		Target.resetCounter();
		setTargets.clear();
	    try	    {	    	
	    	
		    //create BufferedReader to read csv file
		    BufferedReader br = new BufferedReader( new FileReader(file_path));
		    String strLine = "";
		    //read comma separated file line by line

		    int cntTarget = 0;
		    while( (strLine = br.readLine()) != null)
		    {
		    	
			   String[] stringArray = strLine.split(",");
			   assert stringArray.length==4;
			   
			   Target t = new Target(id);
			   t.defCovPayoff = Double.parseDouble(stringArray[0]);
			   t.defUncovPayoff = Double.parseDouble(stringArray[1]);
			   t.attUncovPayoff = Double.parseDouble(stringArray[2]);
			   t.attCovPayoff = Double.parseDouble(stringArray[3]);
			   
			   setTargets.add(t);
			   cntTarget ++;		   

			}
		    br.close();

		    //System.out.print("numTargets: "+ cntTarget +"\n");
	    }
	    catch(Exception e)
	    {
	    	System.out.println("Exception while reading csv file: " + e);
	    }   
	}
}

