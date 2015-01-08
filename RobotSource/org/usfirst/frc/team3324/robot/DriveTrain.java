package org.usfirst.frc.team3324.robot;

import edu.wpi.first.wpilibj.SpeedController;

public class DriveTrain
{
	private SpeedController flMotor;
	private SpeedController blMotor;
	private SpeedController frMotor;
	private SpeedController brMotor;

	public void tankDrive(double left, double right)
	{
		flMotor.set(left);
		blMotor.set(left);
		frMotor.set(-right);
		brMotor.set(-right);
	}

	public void arcadeDrive(double magnitude, double angle)
	{
		double left, right;
		if (magnitude > 0.0)
		{
			if (angle > 0.0)
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
			if (angle > 0.0)
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
		tankDrive(left, right);
	}

	public DriveTrain(SpeedController flMotor_, SpeedController blMotor_,
			SpeedController frMotor_, SpeedController brMotor_)
	{
		flMotor = flMotor_;
		blMotor = blMotor_;
		frMotor = frMotor_;
		brMotor = brMotor_;
	}
}
