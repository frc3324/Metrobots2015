package org.usfirst.frc.team3325.robot;

import edu.wpi.first.wpilibj.SpeedController;

public class ArmLift
{
	public static SpeedController motor;
	
	public ArmLift(SpeedController motor_) {
		motor = motor_;
	}
	
	public static void up() {
		motor.set(1);
	}
	
	public static void down() {
		motor.set(-1);
	}
	
	public void set(double speed) {
		motor.set(speed);
	}
}
