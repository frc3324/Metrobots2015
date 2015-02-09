package org.usfirst.frc.team3325.robot;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;

public class LinearLift
{
	public static SpeedController motor1;
	public static SpeedController motor2;

	public LinearLift(SpeedController motor1_, SpeedController motor2_)
	{

		motor1 = motor1_;
		motor2 = motor2_;


	}

	public LinearLift(Talon motor1_, Talon motor2_)
	{
		motor1 = motor1_;
		motor2 = motor2_;
	}

	public void set(double power)
	{
		motor1.set(power);
		motor2.set(power);

	}

	public void lower()
	{
		motor1.set(-1);
		motor2.set(-1);

	}

	public void raise()
	{
		motor1.set(1);
		motor2.set(1);
	}
}
