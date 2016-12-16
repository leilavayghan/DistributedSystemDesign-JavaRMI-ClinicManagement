package view;

import java.awt.BorderLayout;
import models.DoctorRecord;
import models.NurseRecord;
import models.StaffRecord;
import server.ClinicService;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.concurrent.TimeUnit;

import javax.naming.Context;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import org.apache.log4j.Logger;
import org.junit.rules.Timeout;

import Manager.ManagerSession;
import data.StaffRecordRepository;
import models.DoctorRecord;
import shared.enumerations.NurseDesignation;
import shared.enumerations.NurseStatus;
import shared.exceptions.RecordIndexOverflowException;

public class MainWindow extends JFrame {

	public static JFrame jframe;
	public  JPanel jpanel;
	static Logger loggerSystem = Logger.getLogger("dsmssystem");
	public static String title = "COMP 6321 DSMS";
	private int createID=0;
	private ManagerSession session;
	
	public MainWindow(ManagerSession session){
		
	JFrame jframe = new JFrame();
	JPanel jpanel = new JPanel();
	
	JMenuBar menubar = new JMenuBar();
	JMenuItem menuDRecord = new JMenuItem("Create Doctor");
	JMenuItem menuNRecord = new JMenuItem("Create Nurse");
	JMenuItem menuTRecord = new JMenuItem("Total Records");
	JMenuItem menuEDRecord = new JMenuItem("Edit Doctor");
	JMenuItem menuENRecord = new JMenuItem("Edit Nurse");
	JMenuItem menuGRecord = new JMenuItem("Record Info");
	JMenuItem menuCTestDoctor = new JMenuItem("CTest1");
	JMenuItem menuCTestNurse = new JMenuItem("CTest2");
	
	menubar.add(menuDRecord);
	menubar.add(menuNRecord);
	menubar.add(menuTRecord);
	menubar.add(menuEDRecord);
	menubar.add(menuENRecord);
	menubar.add(menuGRecord);
	menubar.add(menuCTestDoctor);
	menubar.add(menuCTestNurse);
	
	jpanel.setLayout(new BorderLayout());
	
	JButton btnSubmit = new JButton("SUBMIT");
	jframe.add(btnSubmit,BorderLayout.SOUTH);
	jframe.setSize(new Dimension(740,480));
	jframe.setLocationRelativeTo(null);
	jframe.setResizable(false);
	jframe.add(jpanel);
	jframe.setJMenuBar(menubar);
	jframe.setVisible(true);
	jframe.setTitle(title);
	jframe.setDefaultCloseOperation(EXIT_ON_CLOSE);

	
	menuDRecord.addActionListener(new ActionListener() 
	{
		
		@Override
		public void actionPerformed(ActionEvent e) 
		{
		   jpanel.removeAll();
		   jpanel.add(btnSubmit,BorderLayout.SOUTH);
		   NewDoctor doctorPanel = new NewDoctor();
		   jpanel.add(doctorPanel.panel(false));
		   jframe.add(jpanel);
		   jframe.setVisible(true);
		   createID =1;
		}
	});

	
	menuNRecord.addActionListener(new ActionListener() 
	{
		
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			   jpanel.removeAll();
			   jpanel.add(btnSubmit,BorderLayout.SOUTH);
			   NewNurse nursePanel = new NewNurse();
			   jpanel.add(nursePanel.panel(false));
			   jframe.add(jpanel);
			   jframe.setVisible(true);
			   createID =2;
		}
	});

