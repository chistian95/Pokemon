package entidades;

import pantalla.Pantalla;

public class EntidadControlable extends Entidad {
	private static final int VELOCIDAD = 20;
	private static final int PROB_ENEMIGO = 100;
	
	private int sx;
	private int escala;
	private int cont;
	private int animacion;
	private int pasos;
	private boolean enemigoSpawneado;
	private EntidadAnimada enemigo;

	public EntidadControlable(String archivo, int x, int y, Pantalla pt) {
		super(archivo, x, y, pt);
		sx = 0;
		escala = 7;
		cont = 0;
		animacion = 0;
		pasos = 0;
		enemigoSpawneado = false;
	}
	
	public void animar() {
		pasos += 3;
		double rnd = Math.random()*100;
		if(!enemigoSpawneado && rnd < PROB_ENEMIGO) {
			enemigo = new EntidadAnimada("pokemon/caterpie", -300, getPantalla().getHeight()-150);	
			getPantalla().meterEntidadAnimada(enemigo);
			enemigoSpawneado = true;				
		}
	}
	
	public void animacion() {
		if(animacion != 0) {
			if(enemigoSpawneado) {
				enemigo.moverEntidad();
				if(enemigo.getX() >= getX()) {
					pararAnimacion();
					enemigoSpawneado = false;
					getPantalla().quitarEntidadAnimada(enemigo);
					getPantalla().getJuego().setEstado(1);
				}
			}
		}
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
	
	public boolean isEnemigoSpawneado() {
		return enemigoSpawneado;
	}
	
	public EntidadAnimada getEnemigo() {
		return enemigo;
	}
}
