package org.metrobots;

public final class Library {
	public static final int JOYSTICK_MAX_VALUE = 127;
	public static final int MOTOR_MAX_VALUE = 127;
	
	/**
	 * 
	 * @param degree The degree of the polynomial mapping
	 * @param value The joystick value to map to the polynomial
	 * @return The motor value from the joystick value
	 * @author Nathan Bennett
	 */
	public static float polynomialControl(int degree, float value){
		float x = value / JOYSTICK_MAX_VALUE;
		float y = intPower(degree, x);
		if(x < 0 && degree % 2 == 0){
			y = -y;
		}
		
		return y * MOTOR_MAX_VALUE;
	}
	
	public static float intPower(int degree, float x)
	{
		for(int i = 1; i < degree; i++)
		{
			x *= x;
		}
		return degree < 1 ? 0 : x;
	}
}
