package edu.nr.robotics;

import java.util.ArrayList;

import edu.nr.lib.*;
import edu.nr.lib.interfaces.Periodic;
import edu.nr.lib.navx.NavX;
import edu.nr.lib.network.UDPClient;
import edu.nr.lib.network.UDPServer;
import edu.nr.robotics.auton.*;
import edu.nr.robotics.auton.AutonOverAlignShootCommandGroup.Positions;
import edu.nr.robotics.commandgroups.AlignAndShootCommandGroup;
import edu.nr.robotics.commandgroups.AlignCommandGroup;
import edu.nr.robotics.commandgroups.AutoGuillotineCommandGroup;
import edu.nr.robotics.commandgroups.AutoShovelOfFriesCommandGroup;
import edu.nr.robotics.commandgroups.AlignCommandGroup.State;
import edu.nr.robotics.subsystems.drive.Drive;
import edu.nr.robotics.subsystems.drive.DriveAnglePIDCommand;
import edu.nr.robotics.subsystems.drive.DriveSimpleDistanceCommand;
import edu.nr.robotics.subsystems.drive.DriveTurnCommand;
import edu.nr.robotics.subsystems.elevator.Elevator;
import edu.nr.robotics.subsystems.hood.Hood;
import edu.nr.robotics.subsystems.intakearm.IntakeArm;
import edu.nr.robotics.subsystems.intakeroller.IntakeRoller;
import edu.nr.robotics.subsystems.lights.Lights;
import edu.nr.robotics.subsystems.loaderroller.LaserCannonTriggerCommand;
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
	
	public AlignCommandGroup.State state;

	
	LaserCannonTriggerCommand fireCommand;

	private static Robot singleton;
	
	//This is technically unsafe, since it's not guarenteed not to return a null pointer, but we don't have any code that runs before the robot is initialized.
	public static Robot getInstance() {
		return singleton; 
	}
	
	Command autonomousCommand;
	SendableChooser autoCommandChooser;
	
	public SendableChooser autoCommandPickerOne;
	public SendableChooser autoCommandPickerTwo;
	public SendableChooser autoCommandPickerThree;
	public SendableChooser autoCommandPickerFour;
	public SendableChooser autoCommandPickerFive;
	public SendableChooser autoCommandPickerSix;

	public static ArrayList<Subsystem> subsystems = new ArrayList<Subsystem>();
	public static ArrayList<SmartDashboardSource> smartDashboardSources = new ArrayList<SmartDashboardSource>();
	public static ArrayList<Periodic> periodics = new ArrayList<Periodic>();
	
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
		singleton = this;
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
	private void robotInit() {
		new UDPClient("Robot init");

		initCamera();
		initSubsystems();
		initSmartDashboardChoosers();
		initServer();
	}
	
	private void initServer() {
		(new Thread(UDPServer.getInstance())).start();
	}
	
	private void initSmartDashboardChoosers() {		
		autoCommandPickerOne = new SendableChooser();
		autoCommandPickerOne.addDefault("Do nothing", new AutonDoNothingCommand());
		autoCommandPickerOne.addObject("Drive over obstacle25", new DriveSimpleDistanceCommand(RobotMap.OVER_DISTANCE_25, 1.0));
		autoCommandPickerOne.addObject("Drive onto obstacle25", new DriveSimpleDistanceCommand(RobotMap.ONTO_DISTANCE_25, 1.0));
		autoCommandPickerOne.addObject("Drive over obstacle134", new DriveSimpleDistanceCommand(RobotMap.OVER_DISTANCE_134, 1.0));
		autoCommandPickerOne.addObject("Drive onto obstacle134", new DriveSimpleDistanceCommand(RobotMap.ONTO_DISTANCE_134, 1.0));
		autoCommandPickerOne.addObject("Auto Guillotine", new AutoGuillotineCommandGroup());
		autoCommandPickerOne.addObject("Auto Shovel of Fries", new AutoShovelOfFriesCommandGroup());
		autoCommandPickerOne.addObject("Align and shoot", new AlignAndShootCommandGroup());
		autoCommandPickerOne.addObject("Return to normal (front)", new AutonReturnToNormalFrontCommandGroup());
		autoCommandPickerOne.addObject("Return to normal (back)", new AutonReturnToNormalBackCommandGroup());
		autoCommandPickerOne.addObject("Rotate 180", new DriveAnglePIDCommand(180, AngleUnit.DEGREE));
		SmartDashboard.putData("Picker One", autoCommandPickerOne);
		
		autoCommandPickerTwo = new SendableChooser();
		autoCommandPickerTwo.addDefault("Do nothing", new AutonDoNothingCommand());
		autoCommandPickerTwo.addObject("Drive over obstacle25", new DriveSimpleDistanceCommand(RobotMap.OVER_DISTANCE_25, 1.0));
		autoCommandPickerTwo.addObject("Drive onto obstacle25", new DriveSimpleDistanceCommand(RobotMap.ONTO_DISTANCE_25, 1.0));
		autoCommandPickerTwo.addObject("Drive over obstacle134", new DriveSimpleDistanceCommand(RobotMap.OVER_DISTANCE_134, 1.0));
		autoCommandPickerTwo.addObject("Drive onto obstacle134", new DriveSimpleDistanceCommand(RobotMap.ONTO_DISTANCE_134, 1.0));
		autoCommandPickerTwo.addObject("Auto Guillotine", new AutoGuillotineCommandGroup());
		autoCommandPickerTwo.addObject("Auto Shovel of Fries", new AutoShovelOfFriesCommandGroup());
		autoCommandPickerTwo.addObject("Align and shoot", new AlignAndShootCommandGroup());
		autoCommandPickerTwo.addObject("Return to normal (front)", new AutonReturnToNormalFrontCommandGroup());
		autoCommandPickerTwo.addObject("Return to normal (back)", new AutonReturnToNormalBackCommandGroup());
		autoCommandPickerOne.addObject("Rotate 180", new DriveAnglePIDCommand(180, AngleUnit.DEGREE));
		SmartDashboard.putData("Picker Two", autoCommandPickerTwo);

		autoCommandPickerThree = new SendableChooser();
		autoCommandPickerThree.addDefault("Do nothing", new AutonDoNothingCommand());
		autoCommandPickerThree.addObject("Drive over obstacle25", new DriveSimpleDistanceCommand(RobotMap.OVER_DISTANCE_25, 1.0));
		autoCommandPickerThree.addObject("Drive onto obstacle25", new DriveSimpleDistanceCommand(RobotMap.ONTO_DISTANCE_25, 1.0));
		autoCommandPickerThree.addObject("Drive over obstacle134", new DriveSimpleDistanceCommand(RobotMap.OVER_DISTANCE_134, 1.0));
		autoCommandPickerThree.addObject("Drive onto obstacle134", new DriveSimpleDistanceCommand(RobotMap.ONTO_DISTANCE_134, 1.0));
		autoCommandPickerThree.addObject("Auto Guillotine", new AutoGuillotineCommandGroup());
		autoCommandPickerThree.addObject("Auto Shovel of Fries", new AutoShovelOfFriesCommandGroup());
		autoCommandPickerThree.addObject("Align and shoot", new AlignAndShootCommandGroup());
		autoCommandPickerThree.addObject("Return to normal (front)", new AutonReturnToNormalFrontCommandGroup());
		autoCommandPickerThree.addObject("Return to normal (back)", new AutonReturnToNormalBackCommandGroup());
		autoCommandPickerThree.addObject("Rotate 180", new DriveAnglePIDCommand(180, AngleUnit.DEGREE));
		SmartDashboard.putData("Picker Three", autoCommandPickerThree);

		autoCommandPickerFour = new SendableChooser();
		autoCommandPickerFour.addDefault("Do nothing", new AutonDoNothingCommand());
		autoCommandPickerFour.addObject("Drive over obstacle25", new DriveSimpleDistanceCommand(RobotMap.OVER_DISTANCE_25, 1.0));
		autoCommandPickerFour.addObject("Drive onto obstacle25", new DriveSimpleDistanceCommand(RobotMap.ONTO_DISTANCE_25, 1.0));
		autoCommandPickerFour.addObject("Drive over obstacle134", new DriveSimpleDistanceCommand(RobotMap.OVER_DISTANCE_134, 1.0));
		autoCommandPickerFour.addObject("Drive onto obstacle134", new DriveSimpleDistanceCommand(RobotMap.ONTO_DISTANCE_134, 1.0));
		autoCommandPickerFour.addObject("Auto Guillotine", new AutoGuillotineCommandGroup());
		autoCommandPickerFour.addObject("Auto Shovel of Fries", new AutoShovelOfFriesCommandGroup());
		autoCommandPickerFour.addObject("Align and shoot", new AlignAndShootCommandGroup());
		autoCommandPickerFour.addObject("Return to normal (front)", new AutonReturnToNormalFrontCommandGroup());
		autoCommandPickerFour.addObject("Return to normal (back)", new AutonReturnToNormalBackCommandGroup());
		autoCommandPickerFour.addObject("Rotate 180", new DriveAnglePIDCommand(180, AngleUnit.DEGREE));
		SmartDashboard.putData("Picker Four", autoCommandPickerFour);

		autoCommandPickerFive = new SendableChooser();
		autoCommandPickerFive.addDefault("Do nothing", new AutonDoNothingCommand());
		autoCommandPickerFive.addObject("Drive over obstacle25", new DriveSimpleDistanceCommand(RobotMap.OVER_DISTANCE_25, 1.0));
		autoCommandPickerFive.addObject("Drive onto obstacle25", new DriveSimpleDistanceCommand(RobotMap.ONTO_DISTANCE_25, 1.0));
		autoCommandPickerFive.addObject("Drive over obstacle134", new DriveSimpleDistanceCommand(RobotMap.OVER_DISTANCE_134, 1.0));
		autoCommandPickerFive.addObject("Drive onto obstacle134", new DriveSimpleDistanceCommand(RobotMap.ONTO_DISTANCE_134, 1.0));
		autoCommandPickerFive.addObject("Auto Guillotine", new AutoGuillotineCommandGroup());
		autoCommandPickerFive.addObject("Auto Shovel of Fries", new AutoShovelOfFriesCommandGroup());
		autoCommandPickerFive.addObject("Align and shoot", new AlignAndShootCommandGroup());
		autoCommandPickerFive.addObject("Return to normal (front)", new AutonReturnToNormalFrontCommandGroup());
		autoCommandPickerFive.addObject("Return to normal (back)", new AutonReturnToNormalBackCommandGroup());
		autoCommandPickerFive.addObject("Rotate 180", new DriveAnglePIDCommand(180, AngleUnit.DEGREE));
		SmartDashboard.putData("Picker Five", autoCommandPickerFive);

		autoCommandPickerSix = new SendableChooser();
		autoCommandPickerSix.addDefault("Do nothing", new AutonDoNothingCommand());
		autoCommandPickerSix.addObject("Drive over obstacle25", new DriveSimpleDistanceCommand(RobotMap.OVER_DISTANCE_25, 1.0));
		autoCommandPickerSix.addObject("Drive onto obstacle25", new DriveSimpleDistanceCommand(RobotMap.ONTO_DISTANCE_25, 1.0));
		autoCommandPickerSix.addObject("Drive over obstacle134", new DriveSimpleDistanceCommand(RobotMap.OVER_DISTANCE_134, 1.0));
		autoCommandPickerSix.addObject("Drive onto obstacle134", new DriveSimpleDistanceCommand(RobotMap.ONTO_DISTANCE_134, 1.0));
		autoCommandPickerSix.addObject("Auto Guillotine", new AutoGuillotineCommandGroup());
		autoCommandPickerSix.addObject("Auto Shovel of Fries", new AutoShovelOfFriesCommandGroup());
		autoCommandPickerSix.addObject("Align and shoot", new AlignAndShootCommandGroup());
		autoCommandPickerSix.addObject("Return to normal (front)", new AutonReturnToNormalFrontCommandGroup());
		autoCommandPickerSix.addObject("Return to normal (back)", new AutonReturnToNormalBackCommandGroup());
		autoCommandPickerSix.addObject("Rotate 180", new DriveAnglePIDCommand(180, AngleUnit.DEGREE));
		SmartDashboard.putData("Picker Six", autoCommandPickerSix);
		
		autoCommandChooser = new SendableChooser();
		autoCommandChooser.addDefault("Do Nothing", new AutonDoNothingCommand());
		autoCommandChooser.addObject("Follow instructions", new AutonFollowInstructionsCommand());
		autoCommandChooser.addObject("Align and shoot", new AlignAndShootCommandGroup());
		autoCommandChooser.addObject("Forward over obstacle, align, shoot 2", new AutonOverAlignShootCommandGroup(Positions.two));
		autoCommandChooser.addObject("Forward over obstacle, align, shoot, return to obstacle 2", new AutonOverAlignShootReturnCommandGroup(Positions.two));
		autoCommandChooser.addObject("Forward over obstacle, align, shoot 5", new AutonOverAlignShootCommandGroup(Positions.five));
		autoCommandChooser.addObject("Forward over obstacle, align, shoot, return to obstacle 5", new AutonOverAlignShootReturnCommandGroup(Positions.five));
		autoCommandChooser.addObject("Forward over obstacle, align, shoot 1", new AutonOverAlignShootCommandGroup(Positions.one));
		autoCommandChooser.addObject("Forward over obstacle, align, shoot, return to obstacle 1", new AutonOverAlignShootReturnCommandGroup(Positions.one));
		autoCommandChooser.addObject("Forward over obstacle, align, shoot 34", new AutonOverAlignShootCommandGroup(Positions.threefour));
		autoCommandChooser.addObject("Forward over obstacle, align, shoot, return to obstacle 34", new AutonOverAlignShootReturnCommandGroup(Positions.threefour));

		// Add more options like:
		// autoCommandChooser.addObject(String name, Command command);
		SmartDashboard.putData("Autonomous Chooser", autoCommandChooser);
		
		OI.getInstance().drivingModeChooser = new SendableChooser();
		OI.getInstance().drivingModeChooser.addDefault("arcade", DrivingMode.ARCADE);
		OI.getInstance().drivingModeChooser.addObject("tank", DrivingMode.TANK);
		SmartDashboard.putData("Driving Mode Chooser", OI.getInstance().drivingModeChooser);
	}
	
	private void initCamera() {
		CameraServer server = CameraServer.getInstance();
		server.setQuality(50);
		// the camera name (ex "cam0") can be found through the roborio web
		// interface
		server.startAutomaticCapture("cam2");
		// TODO: Get potentially the camera name with the real one we use
	}
	
	private void initSubsystems() {
		//Init subsystems
		Drive.init();
		NavX.init();
		FieldCentric.init();
		Lights.init();
		Shooter.init();
		IntakeArm.init();
		Elevator.init();
		LoaderRoller.init();
		Hood.init();
		IntakeRoller.init();
		OI.init();
		
		//Add subsystems to subsystem array list
		//subsystems.add(Drive.getInstance());
		subsystems.add(Lights.getInstance());
		subsystems.add(Shooter.getInstance());
		subsystems.add(IntakeArm.getInstance());
		subsystems.add(LoaderRoller.getInstance());
		subsystems.add(Elevator.getInstance());
		subsystems.add(Hood.getInstance());
		subsystems.add(IntakeRoller.getInstance());
		
		//Add SmartDashboard sources to the smartdashboard source array list
		smartDashboardSources.add(NavX.getInstance());
		smartDashboardSources.add(Drive.getInstance());
		smartDashboardSources.add(FieldCentric.getInstance());
		smartDashboardSources.add(Lights.getInstance());
		smartDashboardSources.add(Shooter.getInstance());
		smartDashboardSources.add(IntakeArm.getInstance());
		smartDashboardSources.add(Elevator.getInstance());
		smartDashboardSources.add(LoaderRoller.getInstance());
		smartDashboardSources.add(Hood.getInstance());
		smartDashboardSources.add(IntakeRoller.getInstance());
		smartDashboardSources.add(OI.getInstance());
		
		periodics.add(Drive.getInstance());
		periodics.add(UDPServer.getInstance());
		periodics.add(OI.getInstance());
		periodics.add(Hood.getInstance());
		periodics.add(IntakeArm.getInstance());
	}

	/**
	 * A generic periodic function that is called by the periodic functions for
	 * the specific modes
	 * 
	 * @param mode
	 *            The name of the mode that is currently occuring
	 */
	private void periodic(Mode mode) {

		if(OI.getInstance().fireButton.get() && !OI.getInstance().alignButton.get() && fireCommand != null && !fireCommand.isRunning()) 
			fireCommand = new LaserCannonTriggerCommand();
		
		periodics.forEach(Periodic::periodic);
		
		FieldCentric.getInstance().update();
		Scheduler.getInstance().run();

		smartDashboardSources.forEach(SmartDashboardSource::smartDashboardInfo);
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
		if(mode == Mode.AUTONOMOUS) {
			Hood.getInstance().resetEncoder();
		}
	}
}
