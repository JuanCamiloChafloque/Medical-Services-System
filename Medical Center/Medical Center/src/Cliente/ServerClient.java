package Cliente;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.Inet4Address;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import IPS.IIPS;

public class ServerClient {

	public static void main(String[] args) throws IOException, NotBoundException, AlreadyBoundException {

		BufferedReader br = new BufferedReader(new FileReader(new File("./pacientes2.txt")));
		String line = br.readLine();
		ArrayList<Patient> lp = new ArrayList<Patient>();
		while (line != null) {
			String[] sep = line.split(";");
			Patient nuevo = new Patient(sep[0],Integer.parseInt(sep[1]), Integer.parseInt(sep[2]),
					sep[3], sep[4],
					Boolean.parseBoolean(sep[5]),Boolean.parseBoolean(sep[6]),
					Boolean.parseBoolean(sep[7]),Boolean.parseBoolean(sep[8]),
					Boolean.parseBoolean(sep[9]),Boolean.parseBoolean(sep[10]),
					Boolean.parseBoolean(sep[11]),Boolean.parseBoolean(sep[12])
					,Boolean.parseBoolean(sep[13]),Boolean.parseBoolean(sep[14])
					,sep[15]);
			lp.add(nuevo);
			line = br.readLine();
		}
		br.close();


		Registry ips = LocateRegistry.getRegistry("127.0.0.1", 5555);
		IIPS iIps = (IIPS) ips.lookup("IPS");
		int port = 5550;

		for(Patient p: lp) {
			p.setPort(port);
			iIps.assignAppointment(p);
		}


		Registry registry = LocateRegistry.createRegistry(port);
		Inet4Address host = (Inet4Address) Inet4Address.getLocalHost();
		Client client = new Client(port, host.getHostAddress());
		IClient iClient = (IClient) UnicastRemoteObject.exportObject(client, 0);
		registry.bind("Client",  iClient);

		System.out.println("Registry del Cliente comenzó...");

	}

}
