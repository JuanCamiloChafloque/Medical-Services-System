package INS;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ServerINS {

	public static void main(String[] args) throws NotBoundException {
		
		try {
			Registry registry = LocateRegistry.createRegistry(5554);
			Inet4Address host = (Inet4Address) Inet4Address.getLocalHost();
			String insName = "ColSalud";
			String[] params = {insName, host.getHostAddress()};
			INS ins = new INS(params);
			IINS interfaceINS = (IINS) UnicastRemoteObject.exportObject(ins, 0);
			registry.bind("INS", interfaceINS);

			System.out.println("Registry INS comenzó...");

			
		} catch (RemoteException e) {
			System.out.println("RemoteException: " + e.getMessage());
		} catch (AlreadyBoundException e) {
			System.out.println("AlreadyBoundException: " + e.getMessage());
		} catch (UnknownHostException e) {
			System.out.println("UnknownHostException: " + e.getMessage());
		}
	}

}
