package org.usfirst.frc.team3325.robot;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;

public class LinearLift
{
	private static SpeedController motor;
	private static AnalogInput bottom;
	private static DigitalInput top;

	public LinearLift(SpeedController motor_, AnalogInput bottom_, DigitalInput top_)
	{

		motor = motor_;
		bottom = bottom_;
		top = top_;
	}

	public LinearLift(Talon motor_)
	{
		motor = motor_;
	}

	public void set(double value)
	{
		if((value > 0 && !top.get()) || (value < 0 && bottom.getVoltage() < 0.2))
		{
			motor.set(value/2);
		}
		else
		{
			motor.set(0);
		}

	}

	public void lower()
	{
		if(bottom.getVoltage() < 0.2)
		{
			motor.set(-1 / 2);
		}
		else
		{
			motor.set(0);
		}

	}

	public void raise()
	{
		if(!top.get())
		{
			motor.set(1 / 2);
		}
		else
		{
			motor.set(0);
		}
	}
}
