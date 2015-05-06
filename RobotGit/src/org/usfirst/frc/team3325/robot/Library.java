package org.usfirst.frc.team3325.robot;

public final class Library
{
	public static final double JOYSTICK_MAX_VALUE = 1;
	public static final double MOTOR_MAX_VALUE = 1;

	/**
	 * Maps the input value to an n-degree polynomial curve, accounting for
	 * {@code JOYSTICK_MAX_VALUE} and {@code MOTOR_MAX_VALUE}
	 * 
	 * @param degree
	 *            The degree of the polynomial mapping
	 * @param value
	 *            The joystick value to map to the polynomial
	 * @return The motor value from the joystick value
	 * @author Nathan Bennett
	 */
	public static double polynomialControl(double value, int degree)
	{
		double x = value / JOYSTICK_MAX_VALUE;
		double y = intPower(x, degree);
		if(x < 0 && degree % 2 == 0)
		{
			y = -y;
		}

		return y * MOTOR_MAX_VALUE;
	}

	/**
	 * Performs and optimized exponent operator on a floating-point base and an integer degree.<br>
	 * This method performs better than Math.pow(x, n) in most scenarios with a non-integer x, but
	 * is slower with integer bases
	 * 
	 * @param x
	 *            The floating-point base of the exponent
	 * @param n
	 *            The integer degree of the exponent
	 * @return x raised to the power of n
	 * @requires n >= 0
	 * @ensures intPower = x^n
	 * @author Scott Fasone
	 */
	public static double intPower(double x, int n)
	{
		if(n == 0) { return 1; }
		if(n > 1)
		{
			if(n % 2 == 0)
			{
				n /= 2;
				x = intPower(x, n);
				x = x * x;
			}
			else
			{
				n--;
				double xTemp = intPower(x, n);
				x = x * xTemp;
			}
		}
		return x;
	}
}
