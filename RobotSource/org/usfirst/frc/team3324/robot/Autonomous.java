package org.usfirst.frc.team3324.robot;

import static org.usfirst.frc.team3324.robot.Robot.gyro;
import org.usfirst.frc.team3324.robot.control.MetroDriveControl;
import org.usfirst.frc.team3324.robot.train.DriveTrain;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Timer;

public class Autonomous
{
	public static final int TICKS_PER_ROTATION = 360;
	public static final int WHEEL_DIAMETER = 8; // inches
	public static final double TICKS_PER_INCH = TICKS_PER_ROTATION / (Math.PI * WHEEL_DIAMETER);

	Encoder fl, bl, fr, br;
	private DriveTrain chassis;

	public Autonomous(DriveTrain train, Encoder fl, Encoder bl, Encoder fr, Encoder br)
	{
		this.chassis = train;
		this.fl = fl;
		this.bl = bl;
		this.fr = fr;
		this.br = br;

		resetEncoders();
		Robot.gyro.reset();
	}

	/**
	 * Has the robot go forward a certain distance
	 * 
	 * @param inches
	 *            How many inches to proceed forward
	 * @param fervency
	 *            With what speed to go forward
	 * @param gyroCorrect
	 *            If the robot should/can correct it's rotation while it moves
	 */
	public void goForward(double inches, double fervency, boolean gyroCorrect)
	{
		int tickDist = (int) (inches * TICKS_PER_INCH);

		while(fl.get() < tickDist || bl.get() < tickDist || fr.get() < tickDist || br.get() < tickDist)
		{
			if(gyroCorrect)
			{
				MetroDriveControl.mecanumDrive(fervency, 0, gyro.getAngle() / 30, chassis);
			}
			else
			{
				chassis.drive(Motion.FORWARD, fervency);
			}
			Timer.delay(0.01);
		}
		chassis.stop();
		fl.reset();
		bl.reset();
		fr.reset();
		br.reset();
	}

	public void goBackward(double inches, double fervency, boolean gyroCorrect)
	{
		int tickDist = (int) (inches * TICKS_PER_INCH);

		resetEncoders();
		while(-fl.get() < tickDist || -bl.get() < tickDist || -fr.get() < tickDist || -br.get() < tickDist)
		{
			if(gyroCorrect)
			{
				MetroDriveControl.mecanumDrive(-fervency, 0, gyro.getAngle() / 30, chassis);
			}
			else
			{
				chassis.drive(Motion.FORWARD, fervency);
			}
			Timer.delay(0.01);
		}
		chassis.stop();
		resetEncoders();
	}

	/**
	 * Rotates the robot to an angle relative to the gyro's current reference point
	 * 
	 * @param angle
	 *            The angle, in degrees, that the robot should rotate to
	 * @param tolerence
	 *            The degrees of "wiggle room" you're allowing the robot
	 * @requires angle in [-360, 360] and<br>
	 *           tolerance > 0
	 */
	public void rotateTo(double angle, double tolerance)
	{
		if(angle > 180)
		{
			angle -= 360;
		}

		while(Math.abs(gyro.getAngle() - angle) > tolerance)
		{
			MetroDriveControl.mecanumDrive(0, 0, (gyro.getAngle() - angle) / 45, chassis);
		}
	}

	/**
	 * Rotates the Robot to an angle relative to its current position and resets the gyro there
	 * 
	 * @param angle
	 *            The angle, in degrees, that the robot should rotate to
	 * @param tolerance
	 *            The degrees of "wiggle room" you're allowing the robot
	 * @clears {@link Robot#gyro Gyro}
	 */
	public void rotate(double angle, double tolerance)
	{
		Robot.gyro.reset();
		rotateTo(angle, tolerance);
		Robot.gyro.reset();
	}

	/**
	 * Resets all Drive Train Motor Encoders
	 */
	public void resetEncoders()
	{
		fl.reset();
		bl.reset();
		fr.reset();
		br.reset();
	}
}
