package org.usfirst.frc.team3324.robot.control;

import static org.usfirst.frc.team3324.robot.MetroJoystick.LEFT_X;
import static org.usfirst.frc.team3324.robot.MetroJoystick.LEFT_Y;
import static org.usfirst.frc.team3324.robot.MetroJoystick.RIGHT_X;
import org.usfirst.frc.team3324.robot.Motion;
import org.usfirst.frc.team3324.robot.train.DriveTrain;
import edu.wpi.first.wpilibj.Joystick;

public class HolonomicControl extends RobotControl
{
	public HolonomicControl(DriveTrain train, Joystick stick, IControl... controllers)
	{
		super(train, stick, controllers);
	}

	public HolonomicControl(DriveTrain train, IControl driveControl, IControl... controllers)
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
				chassis.drive(Motion.RIGHT, 1);
				break;
			case 180:
				chassis.drive(Motion.BACKWARD, 1);
				break;
			case 270:
				chassis.drive(Motion.LEFT, 1);
				break;
			default:
				double rotate = stick.getRawAxis(RIGHT_X);
				double leftY = stick.getRawAxis(LEFT_Y);
				double leftX = stick.getRawAxis(LEFT_X);
				chassis.drive((leftY + leftX - rotate), (leftY - leftX - rotate), (leftY - leftX + rotate), (leftY + leftX + rotate));
		}
	}

	@Override
	public String toString()
	{
		return "Holonomic Control";
	}
}