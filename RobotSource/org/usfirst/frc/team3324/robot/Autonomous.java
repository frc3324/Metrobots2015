package org.usfirst.frc.team3324.robot;

import org.usfirst.frc.team3324.robot.train.DriveTrain;
import edu.wpi.first.wpilibj.Encoder;

public class Autonomous
{
	public static final int TICKS_PER_ROTATION = 360;
	public static final int WHEEL_DIAMETER = 8; // inches
	public static final double TICKS_PER_INCH = TICKS_PER_ROTATION / (Math.PI * WHEEL_DIAMETER);

	Encoder fl, bl, fr, br;
	DriveTrain drive;

	public Autonomous(DriveTrain train, Encoder fl, Encoder bl, Encoder fr, Encoder br)
	{
		this.drive = train;
		this.fl = fl;
		this.bl = bl;
		this.fr = fr;
		this.br = br;
	}

	/**
	 * Has the robot go forward a certain distance
	 * 
	 * @param inches
	 * @param fervency
	 */
	public void goForward(double inches, double fervency)
	{

	}
}
