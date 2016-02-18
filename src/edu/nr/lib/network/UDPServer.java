package edu.nr.lib.network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import edu.nr.robotics.subsystems.hood.Hood;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class UDPServer implements Runnable {
	public static UDPServer singleton;
	char delimiter = ':'; //The information is split distance:beta_h
	DatagramSocket serverSocket;
	
	private double hoodAngle;
	private double turnAngle;
	
	private Object turnLock = new Object();
	private Object hoodLock = new Object();
		
	public static UDPServer getInstance() {
		if(singleton == null) {
			singleton = new UDPServer();
		}
		return singleton;
	}
	
	public UDPServer() {
		try {
			serverSocket = new DatagramSocket(1768);
		} catch (SocketException e) {
			System.err.println("Couldn't set up the socket");
		} catch (SecurityException e) {
			System.err.println("Server initialization was blocked");
		}
	}

	public void run() {
		while(true) {
			byte[] receiveData = new byte[1024];
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			try {
				serverSocket.receive(receivePacket);
			} catch (IOException e) {
				System.err.println("Couldn't get a packet");
			}
			
			String data = new String( receivePacket.getData() );
			new UDPClient(data);
			int p = data.indexOf(delimiter);
			if (p >= 0) {
			    String left = data.substring(0, p);
			    String right = data.substring(p + 1);
			    try {
			    	double distance = Double.valueOf(left);
			    	double beta_h = Double.valueOf(right);
	
			    	synchronized(hoodLock) {
				    	this.hoodAngle = Hood.distanceToAngle(distance);
			    	}
			    	
				    synchronized(turnLock) {
						this.turnAngle = beta_h;
				    }
			    } catch (NumberFormatException e) {
			    	System.err.println("Coudln't parse number from Jetson. Recieved Message: " + data);
			    }
			    System.out.println("Shoot: " + getShootAngle() + " Angle: " + getTurnAngle());
			    SmartDashboard.putNumber("Shoot angle", getShootAngle());
			    SmartDashboard.putNumber("Angle from camera", getTurnAngle());
	
			} else {
			  System.err.println("Packet received doesn't have a delimiter");
			}
		}
	}
		
	public double getShootAngle() {
		synchronized(hoodLock) {
			return hoodAngle;
		}
	}
	
	public double getTurnAngle() {
		synchronized(turnLock) {
			return turnAngle;
		}	
	}
}
