package models;

import java.io.Serializable;

import shared.exceptions.RecordIndexOverflowException;
/**
 * Class representing the doctor records and the attributes
 *
 */
public class DoctorRecord extends StaffRecord implements Serializable
{
	public String Address;
	public static int dCount=0;
	public String PhoneNumber;
	public String Specialization;
	public String Location;

	public DoctorRecord() throws RecordIndexOverflowException
	{
		int index = 10000 + count++;
		countString = Integer.toString(count);
		dCount++;
		if(index <= 99999)
		{
			RecordID = "DR" + Integer.toString(index);
		}
		else
		{
			throw new RecordIndexOverflowException("Doctor Record");
		}
	}
}
