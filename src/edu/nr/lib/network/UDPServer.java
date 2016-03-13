package edu.nr.lib.network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import edu.nr.lib.AngleUnit;
import edu.nr.lib.interfaces.Periodic;
import edu.nr.robotics.RobotMap;
import edu.wpi.first.wpilibj.livewindow.LiveWindowSendable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.tables.ITable;

public class UDPServer implements Runnable, Periodic, LiveWindowSendable {
	public static UDPServer singleton;
	
	public final JetsonImagePacket NULL_PACKET = new JetsonImagePacket(0,0,0);
	
	char delimiter1 = ';';
	char delimiter2 = ':'; 
	//The information is split packet_number;distance:angle
	
	DatagramSocket serverSocket;
	
	boolean goodToGo = true;
	
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

	@Override
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
				e.printStackTrace();
				goodToGo = false;
				continue;
			}
			goodToGo = true;
			String data = new String( receivePacket.getData() );
			//System.out.println("Received: " + data);
			int x = data.indexOf(delimiter1);
			int p = data.indexOf(delimiter2);
			if (p >= x && x > 0) {
				String count_s = data.substring(0, x);
			    String left = data.substring(x+1, p);
			    String right = data.substring(p + 1);
			    try {
			    	final int count = Integer.valueOf(count_s);
			    	if(lastCount != count - 1 && lastCount != 0) {
			    		System.out.println("We dropped a Jetson packet");
			    		droppedPackets += count - lastCount - 1;
			    	}
		    		//For example, if the last packet was 400, and we're at 405, then the droppedPackets 
		    		//increase should only be 4, since there were 4 packets that we missed: 401,402,403,404
			    	
		    		lastCount = count;

			    	final double distance = Double.valueOf(left);
			    	final double turnAngle = Double.valueOf(right) + 1.3;
			    					    
				    synchronized(lock) {
				    	lastPacket = new JetsonImagePacket(turnAngle, distance, lastCount);
				    }
				    
				    //System.out.println("Packet number: " + count + "Shoot: " + hoodAngle + " Angle: " + turnAngle + " Distance: " + distance);
			    } catch (NumberFormatException e) {
			    	System.err.println("Coudln't parse number from Jetson. Recieved Message: " + data);
			    }

			}
		}
	}
	
	
	public JetsonImagePacket getLastPacket() {
		synchronized(lock) {
			if(lastPacket != null)
				return lastPacket;
			return NULL_PACKET;
		}
	}

	@Override
	public void periodic() {
	    SmartDashboard.putNumber("Jetson Turn Angle", getLastPacket().getTurnAngle());
	    SmartDashboard.putNumber("Jetson Distance", getLastPacket().getDistance());
	    SmartDashboard.putNumber("Shoot angle", getLastPacket().getHoodAngle());

	    SmartDashboard.putBoolean("Jetson Good to Go", goodToGo);
	    SmartDashboard.putBoolean("Jetson Aligned", getLastPacket().getTurnAngle() < RobotMap.TURN_THRESHOLD);
		if(System.currentTimeMillis() - lastUpdateTime > 1000 && System.currentTimeMillis() - lastPrintTime > 300) {
			lastPrintTime = System.currentTimeMillis();
		}
	}
	
private ITable m_table;
	
	@Override
	public void initTable(ITable subtable) {
	    m_table = subtable;
	    updateTable();		
	}

	@Override
	public ITable getTable() {
	    return m_table;
	}

	@Override
	public String getSmartDashboardType() {
		return "NavX";
	}

	@Override
	public void updateTable() {
		if (m_table != null) {
			m_table.putNumber("Time since last packet", (System.currentTimeMillis() - lastPrintTime)/1000.0);
			m_table.putNumber("Dropped packet count", droppedPackets);
			m_table.putNumber("Shoot angle", getLastPacket().getHoodAngle());
			m_table.putNumber("Angle from camera", getLastPacket().getTurnAngle());	
			
		}
	}

	@Override
	public void startLiveWindowMode() {}

	@Override
	public void stopLiveWindowMode() {}
	
	//Note: the two angle/distance functions aren't inverses of each other
	//The distanceToAngle is more accurate, but the inverse of it is hard to calculate
	public static double distanceToAngle(double distance) {
		return  0.0095*Math.pow(distance, 3) - 0.4725*Math.pow(distance, 2) + 8.2134*Math.pow(distance, 1) + 9.1025;
	}

	public static double angleToDistance(double angle) {
		return 0.334902 * Math.exp(0.0657678 * angle);
	}
}
