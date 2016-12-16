package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;

import Manager.ManagerSession;
import models.StaffRecord;
import shared.enumerations.NurseDesignation;
import shared.enumerations.NurseStatus;
/**
 * Interface between Client and server by which client can communicate with server
 */
public interface IClinicService extends Remote
{
	
	public void createDoctorRecord(String FirstName, String LastName, String Address
			, String Phone, String Specialization, String Location) throws RemoteException ;
	
	public void editRecord(String RecordID, String FieldName, String FieldValue) throws RemoteException ;

	public String[] getRecordIDs() throws RemoteException;
	
	public StaffRecord getRecord(String recordID) throws RemoteException;
	
	public String[] getRecordFields(String recordID) throws RemoteException;

	void createNurseRecord(String FirstName, String LastName, NurseDesignation designation, NurseStatus status,
			String string, String Location) throws RemoteException;
	
}
