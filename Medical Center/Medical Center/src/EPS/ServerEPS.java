package EPS;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;


public class ServerEPS {

	public static void main(String[] args) throws UnknownHostException, NotBoundException {

		try {
			Inet4Address host = (Inet4Address) Inet4Address.getLocalHost();
			String[] arg = {"Colsanitas", host.getHostAddress(), host.getHostAddress(), "6666", "A", "B", "C"};
			Registry registry = LocateRegistry.createRegistry(6666);
			EPS eps = new EPS(arg);
			IEPS interfaceEPS = (IEPS) UnicastRemoteObject.exportObject(eps, 0);
			registry.bind("EPS", interfaceEPS);
			
			System.out.println("Registry de la EPS " + arg[0] + " comenzó...");

			
		} catch (RemoteException e) {
			System.out.println("RemoteException: " + e.getMessage());
		} catch (AlreadyBoundException e) {
			System.out.println("AlreadyBoundException: " + e.getMessage());
		}
	}

}
