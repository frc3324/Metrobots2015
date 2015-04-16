package org.usfirst.frc.team3325.robot;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DriverStation;
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
	public static Talon fl, bl, fr, br;
	public static Talon liftMotor;
	public static Talon armMotor;
	public static DigitalInput armBottom, armTop;
	//public static Encoder flEncoder, frEncoder;
	public static Gyro gyro;
	public static DigitalInput autonToteSensor;
	
	public static Compressor compressor;
	public static DoubleSolenoid piston;

	public static Timer timer;

	public static MetroJS driver;
	public static MetroJS lifterJS;

	public static LinearLift autonLift;

	public static DigitalInput autonBottom;
	public static DigitalInput autonTop;
	public static AnalogInput autonHasTote;

	public static GarageSensor autonGarage;

	public static ArmLift armLift;

	public static DriverStation ds;
	
	public static Vision sight;

	public void robotInit()
	{
		fl = new Talon(1);
		bl = new Talon(2);
		fr = new Talon(3);
		br = new Talon(4);

		liftMotor = new Talon(6);
		armMotor = new Talon(5);

		armBottom = new DigitalInput(6);
		armTop = new DigitalInput(5);
		
		compressor = new Compressor(0);
		compressor.setClosedLoopControl(true);
		piston = new DoubleSolenoid(2, 3);
		piston.set(DoubleSolenoid.Value.kForward);

		// flEncoder = new Encoder(0, 1);
		// frEncoder = new Encoder(2, 3);

		gyro = new Gyro(0);

		autonBottom = new DigitalInput(7);
		autonTop = new DigitalInput(8);
		autonToteSensor = new DigitalInput(9);

		autonGarage = new GarageSensor(1);

		driver = new MetroJS(0);
		lifterJS = new MetroJS(1);

		chassis = new DriveTrain(fl, bl, fr, br, /*flEncoder, frEncoder,*/ gyro);
		chassis.setInvertedMotors(false, false, true, true);
		chassis.setDriveType(DriveTrain.MECANUM_DRIVE);

		autonLift = new LinearLift(liftMotor, autonBottom, autonTop);

		armLift = new ArmLift(armMotor, armBottom, armTop);
		
		sight = new Vision();

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

		chassis.setHoldAngle(true);
		chassis.setTargetAngle(0);
		chassis.setFieldOriented(true);
		chassis.setGyroHoldSensitivity(20);

		Auton.setAutonCount(0);

	}

	/**
	 * This function is called periodically during autonomous
	 */
	public void autonomousPeriodic()
	{
		//Auton.run();
		Auton.driveToTote();
		printValues();
	}

	/**
	 * This function is called once when the teleop is enabled
	 */
	public void teleopInit()
	{
		/*
		 * if(chassis.getGyro() < 720) chassis.setHoldAngle(true); else
		 */
		chassis.setHoldAngle(false);
		chassis.setFieldOriented(false);
		chassis.setGyroHoldSensitivity(20);
		chassis.setDriveType(DriveTrain.MECANUM_DRIVE);
		chassis.setTargetAngle(chassis.getGyro());

		driver.prevA = false;
		driver.toggleA = false;
	}

	/**
	 * This function is called periodically during operator control
	 */
	public void teleopPeriodic()
	{
		// chassis.setHoldAngle(driver.toggleWhenPressed(MetroJS.BUTTON_A));

		/*
		 * if(driver.toggleWhenPressed(MetroJS.BUTTON_A) && driver.getButton(MetroJS.BUTTON_A)) {
		 * chassis.setTargetAngle(gyro.getAngle()); }
		 */

		// chassis.setFieldOriented(driver.toggleWhenPressed(MetroJS.BUTTON_X));
		
		double lx = driver.getAxis(MetroJS.LEFT_X);
		double ly = driver.getAxis(MetroJS.LEFT_Y) + (driver.getDPadY() / 3);
		double rx = driver.getAxis(MetroJS.RIGHT_X) + (lifterJS.getButton(MetroJS.BUTTON_X) ? -0.5 : 0) + (lifterJS.getButton(MetroJS.BUTTON_B) ? 0.5 : 0);
		double ry = driver.getAxis(MetroJS.RIGHT_Y);

		chassis.drive(lx, ly, rx, ry);

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
		
		//piston.set(lifterJS.getButton(MetroJS.BUTTON_A) ? DoubleSolenoid.Value.kForward : lifterJS.getButton(MetroJS.BUTTON_Y) ? DoubleSolenoid.Value.kReverse : DoubleSolenoid.Value.kOff);


		/*
		 * chassis.setHoldAngle(driver.toggleWhenPressed(MetroJS.BUTTON_A));
		 * 
		 * chassis.setFieldOriented(driver.toggleWhenPressed(MetroJS.BUTTON_X));
		 */

		if(driver.getButton(MetroJS.BUTTON_B))
		{
			chassis.resetGyro();
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

		/*
		 * chassis.setHoldAngle(driver.toggleWhenPressed(MetroJS.BUTTON_A));
		 * 
		 * chassis.setFieldOriented(driver.toggleWhenPressed(MetroJS.BUTTON_X));
		 */

		if(driver.getButton(MetroJS.BUTTON_B))
		{
			chassis.resetGyro();
		}

		if(driver.getButton(MetroJS.BUTTON_Y) && !driver.prevY)
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

		SmartDashboard.putString("autonType", Auton.getAutonType());

		SmartDashboard.putNumber("lifterdpad", lifterJS.getDPadY());

		SmartDashboard.putNumber("auytonCount", Auton.autonCount);

		SmartDashboard.putBoolean("autonBottom", autonBottom.get());
		SmartDashboard.putBoolean("autonTop", autonTop.get());
		SmartDashboard.putBoolean("has tote", autonGarage.beamBroken());
		//SmartDashboard.putNumber("tote volts", autonGarage.input.getVoltage());
		SmartDashboard.putNumber("Auto angle", sight.get());


		/*
		 * SmartDashboard.putNumber("garageDoor thingy", autonToteSensor.getVoltage());
		 * SmartDashboard.putBoolean("garage is blocked", autonToteSensor.beamBroken());
		 * SmartDashboard.putBoolean("Has Tote", autonToteSensor.hasTote());
		 * SmartDashboard.putBoolean("beam connected", autonToteSensor.beamConnected());
		 */
	}

}
