package org.usfirst.frc.team3325.robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Talon;

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
	private Talon liftMotor1, liftMotor2;
	private Encoder flEncoder, blEncoder, frEncoder, brEncoder;
	private Gyro gyro;

	private MetroJS joystick;
	
	private DigitalOutput piOut;
	private DigitalInput piIn;
	
	private LinearLift lift;

	// private RGBLED led;

	public void robotInit()
	{
		fl = new Talon(0);
		bl = new Talon(1);
		fr = new Talon(2);
		br = new Talon(3);
		
		liftMotor1 = new Talon(4);
		liftMotor2 = new Talon(5);
		
		flEncoder = new Encoder(0, 1);
		blEncoder = new Encoder(2, 3);
		frEncoder = new Encoder(4, 5);
		brEncoder = new Encoder(6, 7);
		
		gyro = new Gyro(0);

		joystick = new MetroJS(0);

		// led = new RGBLED(1/* , 2, 3 */);

		chassis = new DriveTrain(fl, bl, fr, br, flEncoder, blEncoder, frEncoder, brEncoder, gyro);
		chassis.setInvertedMotors(false, false, true, true);
		chassis.setDriveType(DriveTrain.MECANUM_DRIVE);

		Connection.instance.start();
		
		piOut = new DigitalOutput(8);
		piIn = new DigitalInput(9);
		
		lift = new LinearLift(liftMotor1, liftMotor2);

	}

	
	/**
	 * This function is called once when autonomous is enabled
	 */
	public void autonomousInit() {
		
	}
	
	/**
	 * This function is called periodically during autonomous
	 */
	public void autonomousPeriodic()
	{

	}

	/**
	 * This function is called once when the teleop is enabled
	 */
	public void teleopInit() {
		
	}
	
	/**
	 * This function is called periodically during operator control
	 */
	public void teleopPeriodic()
	{

		chassis.drive(joystick.getAxis(MetroJS.LEFT_X), joystick.getAxis(MetroJS.LEFT_Y), joystick.getAxis(MetroJS.RIGHT_X), joystick.getAxis(MetroJS.RIGHT_Y));

		/*
		 * Resets gyro on 'A' button press
		 */

		if(joystick.getButton(MetroJS.BUTTON_A))
			chassis.resetGyro();

		/*
		 * Sets Hold angle when holding down 'B' button
		 */

		chassis.setHoldAngle(joystick.getButton(MetroJS.BUTTON_B));

		/*
		 * Sets field oriented, corresponding to 'X' and 'Y' buttons
		 */

		if(joystick.getButton(MetroJS.BUTTON_X))
			chassis.setFieldOriented(true);
		if(joystick.getButton(MetroJS.BUTTON_Y))
			chassis.setFieldOriented(false);

		/*Connection.broadcast("Yolo");

		String[] message = Connection.getData("pi");
		if(message.length > 0)
		{
			System.out.println(Arrays.toString(message));
		}*/
		
		if (piIn.get()) {
			piOut.set(true);
		} else {
			piOut.set(false);
		}

		if (joystick.getButton(MetroJS.BUMPER_LEFT)){
			lift.raise();
		} else if (joystick.getButton(MetroJS.BUMPER_RIGHT)) {
			lift.lower();
		} else {
			lift.set(0);
		}
	}

	/**
	 * This function is called once when the test mode is enabled
	 */
	public void testInit() {
		chassis.setDriveType(DriveTrain.MECANUM_DRIVE);
		chassis.setHoldAngle(true);
		chassis.setTargetAngle((int)chassis.getGyro());
	}
	
	/**
	 * This function is called periodically during test mode
	 */
	public void testPeriodic()
	{
		//chassis.setHoldAngle(true);
		
		if (Math.abs(joystick.getAxis(MetroJS.LEFT_X)) > 0.2) {
			chassis.setHoldAngle(true);
		} else {
			chassis.setHoldAngle(false);
		}
		chassis.drive(joystick.getAxis(MetroJS.LEFT_X), joystick.getAxis(MetroJS.LEFT_Y), joystick.getAxis(MetroJS.RIGHT_X), joystick.getAxis(MetroJS.RIGHT_Y));
	}

}
