package Cliente;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.HashMap;

public class Client implements IClient, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private HashMap<String, Patient> patients;
	private int clientPort;
	private String clientIP;


	public Client(int clientPort, String clientIP) {
		super();
		this.patients = new HashMap<String, Patient>();
		this.clientPort = clientPort;
		this.clientIP = clientIP;
	}

	@Override
	public void reciveNot(Patient p, int val) throws RemoteException {

		switch(val) {
		case 1:
			if(p != null) {
				System.out.println("Al paciente " + p.getName() + " se le modificó la fecha de la cita...");
			}
			break;
		case 2:
			System.out.println("Al paciente  " + p.getName() + " se le asigno una cita...");
			break;
		case 3:
			System.out.println("Al paciente  " + p.getName() + " no se le asigno cita ya que no cumple con los criterios minimos de puntaje...");
			break;
		default:
			break;
		}
	}

	public HashMap<String, Patient> getPatients() {
		return patients;
	}

	public void setPatients(HashMap<String, Patient> patients) {
		this.patients = patients;
	}

	public int getClientPort() {
		return clientPort;
	}

	public void setClientPort(int clientPort) {
		this.clientPort = clientPort;
	}

	public String getClientIP() {
		return clientIP;
	}

	public void setClientIP(String clientIP) {
		this.clientIP = clientIP;
	}
	
	

}
