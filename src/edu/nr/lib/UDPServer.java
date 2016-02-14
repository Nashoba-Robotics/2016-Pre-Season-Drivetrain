package edu.nr.lib;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class UDPServer implements Runnable {
	public static UDPServer singleton;
	char delimiter = ':'; //The information is split distance:beta_h
	DatagramSocket serverSocket;
	
	double distance;
	double beta_h;
	
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
		byte[] receiveData = new byte[1024];
		DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
		try {
			serverSocket.receive(receivePacket);
		} catch (IOException e) {
			System.err.println("Couldn't get a packet");
		}
		
		String data = new String( receivePacket.getData() );
		int p = data.indexOf(delimiter);
		if (p >= 0) {
		    String left = data.substring(0, p);
		    String right = data.substring(p + 1);
		    double distance = Double.valueOf(left);
		    double beta_h = Double.valueOf(right);
		    synchronized(lock) {
		    	this.distance = distance;
				this.beta_h = beta_h;
		    }
		} else {
		  System.err.println("Packet received doesn't have a delimiter");
		}
	}
	
	public double getDistance() {
		synchronized(lock) {
			return distance;
		}
	}
	
	public double getBetaH() {
		synchronized(lock) {
			return beta_h;
		}	
	}
}
