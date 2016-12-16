package view;

import java.rmi.RemoteException;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

import Manager.ManagerSession;
import models.NurseRecord;
import models.StaffRecord;
import server.ClinicService;
import shared.enumerations.NurseDesignation;
import shared.enumerations.NurseStatus;


public class NewNurse extends JPanel{
	
	private JPanel jpanel;
	private static JTextField txtFirstName,txtLastName,txtStatusDate;
	private static String strFirstName,strLastName,strStatusDate;
	private Date statusDate;
	private static NurseDesignation strDesignation;
	private static NurseStatus strStatus;
	private static JComboBox cmbStatus,cmbDesignation;
	static Logger loggerSystem = Logger.getLogger("dsmssystem");
	static Logger loggerServer = Logger.getLogger("server");
	
	public JPanel panel(boolean restriction){			// false for creating , true for editing

	JPanel jpanel = new JPanel();
	
	JLabel lblFirstName = new JLabel("First Name: ");
	txtFirstName = new JTextField();
	
	JLabel lblLastName = new JLabel("Last Name: ");
	txtLastName = new JTextField();
	
	JLabel lblDesignation = new JLabel("Designation: ");
	cmbDesignation = new JComboBox(NurseDesignation.values());
	
	JLabel lblStatus = new JLabel("Status: ");
	cmbStatus= new JComboBox(NurseStatus.values());
	
	JLabel lblStatusDate = new JLabel("Status Date: ");
	txtStatusDate = new JTextField();

	
	jpanel.setLayout(new BoxLayout(jpanel, BoxLayout.Y_AXIS));
	jpanel.add(lblFirstName);
	jpanel.add(txtFirstName);
	
	jpanel.add(lblLastName);
	jpanel.add(txtLastName);
	
	jpanel.add(lblDesignation);
	jpanel.add(cmbDesignation);
	
	jpanel.add(lblStatus);
	jpanel.add(cmbStatus);
	
	jpanel.add(lblStatusDate);
	jpanel.add(txtStatusDate);
	
	if(restriction)
	{
		txtFirstName.setEditable(false);
		txtLastName.setEditable(false);
	}
	return jpanel;
	
	}
	
	public void NurseValidCreate(ManagerSession session) throws Exception
	{
		
		strFirstName = txtFirstName.getText().toString();
		strLastName = txtLastName.getText().toString();
		strDesignation = (NurseDesignation)cmbDesignation.getSelectedItem();
		strStatus = (NurseStatus)cmbStatus.getSelectedItem();
		strStatusDate = txtStatusDate.getText().toString();
		if(strFirstName.equals(""))
			JOptionPane.showMessageDialog(jpanel,"Enter First Name");	
			else if(strLastName.equals(""))
			JOptionPane.showMessageDialog(jpanel,"Enter Last Name");
			else
			{
				session.getService().createNurseRecord(strFirstName, strLastName ,strDesignation,strStatus,strStatusDate,Login.managerCode );
				loggerSystem.info("New Nurse Record is created by manager with managerID "
						 + Login.managerID + " in clinic location " + Login.managerCode);
				loggerServer.info("Create Nurse Operation is Invoked at server of " + Login.managerCode + "by manager with manager-id" + Login.managerID);
			}
	}
}
