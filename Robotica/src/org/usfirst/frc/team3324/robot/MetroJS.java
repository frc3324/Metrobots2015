package org.usfirst.frc.team3324.robot;

import edu.wpi.first.wpilibj.Joystick;

public class MetroJS {

	static Joystick joystick;

	static double AXIS_DEADBAND = 0.1;

	static int LEFT_X = 1;
	static int LEFT_Y = 2;
	static int TRIGGER = 3;
	static int RIGHT_X = 4;
	static int RIGHT_Y = 5;

	public int getAxis(int axis) {

		int output;

		if (axis == LEFT_X || axis == RIGHT_X) {

			output = (int) (joystick.getRawAxis(axis) * 127);

		} else {

			output = (int) (-joystick.getRawAxis(axis) * 127);

		}

		if (Math.abs(output) < AXIS_DEADBAND) {
			output = 0;
		}

		return output;

	}

	public MetroJS(int port) {
		joystick = new Joystick(port);
	}
}