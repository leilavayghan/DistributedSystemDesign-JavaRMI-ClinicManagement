package view;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.naming.Context;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import Manager.ClientServiceFactory;
import Manager.ManagerSession;
import server.ClinicService;

public class Login {

	public static String title = "COMP 6321 Distributed Staff Management System";
	public static String managerID,managerCode,managerLocation;
	static Logger loggerSystem = Logger.getLogger("dsmssystem");
	public int ID;
	public void frame(){
	String strLocations[] = {"","Montreal" , "Dollard-Des-Ormeaux","Laval"};	
	JFrame jframe = new JFrame();
	
	JPanel jpanel = new JPanel();
	jpanel.setLayout(new GridLayout(8, 6));
	JLabel lblLocations = new JLabel("Location: ");
	JComboBox cmbLocations = new JComboBox(strLocations);
	JLabel lblManagerID = new JLabel("Manager ID");
	JTextField txtManagerID = new JTextField();
	JButton btnCreateManager = new JButton("New Manager");
	JButton btnSubmit = new JButton("LOGIN");
	managerLocation = cmbLocations.getSelectedItem().toString();
	jpanel.add(lblLocations);
	jpanel.add(cmbLocations);
	jpanel.add(lblManagerID);
	jpanel.add(txtManagerID);
	jpanel.add(btnSubmit);
	jframe.add(jpanel);
	jframe.setTitle(title);
	jframe.setSize(new Dimension(640,480));
	jframe.setLocationRelativeTo(null);
	jframe.setResizable(false);
	jframe.setVisible(true);
	jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
	cmbLocations.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			if(cmbLocations.getSelectedIndex() == 1){
				txtManagerID.setText("MTL");
			}else if(cmbLocations.getSelectedIndex() == 2){
				txtManagerID.setText("DDO");
			}else if(cmbLocations.getSelectedIndex() == 3){
				txtManagerID.setText("LVL");
			}
		}
	});
		
	
	btnSubmit.addActionListener(new ActionListener() 
	{
	
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			managerID = txtManagerID.getText().toString();
			if(managerID.length()>0)
			{
			managerCode = managerID.substring(0,3);
			}
			if(managerID.length() == 7 )
			{	
				try {
					loggerSystem.info("Manager from " + managerCode + " with manager ID " + managerID + "logged in this application");
					MainWindow mainwindow = new MainWindow(new ManagerSession(managerID));
				} catch (Exception e1) {
					
					e1.printStackTrace();
				}
				jframe.dispose();
			}
			else
				JOptionPane.showMessageDialog(jframe,"Invalid Manager-ID");
		}
	});
	
	BasicConfigurator.configure();
	} 
	
	public static void main(String args[])
	{
		Login login = new Login();
		login.frame();
	}
}
