package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

import java.rmi.RemoteException;

import org.junit.Test;

import models.DoctorRecord;
import server.ClinicService;
import view.Login;

public class EditionTestClass2 {
	ClinicService clinicservice = new ClinicService();
	@Test
	public void editDoctor2() throws RemoteException{

	clinicservice.createDoctorRecord("Ranjan", "Batra", "2360 Rue Sigouin", "909", "SURGEON", Login.managerLocation);
	
	clinicservice.editRecord("DR10100", "Address", "Rivere");
	
	DoctorRecord record = (DoctorRecord)clinicservice.getRecord("DR10100");
	assertNotSame(record.Address,"Noere");
	}
}