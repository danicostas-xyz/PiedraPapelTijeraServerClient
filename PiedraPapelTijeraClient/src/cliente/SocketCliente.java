package cliente;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Scanner;

public class SocketCliente {
	
	public static final int PUERTO = 2024;
	public static final String IP_SERVER = "172.26.100.184";
	public static Scanner sc = new Scanner(System.in);
	public static InetSocketAddress direccionServidor = new InetSocketAddress(IP_SERVER,PUERTO);
	

	public static void main(String[] args) {
		System.out.println("PIEDRA,PAPEL,TIJERAS APP");
		System.out.println("------------------------");
		
	
		boolean ganador = false;
		
		Socket socketAlServidor = new Socket();
		
		try {
			socketAlServidor.connect(direccionServidor);
			while (!ganador) {
				System.out.println("¡PIDRA,PAPEL O TIJERA!");
				int eleccion = menu();
				System.out.println("Has elegido: " + eleccion);
				
				PrintStream ps = new PrintStream(socketAlServidor.getOutputStream());
				ps.println(eleccion);
				
				System.out.println("Mensaje de servidor");
				InputStreamReader isr = new InputStreamReader(socketAlServidor.getInputStream());
				BufferedReader br = new BufferedReader(isr);
				String cadenaRespuesta = br.readLine();
				System.out.println(cadenaRespuesta);
				
				System.out.println("Mensaje de servidor 2");
				cadenaRespuesta = br.readLine();
				
				if(Integer.parseInt(cadenaRespuesta) == 1 || 
						Integer.parseInt(cadenaRespuesta) == 2) {
					System.out.println("Ganador");
					socketAlServidor.close();
					br.close();
					isr.close();
				}else {
					System.out.println("No hay ganador");
				}
				
				
			}
				
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
		
	private static int menu() {
	    System.out.println("=======================");
	    System.out.println("  PIEDRA PAPEL TIJERA  ");
	    System.out.println("=======================");
	    System.out.println("  1 - Piedra");
	    System.out.println("  2 - Papel");
	    System.out.println("  3 - Tijera");
	    System.out.println("  0 - Salir");
	    System.out.println("=======================");
	    System.out.print("Elige una opción: ");
	    
	    int opcion = sc.nextInt();
	    return opcion;
	}


}
