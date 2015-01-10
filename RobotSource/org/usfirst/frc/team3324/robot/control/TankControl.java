package org.usfirst.frc.team3324.robot.control;

import static org.usfirst.frc.team3324.robot.MetroJoystick.LEFT_Y;
import static org.usfirst.frc.team3324.robot.MetroJoystick.RIGHT_Y;
import org.usfirst.frc.team3324.robot.Motion;
import org.usfirst.frc.team3324.robot.train.DriveTrain;
import edu.wpi.first.wpilibj.Joystick;

public class TankControl extends RobotControl
{


	public TankControl(DriveTrain train, Joystick stick, IControl... controllers)
	{
		super(train, stick, controllers);
	}

	public TankControl(DriveTrain train, IControl driveControl, IControl... controllers)
	{
		super(train, driveControl, controllers);
	}



	@Override
	public void driveCode()
	{
		switch(stick.getPOV())
		{
			case 0:
				chassis.drive(Motion.FORWARD, 1);
				break;
			case 90:
				chassis.drive(Motion.CLOCKWISE, 1);
				break;
			case 180:
				chassis.drive(Motion.BACKWARD, 1);
				break;
			case 270:
				chassis.drive(Motion.COUNTER_CLOCKWISE, 1);
				break;
			default:
				chassis.drive(stick.getRawAxis(LEFT_Y), stick.getRawAxis(LEFT_Y), stick.getRawAxis(RIGHT_Y), stick.getRawAxis(RIGHT_Y));
		}
	}

	@Override
	public String toString()
	{
		return "Tank Control";
	}
}