package edu.nr.robotics;

import java.util.ArrayList;

import edu.nr.lib.FieldCentric;
import edu.nr.lib.SmartDashboardSource;
import edu.nr.lib.navx.NavX;
import edu.nr.robotics.auton.AutonDoNothingCommand;
import edu.nr.robotics.subsystems.drive.Drive;
import edu.nr.robotics.subsystems.elevator.Elevator;
import edu.nr.robotics.subsystems.intakearm.IntakeArm;
import edu.nr.robotics.subsystems.shooter.Shooter;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	public static OI oi;

	Command autonomousCommand;
	SendableChooser autoCommandChooser;

	public static ArrayList<Subsystem> subsystems = new ArrayList<Subsystem>();
	public static ArrayList<SmartDashboardSource> smartDashboardSources = new ArrayList<SmartDashboardSource>();
	
	public enum Mode {
		TELEOP, AUTONOMOUS, DISABLED
	}

	public Mode currentMode;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		CameraServer server = CameraServer.getInstance();
		server.setQuality(50);
		// the camera name (ex "cam0") can be found through the roborio web
		// interface
		server.startAutomaticCapture("cam1");
		// TODO: Potentially find the camera name with the real one we use

		OI.init();
		Drive.init();
		NavX.init();
		FieldCentric.init();
		Shooter.init();
		IntakeArm.init();
		Elevator.init();
		
		subsystems.add(Drive.getInstance());
		subsystems.add(Shooter.getInstance());
		subsystems.add(IntakeArm.getInstance());
		
		smartDashboardSources.add(NavX.getInstance());
		smartDashboardSources.add(Drive.getInstance());
		smartDashboardSources.add(FieldCentric.getInstance());
		smartDashboardSources.add(Shooter.getInstance());
		smartDashboardSources.add(IntakeArm.getInstance());
		smartDashboardSources.add(Elevator.getInstance());

		autoCommandChooser = new SendableChooser();
		autoCommandChooser.addDefault("Do Nothing", new AutonDoNothingCommand());
		// Add more options like:
		// autoCommandChooser.addObject(String name, Command command);
		SmartDashboard.putData("Autonomous Chooser", autoCommandChooser);

		OI.getInstance().drivingModeChooser = new SendableChooser();
		OI.getInstance().drivingModeChooser.addDefault("arcade", "arcade");
		OI.getInstance().drivingModeChooser.addObject("tank", "tank");
		SmartDashboard.putData("Driving Mode Chooser", OI.getInstance().drivingModeChooser);

		for (Subsystem subsystem : subsystems) {
			SmartDashboard.putData(subsystem);
		}
	}

	/**
	 * This function is run when the autonomous period begins
	 */
	@Override
	public void autonomousInit() {
		autonomousCommand = (Command) autoCommandChooser.getSelected();
		autonomousCommand.start();
		initialize(Mode.AUTONOMOUS);
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		periodic(Mode.AUTONOMOUS);
	}

	/**
	 * This function is run when the operator control period begins
	 */
	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running.
		if (autonomousCommand != null) {
			autonomousCommand.cancel();
		}

		initialize(Mode.TELEOP);
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		periodic(Mode.TELEOP);
	}

	/**
	 * This is called when the disabled button is hit. You can use it to reset
	 * subsystems before shutting down.
	 */
	@Override
	public void disabledInit() {
		initialize(Mode.DISABLED);

		// Make sure that the PIDs are set to 0, otherwise if we disable while
		// the PIDs are set, we might have issues...
		Drive.getInstance().setPIDSetpoint(0, 0, false);
	}

	/**
	 * This is called periodically while the robot is disabled
	 */
	@Override
	public void disabledPeriodic() {
		periodic(Mode.DISABLED);
	}

	/**
	 * A generic periodic function that is called by the periodic functions for
	 * the specific modes
	 * 
	 * @param mode
	 *            The name of the mode that is currently occuring
	 */
	private void periodic(Mode mode) {
		Drive.getInstance().setPIDEnabled(!OI.getInstance().fighter.get());

		FieldCentric.getInstance().update();
		Scheduler.getInstance().run();

		putSubsystemDashInfo();
	}

	/**
	 * A generic initialization function that is called by the periodic
	 * functions for the specific modes
	 * 
	 * @param mode
	 *            The name of the mode that just started
	 */
	private void initialize(Mode mode) {
		currentMode = mode;
	}

	/**
	 * Calls the putSmartDashboardInfo function of every smartDashboardSource
	 */
	private void putSubsystemDashInfo() {
		for (SmartDashboardSource source : smartDashboardSources) {
			source.putSmartDashboardInfo();
		}
	}
}
