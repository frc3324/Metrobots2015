package org.usfirst.frc.team3324.robot.control;

import org.usfirst.frc.team3324.robot.MetroJoystick;
import org.usfirst.frc.team3324.robot.train.DriveTrain;
import edu.wpi.first.wpilibj.Joystick;

public class ArcadeControl extends RobotControl
{

	public ArcadeControl(DriveTrain train, Joystick stick, IControl... controllers)
	{
		super(train, stick, controllers);
	}

	public ArcadeControl(DriveTrain train, IControl driveControl, IControl... controllers)
	{
		super(train, driveControl, controllers);
	}



	public static void arcadeDrive(double magnitude, double angle, DriveTrain chassis)
	{
		double left, right;
		if(magnitude > 0.0)
		{
			if(angle > 0.0)
			{
				left = magnitude - angle;
				right = Math.max(magnitude, angle);
			}
			else
			{
				left = Math.max(magnitude, -angle);
				right = magnitude + angle;
			}
		}
		else
		{
			if(angle > 0.0)
			{
				left = -Math.max(-magnitude, angle);
				right = magnitude + angle;
			}
			else
			{
				left = magnitude - angle;
				right = -Math.max(-magnitude, -angle);
			}
		}

		chassis.drive(left, left, right, right);
	}

	@Override
	protected void driveCode()
	{
		double magnitude = stick.getRawAxis(MetroJoystick.LEFT_Y);
		double angle = stick.getRawAxis(MetroJoystick.RIGHT_X);
		arcadeDrive(magnitude, angle, chassis);
	}

	@Override
	public String toString()
	{
		return "Arcade Control";
	}
}
