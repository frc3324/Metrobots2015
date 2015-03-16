package org.usfirst.frc.team3325.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.SpeedController;

public class ArmLift
{
	public static SpeedController motor;

	public static DigitalInput button;

	public ArmLift(SpeedController motor_, DigitalInput button_)
	{
		motor = motor_;
		button = button_;
	}

	public static void up()
	{
		motor.set(-1);
	}

	public static void down()
	{
		if(!button.get())
		{
			motor.set(1);
		}
	}

	public void set(double speed)
	{
		if(speed < 0 && button.get())
		{
			motor.set(-speed);
		}
		else if(speed > 0)
		{
			motor.set(-speed);
		}
		else
		{
			motor.set(0);
		}
	}
}
