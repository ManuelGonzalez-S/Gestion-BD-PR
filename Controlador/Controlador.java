package Controlador;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;
import Datos.BD;

public class Controlador {

	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub

		/*
		 * 1.- Tema que elijamos Minimo una relacion n - m
		 * 
		 * BBDD --> hoja10pro
		 * 
		 * 2.- Conexion a MySQL en local
		 * 
		 * 3.- CRUD
		 * 
		 * 4.- Consultas <3 (Con inner join)
		 * 
		 * 
		 * 
		 * 
		 * 1.- Insertar en la T1(proyecto) 2.- Insertar en T2(empleado) asociado a
		 * T1(proyecto) 3.- Borrar T2 Borrar T1 ----> Borro relacion T1-T2 4.- Modificar
		 * T1 y T2 excepto el ID 5.- Asociar un T2(empleado) a un T1(proyecto) (ambos ya
		 * existentes)
		 * 
		 * Consultas---> 6.- Buscar por trabajador los proyectos en los que se incluye
		 * 7.- Buscar proyecto y decir que trabajadores tiene 8.- Cuantos proyectos hay
		 * 9.- Trabajador más usado 10.- Proyectos sin trabajadores
		 */

		int decisionUsuario;

		boolean salir = false;

		Scanner sc = new Scanner(System.in);

		do {

			mostrarMenu();

			decisionUsuario = pedirDatos.pedirNumero(10, 1);

			switch (decisionUsuario) {

			case 1:

				BD.CrearProyecto();

				break;

			case 2:

				BD.CrearEmpleado();

				break;

			case 3:

				borrarDatos();

				break;

			case 4:

				modificarDatos();

				break;

			case 5:

				asociarEmpleadoProyecto();

				break;

			case 6:

				buscarProyectosTrabajador();

				break;

			case 7:

				buscarTrabajadoresProyecto();

				break;

			case 8:

				BD.ejecutarSelectYMostrar("Select * from proyectos", "proyectos");

				break;

			case 9:

				buscarTrabajadorMasProyectos();

				break;

			case 10:

				mostrarTrabajosSinEmpleados();

				break;

			case 11:

				salir = true;

				break;
			}

		} while (!salir);

