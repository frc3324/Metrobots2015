package org.usfirst.frc.team3325.robot;

import edu.wpi.first.wpilibj.DigitalInput;

public class Vision {
	DigitalInput pin1; DigitalInput pin2;
	boolean v0, v1;
	
	public Vision(){
		pin1 = new DigitalInput(0);
		pin2 = new DigitalInput(1);
		v0 = false; v1 = false;
	}
	
	public int get(){
		v0 = pin1.get(); v1 = pin2.get();
		if (v0 && v1) return 0;
		else if (v0 && !v1) return -1;
		else if (!v0 && v1) return 1;
		else if (!v0 && !v1) return 0;
		else return 0;
	}
}
