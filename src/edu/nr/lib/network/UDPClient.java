package edu.nr.lib.network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.PortUnreachableException;
import java.net.SocketException;
import java.net.UnknownHostException;

public class UDPClient {
	private static final int defaultPort = 1768;
	private static final String defaultIpAddress = "GarrisoComputer";
	
	public UDPClient(String message, String ipAddress, int port) {
		/*
		DatagramSocket clientSocket = null;
		try {
			clientSocket = new DatagramSocket();
			InetAddress IPAddress = null;
			try {
				IPAddress = InetAddress.getByName(ipAddress);
				byte[] sendData = new byte[1024];
				sendData = message.getBytes();
				DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
				try {
					clientSocket.send(sendPacket);
				} catch (PortUnreachableException e) {
					System.err.println("Couldn't reach port");
				} catch (IOException e) {
					e.printStackTrace();
				}
				clientSocket.close();
			} catch (UnknownHostException e) {
				System.err.println("No IP address for the host could be found by the client socket");
			}
		} catch (SocketException e) {
			System.err.println("Couldn't initialize client socket");
		}*/
	}
	
	public UDPClient(String message) {
		this(message, defaultIpAddress, defaultPort);
	}
	
	public UDPClient(String message, int port) {
		this(message, defaultIpAddress, port);
	}
	
	public UDPClient(String message, String ipAddress) {
		this(message, ipAddress, defaultPort);
	}
	
	
}
