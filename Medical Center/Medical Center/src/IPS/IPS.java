package IPS;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;

import Cliente.IClient;
import Cliente.Patient;
import EPS.EPS;
import EPS.IEPS;
import INS.IINS;

public class IPS implements IIPS {

	private String insIp;
	private HashMap<String, EPS> epsList;
	private int count;
	
	public IPS() {
		this.epsList = new HashMap<String, EPS>();
		this.count = 0;
	}

	
	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@Override
	public void assignAppointment(Patient p) throws RemoteException, NotBoundException {

		Thread assign = new Thread(new Runnable() {
			@Override
			public void run() {

				boolean valIPS = false;
				boolean valEPS = false;
				boolean valIPSUrg = false;

				EPS eps = epsList.get(p.getEps());

				Registry registry;

				try {
					registry = LocateRegistry.getRegistry(insIp, 5554);
					IINS remINS = (IINS) registry.lookup("INS");

					if (eps != null) {
						Registry registrys = LocateRegistry.getRegistry(eps.getIpEPS(), eps.getPort());
						IEPS remEPS = (IEPS) registrys.lookup("EPS");

						remEPS.addPatient(p);
						p.setPrioridad(remINS.evPatient(p));

						if (remINS.evPatient(p) < 70) {
							System.out.println("El paciente " + p.getName() + " no cumple con puntos necesarios para la asignación de una cita...");
						} else {
							if (remINS.evPatient(p) >= 90) {
								valIPSUrg = true;
								valIPS = true;
								if (remEPS.haveCovert(p)) {
									valEPS = true;
								}
							} else {
								valIPS = true;
								if (remEPS.haveCovert(p)) {
									valEPS = true;
								}
							}
						}

						if (valIPS && valEPS) {
							if (valIPSUrg) {
								System.out.println("Commit de cita urgente");
								Patient pMove = remEPS.setUrgAppointment(p);
								remINS.addCase(p);
								Registry registryPat = LocateRegistry.getRegistry(p.getIp(), p.getPort());
								IClient remPat = (IClient) registryPat.lookup("Client");


								if(pMove != null){
									Registry registryPatM = LocateRegistry.getRegistry(pMove.getIp(), pMove.getPort());
									IClient remPatM = (IClient) registryPatM.lookup("Client");

									remPatM.reciveNot(pMove, 1);
								}
								remPat.reciveNot(p, 2);

							} else {
								System.out.println("Commit de cita normal");
								Registry registryPat = LocateRegistry.getRegistry(p.getIp(), p.getPort());
								IClient remPat = (IClient) registryPat.lookup("Client");
								remPat.reciveNot(p, 2);
								remEPS.setAppointment(p);
							}
						} else {
							Registry registryPat = LocateRegistry.getRegistry(p.getIp(), p.getPort());
							IClient remPat = (IClient) registryPat.lookup("Client");
							remPat.reciveNot(p, 3);
							System.out.println("Abort...");
						}

					} else {
						System.out.println("El paciente " + p.getName() + " no tiene una eps asociada.");
						System.out.println("Abort...");
					}

				} catch (RemoteException e) {
					System.out.println("RemoteException: " + e.getMessage());
				} catch (NotBoundException e) {
					System.out.println("NotBoundException: " + e.getMessage());
				}
			}

		});
		assign.start();

	}

	@Override
	public boolean createINS(String ip) throws RemoteException {
		this.insIp = ip;
		return true;
	}

	@Override
	public boolean createEPS(EPS eps) throws RemoteException {

		this.epsList.put(eps.getName(), eps);
		return true;
	}

}
