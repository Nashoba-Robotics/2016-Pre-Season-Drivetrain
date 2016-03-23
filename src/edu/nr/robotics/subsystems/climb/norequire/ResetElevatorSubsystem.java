package edu.nr.robotics.subsystems.climb.norequire;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class ResetElevatorSubsystem extends Subsystem {

	static ResetElevatorSubsystem singleton;
	
	public static void init() {
		if(singleton == null)
			singleton = new ResetElevatorSubsystem();
	}
	
	public static ResetElevatorSubsystem getInstance() {
		init();
		return singleton;
	}
	
    @Override
	public void initDefaultCommand() {
    }
}

