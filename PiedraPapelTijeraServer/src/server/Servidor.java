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
				System.out.println("Esperando al turno del cliente...");
				System.out.println("");
				System.out.println();
				System.out.println("El cliente ha terminado su turno.");
				System.out.println();
				System.out.print("Selecciona tu opción: ");
				int mensajeServer = sc.nextInt();
				System.out.println();

				int resultadoTurno = validarGanadorTurno(mensajeCliente, mensajeServer);
				salida = new PrintStream(socketAlCliente.getOutputStream());

				if (resultadoTurno == 0) {
					System.out.println("Cliente saca: " + mensajeCliente);
					System.out.println("Server saca: " + mensajeServer);
					System.out.println("Resultado turno: Empate");
					salida.println("Cliente saca: " + mensajeCliente + ". | " + 
				               "Server saca: " + mensajeServer + ". | " +
				               "Resultado turno: Empate");

				} else if (resultadoTurno == 1) {
					System.out.println("Cliente saca: " + mensajeCliente);
					System.out.println("Server saca: " + mensajeServer);
					System.out.println("Resultado turno: Cliente gana");
					salida.println("Cliente saca: " + mensajeCliente + ". | " + 
								"Server saca: " + mensajeServer + ". | " +
								"Resultado turno: Cliente gana");
					carlos++;

				} else if (resultadoTurno == 2) {
					System.out.println("Cliente saca: " + mensajeCliente);
					System.out.println("Server saca: " + mensajeServer);
					System.out.println("Resultado turno: Server gana");
					salida.println("Cliente saca: " + mensajeCliente + ". | " + 
								"Server saca: " + mensajeServer + ". | " +
								"Resultado turno: Server gana");
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
					System.out.println();
					salida.println("3");
				}

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Método que valida el resultado del turno del juego Piedra, Papel y Tijera en función de la entrada del cliente y la entrada del servidor-cliente.
	 * Las entradas posibles son: <ul>
	 * 									<li>1: Piedra</li>
	 * 									<li>2: Papel</li>
	 * 									<li>3: Tijera</li>
	 * 							</ul>
	 * @param mCl Entrada del cliente
	 * @param mSr Entrada del servidor-cliente
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
