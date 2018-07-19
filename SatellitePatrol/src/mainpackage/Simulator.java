package mainpackage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import basicdatastructures.SurveillancePatrols;

public class Simulator {
	
	
	public static void main(String[] args) {
		
		Game game = new Game(2, 2, 6); // tentative
		game.m = 2;
		new CDOG().run(game, false, true);
//		double startingtime = System.nanoTime();
//		int numberRuns = 1;
//		for (int t = 16; t <= 31; t = t + 5) {
//			for (int m = 1; m <= 21; m = m + 2) {
//				double gtime = System.nanoTime();
//				for (int k = 0; k < numberRuns; k++) {
//					Game game = new Game(3, 3, 12); // tentative
//					game.m = m;
//					new CDOG().run(game, false, true);
//				}
//				System.out.println(m + "," + t + "," + (((System.nanoTime() - gtime) / 1000000000) / numberRuns));
//			}
//		}
//		System.out.println("Time:" + ((System.nanoTime() - startingtime) / 1000000000));
	}

	/*
	public static void main2(String[] args) {
		double startingtime = System.nanoTime();
		int numberRuns = 30; //multiple number of runs
		int t = 10; //tau
		for(int j=0; j<=10; j++) {
			double gtime = System.nanoTime();
			for(int k=0; k<numberRuns; k++) {
				Game game = new Game(5,5,t);
				game.epsilon = j * 0.1; //tuning of detecting probability
				new CDOG().run(game, false, true);
			}
			System.out.println(j + "," + (((System.nanoTime() - gtime) / 1000000000) / numberRuns));
		}
		System.out.println("Time:" + ((System.nanoTime() - startingtime) / 1000000000));
	}
	
	public static void main1(String[] args) {
		double startingtime = System.nanoTime();
		int numberRuns = 30;
		int t=10;
		double gtime = System.nanoTime();
		for(int k= 0; k< numberRuns; k++) {
			Game game = new Game(5,5,t);
			List<Integer> targets = new ArrayList<Integer>();
			for(int tar =0; tar<game.getNumberOfNodes(); tar++) {
				targets.add(tar);
			}
			game.targets = targets;
			new CDOG().run(game, false, true);
		}
		System.out.println(t + "," + (((System.nanoTime() - gtime) / 1000000000) / numberRuns));
		System.out.println("Time:" + ((System.nanoTime() - startingtime) / 1000000000));
	}
	*/
}
