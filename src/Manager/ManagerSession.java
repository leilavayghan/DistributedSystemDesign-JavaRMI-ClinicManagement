package Manager;

import java.io.IOException;
import java.io.Serializable;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javax.swing.JOptionPane;

import interfaces.IClinicService;
import server.ClinicService;
/**
 * Class containing the necessary variables for creating a session by manager or client
 */
public class ManagerSession implements Serializable{

	private String managerID;
	private IClinicService service;
	/**
	 * Getting client service based on manager id and clinic code
	 * @param managerID
	 * @throws Exception
	 */
	public ManagerSession(String managerID) throws Exception
	{
		this.managerID = managerID;
		String clinicCode = managerID.substring(0, 3);
		this.service = ClientServiceFactory.getService(clinicCode);
	}
	
	/**
	 *getting manager id
	 * @return
	 */
	public String getManagerID()
	{
		return managerID;
	}
	/**
	 * Gettin instance of IClinicSevice
	 * @return
	 */
	public IClinicService getService()
	{
		return service;
	}
	
	
		
	public String getCountByPacket(int serverport) {
		DatagramSocket clientMTLSocket = null;
		try
		{
			

			clientMTLSocket = new DatagramSocket();
			
			byte[] udpCount = "RandomText".getBytes();
			InetAddress address = InetAddress.getByName("localhost");
			
			DatagramPacket request = new DatagramPacket(udpCount, "RandomText".length(),address,serverport);
			
			clientMTLSocket.send(request);
			
			byte[] buffer = new byte[1000];
			DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
			clientMTLSocket.receive(reply);
		
			return new String(reply.getData());
		
			
		}
		catch (SocketException e){System.out.println("Socket: " + e.getMessage());
		} catch (IOException e) {System.out.println("IO: " + e.getMessage());}
		finally
		{
			if(clientMTLSocket!=null)
			clientMTLSocket.close();
		}
		return null;
	
	}
}
