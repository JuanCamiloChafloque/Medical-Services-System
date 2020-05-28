package INS;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

import Cliente.Patient;
import IPS.IIPS;

public class INS implements IINS {


	private ArrayList<Patient> cases;
	
	public INS(String[] args) throws RemoteException, NotBoundException {
		
		this.cases = new ArrayList<Patient>();
		Registry registry = LocateRegistry.getRegistry(args[1], 5555);
		IIPS newIps = (IIPS) registry.lookup("IPS");
		newIps.createINS(args[1]);
	}

	@Override
	public void addCase(Patient p) throws RemoteException, NotBoundException {
		this.cases.add(p);
		System.out.println("Se agregó como caso urgente al paciente: " + p.getName());
	}


	@Override
	public int evPatient(Patient p) throws RemoteException {

		int peso = 0;

		if((p.isFiebre() && p.isTos() && p.isCansancio() && p.isDolor()) && (p.isFaltaAire() || p.isInsuficienciaPulmonar()
				|| p.isShockSeptico() || p.isFallaOrganica())) {
			peso += 60;
		}
		if(p.isOtrasTatologias()) {
			peso += 10;
		}
		if(p.isCirugias()) {
			peso += 10;
		}
		if(p.getAge() > 70) {
			peso += 20;
		} else {
			peso += 10;
		}

		return peso;
	}
}
