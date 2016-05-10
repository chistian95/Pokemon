package entidades;

import pantalla.Pantalla;

public class EntidadControlable extends Entidad {
	private static final int VELOCIDAD = 10;
	private static final int PROB_ENEMIGO = 100;
	
	private int sx;
	private int cont;
	private int animacion;
	private int pasos;
	private boolean enemigoSpawneado;
	private Entidad enemigo;

	public EntidadControlable(String archivo, int x, int y, Pantalla pt) {
		super(archivo, x, y, 4, pt);
		sx = 0;
		cont = 0;
		animacion = 0;
		pasos = 0;
		enemigoSpawneado = false;
	}
	
	public void animar() {
		pasos += 3;
		double rnd = Math.random()*100;
		if(!enemigoSpawneado && rnd < PROB_ENEMIGO) {
			int escala = 1;
			enemigo = new Entidad("pokemon/caterpie", -150, getPantalla().getHeight()-130, escala, getPantalla());	
			getPantalla().meterEntidad(enemigo);
			enemigoSpawneado = true;				
		}
	}
	
	public void animacion() {
		if(animacion != 0) {
			if(enemigoSpawneado) {
				enemigo.moverConSuelo();
				if(enemigo.getX()+enemigo.getImagen().getWidth(null) >= getX()) {
					pararAnimacion();
					enemigoSpawneado = false;
					getPantalla().quitarEntidad(enemigo);
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
	
	public int getPasos() {
		return pasos;
	}
	
	public int getAnimacion() {
		return animacion;
	}
	
	public boolean isEnemigoSpawneado() {
		return enemigoSpawneado;
	}
	
	public Entidad getEnemigo() {
		return enemigo;
	}
}
