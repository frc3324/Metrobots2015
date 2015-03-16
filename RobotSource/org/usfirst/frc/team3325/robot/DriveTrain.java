package org.usfirst.frc.team3325.robot;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.SpeedController;

public class DriveTrain
{
	public static SpeedController fl, bl, fr, br;

	public static Encoder flEncoder, blEncoder, frEncoder, brEncoder;

	public static Gyro gyro, tilt;

	public static boolean isFieldOriented, isHoldAngle, isSlowDrive;

	public static double targetAngle, targetTilt;

	public static int flInv, blInv, frInv, brInv;

	public static int driveType;

	public static final int TANK_DRIVE = 0;
	public static final int MECANUM_DRIVE = 1;
	public static final int ARCADE_DRIVE = 2;

	public DriveTrain(SpeedController fl_, SpeedController bl_, SpeedController fr_, SpeedController br_, Encoder flEncoder_, Encoder blEncoder_, Encoder frEncoder_, Encoder brEncoder_, Gyro gyro_, Gyro tilt_)
	{
		fl = fl_;
		bl = bl_;
		fr = fr_;
		br = br_;

		flEncoder = flEncoder_;
		blEncoder = blEncoder_;
		frEncoder = frEncoder_;
		brEncoder = brEncoder_;

		flEncoder.reset();
		blEncoder.reset();
		frEncoder.reset();
		brEncoder.reset();

		gyro = gyro_;
		tilt = tilt_;

		isFieldOriented = false;
		isHoldAngle = false;

		targetAngle = 0;
		targetTilt = 0;

		frInv = 1;
		brInv = 1;
		flInv = 1;
		blInv = 1;

		driveType = 1;
	}

	public static void tankDrive(double left, double right)
	{
		fl.set(left * frInv);
		bl.set(left * brInv);
		fr.set(right * flInv);
		br.set(right * blInv);
	}

	public void mecanumDrive(double y, double x, double turn)
	{

		if(isFieldOriented)
		{

			double gAngle = gyro.getAngle();
			double cosA = Math.cos(gAngle * 3.1415926535 / 180);
			double sinA = Math.sin(gAngle * 3.1415926535 / 180);

			x = (x * cosA) - (y * sinA);
			y = (x * sinA) + (y * cosA);

		}

		if(isHoldAngle)
		{

			turn = turn + ((targetAngle - gyro.getAngle()) / 20);

		}

		/*if(Math.abs(targetTilt - tilt.getAngle()) > 5)
		{
			if(tilt.getAngle() > 0)
			{
				y = y - ((targetTilt - tilt.getAngle()) / 50);
			}
			else
			{
				y = y + ((targetTilt - tilt.getAngle()) / 50);
			}
		}*/
		
		turn = turn / 3;
		if (isSlowDrive) {
			fl.set((y + x + turn) * flInv * 0.6);
			bl.set((y - x + turn) * blInv * 0.6);
			fr.set((y - x - turn) * frInv * 0.6);
			br.set((y + x - turn) * brInv * 0.6);
		} else {
			fl.set((y + x + turn) * flInv);
			bl.set((y - x + turn) * blInv);
			fr.set((y - x - turn) * frInv);
			br.set((y + x - turn) * brInv);
		}

	}

	public void drive(double lx, double ly, double rx, double ry)
	{
		if(driveType == 0)
		{
			tankDrive(ly, ry);
		}
		else if(driveType == 1)
		{
			mecanumDrive(ly, lx, rx);
		}
		else if(driveType == 2)
		{
			// Insert Arcade Drive
		}
		else
		{
			stop();
		}
	}

	public void setInvertedMotors(boolean fl, boolean bl, boolean fr, boolean br)
	{
		flInv = fl ? -1 : 1;
		blInv = bl ? -1 : 1;
		frInv = fr ? -1 : 1;
		brInv = br ? -1 : 1;
	}

	public void setSlowDrive(boolean val)
	{
		isSlowDrive = val;
	}

	public boolean isSlowDrive()
	{
		return isSlowDrive;
	}

	public void setHoldAngle(boolean val)
	{
		isHoldAngle = val;
	}

	public boolean isHoldAngle()
	{
		return isHoldAngle;
	}

	public void setFieldOriented(boolean val)
	{
		isFieldOriented = val;
	}

	public boolean isFieldOriented()
	{
		return isFieldOriented;
	}

	public int getEncoder(Encoder e)
	{
		return e.get();
	}

	public void resetEncoders()
	{
		flEncoder.reset();
		blEncoder.reset();
		frEncoder.reset();
		brEncoder.reset();
	}

	public double getGyro()
	{
		return gyro.getAngle();
	}

	public void resetGyro()
	{
		gyro.reset();
	}

	public void setTargetAngle(double angle)
	{
		targetAngle = angle;
	}

	public double getTilt()
	{
		return tilt.getAngle();
	}

	public void resetTilt()
	{
		tilt.reset();
	}

	public void setTargetTilt(double angle)
	{
		targetTilt = angle;
	}

	public int getDistanceMoved()
	{
		return (flEncoder.get() + blEncoder.get() + frEncoder.get() + brEncoder.get()) / 4;
	}

	public void stop()
	{
		fl.set(0);
		bl.set(0);
		fr.set(0);
		br.set(0);
	}

	public void setDriveType(int type)
	{
		driveType = type;
	}

	public String getDriveType()
	{
		if(driveType == 0)
		{
			return "Tank Drive";
		}
		else if(driveType == 1)
		{
			return "Mecanum Drive";
		}
		else if(driveType == 2)
		{
			return "Arcade Drive";
		}
		else
		{
			return "Stopped";
		}
	}

}
