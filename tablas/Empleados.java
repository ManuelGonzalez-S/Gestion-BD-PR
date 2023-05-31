package tablas;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Empleados {

	private int id;

	private int anos_de_experiencia;

	private String nombre;

	public Empleados(int id, int anos_de_experiencia, String nombre) {
		super();
		this.id = id;
		this.anos_de_experiencia = anos_de_experiencia;
		this.nombre = nombre;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAnos_de_experiencia() {
		return anos_de_experiencia;
	}

	public void setAnos_de_experiencia(int anos_de_experiencia) {
		this.anos_de_experiencia = anos_de_experiencia;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public String toString() {
		return "Empleados [id=" + id + ", anos_de_experiencia=" + anos_de_experiencia + ", nombre=" + nombre + "]";
	}

	public static int UpdateBD(String query) {
		Connection conexion = null;
		Statement sentenciaSQL = null;
		int resultado = 0;

		try {
			// conectar con la base de datos
			Class.forName("com.mysql.jdbc.Driver");
			conexion = DriverManager.getConnection("jdbc:mysql://localhost/videojuegos", "root", "");// proporcionamos
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
				conexion.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return resultado;
	}

}
