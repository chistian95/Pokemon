package pantalla;

import entidades.Entidad;

public class Terreno extends Entidad {
	private Entidad monte;
	private Entidad bosque;

	public Terreno(Pantalla pt) {
		super("fondo/suelo", 720, 0, pt);
		bosque = new Entidad("fondo/suelo2", 720, 0, pt);
		monte = new Entidad("fondo/montes", 720, 0, pt);
	}
	
	public void moverTerreno() {
		setX(getX()-4);
		if(getX() <= 0) {
			setX(720);
		}
		bosque.setX(bosque.getX()-3);
		if(bosque.getX() <= 0) {
			bosque.setX(720);
		}
		monte.setX(monte.getX()-2);
		if(monte.getX() <= 0) {
			monte.setX(720);
		}
	}
	
	public Entidad getMontes() {
		return monte;
	}
	
	public Entidad getBosque() {
		return bosque;
	}
}
