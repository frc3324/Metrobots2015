package org.usfirst.frc.team3325.robot;

import edu.wpi.first.wpilibj.AnalogOutput;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Talon;
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
	private CANTalon fl, bl, fr, br;
	private Talon liftMotor1, liftMotor2;
	private CANTalon armMotor;
	private Encoder flEncoder, blEncoder, frEncoder, brEncoder;
	private Gyro gyro;

	private MetroJS driver;
	// private MetroJS lifter;

	private DigitalOutput piOut;
	private DigitalInput piIn;

	private LinearLift autonLift;

	private ArmLift armLift;

	// private RGBLED led;

	public void robotInit()
	{
		fl = new CANTalon(1);
		bl = new CANTalon(2);
		fr = new CANTalon(3);
		br = new CANTalon(4);

		liftMotor1 = new Talon(4);
		liftMotor2 = new Talon(5);
		armMotor = new CANTalon(5);

		flEncoder = new Encoder(0, 1);
		blEncoder = new Encoder(2, 3);
		frEncoder = new Encoder(4, 5);
		brEncoder = new Encoder(6, 7);

		gyro = new Gyro(0);

		driver = new MetroJS(0);
		// lifter = new MetroJS(1);

		// led = new RGBLED(1/* , 2, 3 */);

		chassis = new DriveTrain(fl, bl, fr, br, flEncoder, blEncoder, frEncoder, brEncoder, gyro);
		chassis.setInvertedMotors(false, false, true, true);
		chassis.setDriveType(DriveTrain.MECANUM_DRIVE);

		Connection.instance.start();

		piOut = new DigitalOutput(8);
		piIn = new DigitalInput(9);

		autonLift = new LinearLift(liftMotor1, liftMotor2);

		armLift = new ArmLift(armMotor);

	}


	/**
	 * This function is called once when autonomous is enabled
	 */
	public void autonomousInit()
	{

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
	public void teleopInit()
	{
		chassis.setDriveType(DriveTrain.MECANUM_DRIVE);
		chassis.setHoldAngle(true);
		chassis.setTargetAngle((int) chassis.getGyro());
	}

	/**
	 * This function is called periodically during operator control
	 */
	public void teleopPeriodic()
	{
		chassis.setHoldAngle(driver.toggleWhenPressed(MetroJS.BUTTON_A));

		chassis.drive(driver.getAxis(MetroJS.LEFT_X), driver.getAxis(MetroJS.LEFT_Y), driver.getAxis(MetroJS.RIGHT_X), driver.getAxis(MetroJS.RIGHT_Y));

		if(driver.getButton(MetroJS.BUTTON_B))
		{
			chassis.resetGyro();
		}

		SmartDashboard.putNumber("Gyro Angle: ", chassis.getGyro());
		SmartDashboard.putBoolean(" - Hold Angle", chassis.isHoldAngle());
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
		armLift.set(driver.getAxis(MetroJS.LEFT_Y));
	}

}
