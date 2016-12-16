package server;

import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * Base class containing functionality to expot the object by server end
 */
public class BaseService 
{
	private static Registry registry;
	
	/*protected void exportServer(String name,int portNumber) throws Exception
	{
		if(registry == null)
		{
			registry = LocateRegistry.createRegistry(portNumber);
		}
		
		Remote obj = UnicastRemoteObject.exportObject((Remote)this, portNumber);
		registry.bind(name, obj);
		//System.out.println("Server running : " + name + portNumber);
	}*/
	
	
}
