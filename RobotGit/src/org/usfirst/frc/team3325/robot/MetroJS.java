package org.usfirst.frc.team3325.robot;

import edu.wpi.first.wpilibj.Joystick;

public class MetroJS
{
	// Analog Inputs (Axes)
	public static final int LEFT_X = 0;
	public static final int LEFT_Y = 1;
	public static final int LT = 2;
	public static final int RT = 3;
	public static final int RIGHT_X = 4;
	public static final int RIGHT_Y = 5;
	// Digital Inputs (Buttons)
	public static final int BUTTON_A = 1, BUTTON_B = 2, BUTTON_X = 3, BUTTON_Y = 4;
	public static final int LB = 5, RB = 6;
	public static final int BUTTON_BACK = 7, BUTTON_START = 8;
	public static final int LEFT_CLICK = 9, RIGHT_CLICK = 10;

	public static final double AXIS_DEADBAND = 0.01;

	public boolean prevA, prevB, prevX, prevY, prevLB, prevRB, prevBack, prevStart, prevLC, prevRC;
	public boolean toggleA, toggleB, toggleX, toggleY, toggleLB, toggleRB, toggleBack, toggleStart, toggleLC, toggleRC;

	public boolean previousButton[];
	public boolean toggleButton[];

	public Joystick joystick;

	public MetroJS(int port)
	{
		joystick = new Joystick(port);

		prevA = false;
		prevB = false;
		prevX = false;
		prevY = false;
		prevLB = false;
		prevRB = false;
		prevBack = false;
		prevStart = false;
		prevLC = false;
		prevRC = false;

		toggleA = false;
		toggleB = false;
		toggleX = false;
		toggleY = false;
		toggleLB = false;
		toggleRB = false;
		toggleBack = false;
		toggleStart = false;
		toggleLC = false;
		toggleRC = false;

		previousButton = new boolean[10];
		toggleButton = new boolean[10];

		for(int i = 0; i < previousButton.length; i++)
		{
			previousButton[i] = false;
		}

		for(int i = 0; i < toggleButton.length; i++)
		{
			toggleButton[i] = false;
		}

	}

	public double getAxis(int axis)
	{
		double value = joystick.getRawAxis(axis);
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

	public int getDPadAngle()
	{
		int angle = joystick.getPOV();
		if(angle == -1)
		{
			return -1;
		}
		else if(angle == 90)
		{
			return 0;
		}
		else if(angle == 180)
		{
			return 270;
		}
		else if(angle == 270)
		{
			return 180;
		}
		else if(angle == 45)
		{
			return 45;
		}
		else if(angle == 135)
		{
			return 315;
		}
		else if(angle == 225)
		{
			return 225;
		}
		else if(angle == 315)
		{
			return 135;
		}
		else if(angle == 0) { return 90; }

		return angle;
	}

	public double getDPadX()
	{
		if(this.getDPadAngle() == -1) { return 0; }
		return Math.cos(Math.toRadians(this.getDPadAngle()));
	}

	public double getDPadY()
	{
		if(this.getDPadAngle() == -1) { return 0; }
		return Math.sin(Math.toRadians(this.getDPadAngle()));
	}

	public boolean getButton(int button)
	{
		boolean buttonVal = joystick.getRawButton(button);
		previousButton[button] = buttonVal;
		/*
		 * if(button == BUTTON_A) prevA = buttonVal; else if(button == BUTTON_B) prevB = buttonVal;
		 * else if(button == BUTTON_X) prevX = buttonVal; else if(button == BUTTON_X) prevY =
		 * buttonVal; else if(button == LB) prevLB = buttonVal; else if(button == RB) prevRB =
		 * buttonVal; else if(button == BUTTON_BACK) prevBack = buttonVal; else if(button ==
		 * BUTTON_START) prevStart = buttonVal; else if(button == LEFT_CLICK) prevLC = buttonVal;
		 * else if(button == RIGHT_CLICK) prevRC = buttonVal;
		 */
		return buttonVal;
	}

	public boolean toggleWhenPressed(int button)
	{
		boolean buttonVal = joystick.getRawButton(button);
		try
		{
			if(!previousButton[button] && buttonVal)
				toggleButton[button] = !toggleButton[button];
			previousButton[button] = buttonVal;
			return toggleButton[button];
		}
		catch(Exception e)
		{
			return false;
		}
		/*
		 * if(button == BUTTON_A) { if(!prevA && buttonVal) { toggleA = !toggleA; } prevA =
		 * buttonVal; return toggleA;
		 * 
		 * } else if(button == BUTTON_B) { if(!prevB && buttonVal) { toggleB = !toggleB; } prevB =
		 * buttonVal; return toggleB; } else if(button == BUTTON_X) { if(!prevX && buttonVal)
		 * toggleX = !toggleX; prevX = buttonVal; return toggleX; } else if(button == BUTTON_Y) {
		 * if(!prevY && buttonVal) toggleY = !toggleY; prevY = buttonVal; return toggleY; } else
		 * if(button == LB) { if(!prevLB && buttonVal) toggleLB = !toggleLB; prevLB = buttonVal;
		 * return toggleLB; } else if(button == RB) { if(!prevRB && buttonVal) toggleRB = !toggleRB;
		 * prevRB = buttonVal; return toggleRB; } else if(button == BUTTON_BACK) { if(!prevBack &&
		 * buttonVal) toggleBack = !toggleBack; prevBack = buttonVal; return toggleBack; } else
		 * if(button == BUTTON_START) { if(!prevStart && buttonVal) toggleStart = !toggleStart;
		 * prevStart = buttonVal; return toggleStart; } else if(button == LEFT_CLICK) { if(!prevLC
		 * && buttonVal) toggleLC = !toggleLC; prevLC = buttonVal; return toggleLC; } else if(button
		 * == RIGHT_CLICK) { if(!prevRC && buttonVal) toggleRC = !toggleRC; prevRC = buttonVal;
		 * return toggleRC; } else { return true; }
		 */
	}
}
