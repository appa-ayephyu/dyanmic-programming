package basicdatastructures;

public class SurveillancePatrols {
	int[][] surveillanceMatrix;

	public int[][] getSurveillanceMatrix() {
		return surveillanceMatrix;
	}

	public void setSurveillanceMatrix(int[][] surveillanceMatrix) {
		this.surveillanceMatrix = surveillanceMatrix;
	}

	public SurveillancePatrols(int n, int tau) {
		super();
		this.surveillanceMatrix = new int[n][tau];
	}
	
	public void setACellValue(int n, int tau) {
		surveillanceMatrix[n][tau] = 1;
	}
}
