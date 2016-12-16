package models;

import java.io.Serializable;
import java.sql.Date;

import shared.enumerations.NurseDesignation;
import shared.enumerations.NurseStatus;
import shared.exceptions.RecordIndexOverflowException;
/**
 * Class representing the nurse records
 */
public class NurseRecord extends StaffRecord implements Serializable
{
	
	public NurseDesignation designation; 
	public NurseStatus status;
	public static int nCount=0;
	public String statusDate;
	
	
	public NurseRecord() throws RecordIndexOverflowException
	{
		int index = 10000 + count++;
		countString = Integer.toString(count);
		nCount++;
		if(index <= 99999)
		{
			RecordID = "NR" + Integer.toString(index);
		}
		else
		{
			throw new RecordIndexOverflowException("Nurse Record");
		}
	}
}
