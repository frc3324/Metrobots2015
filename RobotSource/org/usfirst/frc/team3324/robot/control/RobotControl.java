package org.usfirst.frc.team3324.robot.control;

import java.util.ArrayList;
import java.util.Arrays;
import org.usfirst.frc.team3324.robot.train.DriveTrain;
import edu.wpi.first.wpilibj.Joystick;


public abstract class RobotControl
{
	protected DriveTrain chassis;
	protected Joystick stick;
	public ArrayList<IControl> robotControllers;
	protected IControl driveControl;

	/**
	 * The main object that controls the robot, with the Robot's {@code DriveTrain}, the
	 * {@code Joystick} used for driving, and any extra {@code  IControl} controllers you want to add
	 * to the robot
	 * 
	 * @param train
	 *            The Robot's current DriveTrain
	 * @param stick
	 *            The Joystick used for Driving
	 * @param controllers
	 *            Any controllers you want to affect the Robot
	 */
	public RobotControl(DriveTrain train, Joystick stick, IControl... controllers)
	{
		this.chassis = train;
		this.stick = stick;
		this.robotControllers = new ArrayList<>(Arrays.asList(controllers));
	}

	/**
	 * Another way to create a Robot Controller, but writing drive code as an {@code IControl}.<br>
	 * See {@link #RobotControl(DriveTrain, Joystick, IControl...) RobotControl(DriveTrain,
	 * Joystick, IControl...)}
	 * 
	 * @param driveControl
	 *            Basic drive controller for the robot
	 * @param train
	 *            The Robot's current DriveTrain
	 * @param controllers
	 *            Any controllers you want to affect the Robot
	 */
	public RobotControl(DriveTrain train, IControl driveControl, IControl... controllers)
	{
		this.chassis = train;
		this.driveControl = driveControl;
		this.robotControllers = new ArrayList<>(Arrays.asList(controllers));
	}



	/**
	 * Provides code for polling Joysticks and sensors and correctly driving the robot based upon
	 * that data
	 */
	protected abstract void driveCode();

	/**
	 * All robot control code excluding drive code.<br>
	 * Run all controllers in {@code robotControllers} by default
	 */
	protected void robotCode()
	{
		for(IControl ic : robotControllers)
		{
			ic.control(chassis);
		}
	}

	/**
	 * An update method that should be called every tick of an iterative game
	 */
	public void update()
	{
		// Decides what drive code to use
		if(this.driveControl != null)
		{
			driveControl.control(chassis);
		}
		else
		{
			driveCode();
		}

		// Runs specified extra-drive robot code
		robotCode();
	}

	@Override
	public abstract String toString();
}
