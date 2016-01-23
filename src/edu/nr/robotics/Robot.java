package edu.nr.robotics;

import java.util.ArrayList;

import edu.nr.lib.FieldCentric;
import edu.nr.lib.SmartDashboardSource;
import edu.nr.lib.navx.NavX;
import edu.nr.robotics.auton.AutonDoNothingCommand;
import edu.nr.robotics.subsystems.drive.Drive;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.communication.FRCNetworkCommunicationsLibrary;
import edu.wpi.first.wpilibj.communication.UsageReporting;
import edu.wpi.first.wpilibj.communication.FRCNetworkCommunicationsLibrary.tInstances;
import edu.wpi.first.wpilibj.communication.FRCNetworkCommunicationsLibrary.tResourceType;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends RobotBase {

	public static OI oi;

	Command autonomousCommand;
	SendableChooser autoCommandChooser;

	public static ArrayList<Subsystem> subsystems = new ArrayList<Subsystem>();
	public static ArrayList<SmartDashboardSource> smartDashboardSources = new ArrayList<SmartDashboardSource>();
	
	private boolean m_disabledInitialized;
	private boolean m_autonomousInitialized;
	private boolean m_teleopInitialized;
	private boolean m_testInitialized;
	
	public enum Mode {
		TELEOP, AUTONOMOUS, DISABLED, TEST
	}

	public Mode currentMode;
	
	public Robot() {
	    // set status for initialization of disabled, autonomous, and teleop code.
	    m_disabledInitialized = false;
	    m_autonomousInitialized = false;
	    m_teleopInitialized = false;
	    m_testInitialized = false;
	  }
	
	@Override
	public void startCompetition() {
		UsageReporting.report(tResourceType.kResourceType_Framework, tInstances.kFramework_Iterative);

		robotInit();

		// Tell the DS that the robot is ready to be enabled
		FRCNetworkCommunicationsLibrary.FRCNetworkCommunicationObserveUserProgramStarting();

		// loop forever, calling the appropriate mode-dependent function
		LiveWindow.setEnabled(false);
		while (true) {
			// Call the appropriate function depending upon the current robot
			// mode
			if (isDisabled()) {
				// call DisabledInit() if we are now just entering disabled mode
				// from either a different mode or from power-on
				if (!m_disabledInitialized) {
					LiveWindow.setEnabled(false);
					initialize(Mode.DISABLED);
					m_disabledInitialized = true;
					// reset the initialization flags for the other modes
					m_autonomousInitialized = false;
					m_teleopInitialized = false;
					m_testInitialized = false;
				}
				FRCNetworkCommunicationsLibrary.FRCNetworkCommunicationObserveUserProgramDisabled();
				periodic(Mode.DISABLED);
			} else if (isTest()) {
				// call TestInit() if we are now just entering test mode from
				// either a different mode or from power-on
				if (!m_testInitialized) {
					LiveWindow.setEnabled(true);
					initialize(Mode.TEST);
					m_testInitialized = true;
					m_autonomousInitialized = false;
					m_teleopInitialized = false;
					m_disabledInitialized = false;
				}
				FRCNetworkCommunicationsLibrary.FRCNetworkCommunicationObserveUserProgramTest();
				periodic(Mode.TEST);
			} else if (isAutonomous()) {
				// call Autonomous_Init() if this is the first time
				// we've entered autonomous_mode
				if (!m_autonomousInitialized) {
					LiveWindow.setEnabled(false);
					autonomousCommand = (Command) autoCommandChooser.getSelected();
					autonomousCommand.start();
					initialize(Mode.AUTONOMOUS);
					m_autonomousInitialized = true;
					m_testInitialized = false;
					m_teleopInitialized = false;
					m_disabledInitialized = false;
				}
				FRCNetworkCommunicationsLibrary.FRCNetworkCommunicationObserveUserProgramAutonomous();
				periodic(Mode.AUTONOMOUS);
			} else {
				// call Teleop_Init() if this is the first time
				// we've entered teleop_mode
				if (!m_teleopInitialized) {
					LiveWindow.setEnabled(false);
					// This makes sure that the autonomous stops running when
					// teleop starts running.
					if (autonomousCommand != null) {
						autonomousCommand.cancel();
					}

					initialize(Mode.TELEOP);
					m_teleopInitialized = true;
					m_testInitialized = false;
					m_autonomousInitialized = false;
					m_disabledInitialized = false;
				}
				FRCNetworkCommunicationsLibrary.FRCNetworkCommunicationObserveUserProgramTeleop();
				periodic(Mode.TELEOP);
			}
		}
	}
	
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
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
		
		subsystems.add(Drive.getInstance());
		
		smartDashboardSources.add(NavX.getInstance());
		smartDashboardSources.add(Drive.getInstance());
		smartDashboardSources.add(FieldCentric.getInstance());

		autoCommandChooser = new SendableChooser();
		autoCommandChooser.addDefault("Do Nothing", new AutonDoNothingCommand());
		// Add more options like:
		// autoCommandChooser.addObject(String name, Command command);
		SmartDashboard.putData("Autonomous Chooser", autoCommandChooser);

		OI.getInstance().drivingModeChooser = new SendableChooser();
		OI.getInstance().drivingModeChooser.addDefault("arcade", "arcade");
		OI.getInstance().drivingModeChooser.addObject("tank", "tank");
		SmartDashboard.putData("Driving Mode Chooser", OI.getInstance().drivingModeChooser);

		subsystems.forEach(SmartDashboard::putData);
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
		smartDashboardSources.forEach(SmartDashboardSource::putSmartDashboardInfo);
	}
}
