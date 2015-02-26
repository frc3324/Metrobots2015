package org.usfirst.frc.team3325.robot;

import java.io.FileReader;
import java.io.IOException;
import java.text.Normalizer;

public class MetroMouse
{
	public static FileReader mouse;

	public static void init()
	{
		try
		{
			mouse = new FileReader("/dev/input/mouse0");
		}
		catch(Exception e)
		{}
	}

	public static int getX()
	{
		try
		{
			char buf[] = new char[3];
			mouse.read(buf, 0, 3);
			
			return (int)(buf[1] - ((0x80 & buf[1]) << 1));
		}
		catch(Exception e)
		{
			return 0;
		}
	}
}
