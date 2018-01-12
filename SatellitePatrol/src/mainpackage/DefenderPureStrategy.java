package mainpackage;

public class DefenderPureStrategy {
	double[][][] imagingStatus;
	
	public DefenderPureStrategy(int numberOfSatellites, int totalTime, int totalImagingTask) {
		imagingStatus = new double[numberOfSatellites][totalImagingTask][totalTime];
	}
}
