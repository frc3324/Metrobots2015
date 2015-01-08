package org.usfirst.frc.team3324.robot;

import static org.usfirst.frc.team3324.robot.MetroJoystick.BUTTON_A;
import static org.usfirst.frc.team3324.robot.MetroJoystick.LEFT_X;
import static org.usfirst.frc.team3324.robot.MetroJoystick.LEFT_Y;
import static org.usfirst.frc.team3324.robot.MetroJoystick.RIGHT_X;
import static org.usfirst.frc.team3324.robot.MetroJoystick.RIGHT_Y;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot
{
	private DriveTrain drive;
	private MetroJoystick stick;
	private Talon fl, bl, fr, br;

	public static enum DriveMode
	{
		TANK(), ARCADE_LEFT(), ARCADE_DUAL();
	}

	public static DriveMode driveState = DriveMode.TANK;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	public void robotInit()
	{
		stick = new MetroJoystick(0);
		fl = new Talon(0);
		bl = new Talon(1);
		fr = new Talon(2);
		br = new Talon(3);
		drive = new DriveTrain(fl, bl, fr, br);
		SmartDashboard.putString("Drive Mode ", driveState.toString());
	}

	/**
	 * This function is called periodically during autonomous
	 */
	public void autonomousPeriodic()
	{
	}

	/**
	 * This function is called periodically during operator control
	 */
	public void teleopPeriodic()
	{
		if (stick.getRawButton(BUTTON_A))
		{
			int index = driveState.ordinal();
			index = driveState.ordinal() == DriveMode.values().length - 1 ? 0
					: index + 1;
			driveState = DriveMode.values()[index];
			SmartDashboard.putString("Drive Mode ", driveState.toString());
			Timer.delay(.2);
		}
		switch (driveState)
		{
			case TANK:
				tankDrive();
				break;
			case ARCADE_LEFT:
				arcadeDriveLeft();
				break;
			case ARCADE_DUAL:
				arcadeDriveDual();
				break;
		}
	}

	/**
	 * This function is called periodically during test mode
	 */
	public void testPeriodic()
	{
	}

	private void tankDrive()
	{
		switch (stick.getPOV())
		{
			case 0:
				drive.tankDrive(1, 1);
				break;
			case 90:
				drive.tankDrive(1, -1);
				break;
			case 180:
				drive.tankDrive(-1, -1);
				break;
			case 270:
				drive.tankDrive(-1, 1);
				break;
			default:
				drive.tankDrive(stick.getAxis(LEFT_Y), stick.getAxis(RIGHT_Y));
		}
	}

	private void arcadeDriveLeft()
	{
		double leftY = stick.getAxis(LEFT_Y);
		double leftX = stick.getAxis(LEFT_X);
		double magnitude = leftY; // Math.sqrt(leftY * leftY + leftX * leftX);
		double angle = 0;
		if (leftX != 0)
		{
			angle = Math.atan(-leftX / leftY);
		}
		angle /= (Math.PI / 2);
		// System.out.println("Magnitude = " + magnitude + "\nAngle = " +
		// angle);
		drive.arcadeDrive(magnitude, angle);
	}

	private void arcadeDriveDual()
	{
		double leftY = stick.getAxis(LEFT_Y);
		double rightX = -stick.getAxis(RIGHT_X);
		drive.arcadeDrive(leftY, rightX);
	}
}