	menuTRecord.addActionListener(new ActionListener() 
	{
		
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			   jpanel.removeAll();
			   jpanel.add(btnSubmit,BorderLayout.SOUTH);
			   TotalRecords doctorRecords = new TotalRecords();
			   jpanel.add(doctorRecords.panel());
			   jframe.add(jpanel);
			   jframe.setVisible(true);
			   try 
			   {
			    loggerSystem.info("Manager of clinic " + Login.managerCode + " with managerID " + Login.managerID + "wants to know "
			    		+ "total number of records in all clinics");
			    
			   
			    doctorRecords.getTotalRecords(session);
			    
			    createID = 7;
			 
			   }
			   catch (Exception e1) 
			   {
				e1.printStackTrace();
			   }
			
		}
	});
	
	menuEDRecord.addActionListener(new ActionListener() 
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			   jpanel.removeAll();
			   jpanel.add(btnSubmit,BorderLayout.SOUTH);
			   EditRecords edtDoctorRecords = new EditRecords();
			   jpanel.add(edtDoctorRecords.panelDoctorID());
			  
			   jframe.add(jpanel);
			   jframe.setVisible(true);
			   createID = 3;
		}
	});
	
	menuENRecord.addActionListener(new ActionListener() 
	{
		
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			   jpanel.removeAll();
			   jpanel.add(btnSubmit,BorderLayout.SOUTH);
			   EditRecords edtNurseRecords = new EditRecords();
			   jpanel.add(edtNurseRecords.panelNurseID());
			   jframe.add(jpanel);
			   jframe.setVisible(true);
			   createID = 4;
		}
	});
	
	menuGRecord.addActionListener(new ActionListener() 
	{
		
		@Override
		public void actionPerformed(ActionEvent e)
		{
	
			try
			{
				String[] records = session.getService().getRecordIDs();
				for(String record: records) System.out.println(record);
				
			} catch (RemoteException e1) {
				
				e1.printStackTrace();
			}
		}
	});
	
	menuCTestDoctor.addActionListener(new ActionListener() 
	{
		
		@Override
		public void actionPerformed(ActionEvent e)
		{
	
			try
			{
				for(int i=0;i<7;i++)
				{
				session.getService().createDoctorRecord("Yousaf", "Hamid", "2445 rue", "222323", "saasa", "MTL");
				TimeUnit.SECONDS.sleep(1);
				}
			
			} catch (RemoteException e1) {
				
				e1.printStackTrace();
			
			} catch (InterruptedException e1) {
				
				e1.printStackTrace();
			}
		}
	});
	
	
	menuCTestNurse.addActionListener(new ActionListener() 
	{
		
		@Override
		public void actionPerformed(ActionEvent e)
		{
	
			try
			{
				for(int i=0;i<7;i++)
				{
				session.getService().createNurseRecord("Yousaf", "Hamid", NurseDesignation.SENIOR, NurseStatus.ACTIVE, "saasa","MTL");
				TimeUnit.SECONDS.sleep(1);
				}
			
			} catch (RemoteException e1) {
				
				e1.printStackTrace();
			
			} catch (InterruptedException e1) {
				
				e1.printStackTrace();
			
			}
		}
	});
	
	
	btnSubmit.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if(createID == 1)
			{
				
				NewDoctor newdoctor = new NewDoctor();
				try {
					newdoctor.DoctorCreateValid(session);
				} catch (Exception e1) {
				
					e1.printStackTrace();
				}
				
			}
			if(createID == 2)
			{
				NewNurse newnurse = new NewNurse();
				try {
					newnurse.NurseValidCreate(session);
				} catch (Exception e1) {
					
					e1.printStackTrace();
				}
			}
			if(createID == 3)
			{
				   String[] records;
				try {
					records = session.getService().getRecordIDs();
					for(String record: records)
					{
					if(new EditRecords().getRecordIDForEdit().equals(record))
					{
				    jpanel.removeAll();
				    jpanel.add(btnSubmit, BorderLayout.SOUTH);
				    EditRecords editrecords = new EditRecords();
				    jpanel.add(editrecords.doctorEditPanel());
					jframe.add(jpanel);
					jframe.setVisible(true);
					createID = 6;
					break;
					}
					else
					{ 
					JOptionPane.showMessageDialog(jpanel, "Invalid ID for Edition");
					break;
					}
				}
				}
				catch (RemoteException e1) {
					
					e1.printStackTrace();
					
				}
				   
			}
			
			if(createID == 4)
			{
				   jpanel.removeAll();
				   jpanel.add(btnSubmit, BorderLayout.SOUTH);
				   EditRecords editrecords = new EditRecords();
				   jpanel.add(editrecords.nurseEditPanel());
				   jframe.add(jpanel);
				   jframe.setVisible(true);
			}
			
			if(createID == 6)
			{
				EditRecords editrecords = new EditRecords();
				editrecords.editDoctor(session);
			}
		}
	});
}
	

	public JFrame getPanel()
	{
		return this.jframe;
	}

}
