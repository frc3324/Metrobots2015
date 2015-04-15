package org.usfirst.frc.team3325.robot;

import edu.wpi.first.wpilibj.Talon;

public class RGBLED {

	public int R, G, B;
	public static Talon rOut, gOut, bOut;

	public RGBLED(int rPort/* , int gPort, int bPort */) {
		rOut = new Talon(rPort);
		/*
		 * gOut = new AnalogOutput(gPort); bOut = new AnalogOutput(bPort);
		 */
	}

	public void set(int R/* , int G, int B */) {

		double RD = (double) (R / 255);
		// double GD = (double) (G / 255);
		// double BD = (double) (B / 255);
		rOut.set(RD);
		// gOut.setVoltage(GD);
		// bOut.setVoltage(BD);

	}
}
