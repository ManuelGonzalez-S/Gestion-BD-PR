package Controlador;

import java.util.Scanner;

public class pedirDatos {

	
	public String pedirNombre() {

		Scanner sc = new Scanner(System.in);

		String nombre;

		try {

			System.out.println("Inserte el nombre que quiere asignarle:");

			nombre = sc.nextLine();

		} catch (Exception e) {
			// TODO: handle exception

			nombre = null;

		}

		return nombre;

	}

	public void pedirSiHayProyectosVinculados() {
		
		System.out.println();
		
	}
	
	public static int pedirNumero(int maximo, int minimo) {

		int numero = 0;
		boolean valido = false;

		Scanner sc = new Scanner(System.in);

		try {

			do {

				System.out.println("\n" + "El numero debe estar entre " + minimo + " y " + maximo);
				numero = sc.nextInt();
				sc.nextLine();

				if (numero < minimo || numero > maximo) {
					System.out.println("Numero no válido, porfavor compruebe su respuesta");
				} else {
					valido = true;
				}

			} while (!valido);

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Respuesta no válida, porfavor compruebe su respuesta");
			numero = Integer.MIN_VALUE;
		}

		return numero;

	}

}
