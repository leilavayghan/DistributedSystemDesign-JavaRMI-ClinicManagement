package Manager;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.security.Provider.Service;

import interfaces.IClinicService;
import server.ClinicService;
import shared.Config;
/**
 * Class dealing with binding the server with associated port and setting security manager from client end
 */
public class ClientServiceFactory {
	/**
	 * To set security manager or start rmi rgistry
	 */
	public ClientServiceFactory()
	{
		System.setSecurityManager(new RMISecurityManager());
	}
	/**
	 *  Common url for binding the server
	 * @param portNumber
	 * @param managerCode
	 * @return
	 */
	private static String getURL(int portNumber, String managerCode)
	{
		return "rmi://localhost:" + portNumber + "/" + managerCode;
	}
	/**
	 * Binding client with server through interface instance 
	 * @param clinicCode code of each clinic location
	 * @return
	 * @throws Exception
	 * @throws MalformedURLException
	 * @throws RemoteException
	 * @throws NotBoundException
	 */
	public static IClinicService getService(String clinicCode) throws Exception, MalformedURLException, RemoteException, NotBoundException
	{
		switch(clinicCode)
		{
		case Config.CLINIC_CODE_MTL:{
			return(IClinicService)Naming.lookup(getURL(Config.PORT_NUMBER_MTL, Config.CLINIC_CODE_MTL));
			}
		case Config.CLINIC_CODE_DDO:{
			return(IClinicService)Naming.lookup(getURL(Config.PORT_NUMBER_DDO, Config.CLINIC_CODE_DDO));
			}
		case Config.CLINIC_CODE_LVL:{
			return(IClinicService)Naming.lookup(getURL(Config.PORT_NUMBER_LVL, Config.CLINIC_CODE_LVL));
			}
		}
		
		throw new Exception("Invalid clinic code");
	}
	
}
