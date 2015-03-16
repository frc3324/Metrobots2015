package org.usfirst.frc.team3325.robot;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;
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

	public static DriveTrain chassis;
	// private Robot robot;
	public static CANTalon fl, bl, fr, br;
	public static CANTalon liftMotor;
	public static Talon armMotor;
	public static DigitalInput armBottom;
	public static Encoder flEncoder, blEncoder, frEncoder, brEncoder;
	public static Gyro gyro, tilt;

	public static Timer timer;

	public static MetroJS driver;
	public static MetroJS lifterJS;

	public static LinearLift autonLift;

	public static AnalogInput autonBottom;
	public static DigitalInput autonTop;
	public static AnalogInput autonHasTote;

	public static ArmLift armLift;

	public static GenericHID usbthing;

	public static DriverStation ds;

	public void robotInit()
	{
		fl = new CANTalon(4);
		bl = new CANTalon(3);
		fr = new CANTalon(2);
		br = new CANTalon(1);

		liftMotor = new CANTalon(6);
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
		autonHasTote = new AnalogInput(3);

		driver = new MetroJS(0);
		lifterJS = new MetroJS(1);

		chassis = new DriveTrain(fl, bl, fr, br, flEncoder, blEncoder, frEncoder, brEncoder, gyro, tilt);
		chassis.setInvertedMotors(false, false, true, true);
		chassis.setDriveType(DriveTrain.MECANUM_DRIVE);

		autonLift = new LinearLift(liftMotor, autonBottom, autonTop);

		armLift = new ArmLift(armMotor, armBottom);

		timer = new Timer();
		timer.start();

		ds = m_ds;

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
		/*chassis.setHoldAngle(true);
		chassis.setFieldOriented(true);
		chassis.setTargetAngle(chassis.getGyro());
		chassis.resetTilt();
		chassis.setTargetTilt(chassis.getTilt());*/
		
		chassis.setHoldAngle(false);
		chassis.setFieldOriented(false);

		Auton.setAutonCount(0);

	}

	/**
	 * This function is called periodically during autonomous
	 */
	public void autonomousPeriodic()
	{
		Auton.run();
		printValues();
	}

	/**
	 * This function is called once when the teleop is enabled
	 */
	public void teleopInit()
	{
		/*if(chassis.getGyro() < 720)
			chassis.setHoldAngle(true);
		elsea*/
			chassis.setHoldAngle(false);
		chassis.setFieldOriented(false);
		chassis.setDriveType(DriveTrain.MECANUM_DRIVE);
		chassis.setTargetAngle(chassis.getGyro());
		chassis.resetTilt();
		chassis.setTargetTilt(chassis.getTilt());

		driver.prevA = false;
		driver.toggleA = false;
	}

	/**
	 * This function is called periodically during operator control
	 */
	public void teleopPeriodic()
	{
		//chassis.setHoldAngle(driver.toggleWhenPressed(MetroJS.BUTTON_A));

		/*if(driver.toggleWhenPressed(MetroJS.BUTTON_A) && driver.getButton(MetroJS.BUTTON_A))
		{
			chassis.setTargetAngle(gyro.getAngle());
		}*/

		//chassis.setFieldOriented(driver.toggleWhenPressed(MetroJS.BUTTON_X));

		chassis.drive(driver.getAxis(MetroJS.LEFT_X), driver.getAxis(MetroJS.LEFT_Y), driver.getAxis(MetroJS.RIGHT_X), driver.getAxis(MetroJS.RIGHT_Y));

		if(driver.getButton(MetroJS.LB) || driver.getButton(MetroJS.RB))
		{
			chassis.setSlowDrive(true);
		}
		else
		{
			chassis.setSlowDrive(false);
		}

		armLift.set(lifterJS.getDPadY());

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

		/*chassis.setHoldAngle(driver.toggleWhenPressed(MetroJS.BUTTON_A));

		chassis.setFieldOriented(driver.toggleWhenPressed(MetroJS.BUTTON_X));*/

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

		/*chassis.setHoldAngle(driver.toggleWhenPressed(MetroJS.BUTTON_A));

		chassis.setFieldOriented(driver.toggleWhenPressed(MetroJS.BUTTON_X));*/

		if(driver.getButton(MetroJS.BUTTON_B))
		{
			chassis.resetGyro();
			chassis.resetTilt();
		}

		/*if(Math.abs(tilt.getAngle()) < 0.5)
		{
			tilt.reset();
		}*/

		if(driver.getButton(MetroJS.BUTTON_Y))
		{
			Auton.cycleType();
		}

		printValues();


	}

	public void printValues()
	{
		SmartDashboard.putBoolean("Hold Angle", chassis.isHoldAngle());
		SmartDashboard.putBoolean("Field Oriented", chassis.isFieldOriented());
		SmartDashboard.putNumber("Gyro Angle", chassis.getGyro());
		SmartDashboard.putNumber("Tilt", tilt.getAngle());

		SmartDashboard.putNumber("autonswitch ", autonBottom.getVoltage());
		SmartDashboard.putBoolean("autonTop", autonTop.get());

		SmartDashboard.putString("autonType", Auton.getAutonType());

		SmartDashboard.putBoolean("armswitch", armBottom.get());
		SmartDashboard.putNumber("autonBottom", autonBottom.getVoltage());
		SmartDashboard.putBoolean("autonTop", autonTop.get());
		
		SmartDashboard.putNumber("lifterdpad", lifterJS.getDPadY());
		
		SmartDashboard.putNumber("bottombump", autonHasTote.getVoltage());
		
		SmartDashboard.putNumber("auytonCount", Auton.autonCount);

	}

}
