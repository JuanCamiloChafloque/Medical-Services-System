package IPS;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

import Cliente.Patient;
import EPS.EPS;

public interface IIPS extends Remote {
	
	public boolean createINS(String ip) throws RemoteException;
	public boolean createEPS(EPS eps) throws RemoteException;
	public void assignAppointment(Patient p) throws RemoteException, NotBoundException;
	public int getCount() throws RemoteException, NotBoundException;
	
}
