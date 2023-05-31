package tablas;

public class Empleados_has_Proyectos {
	
	private int empleado_id;
	
	private int proyecto_id;

	public Empleados_has_Proyectos(int empleado_id, int proyecto_id) {
		super();
		this.empleado_id = empleado_id;
		this.proyecto_id = proyecto_id;
	}

	public int getEmpleado_id() {
		return empleado_id;
	}

	public void setEmpleado_id(int empleado_id) {
		this.empleado_id = empleado_id;
	}

	public int getProyecto_id() {
		return proyecto_id;
	}

	public void setProyecto_id(int proyecto_id) {
		this.proyecto_id = proyecto_id;
	}

	@Override
	public String toString() {
		return "Empleados_has_Proyectos [empleado_id=" + empleado_id + ", proyecto_id=" + proyecto_id + "]";
	}

}
