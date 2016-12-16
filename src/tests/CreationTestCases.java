package tests;

import static org.junit.Assert.assertEquals;

import java.rmi.RemoteException;
import java.sql.Date;

import org.junit.BeforeClass;
import org.junit.Assert.*;

import data.GroupedRepository;

import org.junit.Assert;
import org.junit.Before;

import server.ClinicService;
import shared.enumerations.NurseDesignation;
import shared.enumerations.NurseStatus;
import view.Login;

import org.junit.Test;

import java.util.Calendar;

import javax.naming.Context;

import models.DoctorRecord;
import models.NurseRecord;
import models.StaffRecord;
public class CreationTestCases {
	ClinicService clinicservice = new ClinicService();
	
	@Test
	public void createDoctor() throws RemoteException{
		clinicservice.createDoctorRecord("Ranjan", "Batra", "2360 Rue Sigouin", "22222", "SURGEON", Login.managerLocation);
		clinicservice.createDoctorRecord("Louisa", "Fletcher", "744 Home", "82622", "ENT", Login.managerLocation);
		clinicservice.createDoctorRecord("Gatma", "Eriv", "85 HL Est-Montreal", "234411", "ORTHOPADEICS", Login.managerLocation);
		clinicservice.createDoctorRecord("Francais", "Mitchel", "550 Rue Filatri", "874422", "surgeon", Login.managerLocation);
		clinicservice.createDoctorRecord("Nimar", "Wain", "745 West Ile", "821622", "CARDIOLOGY", Login.managerLocation);
		clinicservice.createDoctorRecord("Chin", "Yun Le", "33 RIverie Sud", "234461", "GYNECOLOGY", Login.managerLocation);
		clinicservice.createDoctorRecord("Katie", "Rosie", "550 Rue Filatri", "824422", "surgeon", Login.managerLocation);
	    System.out.println("Doctord.dCount");
		assertEquals(DoctorRecord.dCount,7);
	   
	}
	@Test
	public void createNurseRecords() throws Exception{
		clinicservice.createNurseRecord("Ranjan", "Batra",NurseDesignation.JUNIOR, NurseStatus.ACTIVE,"23/10/2010","MTL");
		clinicservice.createNurseRecord("Javed", "Jahar",NurseDesignation.JUNIOR, NurseStatus.ACTIVE,"02/16/2015","DOD");
		clinicservice.createNurseRecord("Siffredi", "Aniston",NurseDesignation.JUNIOR, NurseStatus.ACTIVE,"15/23/2013","LVL");
		assertEquals(NurseRecord.nCount, 3);
	}

}
