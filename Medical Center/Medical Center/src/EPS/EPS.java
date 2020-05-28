package EPS;

import java.io.Serializable;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.HashMap;

import Cliente.Patient;
import IPS.IIPS;


public class EPS implements IEPS, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String name;
	private int port;
	private String ipEPS;
	private HashMap<Integer, Patient> usuarios;
	private ArrayList<String> priorityD;
	private Patient[][] matAppointments;
	private int contCol;
	private int contRow;

	public EPS(String[] args) throws RemoteException, NotBoundException {
		this.name = args[0];
		this.port = Integer.parseInt(args[3]);
		this.ipEPS = args[1];
		this.contCol = 0;
		this.contRow = 0;
		this.usuarios = new HashMap<Integer, Patient>();
		this.priorityD = new ArrayList<String>();
		this.matAppointments = new Patient[4][7];
		this.priorityD.add(args[4]);
		this.priorityD.add(args[5]);
		this.priorityD.add(args[6]);

		Registry registry = LocateRegistry.getRegistry(args[1], 5555);
		IIPS newEps = (IIPS) registry.lookup("IPS");
		newEps.createEPS(this);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getIpEPS() {
		return ipEPS;
	}

	public void setIpEPS(String ipEPS) {
		this.ipEPS = ipEPS;
	}

	public HashMap<Integer, Patient> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(HashMap<Integer, Patient> usuarios) {
		this.usuarios = usuarios;
	}

	@Override
	public void addPatient(Patient p) {

		this.usuarios.put(p.getDocument(), p);
	}

	@Override
	public boolean haveCovert(Patient p) throws RemoteException {

		for (String s : priorityD) {
			if (p.getPlan().equals(s)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean setAppointment(Patient p) throws RemoteException {
		// TODO Auto-generated method stub

		synchronized (this) {

			matAppointments[contRow][contCol] = p;
			contCol++;
			if (contCol == 7) {
				contCol = 0;
				contRow++;
			}
			for(int i = 0; i < 4; i++) {
				for(int j = 0; j < 7; j++) {
					if(matAppointments[i][j] != null) {
						System.out.print(matAppointments[i][j].getName() + "\t");
					}else {
						System.out.print(" 0 \t ");
					}
				}
				System.out.println();
				System.out.println();
			}
			System.out.println();
			System.out.println("------------------------------------------------------------------------");
			System.out.println();
			return true;
		}

	}

	@Override
	public Patient setUrgAppointment(Patient p) throws RemoteException {

		synchronized (this) {
			if(matAppointments[0][0] == null) {
				matAppointments[contRow][contCol] = p;
				contCol++;
				if (contCol == 7) {
					contCol = 0;
					contRow++;
				}
				for(int i = 0; i < 4; i++) {
					for(int j = 0; j < 7; j++) {
						if(matAppointments[i][j] != null) {
							System.out.print(matAppointments[i][j].getName() + "\t");
						}else {
							System.out.print(" 0 \t ");
						}
					}
					System.out.println();
					System.out.println();
				}
				System.out.println();
				System.out.println("------------------------------------------------------------------------");
				System.out.println();
				return null;
			}else {

				Patient forChange = null;
				int auxRow = 0, auxCol = 0;
				boolean breaki = true;

				for(int i = 0; i < 4; i++) {
					for(int j = 0; j < 7; j++) {
						if(matAppointments[i][j] != null) {
							if(matAppointments[i][j].getPrioridad() < 90 && breaki) {
								forChange = matAppointments[i][j];
								auxCol = j;
								auxRow = i;
								breaki = false;
							}
						}
					}
				}
				if(breaki == false) {
					matAppointments[auxRow][auxCol] = p;
					matAppointments[contRow][contCol] = forChange;
					contCol++;
					if (contCol == 7) {
						contCol = 0;
						contRow++;
					}
					for(int i = 0; i < 4; i++) {
						for(int j = 0; j < 7; j++) {
							if(matAppointments[i][j] != null) {
								System.out.print(matAppointments[i][j].getName() + "\t");
							}else {
								System.out.print(" 0 \t ");
							}
						}
						System.out.println();
						System.out.println();
					}
					System.out.println();
					System.out.println("------------------------------------------------------------------------");
					System.out.println();
				}else {
					matAppointments[contRow][contCol] = p;
					contCol++;
					if (contCol == 7) {
						contCol = 0;
						contRow++;
					}
					for(int i = 0; i < 4; i++) {
						for(int j = 0; j < 7; j++) {
							if(matAppointments[i][j] != null) {
								System.out.print(matAppointments[i][j].getName() + "\t");
							}else {
								System.out.print(" 0 \t ");
							}
						}
						System.out.println();
						System.out.println();

					}
					System.out.println();
					System.out.println("------------------------------------------------------------------------");
					System.out.println();
					return null;
				}

				return forChange;
			}

		}
	}

}
