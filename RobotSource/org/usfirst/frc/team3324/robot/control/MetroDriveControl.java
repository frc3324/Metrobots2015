package org.usfirst.frc.team3324.robot.control;

import org.usfirst.frc.team3324.robot.MetroJoystick;
import org.usfirst.frc.team3324.robot.Robot;
import org.usfirst.frc.team3324.robot.train.DriveTrain;
import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.interfaces.Accelerometer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class MetroDriveControl implements IControl
{
	Joystick stick;
	Gyro gyro;
	Accelerometer accel;
	Timer accelTimer;
	double accelSumX, accelSumY;

	public MetroDriveControl(Joystick stick)
	{
		this.stick = stick;

		this.gyro = new Gyro(0);

		accel = new BuiltInAccelerometer();
		accelTimer = new Timer();
		accelTimer.start();

		SmartDashboard.putNumber(" Auto Time ", 5);
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

		if(!Robot.isAuto)
		{
			mecanumDrive(stick.getRawAxis(MetroJoystick.LEFT_Y), stick.getRawAxis(MetroJoystick.LEFT_X), stick.getRawAxis(MetroJoystick.RIGHT_X), chassis);
		}
		else
		{
			accelSumX += accel.getX();
			accelSumY += accel.getY();

			double powerX = SmartDashboard.getNumber(" Auto Power, X ");
			double powerY = SmartDashboard.getNumber(" Auto Power, Y ");
			mecanumDrive(powerX, powerY, gyro.getAngle() / 30, chassis);
			SmartDashboard.putNumber(" Gyro Angle ", gyro.getAngle());
			SmartDashboard.putNumber(" Accelerometer, X ", accel.getX());
			SmartDashboard.putNumber(" Accelerometer, Y ", accel.getY());
			SmartDashboard.putNumber(" Accelerometer, Z ", accel.getZ());
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
