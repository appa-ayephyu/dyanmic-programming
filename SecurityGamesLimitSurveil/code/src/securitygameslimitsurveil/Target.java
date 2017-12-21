package securitygameslimitsurveil;

import java.util.Random;

public class Target implements Comparable<Target> {
	public final static int DEF_COV_MIN = 1;
	public final static int DEF_COV_MAX = 10;
	public final static int DEF_UNCOV_MIN = -10;
	public final static int DEF_UNCOV_MAX = -1;
	public final static int ATT_COV_MIN = -10;
	public final static int ATT_COV_MAX = -1;
	public final static int ATT_UNCOV_MIN = 1;
	public final static int ATT_UNCOV_MAX = 10;
	
	private static Random rand = new Random();
	
	private static int counter = 1;
	
	public int id;
	public int securityGameId;
	
	public double defCovPayoff;
	public double defUncovPayoff;
	public double attCovPayoff;
	public double attUncovPayoff;

	public Target(int gameId) {
		this.securityGameId = gameId;
		this.id = counter;
		counter++;
	}

	public Target(int gameId, double defCovPayoff, double defUncovPayoff, double attCovPayoff, double attUncovPayoff) {
		this.securityGameId = gameId;
		this.id = counter;
		counter++;
		this.setPayoffs(defCovPayoff, defUncovPayoff, attCovPayoff, attUncovPayoff);
	}
	
	public Target(int gameId, int targetId, double defCovPayoff, double defUncovPayoff, double attCovPayoff, double attUncovPayoff) {
		this.securityGameId = gameId;
		this.id = targetId;
		counter++;
		this.setPayoffs(defCovPayoff, defUncovPayoff, attCovPayoff, attUncovPayoff);
	}

	public Target(Target target) {
		this.id = target.id;
		this.securityGameId = target.securityGameId;

		this.defCovPayoff = target.defCovPayoff;
		this.defUncovPayoff = target.defUncovPayoff;
		this.attCovPayoff = target.attCovPayoff;
		this.attUncovPayoff = target.attUncovPayoff;
	}
	
	public static void setSeed(long seed){
		rand.setSeed(seed);
	}

	public static void resetCounter() {
		counter = 1;
	}
	
	public double getDeltaDef() {
		return this.defCovPayoff - this.defUncovPayoff;
	}

	public double getDeltaAtt() {
		return this.attCovPayoff - this.attUncovPayoff;
	}

	public void setRandomPayoffs() {
		this.defCovPayoff = (int) (DEF_COV_MIN + (rand.nextDouble() * (DEF_COV_MAX - DEF_COV_MIN)));
		this.defUncovPayoff = (int) (DEF_UNCOV_MIN + (rand.nextDouble() * (DEF_UNCOV_MAX - DEF_UNCOV_MIN)));
		this.attCovPayoff = (int) (ATT_COV_MIN + (rand.nextDouble() * (ATT_COV_MAX - ATT_COV_MIN)));
		this.attUncovPayoff = (int) (ATT_UNCOV_MIN + (rand.nextDouble() * (ATT_UNCOV_MAX - ATT_UNCOV_MIN)));
	}

	public void setPayoffs(double defCovPayoff, double defUncovPayoff, double attCovPayoff, double attUncovPayoff) {
		this.defCovPayoff = defCovPayoff;
		this.defUncovPayoff = defUncovPayoff;
		this.attCovPayoff = attCovPayoff;
		this.attUncovPayoff = attUncovPayoff;
	}
	
	@Override
	public int compareTo(Target arg0) {
		if (this.equals(arg0)) {
			return 0;
		} else if (this.id < arg0.id) {
			return -1;
		} else
			return 1;
	}

	@Override
	public String toString() {
//		return "id=" + id;
		return "Target [securityGameId=" + securityGameId + ", id=" + id
				+ ", defCovPayoff=" + defCovPayoff + ", defUncovPayoff="
				+ defUncovPayoff + ", attCovPayoff=" + attCovPayoff
				+ ", attUncovPayoff=" + attUncovPayoff + "]";
	}
}