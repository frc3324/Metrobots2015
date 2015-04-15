package org.usfirst.frc.team3325.robot;

import edu.wpi.first.wpilibj.DigitalInput;

public class Vision {
	DigitalInput pin1; DigitalInput pin2;
	boolean[] v;
	//char state;
	
	public void Vision(){
		pin1 = new DigitalInput(0);
		pin2 = new DigitalInput(1);
		v = new boolean[2];
	}
	
	public char get(){
		v[0] = pin1.get(); v[1] = pin2.get();
		if (v[0] && v[1]) return 'g';
		else if (v[0] && !v[1]) return 'l';
		else if (!v[0] && v[1]) return 'r';
		else if (!v[0] && !v[1]) return 'n';
		return 'e';
	}
}
