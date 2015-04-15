package org.usfirst.frc.team3325.robot;

import edu.wpi.first.wpilibj.AnalogInput;

public class GarageSensor
{
	public AnalogInput input;
	public double deadband;

	public GarageSensor(int AnalogPort)
	{
		input = new AnalogInput(AnalogPort);
		deadband = 3.75;
	}

	public boolean beamBroken()
	{
		if(input.getVoltage() < deadband)
		{
			return false;
		}
		else
		{
			return true;
		}
	}

	public void setDeadband(double deadband_)
	{
		deadband = deadband_;
	}

	public boolean hasTote()
	{
		return beamBroken();
	}

	public boolean beamConnected()
	{
		return !beamBroken();
	}

	public double getVoltage()
	{
		return input.getVoltage();
	}
}
