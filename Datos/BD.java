package Datos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

import Controlador.pedirDatos;

public class BD {

	public static void CrearEmpleado() {

		char respuesta;

		String nombre = "";

		int anosDeExperiencia = -1;

		Scanner sc = new Scanner(System.in);

		try {

			System.out.println("Introduce el nombre del empleado nuevo");
			nombre = sc.nextLine();

			System.out.println("Quiere definir los años de experiencia del trabajador?(S/N)");

			do {

				respuesta = sc.next().charAt(0);

				if (respuesta != 'S' && respuesta != 'N') {
					System.out.println("Respuesta no válida");
				}

			} while (respuesta != 'S' && respuesta != 'N');

			if (respuesta == 'S') {

				System.out.println("Cuantos años tiene de experiencia?");

				do {

					anosDeExperiencia = pedirDatos.pedirNumero(65, 0);

				} while (anosDeExperiencia == Integer.MIN_VALUE);

			}

			if (anosDeExperiencia == -1) {
				ejecutarUpdate("insert into empleados (nombre) values ('" + nombre + "')");
			} else {
				ejecutarUpdate("insert into empleados (nombre,anos_de_experiencia) values ('" + nombre + "', "
						+ anosDeExperiencia + ")");
			}

//			Sentencia para insertar

			System.out.println(nombre + " creado con éxito!!");

		} catch (Exception e) {
			// TODO: handle exception

			System.out.println("No se pudo crear al empleado " + nombre);
		}

	}

	public static void CrearProyecto() {

		String nombre = "";

		char respuestaUsuario;

		int resultado;

		boolean confirmar = false;

		Scanner sc = new Scanner(System.in);

		try {

			do {

				System.out.println("\n" + "Que nombre quiere asignar al proyecto?");
				nombre = sc.nextLine();

				do {

					System.out
							.println("Está seguro de que quiere crear un proyecto con el nombre " + nombre + "? (S/N)");
					respuestaUsuario = sc.next().toUpperCase().charAt(0);
					sc.nextLine();

					if (respuestaUsuario != 'S' && respuestaUsuario != 'N') {
						System.out.println("Respuesta no válida");
					}

				} while (respuestaUsuario != 'S' && respuestaUsuario != 'N');

				if (respuestaUsuario == 'S') {
					confirmar = true;
				}

			} while (!confirmar);

//			Sentencia para insertar

			resultado = ejecutarUpdate("insert into proyectos(nombre) values ('" + nombre + "') ");

			if (resultado == 1) {
				System.out.println(nombre + " creado con éxito!!");
			}

		} catch (Exception e) {
			// TODO: handle exception

			System.out.println("No se pudo crear el proyecto " + nombre + " :(");
			System.out.println("Intentelo de nuevo más tarde");
		}

	}

