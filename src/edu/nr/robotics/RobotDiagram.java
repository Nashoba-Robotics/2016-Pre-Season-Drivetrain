package edu.nr.robotics;

import edu.nr.robotics.subsystems.hood.Hood;
import edu.nr.robotics.subsystems.intakearm.IntakeArm;
import edu.nr.robotics.subsystems.rollers.Rollers;
import edu.nr.robotics.subsystems.shooter.Shooter;
import edu.wpi.first.wpilibj.NamedSendable;
import edu.wpi.first.wpilibj.tables.ITable;

public class RobotDiagram implements NamedSendable {

	public RobotDiagram() {
		// TODO Auto-generated constructor stub
	}

private ITable table;

	public void initTable(ITable table) {
		this.table = table;
		if (table != null) {
			table.putString("~TYPE~", "robo-diagram");

			//Hood
			table.putNumber("Hood Position", Hood.getInstance().get());
			table.putBoolean("Hood Moving", Hood.getInstance().getMoving());
			//Intake Arm
			table.putNumber("Intake Arm Position", IntakeArm.getInstance().get());
			table.putBoolean("Intake Arm Moving", IntakeArm.getInstance().getMoving());
			//Rollers
			table.putBoolean("Intake Roller Running", Rollers.getInstance().getIntakeRunning());
			table.putBoolean("Loader Roller Runing", Rollers.getInstance().getLoaderRunning());
			//Shooter
			table.putBoolean("Shooter Moving", Shooter.getInstance().getRunning());
			table.putBoolean("Shooter Full Speed", Shooter.getInstance().getSped());
			//Ball
			table.putBoolean("Ball In Intake", Rollers.getInstance().getBallInIntake());
			table.putBoolean("Ball in Loader", Rollers.getInstance().getBallInLoader());
		}
	}

	@Override
	public ITable getTable() {
		return table;
	}

	@Override
	public String getSmartDashboardType() {
		return "Robot Diagram";
	}

	@Override
	public String getName() {
		return "Robot Diagram";
	}

}
