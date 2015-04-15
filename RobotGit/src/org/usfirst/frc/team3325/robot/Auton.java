package org.usfirst.frc.team3325.robot;

public class Auton
{

	public static String autonType = "3-Tote";

	public static int autonCount = 0;

	public static void run()
	{
		if(autonType == "3-Tote")
		{
			switch(autonCount)
			{
				case 0:
					Robot.chassis.mecanumDrive(-0.2, 0, 0);
					Robot.autonLift.set(-1);
					if(Robot.autonBottom.get() && Robot.timer.get() > 0.5)
						advanceStep();
					break;
				case 1:
					Robot.autonLift.set(1);
					if(Robot.autonTop.get() && Robot.autonGarage.hasTote() && (Robot.timer.get() > 0.5))
						advanceStep();
					break;
				case 2:
					Robot.autonLift.set(-1);
					if(Robot.autonBottom.get())
						advanceStep();
					break;
				case 3:
					Robot.autonLift.set(1);
					if(Robot.autonTop.get() && Robot.autonGarage.hasTote() && (Robot.timer.get() > 0.5))
					{
						advanceStep();
					}
					break;
				case 4:
					if(Robot.timer.get() > 1)
					{
						advanceStep();
					}
					break;
				case 5:
					Robot.autonLift.set(-1);
					if(Robot.timer.get() > 0.25 && Robot.autonBottom.get())
					{
						advanceStep();
						Robot.chassis.resetGyro();
						Robot.autonLift.set(0);
					}
					break;
				case 6:
					Robot.chassis.drive(0, 0, 0, 0);
					Robot.chassis.setTargetAngle(90);
					if((Robot.chassis.targetAngle - Robot.chassis.getGyro()) > 5 && Robot.timer.get() > 0.5)
					{
						advanceStep();
					}
					break;
			}
		}
		else if(autonType == "Forward")
		{
			switch(autonCount)
			{
				case 0:
					Robot.chassis.mecanumDrive(0.5, 0, 0);
					if(Robot.timer.get() > 1.5)
					{
						advanceStep();
					}
					break;
				case 1:
					Robot.chassis.mecanumDrive(0, 0, 0);
					advanceStep();
					break;
			}
		}
		else if(autonType == "Strafe-Right")
		{
			switch(autonCount)
			{
				case 0:
					Robot.chassis.mecanumDrive(0, 0.5, 0);
					if(Robot.timer.get() > 0.5)
						advanceStep();
					break;
				case 1:
					Robot.chassis.mecanumDrive(0, 0, 0);
					break;
			}
		}
		else if(autonType == "Strafe-Left")
		{
			switch(autonCount)
			{
				case 0:
					Robot.chassis.mecanumDrive(0, -0.5, 0);
					if(Robot.timer.get() > 0.5)
						advanceStep();
					break;
				case 1:
					Robot.chassis.mecanumDrive(0, 0, 0);
					break;
			}
		}
		else if(autonType == "Backward")
		{
			switch(autonCount)
			{
				case 0:
					Robot.chassis.mecanumDrive(-0.5, 0, 0);
					if(Robot.timer.get() > 1.5)
					{
						advanceStep();
					}
					break;
				case 1:
					Robot.chassis.mecanumDrive(0, 0, 0);
					advanceStep();
					break;
			}
		}
		else if(autonType == "F-Tote")
		{
			switch(autonCount)
			{
				case 0:
					Robot.chassis.mecanumDrive(0.5, 0, 0);
					if(Robot.timer.get() > 2.5)
					{
						advanceStep();
					}
					break;
				case 1:
					Robot.chassis.mecanumDrive(0, 0, 0);
					advanceStep();
					break;
			}
		}
		else if(autonType == "BinBackward") {

			switch(autonCount)
			{
				case 0:
					//Robot.armLift.set(1);
					Robot.autonLift.set(1);
					if (Robot.timer.get() > 0.5) {
						advanceStep();
					}
				case 1:
					Robot.chassis.mecanumDrive(0, 0, -1);
					if(Robot.timer.get() > 1)
					{
						advanceStep();
					}
					break;
				case 2:
					Robot.armLift.set(0);
					Robot.chassis.mecanumDrive(1, 0, 0);
					if (Robot.timer.get() > 1.5){
						advanceStep();
					}
					break;
				case 3:
					Robot.chassis.mecanumDrive(0,0,0);
			}
		}
		else
		{

		}
	}

	public static String getAutonType()
	{
		return autonType;
	}

	public static void setAutonType(String s)
	{
		autonType = s;
	}

	public static void setAutonCount(int num)
	{
		autonCount = num;
	}

	public static int getAutonCount()
	{
		return autonCount;
	}

	public static void cycleType()
	{
		if(autonType == "3-Tote")
			autonType = "Forward";
		else if(autonType == "Forward")
			autonType = "Strafe-Right";
		else if(autonType == "Strafe-Right")
			autonType = "Strafe-Left";
		else if(autonType == "Strafe-Left")
			autonType = "Backward";
		else if(autonType == "Backward")
			autonType = "None";
		else if(autonType == "None")
			autonType = "F-Tote";
		else if(autonType == "F-Tote")
			autonType = "BinBackward";
		else
			autonType = "3-Tote";
	}

	private static void advanceStep()
	{
		autonCount++;
		Robot.timer.reset();
		Robot.timer.start();
	}

	private static void CFK()
	{
		// if (Robot.autonKill)
	}

	private static void kill()
	{
		while(Robot.ds.isAutonomous())
		{
			Robot.chassis.drive(0, 0, 0, 0);
			Robot.autonLift.set(0);
			Robot.armLift.set(0);
		}
	}
}
