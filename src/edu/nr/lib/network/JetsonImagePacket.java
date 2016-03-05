package edu.nr.lib.network;

public class JetsonImagePacket {
	private double hoodAngle, turnAngle;
	private int packetNum;
	
	
	public JetsonImagePacket(double hoodAngle, double turnAngle, int packetNum) {
		this.hoodAngle = hoodAngle;
		this.turnAngle = turnAngle;
		this.packetNum = packetNum;
	}
	
	public int getPacketNum() {
		return packetNum;
	}

	public double getHoodAngle() {
		return hoodAngle;
	}

	public double getTurnAngle() {
		return turnAngle;
	}
}
