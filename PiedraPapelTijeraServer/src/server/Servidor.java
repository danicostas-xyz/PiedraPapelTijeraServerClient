package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Servidor {

	public static int PUERTO = 2024;
	public static InetSocketAddress address = new InetSocketAddress(PUERTO);
	public static Scanner sc = new Scanner(System.in);
	static int dani = 0;
	static int carlos = 0;

	/*
	 * 1: PIEDRA 2: PAPEL 3: TIJERA
	 * 
	 */

	public static void main(String[] args) {

		InputStreamReader entrada = null;
		Socket socketAlCliente = null;
		PrintStream salida = null;

		try (ServerSocket serverSocket = new ServerSocket();) {

			serverSocket.bind(address);
			boolean ganador = false;
			String ganadorPartida = "";
			socketAlCliente = serverSocket.accept();
			
			while (!ganador) {

				entrada = new InputStreamReader(socketAlCliente.getInputStream());
				BufferedReader br = new BufferedReader(entrada);
				int mensajeCliente = Integer.parseInt(br.readLine());

				System.out.println("El cliente ha terminado su turno.");
				System.out.print("Selecciona tu opción: ");
				int mensajeServer = sc.nextInt();

				int resultadoTurno = validarGanadorTurno(mensajeCliente, mensajeServer);
				salida = new PrintStream(socketAlCliente.getOutputStream());

				if (resultadoTurno == 0) {
					System.out.println("Cliente saca: " + mensajeCliente);
					System.out.println("Server saca: " + mensajeServer);
					System.out.println("Resultado turno: empate");
					salida.println("Cliente saca: " + mensajeCliente + "\\n " + "Server saca: " + mensajeServer
							+ "Resultado turno: empate");
				} else if (resultadoTurno == 1) {
					System.out.println("Cliente saca: " + mensajeCliente);
					System.out.println("Server saca: " + mensajeServer);
					System.out.println("Resultado turno: cliente gana");
					salida.println("Cliente saca: " + mensajeCliente + "\\n " + "Server saca: " + mensajeServer
							+ "Resultado turno: cliente gana");
					carlos++;
				} else if (resultadoTurno == 2) {
					System.out.println("Cliente saca: " + mensajeCliente);
					System.out.println("Server saca: " + mensajeServer);
					System.out.println("Resultado turno: server gana");
					salida.println("Cliente saca: " + mensajeCliente + "\\n " + "Server saca: " + mensajeServer
							+ "Resultado turno: server gana");
					dani++;
				}

				if (dani == 3 || carlos == 3) {
					if (dani == 3) {
						ganadorPartida = "Dani";
						salida.println("1");
					} else if (carlos == 3) {
						ganadorPartida = "Carlos";
						salida.println("2");
					}

					System.out.println("Partida terminada");
					System.out.println("Ganador: " + ganadorPartida);
					ganador = true;

				} else {
					System.out.println();
					System.out.println("Todavía no hay ganador, siguiente turno.");
					salida.println("3");
				}

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 
	 * @param mCl
	 * @param mSr
	 * @return 0 si hay empate, 1 si gana mCl, 2 si gana mSr,
	 */
	private static int validarGanadorTurno(int mCl, int mSr) {

		/*
		 * 1: PIEDRA 2: PAPEL 3: TIJERA
		 * 
		 */

		if ((mCl == 1 && mSr == 1) || (mCl == 2 && mSr == 2) || (mCl == 3 && mSr == 3)) {
			return 0;
		}

		if ((mCl == 1 && mSr == 2) || (mCl == 2 && mSr == 1) || (mCl == 3 && mSr == 2)) {
			return 1;
		}

		if ((mCl == 1 && mSr == 2) || (mCl == 2 && mSr == 3) || (mCl == 3 && mSr == 1)) {
			return 2;
		}

		return -1;

	}

}
