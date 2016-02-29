package edu.nr.lib.network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import edu.nr.lib.interfaces.Periodic;
import edu.nr.robotics.subsystems.hood.Hood;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class UDPServer implements Runnable, Periodic {
	public static UDPServer singleton;
	
	char delimiter1 = ':';
	char delimiter2 = ';'; 
	//The information is split packet_number;distance:angle
	
	DatagramSocket serverSocket;
	
	private long lastUpdateTime;
	long lastPrintTime;
		
	private JetsonImagePacket lastPacket;
	
	private int lastCount = 0;
	private int droppedPackets = 0;
	
	private Object lock = new Object();
		
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
		lastUpdateTime = System.currentTimeMillis();
		while(true) {
			byte[] receiveData = new byte[1024];
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			
			try {
				serverSocket.receive(receivePacket);
				lastUpdateTime = System.currentTimeMillis();
			} catch (IOException e) {
				System.err.println("Couldn't get a packet");
			}
			String data = new String( receivePacket.getData() );
			new UDPClient(data);
			//System.out.println("Received: " + data);
			int x = data.indexOf(delimiter1);
			int p = data.indexOf(delimiter2);
			if (p >= x && x > 0) {
				String count_s = data.substring(0, x);
			    String left = data.substring(x+1, p);
			    String right = data.substring(p + 1);
			    try {
			    	final int count = Integer.valueOf(count_s);
			    	if(lastCount != 0) {
			    		System.out.println("We dropped a Jetson packet");
			    		droppedPackets += count - lastCount - 1;
			    	}
		    		//For example, if the last packet was 400, and we're at 405, then the droppedPackets 
		    		//increase should only be 4, since there were 4 packets that we missed: 401,402,403,404
			    	
		    		lastCount = count;

			    	final double distance = Double.valueOf(left);
			    	final double turnAngle = Double.valueOf(right);
	
				    final double hoodAngle = Hood.distanceToAngle(distance);
			    					    
				    synchronized(lock) {
				    	lastPacket = new JetsonImagePacket(hoodAngle, turnAngle, lastCount);
				    }
				    
				    //System.out.println("Packet number: " + count + "Shoot: " + hoodAngle + " Angle: " + turnAngle + " Distance: " + distance);
				    SmartDashboard.putNumber("Shoot angle", hoodAngle);
				    SmartDashboard.putNumber("Angle from camera", turnAngle);	
			    } catch (NumberFormatException e) {
			    	System.err.println("Coudln't parse number from Jetson. Recieved Message: " + data);
			    }

			}
		}
	}
	
	public JetsonImagePacket getLastPacket() {
		synchronized(lock) {
			return lastPacket;
		}
	}

	@Override
	public void periodic() {
		SmartDashboard.putNumber("Time since last packet", (System.currentTimeMillis() - lastUpdateTime)/1000.0);
		SmartDashboard.putNumber("Dropped packet count", droppedPackets);
		if(System.currentTimeMillis() - lastUpdateTime > 1000 && System.currentTimeMillis() - lastPrintTime > 300) {
			lastPrintTime = System.currentTimeMillis();
			System.err.println("TIME SINCE LAST JETSON PACKET IS TOO MUCH!!!");
		}
	}
}
