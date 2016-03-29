package edu.nr.robotics.commandgroups;

import edu.nr.robotics.OI;
import edu.nr.robotics.subsystems.drive.Drive;
import edu.nr.robotics.subsystems.hood.Hood;
import edu.nr.robotics.subsystems.intakearm.IntakeArm;
import edu.nr.robotics.subsystems.shooter.Shooter;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AlignEndCommandGroup extends CommandGroup {
    
    @Override
    public void start() {
    	OI.getInstance().alignCommand.cancel();
    }
}
