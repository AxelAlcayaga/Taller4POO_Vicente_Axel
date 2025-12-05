package dominio;

public abstract class Certificacion {

	protected String id;
	protected String nombre;
	protected String descripcion;
	protected int creditosMinimos;
	protected int añosValidez;

	public Certificacion(String id, String nombre, String descripcion, int creditosMinimos, int añosValidez) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.creditosMinimos = creditosMinimos;
		this.añosValidez = añosValidez;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getCreditosMinimos() {
		return creditosMinimos;
	}

	public void setCreditosMinimos(int creditosMinimos) {
		this.creditosMinimos = creditosMinimos;
	}

	public int getAñosValidez() {
		return añosValidez;
	}

	public void setAñosValidez(int añosValidez) {
		this.añosValidez = añosValidez;
	}
	
	public abstract void accept(logica.CertificacionVisitor visitor);

}
