package org.usfirst.frc.team3324.robot;

import static org.usfirst.frc.team3324.robot.MetroJoystick.BUTTON_A;
import java.util.ArrayList;
import org.usfirst.frc.team3324.robot.control.ArcadeControl;
import org.usfirst.frc.team3324.robot.control.IControl;
import org.usfirst.frc.team3324.robot.control.RobotControl;
import org.usfirst.frc.team3324.robot.control.TankControl;
import org.usfirst.frc.team3324.robot.train.DriveTrain;
import org.usfirst.frc.team3324.robot.train.TankDrive;
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
	public RobotControl tankControl, arcadeLeftControl, arcadeDualControl;
	public ArrayList<RobotControl> controlTypes;
	private IControl modeSwitch;

	public RobotControl controlMode;

	private DriveTrain chassis;
	private MetroJoystick stick;
	private Talon fl, bl, fr, br;

	/**
	 * This function is run when the robot is first started up and should be used for any
	 * initialization code.
	 */
	public void robotInit()
	{
		stick = new MetroJoystick(0);
		fl = new Talon(0);
		bl = new Talon(1);
		fr = new Talon(2);
		br = new Talon(3);
		chassis = new TankDrive(fl, bl, fr, br);

		tankControl = new TankControl(chassis, stick, modeSwitch);
		arcadeDualControl = new ArcadeControl(chassis, stick, modeSwitch);
		arcadeLeftControl = new ArcadeControl(chassis, (chassis) -> {
			double magnitude = stick.getRawAxis(MetroJoystick.LEFT_Y);
			double angle = stick.getRawAxis(MetroJoystick.LEFT_X);
			ArcadeControl.arcadeDrive(magnitude, angle, chassis);
		}, modeSwitch);
		controlTypes.add(tankControl);
		controlTypes.add(arcadeDualControl);
		controlTypes.add(arcadeLeftControl);

		controlMode = tankControl;

		SmartDashboard.putString(" Drive Train ", chassis.toString());
		SmartDashboard.putString(" Current Control Mode ", controlMode.toString());

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
	}

	/**
	 * This function is called periodically during autonomous
	 */
	public void autonomousPeriodic()
	{

	}

	/**
	 * This function is called periodically during operator control
	 */
	public void teleopPeriodic()
	{
		this.controlMode.update();
	}

	/**
	 * This function is called periodically during test mode
	 */
	public void testPeriodic()
	{

	}
}
