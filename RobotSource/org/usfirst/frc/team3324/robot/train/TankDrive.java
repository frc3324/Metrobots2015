package org.usfirst.frc.team3324.robot.train;

import org.usfirst.frc.team3324.robot.Motion;
import edu.wpi.first.wpilibj.SpeedController;

public class TankDrive extends DriveTrain
{

	public TankDrive(SpeedController flMotor, SpeedController blMotor, SpeedController frMotor, SpeedController brMotor)
	{
		super(flMotor, blMotor, frMotor, brMotor);
	}

	@Override
	public void drive(double fl, double bl, double fr, double br)
	{
		flMotor.set(fl);
		blMotor.set(bl);
		frMotor.set(-fr);
		brMotor.set(-br);
	}

	@Override
	public void drive(Motion motion, double fervency)
	{
		if(!this.isAllowedMotion(motion)) { throw new RobotMotionException(motion, this); }

		switch(motion)
		{
			case FORWARD:
				drive(fervency, fervency, fervency, fervency);
				break;
			case BACKWARD:
				drive(-fervency, -fervency, -fervency, -fervency);
				break;
			case CLOCKWISE:
				drive(-fervency, -fervency, fervency, fervency);
				break;
			case COUNTER_CLOCKWISE:
				drive(fervency, fervency, -fervency, -fervency);
				break;
			default:
				break;
		}
	}

	@Override
	public Motion[] allowedMotion()
	{
		return new Motion[] { Motion.FORWARD, Motion.BACKWARD, Motion.CLOCKWISE, Motion.COUNTER_CLOCKWISE };
	}

	@Override
	public String toString()
	{
		return "Tank Drive";
	}
}
