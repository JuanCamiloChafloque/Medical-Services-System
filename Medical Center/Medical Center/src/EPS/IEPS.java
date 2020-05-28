package EPS;

import java.rmi.Remote;
import java.rmi.RemoteException;

import Cliente.Patient;

public interface IEPS extends Remote {
	
	public void addPatient(Patient p) throws RemoteException ;
	public boolean haveCovert(Patient p) throws RemoteException;
	public boolean setAppointment(Patient p) throws RemoteException;
	public Patient setUrgAppointment(Patient p) throws RemoteException;

}
