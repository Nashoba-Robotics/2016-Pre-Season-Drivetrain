package edu.nr.robotics;

import java.util.ArrayList;

import edu.nr.lib.FieldCentric;
import edu.nr.lib.SmartDashboardSource;
import edu.nr.lib.navx.NavX;
import edu.nr.robotics.auton.AutonDoNothingCommand;
import edu.nr.robotics.subsystems.drive.Drive;
import edu.nr.robotics.subsystems.elevator.Elevator;
import edu.nr.robotics.subsystems.hood.Hood;
import edu.nr.robotics.subsystems.intakearm.IntakeArm;
import edu.nr.robotics.subsystems.loaderroller.LoaderRoller;
import edu.nr.robotics.subsystems.shooter.Shooter;
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
 * The VM is configured to automatically run this class. 
 * If you change the name of this class or the package after creating 
 * this project, you must also update the manifest file in the resource
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
	
	long prevTime;
	ArrayList<Long> last1000Times;
	
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
	    last1000Times = new ArrayList<Long>();
	    prevTime = System.currentTimeMillis();
	}
	
	private void updateLoopTime() {
		SmartDashboard.putNumber("Time in the loop", System.currentTimeMillis() - prevTime);
	    prevTime = System.currentTimeMillis();
	    last1000Times.add(prevTime);
	    if(last1000Times.size() > 1000) {
	    	last1000Times.remove(0);
	    	SmartDashboard.putNumber("Time of last 1000 loops", System.currentTimeMillis() - last1000Times.get(0));
	    }
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
			updateLoopTime();

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
		initCamera();
		initSubsystems();
		initSmartDashboardChoosers();
	}
	
	public void initSmartDashboardChoosers() {
		autoCommandChooser = new SendableChooser();
		autoCommandChooser.addDefault("Do Nothing", new AutonDoNothingCommand());
		// Add more options like:
		// autoCommandChooser.addObject(String name, Command command);
		SmartDashboard.putData("Autonomous Chooser", autoCommandChooser);

		OI.getInstance().drivingModeChooser = new SendableChooser();
		OI.getInstance().drivingModeChooser.addDefault("arcade", "arcade");
		OI.getInstance().drivingModeChooser.addObject("tank", "tank");
		SmartDashboard.putData("Driving Mode Chooser", OI.getInstance().drivingModeChooser);
	}
	
	public void initCamera() {
		CameraServer server = CameraServer.getInstance();
		server.setQuality(50);
		// the camera name (ex "cam0") can be found through the roborio web
		// interface
		server.startAutomaticCapture("cam2");
		// TODO: Get potentially the camera name with the real one we use
	}
	
	public void initSubsystems() {
		OI.init();
		Drive.init();
		NavX.init();
		FieldCentric.init();
		/*Shooter.init();
		IntakeArm.init();
		Elevator.init();
		LoaderRoller.init();
		Hood.init();*/
		
		subsystems.add(Drive.getInstance());
		/*subsystems.add(Shooter.getInstance());
		subsystems.add(IntakeArm.getInstance());
		subsystems.add(LoaderRoller.getInstance());
		subsystems.add(Elevator.getInstance());
		subsystems.add(Hood.getInstance());*/
		
		smartDashboardSources.add(NavX.getInstance());
		smartDashboardSources.add(Drive.getInstance());
		smartDashboardSources.add(FieldCentric.getInstance());
		/*smartDashboardSources.add(Shooter.getInstance());
		smartDashboardSources.add(IntakeArm.getInstance());
		smartDashboardSources.add(Elevator.getInstance());
		smartDashboardSources.add(LoaderRoller.getInstance());
		smartDashboardSources.add(Hood.getInstance());*/
		smartDashboardSources.add(OI.getInstance());
		
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
