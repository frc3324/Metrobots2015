package org.usfirst.frc.team3325.robot;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the IterativeRobot documentation. If you change the name of this class
 * or the package after creating this project, you must also update the manifest file in the
 * resource directory.
 */
public class Robot extends IterativeRobot
{
	/**
	 * This function is run when the robot is first started up and should be used for any
	 * initialization code.
	 */

	private DriveTrain chassis;
	// private Robot robot;
	private Talon fl, bl, fr, br;
	private Talon liftMotor;
	private Talon armMotor;
	private DigitalInput armBottom;
	private Encoder flEncoder, blEncoder, frEncoder, brEncoder;
	private Gyro gyro, tilt;

	private Timer timer;

	private MetroJS driver;
	private MetroJS lifterJS;

	private MouseAPI mouse;
	private boolean mouseOn;

	private LinearLift autonLift;

	private AnalogInput autonBottom;
	private DigitalInput autonTop;

	private ArmLift armLift;
	
	private GenericHID usbthing;

	int autonCount;

	// private RGBLED led;

	public void robotInit()
	{
		fl = new Talon(4);
		bl = new Talon(3);
		fr = new Talon(2);
		br = new Talon(1);

		liftMotor = new Talon(6);
		armMotor = new Talon(5);

		armBottom = new DigitalInput(9);

		flEncoder = new Encoder(0, 1);
		blEncoder = new Encoder(2, 3);
		frEncoder = new Encoder(4, 5);
		brEncoder = new Encoder(6, 7);

		gyro = new Gyro(0);
		tilt = new Gyro(1);

		autonBottom = new AnalogInput(2);
		autonTop = new DigitalInput(8);

		driver = new MetroJS(0);
		lifterJS = new MetroJS(1);

		// led = new RGBLED(1/* , 2, 3 */);

		chassis = new DriveTrain(fl, bl, fr, br, flEncoder, blEncoder, frEncoder, brEncoder, gyro, tilt);
		chassis.setInvertedMotors(false, false, true, true);
		chassis.setDriveType(DriveTrain.MECANUM_DRIVE);

		autonLift = new LinearLift(liftMotor, autonBottom, autonTop);

		armLift = new ArmLift(armMotor, armBottom);
		
		CameraServer.getInstance().startAutomaticCapture("cam3");
		
		

		/*
		 * try { mouse = new MouseAPI(false); mouse.start(); mouseOn = true; } catch(IOException e)
		 * { e.printStackTrace(); }
		 */

		mouseOn = false;

		timer = new Timer();
		timer.start();

	}


	/**
	 * This function is called once when autonomous is enabled
	 */
	public void autonomousInit()
	{
		timer.reset();
		timer.start();
		gyro.reset();
		chassis.setDriveType(DriveTrain.MECANUM_DRIVE);
		chassis.setHoldAngle(true);
		chassis.setFieldOriented(true);
		chassis.setTargetAngle(chassis.getGyro());
		chassis.resetTilt();
		chassis.setTargetTilt(chassis.getTilt());

		autonCount = 0;
	}

	/**
	 * This function is called periodically during autonomous
	 */
	public void autonomousPeriodic()
	{
		switch(autonCount)
		{
			case 0:
				chassis.drive(0, -0.25, 0, 0);
				autonLift.set(-2);
				if (autonBottom.getValue() > 50) {
					autonCount++;
					timer.reset();
				}
				break;
			case 1:
				autonLift.set(1);
				if (autonTop.get()) {
					autonCount++;
					timer.reset();
				}
				break;
			case 2:
				chassis.drive(0, 0, 0, 0);
				break;
		}
	}

	/**
	 * This function is called once when the teleop is enabled
	 */
	public void teleopInit()
	{
		chassis.setDriveType(DriveTrain.MECANUM_DRIVE);
		chassis.setHoldAngle(true);
		chassis.setTargetAngle(chassis.getGyro());
		chassis.resetTilt();
		chassis.setTargetTilt(chassis.getTilt());
	}

	/**
	 * This function is called periodically during operator control
	 */
	public void teleopPeriodic()
	{
		chassis.setHoldAngle(driver.toggleWhenPressed(MetroJS.BUTTON_A));

		if(driver.toggleWhenPressed(MetroJS.BUTTON_A) && driver.getButton(MetroJS.BUTTON_A))
		{
			chassis.setTargetAngle(gyro.getAngle());
		}

		chassis.setFieldOriented(driver.toggleWhenPressed(MetroJS.BUTTON_X));

		chassis.drive(driver.getAxis(MetroJS.LEFT_X), driver.getAxis(MetroJS.LEFT_Y), driver.getAxis(MetroJS.RIGHT_X), driver.getAxis(MetroJS.RIGHT_Y));

		if(driver.getButton(MetroJS.LB) || driver.getButton(MetroJS.RB))
		{
			chassis.setSlowDrive(true);
		}
		else
		{
			chassis.setSlowDrive(false);
		}

		if(lifterJS.getDPadY() == 1)
		{
			armLift.set(1);
		}
		else if(lifterJS.getDPadY() == -1)
		{
			armLift.set(-1);
		}
		else
		{
			armLift.set(0);
		}

		if(lifterJS.getButton(MetroJS.RB))
		{
			autonLift.set(1);
		}
		else if(lifterJS.getButton(MetroJS.LB))
		{
			autonLift.set(-1);
		}
		else
		{
			autonLift.set(0);
		}

		chassis.setHoldAngle(driver.toggleWhenPressed(MetroJS.BUTTON_A));

		chassis.setFieldOriented(driver.toggleWhenPressed(MetroJS.BUTTON_X));

		if(driver.getButton(MetroJS.BUTTON_B))
		{
			chassis.resetGyro();
			chassis.resetTilt();
		}

		if(Math.abs(tilt.getAngle()) < 0.5)
		{
			tilt.reset();
		}
		
		printValues();
	}

	/**
	 * This function is called once when the test mode is enabled
	 */
	public void testInit()
	{

	}

	/**
	 * This function is called periodically during test mode
	 */
	public void testPeriodic()
	{

	}

	public void disabledPeriodic()
	{

		chassis.setHoldAngle(driver.toggleWhenPressed(MetroJS.BUTTON_A));

		chassis.setFieldOriented(driver.toggleWhenPressed(MetroJS.BUTTON_X));

		if(driver.getButton(MetroJS.BUTTON_B))
		{
			chassis.resetGyro();
			chassis.resetTilt();
		}

		if(Math.abs(tilt.getAngle()) < 0.5)
		{
			tilt.reset();
		}
		
		printValues();
		
		
	}
	
	public void printValues() {
		SmartDashboard.putNumber("Gyro Angle ", chassis.getGyro());
		SmartDashboard.putBoolean("Hold Angle ", chassis.isHoldAngle());
		SmartDashboard.putBoolean("Field Oriented ", chassis.isFieldOriented());
		SmartDashboard.putNumber("Tilt ", tilt.getAngle());
		SmartDashboard.putNumber("flEncoder ", flEncoder.get());
		SmartDashboard.putNumber("blEncoder ", blEncoder.get());
		SmartDashboard.putNumber("frEncoder ", frEncoder.get());
		SmartDashboard.putNumber("brEncoder ", brEncoder.get());

		SmartDashboard.putNumber("autonswitch ", autonBottom.getValue());
		SmartDashboard.putBoolean("autonTop", autonTop.get());
		
		SmartDashboard.putNumber("drive lx", driver.getAxis(MetroJS.LEFT_X));
		SmartDashboard.putNumber("lifter lx", lifterJS.getAxis(MetroJS.LEFT_X));
		
	}

}
