package entidades;

public class EntidadControlable extends Entidad {
	private static final int VELOCIDAD = 20;
	
	private int sx;
	private int escala;
	private int cont;
	private int animacion;
	private int pasos;

	public EntidadControlable(String archivo) {
		super(archivo, 0, 0);
		sx = 0;
		escala = 10;
		cont = 0;
		animacion = 0;
		pasos = 0;
	}
	
	public void animar() {
		pasos += 3;
	}
	
	public void animacion() {
		cont = cont%VELOCIDAD + 1;
		if(cont == VELOCIDAD) {
			cambiarAnimacion();
			pasos--;
			if(pasos <= 0) {
				pararAnimacion();
			}
		}
	}
	
	private void pararAnimacion() {
		animacion = 0;
		pasos = 0;
		sx = 0;
	}
	
	private void cambiarAnimacion() {
		animacion = animacion%2 + 1;
		sx = 16*animacion;
	}
	
	public int getSx() {
		return sx;
	}
	
	public int getEscala() {
		return escala;
	}
	
	public int getPasos() {
		return pasos;
	}
	
	public int getAnimacion() {
		return animacion;
	}
}
