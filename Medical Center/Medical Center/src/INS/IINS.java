package INS;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

import Cliente.Patient;


public interface IINS extends Remote {
	
	public int evPatient(Patient p) throws RemoteException;
	public void addCase(Patient p) throws RemoteException, NotBoundException;

}
