package org.usfirst.frc.team3325.robot;

import java.io.FileInputStream;
import java.io.DataInputStream;
import java.io.IOException;

public class MouseAPI extends Thread{
	private int[] position = new int[2];
	boolean debugmode;
	DataInputStream file;
	
	public MouseAPI(boolean debug) throws IOException {
		file = getFile();
		debugmode = debug;
	}
	
	public void run(String[] args) throws IOException {
		while (true) {
			byte[] buf = new byte[3];
			file.read(buf, 0, 3);
			
			position[0] += buf[1];
			position[1] += buf[2];
			if (debugmode) {
				System.out.print(position[0]); System.out.print("    "); System.out.println(position[1]);
			}
		}
	}
	
	public int getX() { return position[0]; }
	public int getY() { return position[1]; }
	
	private static DataInputStream getFile() throws IOException {
		FileInputStream file = new FileInputStream("/dev/input/mouse0");
		DataInputStream data = new DataInputStream(file);
		return data;
	}
}
