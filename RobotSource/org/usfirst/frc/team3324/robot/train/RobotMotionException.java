package org.usfirst.frc.team3324.robot.train;

import org.usfirst.frc.team3324.robot.Motion;

/**
 * An exception thrown when someone tries to control the Robot an a way not acceptable to the
 * current {@code DriveTrain}
 * 
 * @author Scott Fasone
 * @see DriveTrain
 */
public class RobotMotionException extends RuntimeException
{
	private static final long serialVersionUID = 1L;

	public RobotMotionException(Motion m, DriveTrain dt)
	{
		super("Motion '" + m.toString() + "' is not acceptable for a " + dt.toString() + " robot control");
	}
}
