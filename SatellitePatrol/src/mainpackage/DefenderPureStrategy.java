package mainpackage;

public class DefenderPureStrategy {
	Integer[][][] imagingStatus;
	
	public DefenderPureStrategy(int numberOfSatellites, int totalTime, int totalImagingTask) {
		imagingStatus = new Integer[numberOfSatellites][totalImagingTask][totalTime];
	}
}