	public static int ejecutarUpdate(String query) {
		Connection conexion = null;
		Statement sentenciaSQL = null;
		int resultado = 0;

		try {
			// conectar con la base de datos
			Class.forName("com.mysql.jdbc.Driver");
			conexion = DriverManager.getConnection("jdbc:mysql://localhost/hoja10", "root", "");// proporcionamos
																								// la
																								// dirección, el
																								// administrador
																								// y
																								// la clave

			// creamos sentencias ejecutables sobre esa conexión
			sentenciaSQL = conexion.createStatement();

			// almaceno el resultado de la sql en un resulset (conjunto de registros)

			// System.out.println(sql);

			resultado = sentenciaSQL.executeUpdate(query);

		} catch (SQLException | ClassNotFoundException ex) {
			ex.printStackTrace();
			// System.out.println("Error");
		} finally {
			try {
				sentenciaSQL.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				conexion.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return resultado;
	}

	public static int ejecutarSelect(String sql, String campo) throws SQLException {
		Connection conexion = null;
		Statement sentenciaSQL = null;
		ResultSet rs;
		int resultado = 0;

		try {
			// conectar con la base de datos
			Class.forName("com.mysql.jdbc.Driver");
			conexion = DriverManager.getConnection("jdbc:mysql://localhost/hoja10", "root", "");// proporcionamos
																								// la
																								// dirección, el
																								// administrador
																								// y
																								// la clave

			// creamos sentencias ejecutables sobre esa conexión
			sentenciaSQL = conexion.createStatement();

			rs = sentenciaSQL.executeQuery(sql);

			while (rs.next()) {
				resultado = Integer.parseInt(rs.getString(campo));
			}

		} catch (SQLException | ClassNotFoundException ex) {
			ex.printStackTrace();
			// System.out.println("Error");
		} finally {
			sentenciaSQL.close();
			conexion.close();

		}

		return resultado;

	}

	public static ArrayList<Integer> ejecutarSelectIDs(String tabla) throws SQLException {
		Connection conexion = null;
		Statement sentenciaSQL = null;
		ResultSet rs;
		String sql = "Select id from " + tabla;

		ArrayList<Integer> miLista = new ArrayList<Integer>();

		try {
			// conectar con la base de datos
			Class.forName("com.mysql.jdbc.Driver");
			conexion = DriverManager.getConnection("jdbc:mysql://localhost/hoja10", "root", "");// proporcionamos
																								// la
																								// dirección, el
																								// administrador
																								// y
																								// la clave

			// creamos sentencias ejecutables sobre esa conexión
			sentenciaSQL = conexion.createStatement();

			rs = sentenciaSQL.executeQuery(sql);

			while (rs.next()) {
				miLista.add(Integer.parseInt(rs.getString("id")));
			}

		} catch (SQLException | ClassNotFoundException ex) {
			ex.printStackTrace();
			// System.out.println("Error");
		} finally {
			sentenciaSQL.close();
			conexion.close();

		}

		return miLista;
	}

	public static ArrayList<Integer> ejecutarSelectInnerJoin(String sql, String campo) throws SQLException {
		
		Connection conexion = null;
		Statement sentenciaSQL = null;
		ResultSet rs;

		ArrayList<Integer> miLista = new ArrayList<Integer>();

		try {
			// conectar con la base de datos
			Class.forName("com.mysql.jdbc.Driver");
			conexion = DriverManager.getConnection("jdbc:mysql://localhost/hoja10", "root", "");

			// creamos sentencias ejecutables sobre esa conexión
			sentenciaSQL = conexion.createStatement();

			rs = sentenciaSQL.executeQuery(sql);

			while (rs.next()) {
				miLista.add(Integer.parseInt(rs.getString(campo)));
			}

		} catch (SQLException | ClassNotFoundException ex) {
			ex.printStackTrace();
			// System.out.println("Error");
		} finally {
			sentenciaSQL.close();
			conexion.close();

		}

		return miLista;
	}
	
	@SuppressWarnings("finally")
	public static int ejecutarSelectYMostrar(String sql, String tablaModifica) throws SQLException {
		Connection conexion = null;
		Statement sentenciaSQL = null;
		ResultSet rs;
		int contador = 0;
		ArrayList<String> campos = new ArrayList<String>();

		try {
			// conectar con la base de datos
			Class.forName("com.mysql.jdbc.Driver");
			conexion = DriverManager.getConnection("jdbc:mysql://localhost/hoja10", "root", "");// proporcionamos
																								// la
																								// dirección, el
																								// administrador
																								// y
																								// la clave

			// creamos sentencias ejecutables sobre esa conexión
			sentenciaSQL = conexion.createStatement();

			rs = sentenciaSQL.executeQuery(sql);

			campos.add("nombre");

			if (tablaModifica.equals("empleados")) {
				campos.add("anos_de_experiencia");
			}

			campos.add("id");

			// chequeo que el result set no sea vacío, moviendo el cursor a la
			// primer fila. (El cursor inicia antes de la primer fila)
			while (rs.next()) {
				// Si hay resultados obtengo el valor.

				contador++;

				System.out.print("\n" + contador + ".- Nombre:" + rs.getString(campos.get(0)));
				if (tablaModifica.equals("empleados")) {
					System.out.print(" / Años de experiencia: " + rs.getString(campos.get(1)));

					System.out.print(" / ID: " + rs.getString(campos.get(2)));
				} else {
					System.out.print(" / ID: " + rs.getString(campos.get(1)));
				}

			}
			
			System.out.println();

			if (contador == 0) {
				System.out.println("por favor inserte un " + tablaModifica.substring(0, tablaModifica.length() - 1)
						+ "  primero");
			}

		} catch (SQLException | ClassNotFoundException ex) {
			ex.printStackTrace();
			// System.out.println("Error");
		} finally {
			sentenciaSQL.close();
			conexion.close();

			return contador;
		}
	}

	public static int ejecutarSelectCampos(String sql, String campo1, String campo2) throws SQLException {
		Connection conexion = null;
		Statement sentenciaSQL = null;
		ResultSet rs;
		int resultado = 0;

		try {
			// conectar con la base de datos
			Class.forName("com.mysql.jdbc.Driver");
			conexion = DriverManager.getConnection("jdbc:mysql://localhost/hoja10", "root", "");// proporcionamos
																								// la
																								// dirección, el
																								// administrador
																								// y
																								// la clave

			// creamos sentencias ejecutables sobre esa conexión
			sentenciaSQL = conexion.createStatement();

			rs = sentenciaSQL.executeQuery(sql);

			while (rs.next()) {
				resultado = Integer.parseInt(rs.getString(campo1));
			}

		} catch (SQLException | ClassNotFoundException ex) {
			ex.printStackTrace();
			// System.out.println("Error");
		} finally {
			sentenciaSQL.close();
			conexion.close();

		}

		return resultado;

	}

}