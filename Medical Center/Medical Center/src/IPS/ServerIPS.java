package IPS;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ServerIPS {

	public static void main(String[] args) {

		try {
			Registry registry = LocateRegistry.createRegistry(5555);
			IPS ips = new IPS();
			IIPS interfaceIPS = (IIPS) UnicastRemoteObject.exportObject(ips, 0);
			registry.bind("IPS", interfaceIPS);
			System.out.println("Registry IPS Comenzó...");
			
		} catch (RemoteException e) {
			System.out.println("RemoteException: " + e.getMessage());
		} catch (AlreadyBoundException e) {
			System.out.println("AlreadyBoundException: " + e.getMessage());
		}
		
		
	}

}
