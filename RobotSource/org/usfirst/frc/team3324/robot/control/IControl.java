package org.usfirst.frc.team3324.robot.control;

import org.usfirst.frc.team3324.robot.train.DriveTrain;

/**
 * A functional interface used to control functions of the robot in addition to normal driving code.<br>
 * Implementing classes should create their own objects for the controlling Joystick(s), extra motor
 * controllers, and sensors
 * 
 * @author Scott Fasone
 */
@FunctionalInterface
public interface IControl
{
	/**
	 * A periodic method used to control functions of the robot relative to extra-driving control
	 * 
	 * @param train
	 *            The DriveTrain currently being used by the Robot
	 */
	public void control(DriveTrain train);
}