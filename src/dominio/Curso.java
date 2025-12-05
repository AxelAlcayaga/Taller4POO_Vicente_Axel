package dominio;

import java.util.ArrayList;

public class Curso{
	
	private String nrc;
	private String nombre;
	private int creditos;
	private String area;
	private String semestre;
	private ArrayList<String> prerrequisitos;
	public Curso(String nrc, String nombre, int creditos, String area, String semestre) {
		super();
		this.nrc = nrc;
		this.nombre = nombre;
		this.creditos = creditos;
		this.area = area;
		this.semestre = semestre;
		this.prerrequisitos = new ArrayList<>();
	}
	public String getNrc() {
		return nrc;
	}
	public void setNrc(String nrc) {
		this.nrc = nrc;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public int getCreditos() {
		return creditos;
	}
	public void setCreditos(int creditos) {
		this.creditos = creditos;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getSemestre() {
		return semestre;
	}
	public void setSemestre(String semestre) {
		this.semestre = semestre;
	}
	public ArrayList<String> getPrerrequisitos() {
		return prerrequisitos;
	}
	public void setPrerrequisitos(ArrayList<String> prerrequisitos) {
		this.prerrequisitos = prerrequisitos;
	}
	public void agregarPrerrequisitos(String codigoCurso) {
		prerrequisitos.add(codigoCurso);
	}
	
	
	

}
