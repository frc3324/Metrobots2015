package org.usfirst.frc.team3324.test;

import org.junit.Test;
import org.usfirst.frc.team3324.robot.Library;

public class LibraryTest
{

	@Test
	public void testIntPower()
	{
		int limit = 0, strain = 1000000;
		long time, elapsed;
		double result = 0;
		for(int i = 1; i < limit; i++)
		{
			time = System.currentTimeMillis();
			for(int t = 0; t < strain; t++)
			{
				result = Library.intPower(i / 9, i);
			}
			elapsed = System.currentTimeMillis() - time;
			System.out.printf("intPower(%f, %d) = %f : in %dms\n", i / 9d, i, result, elapsed);

			time = System.currentTimeMillis();
			for(int t = 0; t < strain; t++)
			{
				result = Math.pow(i / 9, i);
			}
			elapsed = System.currentTimeMillis() - time;
			System.out.printf("Math.pow(%f, %d) = %f : in %dms\n", i / 9d, i, result, elapsed);
		}
	}

	@Test
	public void testPolynomialDrive()
	{
		for(int i = 0; i <= 10; i++)
		{
			System.out.println(Library.polynomialControl(i / 10d, 10));
		}
	}
}
