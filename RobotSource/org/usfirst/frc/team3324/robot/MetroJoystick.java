package org.usfirst.frc.team3324.robot;

import edu.wpi.first.wpilibj.Joystick;

public class MetroJoystick extends Joystick
{
	public static final double AXIS_DEADBAND = 0.04;
	// Analog Inputs (Axes)
	public static final int LEFT_X = 0;
	public static final int LEFT_Y = 1;
	public static final int LEFT_TRIGGER = 2;
	public static final int RIGHT_TRIGGER = 3;
	public static final int RIGHT_X = 4;
	public static final int RIGHT_Y = 5;
	// Digital Inputs (Buttons)
	public static final int BUTTON_A = 1, BUTTON_B = 2, BUTTON_X = 3, BUTTON_Y = 4;
	public static final int BUMPER_LEFT = 5, BUMPER_RIGHT = 6;
	public static final int BUTTON_BACK = 7, BUTTON_START = 8;

	public MetroJoystick(int port)
	{
		super(port);
	}

	@Override
	public double getRawAxis(int axis)
	{
		double value = super.getRawAxis(axis);
		double output = 0;
		if(Math.abs(value) > AXIS_DEADBAND)
		{
			if(axis == LEFT_Y || axis == RIGHT_Y)
			{
				output = -value;
			}
			else
			{
				output = value;
			}
		}
		return output;
	}
}
