package org.usfirst.frc.team3325.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.SpeedController;

public class ArmLift
{
	public static SpeedController motor;

	public static DigitalInput buttonT, buttonB;

	public ArmLift(SpeedController motor_, DigitalInput buttonTop_, DigitalInput buttonBottom_)
	{
		motor = motor_;
		buttonT = buttonTop_;
		buttonB = buttonBottom_;
	}

	public static void up()
	{
		if(!buttonB.get())
		{
			motor.set(-1);
		}
	}

	public static void down()
	{
		if(buttonT.get())
		{
			motor.set(1);
		}
	}

	public void set(double speed)
	{
		if(speed < 0 && buttonT.get())
		{
			motor.set(-speed);
		}
		else if(speed > 0 && !buttonB.get())
		{
			motor.set(-speed);
		}
		else
		{
			motor.set(0);
		}
	}
}
