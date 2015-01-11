package org.usfirst.frc.team3324.robot;

import static org.usfirst.frc.team3324.robot.MetroJoystick.BUTTON_A;
import java.util.ArrayList;
import org.usfirst.frc.team3324.robot.control.ArcadeControl;
import org.usfirst.frc.team3324.robot.control.HolonomicControl;
import org.usfirst.frc.team3324.robot.control.IControl;
import org.usfirst.frc.team3324.robot.control.MetroDriveControl;
import org.usfirst.frc.team3324.robot.control.RobotControl;
import org.usfirst.frc.team3324.robot.control.TankControl;
import org.usfirst.frc.team3324.robot.train.DriveTrain;
import org.usfirst.frc.team3324.robot.train.MecanumDrive;
import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.interfaces.Accelerometer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the IterativeRobot documentation. If you change the name of this class
 * or the package after creating this project, you must also update the manifest file in the
 * resource directory.
 */
public class Robot extends IterativeRobot
{
	// Global Truths/Variables
	public static final double g = 9.81; // m/s^2

	// Robot State declaration
	public static enum RobotState
	{
		DISABLED, AUTO, TELEOP, TEST, UNKNOWN;
	}

	public static RobotState state;

	// Robot Control declaration
	public RobotControl tankControl, arcadeLeftControl, arcadeDualControl, holonomicControl;
	public ArrayList<RobotControl> controlTypes = new ArrayList<>();
	public RobotControl controlMode;

	// Robot Hardware declaration
	// ALL robot hardware should be put here
	// Static Hardware should be initialized here
	private DriveTrain chassis;
	private Joystick stick = new MetroJoystick(0);
	private Talon fl, bl, fr, br;
	private Encoder flEncoder, blEncoder, frEncoder, brEncoder;
	private Autonomous auto;
	private Timer timer;
	public static Gyro gyro = new Gyro(0);
	public static Accelerometer accelerometer = new BuiltInAccelerometer();

	// Data measurement utilities
	private Timer accelTimer;
	double accelX, accelY, accelZ, accelXPrev = 0, accelYPrev = 0, accelZPrev = 0;
	double velX, velY, velZ, velXPrev = 0, velYPrev = 0, velZPrev = 0;
	double deltaX = 0, deltaY = 0, deltaZ = 0;

	private IControl modeSwitch = (chassis) -> {
		if(stick.getRawButton(BUTTON_A))
		{
			int index = controlTypes.indexOf(controlMode);
			index = index + 1 == controlTypes.size() ? 0 : index + 1;
			controlMode = controlTypes.get(index);
			SmartDashboard.putString(" Current Control Mode ", controlMode.toString());
			Timer.delay(.2);
		}
	};

	IControl metroDrive;

	/**
	 * This function is run when the robot is first started up and should be used for any
	 * initialization code.
	 */
	@Override
	public void robotInit()
	{
		state = RobotState.UNKNOWN;

		fl = new Talon(0);
		bl = new Talon(1);
		fr = new Talon(2);
		br = new Talon(3);
		chassis = new MecanumDrive(fl, bl, fr, br);
		// TO-DO put correct Encoder channels
		flEncoder = new Encoder(0, 0);
		blEncoder = new Encoder(1, 1);
		frEncoder = new Encoder(2, 2);
		brEncoder = new Encoder(3, 3);
		timer = new Timer();
		accelTimer = new Timer();


		// Metro Specific Drive Code
		metroDrive = new MetroDriveControl(stick);

		// Standard Drive Modes' initialization
		tankControl = new TankControl(chassis, stick, modeSwitch);
		arcadeDualControl = new ArcadeControl(chassis, stick, modeSwitch);
		arcadeLeftControl = new ArcadeControl(chassis, (chassis) -> {
			double magnitude = stick.getRawAxis(MetroJoystick.LEFT_Y);
			double angle = stick.getRawAxis(MetroJoystick.LEFT_X);
			ArcadeControl.arcadeDrive(magnitude, angle, chassis);
		}, modeSwitch);
		// Standard Holonomic Drive extended to use Metro's Drive code
		holonomicControl = new HolonomicControl(chassis, metroDrive, modeSwitch);
		// Adding control modes to a cycle-able list
		controlTypes.add(tankControl);
		controlTypes.add(arcadeDualControl);
		controlTypes.add(arcadeLeftControl);
		controlTypes.add(holonomicControl);
		// Setting current (starting) control mode
		controlMode = holonomicControl;

		// Outputs Drive Train and Robot Control mode to the Smart Dashboard
		SmartDashboard.putString(" Drive Train ", chassis.toString());
		SmartDashboard.putString(" Current Control Mode ", controlMode.toString());
	}



	@Override
	public void autonomousInit()
	{
		state = RobotState.AUTO;
		auto = new Autonomous(chassis, flEncoder, blEncoder, frEncoder, brEncoder);

		timer.reset();
		timer.start();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic()
	{
		/*
		 * double time = SmartDashboard.getNumber(" Auto Time "); if(timer.get() < time) {
		 * metroDrive.control(chassis); } else { chassis.drive(0, 0, 0, 0); }
		 */

		auto.goForward(24, .25, true);
		Timer.delay(5);
	}



	@Override
	public void teleopInit()
	{
		state = RobotState.TELEOP;
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic()
	{
		this.controlMode.update();
	}



	@Override
	public void testInit()
	{
		state = RobotState.TEST;
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic()
	{
		// Code to get acceleration, velocity, and displacement from the Built-In Accelerometer
		accelX = accelerometer.getX() * g;
		accelY = accelerometer.getY() * g;
		accelZ = accelerometer.getZ() * g;

		double deltaT = accelTimer.get();
		velX = velXPrev + ((accelX + accelXPrev) / 2) * deltaT;
		velY = velYPrev + ((accelY + accelYPrev) / 2) * deltaT;
		velZ = velZPrev + ((accelZ + accelZPrev) / 2) * deltaT;

		deltaX += ((velX + velXPrev) / 2) * deltaT;
		deltaY += ((velY + velYPrev) / 2) * deltaT;
		deltaZ += ((velZ + velZPrev) / 2) * deltaT;

		SmartDashboard.putNumber(" Gyro Angle ", gyro.getAngle());

		SmartDashboard.putNumber(" Acceleration, X ", accelX);
		SmartDashboard.putNumber(" Acceleration, Y ", accelY);
		SmartDashboard.putNumber(" Acceleration, Z ", accelZ);
		SmartDashboard.putNumber(" Velocity, X ", velX);
		SmartDashboard.putNumber(" Velocity, Y ", velY);
		SmartDashboard.putNumber(" Velocity, Z ", velZ);
		SmartDashboard.putNumber(" Displacement, X ", deltaX);
		SmartDashboard.putNumber(" Displacement, Y ", deltaY);
		SmartDashboard.putNumber(" Displacement, Z ", deltaZ);
	}

	@Override
	public void disabledInit()
	{
		state = RobotState.DISABLED;
	}
}
