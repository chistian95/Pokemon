package principal;

import pantalla.Pantalla;

public class Juego {
	private Pantalla pt;
	private int estado = EstadoJuego.REINICIAR;
	
	Juego() {
		pt = new Pantalla(this);
		pt.comenzar();
	}
	
	public int getEstado() {
		return estado;
	}
	
	public void setEstado(int estado) {
		this.estado = estado;
	}
	
	public static void main(String[] args) {
		new Juego();
		
	}
}
