package org.usfirst.frc.team3324.robot;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import edu.wpi.first.wpilibj.Joystick;

public class MetroJoystick extends Joystick
{
	public static final double AXIS_DEADBAND = 0.04;
	// Analog Inputs (Axes)
	public static final int LEFT_X = 0;
	public static final int LEFT_Y = 1;
	public static final int LEFT_TRIGGER = 2;
	public static final int RIGHT_TRIGGER = 3;
	public static final int RIGHT_X = 4;
	public static final int RIGHT_Y = 5;
	// Digital Inputs (Buttons)
	public static final int BUTTON_A = 1, BUTTON_B = 2, BUTTON_X = 3, BUTTON_Y = 4;
	public static final int BUMPER_LEFT = 5, BUMPER_RIGHT = 6;
	public static final int BUTTON_BACK = 7, BUTTON_START = 8;

	private boolean[] activeButtons = new boolean[this.getButtonCount()];
	private ArrayList<ActionListener> listeners;

	public MetroJoystick(int port)
	{
		super(port);
		listeners = new ArrayList<>();
	}

	@Override
	public double getRawAxis(int axis)
	{
		double value = super.getRawAxis(axis);
		double output = 0;
		if(Math.abs(value) > AXIS_DEADBAND)
		{
			if(axis == LEFT_Y || axis == RIGHT_Y)
			{
				output = -value;
			}
			else
			{
				output = value;
			}
		}
		return output;
	}

	/**
	 * Adds a listener to the list of objects to be notified when a button on the Joystick is
	 * pressed or released
	 * 
	 * @param al
	 *            Listener to now notify of any button events
	 */
	public void addListener(ActionListener al)
	{
		this.listeners.add(al);
	}

	/**
	 * Removes a listener from the list of objects receiving button press/release events
	 * 
	 * @param al
	 *            Listener that was receiving events
	 * @return true if al was successfully removed from the list, false otherwise
	 */
	public boolean removeListener(ActionListener al)
	{
		return this.listeners.remove(al);
	}

	private void fireEvent(ActionEvent ae)
	{
		for(ActionListener al : listeners)
		{
			al.actionPerformed(ae);
		}
	}

	/**
	 * Should be called every tick(if you want to use an event-based system for button controls).
	 * When a button is pressed or released it will fire an event to all listeners of the button's
	 * number and whether it was pressed or released
	 */
	public void update()
	{
		for(int i = 0; i < activeButtons.length; i++)
		{
			boolean press = this.getRawButton(i + 1);
			if(press != activeButtons[i])
			{
				ActionEvent event = new ActionEvent(this, i + 1, press ? "pressed" : "released");
				fireEvent(event);

				activeButtons[i] = press;
			}
		}
	}
}
