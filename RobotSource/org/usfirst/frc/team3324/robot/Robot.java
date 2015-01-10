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
	public static boolean isAuto;

	public RobotControl tankControl, arcadeLeftControl, arcadeDualControl, holonomicControl;
	public ArrayList<RobotControl> controlTypes = new ArrayList<>();
	private IControl modeSwitch;
	public RobotControl controlMode;
	private DriveTrain chassis;
	private MetroJoystick stick;
	private Talon fl, bl, fr, br;
	private Timer timer;

	IControl metroDrive;

	/**
	 * This function is run when the robot is first started up and should be used for any
	 * initialization code.
	 */
	@Override
	public void robotInit()
	{
		isAuto = false;

		stick = new MetroJoystick(0);
		fl = new Talon(0);
		bl = new Talon(1);
		fr = new Talon(2);
		br = new Talon(3);
		timer = new Timer();
		chassis = new MecanumDrive(fl, bl, fr, br);


		modeSwitch = (chassis) -> {
			if(stick.getRawButton(BUTTON_A))
			{
				int index = controlTypes.indexOf(controlMode);
				index = index + 1 == controlTypes.size() ? 0 : index + 1;
				controlMode = controlTypes.get(index);
				SmartDashboard.putString(" Current Control Mode ", controlMode.toString());
				Timer.delay(.2);
			}
		};
		metroDrive = new MetroDriveControl(stick);

		tankControl = new TankControl(chassis, stick, modeSwitch);
		arcadeDualControl = new ArcadeControl(chassis, stick, modeSwitch);
		arcadeLeftControl = new ArcadeControl(chassis, (chassis) -> {
			double magnitude = stick.getRawAxis(MetroJoystick.LEFT_Y);
			double angle = stick.getRawAxis(MetroJoystick.LEFT_X);
			ArcadeControl.arcadeDrive(magnitude, angle, chassis);
		}, modeSwitch);
		holonomicControl = new HolonomicControl(chassis, metroDrive, modeSwitch);
		controlTypes.add(tankControl);
		controlTypes.add(arcadeDualControl);
		controlTypes.add(arcadeLeftControl);
		controlTypes.add(holonomicControl);
		controlMode = holonomicControl;

		SmartDashboard.putString(" Drive Train ", chassis.toString());
		SmartDashboard.putString(" Current Control Mode ", controlMode.toString());
	}

	@Override
	public void autonomousInit()
	{
		isAuto = true;

		timer.reset();
		timer.start();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic()
	{
		double time = SmartDashboard.getNumber(" Auto Time ");
		if(timer.get() < time)
		{
			metroDrive.control(chassis);

		}
		else
		{
			chassis.drive(0, 0, 0, 0);
		}
	}

	@Override
	public void teleopInit()
	{
		isAuto = false;
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic()
	{
		this.controlMode.update();
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic()
	{
	}
}
