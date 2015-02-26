package org.usfirst.frc.team3325.robot;

import edu.wpi.first.wpilibj.Encoder;

public class MetroEncoderConversion
{	
	public static final double FOOT_CONVERSION = 6.2831853071795864769252867665592;
	public static final double METER_CONVERSION = 1.9151148816283379581668274064472;
	
	public static double toFeet(Encoder e) {
		return e.get() / FOOT_CONVERSION;
	}
	
	public static double toMeters(Encoder e) {
		return e.get() / METER_CONVERSION;
	}
	
	
}
