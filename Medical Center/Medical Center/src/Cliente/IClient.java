package Cliente;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IClient extends Remote {
	
	public void reciveNot(Patient p, int val) throws RemoteException;

}