		sc.close();

	}

	private static void mostrarMenu() {

		System.out.println("\n" + "Que quiere hacer:");
		System.out.println("1.- Crear un proyecto");
		System.out.println("2.- Crear un trabajador");
		System.out.println("3.- Borrar datos");
		System.out.println("4.- Modificar datos");
		System.out.println("5.- Asociar empleados y proyectos");
		System.out.println("6.- Buscar proyectos de un trabajador");
		System.out.println("7.- Mostrar trabajadores de un proyecto");
		System.out.println("8.- Mostrar los proyectos");
		System.out.println("9.- Mostrar trabajador con más proyectos");
		System.out.println("10.- Mostrar proyectos sin trabajadores");
		System.out.println("11.- Salir");
	}

	private static void modificarDatos() throws SQLException {

		int decisionUsuario;

		int idAModificar;

		String tablaModificar;

		Scanner sc = new Scanner(System.in);

		ArrayList<Integer> idValidos = new ArrayList<Integer>();

		int resultados;

		int respuestaUsuario;

		String nombreNuevo;

		int anos_de_experienciaNuevo;

		int maximoAux;

		int minimoAux;

		try {

			System.out.println("\n" + "Que quieres modificar:");
			System.out.println("1.- Empleados" + "\n" + "2.- Proyectos");

			do {
				decisionUsuario = pedirDatos.pedirNumero(2, 1);
			} while (decisionUsuario == Integer.MIN_VALUE);

			if (decisionUsuario == 1) {
				tablaModificar = "empleados";
			} else {
				tablaModificar = "proyectos";
			}

			System.out.println("\n" + "Introduce el id del " + tablaModificar.substring(0, tablaModificar.length() - 1)
					+ " que quiere modificar");

			idValidos = BD.ejecutarSelectIDs(tablaModificar);

			maximoAux = BD.ejecutarSelect("Select max(id) from " + tablaModificar, "max(id)");

			minimoAux = BD.ejecutarSelect("Select min(id) from " + tablaModificar, "min(id)");

			BD.ejecutarSelectYMostrar("Select * from " + tablaModificar, tablaModificar);

			do {

				idAModificar = pedirDatos.pedirNumero(maximoAux, minimoAux);

			} while (idAModificar == Integer.MIN_VALUE || idValidos.indexOf(idAModificar) == -1);

			if (decisionUsuario == 1) {
				System.out.println("Que quiere cambiar:");
				System.out.println("1.- Nombre" + "\n" + "2.- Años de experiencia");

				respuestaUsuario = pedirDatos.pedirNumero(2, 1);

				if (respuestaUsuario == 1) {
					System.out.println("Que nombre quiere ponerle:");
					nombreNuevo = sc.nextLine();

					resultados = BD.ejecutarUpdate("update " + tablaModificar + " set nombre = '" + nombreNuevo
							+ "' where id = " + idAModificar + ';');

				} else {
					System.out.println("Cuantos años de experiencia quiere ponerle:");
					anos_de_experienciaNuevo = pedirDatos.pedirNumero(70, 0);

					resultados = BD.ejecutarUpdate("update " + tablaModificar + " set anos_de_experiencia = "
							+ anos_de_experienciaNuevo + " where id = " + idAModificar);

				}

			} else {

				System.out.println("Que nombre quiere ponerle:");
				nombreNuevo = sc.nextLine();

				resultados = BD.ejecutarUpdate("update " + tablaModificar + " set nombre = '" + nombreNuevo
						+ "' where id = " + idAModificar + ';');

			}

			if (resultados > 0) {
				System.out.println("Se ha modificado correctamente");
			} else {
				System.out.println("No se pudieron actualizar los datos");
			}

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Hubo un error, intentelo mas tarde");
		}

	}

	private static void borrarDatos() throws SQLException {

		int respuestaUsuario;

		int resultados;

		int idAModificar;

		String tablaModificar;

		ArrayList<Integer> idValidos = new ArrayList<Integer>();

		Scanner sc = new Scanner(System.in);

		try {

			System.out.println("Que quiere borrar?");
			System.out.println("1.- Un proyecto");
			System.out.println("2.- Un trabajador");

			do {

				respuestaUsuario = pedirDatos.pedirNumero(2, 1);

			} while (respuestaUsuario == Integer.MIN_VALUE);

			if (respuestaUsuario == 1) {
				tablaModificar = "proyectos";
			} else {
				tablaModificar = "empleados";
			}

			BD.ejecutarSelectYMostrar("select * from " + tablaModificar, tablaModificar);

			resultados = BD.ejecutarSelect("Select max(id) from " + tablaModificar, "max(id)");

			System.out.println("\n" + "Introduce el id del " + tablaModificar.substring(0, tablaModificar.length() - 1)
					+ " que quiere modificar");

			idValidos = BD.ejecutarSelectIDs(tablaModificar);

			do {

				idAModificar = pedirDatos.pedirNumero(resultados, 1);

			} while (idAModificar == Integer.MIN_VALUE || idValidos.indexOf(idAModificar) == -1);

			resultados = BD.ejecutarUpdate(
					"DELETE FROM empleados_has_proyectos WHERE " + tablaModificar + "_id = " + idAModificar);

			resultados = BD.ejecutarUpdate("DELETE FROM " + tablaModificar + " WHERE id = " + idAModificar);

			if (resultados > 0) {
				System.out.println("Se ha borrado correctamente");
			} else {
				System.out.println("No se pudieron borrar los datos");
			}

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Hubo un error, intentelo mas tarde");
		}

	}

	private static void asociarEmpleadoProyecto() throws SQLException {

		Scanner sc = new Scanner(System.in);

		final String EMPLEADOS = "empleados";

		final String PROYECTOS = "proyectos";

		ArrayList<Integer> idValidosEmpleados = new ArrayList<Integer>();

		ArrayList<Integer> idValidosProyectos = new ArrayList<Integer>();

		ArrayList<Integer> idEmpleados = new ArrayList<Integer>();

		int idAux;

		int idProyecto;

		int maximoAux;

		int minimoAux;

		int resultados = 0;

		char decisionUsuario;

		boolean salir = false;

		int resultado = 0;

		try {

			idValidosEmpleados = BD.ejecutarSelectIDs(EMPLEADOS);

			idValidosProyectos = BD.ejecutarSelectIDs(PROYECTOS);

			if (idValidosEmpleados.size() > 0 && idValidosProyectos.size() > 0) {

				System.out.println("Seleccione el id del empleado que quiere seleccionar:");

				BD.ejecutarSelectYMostrar("Select * from empleados", EMPLEADOS);

				minimoAux = idValidosEmpleados.get(idValidosEmpleados.size() - 1);

				maximoAux = idValidosEmpleados.get(0);

				do {

					do {

						idAux = pedirDatos.pedirNumero(minimoAux, maximoAux);

					} while (idAux == Integer.MIN_VALUE || idValidosEmpleados.indexOf(idAux) == -1);

					idEmpleados.add(idAux);

					System.out.println("Quieres relacionar otro trabajador? (S/N)");

					do {

						decisionUsuario = sc.next().toUpperCase().charAt(0);
						sc.nextLine();

						if (decisionUsuario != 'S' && decisionUsuario != 'N') {
							System.out.println("Compruebe su respuesta...");
						}

					} while (decisionUsuario != 'S' && decisionUsuario != 'N');

					if (decisionUsuario == 'N') {
						salir = true;
					}

				} while (!salir);

				System.out.println("Seleccione el id del proyecto al que quiere relacionar al trabajador:");

				BD.ejecutarSelectYMostrar("Select * from proyectos", PROYECTOS);

				minimoAux = idValidosProyectos.get(idValidosProyectos.size() - 1);

				maximoAux = idValidosProyectos.get(0);

				do {

					idProyecto = pedirDatos.pedirNumero(minimoAux, maximoAux);

				} while (idProyecto == Integer.MIN_VALUE || idValidosProyectos.indexOf(idProyecto) == -1);

				for (Integer empleado : idEmpleados) {

					resultado = BD.ejecutarSelect("Select count(*) from empleados_has_proyectos where empleados_id = "
							+ empleado + " && proyectos_id = " + idProyecto + ";", "count(*)");

					if (resultado > 0) {
						System.out
								.println("El empleado con id " + empleado + " ya se encuentra relacionado al proyecto");
					} else {
						BD.ejecutarUpdate("insert into empleados_has_proyectos (empleados_id,proyectos_id) values ("
								+ empleado + ", " + idProyecto + ");");
					}

					resultados++;
				}

				if (resultado > 0) {
					if (resultados >= 1) {
						System.out.println("Inserciones hechas correctamente");
					} else {
						System.out.println("No se ha podido insertar, intentelo de nuevo");
					}
				}

			} else {

				System.out.println("No hay datos suficientes de empleados o proyectos");
				System.out.println("Por favor, inserte datos e intentelo de nuevo");
			}

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Hubo un error, intentelo mas tarde");
		}

	}

	private static void buscarProyectosTrabajador() throws SQLException {

		Scanner sc = new Scanner(System.in);

		int idEmpleado;

		int maximoAux;

		int minimoAux;

		ArrayList<Integer> idValidosEmpleados = new ArrayList<Integer>();

		try {
			idValidosEmpleados = BD.ejecutarSelectInnerJoin(
					"select empleados.* from empleados\r\n" + "inner join empleados_has_proyectos\r\n"
							+ "on empleados.id = empleados_has_proyectos.empleados_id group by empleados.id;",
					"empleados.id");

			maximoAux = idValidosEmpleados.get(idValidosEmpleados.size() - 1);

			minimoAux = idValidosEmpleados.get(0);

			System.out.println("Seleccione el id del trabajador que quiere ver:");

			BD.ejecutarSelectYMostrar(
					"select empleados.* from empleados\r\n" + "inner join empleados_has_proyectos\r\n"
							+ "on empleados.id = empleados_has_proyectos.empleados_id group by empleados.id;",
					"empleados");

			do {

				idEmpleado = pedirDatos.pedirNumero(maximoAux, minimoAux);

			} while (idEmpleado == Integer.MIN_VALUE || idValidosEmpleados.indexOf(idEmpleado) == -1);

			BD.ejecutarSelectYMostrar("select proyectos.* from empleados\r\n" + "inner join empleados_has_proyectos\r\n"
					+ "on empleados.id = empleados_has_proyectos.empleados_id\r\n" + "inner join proyectos\r\n"
					+ "on proyectos.id = empleados_has_proyectos.proyectos_id\r\n" + "where empleados.id = "
					+ idEmpleado, "proyectos");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Hubo un error, intentelo mas tarde");
		}

		System.out.println();

	}

	private static void buscarTrabajadoresProyecto() throws SQLException {

		Scanner sc = new Scanner(System.in);

		int idProyecto;

		int maximoAux;

		int minimoAux;

		ArrayList<Integer> idValidosProyectos = new ArrayList<Integer>();

		try {
			idValidosProyectos = BD.ejecutarSelectInnerJoin(
					"select id from proyectos where id in (select proyectos_id from empleados_has_proyectos group by proyectos_id);",
					"id");

			maximoAux = idValidosProyectos.get(idValidosProyectos.size() - 1);

			minimoAux = idValidosProyectos.get(0);

			System.out.println("Seleccione el id del proyecto que quiere ver:");

			BD.ejecutarSelectYMostrar(
					"select * from proyectos where id in (select proyectos_id from empleados_has_proyectos group by proyectos_id);",
					"id");

			do {

				idProyecto = pedirDatos.pedirNumero(maximoAux, minimoAux);

			} while (idProyecto == Integer.MIN_VALUE || idValidosProyectos.indexOf(idProyecto) == -1);

			BD.ejecutarSelectYMostrar("select empleados.* from empleados\r\n" + "inner join empleados_has_proyectos\r\n"
					+ "on empleados.id = empleados_has_proyectos.empleados_id\r\n" + "inner join proyectos\r\n"
					+ "on proyectos.id = empleados_has_proyectos.proyectos_id\r\n" + "where proyectos.id = "
					+ idProyecto, "proyectos");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Hubo un error, intentelo mas tarde");
		}

		System.out.println();

	}

	private static void buscarTrabajadorMasProyectos() throws SQLException {

		ArrayList<Integer> idTrabajador = new ArrayList<Integer>();

		try {

			idTrabajador = BD.ejecutarSelectInnerJoin(
					"select empleados.id from empleados\r\n" + "inner join empleados_has_proyectos\r\n"
							+ "on empleados.id = empleados_has_proyectos.empleados_id\r\n" + "group by empleados_id\r\n"
							+ "order by count(*) desc\r\n" + "limit 1",
					"id");

			BD.ejecutarSelectYMostrar("Select * from empleados where id = " + idTrabajador.get(0), "id");

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Hubo un error, intentelo mas tarde");
		}

	}

	private static void mostrarTrabajosSinEmpleados() {
		Connection conexion = null;
		Statement sentenciaSQL = null;
		int cantidad = 0;

		try {
			String url = "jdbc:mysql://localhost:3306/hoja10";
			String user = "root";
			String password = "";

			conexion = DriverManager.getConnection(url, user, password);
			sentenciaSQL = conexion.createStatement();

			String sql = "SELECT * FROM proyectos "
					+ "LEFT JOIN empleados_has_proyectos ON proyectos.id = empleados_has_proyectos.proyectos_id "
					+ "WHERE empleados_has_proyectos.empleados_id IS NULL";

			ResultSet rs = sentenciaSQL.executeQuery(sql);

			while (rs.next()) {
				int id = rs.getInt("id");
				String nombre = rs.getString("nombre");
				System.out.printf("Proyecto ID: %d, Nombre: %s%n", id, nombre);
				cantidad++;
			}
			
			if(cantidad == 0) {
				System.out.println("Todos los proyectos existentes se encuentran con trabajadores");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (sentenciaSQL != null) {
					sentenciaSQL.close();
				}
				if (conexion != null) {
					conexion.close();
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
	}

}