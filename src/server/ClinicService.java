package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

import Manager.ManagerSession;
import data.StaffRecordRepository;
import interfaces.IClinicService;
import models.DoctorRecord;
import models.NurseRecord;
import models.StaffRecord;
import shared.Config;
import shared.enumerations.NurseDesignation;
import shared.enumerations.NurseStatus;
import shared.exceptions.RecordIndexOverflowException;
import view.Login;
/**
 * Server class 
 */
public class ClinicService implements IClinicService
{
	private String code;
	private int portNumber;
	public String[] doctorRecordFields;
	public StaffRecordRepository StaffRecords = new StaffRecordRepository();
	public static int MTLcount = 0;
	public static int DDOcount = 0;
	public static int LVLcount = 0;

	public ClinicService()
	{
		
	}
	/**
	 * To export the object using exportServer method
	 */
	
	public ClinicService(String clinicCode,int portNumber) throws Exception
	{
		this.code = clinicCode;
		this.portNumber = portNumber;
		exportServer(this.code,this.portNumber);
	}
	
	public void exportServer(String name,int portNumber) throws Exception{
		Remote obj = UnicastRemoteObject.exportObject(this, portNumber);
		Registry r = LocateRegistry.createRegistry(portNumber);
		r.bind(name, obj);
		
	}
	
	
	
	/**
	 * Function to create doctor record 
	 */
	@Override
	public void createDoctorRecord(String FirstName, String LastName, String Address, String PhoneNumber,
			String Specialization, String Location)	throws RemoteException 
	{
		DoctorRecord record = new DoctorRecord();
		
		record.FirstName = FirstName;	
		record.LastName = LastName;
		record.Address = Address;
		record.PhoneNumber = PhoneNumber;
		record.Specialization = Specialization;
		record.getRecordID();
	
		StaffRecords.Add(record);
		if(Location.equals("MTL")){
			MTLcount++;
		}
		if(Location.equals("DDO")){
			DDOcount++;
		}
		if(Location.equals("LVL")){
			LVLcount++;
		}
	}
	/**
	 * Function to create nurse record
	 */
	@Override
	public void createNurseRecord(String FirstName, String LastName,NurseDesignation designation, NurseStatus status,
			String string, String Location) throws RemoteException 
	{
		NurseRecord record = new NurseRecord();
		record.FirstName = FirstName;
		record.LastName = LastName;
		record.designation = designation;
		record.status = status;
		record.statusDate =  string;
		record.getRecordID();
		
		StaffRecords.Add(record);
		
		if(Location.equals("MTL")){
			MTLcount++;
		}
		if(Location.equals("DDO")){
			DDOcount++;
		}
		if(Location.equals("LVL")){
			LVLcount++;
		}
		
	}

	/**
	 * To edit the record
	 */
	@Override
	public void editRecord(String RecordID, String FieldName, String FieldValue) throws RemoteException 
	{
		try
		{
			StaffRecords.Edit(RecordID, FieldName, FieldValue);
		}
		catch(Exception ex)
		{
			
		}
	}
	/**
	 * Getting all record ids
	 */
	public String[] getRecordIDs() throws RemoteException
	{
		return StaffRecords.getRecordIDs();
	}
	/**
	 * Getting attributes of record
	 * @see interfaces.IClinicService#getRecordFields(java.lang.String)
	 */
	public String[] getRecordFields(String recordID){
		
		StaffRecord doctorrecord = StaffRecords.GetRecord(recordID);
		doctorRecordFields[0] = doctorrecord.FirstName;
		doctorRecordFields[1] = doctorrecord.LastName;
		
		
		return doctorRecordFields;
	}
	public static void main(String args[])
	{
		
		try
		{
			new ClinicService("MTL",Config.PORT_NUMBER_MTL);
			new ClinicService("DDO",Config.PORT_NUMBER_DDO);
			new ClinicService("LVL",Config.PORT_NUMBER_LVL);
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		DatagramSocket MTLCountSocket = null;
		DatagramSocket DDOCountSocket = null;
		DatagramSocket LVLCountSocket = null;
		
		try
		{
			
			MTLCountSocket = new DatagramSocket(Config.PORT_NUMBER_MTL);
			DDOCountSocket = new DatagramSocket(Config.PORT_NUMBER_DDO);
			LVLCountSocket = new DatagramSocket(Config.PORT_NUMBER_LVL);
			
			byte[] bufferMTL = new byte[1000];
			byte[] bufferDDO = new byte[1000];
			byte[] bufferLVL = new byte[1000];
			
			while(true)
			{
				
				DatagramPacket MTLrequest = new DatagramPacket(bufferMTL, bufferMTL.length);
				
				
				MTLCountSocket.receive(MTLrequest);
					
				DatagramPacket MTLreply = new DatagramPacket(Integer.toString(MTLcount).getBytes(),Integer.toString(MTLcount).length(),MTLrequest.getAddress(),MTLrequest.getPort());
				MTLCountSocket.send(MTLreply);
				
				DatagramPacket DDOrequest = new DatagramPacket(bufferDDO, bufferDDO.length);
				DDOCountSocket.receive(DDOrequest);
					
				DatagramPacket DDOreply = new DatagramPacket(Integer.toString(DDOcount).getBytes(),Integer.toString(DDOcount).length(),DDOrequest.getAddress(),DDOrequest.getPort());
				DDOCountSocket.send(DDOreply);
				
				DatagramPacket LVLrequest = new DatagramPacket(bufferLVL, bufferLVL.length);
				LVLCountSocket.receive(LVLrequest);
					
				DatagramPacket LVLreply = new DatagramPacket(Integer.toString(LVLcount).getBytes(),Integer.toString(LVLcount).length(),LVLrequest.getAddress(),LVLrequest.getPort());
				LVLCountSocket.send(LVLreply);
				
			}
		}
		catch (SocketException e){System.out.println("Socket: " + e.getMessage());
		} catch (IOException e) {System.out.println("IO: " + e.getMessage());}
		finally
		{
			if(MTLCountSocket!=null || DDOCountSocket!=null || LVLCountSocket!=null){
				MTLCountSocket.close();
				DDOCountSocket.close();
				LVLCountSocket.close();
			}
		}
	}
/**
 *  Getting the record
 */
	public StaffRecord getRecord(String recordID) throws RemoteException
	{
		return StaffRecords.GetRecord(recordID);
	}

}
