package org.usfirst.frc.team3324.robot.train;

import org.usfirst.frc.team3324.robot.Motion;
import edu.wpi.first.wpilibj.SpeedController;

/**
 * An abstract class extended by all programmable methods of robotic locomotion
 * 
 * @author Scott Fasone
 */
public abstract class DriveTrain
{
	protected SpeedController flMotor;
	protected SpeedController blMotor;
	protected SpeedController frMotor;
	protected SpeedController brMotor;

	public DriveTrain(SpeedController flMotor, SpeedController blMotor, SpeedController frMotor, SpeedController brMotor)
	{
		this.flMotor = flMotor;
		this.blMotor = blMotor;
		this.frMotor = frMotor;
		this.brMotor = brMotor;
	}

	/**
	 * A drive method that sets each separate motor to a specific motor value
	 * 
	 * @param fl
	 *            Front-Left motor value, [-1, 1]
	 * @param bl
	 *            Back-Left motor value, [-1, 1]
	 * @param fr
	 *            Front-Right motor value, [-1, 1]
	 * @param br
	 *            Back-Right motor value, [-1, 1]
	 * @requires All motor value are between -1 and 1, inclusive
	 */
	public abstract void drive(double fl, double bl, double fr, double br);

	/**
	 * A drive method that propels the robot in a certain direction with a certain fervency<br>
	 * <strong>Note: </strong>
	 * <em>All methods overriding this should check that the Motion provided is legal for the Robot's DriveTrain</em>
	 * 
	 * @param m
	 *            The direction or {@code Motion} in which the robot should move
	 * @param fervency
	 *            An arbitrary value that corresponds to what speed the robot should move, [0, 1]
	 * @requires {@code Motion m} is allowed by the {@code DriveTrain}<br>
	 *           and fervency is from 0 to 1, inclusive
	 * @see org.usfirst.frc.team3324.robot.DriveTrain
	 */
	public abstract void drive(Motion m, double fervency);

	/**
	 * A list of all {@code Motion}s that are allowed by this control system
	 * 
	 * @return A array of legal {@code Motion}s this control can use
	 */
	public abstract Motion[] allowedMotion();

	public boolean isAllowedMotion(Motion motion)
	{
		for(Motion m : this.allowedMotion())
		{
			if(m.equals(motion)) { return true; }
		}
		return false;
	}

	@Override
	public abstract String toString();
}
