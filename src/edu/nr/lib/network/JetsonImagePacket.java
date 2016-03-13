package edu.nr.lib.network;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class JetsonImagePacket {
	private double turnAngle, distance;
	private int packetNum;
	
	
	public JetsonImagePacket(double turnAngle, double distance, int packetNum) {
		this.distance = distance;
		this.turnAngle = turnAngle;
		this.packetNum = packetNum;
	}
	
	public int getPacketNum() {
		return packetNum;
	}

	public double getHoodAngle() {
		return UDPServer.distanceToAngle(getDistance());
	}

	public double getTurnAngle() {
		return turnAngle;
	}
	
	public double getDistance() {
		return distance / (SmartDashboard.getNumber("Hood Multiplier Percent")/100.0);
	}
}
