package org.usfirst.frc.team3325.robot;

public class Auton
{

	public static String autonType = "None";

	public static int autonCount = 0;

	public static void run()
	{
		if(autonType == "3-Tote")
		{
			switch(autonCount)
			{
				case 0:
					Robot.chassis.mecanumDrive(-0.25, 0, 0);
					Robot.autonLift.set(-0.75);
					if(Robot.autonBottom.getVoltage() > 0.1 && Robot.timer.get() > 0.5)
						advanceStep();
					break;
				case 1:
					Robot.autonLift.set(1.5);
					if(Robot.autonTop.get() && (Robot.autonHasTote.getVoltage() < 0.01) && (Robot.timer.get() > 0.5))
						advanceStep();
					break;
				case 2:
					Robot.autonLift.set(-1.5);
					if(Robot.autonBottom.getVoltage() > 0.1)
						advanceStep();
					break;
				case 3:
					Robot.autonLift.set(1.5);
					if(Robot.autonTop.get() && (Robot.autonHasTote.getVoltage() < 0.01) && (Robot.timer.get() > 0.5))
					{
						advanceStep();
					}
					break;
				case 4:
					Robot.autonLift.set(-1.5);
					if (Robot.timer.get() > 0.5)
						advanceStep();
					break;
				case 5:
					Robot.chassis.mecanumDrive(0, 0, 1);
					if(Robot.timer.get() > 1)
					{
						advanceStep();
					}
					break;
				case 6:
					Robot.chassis.mecanumDrive(0.25, 0, 0);
					if(Robot.timer.get() > 1.5)
						advanceStep();
					break;
				case 7:
					Robot.chassis.mecanumDrive(0, 0, 0);
					if(Robot.timer.get() > 0.25)
					{
						advanceStep();
					}
					break;
				case 8:
					Robot.chassis.mecanumDrive(-0.25, 0, 0);
					if(Robot.timer.get() > 0.5)
						advanceStep();
					break;
				case 9:
					Robot.chassis.mecanumDrive(0, 0, 0);
					advanceStep();
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
		else
			autonType = "3-Tote";
	}

	private static void advanceStep()
	{
		autonCount++;
		Robot.timer.reset();
		Robot.timer.start();
	}
}
