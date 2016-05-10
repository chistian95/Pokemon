package principal;

import entidades.EntidadControlable;
import entidades.GUI;
import pantalla.Pantalla;

public class Juego {
	private Pantalla pt;
	private int estado = 0;
	
	Juego() {
		pt = new Pantalla(this);
		Thread trPt = new Thread(pt);
		trPt.start();
		
		cargarGUI();
		cargarPJ();
	}
	
	private void cargarGUI() {
		GUI cuadrado = new GUI("botones/cuadrado", 0, 0, "cuadrado", pt);
		cuadrado.setY(pt.getHeight() - cuadrado.getY2());
		GUI triangulo = new GUI("botones/triangulo", 0, 0, "triangulo", pt);
		triangulo.setY(cuadrado.getY() - triangulo.getY2());
		GUI equis = new GUI("botones/equis", 0, 0, "equis", pt);
		equis.setY(cuadrado.getY() - (equis.getY2() / 2));
		equis.setX(cuadrado.getX2());
		GUI circulo = new GUI("botones/circulo", 0, 0, "circulo", pt);
		circulo.setX(pt.getWidth() - circulo.getX2());
		circulo.setY(cuadrado.getY() - (circulo.getY2() / 2));
		
		pt.meterGUI(cuadrado);
		pt.meterGUI(triangulo);
		pt.meterGUI(equis);
		pt.meterGUI(circulo);
	}
	
	private void cargarPJ() {
		EntidadControlable pj = new EntidadControlable("sprites/pj", pt.getWidth()/2, pt.getHeight()-150, pt);		
		pt.meterEntidadControlable(pj);
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
