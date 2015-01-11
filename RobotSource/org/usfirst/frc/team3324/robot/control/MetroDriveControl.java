package org.usfirst.frc.team3324.robot.control;

import static org.usfirst.frc.team3324.robot.Robot.gyro;
import org.usfirst.frc.team3324.robot.MetroJoystick;
import org.usfirst.frc.team3324.robot.Robot;
import org.usfirst.frc.team3324.robot.train.DriveTrain;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class MetroDriveControl implements IControl
{
	Joystick stick;
	Timer accelTimer;
	double accelSumX, accelSumY;

	public MetroDriveControl(Joystick stick)
	{
		this.stick = stick;

		accelTimer = new Timer();
		accelTimer.start();

		SmartDashboard.putNumber(" Auto Power, X ", .5);
		SmartDashboard.putNumber(" Auto Power, Y ", .5);
	}

	public void control(DriveTrain chassis)
	{
		if(stick.getRawButton(MetroJoystick.BUTTON_B))
		{
			gyro.reset();
			Timer.delay(.2);
			System.out.println("Gyro Reset");
		}

		switch(Robot.state)
		{
			case TELEOP:
				mecanumDrive(stick.getRawAxis(MetroJoystick.LEFT_Y), stick.getRawAxis(MetroJoystick.LEFT_X), stick.getRawAxis(MetroJoystick.RIGHT_X), chassis);
				break;
			case AUTO:
				double powerX = SmartDashboard.getNumber(" Auto Power, X ");
				double powerY = SmartDashboard.getNumber(" Auto Power, Y ");
				mecanumDrive(powerX, powerY, gyro.getAngle() / 30, chassis);
				break;
			default:
				break;
		}
	}

	public static void mecanumDrive(double y, double x, double turn, DriveTrain chassis)
	{
		double fl = (y + x - turn);
		double bl = (y - x - turn);
		double fr = (y - x + turn);
		double br = (y + x + turn);
		chassis.drive(fl, bl, fr, br);
	}
}
